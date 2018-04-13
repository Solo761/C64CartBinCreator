package com.solo761.cartcreator.business.utils;

import com.solo761.cartcreator.business.model.Arguments;
import com.solo761.cartcreator.business.model.CartTypes;

public class CartCreatorArgumentParser {
	
	/**
	 * Parses arguments from command line and fills them to<br>
	 * Arguments model to make them easier to use later on.<br><br>
	 * Any errors are stored in "errors" String field of arguments<br>
	 * so they all can be reported after parsing. 
	 * @param args - String array from command line
	 * @return <b>Arguments</b> - Arguments object with filled in parameters
	 */
	public static Arguments parseArguments(String[] args) {
		//List<String> argsList = Arrays.asList(args);
		//Map<String, String> argsMap = new HashMap<String, String>();
		
		Arguments arguments = new Arguments();
		
		StringBuffer errors = new StringBuffer();
		
		for (int x = 0; x < args.length; x++) {
			
			// check for input command and matching path
			if ( "-i".equals(args[x].toLowerCase()) ) {
				if ( (x + 1) < args.length ) {
					if ( !CartCreatorUtils.isFile( args[x + 1] ) )
						errors.append( "Input parameter is not correct file path" + System.lineSeparator() );
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
		
		return arguments;
	}

}
