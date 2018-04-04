package com.solo761.cartcreator.main;

import java.io.File;
import java.io.IOException;

import com.solo761.cartcreator.business.manager.CartCreatorManager;
import com.solo761.cartcreator.business.manager.impl.CartCreatorManagerImpl;
import com.solo761.cartcreator.business.model.BinFileTemplate;
import com.solo761.cartcreator.business.utils.CartCreatorByteArrays;

public class CommandLine {
	
	private static CartCreatorManager cartCreatorManager = new CartCreatorManagerImpl();
	
	public void commandLine( String args[] ) {
		
		File prgFile = new File( args[0] ); 
		
		if ( !prgFile.isFile() ) {
			System.out.println( "First parameter is not a file!" );
			return;
		}
		else if (!"prg".equals(prgFile.getName().substring(prgFile.getName().length() - 3, prgFile.getName().length()))) {
			System.out.println( "File is not prg file!" );
			return;
		}
		
		BinFileTemplate filePrep = new BinFileTemplate();
		
		filePrep.setHeaderPayload(CartCreatorByteArrays.huckyPrg2Crt);
		
		try {
			filePrep.setPrgPayload(cartCreatorManager.loadFile(prgFile));
		} catch (IOException e) {
			System.out.println("Error reading file: " + prgFile.getName());
			e.printStackTrace();
		}
		
		filePrep.setPrgSize(cartCreatorManager.calculatePrgSize(filePrep.getPrgPayload().length));
		
		// TODO dodati/smisliti prepoznavanje da li je args[1] ispravna putanja ili ne
		String fileName = "";
		
		if (args.length > 1 && args[1] != null && args[1].length() > 0)
			fileName = args[1];
		else
			fileName = prgFile.getName().substring(0, prgFile.getName().length() - 4);
		
		File outFile = new File((prgFile.getParent() != null ? prgFile.getParent() + "\\" : "")  + fileName + ".bin");

		try {
			cartCreatorManager.saveFile(filePrep.getFinalBin(), outFile);
		} catch (IOException e) {
			System.out.println("Error writing file: " + prgFile.getName());
			e.printStackTrace();
		}
		
	}

}
