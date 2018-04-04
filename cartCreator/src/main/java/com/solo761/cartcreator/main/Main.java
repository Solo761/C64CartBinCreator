package com.solo761.cartcreator.main;

import com.solo761.cartcreator.business.model.Arguments;
import com.solo761.cartcreator.business.utils.CartCreatorArgumentParser;
import com.solo761.cartcreator.business.utils.CartCreatorUtils;

public class Main {
	
	//private static final Logger	LOGGER	= LoggerFactory.getLogger( Main.class );
	private static CommandLine commandLine = new CommandLine();

	public static void main( String[] args ) {
		
		Arguments arguments = null;
		
		if ( args.length > 0 ) {
			arguments = CartCreatorArgumentParser.parseArguments(args);
			if ( arguments.isHelp() ) {
				CartCreatorUtils.printHelp();
				return;
			}
			if ( arguments.getErrors().length() > 0 ) {
				System.out.println( System.lineSeparator() + 
									System.lineSeparator() +
									arguments.getErrors() );
				System.out.println( System.lineSeparator() + 
									System.lineSeparator() + 
									"Try -h for help" );
				return;
			}
		}
		else {
			System.out.println( "No parameters entered" );
			return;
		}
			
		commandLine.commandLine(arguments);
		
	}

}
