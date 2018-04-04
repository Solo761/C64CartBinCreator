package com.solo761.cartcreator.business.CRTgenerator;

import java.nio.ByteBuffer;
import java.util.Arrays;

import com.solo761.cartcreator.business.utils.CartCreatorUtils;

public class CRTGenerator {
	
	private static final byte[] crtSignature = {
			(byte)0x43, (byte)0x36, (byte)0x34, (byte)0x20, (byte)0x43, (byte)0x41,
			(byte)0x52, (byte)0x54, (byte)0x52, (byte)0x49, (byte)0x44, (byte)0x47,
			(byte)0x45, (byte)0x20, (byte)0x20, (byte)0x20
		};
	private static final byte[] headerLength = { (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x40 };
	private static final byte[] cartridgeVersion = { (byte)0x01, (byte)0x00 };
	private static final byte[] magicDeskSignature = { (byte)0x00, (byte)0x13, };
	private static final byte[] rgcdSignature = { (byte)0x00, (byte)0x39, };			// inverted hucky 		
	private static final byte[] exrom = { (byte)0x00 };
	private static final byte[] game = { (byte)0x01 };
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
	
	
	
	public byte[] makeCRT( byte[] payload, String type ) {
		int banks = (payload.length / 0x2000) + 1 ;
		
		final int block = 8192;
		
		byte[] cartType;
		if ("ih".equals(type))
			cartType = rgcdSignature.clone();
		else if ("md".equals(type))
			cartType = magicDeskSignature.clone();
		else
			cartType = new byte[] { (byte)0xFF, (byte)0xFF };
		
		byte[] crtHeader = CartCreatorUtils.concatenateByteArrays(	crtSignature, 
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
			b.put(CartCreatorUtils.concatenateByteArrays(	chipString, 
															chipPacketLength, 
															chipType, 
															chipBankNumber,
															chipLoadAddress,
															chipRomImageSize ));
			b.put(Arrays.copyOfRange(	payload, 
										x * block, 
										((x+1) * block) > payload.length ? (payload.length) : ((x+1) * block) ));
		}
		
		return b.array();
	}
}
