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
	public void saveFile(byte[] data, File file) throws IOException{
		Path path = file.toPath();
		Files.write(path, data);
	}

}
