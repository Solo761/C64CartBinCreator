package com.solo761.cartcreator.view.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.solo761.cartcreator.business.logic.GuiPrepareJobList;
import com.solo761.cartcreator.business.logic.JobListProcessor;
import com.solo761.cartcreator.business.model.CartTypes;
import com.solo761.cartcreator.business.model.FileData;
import com.solo761.cartcreator.business.model.JobList;
import com.solo761.cartcreator.business.model.LoaderTypes;
import com.solo761.cartcreator.business.utils.Utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
	
	private static GuiPrepareJobList guiJobListParser = new GuiPrepareJobList();
	private static JobListProcessor jobListProcessor = new JobListProcessor();

	@FXML
    private Button btnAddFile;

    @FXML
    private Button btnRemoveFile;

    @FXML
    private ChoiceBox<CartTypes> choiceBoxCartType;
    
    @FXML
    private ChoiceBox<LoaderTypes> choiceBoxLoaderType;

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
        assert choiceBoxLoaderType != null : "fx:id=\"choiceBoxLoaderType\" was not injected: check your FXML file 'MainWindow.fxml'.";
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
				addFiles();
			}
		});
        
        btnRemoveFile.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				removeFile();
			}
		});
        
        btnBrowseOutput.setOnAction(new EventHandler<ActionEvent>() {
        	
			@Override
			public void handle(ActionEvent event) {
				selectFolder();
			}
        });
        
        btnStart.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				generateFiles();
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
	
	/**
	 * Manually fill cart type dropdown menu, since not all types are implemented yet
	 */
	private void fillDropDownTypes() {
		choiceBoxCartType.getItems().setAll(CartTypes.INVERTEDHUCKY, CartTypes.HUCKY, CartTypes.MAGICDESK);
		choiceBoxCartType.getSelectionModel().select(0);
		choiceBoxLoaderType.getItems().setAll(LoaderTypes.values());
		choiceBoxLoaderType.getSelectionModel().select(0);
	}

	/**
	 * Add file button code
	 */
	private void addFiles() {
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
	
	/**
	 * Remove file button code
	 */
	private void removeFile() {
		if (tableViewMain.getSelectionModel().getSelectedItems().size() > 0) {
			ObservableList<FileData> selectedList = tableViewMain.getSelectionModel().getSelectedItems();
			for (FileData file : selectedList)
				tableViewMain.getItems().remove(file);
			tableViewMain.getSelectionModel().clearSelection();
		}
	}
	
	/**
	 * Browse for folder button code
	 */
	private void selectFolder() {
		DirectoryChooser dirChooser = new DirectoryChooser();
		dirChooser.setTitle("Choose output folder");
		File output = dirChooser.showDialog(btnBrowseOutput.getScene().getWindow());
		
		if (output != null)
			textFieldOutput.setText(output.getAbsolutePath());
		
	}
	
	/**
	 * Start conversion button code
	 */
	private void generateFiles() {
		if ( "".equals(textFieldOutput.getText().trim()) ) {
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Output path missing");
			alert.setHeaderText(null);
			alert.setContentText("Please select output folder");
			alert.showAndWait();
			return;
		}
		else if ( !Utils.isPath( textFieldOutput.getText().trim() ) ) {
			if ( !Utils.makeDir( textFieldOutput.getText().trim() ) ) {
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
		
		if ( choiceBoxCartType.getSelectionModel().getSelectedItem() == CartTypes.HUCKY && checkBoxCreateCRT.isSelected() ) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("CRT file info");
			alert.setHeaderText( "You have selected Hucky cartridge type and CRT checkbox." );
			String message = "AFAIK emulators (and/or CRT file format) don't have support for"
					+ " Hucky bank configuration, but they do support inverted"
					+ " Hucky bank configuration (so called RGCD carts)." + System.lineSeparator() + System.lineSeparator()
					+ "If you choose to continue banks in CRT files generated will be in inverted Hucky"
					+ " configuration so they will work in emulators.";
			if ( checkBoxCreateBin.isSelected() )
				message += System.lineSeparator() + System.lineSeparator() + "Bin files will be created with correct Hucky configuration!";
			alert.setContentText( message );
			
			ButtonType buttonContinue = new ButtonType("Continue");
			ButtonType buttonAbort = new ButtonType("Abort");
			
			alert.getButtonTypes().setAll(buttonContinue, buttonAbort);
			
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == buttonAbort)
				return;
		}
		
		JobList jobList = guiJobListParser.prepareJobList( tableViewMain.getItems(), 
														   textFieldOutput.getText(),
														   choiceBoxCartType.getSelectionModel().getSelectedItem(),
														   choiceBoxLoaderType.getSelectionModel().getSelectedItem(), 
														   checkBoxCreateBin.isSelected(),
														   checkBoxCreateCRT.isSelected() );
		
		jobListProcessor.processJobList(jobList);
	}
	
}
