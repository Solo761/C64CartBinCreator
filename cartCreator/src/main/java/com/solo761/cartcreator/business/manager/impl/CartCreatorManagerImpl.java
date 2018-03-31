package com.solo761.cartcreator.business.manager.impl;

import java.io.File;
import java.io.IOException;

import com.solo761.cartcreator.business.fsdao.FileDao;
import com.solo761.cartcreator.business.fsdao.impl.FileDaoImpl;
import com.solo761.cartcreator.business.manager.CartCreatorManager;

public class CartCreatorManagerImpl implements CartCreatorManager {
	
	private static FileDao fileDao = new FileDaoImpl();

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
		
		int effectiveSize = prgSize - 2;
		
		byte lower = (byte)(effectiveSize & 0xFF);
		byte higher = (byte)((effectiveSize >> 8) & 0xFF);
		
		size = new byte[] {lower, higher};
		
		return size;
	}
}
