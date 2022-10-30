package main.models;

import org.apache.commons.codec.digest.DigestUtils;

public class Admin implements java.io.Serializable{
    private static final long serialVersionUID = 9L;
    private String userId;
    private String hashedPassword; 

    public Admin(String userId, String password) {
        this.userId = userId;
        hashedPassword = DigestUtils.sha256Hex(password);
    }

    public boolean verifyPassword(String passwword) {
        return DigestUtils.sha256Hex(passwword).equals(hashedPassword);
    }

    public String getUserId() {
        return userId;
    }

    public void setPassword(String password) {
        hashedPassword = DigestUtils.sha256Hex(password);
    }
}
