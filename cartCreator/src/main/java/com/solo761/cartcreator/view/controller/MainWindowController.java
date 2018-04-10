package com.solo761.cartcreator.view.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.solo761.cartcreator.business.model.FileData;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MainWindowController implements Initializable {

	@FXML
    private Button btnAddFile;

    @FXML
    private Button btnRemoveFile;

    @FXML
    private ChoiceBox<?> choiceBoxCartType;

    @FXML
    private CheckBox checkBoxCreateBin;

    @FXML
    private CheckBox checkBoxCreateCRT;

    @FXML
    private Button btnStart;

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
        assert tableViewMain != null : "fx:id=\"tableViewMain\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert tableColumnFile != null : "fx:id=\"tableColumnFile\" was not injected: check your FXML file 'MainWindow.fxml'.";
        
        initTableSource();
        
        btnAddFile.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle( "Select prg file:" );
				fileChooser.getExtensionFilters().addAll(new ExtensionFilter( "Prg file", "*.prg" ),
														 new ExtensionFilter( "All files", "*.*" ));
				List<File> selectedFiles = fileChooser.showOpenMultipleDialog(btnAddFile.getScene().getWindow());
				
				if (selectedFiles != null && !selectedFiles.isEmpty())  {
					System.out.println(selectedFiles);
					
					for (File file : selectedFiles) {
						tableViewMain.getItems().add( new FileData(file.getAbsolutePath(), file.getName()) );
//						tableViewMain.getItems().add( file.getAbsolutePath() );
					}
					
				}
				
			}
		});
        
        btnRemoveFile.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				FileData selectedFile = tableViewMain.getSelectionModel().getSelectedItem();
				System.out.println("Removing " + selectedFile.getFileName());
				tableViewMain.getItems().remove(selectedFile);
				//getFolder();
			}
		});
        
        btnStart.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Start");
				//getFolder();
			}
		});


	}

	private void initTableSource() {
		tableViewMain.setEditable(false);

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

}
