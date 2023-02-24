package imsclasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/** The Inventory class creates an inventory model containing Parts and Products. */
public class Inventory
{

    private static  ObservableList<Part> allParts = FXCollections.observableArrayList();

    private static  ObservableList<Product> allProducts = FXCollections.observableArrayList();



    /** The addPart method adds a new part to the Inventory.
     *
     * @param newPart This the part to be added. */
    public static void addPart(Part newPart)
    {
        allParts.add(newPart);
    }


    /** The addProduct method adds a new product to the Inventory.
     *
     * @param newProduct This is the product to be added. */
    public static void addProduct(Product newProduct)
    {
        allProducts.add(newProduct);
    }


    /** The lookupPart method allows the user to search for a part by the part ID.
     *
     * @param partId This is the part ID that is going to be searched for.
     *
     * @return returns the part associated with the found part ID. */
    public static Part lookupPart(int partId)
    {
        Part foundPartId = null;

        for(Part part : allParts) {
            if(part.getId() == partId){
                foundPartId = part;
            }
        }
        return foundPartId;
    }


    /** The lookupProduct method allows the user to search for a product by the
     * product ID.
     *
     * @param productId This is the product ID that is going to be searched for.
     *
     * @return  returns the product associated with the found product ID. */
    public static Product lookupProduct(int productId)
    {
        Product foundProductId = null;

        for (Product product : allProducts) {
            if (product.getId() == productId) {
                foundProductId = product;
            }
        }
        return foundProductId;
    }


    /** The lookupPart method allows the user to search for a part by the part name.
     *
     * @param partName This is the part name that is going to be searched for.
     *
     * @return returns the part associated with the found part name. */
    public static ObservableList<Part> lookupPart(String partName)
    {
        ObservableList<Part> foundPartName = FXCollections.observableArrayList();

        for(Part part : allParts){
            if(part.getName().equals(partName)) {
                foundPartName.add(part);
            }
            return foundPartName;
        }
        return foundPartName;
    }


    /** The lookupProduct method allows the user to search for a part by the product name.
     *
     * @param productName This is the product name that is going to be searched for.
     *
     * @return returns the product associated with the found product name. */
    public static ObservableList<Product> lookupProduct(String productName)
    {
        ObservableList<Product> foundProductName = FXCollections.observableArrayList();

        for(Product product : allProducts){
            if(product.getName().equals(productName)) {
                foundProductName.add(product);
            }
            return foundProductName;
        }
        return foundProductName;
    }


    /** The updatePart method allows the user to update a selected part.
     *
     * @param index This is the index of the part to be updated.
     * @param selectedPart This is the parameter for the part that is selected. */
    public static void updatePart(int index, Part selectedPart)
    {
        allParts.set(index, selectedPart);
    }


    /** The updateProduct method allows the user to update a selected product.
     *
     * @param index This is the index of the product to be updated.
     * @param newProduct This is the parameter for the product that is selected. */
    public static void updateProduct(int index, Product newProduct)
    {
        allProducts.set(index, newProduct);
    }


    /** The deletePart method allows the user to delete a selected part.
     * @param selectedPart This is the part that is selected for deletion.
     * @return returns true if the selected part is within the allParts method and
     * returns false if the selected part is not within the allParts method. */
    public static Boolean deletePart(Part selectedPart)
    {
        if(allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        }
        else {
            return false;
        }
    }


    /** The deleteProduct method allows the user to delete a selected product.
     * @param selectedProduct This is the product that is selected for deletion.
     * @return returns true if the selected product is within the allProducts method and
     * returns false if the selected product is not within the allProducts method. */
    public static Boolean deleteProduct(Product selectedProduct)
    {
        if(allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        }
        else {
            return false;
        }
    }

    /** The getAllParts method creates a list of all the parts in the inventory.
     *
     * @return returns all the parts within the inventory. */
    public static ObservableList<Part> getAllParts()
    {
        return allParts;
    }


    /** The getAllProducts method creates a list of all the products in the inventory.
     *
     * @return returns all the products within the inventory. */
    public static ObservableList<Product> getAllProducts()
    {
        return allProducts;
    }

}
