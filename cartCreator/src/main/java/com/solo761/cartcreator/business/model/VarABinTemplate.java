package com.solo761.cartcreator.business.model;

import java.nio.ByteBuffer;

/**
 * Used for <b>prg2crt loader</b><br><br>
 * GetFinalBin and GetCRTTemp methods take care of assembling<br>
 * everything to final file<br><br>
 * 
 * Settable properties:<br>
 * byte[] <b>loaderPayload</b><br>
 * byte[] <b>prgPayload</b><br>
 * byte[] <b>prgSize</b><br>
 */
public class VarABinTemplate extends BinFileTemplate {
	
	private byte[] prgSize;

	@Override
	public byte[] getFinalBin() {
		ByteBuffer b = ByteBuffer.allocate(65536);
		b.put(super.getLoaderPayload());
		b.put(prgSize);
		b.put(super.getPrgPayload());
		return b.array();
	}

	@Override
	public byte[] getCRTTemp() {
		ByteBuffer b = ByteBuffer.allocate(super.getLoaderPayload().length + prgSize.length + super.getPrgPayload().length);
		b.put(super.getLoaderPayload());
		b.put(prgSize);
		b.put(super.getPrgPayload());
		return b.array();
	}

	public byte[] getPrgSize() {
		return prgSize;
	}

	// first two bytes are BASIC start address, they're not part of PRG
	// code, so they're not included in size, so we subtract 2
	// from prgSize
	/** Size is length of input file minus first two bytes ( prg.length() - 2 )<br>
	 * use convertIntToLittleEndian method from CartCreatorUtils class<br>
	 * to convert it to little endian
	 * @param prgSize - 
	 */
	public void setPrgSize(byte[] prgSize) {
		this.prgSize = prgSize;
	}
	
	

}
