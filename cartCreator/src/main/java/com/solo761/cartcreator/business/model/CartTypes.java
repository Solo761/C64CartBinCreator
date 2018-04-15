package com.solo761.cartcreator.business.model;

/**
 * Enum for supported cart types, although currently not all are used
 *
 */
public enum CartTypes {
	
	HUCKY("Hucky"),
	INVERTEDHUCKY("Inverted hucky"),
	MAGICDESK("MagicDesk"),
	SIXTEENK("16k cartridge"),
	EIGHTK("8k cartridge");
	
	private String type;
	
	CartTypes(String type) {
		this.type = type;
	}
	
	public String toString() {
		return type;
	}
}
