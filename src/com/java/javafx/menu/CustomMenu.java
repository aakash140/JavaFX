package com.java.javafx.menu;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class CustomMenu extends Application {

	Hyperlink aboutMe = new Hyperlink("Aakash");
	final String LinkedINURL = "https://goo.gl/PHqIa5";
	Alert aboutInfo;
	int textLength;
	String tempString;
	File saveFile;
	ProgressBar pb;
	Text workDone = new Text();
	FontWeight fWeight;
	FontPosture fPosture;
	Thread appThread = Thread.currentThread();

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Menu");
		primaryStage.setResizable(false);
		BorderPane rootNode = new BorderPane();
		Scene aScene = new Scene(rootNode, 500, 300);
		primaryStage.setScene(aScene);
		Menu file = new Menu("File");
		MenuItem open = new MenuItem("Open");
		MenuItem save = new MenuItem("Save");
		MenuItem saveAs = new MenuItem("Save As");
		MenuItem exit = new MenuItem("Exit");
		file.getItems().addAll(open, new SeparatorMenuItem(), save, saveAs, new SeparatorMenuItem(), exit);

		Menu options = new Menu("Options");

		Menu textSize = new Menu("Text Size");
		RadioMenuItem large = new RadioMenuItem("Large");
		RadioMenuItem normal = new RadioMenuItem("Normal");
		normal.setSelected(true);
		RadioMenuItem small = new RadioMenuItem("Small");
		ToggleGroup tg = new ToggleGroup();
		large.setToggleGroup(tg);
		normal.setToggleGroup(tg);
		small.setToggleGroup(tg);
		textSize.getItems().addAll(large, normal, small);

		Menu textStyle = new Menu("Text Style");
		CheckMenuItem bold = new CheckMenuItem("Bold");
		CheckMenuItem italic = new CheckMenuItem("Italic");
		textStyle.getItems().addAll(bold, italic);

		options.getItems().addAll(textSize, textStyle);

		Menu help = new Menu("Help");
		MenuItem helpGuide = new MenuItem("Help Guide");
		MenuItem about = new MenuItem("About");
		help.getItems().addAll(helpGuide, about);

		open.setGraphic(new ImageView("open.png"));
		save.setGraphic(new ImageView("save.png"));
		saveAs.setGraphic(new ImageView("saveAs.png"));
		exit.setGraphic(new ImageView("exit.png"));
		helpGuide.setGraphic(new ImageView("book.jpg"));
		about.setGraphic(new ImageView("about_info1.png"));

		MenuBar mainMenu = new MenuBar(file, options, help);

		TextArea textBox = new TextArea();
		textBox.setPrefRowCount(5);
		textBox.setPrefColumnCount(5);
		textBox.setWrapText(true);

		MenuItem menu = new MenuItem("Menu");
		textBox.setContextMenu(new ContextMenu(menu));

		Label textLabel = new Label("Following Text can be saved in a file.");
		Label saveResponse = new Label();
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.add(textLabel, 0, 0);
		gridPane.add(textBox, 0, 1);

		open.setAccelerator(KeyCombination.keyCombination("Shortcut+O"));
		save.setAccelerator(KeyCombination.keyCombination("Shortcut+s"));
		saveAs.setAccelerator(KeyCombination.keyCombination("Shift+Shortcut+s"));
		exit.setAccelerator(KeyCombination.keyCombination("Shortcut+E"));
		helpGuide.setAccelerator(KeyCombination.keyCombination("F1"));
		about.setAccelerator(KeyCombination.keyCombination("F3"));

		open.setOnAction((ae) -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setInitialDirectory(new File("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop"));
			File openedFile = fileChooser.showOpenDialog(null);
			try {
				Desktop.getDesktop().open(openedFile);
			} catch (IOException | NullPointerException exception) {
				if (exception instanceof IOException) {
					String headerMessage = "Sorry! The file could not be opened.";
					displayErrorAlert(exception, headerMessage);
				}
			}
		});

		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ae) {
				String textBoxData = textBox.getText();
				if (textBoxData.length() > 0) {
					if (tempString == null) {
						displaySaveFileDialog(textBoxData, primaryStage);
					} else if (saveFile.exists()) {
						saveFile(textBoxData);
					} else
						displaySaveFileDialog(textBoxData, primaryStage);

				} else if (textBoxData.length() == 0) {
					InnerShadow shadow = new InnerShadow();
					shadow.setColor(Color.RED);
					shadow.setRadius(10.0);
					textBox.setEffect(shadow);
					textBox.requestFocus();

					displayNoTextAlert();
				}
			}
		});

		saveAs.setOnAction((ae) -> {
			String textBoxData = textBox.getText();
			if (textBoxData.length() > 0) {
				displaySaveFileDialog(textBoxData, primaryStage);
			} else if (textBoxData.length() == 0) {
				InnerShadow shadow = new InnerShadow();
				shadow.setColor(Color.RED);
				shadow.setRadius(10.0);
				textBox.setEffect(shadow);
				textBox.requestFocus();

				displayNoTextAlert();
			}
		});

		exit.setOnAction((ae) -> {
			Platform.exit();
		});

		large.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent ae) {
				textBox.setFont(Font.font(null, fWeight, fPosture, 20.0));
			}
		});

		normal.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent ae) {
				textBox.setFont(Font.font(null, fWeight, fPosture, -1.0));
			}
		});

		small.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent ae) {
				textBox.setFont(Font.font(null, fWeight, fPosture, 10.0));
			}
		});

		bold.setOnAction((ae) -> {
			double originalSize = textBox.getFont().getSize();

			if (fWeight == null) {
				fWeight = FontWeight.BOLD;
				textBox.setFont(Font.font(null, fWeight, fPosture, originalSize));
			} else {
				fWeight = null;
				textBox.setFont(Font.font(null, fWeight, fPosture, originalSize));
			}
		});

		italic.setOnAction((ae) -> {
			double originalSize = textBox.getFont().getSize();

			if (fPosture == null) {
				fPosture = FontPosture.ITALIC;
				textBox.setFont(Font.font(null, fWeight, fPosture, originalSize));
			} else {
				fPosture = null;
				textBox.setFont(Font.font(null, fWeight, fPosture, originalSize));
			}

		});

		helpGuide.setOnAction((ae) -> {
			File helpDoc = new File("Help Guide.docx");
			try {
				Desktop.getDesktop().open(helpDoc);
			} catch (IOException | IllegalArgumentException exception) {
				String headerMessage = "Sorry! Help Guide is not available at this time.";
				displayErrorAlert(exception, headerMessage);
			}
		});

		about.setOnAction((ae) -> {
			displayAboutInfo();
		});

		aboutMe.setOnAction((ae) -> {
			try {
				Desktop.getDesktop().browse(new URI(LinkedINURL));
				aboutInfo.close();
			} catch (IOException | URISyntaxException exception) {
				String headerMessage = "Sorry! There is a problem with the URL.";
				displayErrorAlert(exception, headerMessage);
			}
		});

		aScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.F1)
					helpGuide.fire();
				else if (event.getCode() == KeyCode.F3)
					about.fire();
				event.consume();
			}
		});

		aScene.setOnMouseClicked((me) -> {
			if (saveResponse.getText().length() > 0)
				saveResponse.setText("");
		});

		rootNode.setTop(mainMenu);
		rootNode.setCenter(gridPane);

		ToggleButton boldButton = new ToggleButton("Bold", new ImageView("Bold.jpg"));
		boldButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		ToggleButton italicButton = new ToggleButton("Bold", new ImageView("Italic.jpg"));
		italicButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		ToolBar textStyleToolbar = new ToolBar();
		textStyleToolbar.getItems().addAll(boldButton, italicButton);
		textStyleToolbar.setOrientation(Orientation.VERTICAL);

		rootNode.setLeft(textStyleToolbar);

		boldButton.setOnAction((ae) -> {
			double originalSize = textBox.getFont().getSize();

			if (fWeight == null) {
				fWeight = FontWeight.BOLD;
				textBox.setFont(Font.font(null, fWeight, fPosture, originalSize));
			} else {
				fWeight = null;
				textBox.setFont(Font.font(null, fWeight, fPosture, originalSize));
			}
		});

		italicButton.setOnAction((ae) -> {
			double originalSize = textBox.getFont().getSize();

			if (fPosture == null) {
				fPosture = FontPosture.ITALIC;
				textBox.setFont(Font.font(null, fWeight, fPosture, originalSize));
			} else {
				fPosture = null;
				textBox.setFont(Font.font(null, fWeight, fPosture, originalSize));
			}

		});

		pb = new ProgressBar(0);
		pb.setPrefWidth(200);

		Text prog = new Text();
		FlowPane flowPane = new FlowPane(10, 10);
		flowPane.setAlignment(Pos.BASELINE_LEFT);
		flowPane.getChildren().addAll(pb, prog, new Separator(Orientation.VERTICAL), workDone);

		pb.indeterminateProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> ob, Boolean oldVal, Boolean newVal) {
				if (newVal) {
					prog.setText("Waiting");
					prog.setFill(Color.BLACK);
					pb.setEffect(null);
				} else {
					prog.setText("Processing");
					prog.setFill(Color.BLACK);
					pb.setEffect(null);
				}
			}
		});

		pb.progressProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> ob, Number oldNUm, Number newNum) {
				if (newNum.floatValue() == 1) {
					prog.setText("Complete");
					prog.setFill(Color.GREEN);
					ColorAdjust colorAdjust = new ColorAdjust();
					colorAdjust.setHue(-0.3);
					pb.setEffect(colorAdjust);
				}
			}
		});

		rootNode.setBottom(flowPane);
		// rootNode.setBottom(saveResponse);
		primaryStage.show();
	}

	public void saveFile(String textBoxData) {

		Task<Object> task = new Task<Object>() {

			@Override
			public Object call() {
				byte[] data = textBoxData.getBytes();
				try (FileOutputStream fout = new FileOutputStream(saveFile)) {
					for (int i = 0; i < data.length; i++) {
						if (!appThread.isAlive())
							System.exit(0);
						fout.write(data[i]);
						Thread.sleep(10, 1);
						String workDone = ((i + 1) * 100) / data.length + "%";
						updateMessage(workDone);
						updateProgress(i + 1, data.length);
					}
					tempString = textBoxData;
				} catch (IOException | InterruptedException exception) {
					String headerMessage = "Error while saving the file: " + saveFile.getName();
					displayErrorAlert(exception, headerMessage);
				}
				return null;
			}
		};

		pb.progressProperty().unbind();
		pb.progressProperty().bind(task.progressProperty());

		task.messageProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> ob, String oldVal, String newVal) {
				workDone.setText(newVal);
			}
		});

		new Thread(task).start();
	}

	public void displaySaveFileDialog(String textBoxData, Stage primaryStage) {
		File tempFile = saveFile;
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop"));
		fileChooser.setTitle("Save");
		fileChooser.setInitialFileName("NewDoc");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Text files (*.txt)", "*.txt"));
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Word Doc (*.doc)", "*.doc"));
		tempFile = fileChooser.showSaveDialog(primaryStage);
		if (tempFile != null) {
			saveFile = tempFile;
			saveFile(textBoxData);

		}
	}

	public void displayErrorAlert(Exception exception, String headerMessage) {
		Alert noFile = new Alert(AlertType.ERROR);
		noFile.setTitle("Error");
		noFile.setHeaderText(headerMessage);
		noFile.setContentText("Press OK to return.");

		StringWriter stringWriter = new StringWriter();
		PrintWriter pw = new PrintWriter(stringWriter);
		exception.printStackTrace(pw);

		Label errorCause = new Label("Following is the exception cause");
		TextArea errorContent = new TextArea(stringWriter.toString());
		errorContent.setEditable(false);

		GridPane gridPane = new GridPane();
		// expContent.setMaxWidth(Double.MAX_VALUE);
		gridPane.add(errorCause, 0, 0);
		gridPane.add(errorContent, 0, 1);
		noFile.getDialogPane().setExpandableContent(gridPane);

		noFile.show();
	}

	public void displayAboutInfo() {

		aboutInfo = new Alert(AlertType.INFORMATION);
		aboutInfo.setHeaderText("Release Version 1.0");
		FlowPane pane = new FlowPane(10, 10);
		pane.getChildren().addAll(new Label("Developed by:"), aboutMe);
		aboutInfo.getDialogPane().setContent(pane);
		aboutInfo.show();
	}

	public void displayNoTextAlert() {
		Alert noText = new Alert(AlertType.ERROR);
		noText.setTitle("Error");
		noText.setHeaderText("You need to write some text to save it in a file.");
		noText.setContentText("Press OK to go back and write some text");
		noText.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
