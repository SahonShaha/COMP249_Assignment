// ---------------------------------------------------------
// Assignment: 1
// Question: 1
// Written by: Sahon Shaha 40339419
// ---------------------------------------------------------

package Client;

public class Client {
    private static int count = 1001; // Represents the amount of objects created. Will be used to create the ID
    private String clientID;
    private String firstName;
    private String lastName;
    private String email;

    // Default Constructor
    public Client(){};

    // Parametrized Constructor
    public Client(String firstName, String lastName, String email) {
        this.clientID = "C" + count++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // Copy Constructor
    public Client(Client client) {
        this.clientID = "C" + count++;
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
    }

    public String toString() {
        return "Client ID: " + this.clientID + "\nFirst Name: " + this.firstName + "\nLast Name: " + this.lastName
                + "\nE-Mail Address: " + this.email + "\n";
    }

    public boolean equals(Client client) {
        // Ensuring the passed object is not null
        if (client == null) {
            return false;
        }

        // Ensuring the passed object is the same type
        if (client.getClass() != this.getClass()) {
            return false;
        }

        if (this.firstName.equals(client.getFirstName()) && this.lastName.equals(client.getLastName())
        && this.email.equals(client.getEmail())) {
            return true;
        }
        else {
            return false;
        }
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
