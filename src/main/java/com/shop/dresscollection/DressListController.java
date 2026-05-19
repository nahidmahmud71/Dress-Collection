package com.shop.dresscollection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

public class DressListController {

    @FXML private TableView<Dress> dressTable;
    @FXML private TableColumn<Dress, String> nameCol;
    @FXML private TableColumn<Dress, String> typeCol;
    @FXML private TableColumn<Dress, String> colorCol;
    @FXML private TableColumn<Dress, Double> priceCol;

    @FXML private TextField searchField;
    @FXML private Button editBtn;
    @FXML private Button deleteBtn;

    @FXML private Label dName;
    @FXML private Label dType;
    @FXML private Label dSize;
    @FXML private Label dColor;
    @FXML private Label dPrice;
    @FXML private Label dDetails;
    @FXML private Label dDate;
    @FXML private Label dQty;
    @FXML private Label dCodeLabel;
    @FXML private Label codeTitleLabel;
    @FXML private Label dTarget;
    @FXML private Label dBoost;
    @FXML private Button toggleCodeBtn;

    private ObservableList<Dress> dressList = FXCollections.observableArrayList();
    private String currentDiscountCode = "";
    private boolean isCodeVisible = false;

    @FXML
    public void initialize() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        nameCol.setCellFactory(column -> new TableCell<Dress, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    Dress dress = getTableView().getItems().get(getIndex());
                    if (dress.getQuantity() < 10) {
                        setStyle("-fx-text-fill: red;");
                    } else {
                        setStyle("-fx-text-fill: black;");
                    }
                }
            }
        });

        deleteBtn.setDisable(true);
        loadData();

        dressTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                displayDetails(newSelection);
            }
        });

        if (!dressList.isEmpty()) {
            Dress lastItem = dressList.get(dressList.size() - 1);
            dressTable.getSelectionModel().select(lastItem);
            displayDetails(lastItem);
        }
    }

    private void loadData() {
        dressList.clear();
        try {
            FileReader fr = new FileReader("dress_data.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length == 11) {
                    Dress d = new Dress(
                            data[0], data[1], data[2], data[3],
                            Double.parseDouble(data[4]), data[5],
                            LocalDate.parse(data[6]), Integer.parseInt(data[7]),
                            data[8], data[9], Boolean.parseBoolean(data[10])
                    );
                    dressList.add(d);
                }
            }
            br.close();
            fr.close();
            dressTable.setItems(dressList);
        } catch (IOException e) {

        }
    }

    private void displayDetails(Dress dress) {
        dName.setText("Dress Name: " + dress.getName());
        dType.setText("Dress Type: " + dress.getType());
        dSize.setText("Available Size: " + dress.getSize());
        dColor.setText("Dress Color: " + dress.getColor());
        dPrice.setText("Price: " + dress.getPrice() + " BDT");
        dDetails.setText("Dress Details: " + dress.getDetails());
        dDate.setText("Last Purchase Date: " + dress.getLastPurchaseDate().toString());
        dQty.setText("Available Quantity: " + dress.getQuantity() + " Unit");
        dTarget.setText("Targeted Customer: " + dress.getTargetCustomer());

        String boostStatus = "Disabled";
        if(dress.isBoosting()) {
            boostStatus = "Enabled";
        }
        dBoost.setText("Facebook Boosting: " + boostStatus);

        currentDiscountCode = dress.getDiscountCode();
        if (currentDiscountCode.isEmpty()) {
            codeTitleLabel.setVisible(false);
            dCodeLabel.setVisible(false);
            toggleCodeBtn.setVisible(false);
        } else {
            codeTitleLabel.setVisible(true);
            dCodeLabel.setVisible(true);
            toggleCodeBtn.setVisible(true);
            isCodeVisible = false;
            dCodeLabel.setText("********");
            toggleCodeBtn.setText("Show");
        }
    }

    @FXML
    public void toggleCodeAction() {
        if (isCodeVisible) {
            dCodeLabel.setText("********");
            toggleCodeBtn.setText("Show");
            isCodeVisible = false;
        } else {
            dCodeLabel.setText(currentDiscountCode);
            toggleCodeBtn.setText("Hide");
            isCodeVisible = true;
        }
    }

    @FXML
    public void searchOnEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String keyword = searchField.getText().toLowerCase().trim();
            if (keyword.isEmpty()) {
                dressTable.setItems(dressList);
            } else {
                ObservableList<Dress> filteredList = FXCollections.observableArrayList();
                for (Dress d : dressList) {
                    if (d.getName().toLowerCase().contains(keyword) ||
                            d.getType().toLowerCase().contains(keyword)) {
                        filteredList.add(d);
                    }
                }
                dressTable.setItems(filteredList);
            }
        }
    }

    @FXML
    public void editAction() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Feature Update");
        alert.setHeaderText(null);
        alert.setContentText("Feature is coming soon.");
        alert.showAndWait();
    }

    @FXML
    public void backAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dress_entry.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}