package com.solo761.cartcreator.business.model;

public class Arguments {
	
	private String inputFile;
	private CartTypes cartType;
	private String outputFile;
	private boolean makeCRT;
	private boolean help;
	private String errors;
	
	public String getInputFile() {
		return inputFile;
	}
	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}
	public CartTypes getCartType() {
		return cartType;
	}
	public void setCartType(CartTypes cartType) {
		this.cartType = cartType;
	}
	public String getOutputFile() {
		return outputFile;
	}
	public void setOutputFile(String outputFile) {
		this.outputFile = outputFile;
	}
	public boolean isMakeCRT() {
		return makeCRT;
	}
	public void setMakeCRT(boolean makeCRT) {
		this.makeCRT = makeCRT;
	}
	public boolean isHelp() {
		return help;
	}
	public void setHelp(boolean help) {
		this.help = help;
	}
	public String getErrors() {
		return errors;
	}
	public void setErrors(String errors) {
		this.errors = errors;
	}

}
