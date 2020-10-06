package App;

class Household
{
    // Class Member Variables
    private int houseID;
    private String houseName;
    // private User user;

    // Default Constructor
    Household()
    {
        houseID = 0;
        houseName = "";
        // user = null;
    }

    // Parameterized Constructor
    Household(int houseID, String name)
    {
        this.houseID = houseID;
        this.houseName = houseName;
        // this.user = user;
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
    
    //public User getUser() { return user; }

    // Setter Methods
    public void setID(int houseID)
    {
        this.houseID = houseID;
    }

    public void setName(String name)
    {
        this.houseName = houseName;
    }

    // public void setUser(User user) { this.user = user; }

    // Concept for associating a user with a household
    /*private boolean assocUser()
    {
        // If User class object references THIS, return true;
        // else return false;
    }*/

    // Concept for associating a user's task with the household
    /*public void assocTask()
    {
        // If User class object creates a new task, associate it with the household
    }*/
}