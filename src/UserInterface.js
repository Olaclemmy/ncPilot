var UserInterface = {};

UserInterface.file_menu = {};
UserInterface.control_window = {};
UserInterface.dro_window = {};

UserInterface.init = function()
{
	create_window(1024, 600, "ncPilot");

	UserInterface.control_window.window = gui.new_window("Controls");
	UserInterface.control_window.thc_set_voltage = gui.add_slider(UserInterface.control_window.window, "THC", 0, 0, 200);
	gui.separator(UserInterface.control_window.window);
	UserInterface.control_window.arc_ok_enable = gui.add_checkbox(UserInterface.control_window.window, "Arc OK", true);
	gui.separator(UserInterface.control_window.window);
	UserInterface.control_window.x_origin = gui.add_button(UserInterface.control_window.window, "X=0");
	gui.sameline(UserInterface.control_window.window);
	UserInterface.control_window.y_origin = gui.add_button(UserInterface.control_window.window, "Y=0");
	UserInterface.control_window.edit = gui.add_button(UserInterface.control_window.window, "Edit");
	gui.sameline(UserInterface.control_window.window);
	UserInterface.control_window.mdi = gui.add_button(UserInterface.control_window.window, "MDI");
	UserInterface.control_window.park = gui.add_button(UserInterface.control_window.window, "Park");
	gui.sameline(UserInterface.control_window.window);
	UserInterface.control_window.hold = gui.add_button(UserInterface.control_window.window, "Hold");
	UserInterface.control_window.wpos = gui.add_button(UserInterface.control_window.window, "Wpos");
	gui.sameline(UserInterface.control_window.window);
	UserInterface.control_window.touch = gui.add_button(UserInterface.control_window.window, "Touch");
	UserInterface.control_window.run = gui.add_button(UserInterface.control_window.window, "Run");
	gui.sameline(UserInterface.control_window.window);
	UserInterface.control_window.stop = gui.add_button(UserInterface.control_window.window, "Stop");

	UserInterface.dro_window.window = gui.new_window("DRO");
	UserInterface.dro_window.x_label = gui.add_text(UserInterface.dro_window.window, "X:      ");
	gui.set_text_style(UserInterface.dro_window.window, UserInterface.dro_window.x_label, { size: 0.6, color: {r: 1, g: 0, b: 0 }});
	gui.sameline(UserInterface.dro_window.window);
	UserInterface.dro_window.x_dro = gui.add_text(UserInterface.dro_window.window, "0.0000");
	gui.set_text_style(UserInterface.dro_window.window, UserInterface.dro_window.x_dro, { size: 0.6, color: {r: 0, g: 1, b: 0 }});
	gui.sameline(UserInterface.dro_window.window);
	UserInterface.dro_window.x_dro_abs = gui.add_text(UserInterface.dro_window.window, "ABS: ");
	gui.set_text_style(UserInterface.dro_window.window, UserInterface.dro_window.x_dro_abs, { size: 0.350, color: {r: 0.3, g: 0.3, b: 0.3 }});
	gui.sameline(UserInterface.dro_window.window);
	UserInterface.dro_window.x_abs_dro = gui.add_text(UserInterface.dro_window.window, "0.0000");
	gui.set_text_style(UserInterface.dro_window.window, UserInterface.dro_window.x_abs_dro, { size: 0.350, color: {r: 0.74, g: 0.458, b: 0.03 }});
	gui.separator(UserInterface.dro_window.window);
	UserInterface.dro_window.y_label = gui.add_text(UserInterface.dro_window.window, "Y:      ");
	gui.set_text_style(UserInterface.dro_window.window, UserInterface.dro_window.y_label, { size: 0.6, color: {r: 1, g: 0, b: 0 }});
	gui.sameline(UserInterface.dro_window.window);
	UserInterface.dro_window.y_dro = gui.add_text(UserInterface.dro_window.window, "0.0000");
	gui.set_text_style(UserInterface.dro_window.window, UserInterface.dro_window.y_dro, { size: 0.6, color: {r: 0, g: 1, b: 0 }});
	gui.sameline(UserInterface.dro_window.window);
	UserInterface.dro_window.y_dro_abs = gui.add_text(UserInterface.dro_window.window, "ABS: ");
	gui.set_text_style(UserInterface.dro_window.window, UserInterface.dro_window.y_dro_abs, { size: 0.350, color: {r: 0.3, g: 0.3, b: 0.3 }});
	gui.sameline(UserInterface.dro_window.window);
	UserInterface.dro_window.y_abs_dro = gui.add_text(UserInterface.dro_window.window, "0.0000");
	gui.set_text_style(UserInterface.dro_window.window, UserInterface.dro_window.y_abs_dro, { size: 0.350, color: {r: 0.74, g: 0.458, b: 0.03 }});
	gui.separator(UserInterface.dro_window.window);
	UserInterface.dro_window.arc_label = gui.add_text(UserInterface.dro_window.window, "ARC:    ");
	gui.set_text_style(UserInterface.dro_window.window, UserInterface.dro_window.arc_label, { size: 0.6, color: {r: 1, g: 1, b: 0 }});
	gui.sameline(UserInterface.dro_window.window);
	UserInterface.dro_window.arc_dro = gui.add_text(UserInterface.dro_window.window, "0.0000");
	gui.set_text_style(UserInterface.dro_window.window, UserInterface.dro_window.arc_dro, { size: 0.6, color: {r: 0, g: 1, b: 0 }});
	gui.sameline(UserInterface.dro_window.window);
	UserInterface.dro_window.arc_set_label = gui.add_text(UserInterface.dro_window.window, "SET: ");
	gui.set_text_style(UserInterface.dro_window.window, UserInterface.dro_window.arc_set_label, { size: 0.350, color: {r: 0.3, g: 0.3, b: 0.3 }});
	gui.sameline(UserInterface.dro_window.window);
	UserInterface.dro_window.arc_set_dro = gui.add_text(UserInterface.dro_window.window, "0.0");
	gui.set_text_style(UserInterface.dro_window.window, UserInterface.dro_window.arc_set_dro, { size: 0.350, color: {r: 0.74, g: 0.458, b: 0.03 }});
	gui.separator(UserInterface.dro_window.window);
	UserInterface.dro_window.status_label = gui.add_text(UserInterface.dro_window.window, "STATUS: ");
	gui.set_text_style(UserInterface.dro_window.window, UserInterface.dro_window.status_label, { size: 0.6, color: {r: 0, g: 1, b: 1 }});
	gui.sameline(UserInterface.dro_window.window);
	UserInterface.dro_window.status_text = gui.add_text(UserInterface.dro_window.window, "Halt");
	gui.set_text_style(UserInterface.dro_window.window, UserInterface.dro_window.status_text, { size: 0.6, color: {r: 0, g: 1, b: 0 }});
	gui.sameline(UserInterface.dro_window.window);
	UserInterface.dro_window.feed_label = gui.add_text(UserInterface.dro_window.window, "    FEED:");
	gui.set_text_style(UserInterface.dro_window.window, UserInterface.dro_window.feed_label, { size: 0.350, color: {r: 0.3, g: 0.3, b: 0.3 }});
	gui.sameline(UserInterface.dro_window.window);
	UserInterface.dro_window.feed_text = gui.add_text(UserInterface.dro_window.window, "0.0");
	gui.set_text_style(UserInterface.dro_window.window, UserInterface.dro_window.feed_text, { size: 0.350, color: {r: 0.74, g: 0.458, b: 0.03 }});

	UserInterface.file_menu.file = {};
	UserInterface.file_menu.file.menu = window_menu.create("File");
	UserInterface.file_menu.file.open = window_menu.add_button(UserInterface.file_menu.file.menu, "Open");
	UserInterface.file_menu.file.close = window_menu.add_button(UserInterface.file_menu.file.menu, "Close");
	UserInterface.file_menu.view = {};
	UserInterface.file_menu.view.menu = window_menu.create("View");
	UserInterface.file_menu.view.cnc_controls = window_menu.add_checkbox(UserInterface.file_menu.view.menu, "CNC Controls", true);
	UserInterface.file_menu.view.cnc_dro = window_menu.add_checkbox(UserInterface.file_menu.view.menu, "CNC DRO", true);
}
UserInterface.tick = function()
{
	if (window_menu.get_button(UserInterface.file_menu.file.menu, UserInterface.file_menu.file.open))
	{
		GcodeViewer.parse_gcode(file_dialog.open({ filter: ["*.nc", "*.ngc"]}));
	}
	if (window_menu.get_button(UserInterface.file_menu.file.menu, UserInterface.file_menu.file.close))
	{
		exit(0);
	}
	//console.log("Windowid: " + UserInterface.control_window.window + " widgetid: " + UserInterface.control_window.park + "\n");
	if (gui.get_button(UserInterface.control_window.window, UserInterface.control_window.run))
	{
		if (GcodeViewer.last_file != null)
		{
			//console.log("Running!\n");
			if (file.open(GcodeViewer.last_file, "r"))
			{
				//console.log("Opened: " + GcodeViewer.last_file + "\n");
				while(file.lines_available())
				{
					var line = file.read();
					//console.log("Sending line: \"" + line + "\"\n");
					if (line.includes("G0") || line.includes("G1") || line.includes("torch"))
					{
						if (line.includes("fire_torch"))
						{
							MotionControl.send("G38.3 Z-10 F50");
							MotionControl.send("G91 G0 Z0.200");
							MotionControl.send("G91 G0 Z0.160");
							MotionControl.send("M3 S1000");
							MotionControl.send("G4 P1.2"); //Pierce Delay
							MotionControl.send("G90"); //Back to absolute
						}
						else if (line.includes("torch_off"))
						{
							MotionControl.send("M5");
							MotionControl.send("G4 P1"); //Post Delay
							MotionControl.send("G91 G0 Z3"); //Retract
							MotionControl.send("G90"); //Back to absolute
						}
						else
						{
							MotionControl.send(line);
						}
					}
				}
				file.close();
			}
			else
			{
				//console.log("Could not read file!\n");
			}
		}
	}
	if (gui.get_button(UserInterface.control_window.window, UserInterface.control_window.touch))
	{
		MotionControl.send("G38.3 Z-10 F50");
		MotionControl.send("G91 G0 Z0.200");
		MotionControl.send("G91 G0 Z0.5");
		MotionControl.send("G90"); //Back to absolute
	}
	if (gui.get_button(UserInterface.control_window.window, UserInterface.control_window.wpos))
	{
		MotionControl.send("G0 X0.00 Y0.00");
	}
	if (gui.get_button(UserInterface.control_window.window, UserInterface.control_window.park))
	{
		//MotionControl.send("torch_off");
		MotionControl.send("G53 G0 X0 Y0 Z0");
		//MotionControl.send("G54");
	}
	if (gui.get_button(UserInterface.control_window.window, UserInterface.control_window.x_origin))
	{
		MotionControl.machine_parameters.work_offset.x = MotionControl.dro_data.X_MCS;
		MotionControl.SaveParameters();
		if (GcodeViewer.last_file != null)
		{
			GcodeViewer.parse_gcode(GcodeViewer.last_file);
		}
	}
	if (gui.get_button(UserInterface.control_window.window, UserInterface.control_window.y_origin))
	{
		MotionControl.machine_parameters.work_offset.y = MotionControl.dro_data.Y_MCS;
		MotionControl.SaveParameters();
		if (GcodeViewer.last_file != null)
		{
			GcodeViewer.parse_gcode(GcodeViewer.last_file);
		}
	}

	gui.show(UserInterface.control_window.window, window_menu.get_checkbox(UserInterface.file_menu.view.menu, UserInterface.file_menu.view.cnc_controls));
	gui.show(UserInterface.dro_window.window, window_menu.get_checkbox(UserInterface.file_menu.view.menu, UserInterface.file_menu.view.cnc_dro));

	gui.set_text(UserInterface.dro_window.window, UserInterface.dro_window.x_dro, MotionControl.dro_data.X_WCS.toFixed(4));
	gui.set_text(UserInterface.dro_window.window, UserInterface.dro_window.y_dro, MotionControl.dro_data.Y_WCS.toFixed(4));
	gui.set_text(UserInterface.dro_window.window, UserInterface.dro_window.x_abs_dro, MotionControl.dro_data.X_MCS.toFixed(4));
	gui.set_text(UserInterface.dro_window.window, UserInterface.dro_window.y_abs_dro, MotionControl.dro_data.Y_MCS.toFixed(4));
	gui.set_text(UserInterface.dro_window.window, UserInterface.dro_window.arc_dro, MotionControl.dro_data.THC_ARC_VOLTAGE.toFixed(2));
	gui.set_text(UserInterface.dro_window.window, UserInterface.dro_window.feed_text, MotionControl.dro_data.VELOCITY.toFixed(2));
	gui.set_text(UserInterface.dro_window.window, UserInterface.dro_window.status_text, MotionControl.dro_data.STATUS);
	//gui.set_text(UserInterface.dro_window.window, UserInterface.dro_window.arc_set_dro, gui.get_slider(control_window.window, control_window.thc_set_voltage).toFixed(2));
}