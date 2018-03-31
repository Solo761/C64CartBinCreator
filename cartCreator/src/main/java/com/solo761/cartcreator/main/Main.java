package com.solo761.cartcreator.main;

public class Main {
	
	//private static final Logger	LOGGER	= LoggerFactory.getLogger( Main.class );
	private static CommandLine commandLine = new CommandLine();

	public static void main( String[] args ) {
		
		if ( args.length == 0 ) {
			System.out.println( "You need to enter at least input file" );
			System.out.println( "path for this to work." );
			return;
		}
		
		commandLine.commandLine(args);
		
	}

}
