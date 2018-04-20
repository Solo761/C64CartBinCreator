package com.solo761.cartcreator.business.utils;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
	
	
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
		String help = "C64 Cartridge Creator tool 1.0.0 by Solo761" + System.lineSeparator() + System.lineSeparator() +
				"Parameters:" + System.lineSeparator() + 
				"  -i <input file>\t\tprg file to convert" + System.lineSeparator() + 
				"  -t <cartidge type>\t\twhich cartridge type to convert to" + System.lineSeparator() +
				"\tCartridge types:" + System.lineSeparator() + 
				"\t  h\t\t\tHucky 64kB" + System.lineSeparator() + 
				"\t  ih\t\t\tinverted Hucky 64kB" + System.lineSeparator() + 
				"\t  md\t\t\tMagicDesk 64kB" + System.lineSeparator() + 
//				"\t  16\t\t\t16kB cartridge" + System.lineSeparator() + 
//				"\t  8\t\t\t8kB cartridge" + System.lineSeparator() + 
				" -l <loader type>\t\twhich loader to use" + System.lineSeparator() +
				"\tLoader types:" + System.lineSeparator() +
				"\t  a\t\t\tprg2crt loader from Frank Buß' python script" +  System.lineSeparator() +
				"\t  b\t\t\t152Blks loader" +  System.lineSeparator() +
				System.lineSeparator() + 
				"Semioptional (you need at least one of them):" + System.lineSeparator() + 
				"  -c\t\t\t\tconvert to emulation cartridge format (CRT)" + System.lineSeparator() +
				"  -b\t\t\t\tconvert to bin file for burning to (E)EPROM" + System.lineSeparator() +
				System.lineSeparator() +
				"Optional:" + System.lineSeparator() + 
				"  -o <output file>\t\toutput file to save converted file to, if it differs from input file" + System.lineSeparator() + 
				"  -h\t\t\t\tthis help" + System.lineSeparator() + 
				System.lineSeparator() + 
				"Example:" + System.lineSeparator() + 
				System.lineSeparator() + 
				"Convert prg to inverted hucky bin file using \"a\" variant loader" + System.lineSeparator() + 
				"  java -jar cartConv.jar -i BubbleBobble.prg -t ih -b -l a" + System.lineSeparator() + 
				System.lineSeparator() +
				"Convert prg to MagicDesk CRT file for use in emulator (e.g. Vice) and bin file using \"b\" variant loader" + System.lineSeparator() + 
				"  java -jar cartConv.jar -i BubbleBobble.prg -o BubbleBobbleEmu.crt -t md -l b -c -b" + System.lineSeparator() + 
				System.lineSeparator() +
				"FYI: Order of parameters is not important." + System.lineSeparator();
		
		System.out.println(help );
	}
	
	/**
	 * Converts int (up to 65536) to byte array with little endian ordering<br>
	 * ( { lower value, higher value } ) 
	 * @param value - integer
	 * @return <b>byte[]</b> - byte array with little endian formatted prg size
	 */
	public static byte[] convertIntToLittleEndian(int value) {
		byte[] littleEndianArr;
		
		// bitwise "and" with 0xFF will leave only first two bytes
		// to get other two bytes we bitshift by 8 bits, effectively moving 
		// upper two bytes to lower position so bitwise "and" isn't exactly
		// needed, but better to be safe
		byte lower = (byte)(value & 0xFF);
		byte higher = (byte)((value >> 8) & 0xFF);
		
		// order bytes in little endian order and return this array
		littleEndianArr = new byte[] {lower, higher};
		
		return littleEndianArr;
	}
	
	
	/**
	 * Looks for and returns first 4 digit sequence in passed byte array,<br>
	 * best way to use it is to send it first 16 - 32 bytes of loaded prg file. 
	 * @param data - byte array
	 * @return <b>int</b> - extracted 4 digit integer 
	 */
	public static int getSysAddress( byte[] data ) {
		
		Pattern pattern = Pattern.compile("(\\d{4})");
		Matcher matcher = pattern.matcher( new String( data ) );

		int sysAddress = 0;
		
		if (matcher.find()) {
			sysAddress = Integer.parseInt( matcher.group(1) ) ;
		}
		
		return sysAddress;
	}

}
