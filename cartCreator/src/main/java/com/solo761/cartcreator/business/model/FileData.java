package com.solo761.cartcreator.business.model;

public class FileData {
	
	private String path;
	private String fileName;
	
	public FileData() {}
	
	public FileData(String path, String fileName) {
		this.path = path;
		this.fileName = fileName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
