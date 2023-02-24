package ims.ims;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

//Javadocs file within the javadocs folder inside the JoseLandaC482Resubmission.zip Folder

/** This class creates an Inventory Management Application in which users can add, modify, or delete
 * parts or products that contain parts.
 *
 * FUTURE_ENHANCEMENT: A future enhancement for this application would have the Product Table have
 * an additional column that states if the product contains an associated part.
 *
 * @author Jose Landa*/
public class MainApplication extends Application {
    @Override

    /** This is the method that loads the main screen or scene of the application.
     * @param stage Loads the first screen the user will see. */
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("MainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
        stage.setTitle("C482 - Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /** This is the Main method for the application that launches the application.
     * @param args The initial launch of the application. */
    public static void main(String[] args) {
        launch(args);
    }



}