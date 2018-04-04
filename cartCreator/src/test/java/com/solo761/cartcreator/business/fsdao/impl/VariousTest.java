package com.solo761.cartcreator.business.fsdao.impl;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import org.junit.Test;

import com.solo761.cartcreator.business.manager.CartCreatorManager;
import com.solo761.cartcreator.business.manager.impl.CartCreatorManagerImpl;
import com.solo761.cartcreator.business.model.CartTypes;

public class VariousTest {
	
	private CartCreatorManager cartCreatorManager = new CartCreatorManagerImpl();
	
	@Test
	public void testPathRegexTest() {
		String pathRegex = "([a-zA-Z]:)?(\\\\[a-zA-Z0-9._-]+)+\\\\?";
		//String fileString = "D:\\Electronics\\_Tools\\_Pickit3\\1.0.0\\PICkit31.0.0.0SetupA.exe";
		//String fileString = "D:\\Electronics\\_Tools\\_Pickit3\\1.0.0\\PICkit 3 1.0.0.0 Setup A.exe";
		String fileString = "D:\\Electronics\\_Tools\\_Picki t3\\1.0.0\\ICkit31.0.0.0SetupA.exe";
		
		//Pattern pattern = Pattern.compile(pathRegex);
		
		boolean isPath = Pattern.matches(pathRegex, fileString);
		
		System.out.println("Path entered is regular path: " + isPath);
	}
	
	@Test
	public void testPathNioTest() {
		String fileString = "T:\\Electronics\\_Tools\\_Pickit3\\1.0.0\\PICkit31.0.0.0SetupA.exe";
		//String fileString = "D:\\Electronics\\_Tools\\_Pickit3\\1.0.0\\PICkit 3 1.0.0.0 Setup A.exe";
		//String fileString = "D:\\Electronics\\_Tools\\_Picki t3\\1.0.0\\ICkit31.0.0.0SetupA.exe";
		
		try{
		    Paths.get(new File(fileString).toURI());
		}catch (InvalidPathException err){
			System.out.println("Wrong path");
		}
		System.out.println("OK path");
		
	}
	
	@Test
	public void testBytes() {
		int effectiveSize = 45623;
		
		byte lower = (byte)(effectiveSize & 0xFF);
		byte higher = (byte)((effectiveSize >> 8) & 0xFF);
		
		System.out.println("Lower: " + Integer.toHexString(lower) + ", higher: " + Integer.toHexString(higher));
	}
	
	@Test
	public void crtGenTest() {
		try {
			File file = new File(ClassLoader.getSystemResource("prg/Bubble Bobble.prg").toURI());
			//File file = new File("D:\\_Coding\\_Workspace\\CartBinCreator\\git\\cartCreator\\src\\test\\resources\\prg\\Bubble Bobble.prg");
			byte[] content = cartCreatorManager.loadFile(file);
			
			byte[] crt = cartCreatorManager.createCRTFile(CartTypes.INVERTEDHUCKY, content);
			
			cartCreatorManager.saveFile(crt, new File("d:\\test.crt") );
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
