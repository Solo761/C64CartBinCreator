package com.solo761.cartcreator.view.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.solo761.cartcreator.business.manager.CartCreatorManager;
import com.solo761.cartcreator.business.manager.impl.CartCreatorManagerImpl;
import com.solo761.cartcreator.business.model.Arguments;
import com.solo761.cartcreator.business.model.CartTypes;
import com.solo761.cartcreator.business.model.FileData;
import com.solo761.cartcreator.business.utils.CartCreatorUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MainWindowController implements Initializable {
	
	private static CartCreatorManager cartCreatorManager = new CartCreatorManagerImpl();

	@FXML
    private Button btnAddFile;

    @FXML
    private Button btnRemoveFile;

    @FXML
    private ChoiceBox<CartTypes> choiceBoxCartType;

    @FXML
    private CheckBox checkBoxCreateBin;

    @FXML
    private CheckBox checkBoxCreateCRT;

    @FXML
    private Button btnStart;
    
    @FXML
    private TextField textFieldOutput;

    @FXML
    private Button btnBrowseOutput;

    @FXML
    private TableView<FileData> tableViewMain;

    @FXML
    private TableColumn<FileData, String> tableColumnFile;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
        assert btnAddFile != null : "fx:id=\"btnAddFile\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert btnRemoveFile != null : "fx:id=\"btnRemoveFile\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert choiceBoxCartType != null : "fx:id=\"choiceBoxCartType\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert checkBoxCreateBin != null : "fx:id=\"checkBoxCreateBin\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert checkBoxCreateCRT != null : "fx:id=\"checkBoxCreateCRT\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert btnStart != null : "fx:id=\"btnStart\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert textFieldOutput != null : "fx:id=\"textFieldOutput\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert btnBrowseOutput != null : "fx:id=\"btnBrowseOutput\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert tableViewMain != null : "fx:id=\"tableViewMain\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert tableColumnFile != null : "fx:id=\"tableColumnFile\" was not injected: check your FXML file 'MainWindow.fxml'.";
        
        initTableSource();
        
        fillDropDownTypes();
        
        btnAddFile.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle( "Select prg file:" );
				fileChooser.getExtensionFilters().addAll(new ExtensionFilter( "Prg file", "*.prg" ),
														 new ExtensionFilter( "All files", "*.*" ));
				List<File> selectedFiles = fileChooser.showOpenMultipleDialog(btnAddFile.getScene().getWindow());
				
				if (selectedFiles != null && !selectedFiles.isEmpty())  {
					for (File file : selectedFiles) {
						FileData newFile = new FileData(file.getAbsolutePath(), file.getName());
						if ( !tableViewMain.getItems().contains(newFile) ) {
							tableViewMain.getItems().add( newFile );
						}
						else
							System.out.println( "File \"" + newFile.getPath() + "\" already added" );
					}
					
				}
				
			}
		});
        
        btnRemoveFile.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if (tableViewMain.getSelectionModel().getSelectedItems().size() > 0) {
					ObservableList<FileData> selectedList = tableViewMain.getSelectionModel().getSelectedItems();
					for (FileData file : selectedList)
						tableViewMain.getItems().remove(file);
					tableViewMain.getSelectionModel().clearSelection();
				}
			}
		});
        
        btnBrowseOutput.setOnAction(new EventHandler<ActionEvent>() {
        	
			@Override
			public void handle(ActionEvent event) {
				DirectoryChooser dirChooser = new DirectoryChooser();
				dirChooser.setTitle("Choose output folder");
				File output = dirChooser.showDialog(btnBrowseOutput.getScene().getWindow());
				
				if (output != null)
					textFieldOutput.setText(output.getAbsolutePath());
				
			}
        });
        
        btnStart.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if ( "".equals(textFieldOutput.getText().trim()) ) {
					
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Output path missing");
					alert.setHeaderText(null);
					alert.setContentText("Please select output folder");
					alert.showAndWait();
					return;
				}
				else if ( !CartCreatorUtils.isPath( textFieldOutput.getText().trim() ) ) {
					if ( !CartCreatorUtils.makeDir( textFieldOutput.getText().trim() ) ) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Output path wrong");
						alert.setHeaderText(null);
						alert.setContentText("Unable to create output folder");
						alert.showAndWait();
						return;
					}
				}
				
				textFieldOutput.setText( new File(textFieldOutput.getText().trim()).getAbsolutePath() );
				
				if ( !(tableViewMain.getItems().size() > 0) ) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Nothing to do");
					alert.setHeaderText(null);
					alert.setContentText("You must add some prg files to the list");
					alert.showAndWait();
					return;
				}
				
				// ovo preraditi da može biti ista logika i za command line i za ovo
				// npr. modificirati argumente pa da se sve obradi kroz to 
				
				for ( FileData fileData : tableViewMain.getItems() ) {
					byte[] prg = null; 
					
					try {
						prg = cartCreatorManager.loadFile( new File(fileData.getPath()) );
					} catch (IOException e) {
						System.out.println("Error reading file: " + fileData.getFileName());
						System.out.println( e.getMessage() );
					}
					
					try {
						if (checkBoxCreateBin.isSelected()) {
							cartCreatorManager.saveFile(
									cartCreatorManager.createBinFile(
											choiceBoxCartType.getSelectionModel().getSelectedItem(),
											prg),
									new File( textFieldOutput.getText() + "\\" + fileData.getFileName().substring( 0, fileData.getFileName().lastIndexOf(".") ) + ".bin" ));
						}
						if (checkBoxCreateCRT.isSelected()) {
							cartCreatorManager.saveFile(
									cartCreatorManager.createCRTFile(
											choiceBoxCartType.getSelectionModel().getSelectedItem(),
											prg),
									new File( textFieldOutput.getText() + "\\" + fileData.getFileName().substring( 0, fileData.getFileName().lastIndexOf(".") ) + ".crt" ));							
						}
					} catch (IOException e) {
						System.out.println("Error saving file: " + fileData.getFileName());
						System.out.println( e.getMessage() );
					}
					
				}
				
			}
		});
        
        checkBoxCreateBin.setOnAction(new EventHandler<ActionEvent>() {
        	
			@Override
			public void handle(ActionEvent event) {
				if (!checkBoxCreateBin.isSelected())
					checkBoxCreateCRT.setSelected(true); 
			}
        });
        
        checkBoxCreateCRT.setOnAction(new EventHandler<ActionEvent>() {
        	
			@Override
			public void handle(ActionEvent event) {
				if (!checkBoxCreateCRT.isSelected())
					checkBoxCreateBin.setSelected(true); 
			}
        });


	}

	private void initTableSource() {
		tableViewMain.setEditable(false);
		tableViewMain.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		tableColumnFile.setCellValueFactory( new PropertyValueFactory<FileData, String>( "fileName" ) );
		tableColumnFile.setCellFactory(
				column -> {
					
					return new TableCell<FileData, String>() {
						@Override
						protected void updateItem(String item, boolean empty)
						{
							super.updateItem(item, empty);
							setText(item);
							hoverProperty().addListener((obversable) -> {
								if (getIndex() < tableViewMain.getItems().size()) {
									String path = tableViewMain.getItems().get( getIndex() ).getPath();
									setTooltip(new Tooltip(path));
								}
							});
						}
					};
				}
				);
		
		List<FileData> fileList = new ArrayList<FileData>();
		ObservableList<FileData> observableFileList = FXCollections.observableArrayList(fileList);
		tableViewMain.setItems(observableFileList);
		
	}
	
	private void fillDropDownTypes() {
		choiceBoxCartType.getItems().setAll(CartTypes.INVERTEDHUCKY, CartTypes.MAGICDESK);
		choiceBoxCartType.getSelectionModel().select(0);
	}

}
