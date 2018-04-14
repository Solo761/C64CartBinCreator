package com.solo761.cartcreator.business.logic;

import java.io.File;

import com.solo761.cartcreator.business.model.Arguments;
import com.solo761.cartcreator.business.model.CartTypes;
import com.solo761.cartcreator.business.utils.CartCreatorUtils;

public class CmdArgsParser {
	
	/** Accepts arguments from command line and parses them to<br>
	 * Arguments object that is sent to file creator.<br>
	 *<br> 
	 * Any errors are stored in "errors" String field of arguments<br>
	 * so they all can be reported after parsing. 
	 * @param args - String array from command line
	 * @return <b>Arguments</b> - Arguments object with filled in parameters
	 */
	public Arguments parseArguments(String[] args) {
		//List<String> argsList = Arrays.asList(args);
		//Map<String, String> argsMap = new HashMap<String, String>();
		
		Arguments arguments = new Arguments();
		
		StringBuffer errors = new StringBuffer();
		
		for (int x = 0; x < args.length; x++) {
			
			// check for input command and matching path
			if ( "-i".equals(args[x].toLowerCase()) ) {
				if ( (x + 1) < args.length ) {
					if ( !CartCreatorUtils.isFile( args[x + 1] ) )
						errors.append( "Input parameter is not a file" + System.lineSeparator() );
					else if ( !"prg".equals( args[x + 1].substring(args[x + 1].length() - 3, args[x + 1].length()) ) )
						errors.append( "Input file is not prg file" + System.lineSeparator() );
					else 
						arguments.setInputFile( args[x + 1] );
				}
			}
			
			// check for cartridge type
			if ( "-t".equals(args[x].toLowerCase()) ) {
				if ( (x + 1) < args.length ) {
					if ( "ih".equals( args[x+1].toLowerCase() ) )
						arguments.setCartType(CartTypes.INVERTEDHUCKY);
					else if ( "md".equals( args[x+1].toLowerCase() ) )
						arguments.setCartType(CartTypes.MAGICDESK);
					else
						errors.append( "Unsupported cartridge type" + System.lineSeparator() );
				}
			}
			
			// check for output command and matching path
			if ( "-o".equals(args[x].toLowerCase()) ) {
				if ( (x + 1) < args.length ) {
					if ( !CartCreatorUtils.isPathNio( args[x + 1] ) )
						errors.append( "Output parameter is not correct file path" + System.lineSeparator() );
					else 
						arguments.setOutputFile( args[x + 1] );
				}
				else
					errors.append( "Output parameter is missing file path" + System.lineSeparator() );
			}
			
			// check if user wants CRT file
			if ( "-c".equals(args[x].toLowerCase()) ) {
				arguments.setMakeCRT( true );
			}
			
			// check if user wants bin file
			if ( "-b".equals(args[x].toLowerCase()) ) {
				arguments.setMakeBin( true );
			}
			
			// check if user wanted help
			if ( "-h".equals(args[x].toLowerCase()) ) {
				arguments.setHelp( true );
				break;
			}
			
		}
		
		if ( arguments.getInputFile() == null )
			errors.append( "Input file not entered" + System.lineSeparator() );
		
		if ( arguments.getInputFile() != null && arguments.getCartType() == null)
			errors.append( "You must enter cart type" + System.lineSeparator() );
		
		if ( arguments.isMakeBin() == false && arguments.isMakeCRT() == false )
			errors.append( "You must enter at least one parameter for output file type" + System.lineSeparator() );
		
		arguments.setErrors( errors.toString() );
		
		if (arguments.getErrors().trim().length() == 0 && !arguments.isHelp() ) {
			
			File prgFile = new File( arguments.getInputFile() ); 
			
			String fileName = "";
			String outPath = "";
			
			// TODO path management is a bit of a mess, but it works (at least combinations 
			// that came to my mind) so I'm leaving it as it is for the moment 
			
			// if there's output file argument use that as file path / name, if extension is 
			// wrong that's on the user's head, but if he didn't use (no dot in file name) extension 
			// one will be added to file name
			// else save file in same path as input file and with same name plus new extension
			if ( arguments.getOutputFile() != null ) {
				File outputFilePath = new File(arguments.getOutputFile());
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
			
			arguments.setOutputFile(outPath + fileName);
		}
		
		
		
		return arguments;
	}

}
