package com.solo761.cartcreator.main;

import com.solo761.cartcreator.business.utils.CartCreatorUtils;

public class Main {
	
	//private static final Logger	LOGGER	= LoggerFactory.getLogger( Main.class );
	private static CommandLine commandLine = new CommandLine();

	public static void main( String[] args ) {
		
		// no parameters entered
		if ( args.length == 0 ) {
			CartCreatorUtils.printHelp();
			return;
		}
		// -h entered
		else if ( (args.length == 1) && ("-h".equals(args[0])) ) {
			CartCreatorUtils.printHelp();
			return;
		}
		// all parameters come in pairs so if there isn't even number of parameters...
		else if ( (args.length > 0) && (args.length % 2 != 0) ) {
			System.out.println("Wrong parameters entered");
			System.out.println("");
			CartCreatorUtils.printHelp();
			return;
		}
		
		commandLine.commandLine(args);
		
	}

}
