package com.solo761.cartcreator.business.model;

/**
 * Object that stores input and output file paths.<br>
 * Part of JobList object 
 *
 */
public class FilePath {
	private String inputFile;
	private String outputFile;
	
	public String getInputFile() {
		return inputFile;
	}
	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}
	public String getOutputFile() {
		return outputFile;
	}
	public void setOutputFile(String outputFile) {
		this.outputFile = outputFile;
	}
		
}
