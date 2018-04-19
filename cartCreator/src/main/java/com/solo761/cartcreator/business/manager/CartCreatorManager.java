package com.solo761.cartcreator.business.manager;

import java.io.File;
import java.io.IOException;

import com.solo761.cartcreator.business.model.CartTypes;
import com.solo761.cartcreator.business.model.LoaderTypes;

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
	 * Creates bin file for (E)EPROM from prg<br>
	 * <br>
	 *		 cTypes:<br>
	 *			HUCKY<br>
	 *			INVERTEDHUCKY<br>
	 *			MAGICDESK<br>
	 *			SIXTEENK		-	not implemented yet<br>
	 *			EIGHTK			-	not implemented yet<br>
	 *<br>
	 *		 lTypes:<br>
	 *			PRG2CRT<br>
	 *			HUCKY<br>
	 * @param cType - CartTypes enum
	 * @param lType - LoaderTypes enum
	 * @param prg - byte[] with loaded prg
	 * @return <b>byte[]</b> - prepared bin
	 */
	public byte[] createBinFile(CartTypes cType, LoaderTypes lType, byte[] prg);
	
	/**
	 * Creates CRT file for emulator from prg<br>
	 * <br>
	 *		 cTypes:<br>
	 *			HUCKY<br>
	 *			INVERTEDHUCKY<br>
	 *			MAGICDESK<br>
	 *			SIXTEENK		-	not implemented yet<br>
	 *			EIGHTK			-	not implemented yet<br>
	 *<br>
	 *		 lTypes:<br>
	 *			PRG2CRT<br>
	 *			HUCKY<br>
	 * @param type - CartTypes enum
	 * @param lType - LoaderTypes enum
	 * @param prg - byte[] with loaded prg
	 * @return <b>byte[]</b> - prepared CRT file
	 */
	public byte[] createCRTFile(CartTypes cType, LoaderTypes lType, byte[] prg);

}
