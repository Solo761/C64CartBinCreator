package com.solo761.cartcreator.business.model;

public class JobResult {
	
	private String processed;
	private String errors;
	
	public JobResult() {}
	
	public JobResult( String processed, String errors ) {
		this.processed = processed;
		this.errors = errors;
	}
	
	@Override
	public String toString() {
		return processed + errors;
	}
	
	public String getProcessed() {
		return processed;
	}
	public void setProcessed(String processed) {
		this.processed = processed;
	}
	public String getErrors() {
		return errors;
	}
	public void setErrors(String errors) {
		this.errors = errors;
	}
	
}
