package main.models;

public class Transaction implements java.io.Serializable {
    private static final long serialVersionUID = 7L;
    private String name;
    private String mobileNumber;
    private String emailAddress;
    private String TID;
    private Session session;
    private String[] seats;
    private double price;

    public Transaction() {

    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }
    
}
