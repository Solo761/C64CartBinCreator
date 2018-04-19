package com.solo761.cartcreator.business.manager.impl;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import com.solo761.cartcreator.business.fsdao.FileDao;
import com.solo761.cartcreator.business.fsdao.impl.FileDaoImpl;
import com.solo761.cartcreator.business.logic.CRTGenerator;
import com.solo761.cartcreator.business.manager.CartCreatorManager;
import com.solo761.cartcreator.business.model.BinFileTemplate;
import com.solo761.cartcreator.business.model.CartTypes;
import com.solo761.cartcreator.business.model.LoaderTypes;
import com.solo761.cartcreator.business.model.VarABinTemplate;
import com.solo761.cartcreator.business.model.VarBBinTemplate;
import com.solo761.cartcreator.business.utils.CartCreatorByteArrays;
import com.solo761.cartcreator.business.utils.CartCreatorUtils;

public class CartCreatorManagerImpl implements CartCreatorManager {
	
	private FileDao fileDao = new FileDaoImpl();
	private CRTGenerator crtGenerator = new CRTGenerator(); 

	@Override
	public byte[] loadFile(File file) throws IOException {
		byte[] fileContent = fileDao.loadFile(file);
		return fileContent;
	}

	@Override
	public void saveFile(byte[] data, File file) throws IOException {
		fileDao.saveFile(data, file);
	}
	
	public byte[] createBinFile(CartTypes cType, LoaderTypes lType, byte[] prg) {
		
		BinFileTemplate filePrep = null;
		
		if ( lType == LoaderTypes.PRG2CRT ) {
			filePrep = new VarABinTemplate();
			
			filePrep.setLoaderPayload(CartCreatorByteArrays.getLoaderVarA( cType ));
			filePrep.setPrgPayload(prg);
			((VarABinTemplate) filePrep).setPrgSize(CartCreatorUtils.convertIntToLittleEndian(prg.length - 2));
		}
		else if ( lType == LoaderTypes.HUCKY ) {
			filePrep = new VarBBinTemplate();
			
			filePrep.setLoaderPayload(CartCreatorByteArrays.getLoaderVarB( cType ));
			filePrep.setPrgPayload(prg);
			((VarBBinTemplate) filePrep).setSysAddress(CartCreatorUtils.convertIntToLittleEndian(
															CartCreatorUtils.getSysAddress(
																Arrays.copyOfRange(prg, 0, 15 ) ) ) );
			((VarBBinTemplate) filePrep).setPrgSize( CartCreatorUtils.convertIntToLittleEndian( prg.length + 2047 ) ); 
		}
		
		
		// if cart type is hucky switch banks
		if (cType == CartTypes.HUCKY) {
			return createHuckyBin(filePrep.getFinalBin());
		}
		else
			return filePrep.getFinalBin();
	}
	
	public byte[] createCRTFile(CartTypes cType, LoaderTypes lType, byte[] prg) {
		
		BinFileTemplate filePrep = null;
		
		if ( lType == LoaderTypes.PRG2CRT ) {
			filePrep = new VarABinTemplate();
			
			filePrep.setLoaderPayload(CartCreatorByteArrays.getLoaderVarA( cType ));
			filePrep.setPrgPayload(prg);
			((VarABinTemplate) filePrep).setPrgSize(CartCreatorUtils.convertIntToLittleEndian(prg.length - 2));
		}
		else if ( lType == LoaderTypes.HUCKY ) {
			filePrep = new VarBBinTemplate();
			
			filePrep.setLoaderPayload(CartCreatorByteArrays.getLoaderVarB( cType ));
			filePrep.setPrgPayload(prg);
			((VarBBinTemplate) filePrep).setSysAddress(CartCreatorUtils.convertIntToLittleEndian(
															CartCreatorUtils.getSysAddress(
																Arrays.copyOfRange(prg, 0, 15 ) ) ) );
			((VarBBinTemplate) filePrep).setPrgSize( CartCreatorUtils.convertIntToLittleEndian( prg.length + 2047 ) ); 
		}
		
		return crtGenerator.makeCRT(filePrep.getCRTTemp(), cType);
	}
	
	private byte[] createHuckyBin( byte[] bin ) {
		byte[][] huckyBanks = new byte[8][];
		
		int block = 8192;
		
		for (int x = 0; x < 8; x++) {
			huckyBanks[x] = Arrays.copyOfRange(	bin, 
				x * block, 
				((x+1) * block) > bin.length ? (bin.length) : ((x+1) * block) );
		}
		
		
		bin = CartCreatorUtils.concatenateByteArrays( huckyBanks[7],
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
