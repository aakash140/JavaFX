package com.java.javafx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ButtonAction extends Application {

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Button Action");
		Label instruction = new Label("Press any Key.");
		Label response = new Label();
		Button alpha = new Button("Alpha");
		Button beta = new Button("Beta");

		alpha.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ae) {
				response.setText("You pressed Alpha");
			}
		});

		beta.setOnAction((ae) -> response.setText("You pressed Beta"));
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(new Label("Button Operations"));
		borderPane.setBottom(instruction);

		GridPane rootNode = new GridPane();
		rootNode.getColumnConstraints().add(new ColumnConstraints(150));
		// rootNode.add(instruction, 0, 0);
		rootNode.add(response, 0, 20);
		rootNode.add(alpha, 10, 10);
		rootNode.add(beta, 15, 10);

		borderPane.setCenter(rootNode);
		primaryStage.setScene(new Scene(borderPane, 400, 100));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}