package com.r4l.curseforge_instance_manager;

import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

import com.r4l.curseforge_instance_manager.file.FolderManager;

public class Main {
	public static void main(String[] args) {
		
		@SuppressWarnings("unused")
		ProgramInstance program = ProgramInstance.getInstance();
		
		FolderManager fm = new FolderManager();
		boolean keepAlive = true;
		
		Scanner scanner = new Scanner(System.in);
		
		while(keepAlive) {
			System.out.println("1) Copy Instance to Minecraft");
			System.out.println("1) Copy Minecraft to Instance");
			System.out.println("2) Exit");
			int input = scanner.nextInt();
			
			switch(input) {

            case 1:
            	
            	Path instance = getInstance(scanner, fm);
            	
            	System.out.println("\nSelected " + instance.toString() + "\n");
            	fm.copyInstance(instance);
            	System.out.println("\nAn Instance has been copied!\n");
            	
                break;
                
            case 2:
            	Path instance1 = getInstance(scanner, fm);
            	
            	System.out.println("\nSelected " + instance1.toString() + "\n");
            	fm.copyInstance(instance1);
            	System.out.println("\nAn Instance has been updated!\n");
                
            case 3:
                System.out.println("\nExiting...\n");
                keepAlive = false;
                break;

            default:
                System.out.println("\nUnknown option!\n");
			
			}

		}
	}
	
	//getting a path to a Minecraft Instance
	private static Path getInstance(Scanner scanner, FolderManager fm) {
		List<Path> instances = fm.getInstances();
    	Path instance = null;
    	
    	while(instance == null) {
    	
    	for (int i = 0; i < instances.size(); i++) {
    		System.out.println(Integer.toString(i + 1) + ") " + instances.get(i).getFileName().toString());
    	}

    	try {
    		int index = scanner.nextInt();
    		instance = instances.get(index - 1);
    	}	catch (IndexOutOfBoundsException e) {
    		System.out.println("Invalid Index\n\n");
    		
    	}
    	
	}
    	return instance;
	}
	
	
}
