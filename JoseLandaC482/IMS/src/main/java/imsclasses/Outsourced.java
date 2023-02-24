package imsclasses;


/** The Outsourced class creates an Outsourced object that extends the attributes of the Part class.*/
public class Outsourced extends Part {

    private String companyName;


    /** The InHouse method creates an InHouse object.
     * @param id This is the parameter for the part ID.
     * @param name This is the parameter for the part name.
     * @param price This is the parameter for the part price.
     * @param stock This is the parameter for the part stock/inventory level.
     * @param min This is the parameter for the part minimum inventory level.
     * @param max This is the parameter for the part maximum inventory level.
     * @param companyName This is the parameter for the part company name.*/
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName)
    {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }


    /** The getCompanyName returns the associated company name.
     *
     * @return returns the associated company name. */
    public String getCompanyName()
    {
        return companyName;
    }


    /** The setCompanyName method sets a company name.
     *
     * @param companyName sets the company name parameter. */
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }
}
