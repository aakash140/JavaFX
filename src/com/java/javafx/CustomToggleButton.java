package com.java.javafx;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class CustomToggleButton extends Application {

	Image powerOn;
	Image powerOff;
	Image bulbOn;
	Image bulbOff;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Toggle Button");
		BorderPane rootNode = new BorderPane();
		Scene aScene = new Scene(rootNode, 300, 200);
		primaryStage.setScene(aScene);

		FlowPane flowPane = new FlowPane(10, 10);
		flowPane.setAlignment(Pos.CENTER);

		File file1 = new File("powerOn.png");
		File file2 = new File("powerOff.png");
		File file3 = new File("BulbOn.png");
		File file4 = new File("BulbOff.png");
		try (FileInputStream fin1 = new FileInputStream(file1);
				FileInputStream fin2 = new FileInputStream(file2);
				FileInputStream fin3 = new FileInputStream(file3);
				FileInputStream fin4 = new FileInputStream(file4);) {
			powerOn = new Image(fin1);
			powerOff = new Image(fin2);
			bulbOn = new Image(fin3);
			bulbOff = new Image(fin4);
		} catch (IOException exception) {
			exception.printStackTrace();
		}

		ImageView imgView = new ImageView(powerOff);
		ImageView imgView1 = new ImageView(bulbOff);
		ToggleButton switchButton = new ToggleButton("Off", imgView);
		Button bulb = new Button("Bulb", imgView1);
		switchButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		bulb.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		Label buttonState = new Label();
		Label instruction = new Label("Press the button to switch on the appliance");

		rootNode.setBottom(buttonState);
		rootNode.setTop(instruction);
		rootNode.setCenter(flowPane);

		switchButton.setOnAction((ae) -> {
			if (switchButton.isSelected()) {
				switchButton.setText("On");
				imgView.setImage(powerOn);
				imgView1.setImage(bulbOn);
				buttonState.setText("Appliance is turned on");
			} else {
				switchButton.setText("Off");
				imgView.setImage(powerOff);
				imgView1.setImage(bulbOff);
				buttonState.setText("Appliance is turned off");
			}
		});
		flowPane.getChildren().addAll(switchButton, bulb);
		primaryStage.show();
	}
}