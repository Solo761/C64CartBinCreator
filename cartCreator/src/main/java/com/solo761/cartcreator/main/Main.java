package com.solo761.cartcreator.main;

import com.solo761.cartcreator.business.logic.CmdPrepareJobList;
import com.solo761.cartcreator.business.logic.JobListProcessor;
import com.solo761.cartcreator.business.model.JobList;
import com.solo761.cartcreator.business.utils.Utils;
import com.solo761.cartcreator.view.controller.StageController;

public class Main{
	
	//private static final Logger	LOGGER	= LoggerFactory.getLogger( Main.class );
	private static JobListProcessor jobListProcessor = new JobListProcessor();
	private static CmdPrepareJobList cmdJobListParser = new CmdPrepareJobList();

	public static void main( String[] args ) {
		
		// if there are command line arguments parse them and run command Line  
		if ( args.length > 0 ) {
			JobList jobList = cmdJobListParser.prepareJobList(args);
			if ( jobList.isHelp() ) {
				Utils.printHelp();
				return;
			}
			if ( jobList.getErrors().length() > 0 ) {
				System.out.println( System.lineSeparator() + 
									System.lineSeparator() +
									jobList.getErrors() );
				System.out.println( System.lineSeparator() + 
									System.lineSeparator() + 
									"Try -h for help" );
				return;
			}
			System.out.println( jobListProcessor.processJobList(jobList).toString() );
		}
		// else start GUI
		else {
			javafx.application.Application.launch( StageController.class, (java.lang.String[]) null );
		}
	}

}
