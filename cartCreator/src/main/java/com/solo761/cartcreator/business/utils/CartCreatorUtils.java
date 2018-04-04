package com.solo761.cartcreator.business.utils;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

import javax.swing.ButtonGroup;

public class CartCreatorUtils {
	
	
	/**
	 * Method test input string if it conforms to<br>
	 * rules for file and folder naming on operating<br>
	 * system it's run on. Should work on Windows and *nix<br>
	 * based OS's.
	 * @param path - String to be tested
	 * @return <b>boolean</b>
	 */
	public static boolean pathTest(String path) {
		try{
		    Paths.get(new File(path).toURI());
		}catch (InvalidPathException err){
			return false;
		}
		return true;
	}
	
//	public static byte[] concatenateByteArrays(byte[] firstArray, byte[] secondArray) {
//		ByteBuffer b = ByteBuffer.allocate(firstArray.length + secondArray.length);
//		b.put(firstArray);
//		b.put(secondArray);
//		return b.array();
//	}
	
	public static byte[] concatenateByteArrays(byte[] ... arrays ) {
		int size = 0;
		
		for (byte[] array : arrays) {
			size += array.length; 
		}
		
		ByteBuffer b = ByteBuffer.allocate(size);
		
		for (byte[] array : arrays) {
			b.put(array);
		}
		
		return b.array();
	}
	
	public static void printHelp() {
		String help = "Parameters:\r\n" + 
				"  -i <input file>		prg file to convert\r\n" + 
				"  -t <cartidge type>		to which cartridge type to convert\r\n" + 
				"\r\n" + 
				"Optional:\r\n" + 
				"  -o <output file>		output file to save converted file to, if it differs from input file\r\n" + 
				"  -h				this help\r\n" + 
				"  -c				convert to emulation cartridge format (CRT)\r\n" + 
				"\r\n" + 
				"Cartridge types:\r\n" + 
//				"  h				Hucky 64kB\r\n" + 
				"  ih				inverted Hucky 64kB\r\n" + 
				"  md				MagicDesk 64kB\r\n" + 
//				"  16				16kB cartridge\r\n" + 
//				"  8				8kB cartridge\r\n" + 
				"\r\n" + 
				"Example:\r\n" + 
				"\r\n" + 
				"Convert prg to inverted hucky bin file\r\n" + 
				"  java -jar cartConv.jar -i BubbleBobble.prg -t ih\r\n" + 
				"\r\n" + 
				"Convert prg to MagicDesk CRT file for use in emulator (e.g. Vice)\r\n" + 
				"  java -jar cartConv.jar -i BubbleBobble.prg -o BubbleBobbleEmu.crt -t md";
		
		System.out.println(help);
	}
}
