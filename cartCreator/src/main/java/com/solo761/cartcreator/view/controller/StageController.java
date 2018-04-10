package com.solo761.cartcreator.view.controller;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class StageController extends Application {
	
	@Override
	public void start(Stage stage) throws Exception {
		
		try {
			BorderPane rootLayout;
			
			stage.setTitle( "Cartridge Creator tool" );
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/com/solo761/cartcreator/view/fxml/MainWindow.fxml"));
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
