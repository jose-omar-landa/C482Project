package ims.ims;

import imsclasses.Part;
import imsclasses.Product;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/** This class is the controller for the AddProducts.fxml file. This class contains the methods for the
 * functionality of the Add Products screen of the application. */
public class AddProductsController implements Initializable
{
    public TextField addProductInventoryText;
    public TextField addProductNameText;
    public TextField addProductIdText;
    public TextField addProductPriceText;
    public TextField addProductMaxText;
    public TextField addProductMinText;
    public TextField addProductSearchBar;
    public Button addProductAddButton;
    public Button addProductRemoveButton;
    public Button addProductSaveButton;
    public Button addProductCancelButton;
    public TableView<Part> addProductPartTable;
    public TableColumn<Part, Integer> addProductPartIdColumn;
    public TableColumn<Part, String> addProductPartNameColumn;
    public TableColumn<Part, Integer> addProductPartInventoryColumn;
    public TableColumn<Part, Double> addProductPartPriceColumn;
    public TableView<Part> addProductAssociatedPartTable;
    public TableColumn<Part, Integer> associatedPartIdColumn;
    public TableColumn<Part, String> associatedPartNameColumn;
    public TableColumn<Part, Integer>associatedPartInventoryColumn;
    public TableColumn<Part, Double> associatedPartPriceColumn;

    private ObservableList<Part> addProductsPageAssociatedParts = FXCollections.observableArrayList();

    public void onAddProductInventoryText(ActionEvent actionEvent) {
    }

    public void onAddProductNameText(ActionEvent actionEvent) {
    }

    public void onAddProductIdText(ActionEvent actionEvent) {
    }

    public void onAddProductPriceText(ActionEvent actionEvent) {
    }

    public void onAddProductMaxText(ActionEvent actionEvent) {
    }

    public void onAddProductMinText(ActionEvent actionEvent) {
    }


    /** The onAddProductSearchBar method allows the user to search for a part within the part table
     * on the Add Product form screen.
     *
     * The user can search for a part via a part ID or a part name. Partial strings of the part name are
     * acceptable for the search functionality.
     *
     * If a part being searched is not found, and error message is displayed to the user stating that the
     * part was not found. */
    public void onAddProductSearchBar(ActionEvent actionEvent)
    {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> foundPart = FXCollections.observableArrayList();
        String stringSearcher = addProductSearchBar.getText();

        for (Part searchedPart : allParts) {
            if (String.valueOf(searchedPart.getId()).contains(stringSearcher) || String.valueOf(searchedPart.getName()).contains(stringSearcher)) {
                foundPart.add(searchedPart);
            }
        }

        addProductPartTable.setItems(foundPart);

        if (foundPart.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("NOT FOUND");
            alert.setContentText("Searched part not found.");
            alert.showAndWait();
        }
    }



    /** The onAddProductAddButton method allows the user to select a part on the Part table and add it to the
     * associated parts table.
     *
     * If a part is not selected prior to clicking the Add button, an alert message is displayed to the
     * user stating that a part must be selected first. */
    public void onAddProductAddButton(ActionEvent actionEvent)
    {
        Part selectedPart = addProductPartTable.getSelectionModel().getSelectedItem();

        if(selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Please select a Part first!");
            alert.showAndWait();
        }
        else {
            addProductsPageAssociatedParts.add(selectedPart);
            addProductAssociatedPartTable.setItems(addProductsPageAssociatedParts);
        }
    }



    /** The onAddProductRemoveButton method allows the user to remove an associated part from the
     * associated parts table.
     *
     * If a part is not selected prior to clicking the Remove Associated Part button, an error message is
     * displayed to the user stating that a part must be selected first. Also displays a confirmation message
     * to the user to ensure the correct part is being removed. If the user clicks the Yes option, the
     * part is removed from the Associated Parts Table. */
    public void onAddProductRemoveButton(ActionEvent actionEvent)
    {
        Part selectedPart = addProductAssociatedPartTable.getSelectionModel().getSelectedItem();

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
                addProductsPageAssociatedParts.remove(selectedPart);
                addProductAssociatedPartTable.setItems(addProductsPageAssociatedParts);
            }
        }
    }



    /** The onAddProductSaveButton method takes the user input in the text fields on the Add Product form and
     * saves the information as a new Product object with an automatically generated Product ID.
     *
     * Multiple error messages are placed to ensure that all text fields
     * are appropriately filled out.
     *
     * Once the Save button is clicked, the information is saved and the product is added to the Product table
     * on the main screen of the application. The Add Product form is then closed and the main screen is opened
     * for the user. */
    public void onAddProductSaveButton(ActionEvent actionEvent)
    {
        try {
            int id = 0;
            for (Product product : Inventory.getAllProducts()) {
                if (product.getId() < id) {
                    id = product.getId();
                }
                else {
                    id++;
                }
            }

            String name = addProductNameText.getText();
            double price = Double.parseDouble(addProductPriceText.getText());
            int stock = Integer.parseInt(addProductInventoryText.getText());
            int min = Integer.parseInt(addProductMinText.getText());
            int max = Integer.parseInt(addProductMaxText.getText());

            if (name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Alert");
                alert.setContentText("Please enter a Product name.");
                alert.showAndWait();
            }
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
            }
            else {
                Product addedProduct = new Product(id, name, price, stock, min, max);

                for (Part part : addProductsPageAssociatedParts) {
                    addedProduct.addAssociatedPart(part);
                }

                Inventory.addProduct(addedProduct);

                Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1200, 600);
                stage.setTitle("C482 - Inventory Management System");
                stage.setScene(scene);
                stage.show();
            }
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("Please enter corresponding values into the fields.");
            alert.showAndWait();
    } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    /** The onAddProductCancelButton method closes the Add Products window and returns the
     * user to the main screen of the application without saving or changing any information. */
    public void onAddProductCancelButton(ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200, 600);
        stage.setTitle("C482 - Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }



    /** The initialize method sets the property values for each of the text fields so that
     * entered text is ready to be associated with the correct value. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        addProductPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        addProductPartTable.setItems(Inventory.getAllParts());

        associatedPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
