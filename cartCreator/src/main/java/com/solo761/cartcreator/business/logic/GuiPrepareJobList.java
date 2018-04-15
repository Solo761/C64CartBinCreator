package com.solo761.cartcreator.business.logic;

import java.util.List;

import com.solo761.cartcreator.business.model.FileData;
import com.solo761.cartcreator.business.model.FilePath;
import com.solo761.cartcreator.business.model.JobList;

public class GuiPrepareJobList {

	/** Takes FileData list with output path string and returns JobList object<br>
	 *  with prepared FilePath list. At this point it's still <b>missing cartType and<br>
	 *  bin/crt booleans</b> needed by JobListProcessor
	 * @param fileList
	 * @param fileOutput
	 * @return <b>JobList</>
	 */
	public JobList prepareJobList( List<FileData> fileList, String fileOutput ) {
		
		JobList jobList = new JobList();
		
		for ( FileData fileData : fileList) {
			FilePath filePath = new FilePath();
			filePath.setInputFile(fileData.getPath());
			filePath.setOutputFile(fileOutput + "\\" + fileData.getFileName().substring(0, fileData.getFileName().lastIndexOf(".")));
			jobList.getFileList().add(filePath);
		}
		
		return jobList;
	}
}
