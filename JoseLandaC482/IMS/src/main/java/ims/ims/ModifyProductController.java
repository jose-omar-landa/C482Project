package ims.ims;

import imsclasses.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/** The ModifyProductController class is the controller for the ModifyProducts.fxml file. This
 * class contains the methods for the functionality of the Modify Product screen of the application. */
public class ModifyProductController implements Initializable {
    public TextField modifyProductInventoryText;
    public TextField modifyProductNameText;
    public TextField modifyProductIdText;
    public TextField modifyProductPriceText;
    public TextField modifyProductMaxText;
    public TextField modifyProductMinText;
    public TextField modifyProductSearchBar;
    public Button modifyProductAddButton;
    public Button modifyProductRemoveButton;
    public Button modifyProductSaveButton;
    public Button modifyProductCancelButton;
    public TableView<Part> modifyProductsPartTable;
    public TableColumn<Part, Integer> modifyProductPartIdColumn;
    public TableColumn<Part, String> modifyProductPartNameColumn;
    public TableColumn<Part, Integer> modifyProductInventoryColumn;
    public TableColumn<Part, Double> modifyProductPriceColumn;
    public TableView<Part> modifyProductAssociatedPartsTable;
    public TableColumn<Part, Integer> associatedPartIdColumn;
    public TableColumn<Part, String> associatedPartNameColumn;
    public TableColumn<Part, Integer> associatedPartInventoryColumn;
    public TableColumn<Part, String> associatedPartPriceColumn;

    private Product selectedProduct;

    private ObservableList<Part> modifyProductsPageAssociatedParts = FXCollections.observableArrayList();

    public void onModifyProductInventoryText(ActionEvent actionEvent) {
    }

    public void onModifyProductNameText(ActionEvent actionEvent) {
    }

    public void onModifyProductIdText(ActionEvent actionEvent) {
    }

    public void onModifyProductPriceText(ActionEvent actionEvent) {
    }

    public void onModifyProductMaxText(ActionEvent actionEvent) {
    }

    public void onModifyProductMinText(ActionEvent actionEvent) {
    }



    /** The onModifyProductSearchBar method allows the user to search for a part within the part table
     * on the Modify Product form screen.
     *
     * The user can search for a part via a part ID or a part name. Partial strings of the part name are
     * acceptable for the search functionality.
     *
     * If a part being searched is not found, and error message is displayed to the user stating that the
     * part was not found. */
    public void onModifyProductSearchBar(ActionEvent actionEvent)
    {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> foundPart = FXCollections.observableArrayList();
        String stringSearcher = modifyProductSearchBar.getText();

        for (Part searchedPart : allParts) {
            if (String.valueOf(searchedPart.getId()).contains(stringSearcher) || String.valueOf(searchedPart.getName()).contains(stringSearcher)) {
                foundPart.add(searchedPart);
            }
        }

        modifyProductsPartTable.setItems(foundPart);

        if (foundPart.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("NOT FOUND");
            alert.setContentText("Searched part not found.");
            alert.showAndWait();
        }
    }



    /** The onModifyProductAddButton method allows the user to select a part on the Part table and add it to the
     * associated parts table.
     *
     * If a part is not selected prior to clicking the Add button, an alert message is displayed to the
     * user stating that a part must be selected first. */
    public void onModifyProductAddButton(ActionEvent actionEvent)
    {
        Part selectedPart = modifyProductsPartTable.getSelectionModel().getSelectedItem();

        if(selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Please select a Part first!");
            alert.showAndWait();
        }
        else {
            modifyProductsPageAssociatedParts.add(selectedPart);
            modifyProductAssociatedPartsTable.setItems(modifyProductsPageAssociatedParts);
        }
    }



    /** The onModifyProductRemoveButton method allows the user to remove an associated part from the
     * associated parts table.
     *
     * If a part is not selected prior to clicking the Remove Associated Part button, an error message is
     * displayed to the user stating that a part must be selected first. Also displays a confirmation message
     * to the user to ensure the correct part is being removed. If the user clicks the Yes option, the
     * part is removed from the Associated Parts Table. */
    public void onModifyProductRemoveButton(ActionEvent actionEvent)
    {
        Part selectedPart = modifyProductAssociatedPartsTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Please select a Part first!");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Removal");
            alert.setContentText("Are you sure you want to remove this associated part?");
            Optional<ButtonType> decision = alert.showAndWait();

            if (decision.isPresent() && decision.get() == ButtonType.OK) {
                modifyProductsPageAssociatedParts.remove(selectedPart);
                modifyProductAssociatedPartsTable.setItems(modifyProductsPageAssociatedParts);
            }
        }
    }



    /** The onModifyProductSaveButton method takes the user input in the text fields on the Modify Product form and
     * saves the information as a new product.
     *
     * Multiple error messages are placed to ensure that all text fields
     * are appropriately filled out.
     *
     * Once the Save button is clicked, the information is saved and the product is added to the Product table
     * on the main screen of the application. The Add Product form is then closed and the main screen is opened
     * for the user. */
    public void onModifyProductSaveButton(ActionEvent actionEvent) throws IOException {
        try {
            int id = selectedProduct.getId();
            String name = modifyProductNameText.getText();
            int stock = Integer.parseInt(modifyProductInventoryText.getText());
            double price = Double.parseDouble(modifyProductPriceText.getText());
            int min = Integer.parseInt(modifyProductMinText.getText());
            int max = Integer.parseInt(modifyProductMaxText.getText());
            boolean productAddSuccessful = true;

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

            Product modifiedProduct = new Product(Integer.parseInt(modifyProductIdText.getText()),
                    modifyProductNameText.getText(),
                    Double.parseDouble(modifyProductPriceText.getText()),
                    Integer.parseInt(modifyProductInventoryText.getText()),
                    Integer.parseInt(modifyProductMinText.getText()),
                    Integer.parseInt(modifyProductMaxText.getText()));


            if (productAddSuccessful) {

                for (Part part : modifyProductsPageAssociatedParts) {
                    modifiedProduct.addAssociatedPart(part);
                }

                Inventory.addProduct(modifiedProduct);
                Inventory.deleteProduct(selectedProduct);
                returnToMainScreenEvent(actionEvent);
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("Please enter corresponding values into the fields.");
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    /** The onModifyProductCancelButton method closes the Modify Products window and returns the
     * user to the main screen of the application without saving or changing any information.*/
    public void onModifyProductCancelButton(ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200, 600);
        stage.setTitle("C482 - Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }



    /** The returnToMainScreenEvent method returns the user to the main screen.
     *
     * This method is called within the onModifyProductSaveButton method in order to return to the main
     * application screen once the user has completed inputing the new data on a modified product. */
    private void returnToMainScreenEvent(ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200, 600);
        stage.setTitle("C482 - Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }


    /** The initialize method is the starting method for the ModifyProductController.
     *
     * This method obtains the data from the product selected on the main screen of the application
     * and displays the selected products current information in the text fields.
     *
     * This method also populates the Parts table with available parts and the Associated Parts
     * table with previously associated parts. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        selectedProduct = MainController.getModifyProductData();

        modifyProductIdText.setText(String.valueOf(selectedProduct.getId()));
        modifyProductNameText.setText(selectedProduct.getName());
        modifyProductInventoryText.setText(String.valueOf(selectedProduct.getStock()));
        modifyProductPriceText.setText(String.valueOf(selectedProduct.getPrice()));
        modifyProductMaxText.setText(String.valueOf(selectedProduct.getMax()));
        modifyProductMinText.setText(String.valueOf(selectedProduct.getMin()));


        modifyProductPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        modifyProductsPartTable.setItems(Inventory.getAllParts());

        associatedPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        modifyProductAssociatedPartsTable.setItems(selectedProduct.getAllAssociatedParts());

    }
}
