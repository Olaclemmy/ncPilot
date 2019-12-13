var MotionControl = {};
MotionControl.RealtimeWriteTimer = time.millis();
MotionControl.ProgramHoldFlag = false;
MotionControl.GStack = [];
MotionControl.WaitingForOkay = false;
MotionControl.machine_parameters = {
	machine_extents: { x: 45.5, y: 45.5 },
	machine_axis_invert: { x: 0, y1: 1, y2: 0, z: 0 },
	machine_axis_scale: { x: 1034.5, y: 1034.5, z: 1000 },
	machine_torch_config: { z_rapid_feed: 25, z_probe_feed: 20, floating_head_takeup: 0.1, clearance_height: 2 },
	machine_thc_config: { pin: "A19", comp_vel: 5, filter: 5000, adc_at_zero: 35, adc_at_one_hundred: 650 },
	work_offset: {x: 0, y: 0},
  };
MotionControl.way_point = null;
MotionControl.is_connected = false;
MotionControl.dro_data = { X_MCS: 0.0000, Y_MCS: 0.0000, X_WCS: 0.0000, Y_WCS: 0.0000, VELOCITY: 0.0, THC_ARC_VOLTAGE: 0.0, THC_SET_VOLTAGE: 0.0, STATUS: "Halt" };
MotionControl.serial_line = "";

MotionControl.SaveParameters = function()
{
	if (file.open("machine_parameters.json", "w"))
	{
		file.write(JSON.stringify(this.machine_parameters));
		file.close();
	}
}
MotionControl.PullParameters = function()
{
	var contents = "";
	if (file.open("machine_parameters.json", "r"))
	{
		while(file.lines_available())
		{
			contents += file.read();
		}
		//console.log("" + contents + "\n");
		this.machine_parameters = JSON.parse(contents);
		file.close();
	}
}
MotionControl.ProgramAbort = function()
{
	this.GStack = [];
	this.WaitingForOkay = false;
	serial.write("soft_abort\n");
}
MotionControl.set_waypoint = function(p)
{
	this.way_point = p;
}
MotionControl.go_to_waypoint = function()
{
	if (this.is_connected)
	{
		if (this.way_point != null) this.send("G0 X" + this.way_point.x.toFixed(4) + " Y" + this.way_point.y.toFixed(4));
	}
}
MotionControl.init = function()
{
    this.reconnect_timer = time.millis();
    this.port = ""; //Automatically finds port!
	this.baud_rate = 9600;
	this.PullParameters();
}
MotionControl.send = function(buff)
{
	if (buff == "")
	{
		return;
	}
	buff = buff.replace(/^\s+|\s+$/g, '');
	if (this.WaitingForOkay == true) //If we are waiting for an okay signal, push it to the send stack and send it after we recieve the okay signal
	{
		this.GStack.push(buff);
	}
	else
	{
		var send_line = this.WorkOffsetTransformation(buff);
		//this.delay(300);
		console.log("(send) " + send_line + "\n");
		serial.write(send_line + "\n");
		//this.delay(300);
		this.WaitingForOkay = true;
	}
    
}
MotionControl.delay = function(d)
{
	this.RealtimeWriteTimer = time.millis();
	while((time.millis() - this.RealtimeWriteTimer) < d);
}
MotionControl.send_rt = function(buf)
{
	this.delay(150);
	console.log("(send_rt) " + buf + "\n");
	serial.write(buf + "\n");
	this.delay(150);
}
MotionControl.on_connect = function()
{
	this.send_rt("invert_dir 0 " + this.machine_parameters.machine_axis_invert.x);
	this.send_rt("invert_dir 1 " + this.machine_parameters.machine_axis_invert.y1);
	this.send_rt("invert_dir 2 " + this.machine_parameters.machine_axis_invert.y2);
	this.send_rt("invert_dir 3 " + this.machine_parameters.machine_axis_invert.z);
	this.send_rt("set_scale 0 " + this.machine_parameters.machine_axis_scale.x);
	this.send_rt("set_scale 1 " + this.machine_parameters.machine_axis_scale.y);
	this.send_rt("set_scale 2 " + this.machine_parameters.machine_axis_scale.z);
	this.send_rt("set_torch " + this.machine_parameters.machine_torch_config.z_rapid_feed + " " + this.machine_parameters.machine_torch_config.z_probe_feed + " " + this.machine_parameters.machine_torch_config.floating_head_takeup + " " + this.machine_parameters.machine_torch_config.clearance_height);
	this.send_rt("set_thc_config " + this.machine_parameters.machine_thc_config.pin + " " + this.machine_parameters.machine_thc_config.filter + " " + this.machine_parameters.machine_thc_config.comp_vel + " " + this.machine_parameters.machine_thc_config.adc_at_zero + " " + this.machine_parameters.machine_thc_config.adc_at_one_hundred);
}
MotionControl.WorkOffsetTransformation = function(send_line)
{
	if (send_line.includes("G0") || send_line.includes("G1"))
	{
		var split = send_line.split(" ");
		//console.log("Split: " + JSON.stringify(split) + "\n");
		for (var x = 0; x < split.length; x++)
		{
			if (split[x].includes("X"))
			{
				var newX = parseFloat(split[x].substring(1));
				newX += this.machine_parameters.work_offset.x;
				split[x] = "X" + newX.toFixed(5);
			}
			if (split[x].includes("Y"))
			{
				var newY = parseFloat(split[x].substring(1));
				newY += this.machine_parameters.work_offset.y;
				split[x] = "Y" + newY.toFixed(5);
			}
		}
		//console.log("Before Work Offset: " + send_line);
		send_line = split.join(" ");
		//console.log("After Work Offset: " + send_line);
	}
	return send_line;
}
MotionControl.RecievedOK = function()
{
	this.WaitingForOkay = false;
	var send_line = this.GStack.shift();
	if (send_line == "")
	{
		send_line = this.GStack.shift();
	}
	if (send_line == undefined) return;
	if (this.ProgramHoldFlag == false)
	{
		var send_line = this.WorkOffsetTransformation(send_line);
		//if (SerialTransmissionLog.length > SerialTransmissionLogSize) SerialTransmissionLog.shift(); //Remove the top element in the array so we don't keep creating a longer list
		if (send_line.includes("M30"))
		{
			ProgramUploaded = false; //We can press start again after the program finishes!
		}
		else //Don't send M30 to controller
		{
			//SerialTransmissionLog.push("->" + send_line);
			serial.write(send_line + "\n");
		}
	}
}
MotionControl.parse_serial_line = function (line)
{
	//console.log("(parse_serial_line) " + line + "\n");
	if (line.includes("ok"))
	{
		MotionControl.RecievedOK();
	}
	else if (line.includes("DRO:"))
	{
		var dro_line = line.split("DRO: ")[1];
		var dro_pairs = dro_line.split(" ");
		for (var x = 0; x < dro_pairs.length; x++)
		{
			var pair = dro_pairs[x].split("=");
			var key = pair[0];
			var value = pair[1];
			//console.log("key=" + pair[0] + " value=" + pair[1]);
			if (key == "X_MCS")
			{
				this.dro_data.X_MCS = parseFloat(value);
				this.dro_data.X_WCS = (this.dro_data.X_MCS - this.machine_parameters.work_offset.x);
			}
			if (key == "Y_MCS")
			{
				this.dro_data.Y_MCS = parseFloat(value);
				this.dro_data.Y_WCS = (this.dro_data.Y_MCS - this.machine_parameters.work_offset.y);
			}
			if (key == "VELOCITY")
			{
				this.dro_data.VELOCITY = parseFloat(value);
			}
			if (key == "THC_SET_VOLTAGE")
			{
				this.dro_data.THC_SET_VOLTAGE = parseFloat(value);
			}
			if (key == "THC_ARC_VOLTAGE")
			{
				this.dro_data.THC_ARC_VOLTAGE = parseFloat(value);
			}
			if (key == "UNITS")
			{
                this.dro_data.UNITS = value;
			}
			if (key == "STATUS")
			{
				this.dro_data.STATUS = value;
			}
		}
	}	
}
MotionControl.tick = function()
{
    if (serial.is_open())
	{
		var avail = serial.available();
		if (avail > 0)
		{
			var char = serial.read(avail);
			for (var i = 0; i < char.length; i++)
			{
				if (char[i] == "\n")
				{
                    MotionControl.parse_serial_line(MotionControl.serial_line);
                    //console.log(MotionControl.serial_line + "\n");
					MotionControl.serial_line  = "";
				}
				else
				{
					MotionControl.serial_line  = MotionControl.serial_line  + char[i];
				}
			}
		}
	}
	else
	{
		if (MotionControl.is_connected == true)
		{
			//console.log("Serial disconect!\n");
			MotionControl.is_connected = false;
        }
        if ((time.millis() - MotionControl.reconnect_timer) > 1000) //Try to reconnect once a second
        {
            var available_ports = serial.list_ports();
            //console.log(JSON.stringify(available_ports));
            for (var x = 0; x < available_ports.length; x++)
            {
                if (available_ports[x].description.includes("Teensyduino"))
                {
                    if (serial.open(available_ports[x].port, MotionControl.baud_rate))
                    {
						//console.log("Serial connect!\n");
						MotionControl.on_connect();
                        MotionControl.is_connected = true;
                    }
                }
            }
        }
		
	}
}
