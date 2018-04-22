package com.solo761.cartcreator.business.model;

/** Template object for creating bin files. Comes with two methods that<br>
 * return either fully prepared bin file or temporary bin file (actually the<br>
 * same as regular bin, but not padded with 00s to fill 64k size) that's<br>
 * used to create CRT files
 */
public class BinFileTemplate {
	
	private byte[] loaderPayload;
	private byte[] prgPayload;
	
	/** Returns final, padded with 00s to full 64k size, bin file that's<br>
	 *  ready to be bured to (E)EPROM
	 * @return <b>byte</b>
	 */
	public byte[] getFinalBin()  throws CartCreatorException {
		return null;
	}
	
	/** Returns unpadded bin file that's used to create CRT file since it doesn't<br>
	 * need padding
	 * @return <b>byte</b>
	 */
	public byte[] getCRTTemp()  throws CartCreatorException {
		return null;
	}

	public byte[] getLoaderPayload() {
		return loaderPayload;
	}

	public void setLoaderPayload(byte[] loaderPayload) {
		this.loaderPayload = loaderPayload;
	}

	public byte[] getPrgPayload() {
		return prgPayload;
	}

	public void setPrgPayload(byte[] prgPayload) {
		this.prgPayload = prgPayload;
	}

	
	
}
