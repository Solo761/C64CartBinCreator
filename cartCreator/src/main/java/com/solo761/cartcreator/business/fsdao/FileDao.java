package com.solo761.cartcreator.business.fsdao;

import java.io.File;
import java.io.IOException;

public interface FileDao {
	
	/**
	 * <p>Loads file from file system intro byte array</p>
	 * @param file - input File path
	 * @return <b>byte[]</b> - loaded file
	 * @throws IOException
	 */
	byte[] loadFile(File file) throws IOException ;
	
	
	/**
	 * <p>Saves byte array to file system</p>
	 * @param data - byte array containing data to be saved
	 * @param file - output File path
	 * @throws IOException
	 */
	void saveFile(byte[] data, File file) throws IOException;

}
