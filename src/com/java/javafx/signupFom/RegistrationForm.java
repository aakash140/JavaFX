package com.java.javafx.signupFom;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class RegistrationForm extends Application {

	Random random = new Random();
	int num;
	InnerShadow shadower = new InnerShadow();

	/**
	 * @param primaryStage
	 * @throws Exception
	 */
	/**
	 * @param primaryStage
	 */
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Registration Form");

		BorderPane rootNode = new BorderPane();
		Scene aScene = new Scene(rootNode, 600, 650);
		primaryStage.setScene(aScene);

		Label instruction = new Label("SignUp Form");
		instruction.setFont(new Font("Arial Rounded MT Bold", 14));
		Label response = new Label();
		rootNode.setTop(instruction);
		rootNode.setBottom(response);

		Label nameLabel = new Label("  Name:");
		nameLabel.setFont(new Font("Arial Rounded MT Bold", 12));
		TextField name = new TextField();
		Tooltip nametip = new Tooltip(STYLESHEET_MODENA);
		nametip.setText("Enter your First name and Last name separated by a space.");
		name.setTooltip(nametip);
		name.setPromptText("First Name and Last Name");

		Label genderLabel = new Label("  Gender:");
		genderLabel.setFont(new Font("Arial Rounded MT Bold", 12));
		RadioButton male = new RadioButton("Male");
		RadioButton female = new RadioButton("Female");
		ToggleGroup genderGroup = new ToggleGroup();
		male.setToggleGroup(genderGroup);
		female.setToggleGroup(genderGroup);
		male.setSelected(true);

		Label passwordLabel = new Label("  Password:");
		passwordLabel.setFont(new Font("Arial Rounded MT Bold", 12));
		TextField password = new PasswordField();
		Tooltip passwordtip = new Tooltip(STYLESHEET_CASPIAN);
		passwordtip.setText(">= 8 characters; a-z, A-Z, 0-9, ! @ # $ % etc. ");
		password.setTooltip(passwordtip);
		password.setPromptText("Atleast 8 characters long: alphabets,numbers,symbols");

		Label emailIDLabel = new Label("  E-Mail:");
		emailIDLabel.setFont(new Font("Arial Rounded MT Bold", 12));
		TextField emailID = new TextField();
		emailID.setText("@");

		Map<String, Image> captchaMap = new HashMap<>();
		String[] captchaImgtext = new String[] { "43wFFV4", "cp5dFAn", "FHdsVIpu", "L7T2n8F", "esGTpL88" };
		for (int i = 0; i <= 4; i++) {
			try (FileInputStream fin = new FileInputStream(new File("cap" + (i + 1) + ".jpg"))) {
				captchaMap.put(captchaImgtext[i], new Image(fin));
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
		num = random.nextInt(5);
		ImageView imgView = new ImageView(captchaMap.get(captchaImgtext[num]));
		TextField captchaText = new TextField();
		captchaText.setMaxWidth(145);
		Tooltip captchatip = new Tooltip("Captcha text is case sensitive");
		captchaText.setTooltip(captchatip);
		Button reloadCaptcha = new Button("Reload", new ImageView("reload.png"));
		reloadCaptcha.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

		CheckBox agreement = new CheckBox("I have read all the conditions\n and agree to adhere.");

		Button submit = new Button("Submit");
		submit.setDisable(true);

		agreement.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ae) {
				if (agreement.isSelected())
					submit.setDisable(false);
				else
					submit.setDisable(true);
			}
		});

		submit.setOnAction((ae) -> {
			if (submit.getText().equals("Start Shopping")) {

				Alert redirectWarnig = new Alert(AlertType.CONFIRMATION);
				redirectWarnig.setTitle("Confirm");
				redirectWarnig.setHeaderText("You will be redirected to www.flipkart.com");
				redirectWarnig.setContentText("Press OK to continue, Cancel otherwise.");
				redirectWarnig.setGraphic(new ImageView("flipkart.jpg"));
				Optional<ButtonType> result = redirectWarnig.showAndWait();
				if (result.isPresent() && result.get() == ButtonType.OK) {
					try {
						Desktop.getDesktop().browse(new URL("http://www.flipkart.com/").toURI());
						redirectWarnig.close();

					} catch (IOException | URISyntaxException exception) {
						exception.printStackTrace();
					}
				} else if (result.isPresent() && result.get() == ButtonType.CANCEL) {
					response.setText("50% OFF for new users. Visit www.flipkart.com and use NEW50 PROMOCODE now.");
					response.setTextFill(Color.LIGHTSLATEGRAY);
					response.setFont(new Font("Arial Rounded MT Bold", 12));
				}

			} else {
				shadower.setRadius(10.0);
				shadower.setColor(Color.RED);
				response.setTextFill(Color.RED);
				String nameInput = name.getText();
				int nameLength = nameInput.length();

				String passwordInput = password.getText();
				int passwordLength = passwordInput.length();

				String emailInput = emailID.getText();
				int emailLength = emailInput.length();

				String captchaInput = captchaText.getText();
				int captchaLength = captchaInput.length();

				if (nameLength == 0 && passwordLength == 0 && emailLength < 2 && captchaLength == 0) {
					Alert emptyFields = new Alert(AlertType.ERROR);
					emptyFields.setTitle("ERROR");
					emptyFields.setHeaderText("Invalid Data");
					emptyFields.setContentText(
							"We need your personal details to sign you up on our website.\nPress OK and fill in the information.");
					emptyFields.show();
					// response.setText("You forgot something!!! (Hint: All the
					// fields)");

					name.setEffect(shadower);
					password.setEffect(shadower);
					emailID.setEffect(shadower);
					name.requestFocus();

				} else {
					if (nameLength == 0) {
						// response.setText("Anonymous doesn't sound a good
						// name. Please tell us your name.");

						Alert emptyFields = new Alert(AlertType.ERROR);
						emptyFields.setTitle("ERROR");
						emptyFields.setHeaderText("Invalid Data");
						emptyFields.setContentText(
								"What do you want us to call you? \nPress OK and enter your name in the Name field.");
						emptyFields.show();

						name.setEffect(shadower);
						name.requestFocus();
					} else if (passwordLength < 8) {
						// response.setText("We want your account to be secure.
						// For that you need to enter a valid password.");
						Alert invalidField = new Alert(AlertType.ERROR);
						invalidField.setTitle("ERROR");
						invalidField.setHeaderText("Invalid Data");
						invalidField.setContentText(
								"We want your account to be secure.\nFor that you need to enter a valid password.");
						invalidField.show();

						password.clear();
						password.setEffect(shadower);
						password.requestFocus();
						name.setEffect(null);
					} else if (emailLength < 2 || emailInput.indexOf("@") < 0 || (emailInput.indexOf(".") < 0)) {
						// response.setText("You may miss out our great deals
						// and offers. Tell us your valid email id to get
						// maximum.");

						Alert invalidField = new Alert(AlertType.ERROR);
						invalidField.setTitle("ERROR");
						invalidField.setHeaderText("Invalid Data");
						invalidField.setContentText(
								"You may miss out our great deals and offers.\nTell us your valid email id to get maximum.");
						invalidField.show();

						emailID.setEffect(shadower);
						emailID.requestFocus();
						name.setEffect(null);
						password.setEffect(null);
					} else if (!captchaImgtext[num].equals(captchaInput)) {
						// response.setText("Captcha Text doesn't match the text
						// in Captcha Image.");

						Alert invalidField = new Alert(AlertType.ERROR);
						invalidField.setTitle("ERROR");
						invalidField.setHeaderText("Invalid Data");
						invalidField.setContentText("Captcha Text doesn't match the text in Captcha Image.");
						invalidField.show();

						captchaText.setEffect(shadower);
						captchaText.requestFocus();
						name.setEffect(null);
						password.setEffect(null);
						emailID.setEffect(null);
					} else {
						response.setText("Thank You!! " + name.getText() + ". You can now proceed with the shopping.");
						response.setFont(new Font("Arial Rounded MT Bold", 14));
						response.setTextFill(Color.BLACK);

						submit.setText("Start Shopping");
						shadower.setColor(Color.LIGHTGREEN);
						shadower.setRadius(15.0);
						submit.setEffect(shadower);

						name.setDisable(true);
						password.setDisable(true);
						emailID.setDisable(true);
						male.setDisable(true);
						female.setDisable(true);
						agreement.setDisable(true);
						reloadCaptcha.setDisable(true);
						captchaText.setDisable(true);
					}
				}
			}
		});

		reloadCaptcha.setOnAction((ae) -> {
			num = random.nextInt(5);
			imgView.setImage(captchaMap.get(captchaImgtext[num]));
			captchaText.clear();
		});

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER_LEFT);
		grid.getColumnConstraints().add(0, new ColumnConstraints(175));
		grid.getColumnConstraints().add(1, new ColumnConstraints(300));
		grid.getColumnConstraints().add(2, new ColumnConstraints(20));

		for (int i = 0; i < 10; i++) {
			if (i != 9)
				grid.getRowConstraints().add(i, new RowConstraints(25));
			else
				grid.getRowConstraints().add(i, new RowConstraints(10));
		}

		grid.add(nameLabel, 0, 0);
		grid.add(name, 1, 0);

		grid.add(genderLabel, 0, 2);
		grid.add(male, 1, 2);
		grid.add(female, 1, 3);

		grid.add(passwordLabel, 0, 4);
		grid.add(password, 1, 4);

		grid.add(emailIDLabel, 0, 6);
		grid.add(emailID, 1, 6);

		grid.add(imgView, 0, 8);
		grid.add(captchaText, 0, 10);
		grid.add(reloadCaptcha, 1, 8);

		grid.add(agreement, 0, 12, 1, 11);

		grid.add(submit, 3, 15);

		rootNode.setCenter(grid);

		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);

	}

}
