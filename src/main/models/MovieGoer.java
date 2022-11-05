package main.models;

import java.util.ArrayList;
import java.util.List;

public class MovieGoer implements java.io.Serializable {
    private static final long serialVersionUID = 8L;
    private String name;
    private String mobile;
    private String email;
    private String cardNumber;
    private List<Transaction> transactions;

    public MovieGoer(String name, String mobile, String email, String cardNumber) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Name cannot be blank or null");
        if (mobile == null || mobile.isBlank()) 
            throw new IllegalArgumentException("Mobile number cannot be blank or null");
        if (email == null || email.isBlank() )
            throw new IllegalArgumentException("Email cannot be blank or null");
        if (cardNumber == null || cardNumber.length() < 8 || cardNumber.length() > 19)
            throw new IllegalArgumentException("Card number must be 8 to 19 digits long");
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.cardNumber = cardNumber;
        transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getMaskedCardNumber() {
        return "*" + cardNumber.substring(cardNumber.length()-4);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 8 || cardNumber.length() > 19)
            throw new IllegalArgumentException("Card number must be 8 to 19 digits long");
        this.cardNumber = cardNumber;
    }   
}
