package com.r4l.curseforge_instance_manager.file;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.r4l.curseforge_instance_manager.ProgramInstance;
import com.r4l.curseforge_instance_manager.json.files.FolderPaths;
import com.r4l.curseforge_instance_manager.json.files.Whitelists;

public class FolderManager {
	
	ProgramInstance program;
	
	FolderPaths folderPaths;
	
	Whitelists whitelist;
	
	
	private String minecraftFolder;
	
	private String instanceFolder;
	
	public FolderManager() {
		program = ProgramInstance.getInstance();
		
		folderPaths = program.getFolderPaths();
		whitelist = program.getWhitelist();
		
		minecraftFolder = folderPaths.getMinecraftPath();
		instanceFolder = folderPaths.getInstancesPath();
		
	}
	
	public List<Path> getInstances() {
		return FileManager.getFolders(Paths.get(instanceFolder));
	}
	
	public void copyInstance(Path instance) {
		FileManager.copyFolder(Paths.get(minecraftFolder), instance, whitelist.getFolders(), whitelist.getFiles());
	}
	
	public void copyToInstance(Path instance) {
		FileManager.copyFolder(instance, Paths.get(minecraftFolder), whitelist.getFolders(), whitelist.getFiles());
	}
	
	
	
	public void printPaths() {
		
		System.out.println("minecraftFolder");
		System.out.println(minecraftFolder);
		System.out.println();
		System.out.println("instanceFolder");
		System.out.println(instanceFolder);
		System.out.println();
		
	}

}
