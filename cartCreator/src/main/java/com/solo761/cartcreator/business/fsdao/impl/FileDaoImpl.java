package com.solo761.cartcreator.business.fsdao.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.solo761.cartcreator.business.fsdao.FileDao;

public class FileDaoImpl implements FileDao {

	@Override
	public byte[] loadFile(File file) throws IOException {
		Path path = file.toPath();
		byte[] content = Files.readAllBytes(path);
		return content;
	}
	
	@Override
	public boolean saveFile(byte[] data, File file) throws IOException{
		Path path = file.toPath();
		Path result = Files.write(path, data);
		
		if (!result.equals(path))
			return false;
		return true;
	}

}
