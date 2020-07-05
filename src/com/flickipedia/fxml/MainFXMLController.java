package com.flickipedia.fxml;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.flickipedia.sql.Logger;
import com.flickipedia.sql.SQLHelper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainFXMLController implements Initializable {
    private SQLHelper sql;
    private Stage primaryStage;

    @FXML
    private TextArea logArea;

    @FXML
    private Button searchBtn;

    @FXML
    private CheckBox movieCheck;

    @FXML
    private CheckBox tvCheck;

    @FXML
    private CheckBox nameCheck;

    @FXML
    private TextField nameField;

    @FXML
    private CheckBox genreCheck;

    @FXML
    private TextField genreField;

    @FXML
    private CheckBox yearCheck;

    @FXML
    private TextField startYearField;

    @FXML
    private TextField endYearField;

    @FXML
    private CheckBox actorCheck;

    @FXML
    private TextField actorField;

    @FXML
    private CheckBox writerCheck;

    @FXML
    private TextField writerField;

    @FXML
    private CheckBox directorCheck;

    @FXML
    private TextField directorField;

    @FXML
    private CheckBox countryCheck;

    @FXML
    private TextField countryField;

    @FXML
    private TextArea output;

    @FXML
    private Button insertBtn;

    private Stage insertStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Logger.init(logArea);

        // clear text
        output.clear();
        Logger.getInstance().clear();
    }

    public void setSQL(SQLHelper sql) {
        this.sql = sql;
    }

    public SQLHelper getSQL() {
        return this.sql;
    }

    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }

    public Stage getInsertStage() {
        return this.insertStage;
    }

    @FXML
    public void searchBtnAction() {
        boolean movie = movieCheck.isSelected(), tvshow = tvCheck.isSelected();
        this.output.clear();

        if (!movie && !tvshow) {
            this.message("You must select either movie or tvshow to search through.");
            return;
        }

        String name = null, genre = null, actor = null, writer = null, director = null, country = null;
        int start = 0, end = 0;
        boolean everything = true;

        if (nameCheck.isSelected()) {
            if (!nameField.getText().isEmpty()) {
                name = nameField.getText();
                everything = false;
            }
        }

        if (genreCheck.isSelected()) {
            if (!genreField.getText().isEmpty()) {
                genre = genreField.getText();
                everything = false;
            }
        }

        if (actorCheck.isSelected()) {
            if (!actorField.getText().isEmpty()) {
                actor = actorField.getText();
                everything = false;
            }
        }

        if (writerCheck.isSelected()) {
            if (!actorField.getText().isEmpty()) {
                writer = writerField.getText();
                everything = false;
            }
        }

        if (directorCheck.isSelected()) {
            if (!directorField.getText().isEmpty()) {
                director = directorField.getText();
                everything = false;
            }
        }

        if (countryCheck.isSelected()) {
            if (!countryField.getText().isEmpty()) {
                country = countryField.getText();
                everything = false;
            }
        }

        if (yearCheck.isSelected()) {
            try {
                if (startYearField.getText().isEmpty() || endYearField.getText().isEmpty()) {
                    Logger.getInstance().log("Both a start and end year must be specified!\nNot applying year filter.");
                    start = 0;
                    end = 0;
                } else {
                    start = Integer.parseInt(startYearField.getText());
                    end = Integer.parseInt(endYearField.getText());
                }
            } catch (NumberFormatException e) {
                Logger.getInstance().log("Invalid Year! Not applying year filter.");
                start = 0;
                end = 0;
            }
        }

        if (everything) {
            everything = start == 0 && end == 0;
        }

        if (movie) {
            this.message(this.sql.queryMovies(everything, name, genre, start, end, actor, writer, director, country));
        }

        if (tvshow) {
            this.message(this.sql.queryTVShow(everything, name, genre, start, end, actor, writer, director, country));
        }

        this.message("End of Query!\n\n");
    }

    @FXML
    public void insertBtnAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/flickipedia/fxml/InsertMovie.fxml"));
        try {
            Parent parent = (Parent) loader.load();
            Scene insertScene = new Scene(parent, 400, 400);

            insertStage = new Stage();
            insertStage.setTitle("Insert New Movie");
            insertStage.setScene(insertScene);
            insertStage.initModality(Modality.WINDOW_MODAL);
            insertStage.initOwner(this.primaryStage);
            insertStage.setOnCloseRequest(e -> {
                Logger.getInstance().log("Closed new movie insertion window.");
            });
            insertStage.show();

            loader.<InsertMovieFXMLController>getController().setMainControl(this);
        } catch (IOException e) {
            Logger.getInstance().log("Error in loading insert btn");
        }
    }

    public void message(String msg) {
        output.appendText(msg);

        if (!msg.endsWith("\n")) {
            output.appendText("\n");
        }
    }
}
