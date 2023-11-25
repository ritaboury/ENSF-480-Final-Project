package role;
import util.*;

public class RegisteredCustomer implements Person{
    private Name name; 
    private String email;
    
    public RegisteredCustomer(Name name, String email){
        this.name = name;
        this.email = email;
    }
    
    @Override
    public Name getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public void setName(Name name){
        this.name = name;
    }

    public void setEmail(String email){
        this.email = email;
    }
    public Name name() {
        return null;
    }
}