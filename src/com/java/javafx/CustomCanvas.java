package com.java.javafx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class CustomCanvas extends Application {
	int colorIndex = 0;
	double fontSize = 10.0;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		Color[] colorCard = new Color[] { Color.INDIANRED, Color.MEDIUMVIOLETRED, Color.CORNFLOWERBLUE, Color.PLUM };
		primaryStage.setTitle("Canvas");
		FlowPane rootNode = new FlowPane(10, 10);
		rootNode.setAlignment(Pos.CENTER);
		Scene aScene = new Scene(rootNode, 400, 400);
		primaryStage.setScene(aScene);
		Canvas theCanvas = new Canvas(100, 100);
		GraphicsContext grphContext = theCanvas.getGraphicsContext2D();
		Button changeColor = new Button("Refresh");
		Button fontUp = new Button("Font +");
		Button fontDown = new Button("Font -");
		changeColor.setOnAction((ae) -> {
			grphContext.setStroke(colorCard[colorIndex]);
			grphContext.setFill(colorCard[colorIndex]);

			grphContext.strokeLine(10, 20, 30, 40);
			grphContext.fillText("This is JAVAFX", 10, 10);
			grphContext.fillRect(50, 50, 50, 25);

			colorIndex++;
			if (colorIndex == colorCard.length)
				colorIndex = 0;
		});

		fontUp.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ae) {
				grphContext.setFont(new Font(fontSize + 0.05));
				grphContext.fillText("This is JAVAFX", 10, 10);
			}
		});

		fontDown.setOnAction((ae) -> {
			grphContext.setFont(new Font(fontSize - 2.0));
			grphContext.fillText("This is JAVAFX", 30, 85);
		});

		grphContext.setStroke(Color.BLACK);
		grphContext.setFill(Color.BLACK);
		grphContext.setFont(new Font(fontSize));
		grphContext.strokeLine(10, 20, 30, 40);
		grphContext.fillText("This is JAVAFX", 10, 10);
		grphContext.fillRect(50, 50, 50, 25);
		grphContext.fillOval(80, 90, 10, 10);

		rootNode.getChildren().addAll(theCanvas, changeColor, fontUp, fontDown);
		primaryStage.show();
	}
}
