package com.java.javafx.alarm;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Used checkbox and combo box.
 *
 * @author Aakash2.Gupta
 *
 */
public class Alarm extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Alarm");

		Label instruction = new Label("Choose Alarm Timings");
		Label response = new Label();
		Button setAlarm = new Button("Set Alarm");

		BorderPane rootNode = new BorderPane();
		rootNode.setTop(instruction);
		rootNode.setBottom(response);

		Scene aScene = new Scene(rootNode, 550, 300);
		primaryStage.setScene(aScene);

		CheckBox monday = new CheckBox("Mondays");
		CheckBox tuesday = new CheckBox("Tuesdays");
		CheckBox wednesday = new CheckBox("Wednesdays");
		CheckBox thursday = new CheckBox("Thursdays");
		CheckBox friday = new CheckBox("Fridays");
		CheckBox saturday = new CheckBox("Saturdays");
		CheckBox sunday = new CheckBox("Sundays");

		List<CheckBox> checkBoxList = new ArrayList<>();
		checkBoxList.add(monday);
		checkBoxList.add(tuesday);
		checkBoxList.add(wednesday);
		checkBoxList.add(thursday);
		checkBoxList.add(friday);
		checkBoxList.add(saturday);
		checkBoxList.add(sunday);

		Separator separator = new Separator(Orientation.VERTICAL);
		separator.setPrefHeight(100);

		ObservableList<String> hourList = FXCollections.observableArrayList("5", "6", "7", "8");
		ComboBox<String> hour = new ComboBox<>(hourList);
		hour.setValue("5");

		ObservableList<String> minuteList = FXCollections.observableArrayList("00", "15", "30", "45");
		ComboBox<String> minute = new ComboBox<>(minuteList);
		minute.setValue("00");

		ObservableList<String> meridianList = FXCollections.observableArrayList("AM", "PM");
		ComboBox<String> meridian = new ComboBox<>(meridianList);
		meridian.setValue("AM");

		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.add(monday, 5, 20);
		gridPane.add(tuesday, 5, 22);
		gridPane.add(wednesday, 5, 24);
		gridPane.add(thursday, 5, 26);
		gridPane.add(friday, 5, 28);
		gridPane.add(saturday, 5, 30);
		gridPane.add(sunday, 5, 32);

		gridPane.add(separator, 10, 15, 10, 20);

		gridPane.add(hour, 30, 24);
		gridPane.add(minute, 32, 24);
		gridPane.add(meridian, 34, 24);

		gridPane.add(setAlarm, 32, 50);

		setAlarm.setOnAction((ae) -> {
			String alarmTime = "You will be alarmed at " + hour.getValue() + ":" + minute.getValue() + " "
					+ meridian.getValue() + '\7';

			String daysGroup = isWeekdaysSelected(checkBoxList) ? " on Weekdays"
					: isWeekendSelected(checkBoxList) ? " every Weekend" : "";

			if (daysGroup.equals("")) {
				DaysLinkedList daysList = new DaysLinkedList();
				for (CheckBox day : checkBoxList) {
					if (day.isSelected())
						daysList.add(day.getText());
				}

				if (daysList.size() == 0)
					response.setText(alarmTime + " tomorrow.");
				else if (daysList.size() < 7)
					response.setText(alarmTime + " on " + daysList.printList());

				else
					response.setText(alarmTime + " everyday.");
			} else
				response.setText(alarmTime + daysGroup);
		});

		rootNode.setCenter(gridPane);
		primaryStage.show();
	}

	public boolean isWeekdaysSelected(List<CheckBox> daysList) {
		int days = 0;
		for (int i = 0; i <= 4; i++) {
			if (daysList.get(i).isSelected())
				days++;
		}

		if (days == 5) {
			if (daysList.get(5).isSelected() || daysList.get(6).isSelected())
				return false;
			else
				return true;
		} else
			return false;
	}

	public boolean isWeekendSelected(List<CheckBox> daysList) {
		int days = 0;
		if (daysList.get(5).isSelected() && daysList.get(6).isSelected())
			days = 2;
		if (days == 2) {
			for (int i = 0; i <= 4; i++) {
				if (daysList.get(i).isSelected())
					return false;
			}
			return true;
		} else
			return false;
	}

	public static void main(String[] args) {
		launch(args);
	}
}