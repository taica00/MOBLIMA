package main.models;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * Represents a Movie-goer who has purchased movie ticket(s).
 * A movie-goer can book tickets for any movie session.
 * @author Tai Chen An
 * @version 1.0 
 * @since 2022-11-06 
 */

public class MovieGoer implements java.io.Serializable {
    private static final long serialVersionUID = 8L;

    /**
     * The name of this movie-goer.
     */
    private String name;

    /**
     * The mobile number of this movie-goer.
     */
    private String mobile;

    /**
     * The email address of this movie-goer.
     */
    private String email;

    /**
     * The card number of this movie-goer to be used for payment.
     */
    private String cardNumber;

    /**
     * A list of {@link Transaction} that this movie goer has made.
     */
    private List<Transaction> transactions;

    /**
     * Creates a new movie-goer with the given fields when the user makes a booking for the first time.
     * None of the fields should be empty.
     * The card number should 8 to 19 digits long.
     * @param name
     * @param mobile
     * @param email
     * @param cardNumber
     */
    public MovieGoer(String name, String mobile, String email, String cardNumber) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Name cannot be blank or null");
        if (mobile == null || mobile.isBlank() || !StringUtils.isNumeric(mobile)) 
            throw new IllegalArgumentException("Mobile number cannot be blank or null and must be numeric");
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
    
    /** 
     * Adds a {@link Transaction} to the list of transactions for this movie-goer.
     * @param transaction
     */
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
    
    /** 
     * @return String
     */
    public String getName() {
        return name;
    }

    
    /** 
     * @return String
     */
    public String getMobile() {
        return mobile;
    }

    
    /** 
     * @return String
     */
    public String getEmail() {
        return email;
    }

    
    /** 
     * @return String
     */
    public String getMaskedCardNumber() {
        return "*" + cardNumber.substring(cardNumber.length()-4);
    }

    
    /** 
     * @return List<Transaction>
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }

    
    /** 
     * @param cardNumber
     */
    public void setCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 8 || cardNumber.length() > 19)
            throw new IllegalArgumentException("Card number must be 8 to 19 digits long");
        this.cardNumber = cardNumber;
    }   
}
