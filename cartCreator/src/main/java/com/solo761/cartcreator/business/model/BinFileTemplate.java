package com.solo761.cartcreator.business.model;

import java.nio.ByteBuffer;

public class BinFileTemplate {
	
	private byte[] headerPayload;
	private byte[] prgSize;
	private byte[] prgPayload;
	
	public byte[] getFinalBin() {
		ByteBuffer b = ByteBuffer.allocate(65536);
		b.put(headerPayload);
		b.put(prgSize);
		b.put(prgPayload);
		return b.array();
	}
	
	public byte[] getCRTTemp() {
		ByteBuffer b = ByteBuffer.allocate(headerPayload.length + prgSize.length + prgPayload.length);
		b.put(headerPayload);
		b.put(prgSize);
		b.put(prgPayload);
		return b.array();
	}
	
	public byte[] getHeaderPayload() {
		return headerPayload;
	}
	public void setHeaderPayload(byte[] headerPayload) {
		this.headerPayload = headerPayload;
	}
	public byte[] getPrgSize() {
		return prgSize;
	}
	public void setPrgSize(byte[] prgSize) {
		this.prgSize = prgSize;
	}
	public byte[] getPrgPayload() {
		return prgPayload;
	}
	public void setPrgPayload(byte[] prgPayload) {
		this.prgPayload = prgPayload;
	}
	
	

}
