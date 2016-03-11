package com.java.javafx;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class ImageOperations extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Images");
		FlowPane rootNode = new FlowPane();
		rootNode.setAlignment(Pos.CENTER);
		Scene aScene = new Scene(rootNode, 400, 400);
		primaryStage.setScene(aScene);
		Path path = Paths.get("ReceiptLogo.jpg");
		Image img = null;
		try (FileInputStream fin = new FileInputStream(path.toFile())) {
			img = new Image(fin);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		ImageView imgView = new ImageView(img);
		rootNode.getChildren().add(imgView);
		primaryStage.show();
	}
}
