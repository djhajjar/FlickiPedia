package com.flickipedia;

import com.flickipedia.fxml.MainFXMLController;
import com.flickipedia.sql.SQLHelper;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        if (this.getParameters().getNamed().size() != 4) {
            System.out.println("Invalid usage, please only include arguements titled \"url\", \"user\", "
                    + "\"password\", \"driver\".\nTo do so, please have it formatted as the following: "
                    + "--url=\"your-url-here.com\"");
            System.exit(1);
        }

        SQLHelper sql = new SQLHelper(this.getParameters().getNamed().get("url"),
                this.getParameters().getNamed().get("user"), this.getParameters().getNamed().get("password"),
                this.getParameters().getNamed().get("driver"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/flickipedia/fxml/Main.fxml"));
        Parent main = (Parent) loader.load();

        Scene scene = new Scene(main, 1200, 800);

        primaryStage.setTitle("FlickiPedia Alpha");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> {
            sql.exit();

            Platform.exit();
            System.exit(0);
        });

        loader.<MainFXMLController>getController().setSQL(sql);
        loader.<MainFXMLController>getController().setStage(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
