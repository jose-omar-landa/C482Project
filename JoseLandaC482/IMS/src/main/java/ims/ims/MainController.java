package ims.ims;

import imsclasses.Product;
import imsclasses.Part;
import imsclasses.Inventory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class is the main controller for the MainScreen.fxml file. This class contains the methods
 * for the functionality of all aspects of the main screen of the application.*/
public class MainController implements Initializable
{
    public TableColumn<Product, Integer> productIdColumn;
    public TableColumn<Product, String> productNameColumn;
    public TableColumn<Product,Integer> productInventoryLevelColumn;
    public TableColumn<Product, Double> productPriceColumn;
    public TextField productSearch;
    public TextField partSearch;
    public TableColumn<Part, Integer> partIdColumn;
    public TableColumn<Part, String> partNameColumn;
    public TableColumn<Part, Double> partPriceColumn;
    public TableColumn<Part, Integer> partInventoryLevelColumn;
    public Button addPartsButton;
    public Button deletePartsButton;
    public Button modifyPartsButton;
    public Button addProductsButton;
    public Button deleteProductsButton;
    public Button modifyProductsButton;
    public Button exitAppButton;

    public TableView<Product> mainScreenProductTable;

    public TableView<Part> mainScreenPartTable;

    public static Part modifyPartData;

    public static Product modifyProductData;

    private static boolean firstTime = true;



    /** The addTestData method contains the initial test data to be loaded into the parts and products tables
     * in the application.
     *
     * This method will run only if it is the first time the Inventory Management System
     * application has opened.*/
    public void addTestData()
    {
        if(!firstTime) {
            return;
        }
        firstTime = false;

        Part Screw = new Part(0,"screw",0.99, 12, 1, 22);
        Inventory.addPart(Screw);

        Part Bolt = new Part(1, "bolt", 0.97, 12, 0, 50);
        Inventory.addPart(Bolt);

        Product simpleShed = new Product(0, "simple shed", 500.00, 5, 0, 5);
        Inventory.addProduct(simpleShed);

        Product luxuryShed = new Product(1, "luxury shed", 1500.00, 2, 0, 3);
        Inventory.addProduct(luxuryShed);
    }



    /** The initialize method is the first method that is run when the main screen of the Inventory Management
     * System is opened.
     *
     * This method calls the addTestData function that creates the initial parts and products, and then adds
     * the initial parts and products to their respective tables.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        addTestData();

        mainScreenPartTable.setItems(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        mainScreenProductTable.setItems(Inventory.getAllProducts());
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }



    /** The onProductSearch method creates the search bar and functionality of the search bar above the Product table.
     *
     * This method allows the user to search for products by either the ID number of the product
     * or by the name of the product. Partial strings of the name are acceptable for the search
     * functionality as well. An alert will pop-up for the user if the product searched is not found. */
    public void onProductSearch(ActionEvent actionEvent)
    {
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        ObservableList<Product> foundProduct = FXCollections.observableArrayList();
        String stringSearch = productSearch.getText();

        for (Product searchedProduct : allProducts) {
            if (String.valueOf(searchedProduct.getId()).contains(stringSearch) || String.valueOf(searchedProduct.getName()).contains(stringSearch)) {
                foundProduct.add(searchedProduct);
            }
        }
        mainScreenProductTable.setItems(foundProduct);

        if (foundProduct.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("NOT FOUND");
            alert.setContentText("Searched product not found.");
            alert.showAndWait();
        }
    }



    /** The onPartSearch method creates the search bar and functionality of the search bar above the Part table.
     *
     * This method allows the user to search for part by either the ID number of the part
     * or by the name of the part. Partial strings of the name are acceptable for the search
     * functionality as well. An alert will pop-up for the user if the searched part is not found. */
    public void onPartSearch(ActionEvent actionEvent)
    {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> foundPart = FXCollections.observableArrayList();
        String stringSearcher = partSearch.getText();

        for (Part searchedPart : allParts) {
            if (String.valueOf(searchedPart.getId()).contains(stringSearcher) || String.valueOf(searchedPart.getName()).contains(stringSearcher)) {
                foundPart.add(searchedPart);
            }
        }
        mainScreenPartTable.setItems(foundPart);

        if (foundPart.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("NOT FOUND");
            alert.setContentText("Searched part not found.");
            alert.showAndWait();
        }
    }



    /** The onAddPartsButton method opens the Add Parts form that allows the user to add a new part
     * to the Part table.  */
    public void onAddPartsButton(ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("AddParts.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 650);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }



    /** The onDeletePartsButton method allows the user to delete a part from the Part Table.
     *
     * This method checks to see which part is selected, and if no part is selected, an alert will pop-up
     * notifying the user that a part needs to be selected in order to be deleted.
     *
     * A confirmation alert will also display a pop-up message to ensure that the user is deleting the
     * correct part. Once the part to be deleted is confirmed, the part is removed from the Inventory. */
    public void onDeletePartsButton(ActionEvent actionEvent)
    {
        Part selectedPart = mainScreenPartTable.getSelectionModel().getSelectedItem();

        if(selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a Part to be deleted.");
            alert.showAndWait();
        }
        else {
           Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
           alert.setTitle("Delete Part Confirmation");
           alert.setContentText("Are you sure you want to delete this Part?");
            Optional<ButtonType> partDeleteDecision = alert.showAndWait();

            if (partDeleteDecision.isPresent() && partDeleteDecision.get() == ButtonType.OK) {
                Inventory.deletePart(selectedPart);
            }
        }
    }



    /** The getModifyPartData method is used to select the part data that is needing to be
     * modified by the user.
     *
     * @return returns the part data to be modified. */
    public static Part getModifyPartData()
    {
        return modifyPartData;
    }



    /**The onModifyPartsButton method opens the Modify Parts form and allows the user to change
     * data such as the part name, inventory level, minimum inventory amounts, maximum
     * inventory amounts, company name, or machine id number.
     *
     * If a part is not selected in the Part table prior to the modify button being pressed,
     * an alert with be shown to the user reminding them to select a part. The part data is automatically
     * populated with the existing data. */
    public void onModifyPartsButton(ActionEvent actionEvent) throws IOException
    {
        modifyPartData = mainScreenPartTable.getSelectionModel().getSelectedItem();

        if(modifyPartData == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a Part to be modified.");
            alert.showAndWait();
            return;
        }
        else {
            Parent root = FXMLLoader.load(getClass().getResource("ModifyParts.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 800, 650);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        }
    }



    /** The onAddProductsButton method opens the Add products form for the user once the
     * Add button below the Products table is pressed. */
    public void onAddProductsButton(ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("AddProducts.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 550);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }




    /** The onDeleteProductsButton method allows the user to delete a product from the Products table.
     *
     * If a product is not selected, an alert message will be displayed notifying the user to select a
     * product that is to be deleted. A confirmation alert message will then display to the user in order
     * to confirm that the correct product is being deleted.
     *
     * When the user clicks the yes confirmation, a final check is performed to see if the product contains
     * any associated parts. If the product does contain associated parts, an alert message is displayed to
     * the user stating that the product contains one or more associated parts and cannot be deleted.
     *
     * If no associated parts are found, the product is then deleted from the Inventory and removed from
     * the Products table. */

    public void onDeleteProductsButton(ActionEvent actionEvent)
    {
        Product selectedProduct = mainScreenProductTable.getSelectionModel().getSelectedItem();

        if(selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a Product to be deleted.");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Product Delete Confirmation");
            alert.setContentText("Are you sure you want to delete this Product?");
            Optional<ButtonType> productDeleteDecision = alert.showAndWait();

            if (productDeleteDecision.isPresent() && productDeleteDecision.get() == ButtonType.OK) {

                ObservableList<Part> associatedParts = selectedProduct.getAllAssociatedParts();

                if (associatedParts.size() >= 1) {
                    Alert associatedPartAlert = new Alert(Alert.AlertType.ERROR);
                    associatedPartAlert.setContentText("This product contains one or more associated parts! Product cannot be deleted!");
                    associatedPartAlert.showAndWait();
                    return;
                }
                Inventory.deleteProduct(selectedProduct);
            }
        }
    }



    /** The getModifyProductData method is used to select the product data that is needing
     *  to be modified by the user.
     *
     *  @return returns the product data to be modified. */
    public static Product getModifyProductData()
    {
        return modifyProductData;
    }



    /** The onModifyProductsButton method opens the Modify Products form and allows the user to change
     * data such as the product name, inventory level, minimum inventory amounts, and maximum inventory amounts.
     *
     * If a product is not selected in the Product table prior to the modify button being pressed,
     * an alert with be shown to the user reminding them to select a product. The product data is automatically
     * populated with the existing data. */
    public void onModifyProductsButton(ActionEvent actionEvent) throws IOException
    {
        modifyProductData = mainScreenProductTable.getSelectionModel().getSelectedItem();

        if(modifyProductData == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a Product to be modified.");
            alert.showAndWait();
            return;
        }
        else {
            Parent root = FXMLLoader.load(getClass().getResource("ModifyProducts.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 550);
            stage.setTitle("Modify Product");
            stage.setScene(scene);
            stage.show();
        }
    }



    /** The onExitAppButton method allows the user to close the Inventory Management Application by clicking on the
     * Exit button on the bottom right of the screen.
     *
     * When the Exit button is clicked, an alert message is displayed to the user in order to confirm that the user
     * actually wants to exit and close the program. If the user clicks the Yes option, the Inventory Management
     * System is then closed.*/
    public void onExitAppButton(ActionEvent actionEvent)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Inventory Management System?");
        alert.setContentText("Are you sure you want to exit the Inventory Management System?");
        Optional<ButtonType> exitProgramDecision = alert.showAndWait();

        if (exitProgramDecision.isPresent() && exitProgramDecision.get() == ButtonType.OK) {
           System.exit(0);
        }
    }

}