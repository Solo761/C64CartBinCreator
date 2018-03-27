package com.solo761.cartcreator.main;

import java.io.File;
import java.io.IOException;

import com.solo761.cartcreator.business.fsdao.FileDao;
import com.solo761.cartcreator.business.fsdao.impl.FileDaoImpl;
import com.solo761.cartcreator.business.model.BinFileTemplate;
import com.solo761.cartcreator.business.utils.Constants;

public class Main {
	
	//private static final Logger	LOGGER	= LoggerFactory.getLogger( Main.class );
	private static FileDao fileDao = new FileDaoImpl();

	public static void main( String[] args ) {
		
		if ( args.length == 0 ) {
			System.out.println( "You need to enter at least input file" );
			System.out.println( "path for this to work." );
			return;
		}
		
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
		
		filePrep.setHeaderPayload(Constants.huckyPrg2Crt);
		
		try {
			filePrep.setPrgPayload(fileDao.loadFile(prgFile));
		} catch (IOException e) {
			System.out.println("Error reading file: " + prgFile.getName());
			e.printStackTrace();
		}
		
		int size = filePrep.getPrgPayload().length - 2;
		
		byte lower = (byte)(size & 0xFF);
		byte higher = (byte)((size >> 8) & 0xFF);
		
		filePrep.setPrgSize(new byte[] {lower, higher});
		
		File outFile = new File(prgFile.getParent() + "\\" + prgFile.getName().substring(0, prgFile.getName().length() - 4) + ".bin");

		try {
			fileDao.saveFile(filePrep.getFinalBin(), outFile);
		} catch (IOException e) {
			System.out.println("Error writing file: " + prgFile.getName());
			e.printStackTrace();
		}
		
	}

}
