package com.r4l.curseforge_instance_manager;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.r4l.curseforge_instance_manager.json.files.FolderPaths;
import com.r4l.curseforge_instance_manager.json.files.Whitelists;

public class ProgramInstance {
	
	private static ProgramInstance instance;
	
	private ObjectMapper mapper;
	
	private FolderPaths folderPaths;
	
	private Whitelists whitelist;
	
	//Files
	private File folderPathsFile;
	
	private File whitelistFile;
	
	public static ProgramInstance getInstance() {
		if (instance == null) {
			instance = new ProgramInstance();
			instance.initialise();
		}
		
		return instance;
	}
	
	
	private void initialise() {
		//Create object mapper
		mapper = new ObjectMapper();
		// Readable JSON
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		
		
		
		folderPathsFile = new File("folderPaths.json");
		whitelistFile = new File("settingsFile.json");
		
		
		
		if (!folderPathsFile.exists()) {
			
			folderPaths = new FolderPaths();
			
			@SuppressWarnings("resource")
			Scanner input = new Scanner(System.in);
			
			
			System.out.println("Please enter .minecraft folder path. (Enter 'D' or 'Default' to enter Default value)");
			String minecraftPath = input.nextLine();
			System.out.println("Please enter instance folder path. (Enter 'D' or 'Default' to enter Default value)");
			String instancesPath = input.nextLine();
			
			boolean mp = false;
			boolean ip = false;
			
			if(minecraftPath.equals("Default") || minecraftPath.equals("D")) {
				folderPaths.createDefaultMP();
				mp = true;
			}
			
			if (instancesPath.equals("Default") || instancesPath.equals("D")) {
				folderPaths.createDefaultIP();
				ip = true;
			}
			
			if (!mp) {
				folderPaths.setMinecraftPath(minecraftPath);
			} 
			
			if (!ip) {
				folderPaths.setInstancesPath(instancesPath);
			}

            try {
				mapper.writeValue(folderPathsFile, folderPaths);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				folderPaths = mapper.readValue(folderPathsFile, FolderPaths.class);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		
		
		if (!whitelistFile.exists()) {
			
			whitelist = new Whitelists();
			whitelist.createDefault();
            try {
				mapper.writeValue(whitelistFile, whitelist);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				whitelist = mapper.readValue(whitelistFile, Whitelists.class);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		System.out.println();
		System.out.println("Minecraft folder Path: " + folderPaths.getMinecraftPath());
		System.out.println("Instances folder Path: " + folderPaths.getInstancesPath());
		System.out.println();
		
	}




	public FolderPaths getFolderPaths() {
		return folderPaths;
	}


	public Whitelists getWhitelist() {
		return whitelist;
	}

}
