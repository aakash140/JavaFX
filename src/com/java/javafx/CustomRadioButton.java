package com.java.javafx;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CustomRadioButton extends Application {

	Image img;

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Radio Buttons");
		BorderPane rootNode = new BorderPane();
		Scene aScene = new Scene(rootNode, 500, 500);
		primaryStage.setScene(aScene);

		Label instruction = new Label("SELECT DONATION OPTIONS");
		Label response = new Label();
		Separator separator = new Separator();
		separator.setPrefWidth(200);

		File file = new File("done.png");
		try (FileInputStream fin = new FileInputStream(file);) {
			img = new Image(fin);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		ImageView imgView = new ImageView();
		ToggleButton confirmPayment = new ToggleButton("Confirm Payment", imgView);

		ToggleGroup amount = new ToggleGroup();
		ToggleGroup paymentMode = new ToggleGroup();

		RadioButton amount200 = new RadioButton("200");
		RadioButton amount400 = new RadioButton("400");
		amount200.setToggleGroup(amount);
		amount400.setToggleGroup(amount);

		RadioButton cod = new RadioButton("COD");
		RadioButton netBanking = new RadioButton("Net Banking");
		RadioButton creditCard = new RadioButton("Credit Card");
		cod.setToggleGroup(paymentMode);
		netBanking.setToggleGroup(paymentMode);
		creditCard.setToggleGroup(paymentMode);

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.add(amount200, 20, 20);
		grid.add(amount400, 25, 20);
		grid.add(cod, 20, 30);
		grid.add(netBanking, 25, 30);
		grid.add(creditCard, 40, 30);
		grid.add(separator, 20, 55, 45, 40);
		grid.add(confirmPayment, 45, 100);
		amount200.setOnAction((ae) -> response.setText("Amount: Rs.200/-"));

		amount400.setOnAction((ae) -> response.setText("Amount: Rs.400/-"));

		paymentMode.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> changed, Toggle oldVal, Toggle newVal) {
				RadioButton rb = (RadioButton) newVal;
				response.setText("Payment Mode: " + rb.getText());
			}
		});

		confirmPayment.setOnAction((ae) -> {
			RadioButton rb = (RadioButton) amount.getSelectedToggle();
			response.setText("Payment of Rs. " + rb.getText() + " has been made successfuly.");
			confirmPayment.setText("Done");
			imgView.setImage(img);
		});
		amount200.setSelected(true);
		// amount200.fire();
		// cod.setSelected(true);
		// cod.fire();

		rootNode.setTop(instruction);
		rootNode.setBottom(response);
		rootNode.setCenter(grid);

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}