package com.solo761.cartcreator.business.model;

import java.nio.ByteBuffer;

/** Template object for creating bin files. Comes with two methods that<br>
 * return either fully prepared bin file or temporary bin file (actually the<br>
 * same as regular bin, but not padded with 00s to fill 64k size) that's<br>
 * used to create CRT files
 */
public class BinFileTemplate {
	
	private byte[] loaderPayload;
	private byte[] prgSize;
	private byte[] prgPayload;
	
	/** Returns final, padded with 00s to full 64k size, bin file that's<br>
	 *  ready to be bured to (E)EPROM
	 * @return <b>byte</b>
	 */
	public byte[] getFinalBin() {
		ByteBuffer b = ByteBuffer.allocate(65536);
		b.put(loaderPayload);
		b.put(prgSize);
		b.put(prgPayload);
		return b.array();
	}
	
	/** Returns unpadded bin file that's used to create CRT file since it doesn't<br>
	 * need padding
	 * @return <b>byte</b>
	 */
	public byte[] getCRTTemp() {
		ByteBuffer b = ByteBuffer.allocate(loaderPayload.length + prgSize.length + prgPayload.length);
		b.put(loaderPayload);
		b.put(prgSize);
		b.put(prgPayload);
		return b.array();
	}

	public byte[] getLoaderPayload() {
		return loaderPayload;
	}

	public void setLoaderPayload(byte[] loaderPayload) {
		this.loaderPayload = loaderPayload;
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
