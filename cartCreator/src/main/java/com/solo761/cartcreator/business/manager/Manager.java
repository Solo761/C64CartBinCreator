package com.solo761.cartcreator.business.manager;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import com.solo761.cartcreator.business.fsdao.FileDao;
import com.solo761.cartcreator.business.fsdao.impl.FileDaoImpl;
import com.solo761.cartcreator.business.logic.CRTGenerator;
import com.solo761.cartcreator.business.model.BinFileTemplate;
import com.solo761.cartcreator.business.model.CartCreatorException;
import com.solo761.cartcreator.business.model.CartTypes;
import com.solo761.cartcreator.business.model.LoaderTypes;
import com.solo761.cartcreator.business.model.VarABinTemplate;
import com.solo761.cartcreator.business.model.VarBBinTemplate;
import com.solo761.cartcreator.business.utils.CartCreatorByteArrays;
import com.solo761.cartcreator.business.utils.Utils;

public class Manager {
	
	private FileDao fileDao = new FileDaoImpl();
	private CRTGenerator crtGenerator = new CRTGenerator(); 

	
	/**
	 * <p>Loads file from file system intro byte array</p>
	 * @param file - input File path
	 * @return <b>byte[]</b> - loaded file
	 * @throws IOException
	 */
	public byte[] loadFile(File file) throws IOException {
		byte[] fileContent = fileDao.loadFile(file);
		return fileContent;
	}
	
	/**
	 * <p>Saves byte array to file system</p>
	 * @param data - byte array containing data to be saved
	 * @param file - output File path
	 * @throws IOException
	 */
	public void saveFile(byte[] data, File file) throws IOException {
		fileDao.saveFile(data, file);
	}
	
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
	public byte[] createBinFile(CartTypes cType, LoaderTypes lType, byte[] prg) throws CartCreatorException {
		
		BinFileTemplate filePrep = null;
		
		if ( lType == LoaderTypes.PRG2CRT ) {
			filePrep = new VarABinTemplate();
			
			filePrep.setLoaderPayload(CartCreatorByteArrays.getLoaderVarA( cType ));
			filePrep.setPrgPayload(prg);
			((VarABinTemplate) filePrep).setPrgSize(Utils.convertIntToLittleEndian(prg.length - 2));
		}
		else if ( lType == LoaderTypes.HUCKY ) {
			filePrep = new VarBBinTemplate();
			
			filePrep.setLoaderPayload(CartCreatorByteArrays.getLoaderVarB( cType ));
			filePrep.setPrgPayload(prg);
			((VarBBinTemplate) filePrep).setSysAddress(Utils.convertIntToLittleEndian(
															Utils.getSysAddress(
																Arrays.copyOfRange(prg, 0, 15 ) ) ) );
			((VarBBinTemplate) filePrep).setPrgSize( Utils.convertIntToLittleEndian( prg.length + 2047 ) ); 
		}
		
		
		// if cart type is hucky switch banks
		if (cType == CartTypes.HUCKY) {
			return createHuckyBin(filePrep.getFinalBin());
		}
		else
			return filePrep.getFinalBin();
	}
	
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
	public byte[] createCRTFile(CartTypes cType, LoaderTypes lType, byte[] prg) throws CartCreatorException {
		
		BinFileTemplate filePrep = null;
		
		if ( lType == LoaderTypes.PRG2CRT ) {
			filePrep = new VarABinTemplate();
			
			filePrep.setLoaderPayload(CartCreatorByteArrays.getLoaderVarA( cType ));
			filePrep.setPrgPayload(prg);
			((VarABinTemplate) filePrep).setPrgSize(Utils.convertIntToLittleEndian(prg.length - 2));
		}
		else if ( lType == LoaderTypes.HUCKY ) {
			filePrep = new VarBBinTemplate();
			
			filePrep.setLoaderPayload(CartCreatorByteArrays.getLoaderVarB( cType ));
			filePrep.setPrgPayload(prg);
			((VarBBinTemplate) filePrep).setSysAddress(Utils.convertIntToLittleEndian(
															Utils.getSysAddress(
																Arrays.copyOfRange(prg, 0, 15 ) ) ) );
			((VarBBinTemplate) filePrep).setPrgSize( Utils.convertIntToLittleEndian( prg.length + 2047 ) ); 
		}
		
		return crtGenerator.makeCRT(filePrep.getCRTTemp(), cType);
	}
	
	
	/** Converts regular bin file to hucky style bin file,<br>
	 *  basically splits byte[] into 8 byte[] of 8192 bytes in length<br>
	 *  (banks) and then puts them back in reverese order. 
	 * @param bin 
	 * @return <b>byte[]</b>
	 */
	private byte[] createHuckyBin( byte[] bin ) {
		byte[][] huckyBanks = new byte[8][];
		
		int block = 8192;
		
		for (int x = 0; x < 8; x++) {
			huckyBanks[x] = Arrays.copyOfRange(	bin, 
				x * block, 
				((x+1) * block) > bin.length ? (bin.length) : ((x+1) * block) );
		}
		
		
		bin = Utils.concatenateByteArrays( huckyBanks[7],
													  huckyBanks[6], 
													  huckyBanks[5],
													  huckyBanks[4],
													  huckyBanks[3],
													  huckyBanks[2],
													  huckyBanks[1],
													  huckyBanks[0] );
		
		return bin;
	}
}
