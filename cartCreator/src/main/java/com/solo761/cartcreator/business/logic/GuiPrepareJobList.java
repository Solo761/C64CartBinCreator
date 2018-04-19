package com.solo761.cartcreator.business.logic;

import java.util.List;

import com.solo761.cartcreator.business.model.CartTypes;
import com.solo761.cartcreator.business.model.FileData;
import com.solo761.cartcreator.business.model.FilePath;
import com.solo761.cartcreator.business.model.JobList;
import com.solo761.cartcreator.business.model.LoaderTypes;

public class GuiPrepareJobList {

	/** Takes FileData list, output path string, CartType, LoaderType, boolean bin<br>
	 *  boolean crt and returns populated JobList object
	 * @param fileList
	 * @param fileOutput
	 * @param cType
	 * @param lType
	 * @param bin
	 * @param crt
	 * @return <b>JobList</>
	 */
	public JobList prepareJobList( List<FileData> fileList, 
								   String fileOutput, 
								   CartTypes cType, 
								   LoaderTypes lType, 
								   boolean bin, 
								   boolean crt ) {
		
		JobList jobList = new JobList();
		
		for ( FileData fileData : fileList) {
			FilePath filePath = new FilePath();
			filePath.setInputFile(fileData.getPath());
			filePath.setOutputFile(fileOutput + "\\" + fileData.getFileName().substring(0, fileData.getFileName().lastIndexOf(".")));
			jobList.getFileList().add(filePath);
		}
		
		jobList.setCartType(cType);
		jobList.setLoaderType(lType);
		jobList.setMakeBin( bin );
		jobList.setMakeCRT( crt );
		
		return jobList;
	}
}
