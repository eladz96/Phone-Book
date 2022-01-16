import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PhoneBookController {

	private PhoneBookLogic phoneBook;	//logic object
	private boolean flag = false;		//flag that say if we have a loaded file
	private File file;					//File object

	@FXML
	private TextArea textArea;

	@FXML
	private TextField nameText;

	@FXML
	private TextField phoneNumberText;

	@FXML
	private Button addButton;

	@FXML
	private Button removeButton;

	@FXML
	private Button searchButton;

	@FXML
	private Button loadButton;

	@FXML
	private Button saveButton;

	public void initialize() {	//initializing the logic
		phoneBook = new PhoneBookLogic();
	}

	@FXML
	void addButtonPress(ActionEvent event) {
		if (!flag) { // if we didn't load any file yet
			Alert alert = new Alert(AlertType.ERROR);//show warning
			alert.setTitle("Error");
			alert.setHeaderText("No Phone Book Loaded");
			alert.setContentText("Please load a phone book file and try again");
			alert.showAndWait();
		} else {
			if (nameText.getText().isEmpty() || phoneNumberText.getText().isEmpty()) {//check if we have all the data we need
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("No name or phone number");
				alert.setContentText("Please fill the textboxes and try again");
				alert.showAndWait();
			} else {
				phoneBook.add(nameText.getText(), phoneNumberText.getText());//adding to the phone book
				Alert alert = new Alert(AlertType.INFORMATION);//show success message
				alert.setTitle("The Addition Was Successfull");
				alert.setHeaderText(null);
				alert.setContentText("The new person was successfully added to the phone book!");
				alert.showAndWait();
				textArea.setText(phoneBook.list4Print());//print the updated phone book
			}
		}
	}

	@FXML
	void loadButtonPress(ActionEvent event) {
		file = getFile();//getting the file
		if (!phoneBook.loadFile(file)) {//loading the data from the file to the data structure
			Alert alert = new Alert(AlertType.ERROR);//if we fail show warning
			alert.setTitle("Error");
			alert.setHeaderText("Loading Faild");
			alert.setContentText("Please check if the file is OK and try again");
			alert.showAndWait();
			flag = false;
		} else {//if we succeeded show a message 
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Loading Successfull");
			alert.setHeaderText(null);
			alert.setContentText("The phone book was loaded successfully!");
			alert.showAndWait();
			flag = true;//update flag
			textArea.setText(phoneBook.list4Print());//print the phone book
		}
	}

	@FXML
	void removeButtonPress(ActionEvent event) {
		if (!flag) { // if we didn't load any file yet
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("No Phone Book Loaded");
			alert.setContentText("Please load a phone book file and try again");
			alert.showAndWait();
		} else {
			if (nameText.getText().isEmpty()) {//check if we have all the data we need
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("No name!");
				alert.setContentText("Please fill the name textbox and try again");
				alert.showAndWait();
			} else {
				phoneBook.remove(nameText.getText());//removing from the book
				Alert alert = new Alert(AlertType.INFORMATION);//show message
				alert.setTitle("The Removal Was Successfull");
				alert.setHeaderText(null);
				alert.setContentText("The person was successfully removed from the phone book!");
				alert.showAndWait();
				textArea.setText(phoneBook.list4Print());//print phone book
			}
		}
	}

	@FXML
	void saveButtonPress(ActionEvent event) {
		if (!flag) { // if we didn't load any file yet
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("No Phone Book Loaded");
			alert.setContentText("Please load a phone book file and try again");
			alert.showAndWait();
		} else {
			try {
				phoneBook.save(file);//save the file
			} catch (IOException e) {//if there was an exception show error message
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Saving Failed");
				alert.setContentText("An IOException occurred during the saving process");
				alert.showAndWait();
			}
			Alert alert = new Alert(AlertType.INFORMATION);//show success message
			alert.setTitle("Saving was successfull");
			alert.setHeaderText(null);
			alert.setContentText("The Phone Book was Successfullly Saved!");
			alert.showAndWait();
			textArea.setText(phoneBook.list4Print());//print phone book
		}
	}

	@FXML
	void searchButtonPress(ActionEvent event) {
		if (!flag) { // if we didn't load any file yet
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("No Phone Book Loaded");
			alert.setContentText("Please load a phone book file and try again");
			alert.showAndWait();
		} else {
			if (nameText.getText().isEmpty()) {//check if we have all the data we need
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("No name!");
				alert.setContentText("Please fill the name textbox and try again");
				alert.showAndWait();
			} else {
				if (phoneBook.contain(nameText.getText())) {//check if the name is in the book
					Alert alert = new Alert(AlertType.INFORMATION);// if so show the phone number of that person
					alert.setTitle(nameText.getText() + " is in the phone book!");
					alert.setHeaderText(null);
					alert.setContentText(
							nameText.getText() + "'s phone number is: " + phoneBook.getNumber(nameText.getText()));
					alert.showAndWait();
					textArea.setText(phoneBook.list4Print());// print the phone book for no reason
				} else {
					Alert alert = new Alert(AlertType.INFORMATION);// if the name is not in the phone book show a helpful message
					alert.setTitle(nameText.getText() + " is not in the phone book!");
					alert.setHeaderText(null);
					alert.setContentText("If you want to add him/her please use the \"Add/Update\" option");
					alert.showAndWait();
				}
			}
		}
	}

	private File getFile() {//file chooser
		FileChooser fc = new FileChooser();
		fc.setTitle("Choose Phone Book File");
		Stage stage = (Stage) textArea.getScene().getWindow();
		File selectedFile = fc.showOpenDialog(stage);
		return selectedFile;

	}

}
