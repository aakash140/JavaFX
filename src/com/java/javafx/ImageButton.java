package com.java.javafx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class ImageButton extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Image Button");
		Label instruction = new Label("Choose an option");
		Label response = new Label();
		BorderPane rootNode = new BorderPane();
		Scene aScene = new Scene(rootNode, 360, 360);
		primaryStage.setScene(aScene);

		FlowPane flowPane = new FlowPane(10, 10);
		flowPane.setAlignment(Pos.CENTER);
		Button forward = new Button("Forward", new ImageView("forward.png"));
		Button backward = new Button("Back", new ImageView("backward.png"));
		Button reload = new Button("Reload", new ImageView("reload.gif"));
		forward.setContentDisplay(ContentDisplay.TOP);
		backward.setContentDisplay(ContentDisplay.TOP);
		reload.setContentDisplay(ContentDisplay.TOP);
		flowPane.getChildren().addAll(backward, forward, reload);

		rootNode.setTop(instruction);
		rootNode.setCenter(flowPane);
		rootNode.setBottom(response);

		forward.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ae) {
				response.setText("Navigating to next page...");
			}
		});

		backward.setOnAction((ae) -> {
			response.setText("Navigating to previous page....");
		});

		reload.setOnAction((ae) -> {
			response.setText("Reloading....");
		});

		primaryStage.show();
	}
}