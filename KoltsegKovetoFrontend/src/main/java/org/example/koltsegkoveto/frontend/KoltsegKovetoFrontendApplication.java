package org.example.koltsegkoveto.frontend;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.koltsegkoveto.frontend.model.Category;
import org.example.koltsegkoveto.frontend.model.Transaction;
import org.example.koltsegkoveto.frontend.service.CategoryService;
import org.example.koltsegkoveto.frontend.service.TransactionService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class KoltsegKovetoFrontendApplication extends Application {

    private final TransactionService transactionService = new TransactionService();
    private final CategoryService categoryService = new CategoryService();

    private TableView<Transaction> table;
    private ComboBox<Category> categoryBox;
    private PieChart pieChart;
    private ComboBox<String> monthSelector;

    private List<Transaction> allTransactions = new ArrayList<>();

    // --- Színek: bevétel + 10 fix kiadás szín ---
    private final String incomeColor = "#4CAF50"; // zöld
    private final List<String> expenseColors = List.of(
            "#F44336", "#FF9800", "#FFC107", "#9C27B0", "#2196F3",
            "#009688", "#795548", "#3F51B5", "#E91E63", "#607D8B"
    );

    @Override
    public void start(Stage stage) {
        table = new TableView<>();

        TableColumn<Transaction, Long> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Transaction, String> descCol = new TableColumn<>("Leírás");
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Transaction, Double> amountCol = new TableColumn<>("Összeg");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<Transaction, LocalDate> dateCol = new TableColumn<>("Dátum");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Transaction, String> categoryCol = new TableColumn<>("Kategória");
        categoryCol.setCellValueFactory(cell -> {
            Category c = cell.getValue().getCategory();
            String name = (c != null) ? c.getName() : "";
            return new javafx.beans.property.ReadOnlyStringWrapper(name);
        });

        table.getColumns().addAll(idCol, descCol, amountCol, dateCol, categoryCol);

        // --- Hónapválasztó ---
        monthSelector = new ComboBox<>();
        monthSelector.setPromptText("Válassz hónapot...");
        monthSelector.setOnAction(e -> refreshData());

        HBox monthBox = new HBox(10, new Label("Hónap:"), monthSelector);
        monthBox.setPadding(new Insets(10));

        // --- Tranzakció űrlap ---
        GridPane form = new GridPane();
        form.setPadding(new Insets(10));
        form.setHgap(10);
        form.setVgap(10);

        TextField descField = new TextField();
        TextField amountField = new TextField();
        DatePicker datePicker = new DatePicker(LocalDate.now());
        categoryBox = new ComboBox<>();

        Button addBtn = new Button("Hozzáadás");
        addBtn.setOnAction(e -> {
            try {
                Transaction t = new Transaction();
                t.setDescription(descField.getText());
                t.setAmount(Double.parseDouble(amountField.getText()));
                t.setDate(datePicker.getValue());
                t.setCategory(categoryBox.getValue());

                transactionService.createTransaction(t);
                descField.clear();
                amountField.clear();
                datePicker.setValue(LocalDate.now());
                categoryBox.getSelectionModel().clearSelection();

                loadAllTransactions();
            } catch (Exception ex) {
                showError("Hiba történt a mentés közben: " + ex.getMessage());
            }
        });

        form.addRow(0, new Label("Leírás:"), descField);
        form.addRow(1, new Label("Összeg:"), amountField);
        form.addRow(2, new Label("Dátum:"), datePicker);
        form.addRow(3, new Label("Kategória:"), categoryBox);
        form.addRow(4, addBtn);

        // --- Kategória hozzáadása ---
        TextField newCategoryField = new TextField();
        Button addCategoryBtn = new Button("Kategória hozzáadása");
        addCategoryBtn.setOnAction(e -> {
            try {
                String name = newCategoryField.getText();
                if (!name.isBlank()) {
                    Category category = new Category();
                    category.setName(name);
                    categoryService.createCategory(category);
                    newCategoryField.clear();
                    loadCategories();
                }
            } catch (Exception ex) {
                showError("Hiba történt a kategória hozzáadásakor: " + ex.getMessage());
            }
        });

        form.addRow(5, new Label("Új kategória:"), newCategoryField);
        form.addRow(6, addCategoryBtn);

        // --- Diagram ---
        pieChart = new PieChart();
        pieChart.setTitle("Kategóriák szerinti összesítés");

        VBox right = new VBox(12, form, pieChart);
        right.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setTop(monthBox);
        root.setCenter(table);
        root.setRight(right);

        Scene scene = new Scene(root, 1000, 650);
        stage.setScene(scene);
        stage.setTitle("Költségkövető");
        stage.show();

        loadCategories();
        loadAllTransactions();
    }

    private void loadCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            categoryBox.setItems(FXCollections.observableArrayList(categories));
        } catch (Exception ex) {
            showError("Kategóriák betöltése sikertelen: " + ex.getMessage());
        }
    }

    private void loadAllTransactions() {
        try {
            allTransactions = transactionService.getAllTransactions();
            updateMonthSelector();
            refreshData();
        } catch (Exception ex) {
            showError("Tranzakciók betöltése sikertelen: " + ex.getMessage());
        }
    }

    private void updateMonthSelector() {
        Set<String> months = allTransactions.stream()
                .map(t -> t.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM")))
                .collect(Collectors.toCollection(TreeSet::new));
        monthSelector.setItems(FXCollections.observableArrayList(months));
        if (!months.isEmpty() && monthSelector.getValue() == null) {
            monthSelector.setValue(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM")));
        }
    }

    private void refreshData() {
        if (monthSelector.getValue() == null) return;

        String selectedMonth = monthSelector.getValue();
        List<Transaction> filtered = allTransactions.stream()
                .filter(t -> t.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM")).equals(selectedMonth))
                .collect(Collectors.toList());

        table.getItems().setAll(filtered);
        updateChart(filtered);
    }

    // --- Kategóriák szerinti összesítés és színezés ---
    private void updateChart(List<Transaction> transactions) {
        double incomeSum = 0.0;
        Map<String, Double> expenses = new LinkedHashMap<>();

        for (Transaction t : transactions) {
            double amount = t.getAmount() != null ? t.getAmount() : 0.0;
            if (amount >= 0) {
                incomeSum += amount;
            } else {
                String name = (t.getCategory() != null && t.getCategory().getName() != null)
                        ? t.getCategory().getName()
                        : "Ismeretlen";
                expenses.put(name, expenses.getOrDefault(name, 0.0) + Math.abs(amount));
            }
        }

        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();

        // Bevétel
        if (incomeSum > 0) {
            data.add(new PieChart.Data("Bevétel", incomeSum));
        }

        // Kiadások
        expenses.forEach((k, v) -> data.add(new PieChart.Data(k, v)));

        pieChart.setData(data);

        // Színezés
        int colorIndex = 0;
        for (PieChart.Data d : data) {
            String color;
            if (d.getName().equals("Bevétel")) {
                color = incomeColor;
            } else {
                color = expenseColors.get(colorIndex % expenseColors.size());
                colorIndex++;
            }
            d.getNode().setStyle("-fx-pie-color: " + color + ";");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Hiba");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}
