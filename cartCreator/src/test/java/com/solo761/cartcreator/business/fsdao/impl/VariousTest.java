package com.solo761.cartcreator.business.fsdao.impl;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.solo761.cartcreator.business.logic.JobListProcessor;
import com.solo761.cartcreator.business.manager.CartCreatorManager;
import com.solo761.cartcreator.business.manager.impl.CartCreatorManagerImpl;
import com.solo761.cartcreator.business.model.CartTypes;
import com.solo761.cartcreator.business.model.FilePath;
import com.solo761.cartcreator.business.model.JobList;
import com.solo761.cartcreator.business.model.LoaderTypes;
import com.solo761.cartcreator.business.utils.CartCreatorByteArrays;
import com.solo761.cartcreator.business.utils.CartCreatorUtils;

public class VariousTest {
	
	private CartCreatorManager cartCreatorManager = new CartCreatorManagerImpl();
	
	@Test
	public void helpTest() {
		CartCreatorUtils.printHelp();
	}
	
	@Test
	public void testPathRegexTest() {
		String pathRegex = "([a-zA-Z]:)?(\\\\[ a-zA-Z0-9._-]+)+\\\\?";
		//String fileString = "D:\\Electronics\\_Tools\\_Pickit3\\1.0.0\\PICkit31.0.0.0SetupA.exe";
		//String fileString = "D:\\Electronics\\_Tools\\_Pickit3\\1.0.0\\PICkit 3 1.0.0.0 Setup A.exe";
		String fileString = "D:\\Electronics\\_Tools\\_Picki t3\\1.0.0\\ICkit31.0.0.0SetupA.exe";
		
		//Pattern pattern = Pattern.compile(pathRegex);
		
		boolean isPath = Pattern.matches(pathRegex, fileString);
		
		System.out.println("Path entered is regular path: " + isPath);
	}
	
	@Test
	public void testPathNioTest() {
		//String fileString = "T:\\Electronics\\_Tools\\_Pickit3\\1.0.0\\PICkit31.0.0.0SetupA.exe";
		//String fileString = "D:\\Electronics\\_Tools\\_Pickit3\\1.0.0\\PICkit 3 1.0.0.0 Setup A.exe";
		//String fileString = "D:\\Electronics\\_Tools\\_Picki t3\\1.0.0\\ICkit31.0.0.0SetupA.exe";
		String fileString = "blas";
		
		try{
		    Paths.get(new File(fileString).toURI());
		}catch (InvalidPathException err){
			System.out.println("Wrong path");
		}
		System.out.println("OK path");
		
	}
	
	@Test
	public void testPathCustomTest() {
		//String fileString = "T:\\Electronics\\_Tools\\_Pickit3\\1.0.0\\PICkit31.0.0.0SetupA.exe";
		//String fileString = "D:\\Electronics\\_Tools\\_Pickit3\\1.0.0\\PICkit 3 1.0.0.0 Setup A.exe";
		//String fileString = "D:\\Electronics\\_Tools\\_Picki t3\\1.0.0\\ICkit31.0.0.0SetupA.exe";
		String fileString = "D:\\Electronics\\_Tools\\_Pickit3\\1.0.0\\";
		//String fileString = "blas";
		
	    File path = new File(fileString);
	    if (path.isDirectory())
	    	System.out.println("OK path");
	    else
			System.out.println("Wrong path");

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
			
			byte[] crt = cartCreatorManager.createCRTFile(CartTypes.INVERTEDHUCKY, LoaderTypes.HUCKY, content);
			
			cartCreatorManager.saveFile(crt, new File("d:\\test.crt") );
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void fileNameTest() {
		String fileString = "T:\\Electronics\\_Tools\\_Pickit3\\1.0.0\\PICkit31.0.0.0SetupA.exe";
		File filePath = new File (fileString);
		String fileName = filePath.getName().substring(0, filePath.getName().lastIndexOf("."));
		System.out.println(fileName);
		
		fileName = "";
		
		for (int x = 0; x < 10; x++) {
			fileName += ".";
			System.out.println(fileName + " = " + fileName.matches("(\\.)\\1{2,}"));
		}
	}
	
	@Test
	public void pathTest() {
		String path = ".\\bla.tst";
		File filePath = new File(path);
		System.out.println("String: " + path);
		System.out.println("Parent: " + filePath.getParent());
		System.out.println("Path: " + filePath.getPath());
		System.out.println("Name: " + filePath.getName());
	}
	
	@Test
	public void arrayTest() {
		System.out.println( "Hucky: " + String.format("%02X ", CartCreatorByteArrays.getLoaderVarA(CartTypes.HUCKY)[144 - 1]) );
		System.out.println( "MagicDesk: " + String.format("%02X ", CartCreatorByteArrays.getLoaderVarA(CartTypes.MAGICDESK)[144 - 1]) );
		System.out.println( "Hucky 152Blks: " + String.format("%02X ", CartCreatorByteArrays.getLoaderVarB(CartTypes.HUCKY)[182 - 1]) );
		System.out.println( "Hucky 152Blks: " + String.format("%02X ", CartCreatorByteArrays.getLoaderVarB(CartTypes.MAGICDESK)[182 - 1]) );
		
		System.out.println("sys adresa:");
		System.out.println( "Lower byte: " + String.format("%02X ", CartCreatorByteArrays.getLoaderVarB(CartTypes.MAGICDESK)[209]) );
		System.out.println( "Higher byte: " + String.format("%02X ", CartCreatorByteArrays.getLoaderVarB(CartTypes.MAGICDESK)[210]) );
		
		System.out.println("Veličina + 2047:");
		System.out.println( "Lower byte: " + String.format("%02X ", CartCreatorByteArrays.getLoaderVarB(CartTypes.MAGICDESK)[193]) );
		System.out.println( "Higher byte: " + String.format("%02X ", CartCreatorByteArrays.getLoaderVarB(CartTypes.MAGICDESK)[199]) );
	}
	
	@Test
	public void huckyCreateTest() {
		FilePath filePath = new FilePath();
		filePath.setInputFile("d:\\inst\\inst8\\wow.prg");
		filePath.setOutputFile("d:\\inst\\inst8\\wow");
		
		JobList jobList = new JobList();
		jobList.setCartType(CartTypes.INVERTEDHUCKY);
		jobList.setMakeBin(true);
		jobList.getFileList().add(filePath);
		
		JobListProcessor jobProcessor = new JobListProcessor();
		jobProcessor.processJobList(jobList);
		
	}
	
	@Test
	public void readSysAddress() throws IOException, URISyntaxException {
		//File file = new File(ClassLoader.getSystemResource("prg/Bubble Bobble.prg").toURI());
		File file = new File(ClassLoader.getSystemResource("prg/wow.prg").toURI());
		byte[] prg = cartCreatorManager.loadFile(file);
		String test = new String(Arrays.copyOfRange(prg, 0, 15));
		
		Pattern pattern = Pattern.compile("(\\d{4})");
		Matcher matcher = pattern.matcher(test);
		
		if (matcher.find()) {
			String extractedString = matcher.group(1);
			System.out.println( "SYS address String: " + extractedString );
			int convertedInt = Integer.parseInt( extractedString ) ;
			System.out.println( "SYS address Int: " + convertedInt );
			byte[] sysAddr = CartCreatorUtils.convertIntToLittleEndian( convertedInt );
			System.out.println( "SYS address bytes: " + String.format("%02X ", sysAddr[0] ) + ", " + String.format("%02X ", sysAddr[1] ) );
		}
		
		

	
	}
	
}
