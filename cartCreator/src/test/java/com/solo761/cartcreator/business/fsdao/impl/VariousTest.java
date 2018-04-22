package com.solo761.cartcreator.business.fsdao.impl;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.solo761.cartcreator.business.logic.CmdPrepareJobList;
import com.solo761.cartcreator.business.logic.JobListProcessor;
import com.solo761.cartcreator.business.manager.Manager;
import com.solo761.cartcreator.business.model.CartTypes;
import com.solo761.cartcreator.business.model.JobList;
import com.solo761.cartcreator.business.model.LoaderTypes;
import com.solo761.cartcreator.business.utils.Utils;

public class VariousTest {
	
	private Manager cartCreatorManager = new Manager();
	private CmdPrepareJobList cmdPrepareJobList = new CmdPrepareJobList();
	private JobListProcessor jobListProcessor = new JobListProcessor();
	
	@Test
	public void helpTest() {
		Utils.printHelp();
	}
	
	@Test
	public void testBytes() {
		int effectiveSize = 45623;
		
		byte lower = (byte)(effectiveSize & 0xFF);
		byte higher = (byte)((effectiveSize >> 8) & 0xFF);
		
		System.out.println("Lower: " + String.format("%02X ", lower) + ", higher: " + String.format("%02X ",higher));
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
	public void argsTest() throws URISyntaxException {
		String[] args = {"-i", new File(ClassLoader.getSystemResource("prg/Bubble Bobble.prg").toURI()).getAbsolutePath(), "-t",  "ih", "-b", "-l", "a" };
		JobList jobList = cmdPrepareJobList.prepareJobList(args);
		System.out.println("Input file: " + jobList.getFileList().get(0).getInputFile());
		System.out.println("Output file: " + jobList.getFileList().get(0).getOutputFile());
		System.out.println("Cart type: " + jobList.getCartType());
		System.out.println("Loader type: " + jobList.getLoaderType());
		System.out.println("Make BIN: " + jobList.isMakeBin());
		System.out.println("Make CRT: " + jobList.isMakeCRT());
		System.out.println("Help?: " + jobList.isHelp());
		System.out.println("Errors: " + jobList.getErrors());
	}
	
	@Test
	public void processArgsJob() throws URISyntaxException {
		String[] args = {"-i", new File(ClassLoader.getSystemResource("prg/Bubble Bobble.prg").toURI()).getAbsolutePath(), "-t",  "ih", "-b", "-c", "-l", "a" };
		JobList jobList = cmdPrepareJobList.prepareJobList(args);
		System.out.println("Input file: " + jobList.getFileList().get(0).getInputFile());
		System.out.println("Output file: " + jobList.getFileList().get(0).getOutputFile());
		System.out.println("Cart type: " + jobList.getCartType());
		System.out.println("Loader type: " + jobList.getLoaderType());
		System.out.println("Make BIN: " + jobList.isMakeBin());
		System.out.println("Make CRT: " + jobList.isMakeCRT());
		System.out.println("Help?: " + jobList.isHelp());
		if ( jobList.getErrors() != null && !"".equals(jobList.getErrors()) )
			System.out.println("Errors: " + jobList.getErrors());
		
		System.out.println();
		
		jobListProcessor.processJobList(jobList);
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
			byte[] sysAddr = Utils.convertIntToLittleEndian( convertedInt );
			System.out.println( "SYS address bytes: " + String.format("%02X ", sysAddr[0] ) + ", " + String.format("%02X ", sysAddr[1] ) );
		}

	}
	
}
