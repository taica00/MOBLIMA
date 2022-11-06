package main.models;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Represents an admin account. 
 * An admin account is required to access admin functions.
 * @author Tai Chen An
 * @version 1.0 
 * @since 2022-11-06 
 */

public class Admin implements java.io.Serializable{
    private static final long serialVersionUID = 9L;
    /**
     * The userId of this admin account.
     */
    private String userId;

    /**
     * The hashed password of this admin account.
     */
    private String hashedPassword; 

    /**
     * Creates a new admin account with the given userId and password.
     * The password is then hashed for security purposes.
     * @param userId
     * @param password
     */
    public Admin(String userId, String password) {
        if (userId == null || userId.isBlank() || password == null || password.isEmpty())
            throw new IllegalArgumentException("userId or password cannot be null or blank");
        this.userId = userId;
        hashedPassword = DigestUtils.sha256Hex(password);
    }

    /** 
     * @param password
     * @return true if input password matches the stored password.
     */
    public boolean verifyPassword(String password) {
        return DigestUtils.sha256Hex(password).equals(hashedPassword);
    }
    
    /** 
     * @return userId of this account.
     */
    public String getUserId() {
        return userId;
    }
    
    /** 
     * @param password password to be hashed and set.
     */
    public void setPassword(String password) {
        if (password == null || password.isEmpty())
            throw new IllegalArgumentException("Password cannot be null or blank");
        hashedPassword = DigestUtils.sha256Hex(password);
    }
}
