package com.solo761.cartcreator.business.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Object that stores all data about what needs to be done.<br>
 *
 */
public class JobList {
	
	private List<FilePath> fileList = new ArrayList<FilePath>();
	private CartTypes cartType;
	private String crtExtension = ".crt";
	private String binExtension = ".bin";
	private boolean makeCRT;
	private boolean makeBin;
	private boolean help;
	private String errors;
	
	public List<FilePath> getFileList() {
		return fileList;
	}
	public void setFileList(List<FilePath> fileList) {
		this.fileList = fileList;
	}
	public CartTypes getCartType() {
		return cartType;
	}
	public void setCartType(CartTypes cartType) {
		this.cartType = cartType;
	}
	public String getCrtExtension() {
		return crtExtension;
	}
	public void setCrtExtension(String crtExtension) {
		this.crtExtension = crtExtension;
	}
	public String getBinExtension() {
		return binExtension;
	}
	public void setBinExtension(String binExtension) {
		this.binExtension = binExtension;
	}
	public boolean isMakeCRT() {
		return makeCRT;
	}
	public void setMakeCRT(boolean makeCRT) {
		this.makeCRT = makeCRT;
	}
	public boolean isMakeBin() {
		return makeBin;
	}
	public void setMakeBin(boolean makeBin) {
		this.makeBin = makeBin;
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
