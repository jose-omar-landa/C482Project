package imsclasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/** The Product class creates a Product object. */
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;


    /** The Product method creates a product object.
     * @param id This is the ID of the product.
     * @param name This is the name of the product.
     * @param price This is the price of the product.
     * @param stock This is the inventory level/stock level of the product.
     * @param min This is the minimum inventory level of the product.
     * @param max This is the maximum inventory level of the product. */
    public Product(int id, String name, double price, int stock, int min, int max)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }


    /** The getID method obtains the ID of the product.
     *
     * @returns returns the product ID. */
    public int getId()
    {
        return id;
    }


    /** The getName method obtains the product name.
     *
     * @return returns the product name. */
    public String getName()
    {
        return name;
    }


    /** The getPrice method obtains the product price.
     *
     * @return returns the product price. */
    public double getPrice()
    {
        return price;
    }


    /** The getStock method obtains the product stock/inventory level.
     *
     * @return returns the product inventory level. */
    public int getStock()
    {
        return stock;
    }


    /** The getMin method obtains the product minimum inventory level.
     *
     * @return returns the product minimum inventory level. */
    public int getMin()
    {
        return min;
    }


    /** The getMax method obtains the product maximum inventory level.
     *
     * @return returns the product maximum inventory level. */
    public int getMax()
    {
        return max;
    }


    /** The setId method sets the product ID number.
     *
     * @param id The parameter for the product ID. */
    public void setID(int id)
    {
        this.id = id;
    }


    /** The setName method sets the product name.
     *
     * @param name The parameter for the product name. */
    public void setName(String name)
    {
        this.name = name;
    }


    /** The setPrice method sets the price of the product.
     *
     * @param price The parameter for the product price. */
    public void setPrice(double price)
    {
        this.price = price;
    }


    /** The setStock method sets the inventory level/stock of the product.
     *
     * @param stock The parameter for the product stock/inventory level. */
    public void setStock(int stock)
    {
        this.stock = stock;
    }


    /** The setMin method sets the minimum inventory level of the product.
     *
     * @param min The parameter for the product minimum stock level. */
    public void setMin(int min)
    {
        this.min = min;
    }


    /** The setMax method sets the maximum inventory level of the product.
     *
     * @param max The parameter for the product maximum inventory level. */
    public void setMax(int max)
    {
        this.max = max;
    }


    /** The addAssociatedPart method adds an associated part to the product.
     *
     * @param part The parameter for the part that is added to the productas an associated part. */
    public void addAssociatedPart(Part part)
    {
        this.associatedParts.add(part);
    }


    /** The deleteAssociatedPart method deletes a selected associated part from
     * the product.
     *
     * @param selectedAssociatedPart The parameter for the associated part that is to be removed from the product
     *
     * @return returns true if a part is selected part is within the associated parts of the product and
     * returns false if the part is not associated to the product. */
    public Boolean deleteAssociatedPart(Part selectedAssociatedPart)
    {
        if(associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        else {
            return false;
        }
    }

    
    /** The getAllAssociatedParts method obtains all the parts associated to the product.
     *
     * @return returns the parts associated to the product. */
    public ObservableList<Part> getAllAssociatedParts()
    {
        return associatedParts;
    }





}
