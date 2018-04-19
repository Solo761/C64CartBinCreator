package com.solo761.cartcreator.business.model;

public enum LoaderTypes {
	PRG2CRT("prg2crt.py loader by Frank Buß"),
	HUCKY("152Blks loader");
	
	private String type;
	
	LoaderTypes(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return type;
	}

}
