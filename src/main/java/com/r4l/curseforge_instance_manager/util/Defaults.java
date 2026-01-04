package com.r4l.curseforge_instance_manager.util;

import java.io.File;

public class Defaults {
	
	private static File userHomeDir = new File(System.getProperty("user.home"));
	

	public static String[] DEFAULT_FOLDERS = new String [] {"config", "essential", "mods", "resourcepacks", "resources", "shaderpacks", "structures"};
	
	public static String[] DEFAULT_FILES = new String [] {"options.txt", "optionsof.txt", "optionsshaders.txt"};
	
	public static String DEFAULT_INSTANCES_FOLDER = "C:\\Users\\" + userHomeDir.getName() + "\\curseforge\\minecraft\\Instances";
	
	public static String DEFAULT_MINECRAFT_FOLDER = "C:\\Users\\" + userHomeDir.getName() + "\\AppData\\Roaming\\.minecraft";
}