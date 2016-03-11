package com.java.javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class HelloWorld extends Application {

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("First App");
		Button newButton = new Button("Click Here");
		StackPane rootPane = new StackPane();
		rootPane.getChildren().add(newButton);
		Scene newScene = new Scene(rootPane, 300, 200);
		primaryStage.setScene(newScene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		System.out.println("Launching JavaFx App");
		launch(args);
	}
}
