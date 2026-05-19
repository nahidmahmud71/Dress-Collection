package com.shop.dresscollection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class DressEntryController {

    @FXML private TextField nameField;
    @FXML private ChoiceBox<String> typeBox;
    @FXML private ComboBox<String> sizeBox;
    @FXML private ColorPicker colorPicker;
    @FXML private Slider priceSlider;
    @FXML private TextArea detailsArea;
    @FXML private DatePicker datePicker;
    @FXML private Spinner<Integer> qtySpinner;
    @FXML private PasswordField discountField;
    @FXML private RadioButton maleRadio;
    @FXML private RadioButton femaleRadio;
    @FXML private CheckBox boostingCheck;

    @FXML private Label nameError;
    @FXML private Label typeError;
    @FXML private Label sizeError;
    @FXML private Label colorError;
    @FXML private Label dateError;
    @FXML private Label targetError;

    private ToggleGroup genderGroup;

    @FXML
    public void initialize() {
        typeBox.getItems().addAll("Hoodie", "Sweater", "T-Shirt", "Jacket");
        sizeBox.getItems().addAll("Small", "Medium", "Large", "XL");

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 500, 0);
        qtySpinner.setValueFactory(valueFactory);

        genderGroup = new ToggleGroup();
        maleRadio.setToggleGroup(genderGroup);
        femaleRadio.setToggleGroup(genderGroup);

        priceSlider.setMin(500);
        priceSlider.setMax(5000);
    }

    @FXML
    public void saveAction(ActionEvent event) {
        boolean isValid = true;

        if (nameField.getText().trim().isEmpty()) {
            nameError.setVisible(true);
            isValid = false;
        } else {
            nameError.setVisible(false);
        }

        if (typeBox.getValue() == null) {
            typeError.setVisible(true);
            isValid = false;
        } else {
            typeError.setVisible(false);
        }

        if (sizeBox.getValue() == null) {
            sizeError.setVisible(true);
            isValid = false;
        } else {
            sizeError.setVisible(false);
        }

        if (colorPicker.getValue() == null) {
            colorError.setVisible(true);
            isValid = false;
        } else {
            colorError.setVisible(false);
        }

        if (datePicker.getValue() == null || datePicker.getValue().isAfter(LocalDate.now())) {
            dateError.setVisible(true);
            isValid = false;
        } else {
            dateError.setVisible(false);
        }

        if (genderGroup.getSelectedToggle() == null) {
            targetError.setVisible(true);
            isValid = false;
        } else {
            targetError.setVisible(false);
        }

        if (isValid) {
            String targetCustomer = "";
            if (maleRadio.isSelected()) {
                targetCustomer = "Male";
            } else if (femaleRadio.isSelected()) {
                targetCustomer = "Female";
            }

            Dress dress = new Dress(
                    nameField.getText().trim(),
                    typeBox.getValue(),
                    sizeBox.getValue(),
                    colorPicker.getValue().toString(),
                    priceSlider.getValue(),
                    detailsArea.getText().trim(),
                    datePicker.getValue(),
                    qtySpinner.getValue(),
                    discountField.getText().trim(),
                    targetCustomer,
                    boostingCheck.isSelected()
            );

            try {
                FileWriter fw = new FileWriter("dress_data.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(dress.toString());
                bw.newLine();
                bw.close();
                fw.close();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("dress_list.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void showListAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dress_list.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}