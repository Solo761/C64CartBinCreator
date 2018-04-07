package com.solo761.cartcreator.business.utils;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

public class CartCreatorUtils {
	
	
	/**
	 * Test input string if it conforms to<br>
	 * rules for file and folder naming on operating<br>
	 * system it's run on. Should work on Windows and *nix<br>
	 * based OS's.<br>
	 * Returns <b>true</b> if path is OK
	 * @param path - String to be tested
	 * @return <b>boolean</b>
	 */
	public static boolean pathTest( String path ) {
		try{
		    Paths.get( new File( path ).toURI() );
		}catch ( InvalidPathException err ){
			return false;
		}
		return true;
	}
	
	/**
	 * Tests string to see if it's existing file on the file system<br>
	 * Returns <b>true</b> if file path is OK
	 * @param filePath - String to be tested
	 * @return <b>boolean</b>
	 */
	public static boolean isFile( String filePath ) {
		File file = new File( filePath );
		
		if ( !file.isFile() )
			return false;

		return true;
	}
	
	/**
	 * Takes "n" byte arrays and returns them concatenated  
	 * @param arrays - arrays to be concatenated
	 * @return <b>byte[]</b> - concatenated byte array
	 */
	public static byte[] concatenateByteArrays( byte[] ... arrays ) {
		int size = 0;
		
		for ( byte[] array : arrays ) {
			size += array.length; 
		}
		
		ByteBuffer b = ByteBuffer.allocate( size );
		
		for ( byte[] array : arrays ) {
			b.put( array );
		}
		
		return b.array();
	}
	
	/**
	 * <i>sysouts</i> help for command line usage
	 */
	public static void printHelp() {
		String help = "Parameters:\r\n" + 
				"  -i <input file>		prg file to convert\r\n" + 
				"  -t <cartidge type>		to which cartridge type to convert\r\n" +
				"\tCartridge types:\r\n" + 
//				"\t  h				Hucky 64kB\r\n" + 
				"\t  ih				inverted Hucky 64kB\r\n" + 
				"\t  md				MagicDesk 64kB\r\n" + 
//				"\t  16				16kB cartridge\r\n" + 
//				"\t  8				8kB cartridge\r\n" + 
				"\r\n" + 
				"Semioptional (you need at least one of them):\r\n" + 
				"  -c				convert to emulation cartridge format (CRT)\r\n" +
				"  -b				convert to bin file for burning to (E)EPROM\r\n" +
				"\r\n" + 
				"Optional:\r\n" + 
				"  -o <output file>		output file to save converted file to, if it differs from input file\r\n" + 
				"  -h				this help\r\n" + 
				"\r\n" + 
				"Example:\r\n" + 
				"\r\n" + 
				"Convert prg to inverted hucky bin file\r\n" + 
				"  java -jar cartConv.jar -i BubbleBobble.prg -t ih -b\r\n" + 
				"\r\n" + 
				"Convert prg to MagicDesk CRT file for use in emulator (e.g. Vice) and bin file\r\n" + 
				"  java -jar cartConv.jar -i BubbleBobble.prg -o BubbleBobbleEmu.crt -t md -c -b\r\n" + 
				"\r\n" +
				"FYI: Order of parameters is not important.\r\n";
		
		System.out.println( System.lineSeparator() + help );
	}
}
