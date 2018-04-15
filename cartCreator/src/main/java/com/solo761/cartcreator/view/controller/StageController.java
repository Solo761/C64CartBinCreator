package com.solo761.cartcreator.view.controller;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Main GUI class, this one loads MainWindow fxml file and controller
 *
 */
public class StageController extends Application {
	
	@Override
	public void start(Stage stage) throws Exception {
		
		try {
			BorderPane rootLayout;
			
			stage.setTitle( "Cartridge Creator tool" );
			
			// I had to set min/max values here, for some reason it ignores fxml settings
			stage.setMaxWidth(495.0);
			stage.setMinWidth(495.0);
			stage.setMinHeight(680.0);
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ClassLoader.getSystemResource("fxml/MainWindow.fxml"));
			rootLayout = (BorderPane) loader.load();
			
			Scene scene = new Scene(rootLayout);
			stage.setScene(scene);
			stage.show();
		}
		catch (IOException e) {
			// TODO error handling
		}
		
	}

}
