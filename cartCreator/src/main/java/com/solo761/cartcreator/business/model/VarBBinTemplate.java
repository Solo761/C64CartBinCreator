package com.solo761.cartcreator.business.model;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Used for <b>152blks loader</b><br><br>
 * GetFinalBin and GetCRTTemp methods take care of assembling<br>
 * everything to final file<br><br>
 * 
 * Settable properties:<br>
 * byte[] <b>loaderPayload</b><br>
 * byte[] <b>prgPayload</b><br>
 * byte[] <b>sysAddress</b><br>
 * byte[] <b>prgSize</b><br>
 */
public class VarBBinTemplate extends BinFileTemplate {
	
	private byte[] sysAddress;
	private byte[] prgSize;

	@Override
	public byte[] getFinalBin() {
		// set first byte (low byte) of prgSize to byte 193 of loader
		// set second byte (high byte) of prgSize to byte 199 of loader
		// set first byte (low byte) of sysAddress to byte 209 of loader
		// set second byte (high byte) of sysAddress to byte 210 of loader
		// cut first byte from prg
		// set second (now first) byte of prg to 0x00 
		
		byte[] loader = super.getLoaderPayload();
		loader[193] = prgSize[0];
		loader[199] = prgSize[1];
		loader[209] = sysAddress[0];
		loader[210] = sysAddress[1];
				
		byte[] prg = super.getPrgPayload();
		prg = Arrays.copyOfRange(prg, 1, prg.length - 1);
		prg[0] = (byte) 0x00;
		
		ByteBuffer b = ByteBuffer.allocate(65536);
		b.put(loader);
	  	b.put(prg);
		return b.array();
	}

	@Override
	public byte[] getCRTTemp() {
		// set first byte (low byte) of prgSize to byte 193 of loader
		// set second byte (high byte) of prgSize to byte 199 of loader
		// set first byte (low byte) of sysAddress to byte 209 of loader
		// set second byte (high byte) of sysAddress to byte 210 of loader
		// cut first byte from prg
		// set second (now first) byte of prg to 0x00 
		
		byte[] loader = super.getLoaderPayload();
		loader[193] = prgSize[0];
		loader[199] = prgSize[1];
		loader[209] = sysAddress[0];
		loader[210] = sysAddress[1];
		
		byte[] prg = super.getPrgPayload();
		prg = Arrays.copyOfRange(prg, 1, prg.length - 1);
		prg[0] = (byte) 0x00;
		
		ByteBuffer b = ByteBuffer.allocate(loader.length + prg.length);
		b.put(loader);
	  	b.put(prg);
		return b.array();
	}

	public byte[] getSysAddress() {
		return sysAddress;
	}

	/**Sys Address is address from prg file that's used to start prg,<br>
	 * get it from first 6 bytes of prg file using getSysAddress method from<br>
	 * CartCreatorUtils class and then convert to little endian byte array using<br>
	 * convertIntToLittleEndian method from same class
	 * @param sysAddress
	 */
	public void setSysAddress( byte[] sysAddress ) {
		this.sysAddress = sysAddress;
	}

	public byte[] getPrgSize() {
		return prgSize;
	}

	/**Size is length of input file plus 2047 ( prg.length() + 2047 )<br>
	 * use convertIntToLittleEndian method from CartCreatorUtils class<br>
	 * to convert it to little endian
	 * @param prgSize
	 */
	public void setPrgSize( byte[] prgSize ) {
		this.prgSize = prgSize;
	}
	
	

}
