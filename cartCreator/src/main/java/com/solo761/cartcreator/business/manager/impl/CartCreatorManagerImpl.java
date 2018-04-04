package com.solo761.cartcreator.business.manager.impl;

import java.io.File;
import java.io.IOException;

import com.solo761.cartcreator.business.CRTgenerator.CRTGenerator;
import com.solo761.cartcreator.business.fsdao.FileDao;
import com.solo761.cartcreator.business.fsdao.impl.FileDaoImpl;
import com.solo761.cartcreator.business.manager.CartCreatorManager;
import com.solo761.cartcreator.business.model.BinFileTemplate;
import com.solo761.cartcreator.business.model.CartTypes;
import com.solo761.cartcreator.business.utils.CartCreatorByteArrays;

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
	
	@Override
	public byte[] calculatePrgSize(int prgSize) {
		byte[] size;
		
		// first two bytes are BASIC start address, they're not part of PRG
		// code, so they're not included in size. That's why we substract 2
		// from prgSize
		int effectiveSize = prgSize - 2;
		
		// bitwise "and" with 0xFF will leave only first two bytes
		// to get other two bytes we bitshift by 8 bits, effectively moving 
		// upper two bytes to lower position so bitwise "and" isn't exactly
		// needed, but better to be safe
		byte lower = (byte)(effectiveSize & 0xFF);
		byte higher = (byte)((effectiveSize >> 8) & 0xFF);
		
		// order bytes in little endian order and return this array
		size = new byte[] {lower, higher};
		
		return size;
	}
	
	public byte[] createBinFile(CartTypes type, byte[] prg) {
		
		BinFileTemplate filePrep = new BinFileTemplate();
		
		filePrep.setPrgPayload(prg);
		filePrep.setPrgSize(calculatePrgSize(filePrep.getPrgPayload().length));
		
		switch (type) {
		case HUCKY:
			
			break;
		case INVERTEDHUCKY:
			filePrep.setHeaderPayload(CartCreatorByteArrays.huckyPrg2Crt);
			break;
		case MAGICDESK:
			filePrep.setHeaderPayload(CartCreatorByteArrays.magicDeskPrg2Crt);
			break;
		case SIXTEENK:
			
			break;
		case EIGHTK:
			
			break;
		}

		return filePrep.getFinalBin();
	}
	
	public byte[] createCRTFile(CartTypes type, byte[] prg) {
		
		BinFileTemplate filePrep = new BinFileTemplate();
		
		filePrep.setPrgPayload(prg);
		filePrep.setPrgSize(calculatePrgSize(filePrep.getPrgPayload().length));
		
		switch (type) {
		case HUCKY:
			
			break;
		case INVERTEDHUCKY:
			filePrep.setHeaderPayload(CartCreatorByteArrays.huckyPrg2Crt);
			break;
		case MAGICDESK:
			filePrep.setHeaderPayload(CartCreatorByteArrays.magicDeskPrg2Crt);
			break;
		case SIXTEENK:
			
			break;
		case EIGHTK:
			
			break;
		}
		
		return crtGenerator.makeCRT(filePrep.getCRTTemp(), type);
	}
}
