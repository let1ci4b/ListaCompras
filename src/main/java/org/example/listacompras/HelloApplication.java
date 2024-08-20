package org.example.listacompas;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    private ListView<String> listView;
    private TextField inputField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Lista de Compras");

        listView = new ListView<>();
        inputField = new TextField();

        Button addButton = new Button("Adicionar");
        addButton.setOnAction(e -> adicionarItem());

        Button editButton = new Button("Editar");
        editButton.setOnAction(e -> editarItem());

        Button deleteButton = new Button("Excluir");
        deleteButton.setOnAction(e -> excluirItem());

        Button markButton = new Button("Marcar como comprado");
        markButton.setOnAction(e -> marcarItem());

        HBox inputBox = new HBox(10, inputField, addButton, editButton, deleteButton, markButton);
        VBox vbox = new VBox(10, listView, inputBox);

        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void adicionarItem() {
        String item = inputField.getText();
        if (!item.isEmpty()) {
            listView.getItems().add(item);
            inputField.clear();
        }
    }

    private void editarItem() {
        String selectedItem = listView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            int selectedIndex = listView.getSelectionModel().getSelectedIndex();
            listView.getItems().set(selectedIndex, inputField.getText());
            inputField.clear();
        }
    }

    private void excluirItem() {
        String selectedItem = listView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            listView.getItems().remove(selectedItem);
        }
    }

    private void marcarItem() {
        String selectedItem = listView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            int selectedIndex = listView.getSelectionModel().getSelectedIndex();
            listView.getItems().set(selectedIndex, selectedItem + " (Comprado)");
        }
    }
}
