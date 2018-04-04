package com.solo761.cartcreator.business.manager;

import java.io.File;
import java.io.IOException;

public interface CartCreatorManager {
	
	
	/**
	 * <p>Loads file from file system intro byte array</p>
	 * @param file - input File path
	 * @return <b>byte[]</b> - loaded file
	 * @throws IOException
	 */
	byte[] loadFile(File file) throws IOException;
	
	/**
	 * <p>Saves byte array to file system</p>
	 * @param data - byte array containing data to be saved
	 * @param file - output File path
	 * @throws IOException
	 */
	void saveFile(byte[] data, File file) throws IOException;
	
	/**
	 * <p>Calculates prg size to be set in bin and returns it as byte<br>
	 * array in little endian format ( { lower value, higher value } )</p> 
	 * @param prgSize - integer with prg file size
	 * @return <b>byte[]</b> - byte array with little endian formatted prg size
	 */
	byte[] calculatePrgSize(int prgSize);

}
