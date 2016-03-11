package com.java.javafx;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CustomProgressBar extends Application {

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("ProgressBar");
		FlowPane rootNode = new FlowPane(10, 10);
		Scene scene = new Scene(rootNode, 500, 200);
		primaryStage.setScene(scene);

		Text text = new Text();
		text.setFont(new Font(12));

		Text progress = new Text();
		progress.setFont(new Font(12));
		progress.setFill(Color.CADETBLUE);

		ToggleButton start = new ToggleButton("Start");

		ProgressBar pb = new ProgressBar(0);

		pb.indeterminateProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> ob, Boolean oldVal, Boolean newVal) {
				if (newVal) {
					text.setText("Calculating");
				} else {
					text.setText("Working");
				}
			}
		});

		pb.progressProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> ob, Number oldVal, Number newVal) {

				if (newVal.floatValue() == 1) {
					text.setText("Complete");
				}
			}
		});

		start.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent ae) {
				start.setDisable(true);
				Task<Object> task = getTask();
				pb.progressProperty().unbind();
				pb.progressProperty().bind(task.progressProperty());

				task.messageProperty().addListener(new ChangeListener<String>() {

					@Override
					public void changed(ObservableValue<? extends String> ob, String oldVal, String newVal) {
						progress.setText(newVal);
					}
				});

				new Thread(task).start();
			}
		});

		rootNode.getChildren().addAll(start, pb, text, new Separator(Orientation.VERTICAL), progress);
		rootNode.setAlignment(Pos.CENTER);
		primaryStage.show();
	}

	public Task<Object> getTask() {

		return new Task<Object>() {

			@Override
			public Object call() throws Exception {
				final int max = 10;
				for (int i = 1; i <= max; i++) {
					Thread.sleep(500);
					String workDone = i * 10 + "%";
					updateMessage(workDone);
					updateProgress(i, max);
				}
				return null;
			}
		};
	}

	public static void main(String[] args) {
		launch(args);
	}
}