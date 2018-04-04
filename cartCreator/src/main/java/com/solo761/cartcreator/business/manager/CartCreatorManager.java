package com.solo761.cartcreator.business.manager;

import java.io.File;
import java.io.IOException;

import com.solo761.cartcreator.business.model.CartTypes;

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
	
	/**
	 * Creates bin file for (E)EPROM from prg<br>
	 *		 types:<br>
	 *			HUCKY			-	not implemented yet<br>
	 *			INVERTEDHUCKY<br>
	 *			MAGICDESK<br>
	 *			SIXTEENK		-	not implemented yet<br>
	 *			EIGHTK			-	not implemented yet<br>
	 * @param type - CartTypes enum
	 * @param prg - byte[] with loaded prg
	 * @return <b>byte[]</b> - prepared bin
	 */
	public byte[] createBinFile(CartTypes type, byte[] prg);
	
	/**
	 * Creates bin file for (E)EPROM from prg<br>
	 *		 types:<br>
	 *			HUCKY			-	not implemented yet<br>
	 *			INVERTEDHUCKY<br>
	 *			MAGICDESK<br>
	 *			SIXTEENK		-	not implemented yet<br>
	 *			EIGHTK			-	not implemented yet<br>
	 * @param type - CartTypes enum
	 * @param prg - byte[] with loaded prg
	 * @return <b>byte[]</b> - prepared CRT file
	 */
	public byte[] createCRTFile(CartTypes type, byte[] prg);

}
