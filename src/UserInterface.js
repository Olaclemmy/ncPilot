var UserInterface = {};

UserInterface.file_menu = {};
UserInterface.control_window = {};
UserInterface.dro_window = {};
UserInterface.machine_parameters = {};

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
	UserInterface.dro_window.thc_label = gui.add_text(UserInterface.dro_window.window, "THC: ");
	gui.set_text_style(UserInterface.dro_window.window, UserInterface.dro_window.thc_label, { size: 0.6, color: {r: 0, g: 1, b: 1 }});
	gui.sameline(UserInterface.dro_window.window);
	UserInterface.dro_window.thc_text = gui.add_text(UserInterface.dro_window.window, "Halt");
	gui.set_text_style(UserInterface.dro_window.window, UserInterface.dro_window.thc_text, { size: 0.6, color: {r: 0, g: 1, b: 0 }});

	UserInterface.machine_parameters.window = gui.new_window("Machine Parameters");
	UserInterface.machine_parameters.x_extent = gui.add_input_double(UserInterface.machine_parameters.window, "X Extent", MotionControl.machine_parameters.machine_extents.x);
	UserInterface.machine_parameters.y_extent = gui.add_input_double(UserInterface.machine_parameters.window, "Y Extent", MotionControl.machine_parameters.machine_extents.y);
	UserInterface.machine_parameters.x_scale = gui.add_input_double(UserInterface.machine_parameters.window, "X Scale", MotionControl.machine_parameters.machine_axis_scale.x);
	UserInterface.machine_parameters.y_scale = gui.add_input_double(UserInterface.machine_parameters.window, "Y Scale", MotionControl.machine_parameters.machine_axis_scale.y);
	UserInterface.machine_parameters.z_scale = gui.add_input_double(UserInterface.machine_parameters.window, "Z Scale", MotionControl.machine_parameters.machine_axis_scale.z);
	UserInterface.machine_parameters.x_vel = gui.add_input_double(UserInterface.machine_parameters.window, "X Max Vel", MotionControl.machine_parameters.machine_max_vel.x);
	UserInterface.machine_parameters.y_vel = gui.add_input_double(UserInterface.machine_parameters.window, "Y Max Vel", MotionControl.machine_parameters.machine_max_vel.y);
	UserInterface.machine_parameters.z_vel = gui.add_input_double(UserInterface.machine_parameters.window, "Z Max Vel", MotionControl.machine_parameters.machine_max_vel.z);
	UserInterface.machine_parameters.x_accel = gui.add_input_double(UserInterface.machine_parameters.window, "X Max Accel", MotionControl.machine_parameters.machine_max_accel.x);
	UserInterface.machine_parameters.y_accel = gui.add_input_double(UserInterface.machine_parameters.window, "Y Max Accel", MotionControl.machine_parameters.machine_max_accel.y);
	UserInterface.machine_parameters.z_accel = gui.add_input_double(UserInterface.machine_parameters.window, "Z Max Accel", MotionControl.machine_parameters.machine_max_accel.z);
	UserInterface.machine_parameters.junction_deviation = gui.add_input_double(UserInterface.machine_parameters.window, "Junction Deviation", MotionControl.machine_parameters.machine_junction_deviation);
	UserInterface.machine_parameters.x_invert = gui.add_checkbox(UserInterface.machine_parameters.window, "Invert X", MotionControl.machine_parameters.machine_axis_invert.x);
	gui.sameline(UserInterface.machine_parameters.window);
	UserInterface.machine_parameters.y1_invert = gui.add_checkbox(UserInterface.machine_parameters.window, "Invert Y1", MotionControl.machine_parameters.machine_axis_invert.y1);
	gui.sameline(UserInterface.machine_parameters.window);
	UserInterface.machine_parameters.y2_invert = gui.add_checkbox(UserInterface.machine_parameters.window, "Invert Y2", MotionControl.machine_parameters.machine_axis_invert.y2);
	gui.sameline(UserInterface.machine_parameters.window);
	UserInterface.machine_parameters.z_invert = gui.add_checkbox(UserInterface.machine_parameters.window, "Invert Z", MotionControl.machine_parameters.machine_axis_invert.z);
	UserInterface.machine_parameters.z_probe_feed = gui.add_input_double(UserInterface.machine_parameters.window, "Probe Feed", MotionControl.machine_parameters.machine_torch_config.z_probe_feed);
	UserInterface.machine_parameters.retract_clearance = gui.add_input_double(UserInterface.machine_parameters.window, "Retract Clearance", MotionControl.machine_parameters.machine_torch_config.clearance_height);
	UserInterface.machine_parameters.floating_head_takeup = gui.add_input_double(UserInterface.machine_parameters.window, "Floating Head Takeup",MotionControl.machine_parameters.machine_torch_config.floating_head_takeup);
	UserInterface.machine_parameters.adc_filter = gui.add_input_double(UserInterface.machine_parameters.window, "ADC Filter",MotionControl.machine_parameters.machine_thc.adc_filter);
	UserInterface.machine_parameters.tolerance = gui.add_input_double(UserInterface.machine_parameters.window, "THC Tolerance",MotionControl.machine_parameters.machine_thc.tolerance);
	UserInterface.machine_parameters.ok_button = gui.add_button(UserInterface.machine_parameters.window, "OK");
	gui.show(UserInterface.machine_parameters.window, false);

	UserInterface.file_menu.file = {};
	UserInterface.file_menu.file.menu = window_menu.create("File");
	UserInterface.file_menu.file.open = window_menu.add_button(UserInterface.file_menu.file.menu, "Open");
	UserInterface.file_menu.file.close = window_menu.add_button(UserInterface.file_menu.file.menu, "Close");
	UserInterface.file_menu.view = {};
	UserInterface.file_menu.view.menu = window_menu.create("View");
	UserInterface.file_menu.view.cnc_controls = window_menu.add_checkbox(UserInterface.file_menu.view.menu, "CNC Controls", true);
	UserInterface.file_menu.view.cnc_dro = window_menu.add_checkbox(UserInterface.file_menu.view.menu, "CNC DRO", true);
	UserInterface.file_menu.edit = {};
	UserInterface.file_menu.edit.menu = window_menu.create("Edit");
	UserInterface.file_menu.edit.machine_parameters = window_menu.add_button(UserInterface.file_menu.edit.menu, "Machine Parameters");

	UserInterface.file_menu.tools = {};
	UserInterface.file_menu.tools.menu = window_menu.create("Tools");
	UserInterface.file_menu.tools.notch_master = window_menu.add_button(UserInterface.file_menu.tools.menu, "Notch Master");
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
	if (window_menu.get_button(UserInterface.file_menu.edit.menu, UserInterface.file_menu.edit.machine_parameters))
	{
		gui.show(UserInterface.machine_parameters.window, true);
	}
	if (window_menu.get_button(UserInterface.file_menu.tools.menu, UserInterface.file_menu.tools.notch_master))
	{
		NotchMaster.show();
	}
	//console.log("Windowid: " + UserInterface.control_window.window + " widgetid: " + UserInterface.control_window.park + "\n");
	if (gui.get_button(UserInterface.control_window.window, UserInterface.control_window.run))
	{
		MotionControl.send_gcode_from_viewer();
		motion_control.cycle_start();
	}
	if (gui.get_button(UserInterface.control_window.window, UserInterface.control_window.touch))
	{
		MotionControl.send("G38.3 Z-10 F" + MotionControl.machine_parameters.machine_torch_config.z_probe_feed);
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
		MotionControl.send("G0 Z0");
		MotionControl.send("G53 G0 X0 Y0");
		motion_control.cycle_start();
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

	if (gui.get_button(UserInterface.machine_parameters.window, UserInterface.machine_parameters.ok_button))
	{
		//console.log("OK BUtton!\n");
		MotionControl.machine_parameters.machine_extents.x = gui.get_input_double(UserInterface.machine_parameters.window, UserInterface.machine_parameters.x_extent);
		MotionControl.machine_parameters.machine_extents.y = gui.get_input_double(UserInterface.machine_parameters.window, UserInterface.machine_parameters.y_extent);
		MotionControl.machine_parameters.machine_axis_scale.x = gui.get_input_double(UserInterface.machine_parameters.window, UserInterface.machine_parameters.x_scale);
		MotionControl.machine_parameters.machine_axis_scale.y = gui.get_input_double(UserInterface.machine_parameters.window, UserInterface.machine_parameters.y_scale);
		MotionControl.machine_parameters.machine_axis_scale.z = gui.get_input_double(UserInterface.machine_parameters.window, UserInterface.machine_parameters.z_scale);
		MotionControl.machine_parameters.machine_max_vel.x = gui.get_input_double(UserInterface.machine_parameters.window, UserInterface.machine_parameters.x_vel);
		MotionControl.machine_parameters.machine_max_vel.y = gui.get_input_double(UserInterface.machine_parameters.window, UserInterface.machine_parameters.y_vel);
		MotionControl.machine_parameters.machine_max_vel.z = gui.get_input_double(UserInterface.machine_parameters.window, UserInterface.machine_parameters.z_vel);
		MotionControl.machine_parameters.machine_max_accel.x = gui.get_input_double(UserInterface.machine_parameters.window, UserInterface.machine_parameters.x_accel);
		MotionControl.machine_parameters.machine_max_accel.y = gui.get_input_double(UserInterface.machine_parameters.window, UserInterface.machine_parameters.y_accel);
		MotionControl.machine_parameters.machine_max_accel.z = gui.get_input_double(UserInterface.machine_parameters.window, UserInterface.machine_parameters.z_accel);
		MotionControl.machine_parameters.machine_junction_deviation = gui.get_input_double(UserInterface.machine_parameters.window, UserInterface.machine_parameters.junction_deviation);
		MotionControl.machine_parameters.machine_axis_invert.x = gui.get_checkbox(UserInterface.machine_parameters.window, UserInterface.machine_parameters.x_invert);
		MotionControl.machine_parameters.machine_axis_invert.y1 = gui.get_checkbox(UserInterface.machine_parameters.window, UserInterface.machine_parameters.y1_invert);
		MotionControl.machine_parameters.machine_axis_invert.y2 = gui.get_checkbox(UserInterface.machine_parameters.window, UserInterface.machine_parameters.y2_invert);
		MotionControl.machine_parameters.machine_axis_invert.z = gui.get_checkbox(UserInterface.machine_parameters.window, UserInterface.machine_parameters.z_invert);
		MotionControl.machine_parameters.machine_torch_config.z_probe_feed = gui.get_input_double(UserInterface.machine_parameters.window, UserInterface.machine_parameters.z_probe_feed);
		MotionControl.machine_parameters.machine_torch_config.clearance_height = gui.get_input_double(UserInterface.machine_parameters.window, UserInterface.machine_parameters.retract_clearance);
		MotionControl.machine_parameters.machine_torch_config.floating_head_takeup = gui.get_input_double(UserInterface.machine_parameters.window, UserInterface.machine_parameters.floating_head_takeup);
		MotionControl.machine_parameters.machine_thc.adc_filter = gui.get_input_double(UserInterface.machine_parameters.window, UserInterface.machine_parameters.adc_filter);
		MotionControl.machine_parameters.machine_thc.tolerance = gui.get_input_double(UserInterface.machine_parameters.window, UserInterface.machine_parameters.tolerance);
		MotionControl.adc_readings = [];
		MotionControl.SaveParameters();
		motion_control.set_parameters(MotionControl.machine_parameters);
		gui.show(UserInterface.machine_parameters.window, false);
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
	gui.set_text(UserInterface.dro_window.window, UserInterface.dro_window.thc_text, MotionControl.thc_command);
	gui.set_text(UserInterface.dro_window.window, UserInterface.dro_window.arc_set_dro, MotionControl.dro_data.THC_SET_VOLTAGE);

	if (MotionControl.arc_ok == true)
	{
		gui.set_text_style(UserInterface.dro_window.window, UserInterface.dro_window.arc_dro, { size: 0.6, color: {r: 1, g: 0.750, b: 0 }});
	}
	else
	{
		gui.set_text_style(UserInterface.dro_window.window, UserInterface.dro_window.arc_dro, { size: 0.6, color: {r: 0, g: 1, b: 0 }});
	}
}
