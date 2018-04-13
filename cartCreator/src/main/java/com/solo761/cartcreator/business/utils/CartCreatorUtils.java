package com.solo761.cartcreator.business.utils;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

public class CartCreatorUtils {
	
	
	/**
	 * *** Turned out not to be good enough as it returns true for strings like "3432jklh32"<br>
	 * *** which is technically ok, it can be folder name, but not usefull for path validation<br>
	 * <br>
	 * Test input string if it conforms to<br>
	 * rules for file and folder naming on operating<br>
	 * system it's run on. Should work on Windows and *nix<br>
	 * based OS's.<br>
	 * <br>
	 * Returns <b>true</b> if path is OK
	 * @param path - String to be tested
	 * @return <b>boolean</b>
	 */
	public static boolean isPathNio( String path ) {
		try{
		    Paths.get( new File( path ).toURI() );
		}catch ( InvalidPathException err ){
			return false;
		}
		return true;
	}
	
	
	/**
	 * Tests if string path is existing folder on the file system<br>
	 * <br>
	 * Returns <b>true</b> if it's existing folder
	 * @param path - String to be tested
	 * @return <b>boolean</b>
	 */
	public static boolean isPath( String path ) {
		File testPath = new File( path );
		
		if (testPath.isDirectory())
			return true;
		return false;
	}
	
	
	/**
	 * Tries to create folder from path in string, returns true if successful<br>
	 * @param path - String to be created as folder
	 * @return <b>boolean</b>
	 */
	public static boolean makeDir( String path ) {
		File testPath = new File( path );
		
		return testPath.mkdir();
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
