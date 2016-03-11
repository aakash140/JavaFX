package com.java.javafx.list;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GroceryList extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Groccery List");

		Label instruction = new Label("Choose your grocery from the list.");
		instruction.setFont(new Font("Arial Rounded MT Bold", 15));
		Label cartLabel = new Label();

		Button addToCart = new Button("Add to Cart", new ImageView("cart.png"));

		BorderPane rootNode = new BorderPane();
		rootNode.setTop(instruction);
		rootNode.setBottom(cartLabel);

		Scene aScene = new Scene(rootNode, 400, 400);
		primaryStage.setScene(aScene);

		ObservableList<String> items = FXCollections.observableArrayList("Potato", "Tomato", "Onion", "Cabbage",
				"Apple", "Banana", "Grapes", "Oatmeal", "Corn Flakes", "Brown Bread", "Whole Grain Bread",
				"Orange Juice", "Protein Shake");
		ListView<String> groceryList = new ListView<>(items);
		groceryList.setPrefSize(130, 140);

		// To allow multiple selection in a list
		groceryList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		MultipleSelectionModel<String> selModel = groceryList.getSelectionModel();

		selModel.selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> changed, String oldVal, String newVal) {
				ObservableList<String> selectedItems = selModel.getSelectedItems();
				GroceryLinkedList linkedList = new GroceryLinkedList();
				for (String item : selectedItems)
					linkedList.add(item);
				cartLabel.setText("Selected Items: " + linkedList.printList());
				cartLabel.setTextFill(Color.BLACK);
			}
		});

		addToCart.setOnAction((ae) -> {
			ObservableList<String> selectedItems = selModel.getSelectedItems();
			if (selectedItems.size() > 0) {
				String cartItems = "";
				int i = 0;
				for (String item : selectedItems)
					cartItems += ++i + ". " + item + "\n";
				cartLabel.setText("Items in your cart:\n" + cartItems);
				cartLabel.setTextFill(Color.BLACK);
			} else {
				cartLabel.setText("Please select atleast 1 item");
				cartLabel.setTextFill(Color.RED);
			}

		});

		GridPane gridPane = new GridPane();
		// gridPane.setGridLinesVisible(true);
		gridPane.getColumnConstraints().add(0, new ColumnConstraints(130));
		gridPane.getColumnConstraints().add(1, new ColumnConstraints(25));
		gridPane.setAlignment(Pos.CENTER);
		gridPane.add(groceryList, 0, 0);
		gridPane.add(addToCart, 5, 25);

		rootNode.setCenter(gridPane);

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}