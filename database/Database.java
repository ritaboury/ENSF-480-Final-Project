package database;
import com.owen_guldberg.gmailsender.GMailer;

import util.*;
import role.*;
import flightInfo.*;
import java.sql.*;
import java.util.ArrayList;
/**
     *IMPLEMENTS SINGLETON DESIGN PATTERN
     * Constructs a new Database object and connects to the database specified
     * by the URL, username, and password instance variables.
     *
     * @throws SQLException if there is an error connecting to the database
     */
public class Database {
     private Connection dbConnection;
    public static Database onlyInstance;
    private ResultSet results;
    private final String URL = "jdbc:mysql://localhost:3306/skyward_bound"; 
    private final String USERNAME = "root";
    private final String PASSWORD = "Root";
    private ArrayList<Location> locations = new ArrayList<Location>();
    private ArrayList<RegisteredCustomer> registeredUsers = new ArrayList<RegisteredCustomer>();
    private ArrayList<Aircraft> aircrafts = new ArrayList<Aircraft>(); 
    private ArrayList<Flight> flights = new ArrayList<Flight>(); 
    private ArrayList<CrewMember> crew = new ArrayList<CrewMember>(); 
    private ArrayList<Ticket> tickets = new ArrayList<Ticket>();
    

/**
 * Constructs a new Database object and connects to the database specified
 * by the URL, username, and password instance variables.
 * @throws SQLException if there is an error connecting to the database
 */
    private Database() throws SQLException {
        this.dbConnection = connectToDatabase();
        readLocationData();
        readRegisteredUsers();
        readAircraftData();
        readFlightData();
        readCrewMemberData(); 
    }


      /**
     * Ensures that only one instance of the Database class exists and returns it (SINGLETON).
     *
     * @return the only instance of the Database class
     */
    public static Database getOnlyInstance(){
        if(onlyInstance == null){
            try{
                onlyInstance = new Database();
            }
            catch(Exception e){
                e.printStackTrace();
            }
            
        }
        return onlyInstance; 
    }

/**
 * Establishes a connection to the database using the URL, username, and password instance variables.
 * @return a Connection object representing the connection to the database
 * @throws SQLException if there is an error connecting to the database
 */
private Connection connectToDatabase() throws SQLException {
        try{
            dbConnection = DriverManager.getConnection(getURL(), getUsername(), getPassword());
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return dbConnection;
    }


// /** WORKS
//  * Reads Registered User data from the database and populates the registeredUsers ArrayList.
//  * @throws SQLException if there is an error reading data from the database
//  */
private void readRegisteredUsers() throws SQLException{
        try {
            Statement stmt = dbConnection.createStatement();
            String query = "SELECT * FROM REGISTEREDUSERS";
            ResultSet results = stmt.executeQuery(query);
            
            while (results.next()) {
                String first = results.getString("FName");
                String last = results.getString("LName");
                String email = results.getString("Email");
                String password = results.getString("Password");

                int house = results.getInt("HouseNum");
                String street= results.getString("Street");
                String city = results.getString("City");
                String country = results.getString("Country");
                String postal= results.getString("PostalCode");
                String cc= results.getString("CreditCardNumber");
                int cvv = results.getInt("CVV"); 

                Name name = new Name(first, last);
                Address a = new Address(house, street, city, country, postal); 
                Payment p = new Payment(cc, cvv);

                RegisteredCustomer user = new RegisteredCustomer(name, email, password, a, p);
                registeredUsers.add(user);
            }
            stmt.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Reads location data from the database and populates the locations ArrayList.
     */
    private void readLocationData(){
        try{
            Statement myStmt = this.dbConnection.createStatement();
            String query = "SELECT * FROM LOCATIONS";
            results = myStmt.executeQuery(query);
            while(results.next()){
              int locID = results.getInt("LocationID");
              String airportName= results.getString("AirportName");
              String city = results.getString("City");
              String country = results.getString("Country");
    
              Location tmp = new Location(airportName, city, country); //creates food item with each item in the database
              this.locations.add(tmp); //stores food item in linkedlist of food objects
              }
              myStmt.close(); 
            }catch (SQLException ex) {
                //ex.printStackTrace();
             }
    }

    


    //** WORKS
//  * Reads Aircraft data from the database and populates the Aircraft ArrayList.
//  */
    private void readAircraftData(){
        try{
            Statement myStmt = this.dbConnection.createStatement();
            String query = "SELECT * FROM AIRCRAFT";
            results = myStmt.executeQuery(query);
            while(results.next()){
              int airID = results.getInt("AircraftID");
              String airName= results.getString("Name");
              int capacity = results.getInt("Capacity");

              Aircraft tmp = new Aircraft(airID, airName, null,capacity); //creates 
              this.aircrafts.add(tmp); //stores
              }
              myStmt.close(); 
            }catch (SQLException ex) {
                //ex.printStackTrace();
             }

    }

// /** WORKS
//  * Reads FLight data from the database and populates the flights ArrayList.
//  */
    private void readFlightData(){
        try{
            Statement myStmt = this.dbConnection.createStatement();
            String query = "SELECT * FROM FLIGHTS";
            ResultSet results = myStmt.executeQuery(query);
            while(results.next()){
              int id = results.getInt("FlightID");
              String flightNum = results.getString("FlightNum");
              String flightDate = results.getString("FlightDate");
              String originName = results.getString("Origin");
              String destinationName = results.getString("Destination");
              String aircraft= results.getString("Aircraft");
              String departTime = results.getString("DepartureTime");
              String arriveTime = results.getString("ArrivalTime");
              String flightTime = results.getString("FlightTime");

              Location origin = new Location();
              Location destination = new Location(); 
              for (int i = 0; i < locations.size(); i++){
                if(locations.get(i).getAirportName().equals(originName) == true){
                  origin = locations.get(i); 
                }
                else if(locations.get(i).getAirportName().equals(destinationName) == true){
                  destination = locations.get(i); 
                }
              }

              Aircraft a = new Aircraft();
              for (int i = 0; i < aircrafts.size(); i++){
                if(aircrafts.get(i).getName().equals(aircraft) == true){
                    a = aircrafts.get(i); 
                }
              }

               

              Flight tmp= new Flight(origin,destination, flightNum, flightDate, departTime, arriveTime, flightTime, a); 
              this.flights.add(tmp);
              
            }
            myStmt.close(); 
            }catch (SQLException ex) {
                //ex.printStackTrace();
             }
             
    }

// /** WORKS
//  * Reads crew member data from the database and populates the crew ArrayList.
//  */
    private void readCrewMemberData(){

        try{
            Statement myStmt = this.dbConnection.createStatement();
            String query = "SELECT * FROM CREWMEMBER";
            results = myStmt.executeQuery(query);
            while(results.next()){
              int crewID = results.getInt("CrewID");
              String FName= results.getString("FName");
              String LName= results.getString("LName");
              String email= results.getString("Email");
              String job= results.getString("Job");
              int house = results.getInt("HouseNum");
              String street= results.getString("Street");
              String city = results.getString("City");
              String country = results.getString("Country");
              String postal= results.getString("PostalCode");
    
              Name n = new Name(FName, LName);//creates food item with each item in the database
              Address ad = new Address(house, street, city, country, postal); //dont need but maybe could add to airline agent if needed


                CrewMember tmp = new CrewMember(n, email, job); 
                crew.add(tmp); 
              }
              myStmt.close(); 
            }catch (SQLException ex) {
                //ex.printStackTrace();
             }

    }

    private void readTicketData(){
        try{
            Statement myStmt = this.dbConnection.createStatement();
            String query = "SELECT * FROM TICKETS";
            ResultSet results = myStmt.executeQuery(query);
            while(results.next()){
                int ticketID = results.getInt("TicketID");
                int seatNum = results.getInt("seatNum");
                int price = results.getInt("price");
                String flightNumber = results.getString("FlightNumber");
                boolean insurance = results.getBoolean("Insurance");
                String clientEmail = results.getString("ClientEmail");
                String departureTime = results.getString("DepartureTime");
                String seatClass = results.getString("classSeat");

                // Create a Ticket object using the retrieved data

                Ticket tmp = new Ticket(seatNum, price, flightNumber, insurance, departureTime, seatClass);
                
                for (int i = 0; i < registeredUsers.size(); i++){
                    if(registeredUsers.get(i).getEmail().equals(clientEmail) == true){
                        registeredUsers.get(i).addTicket(tmp);
                    }
                }
                // Add the Ticket object to your list or perform any other necessary operations
                this.tickets.add(tmp);
            }
            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public ArrayList<Ticket> getTicketData(){
        return this.tickets;
    }

    public boolean addTicket(Ticket t, String userEmail){
        boolean success = false; 
        try{
        
                String query = "INSERT INTO TICKETS(seatNum, price, FlightNumber, insurance, Email, DepartureTime, classSeat) "
                + "VALUES(?,?,?,?,?,?,?)";
            
                PreparedStatement myStmt = dbConnection.prepareStatement(query);
   
               myStmt.setInt(1, t.getSeatNum());
               myStmt.setDouble(2, t.getPrice());
               myStmt.setString(3, t.getFlightNumber());
               myStmt.setBoolean(4, t.getHasCancellationInsurance());
               myStmt.setString(5, userEmail);
               myStmt.setString(6, t.getDepartureTime());
               myStmt.setString(7, t.getClassSeat());

               int rowCount = myStmt.executeUpdate();
               if(rowCount == 0)
               {
                 return false;
               }
               else{
                    success = true;
                    tickets.add(t); 
                    for (int i = 0; i < registeredUsers.size(); i++){
                        if(registeredUsers.get(i).getEmail().equals(userEmail) == true){
                            // registeredUsers.get(i).addTicket(t);
                        }
                    }
                }
                myStmt.close();

        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return success;
    }

     /**
     * Gets the ArrayList of airport locations.
     *
     * @return the locations ArrayList
     */
    public ArrayList<Location> getLocationData(){
        return this.locations; 
    }

     /**
     * Gets the ArrayList of registered customers.
     *
     * @return the registeredUsers ArrayList
     */
    public ArrayList<RegisteredCustomer> getRegisteredCustomerData(){
        return this.registeredUsers; 
    }

     /**
     * Gets the ArrayList of aircraft.
     *
     * @return the aircrafts ArrayList
     */
    public ArrayList<Aircraft> getAircraftData(){
        return this.aircrafts; 
    }

     /**
     * Gets the ArrayList of flights.
     *
     * @return the flights ArrayList
     */
    public ArrayList<Flight> getFlightData(){
        return this.flights; 
    }

     /**
     * Gets the ArrayList of airline crew members.
     *
     * @return the crew ArrayList
     */
    public ArrayList<CrewMember> getCrewMemberData(){
        return this.crew; 
    }

     /**
     * Cancels a flight in the database.
     *
     * @param f the Flight object representing the flight to be canceled
     * @return true if the cancellation is successful, false otherwise
     */
      public boolean cancelFlight(Flight f){
        boolean success = false; 
        try{
            String query = "DELETE FROM FLIGHTS WHERE FlightNum = ?"; //matches by flight number 
               PreparedStatement myStmt = dbConnection.prepareStatement(query);
   
               myStmt.setString(1,f.getFlightNum());
            
               int rowCount = myStmt.executeUpdate();
               //System.out.println("Rows affected: " + rowCount);
               if(rowCount == 0)
               {
                 return false;
               }
               else{
                success = true;
                }
               myStmt.close();
             }
             catch (SQLException ex) {
               ex.printStackTrace();
               return false;
             }
             
             for (int i = 0; i < flights.size(); i++){
                // notifying passengers
                if (f == flights.get(i)){
                    for(int j = 0; j < flights.get(i).getPassengers().size();j++){
                        try {
                            RegisteredCustomer passenger = flights.get(i).getPassengers().get(j);
                            GMailer gMailer = new GMailer();
                            
                            String flightNum = flights.get(i).getFlightNum();
                            String flightDate = flights.get(i).getFlightDate();
                            String destination = flights.get(i).getDestination().getCity();
                            String orgin = flights.get(i).getOrigin().getCity();
                            String emailSubject = "Flight Cancellation Notification";
                            String emailBody = "Hello,\n\n"
                                    + "We regret to inform you that your flight ("
                                    + flightNum + ") on " + flightDate + " from "
                                    + orgin + " to " + destination
                                    + " has been cancelled.\n\n"
                                    + "As a token of our appreciation, we're delighted to offer you a special promotion. Use the promo code below for 50% off your next flight!\n\n"
                                    + "Promo Code: MEMEBR50\n\n"
                                    + "We look forward to serving you on board and providing you with a great travel experience.\n\n"
                                    + "Best regards,\n"
                                    + "The Skyward Bound Team";
                
                            gMailer.sendMail(passenger.getEmail(), emailSubject, emailBody);
                     } catch (Exception e) {
                                e.printStackTrace();
                            }
                    }
                    flights.remove(i);
                }
             }
             return success;
         }
     /**
     * Updates the Database by clearing existing data and re-reading from the database.
     *
     * @throws SQLException if there is an error updating the database
     */
    public void updateDatabase() throws SQLException{
        try{
            locations.clear(); 
            readLocationData();
            registeredUsers.clear();
            readRegisteredUsers();
            aircrafts.clear();
            readAircraftData();
            flights.clear();
            readFlightData();
            crew.clear(); 
            readCrewMemberData(); 
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
    }

     /**
     * Adds a new registered user to the database and updates the registeredUsers ArrayList.
     *
     * @param r the RegisteredCustomer object to be added
     * @return true if the addition is successful, false otherwise
     */
     // saving to db REGISTEREDUSERS(FName, LName, Email, Password, HouseNum, Street, City, Country, PostalCode, CreditCardNumber, CVV)
     public boolean saveUser(RegisteredCustomer r){
        boolean success = false; 
        try{
            String query = "INSERT INTO REGISTEREDUSERS(FName,LName, Email, Password, HouseNum, Street, City, Country, PostalCode, CreditCardNumber, CVV) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?)";

                PreparedStatement myStmt = dbConnection.prepareStatement(query);
   
               myStmt.setString(1, r.getName().getFirstName());
               myStmt.setString(2,r.getName().getLastName());
               myStmt.setString(3, r.getEmail());
               myStmt.setString(4, r.getPassword());
               myStmt.setString(5, Integer.toString(r.getAddress().getHouseNum()));
               myStmt.setString(6, r.getAddress().getStreetName());
               myStmt.setString(7, r.getAddress().getCity());
               myStmt.setString(8, r.getAddress().getCountry());
               myStmt.setString(9, r.getAddress().getPostalCode());
               myStmt.setString(10, null);
               myStmt.setString(11, null);

               int rowCount = myStmt.executeUpdate();
               //System.out.println("Rows affected: " + rowCount);
               if(rowCount == 0)
               {
                 return false;
               }
               else{
                success = true;
                registeredUsers.add(r); 
                }
                myStmt.close();

        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return success;
    }

    public boolean updateUser(RegisteredCustomer r) {
        boolean success = false;
        try {
            String query = "UPDATE REGISTEREDUSERS SET FName = ?, LName = ?, Password = ?, HouseNum = ?, Street = ?, City = ?, Country = ?, PostalCode = ?, CreditCardNumber = ?, CVV = ? WHERE Email = ?";
    
            PreparedStatement myStmt = dbConnection.prepareStatement(query);
    
            myStmt.setString(1, r.getName().getFirstName());
            myStmt.setString(2, r.getName().getLastName());
            myStmt.setString(3, r.getPassword());
            myStmt.setInt(4, r.getAddress().getHouseNum());
            myStmt.setString(5, r.getAddress().getStreetName());
            myStmt.setString(6, r.getAddress().getCity());
            myStmt.setString(7, r.getAddress().getCountry());
            myStmt.setString(8, r.getAddress().getPostalCode());
    
            myStmt.setString(9, r.getPayment().getCreditCardNumber());
            myStmt.setString(10, Integer.toString(r.getPayment().getCVV()));
            myStmt.setString(11, r.getEmail());
    
            int rowCount = myStmt.executeUpdate();
            if (rowCount > 0) {
                success = true;
            }
    
            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return success;
    }
    
    /**
     * Gets the URL of the database.
     * @return the URL of the database
     */
    public String getURL(){
        return this.URL;
    }

    /**
    * Gets the username used for authentication.
    * @return the username used for authentication
    */
    public String getUsername(){
        return this.USERNAME;
    }

    /**
     * Gets the password used for authentication.
     * @return the password used for authentication
     */
    public String getPassword(){
        return this.PASSWORD;
    }

    /**
    * Closes the database connection and result set.
    */
    public void close() {
        //close the connection and result set
        try{
            dbConnection.close();
            results.close();
        }
        //catch exception if database cannot be accessed or any other error
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
