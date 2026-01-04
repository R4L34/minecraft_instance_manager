package com.r4l.curseforge_instance_manager.json.files;

import com.r4l.curseforge_instance_manager.util.Defaults;

public class FolderPaths {
	
	private String minecraftPath;
	
	private String instancesPath;
	
	public FolderPaths() {}
	
	
	public void createDefaultMP() {
		minecraftPath = Defaults.DEFAULT_MINECRAFT_FOLDER;
	}

	public void createDefaultIP() {
		instancesPath = Defaults.DEFAULT_INSTANCES_FOLDER;
	}

	public String getMinecraftPath() {
		return minecraftPath;
	}

	public void setMinecraftPath(String minecraftPath) {
		this.minecraftPath = minecraftPath;
	}

	public String getInstancesPath() {
		return instancesPath;
	}

	public void setInstancesPath(String instancesPath) {
		this.instancesPath = instancesPath;
	}

}
