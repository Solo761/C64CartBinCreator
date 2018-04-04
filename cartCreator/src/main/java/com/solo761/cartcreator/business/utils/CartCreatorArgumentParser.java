package com.solo761.cartcreator.business.utils;

import com.solo761.cartcreator.business.model.Arguments;
import com.solo761.cartcreator.business.model.CartTypes;

public class CartCreatorArgumentParser {
	
	public static Arguments parseArguments(String[] args) {
		//List<String> argsList = Arrays.asList(args);
		//Map<String, String> argsMap = new HashMap<String, String>();
		
		Arguments arguments = new Arguments();
		
		StringBuffer errors = new StringBuffer();
		
		for (int x = 0; x < args.length; x++) {
			
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
			
			if ( "-o".equals(args[x].toLowerCase()) ) {
				if ( (x + 1) < args.length ) {
					if ( !CartCreatorUtils.pathTest( args[x + 1] ) )
						errors.append( "Output parameter is not correct file path" + System.lineSeparator() );
					else 
						arguments.setOutputFile( args[x + 1] );
				}
				else
					errors.append( "Output parameter is missing file path" + System.lineSeparator() );
			}
			
			if ( "-c".equals(args[x].toLowerCase()) ) {
				arguments.setMakeCRT( true );
			}
			
			if ( "-h".equals(args[x].toLowerCase()) ) {
				arguments.setHelp( true );
				break;
			}
			
		}
		
		if ( arguments.getInputFile() == null )
			errors.append( "Input file not entered" + System.lineSeparator() );
		
		if ( arguments.getInputFile() != null && arguments.getCartType() == null)
			errors.append( "You must enter cart" + System.lineSeparator() );
		
		arguments.setErrors( errors.toString() );
		
		return arguments;
	}

}
