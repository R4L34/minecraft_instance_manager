package com.r4l.curseforge_instance_manager.json.files;

import java.util.List;
import com.r4l.curseforge_instance_manager.util.Defaults;

import java.util.Arrays;

public class Whitelists {
	
	private List<String> folders;
	
	private List<String> files;
	
	public Whitelists() {}
	
	public void createDefault() {
		folders = Arrays.asList(Defaults.DEFAULT_FOLDERS);
		files = Arrays.asList(Defaults.DEFAULT_FILES);
	}
	
	
	public List<String> getFolders() {
        return folders;
    }

    public void setFolders(List<String> folders) {
        this.folders = folders;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }
}