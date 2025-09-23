package org.example.koltsegkoveto.frontend;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.example.koltsegkoveto.frontend.model.Category;
import org.example.koltsegkoveto.frontend.model.Transaction;
import org.example.koltsegkoveto.frontend.service.CategoryService;
import org.example.koltsegkoveto.frontend.service.TransactionService;

public class KoltsegKovetoFrontendApplication extends Application {

    private final TransactionService transactionService = new TransactionService();
    private final CategoryService categoryService = new CategoryService();

    @Override
    public void start(Stage stage) {
        // --- Táblázat ---
        TableView<Transaction> table = new TableView<>();

        TableColumn<Transaction, Long> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Transaction, String> descCol = new TableColumn<>("Leírás");
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Transaction, Double> amountCol = new TableColumn<>("Összeg");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<Transaction, String> categoryCol = new TableColumn<>("Kategória");
        categoryCol.setCellValueFactory(cell -> {
            Transaction t = cell.getValue();
            String name = (t.getCategory() != null) ? t.getCategory().getName() : "";
            return new ReadOnlyStringWrapper(name);
        });

        table.getColumns().addAll(idCol, descCol, amountCol, categoryCol);
        table.getItems().setAll(transactionService.getAllTransactions());

        // --- Űrlap ---
        TextField descField = new TextField();
        TextField amountField = new TextField();
        DatePicker datePicker = new DatePicker();

        ComboBox<Category> categoryBox = new ComboBox<>();
        categoryBox.getItems().setAll(categoryService.getAllCategories());

        Button saveButton = new Button("Mentés");

        // Mentés gomb működése
        saveButton.setOnAction(e -> {
            try {
                String description = descField.getText().trim();
                if (description.isEmpty()) {
                    showError("A leírás nem lehet üres!");
                    return;
                }

                double amount;
                try {
                    amount = Double.parseDouble(amountField.getText().trim());
                } catch (NumberFormatException ex) {
                    showError("Az összegnek számnak kell lennie!");
                    return;
                }

                if (datePicker.getValue() == null) {
                    showError("Kérlek, válassz dátumot!");
                    return;
                }

                Category category = categoryBox.getValue();
                if (category == null) {
                    showError("Kérlek, válassz kategóriát!");
                    return;
                }



                // Tranzakció létrehozása
                Transaction transaction = new Transaction();
                transaction.setDescription(description);
                transaction.setAmount(amount);
                transaction.setDate(datePicker.getValue());
                transaction.setCategory(category);

                Transaction saved = transactionService.createTransaction(transaction);
                if (saved != null) {
                    table.getItems().add(saved);
                    descField.clear();
                    amountField.clear();
                    datePicker.setValue(null);
                    categoryBox.getSelectionModel().clearSelection();
                } else {
                    showError("Hiba a tranzakció mentésekor!");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                showError("Ismeretlen hiba történt!");
            }
        });

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(10));
        form.add(new Label("Leírás:"), 0, 0);
        form.add(descField, 1, 0);
        form.add(new Label("Összeg:"), 0, 1);
        form.add(amountField, 1, 1);
        form.add(new Label("Dátum:"), 0, 2);
        form.add(datePicker, 1, 2);
        form.add(new Label("Kategória:"), 0, 3);
        form.add(categoryBox, 1, 3);
        form.add(saveButton, 1, 4);

        TextField categoryField = new TextField();
        Button addCategoryButton = new Button("Kategória hozzáadása");

        // Gomb esemény
        addCategoryButton.setOnAction(e -> {
            String name = categoryField.getText().trim();
            if (!name.isEmpty()) {
                Category newCat = new Category();
                newCat.setName(name);
                Category savedCat = categoryService.createCategory(newCat);
                if (savedCat != null) {
                    categoryBox.getItems().add(savedCat); // hozzáadjuk a legördülő listához
                    categoryField.clear();
                } else {
                    showError("Hiba a kategória mentésekor!");
                }
            } else {
                showError("A kategória neve nem lehet üres!");
            }
        });

// Hozzáadás a formhoz
        form.add(new Label("Új kategória:"), 0, 5);
        form.add(categoryField, 1, 5);
        form.add(addCategoryButton, 1, 6);

        // --- Elrendezés ---
        BorderPane root = new BorderPane();
        root.setCenter(table);
        root.setBottom(form);

        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Költségkövető alkalmazás");
        stage.setScene(scene);
        stage.show();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}
