package imsclasses;


/** The InHouse class creates an InHouse object that extends the attributes of the Part class. */
public class InHouse extends Part{

    private int machineId;



    /** The InHouse method creates an InHouse object.
     * @param id This is the parameter for the part ID.
     * @param name This is the parameter for the part name.
     * @param price This is the parameter for the part price.
     * @param stock This is the parameter for the part stock/inventory level.
     * @param min This is the parameter for the part minimum inventory level.
     * @param max This is the parameter for the part maximum inventory level.
     * @param machineId This is the parameter for the part machine ID. */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId)
    {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }



    /** The getMachineID method returns the associated machine ID.
     *
     * @return returns the associated machine ID. */
    public int getMachineId()
    {
        return machineId;
    }



    /** The setMachineID sets a machine ID.
     *
     * @param machineId sets the machineID parameter.*/
    public void setMachineId(int machineId)
    {
        this.machineId = machineId;
    }
}
