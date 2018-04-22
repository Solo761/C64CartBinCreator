package com.solo761.cartcreator.business.logic;

import java.io.File;
import java.io.IOException;

import com.solo761.cartcreator.business.manager.CartCreatorManager;
import com.solo761.cartcreator.business.manager.impl.CartCreatorManagerImpl;
import com.solo761.cartcreator.business.model.CartCreatorException;
import com.solo761.cartcreator.business.model.FilePath;
import com.solo761.cartcreator.business.model.JobList;
import com.solo761.cartcreator.business.model.JobResult;

public class JobListProcessor {
	
	private static CartCreatorManager cartCreatorManager = new CartCreatorManagerImpl();
	
	/**	Takes prepared JobList object and processes files listed in List<FilePath> property<br>
	 * and converts them to bin/crt files
	 * @param jobList
	 */
	public JobResult processJobList( JobList jobList ) {
		
		StringBuffer processed = new StringBuffer();
		StringBuffer errors = new StringBuffer();
		
		for (FilePath filePath : jobList.getFileList()) {
	
			File prgFile = new File( filePath.getInputFile() ); 
			
			byte[] prg = null; 
			
			try {
				prg = cartCreatorManager.loadFile(prgFile);
			} catch (IOException e) {
				errors.append( "Error reading file: " + prgFile.getName() + System.lineSeparator() );
				e.printStackTrace();
			}
			
			try {
				if ( jobList.isMakeCRT() ) {
					File outFile = new File( filePath.getOutputFile() + jobList.getCrtExtension() );
					cartCreatorManager.saveFile(cartCreatorManager.createCRTFile(jobList.getCartType(), jobList.getLoaderType(), prg), outFile);
					processed.append( "Created CRT file: " + outFile.getAbsolutePath() + System.lineSeparator() );
				}
				if ( jobList.isMakeBin() ) {
					File outFile = new File( filePath.getOutputFile() + jobList.getBinExtension() );
					cartCreatorManager.saveFile(cartCreatorManager.createBinFile(jobList.getCartType(), jobList.getLoaderType(), prg), outFile);
					processed.append( "Created bin file: " + outFile.getAbsolutePath() + System.lineSeparator());
				}
			} catch (IOException e) {
				errors.append( "Error writing file: " + prgFile.getName() + System.lineSeparator() );
				e.printStackTrace();
			} catch (CartCreatorException e) {
				errors.append(prgFile.getName() + " skipped - " + e.getMessage() + System.lineSeparator());
			}
		
		}
		
		return new JobResult( processed.toString(), errors.toString() );
		
	}

}
