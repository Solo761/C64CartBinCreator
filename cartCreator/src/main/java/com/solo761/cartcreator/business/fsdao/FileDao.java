package com.solo761.cartcreator.business.fsdao;

import java.io.File;
import java.io.IOException;

public interface FileDao {
	
	byte[] loadFile(File file) throws IOException ;
	
	boolean saveFile(byte[] data, File file) throws IOException;

}
