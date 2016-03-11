package com.java.javafx.directoryTree;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DirectoryTree extends Application {

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Directoy Structure");
		FlowPane rootNode = new FlowPane();
		Scene aScene = new Scene(rootNode, 500, 500);
		primaryStage.setScene(aScene);

		Label response = new Label();
		TreeItem<String> rootDirectory = new TreeItem<>("Computer");

		TreeItem<String> programFiles = new TreeItem<>("Program Files");
		programFiles.getChildren().add(new TreeItem<String>("CCleaner"));
		programFiles.getChildren().add(new TreeItem<String>("Notepad++"));
		programFiles.getChildren().add(new TreeItem<String>("uTorrent"));

		TreeItem<String> cDrive = new TreeItem<>("C:\\");
		cDrive.getChildren().add(new TreeItem<String>("Java"));
		cDrive.getChildren().add(programFiles);
		cDrive.getChildren().add(new TreeItem<String>("Windows"));

		TreeItem<String> dDrive = new TreeItem<>("D:\\");
		dDrive.getChildren().add(new TreeItem<String>("Images"));
		dDrive.getChildren().add(new TreeItem<String>("Movies"));
		dDrive.getChildren().add(new TreeItem<String>("Softwares"));

		rootDirectory.getChildren().add(cDrive);
		rootDirectory.getChildren().add(dDrive);

		TreeView<String> dirView = new TreeView<>(rootDirectory);

		rootNode.getChildren().addAll(dirView, response);

		MultipleSelectionModel<TreeItem<String>> selModel = dirView.getSelectionModel();
		selModel.selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {

			@Override
			public void changed(ObservableValue<? extends TreeItem<String>> changed, TreeItem<String> oldVal,
					TreeItem<String> newVal) {
				if (newVal != null) {
					String path = newVal.getValue();
					TreeItem<String> temp = newVal.getParent();
					while (temp != null) {
						path = temp.getValue() + " >" + path;
						temp = temp.getParent();
					}
					response.setText("Your Selection: " + path);
					response.setTextFill(Color.DARKCYAN);
				}
			}
		});

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
