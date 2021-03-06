package com.solo761.cartcreator.business.logic;

import java.nio.ByteBuffer;
import java.util.Arrays;

import com.solo761.cartcreator.business.model.CartTypes;
import com.solo761.cartcreator.business.utils.Utils;

public class CRTGenerator {
	
	// static arrays that are used to make CRT headers and segments
	
	private static final byte[] crtSignature = {
			(byte)0x43, (byte)0x36, (byte)0x34, (byte)0x20, (byte)0x43, (byte)0x41,
			(byte)0x52, (byte)0x54, (byte)0x52, (byte)0x49, (byte)0x44, (byte)0x47,
			(byte)0x45, (byte)0x20, (byte)0x20, (byte)0x20
		};
	private static final byte[] headerLength = { (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x40 };
	private static final byte[] cartridgeVersion = { (byte)0x01, (byte)0x00 };
	private static final byte[] magicDeskSignature = { (byte)0x00, (byte)0x13, };
	private static final byte[] rgcdSignature = { (byte)0x00, (byte)0x39, };			// inverted hucky
	private static final byte[] normalCartridge = { (byte)0x00, (byte)0x00, };
	private static byte[] cartType;
	private static byte[] exrom;
	private static byte[] game;
	private static final byte[] reserved = { 
			(byte)0x00,  (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 
		};
	private static final byte[] cartName = { 
			(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
			(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
			(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
			(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00
	};
	
	
	private static final byte[] chipString = { (byte)0x43, (byte)0x48, (byte)0x49, (byte)0x50 };
	private static final byte[] chipPacketLength = { (byte)0x00, (byte)0x00, (byte)0x20, (byte)0x10 };
	private static final byte[] chipType = { (byte)0x00, (byte)0x00 };
	private static byte[] chipBankNumber = { (byte)0x00, (byte)0x00 }; // + bank number byte 
	private static final byte[] chipLoadAddress = { (byte)0x80, (byte)0x00 };
	private static final byte[] chipRomImageSize = { (byte)0x20, (byte)0x00 };
	
	private static final int block = 8192;
	
	
	/**
	 * This method splits bin file in 8k banks and inserts header and lines required by CRT "rules"
	 * @param trimmedBin	- prepared bin file, but without filler zero bytes (although I suspect it would work the same with them)
	 * @param type			- CartType enum 
	 * @return <b>byte[]</b>	- prepared CRT file
	 */
	public byte[] makeCRT( byte[] trimmedBin, CartTypes type ) {
		int banks = (trimmedBin.length / 0x2000) + 1 ;
		
		switch (type) {
		case HUCKY:
			exrom = new byte[] { (byte)0x00 };
			game = new byte[] { (byte)0x01 };
			cartType = rgcdSignature.clone();
			break;
		case INVERTEDHUCKY:
			exrom = new byte[] { (byte)0x00 };
			game = new byte[] { (byte)0x01 };
			cartType = rgcdSignature.clone();
			break;
		case MAGICDESK:
			exrom = new byte[] { (byte)0x00 };
			game = new byte[] { (byte)0x01 };
			cartType = magicDeskSignature.clone();
			break;
		case SIXTEENK:
			cartType = normalCartridge.clone();
			break;
		case EIGHTK:
			cartType = normalCartridge.clone();
			break;
		default:
			cartType = new byte[] { (byte)0xFF, (byte)0xFF };
			break;
		}
		
		byte[] crtHeader = Utils.concatenateByteArrays(	crtSignature, 
																	headerLength, 
																	cartridgeVersion, 
																	cartType,
																	exrom,
																	game,
																	reserved,
																	cartName);
		
		ByteBuffer b = ByteBuffer.allocate( crtHeader.length + 
											block * banks + 
											16 * banks ); // size of header + number of full blocks + number of CHIP headers  

		b.put(crtHeader);
		
		for (int x = 0; x < banks; x++  ) {
			chipBankNumber[1] = (byte)x;
			b.put(Utils.concatenateByteArrays(	chipString, 
															chipPacketLength, 
															chipType, 
															chipBankNumber,
															chipLoadAddress,
															chipRomImageSize ));
			b.put(Arrays.copyOfRange(	trimmedBin, 
										x * block, 
										((x+1) * block) > trimmedBin.length ? (trimmedBin.length) : ((x+1) * block) ));
		}
		
		return b.array();
	}
}
