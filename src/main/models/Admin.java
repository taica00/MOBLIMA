package main.models;

import org.apache.commons.codec.digest.DigestUtils;

public class Admin implements java.io.Serializable{
    private static final long serialVersionUID = 9L;
    private String userId;
    private String hashedPassword; 

    public Admin(String userId, String password) {
        if (userId == null || userId.isBlank() || password == null || password.isEmpty())
            throw new IllegalArgumentException("userId or password cannot be null or blank");
        this.userId = userId;
        hashedPassword = DigestUtils.sha256Hex(password);
    }

    public boolean verifyPassword(String password) {
        return DigestUtils.sha256Hex(password).equals(hashedPassword);
    }

    public String getUserId() {
        return userId;
    }

    public void setPassword(String password) {
        if (password == null || password.isEmpty())
            throw new IllegalArgumentException("Password cannot be null or blank");
        hashedPassword = DigestUtils.sha256Hex(password);
    }
}
