
public class Name {
    private String firstName;
    private String lastName;

    public Name(){
        firstName = "";
        lastName = "";
    }
    public Name(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }
    void setName(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }
    String getFirstName(){
        return firstName;
    }
    String getLastName(){
        return lastName;
    }
    void setFirstName(String firstName){
        this.firstName = firstName;
    }
    void setLastName(String lastName){
        this.lastName = lastName;
    }
}