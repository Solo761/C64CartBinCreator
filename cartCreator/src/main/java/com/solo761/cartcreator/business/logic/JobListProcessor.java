package com.solo761.cartcreator.business.logic;

import java.io.File;
import java.io.IOException;

import com.solo761.cartcreator.business.manager.CartCreatorManager;
import com.solo761.cartcreator.business.manager.impl.CartCreatorManagerImpl;
import com.solo761.cartcreator.business.model.FilePath;
import com.solo761.cartcreator.business.model.JobList;

public class JobListProcessor {
	
	private static CartCreatorManager cartCreatorManager = new CartCreatorManagerImpl();
	
	/**	Takes prepared JobList object and processes files listed in List<FilePath> property<br>
	 * and converts them to bin/crt files
	 * @param jobList
	 */
	public void processJobList( JobList jobList ) {
		
		for (FilePath filePath : jobList.getFileList()) {
	
			File prgFile = new File( filePath.getInputFile() ); 
			
			byte[] prg = null; 
			
			try {
				prg = cartCreatorManager.loadFile(prgFile);
			} catch (IOException e) {
				System.out.println("Error reading file: " + prgFile.getName());
				System.out.println( e.getMessage() );
			}
			
			try {
				if ( jobList.isMakeCRT() ) {
					File outFile = new File( filePath.getOutputFile() + jobList.getCrtExtension() );
					// TODO add check if it's null, it can only be null if it's, or maybe remove calculaction from model and add to CartCreatorManagerImpl
					cartCreatorManager.saveFile(cartCreatorManager.createCRTFile(jobList.getCartType(), jobList.getLoaderType(), prg), outFile);
					System.out.println("Created CRT file: " + outFile.getAbsolutePath() );
				}
				if ( jobList.isMakeBin() ) {
					File outFile = new File( filePath.getOutputFile() + jobList.getBinExtension() );
					// TODO add check if it's null, it can only be null if it's, or maybe remove calculaction from model and add to CartCreatorManagerImpl
					cartCreatorManager.saveFile(cartCreatorManager.createBinFile(jobList.getCartType(), jobList.getLoaderType(), prg), outFile);
					System.out.println("Created bin file: " + outFile.getAbsolutePath() );
				}
			} catch (IOException e) {
				System.out.println("Error writing file: " + prgFile.getName());
				e.printStackTrace();
			}
		
		}
		
	}

}
