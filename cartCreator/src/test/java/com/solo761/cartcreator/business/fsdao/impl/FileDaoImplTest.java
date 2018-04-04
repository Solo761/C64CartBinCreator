package com.solo761.cartcreator.business.fsdao.impl;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import org.junit.Test;

import com.solo761.cartcreator.business.fsdao.FileDao;

public class FileDaoImplTest{
	
	FileDao fileDao = new FileDaoImpl();

	@Test
	public void loadFileTest() throws URISyntaxException {
		File file = new File(ClassLoader.getSystemResource("prg/Bubble Bobble.prg").toURI());
		//File file = new File("D:\\_Coding\\_Workspace\\CartBinCreator\\git\\cartCreator\\src\\test\\resources\\prg\\Bubble Bobble.prg"); 
		try {
			byte[] content = fileDao.loadFile(file);
			System.out.println(content.length);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
