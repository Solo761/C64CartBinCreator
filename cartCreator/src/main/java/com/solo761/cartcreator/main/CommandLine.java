package com.solo761.cartcreator.main;

import java.io.File;
import java.io.IOException;

import com.solo761.cartcreator.business.manager.CartCreatorManager;
import com.solo761.cartcreator.business.manager.impl.CartCreatorManagerImpl;
import com.solo761.cartcreator.business.model.Arguments;

public class CommandLine {
	
	private static CartCreatorManager cartCreatorManager = new CartCreatorManagerImpl();
	
	public void commandLine( Arguments arguments ) {
		
		File prgFile = new File( arguments.getInputFile() ); 
		
		if ( !prgFile.isFile() ) {
			System.out.println( "First parameter is not a file!" );
			return;
		}
		else if (!"prg".equals(prgFile.getName().substring(prgFile.getName().length() - 3, prgFile.getName().length()))) {
			System.out.println( "File is not a prg file!" );
			return;
		}
		
		byte[] prg = null; 
		
		try {
			prg = cartCreatorManager.loadFile(prgFile);
		} catch (IOException e) {
			System.out.println("Error reading file: " + prgFile.getName());
			System.out.println( e.getMessage() );
		}
		
		// file size check, works, but disabled for now, I have to make it a bit better
//		if ( (arguments.getCartType() == CartTypes.INVERTEDHUCKY || 
//				arguments.getCartType() == CartTypes.MAGICDESK) && 
//			 (prg.length + 156) > 65536 ) {
//			System.out.println("Resulting file would be bigger than 64kB");
//			return;
//		}
		
		String fileName = "";
		String outPath = "";
		String[] extension = { "", "" };
		
		// TODO path management is a bit of a mess, but it works (at least combinations 
		// that came to my mind) so I'm leaving it as it is for the moment 
		
		// if there's output file argument use that as file path / name, if extension is 
		// wrong that's on the user's head, but if he didn't use (no dot in file name) extension 
		// one will be added to file name
		// else save file in same path as input file and with same name plus new extension
		if ( arguments.getOutputFile() != null ) {
			File outputFilePath = new File(arguments.getOutputFile());
			fileName = outputFilePath.getName();
			
			
			// if filename is blank that means whole output file path is output path
			// else output path is parent of whole path
			if ( "".equals(fileName.trim()) )
				outPath = outputFilePath.getPath();
			else
				outPath = outputFilePath.getParent() != null ? outputFilePath.getParent() + File.separator : "";
			
			// if file name is just one dot or 3+ dots just use input file name
			if ( ".".equals(fileName) || fileName.matches("(\\.)\\1{2,}") ) {
				fileName = prgFile.getName().substring( 0, prgFile.getName().length() - 4 );
			}
			// else if file name is two dots (previous folder) set output path to one folder up
			else if ( "..".equals(fileName ) ){
				fileName = prgFile.getName().substring( 0, prgFile.getName().length() - 4 );
				outPath = outPath + ".." + File.separator;
			}
			// else if file name is blank use whole path from output parameter
			else if ( "".equals(fileName.trim()) ){
				fileName = prgFile.getName().substring( 0, prgFile.getName().length() - 4 );
				outPath = outPath.endsWith("\\") ? outPath : outPath + File.separator;
			}
			
			if (!fileName.contains("."))
				extension = new String[] { ".crt", ".bin" };
		}
		else {
			extension = new String[] { ".crt", ".bin" };
			outPath = prgFile.getParent() != null ? prgFile.getParent() + File.separator : "";
			fileName = prgFile.getName().substring(0, prgFile.getName().length() - 4);
		}
		
//		System.out.println("***************************");
//		System.out.println("outPath: " + outPath);
//		System.out.println("fileName: " + fileName);
//		System.out.println("extension: " + extension[0] + ", " + extension[1]);
//		System.out.println("***************************");

		try {
			if ( arguments.isMakeCRT() ) {
				File outFile = new File( outPath + fileName + extension[0]);
				cartCreatorManager.saveFile(cartCreatorManager.createCRTFile(arguments.getCartType(), prg), outFile);
				System.out.println("Created CRT file: " + outPath + fileName + extension[0]);
			}
			if ( arguments.isMakeBin() ) {
				File outFile = new File( outPath + fileName + extension[1] );
				cartCreatorManager.saveFile(cartCreatorManager.createBinFile(arguments.getCartType(), prg), outFile);
				System.out.println("Created bin file: " + outPath + fileName + extension[1]);
			}
		} catch (IOException e) {
			System.out.println("Error writing file: " + prgFile.getName());
			e.printStackTrace();
		}
		
	}

}
