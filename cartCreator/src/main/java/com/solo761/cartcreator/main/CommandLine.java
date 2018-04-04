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
			System.out.println( "File is not prg file!" );
			return;
		}
		
		byte[] prg = null; 
		
		try {
			prg = cartCreatorManager.loadFile(prgFile);
		} catch (IOException e) {
			System.out.println("Error reading file: " + prgFile.getName());
			System.out.println( e.getMessage() );
		}
		
		// TODO riješiti hendlanje extenzije
		
		String fileName = "";
		
		if ( arguments.getOutputFile() != null )
			fileName = arguments.getOutputFile();
		else
			fileName = prgFile.getName().substring(0, prgFile.getName().length() - 4);
		
		try {
			if (arguments.isMakeCRT()) {
				File outFile = new File((prgFile.getParent() != null ? prgFile.getParent() + "\\" : "")  + fileName + ".crt");
				cartCreatorManager.saveFile(cartCreatorManager.createCRTFile(arguments.getCartType(), prg), outFile);
			}
			else {
				File outFile = new File((prgFile.getParent() != null ? prgFile.getParent() + "\\" : "")  + fileName + ".bin");
				cartCreatorManager.saveFile(cartCreatorManager.createBinFile(arguments.getCartType(), prg), outFile);
			}
		} catch (IOException e) {
			System.out.println("Error writing file: " + prgFile.getName());
			e.printStackTrace();
		}
		
	}

}
