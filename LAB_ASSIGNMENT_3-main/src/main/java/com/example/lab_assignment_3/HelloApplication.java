package com.example.lab_assignment_3;

import Models.Data;
import Models.Manager;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    Manager manager;
    public HelloApplication(){
        this.manager = new Manager();
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Lab Assignment");
        GridPane formLayout = new GridPane();
        formLayout.setPadding(new Insets(20));
        formLayout.setHgap(15);
        formLayout.setVgap(15);

        Label nameLabel = new Label("Enter Name:");
        TextField nameField = new TextField();
        Label idLabel = new Label("Enter ID:");
        TextField idField = new TextField();

        Label genderLabel = new Label("Gender:");
        ToggleGroup genderGroup = new ToggleGroup();
        RadioButton maleRadio = new RadioButton("Male");
        RadioButton femaleRadio = new RadioButton("Female");
        RadioButton otherRadio = new RadioButton("Other");
        maleRadio.setToggleGroup(genderGroup);
        femaleRadio.setToggleGroup(genderGroup);
        otherRadio.setToggleGroup(genderGroup);
        HBox genderBox = new HBox(15, maleRadio, femaleRadio, otherRadio);

        Label provinceLabel = new Label("Province:");
        ComboBox<String> provinceList = new ComboBox<>();
        provinceList.getItems().addAll("Punjab", "Sindh", "KPK", "Balochistan", "Gilgit-Baltistan");
        provinceList.setMaxHeight(40);

        Label dobLabel = new Label("Select Date of Birth:");
        DatePicker dobPicker = new DatePicker();

        Label response = new Label("");
        response.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: darkblue;");

        formLayout.add(nameLabel, 0, 0);
        formLayout.add(nameField, 1, 0);
        formLayout.add(idLabel, 0, 1);
        formLayout.add(idField, 1, 1);
        formLayout.add(genderLabel, 0, 2);
        formLayout.add(genderBox, 1, 2);
        formLayout.add(provinceLabel, 0, 3);
        formLayout.add(provinceList, 1, 3);
        formLayout.add(dobLabel, 0, 4);
        formLayout.add(dobPicker, 1, 4);
        formLayout.add(response, 0, 5, 2, 1);
        GridPane.setMargin(response, new Insets(10, 0, 0, 0));

        VBox buttonLayout = new VBox(15);
        buttonLayout.setPadding(new Insets(20));
        buttonLayout.setAlignment(Pos.CENTER);

        Button newButton = new Button("Submit");
        newButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000;");
        newButton.setPrefWidth(120);
        newButton.setOnAction(e -> {
            String id = idField.getText();
            String name = nameField.getText();
            Toggle selectedGender = genderGroup.getSelectedToggle();
            String province = provinceList.getSelectionModel().getSelectedItem();
            String date = dobPicker.getValue() != null ? dobPicker.getValue().toString() : null;
            if (id != null && !id.isEmpty() &&
                    name != null && !name.isEmpty() &&
                    selectedGender != null &&
                    province != null &&
                    date != null) {
                manager.addData(new Data(id, name, ((RadioButton) selectedGender).getText(), province, date));
                response.setText("Data Added Successfully!");
            } else {
                response.setText("Please fill all fields correctly.");
            }
        });

        Button deleteButton = new Button("Delete Entry");
        deleteButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000;");
        deleteButton.setPrefWidth(120);
        deleteButton.setDisable(true);

        Button restoreButton = new Button("Clear Fields");
        restoreButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000;");
        restoreButton.setPrefWidth(120);
        restoreButton.setOnAction(e -> {
            idField.setText("");
            nameField.setText("");
            dobPicker.setValue(null);
            genderGroup.selectToggle(null);
            provinceList.setValue("");
        });

        Button findPreButton = new Button("Find Previous");
        findPreButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000;");
        findPreButton.setPrefWidth(120);
        findPreButton.setOnAction(e -> {
            String id = idField.getText();
            if (id != null && !id.isEmpty()) {
                Data data = manager.searchData(id);
                if (data != null) {
                    StringBuilder dataString = new StringBuilder();
                    dataString.append("Name: " + data.getName()).append("\n")
                            .append("Gender: " + data.getGender()).append("\n")
                            .append("Province: " + data.getProvince()).append("\n")
                            .append("Date of Birth: " + data.getDate());
                    response.setText(dataString.toString());
                } else {
                    response.setText("No data found for this ID.");
                }
            } else {
                response.setText("Please Enter ID.");
            }
        });

        Button exitButton = new Button("Exit");
        exitButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000;");
        exitButton.setPrefWidth(120);
        exitButton.setOnAction(e -> primaryStage.close());

        buttonLayout.getChildren().addAll(newButton, deleteButton, restoreButton, findPreButton, exitButton);

        HBox mainLayout = new HBox(50, formLayout, buttonLayout);
        mainLayout.setPadding(new Insets(20));

        Scene scene = new Scene(mainLayout, 650, 450);
        primaryStage.setTitle("JavaFX Form with Buttons");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
