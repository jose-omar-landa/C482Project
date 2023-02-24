package ims.ims;

import imsclasses.InHouse;
import imsclasses.Inventory;
import imsclasses.Outsourced;
import imsclasses.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;


/** The AddPartsController class is the controller for the AddParts.fxml file.
 *
 * This class contains the functionality of the Add Parts form page. */
public class AddPartsController {
    public RadioButton addPartInHouseRadio;
    public RadioButton addPartOutsourcedRadio;
    public TextField addPartIdText;
    public TextField addPartNameText;
    public TextField addPartInventoryText;
    public TextField addPartPriceText;
    public TextField addPartMaxText;
    public TextField addPartMachineIdText;
    public TextField addPartMinText;
    public Button addPartSaveButton;
    public Button addPartCancelButton;
    public Label addPartLabelMachineId;
    public ToggleGroup addPartRadioToggle;


    /** The onAddPartInHouseRadio method sets the label to Machine ID if the InHouse
     * radio button is selected. */
    public void onAddPartInHouseRadio(ActionEvent actionEvent)
    {
        addPartRadioToggle.getSelectedToggle();
        if (addPartInHouseRadio.isSelected()) {
            addPartLabelMachineId.setText("Machine ID");
        }
        else {
            addPartLabelMachineId.setText("Company Name");
        }
    }



    /** The onAddPartOutsourcedRadio method sets the label to Company Name if the
     * Outsourced radio button is selected. */
    public void onAddPartOutsourcedRadio(ActionEvent actionEvent)
    {
        addPartRadioToggle.getSelectedToggle();
        if (addPartOutsourcedRadio.isSelected()){
            addPartLabelMachineId.setText("Company Name");
        }
        else {
            addPartLabelMachineId.setText("Machine ID");
        }
    }

    public void onAddPartIdText(ActionEvent actionEvent) {
    }

    public void onAddPartNameText(ActionEvent actionEvent) {
    }

    public void onAddPartInventoryText(ActionEvent actionEvent) {
    }

    public void onAddPartPriceText(ActionEvent actionEvent) {
    }

    public void onAddPartMaxText(ActionEvent actionEvent) {
    }

    public void onAddPartMachineIdText(ActionEvent actionEvent) {
    }

    public void onAddPartMinText(ActionEvent actionEvent) {
    }



    /** The onAddPartSaveButton method saves the information the user has input into the text fields
     * as a new Part object with an automatically generated Part ID.
     *
     * Error messages are displayed if the user does not input appropriate values into the text fields.
     *
     * Once the Save button is clicked, the new Part is saved to the Inventory and the user is returned
     * to the main screen of the application. */
    public void onAddPartSaveButton(ActionEvent actionEvent) throws IOException
    {
        int id = 0;
        for (Part part : Inventory.getAllParts()) {
            if (part.getId() < id) {
                id = part.getId();
            }
            else {
                id++;
            }
        }

        try {
            int stock = Integer.parseInt(addPartInventoryText.getText());
            int max = Integer.parseInt(addPartMaxText.getText());
            int min = Integer.parseInt(addPartMinText.getText());

            if (max < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("The maximum value must be greater than the minimum value.");
                alert.showAndWait();
                return;
            }
            if (stock > max || stock < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("The inventory level must not be less than the minimum value or greater than the maximum value.");
                alert.showAndWait();
                return;
            }

            Part newPart;

            if (addPartInHouseRadio.isSelected()) {
                newPart = new InHouse(id,"", 0,0,0,0,0);
                newPart.setName((addPartNameText.getText()));
                newPart.setStock(Integer.parseInt(addPartInventoryText.getText()));
                newPart.setPrice(Double.parseDouble(addPartPriceText.getText()));
                newPart.setMax(Integer.parseInt(addPartMaxText.getText()));
                newPart.setMin(Integer.parseInt(addPartMinText.getText()));
                ((InHouse) newPart).setMachineId(Integer.parseInt(addPartMachineIdText.getText()));

                Inventory.addPart(newPart);
            }
            else {
                newPart = new Outsourced(id, "",0,0,0,0,"");
                newPart.setName((addPartNameText.getText()));
                newPart.setStock(Integer.parseInt(addPartInventoryText.getText()));
                newPart.setPrice(Double.parseDouble(addPartPriceText.getText()));
                newPart.setMax(Integer.parseInt(addPartMaxText.getText()));
                newPart.setMin(Integer.parseInt(addPartMinText.getText()));
                ((Outsourced) newPart).setCompanyName(addPartMachineIdText.getText());

                Inventory.addPart(newPart);
            }

            Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1200, 600);
            stage.setTitle("C482 - Inventory Management System");
            stage.setScene(scene);
            stage.show();
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("Please enter corresponding values into the fields.");
            alert.showAndWait();
        }
    }




    /** The onAddPartCancelButton method allows the user to close the Add Part form window without
     * saving any entered information. The user is then returned to the main screen of
     * the application.*/
    public void onAddPartCancelButton(ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200, 600);
        stage.setTitle("C482 - Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }
}
