package com.solo761.cartcreator.main;

import com.solo761.cartcreator.business.logic.CmdArgsParser;
import com.solo761.cartcreator.business.model.Arguments;
import com.solo761.cartcreator.business.utils.CartCreatorUtils;
import com.solo761.cartcreator.view.controller.StageController;

public class Main{
	
	//private static final Logger	LOGGER	= LoggerFactory.getLogger( Main.class );
	private static CommandLine commandLine = new CommandLine();
	private static CmdArgsParser cmdArgsParser = new CmdArgsParser();

	public static void main( String[] args ) {
		
		Arguments arguments = null;
		
		// if there are command line arguments parse them and run command Line  
		if ( args.length > 0 ) {
			arguments = cmdArgsParser.parseArguments(args);
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
			commandLine.commandLine(arguments);
		}
		// else start GUI
		else {
			javafx.application.Application.launch( StageController.class, (java.lang.String[]) null );
		}
	}

}
