package com.solo761.cartcreator.business.logic;

import java.io.File;

import com.solo761.cartcreator.business.model.JobList;
import com.solo761.cartcreator.business.model.LoaderTypes;
import com.solo761.cartcreator.business.model.CartTypes;
import com.solo761.cartcreator.business.model.FilePath;
import com.solo761.cartcreator.business.utils.Utils;

public class CmdPrepareJobList {
	
	/** Takes arguments from command line and prepares JobList<br>
	 * object that is sent to JobListProcessor to create files.<br>
	 *<br> 
	 * Any errors are stored in "errors" String field of arguments<br>
	 * so they all can be reported after parsing. 
	 * @param args - String array from command line
	 * @return <b>Arguments</b> - Arguments object with filled in parameters
	 */
	public JobList prepareJobList(String[] args) {
		JobList jobList = new JobList();
		
		StringBuffer errors = new StringBuffer();
		
		FilePath filePaths = new FilePath();
		
		for (int x = 0; x < args.length; x++) {
			
			// check for input command and matching path
			if ( "-i".equals(args[x].toLowerCase()) ) {
				if ( (x + 1) < args.length ) {
					if ( !Utils.isFile( args[x + 1] ) )
						errors.append( "Input parameter is not a file" + System.lineSeparator() );
					else if ( !"prg".equals( args[x + 1].substring(args[x + 1].length() - 3, args[x + 1].length()) ) )
						errors.append( "Input file is not prg file" + System.lineSeparator() );
					else 
						filePaths.setInputFile( args[x + 1] );
				}
			}
			
			// check for cartridge type
			if ( "-t".equals(args[x].toLowerCase()) ) {
				if ( (x + 1) < args.length ) {
					if ( "ih".equals( args[x+1].toLowerCase() ) )
						jobList.setCartType(CartTypes.INVERTEDHUCKY);
					else if ( "h".equals( args[x+1].toLowerCase() ) )
						jobList.setCartType(CartTypes.HUCKY);
					else if ( "md".equals( args[x+1].toLowerCase() ) )
						jobList.setCartType(CartTypes.MAGICDESK);
					else
						errors.append( "Unsupported cartridge type" + System.lineSeparator() );
				}
			}
			
			// check for output command and matching path
			if ( "-o".equals(args[x].toLowerCase()) ) {
				if ( (x + 1) < args.length ) {
					if ( !Utils.isPathNio( args[x + 1] ) )
						errors.append( "Output parameter is not correct file path" + System.lineSeparator() );
					else 
						filePaths.setOutputFile( args[x + 1] );
				}
				else
					errors.append( "Output parameter is missing file path" + System.lineSeparator() );
			}
			
			// check for loader type
			if ( "-l".equals(args[x].toLowerCase()) ) {
				if ( (x + 1) < args.length ) {
					if ( "a".equals( args[x+1].toLowerCase() ) )
						jobList.setLoaderType(LoaderTypes.PRG2CRT);
					else if ( "b".equals( args[x+1].toLowerCase() ) )
						jobList.setLoaderType(LoaderTypes.HUCKY);
					else
						errors.append( "Unsupported loader type" + System.lineSeparator() );
				}
			}
			
			// check if user wants CRT file
			if ( "-c".equals(args[x].toLowerCase()) ) {
				jobList.setMakeCRT( true );
			}
			
			// check if user wants bin file
			if ( "-b".equals(args[x].toLowerCase()) ) {
				jobList.setMakeBin( true );
			}
			
			// check if user wanted help
			if ( "-h".equals(args[x].toLowerCase()) ) {
				jobList.setHelp( true );
				break;
			}
		}
		
		if ( filePaths.getInputFile() == null )
			errors.append( "Input file not entered" + System.lineSeparator() );
		
		if ( filePaths.getInputFile() != null && jobList.getCartType() == null )
			errors.append( "You must enter cart type" + System.lineSeparator() );
		
		if ( filePaths.getInputFile() != null && jobList.isMakeBin() == false && jobList.isMakeCRT() == false )
			errors.append( "You must enter at least one parameter for output file type" + System.lineSeparator() );
		
		if ( filePaths.getInputFile() != null &&jobList.getLoaderType() == null )
			errors.append( "You must enter loader type" + System.lineSeparator() );
		
		jobList.setErrors( errors.toString() );
		
		if (jobList.getErrors().trim().length() == 0 && !jobList.isHelp() ) {
			
			File prgFile = new File( filePaths.getInputFile() ); 
			
			String fileName = "";
			String outPath = "";
			
			// TODO path management is a bit of a mess, but it works (at least combinations 
			// that came to my mind) so I'm leaving it as it is for the moment 
			
			// if there's output file argument use that as file path / name, if extension is 
			// wrong that's on the user's head, but if he didn't use (no dot in file name) extension 
			// one will be added to file name
			// else save file in same path as input file and with same name plus new extension
			if ( filePaths.getOutputFile() != null ) {
				File outputFilePath = new File(filePaths.getOutputFile());
				fileName = outputFilePath.getName();
				
				// if filename is blank that means whole output file path is output path
				// else output path is parent of whole path
				if ( "".equals(fileName.trim()) )
					outPath = outputFilePath.getPath();
				else
					outPath = outputFilePath.getParent() != null ? outputFilePath.getParent() + File.separator : "";
				
				// if file name is just one dot or 3+ dots just use input file name
				if ( ".".equals(fileName) || fileName.matches("(\\.)\\1{2,}") ) {
					fileName = prgFile.getName().substring( 0, prgFile.getName().lastIndexOf(".") );
				}
				// else if file name is two dots (previous folder) set output path to one folder up
				else if ( "..".equals(fileName ) ){
					fileName = prgFile.getName().substring( 0, prgFile.getName().lastIndexOf(".") );
					outPath = outPath + ".." + File.separator;
				}
				// else if file name is blank use whole path from output parameter
				else if ( "".equals(fileName.trim()) ){
					fileName = prgFile.getName().substring( 0, prgFile.getName().lastIndexOf(".") );
					outPath = outPath.endsWith("\\") ? outPath : outPath + File.separator;
				}
				
				if ( fileName.contains(".") ) {
					fileName = fileName.substring( 0, fileName.lastIndexOf(".") );
				}
			}
			else {
				outPath = prgFile.getParent() != null ? prgFile.getParent() + File.separator : "";
				fileName = prgFile.getName().substring(0, prgFile.getName().length() - 4);
			}
			
			filePaths.setOutputFile(outPath + fileName);
		}
		
		jobList.getFileList().add(filePaths);
		
		return jobList;
	}

}
