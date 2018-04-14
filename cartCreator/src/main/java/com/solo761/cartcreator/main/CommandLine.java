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
		

		try {
			if ( arguments.isMakeCRT() ) {
				File outFile = new File( arguments.getOutputFile() + arguments.getCrtExtension() );
				cartCreatorManager.saveFile(cartCreatorManager.createCRTFile(arguments.getCartType(), prg), outFile);
				System.out.println("Created CRT file: " + outFile.getAbsolutePath() );
			}
			if ( arguments.isMakeBin() ) {
				File outFile = new File( arguments.getOutputFile() + arguments.getBinExtension() );
				cartCreatorManager.saveFile(cartCreatorManager.createBinFile(arguments.getCartType(), prg), outFile);
				System.out.println("Created bin file: " + outFile.getAbsolutePath() );
			}
		} catch (IOException e) {
			System.out.println("Error writing file: " + prgFile.getName());
			e.printStackTrace();
		}
		
	}

}
