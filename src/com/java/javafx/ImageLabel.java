package com.java.javafx;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class ImageLabel extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Image Lable");
		FlowPane rootNode = new FlowPane();
		rootNode.setAlignment(Pos.CENTER);
		Scene aScene = new Scene(rootNode, 200, 200);
		primaryStage.setScene(aScene);
		File file = new File("./busy.gif");
		Image img = null;
		try (FileInputStream fin = new FileInputStream(file)) {
			img = new Image(fin);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		ImageView imgView = new ImageView(img);
		Label label = new Label("Loading...", imgView);
		label.setContentDisplay(ContentDisplay.TOP);
		rootNode.getChildren().add(label);
		primaryStage.show();
	}
}