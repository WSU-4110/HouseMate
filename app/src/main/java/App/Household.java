package App;

import java.util.Scanner;

public class Household
{
    // Class Variables
    private int houseID;
    private String houseName;

    // Default Constructor
    Household()
    {
        houseID = 0;
        houseName = "";
    }

    // Parameterized Constructor
    Household(int houseID, String name)
    {
        this.houseID = houseID;
        this.houseName = houseName;
    }

    // Getter Methods
    public int getID()
    {
        return houseID;
    }

    public String getName()
    {
        return houseName;
    }

    // Setter Methods
    public void setID(int houseID)
    {
        this.houseID = houseID;
    }

    public void setName(String name)
    {
        this.houseName = houseName;
    }

    // Associate user w/ household
    private boolean assocUser()
    {
        // ?? Stuff in here ??
    }
}
