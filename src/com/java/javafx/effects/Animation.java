package com.java.javafx.effects;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class Animation extends Application {
	double glowLevel = 0.0;
	boolean shadowFlag = true;
	double angle = 90.0;

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Animation");
		FlowPane rootNode = new FlowPane();
		rootNode.setAlignment(Pos.CENTER);
		Scene aScene = new Scene(rootNode, 400, 400);
		primaryStage.setScene(aScene);

		Button rotate = new Button("Rotate");
		Rotate rotation = new Rotate();
		rotate.getTransforms().add(rotation);

		Button glow = new Button("Glow");
		Glow glower = new Glow(0.0);
		glow.setEffect(glower);

		Button shadow = new Button("Shadow");
		InnerShadow shadower = new InnerShadow(10.0, Color.RED);
		shadow.setEffect(shadower);

		Button lighting = new Button("Lighting");
		Light.Distant light = new Light.Distant();
		light.setColor(Color.TAN);
		Lighting lightSource = new Lighting(light);
		lighting.setEffect(lightSource);

		Button reflection = new Button("Reflection");
		Reflection reflector = new Reflection();
		reflection.setEffect(reflector);

		rotate.setOnAction((ae) -> {
			rotation.setAngle(angle);
			rotation.setPivotX(rotate.getWidth() / 2);
			rotation.setPivotY(rotate.getHeight() / 2);
			angle += angle + 1;
		});

		glow.setOnAction((ae) -> {
			if (glowLevel == 1.0)
				glowLevel = 0.0;
			glower.setLevel(glowLevel + 0.3);

		});

		shadow.setOnAction((ae) -> {
			shadowFlag = !shadowFlag;
			shadow.setEffect(shadowFlag == true ? shadower : null);
		});

		rootNode.getChildren().addAll(lighting, rotate, glow, shadow, reflection);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
