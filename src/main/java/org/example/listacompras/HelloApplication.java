package org.example.listacompras;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    private ListView<String> listView;
    private TextField inputField;
    private int editIndex = -1; // Variável de estado para o índice de edição

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Lista de Compras");

        listView = new ListView<>();
        inputField = new TextField();

        Button addButton = new Button("Adicionar");
        addButton.setOnAction(e -> adicionarOuEditarItem());

        Button editButton = new Button("Editar");
        editButton.setOnAction(e -> prepararEdicao());

        Button deleteButton = new Button("Excluir");
        deleteButton.setOnAction(e -> excluirItem());

        Button markButton = new Button("Marcar como Comprado");
        markButton.setOnAction(e -> marcarItem());

        Button unmarkButton = new Button("Desmarcar");
        unmarkButton.setOnAction(e -> desmarcarItem());

        HBox inputBox = new HBox(10, inputField, addButton, editButton, deleteButton, markButton, unmarkButton);
        VBox vbox = new VBox(10, listView, inputBox);

        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void adicionarOuEditarItem() {
        String item = inputField.getText();

        // Verificar se o item contém caracteres especiais
        if (item.matches("[a-zA-Z0-9 ]*")) {  // Verifica se o item contém apenas letras, números e espaços
            if (!item.isEmpty()) {
                if (editIndex >= 0) {
                    // Estamos em modo de edição, verificar se o novo item é diferente do item original
                    String originalItem = listView.getItems().get(editIndex);
                    if (!item.equals(originalItem)) {
                        // Verificar se o novo item já existe na lista
                        if (listView.getItems().contains(item)) {
                            mostrarAlerta("O item já existe na lista. Por favor, insira um item diferente.");
                        } else {
                            // Substituir o item
                            listView.getItems().set(editIndex, item);
                            editIndex = -1; // Resetar a variável de estado
                        }
                    } else {
                        mostrarAlerta("O item não foi alterado. Por favor, edite para um valor diferente.");
                    }
                } else {
                    // Verificar se o item já existe na lista
                    if (listView.getItems().contains(item)) {
                        mostrarAlerta("O item já existe na lista. Por favor, insira um item diferente.");
                    } else {
                        // Estamos em modo de adição, adicionar um novo item
                        listView.getItems().add(item);
                    }
                }
                inputField.clear();
            }
        } else {
            mostrarAlerta("O item não pode conter caracteres especiais. Por favor, use apenas letras, números e espaços.");
        }
    }

    private void prepararEdicao() {
        String selectedItem = listView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            editIndex = listView.getSelectionModel().getSelectedIndex(); // Armazenar o índice do item selecionado
            inputField.setText(selectedItem); // Preencher a caixa de texto com o item selecionado
        } else {
            mostrarAlerta("Selecione um item para editar.");
        }
    }

    private void excluirItem() {
        String selectedItem = listView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            listView.getItems().remove(selectedItem);
            inputField.clear();
        } else {
            mostrarAlerta("Selecione um item para excluir.");
        }
    }

    private void marcarItem() {
        String selectedItem = listView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            if (!selectedItem.contains(" (Comprado)")) {  // Verifica se já está marcado
                int selectedIndex = listView.getSelectionModel().getSelectedIndex();
                listView.getItems().set(selectedIndex, selectedItem + " (Comprado)");
            } else {
                mostrarAlerta("Este item já foi marcado como comprado.");
            }
        } else {
            mostrarAlerta("Selecione um item para marcar.");
        }
    }

    private void desmarcarItem() {
        String selectedItem = listView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            if (selectedItem.contains(" (Comprado)")) {  // Verifica se está marcado
                int selectedIndex = listView.getSelectionModel().getSelectedIndex();
                String unmarkedItem = selectedItem.replace(" (Comprado)", ""); // Remove a marcação "(Comprado)"
                listView.getItems().set(selectedIndex, unmarkedItem);
            } else {
                mostrarAlerta("Este item não está marcado como comprado.");
            }
        } else {
            mostrarAlerta("Selecione um item para desmarcar.");
        }
    }

    private void mostrarAlerta(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
