package com.flickipedia.fxml;

import java.net.URL;
import java.util.ResourceBundle;

import com.flickipedia.sql.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class InsertMovieFXMLController implements Initializable {
    private MainFXMLController mainControl;

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField countryField;

    @FXML
    private TextField ageRatingField;

    @FXML
    private TextField dayField;

    @FXML
    private TextField monthField;

    @FXML
    private TextField yearField;

    @FXML
    private TextField durationField;

    @FXML
    private Button insertBtn;

    @FXML
    private Label errorLbl;

    public void setMainControl(MainFXMLController mainControl) {
        this.mainControl = mainControl;
    }

    @FXML
    public void insertBtnPressed(ActionEvent event) {
        boolean success = false, fieldError = false;
        int id = 0, day = 0, month = 0, year = 0;
        String name = "", country = "", ageRating = "";
        double duration = 0.0;

        try {
            if (idField.getText().isEmpty()) {
                fieldError = true;
                errorLbl.setText("Id is missing");
            } else {
                id = Integer.parseInt(idField.getText());
            }
        } catch (NumberFormatException e) {
            fieldError = true;
            errorLbl.setText("Error in id field");
        }

        if (!fieldError && nameField.getText().isEmpty()) {
            fieldError = true;
            errorLbl.setText("Name is missing");
        } else {
            name = nameField.getText();
        }

        if (!fieldError && countryField.getText().isEmpty()) {
            fieldError = true;
            errorLbl.setText("Country is missing.");
        } else {
            country = countryField.getText();
        }

        if (!fieldError && ageRatingField.getText().isEmpty()) {
            fieldError = true;
            errorLbl.setText("Age Rating is missing.");
        } else {
            ageRating = ageRatingField.getText();
        }

        if (!fieldError && dayField.getText().isEmpty()) {
            fieldError = true;
            errorLbl.setText("Day is missing.");
        } else {
            try {
                day = Integer.parseInt(dayField.getText());
            } catch (NumberFormatException e) {
                fieldError = true;
                errorLbl.setText("Error in day field.");
            }
        }

        if (!fieldError && monthField.getText().isEmpty()) {
            fieldError = true;
            errorLbl.setText("Month is missing.");
        } else {
            try {
                month = Integer.parseInt(monthField.getText());
            } catch (NumberFormatException e) {
                fieldError = true;
                errorLbl.setText("Error in month field.");
            }
        }

        if (!fieldError && yearField.getText().isEmpty()) {
            fieldError = true;
            errorLbl.setText("Year is missing.");
        } else {
            try {
                year = Integer.parseInt(yearField.getText());
            } catch (NumberFormatException e) {
                fieldError = true;
                errorLbl.setText("Error in year field.");
            }
        }

        if (!fieldError && durationField.getText().isEmpty()) {
            fieldError = true;
            errorLbl.setText("Duration missing");
        } else {
            try {
                duration = Double.parseDouble(durationField.getText());
            } catch (NumberFormatException e) {
                fieldError = true;
                errorLbl.setText("Error in duration field.");
            }
        }

        if (!fieldError) {
            success = this.mainControl.getSQL().insertMovie(id, name, country, ageRating, day, month, year, duration);
        }

        if (success) {
            closeWindow();
            Logger.getInstance().log("Insertion successful!");
        } else {
            errorLbl.setText("Unable to insert movie. It may already exist.");
        }
    }

    private void closeWindow() {
        this.mainControl.getInsertStage().close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.errorLbl.setText("");
    }
}
