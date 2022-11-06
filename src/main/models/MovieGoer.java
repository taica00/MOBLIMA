package main.models;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * Represents a Movie-goer who has purchased movie ticket(s).
 * A movie-goer can reuse the stored card number for future payments.
 * All payments are stored as transactions.
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
     * The mobile number must be 8 to 15 digits long.
     * The card number must be 8 to 19 digits long.
     * @param name
     * @param mobile
     * @param email
     * @param cardNumber
     */
    public MovieGoer(String name, String mobile, String email, String cardNumber) {
        if (name == null || name.isBlank() || mobile == null || email == null || email.isBlank() || cardNumber == null)
            throw new IllegalArgumentException("Fields cannot be blank or null.");
        if (!StringUtils.isNumeric(mobile) || mobile.length() < 8 || mobile.length() > 15) 
            throw new IllegalArgumentException("Mobile number must be 8 to 15 digits long.");
        if (!StringUtils.isNumeric(cardNumber) || cardNumber.length() < 8 || cardNumber.length() > 19)
            throw new IllegalArgumentException("Card number must be 8 to 19 digits long.");
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
     * @return name of this movie-goer.
     */
    public String getName() {
        return name;
    }

    
    /** 
     * @return mobile number of this movie-goer.
     */
    public String getMobile() {
        return mobile;
    }

    
    /** 
     * @return email address of this movie-goer.
     */
    public String getEmail() {
        return email;
    }

    
    /** 
     * @return masked card number of this movie-goer.
     */
    public String getMaskedCardNumber() {
        return "*" + cardNumber.substring(cardNumber.length()-4);
    }

    
    /** 
     * @return list of transactions of this movie-goer.
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }

    /** 
     * @param cardNumber card number to be used for payment.
     */
    public void setCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 8 || cardNumber.length() > 19)
            throw new IllegalArgumentException("Card number must be 8 to 19 digits long");
        this.cardNumber = cardNumber;
    }   
}
