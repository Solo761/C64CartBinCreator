package com.solo761.cartcreator.business.manager;

import java.io.File;
import java.io.IOException;

public interface CartCreatorManager {
	
	
	byte[] loadFile(File file) throws IOException;
	
	void saveFile(byte[] data, File file) throws IOException;
	
	byte[] calculatePrgSize(int prgSize);

}
