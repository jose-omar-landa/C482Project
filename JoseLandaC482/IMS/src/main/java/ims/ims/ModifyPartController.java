package ims.ims;
import imsclasses.Outsourced;
import imsclasses.Part;
import imsclasses.InHouse;
import imsclasses.Inventory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/** The ModifyPartController class is the controller for the ModifyParts.fxml file.
 *
 * This class contains the functionality of the Modify Parts form page. */
public class ModifyPartController implements Initializable {
    public RadioButton modifyPartInHouseRadio;
    public RadioButton modifyPartOutsourcedRadio;
    public TextField modifyPartIdText;
    public TextField modifyPartNameText;
    public TextField modifyPartInventoryText;
    public TextField modifyPartPriceText;
    public TextField modifyPartMaxText;
    public TextField modifyPartMinText;
    public TextField modifyPartMachineIdText;
    public Button modifyPartSaveButton;
    public Button modifyPartCancelButton;
    public ToggleGroup modifyPartsRadioToggle;
    public Label modifyPartLabelMachineId;

    private ObservableList<Part> parts = FXCollections.observableArrayList();

    private Part selectedPart;



    /** The onModifyPartInHouseRadio method sets the label to Machine ID if the InHouse
     * radio button is selected. */
    public void onModifyPartInHouseRadio(ActionEvent actionEvent)
    {
        modifyPartsRadioToggle.getSelectedToggle();
        if (modifyPartInHouseRadio.isSelected()) {
            modifyPartLabelMachineId.setText("Machine ID");
        }
        else {
            modifyPartLabelMachineId.setText("Company Name");
        }
    }



    /** The onModifyPartOutsourcedRadio method sets the label to Company Name if the
     * Outsourced radio button is selected. */
    public void onModifyPartOutsourcedRadio(ActionEvent actionEvent)
    {
        modifyPartsRadioToggle.getSelectedToggle();
        if (modifyPartOutsourcedRadio.isSelected()){
            modifyPartLabelMachineId.setText("Company Name");
        }
        else {
            modifyPartLabelMachineId.setText("Machine ID");
        }
    }

    public void onModifyPartIdText(ActionEvent actionEvent) {
    }

    public void onModifyPartNameText(ActionEvent actionEvent) {
    }

    public void onModifyPartInventoryText(ActionEvent actionEvent) {
    }

    public void onModifyPartPriceText(ActionEvent actionEvent) {
    }

    public void onModifyPartMaxText(ActionEvent actionEvent) {
    }

    public void onModifyPartMinText(ActionEvent actionEvent) {
    }

    public void onModifyPartMachineIdText(ActionEvent actionEvent) {
    }



    /** The onModifyPartSaveButton method saves the information the user has input into the text fields
     * as a new Part object with an automatically generated Part ID.
     *
     * Error messages are displayed if the user does not input appropriate values into the text fields.
     *
     * Once the Save button is clicked, the new Part is saved to the Inventory and the old part is deleted
     * from the Inventory. The user is then returned to the main screen of the application.
     *
     *
     * RUNTIME_ERROR: A runtime error occured when trying to get the part data to cross over and populate
     * into the text fields. I had not realized I needed to use the .setText method in the initialize method of
     * this class and spent a bit of time attempting to figure this out. Once I set the text fields to receive
     * the data inside the initialize method of this class, I was able to get the selected part's data to
     * populate inside the text fields. */
    public void onModifyPartSaveButton(ActionEvent actionEvent)  throws IOException
    {

        try {
            int stock = Integer.parseInt(modifyPartInventoryText.getText());
            int min = Integer.parseInt(modifyPartMinText.getText());
            int max = Integer.parseInt(modifyPartMaxText.getText());
            boolean partAddSuccessful = true;

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

            if (modifyPartsRadioToggle.getSelectedToggle().equals(modifyPartInHouseRadio)) {
                Part modifiedPart = new InHouse(Integer.parseInt(modifyPartIdText.getText()),
                        modifyPartNameText.getText(),
                        Double.parseDouble(modifyPartPriceText.getText()),
                        Integer.parseInt(modifyPartInventoryText.getText()),
                        Integer.parseInt(modifyPartMinText.getText()),
                        Integer.parseInt(modifyPartMaxText.getText()),
                        Integer.parseInt(modifyPartMachineIdText.getText()));

                Inventory.addPart(modifiedPart);
                Inventory.deletePart(selectedPart);
            }

            if (modifyPartsRadioToggle.getSelectedToggle().equals(modifyPartOutsourcedRadio)) {
                Part modifiedPart = new Outsourced(Integer.parseInt(modifyPartIdText.getText()),
                        modifyPartNameText.getText(),
                        Double.parseDouble(modifyPartPriceText.getText()),
                        Integer.parseInt(modifyPartInventoryText.getText()),
                        Integer.parseInt(modifyPartMinText.getText()),
                        Integer.parseInt(modifyPartMaxText.getText()),
                        modifyPartMachineIdText.getText());

                Inventory.addPart(modifiedPart);
                Inventory.deletePart(selectedPart);
            }

            if (partAddSuccessful) {
                Inventory.deletePart(selectedPart);
                returnToMainScreenEvent(actionEvent);
            }
            else {
                return;
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("Please enter corresponding values into the fields.");
            alert.showAndWait();
            return;
        }
    }



    /** The onModifyPartCancelButton method allows the user to close the Modify Part form window without
     * saving any entered information. The user is then returned to the main screen of
     * the application. */
    public void onModifyPartCancelButton(ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200, 600);
        stage.setTitle("C482 - Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }



    /** The returnToMainScreenEvent method returns the user to the main screen of the application.
     * This method is called within the onModifyPartSaveButton method in order to return the user to the
     * main screen if the new modified part is successfully added to the Inventory. */
    private void returnToMainScreenEvent(ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200, 600);
        stage.setTitle("C482 - Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }



    /** The initialize method is the starting method for the ModifyPartController class.
     *
     * This method retrieves the current data associated with the part selected on the main
     * screen Part table and generates the data within the appropriate text fields. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        selectedPart = MainController.getModifyPartData();

        if(selectedPart instanceof InHouse) {
            modifyPartInHouseRadio.setSelected(true);
            modifyPartLabelMachineId.setText("Machine ID");
            modifyPartMachineIdText.setText(String.valueOf(((InHouse)selectedPart).getMachineId()));
        }

        if(selectedPart instanceof Outsourced) {
            modifyPartOutsourcedRadio.setSelected(true);
            modifyPartLabelMachineId.setText("Company Name");
            modifyPartMachineIdText.setText(((Outsourced)selectedPart).getCompanyName());
        }

        modifyPartIdText.setText(String.valueOf(selectedPart.getId()));
        modifyPartNameText.setText(selectedPart.getName());
        modifyPartInventoryText.setText(String.valueOf(selectedPart.getStock()));
        modifyPartPriceText.setText(String.valueOf(selectedPart.getPrice()));
        modifyPartMinText.setText(String.valueOf(selectedPart.getMin()));
        modifyPartMaxText.setText(String.valueOf(selectedPart.getMax()));
    }
}
