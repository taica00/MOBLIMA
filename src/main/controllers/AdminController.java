package main.controllers;

import java.util.List;

import main.models.Admin;

/**
 * This class manages user actions pertaining to the {@link Admin} class.
 * Stores a list of admin accounts.
 * @author Tai Chen An
 * @version 1.0 
 * @since 2022-11-08 
 */

public class AdminController extends Controller {
    /**
     * The list of admin accounts.
     */
    private static List<Admin> adminAccounts;
    private static final String FILEPATH = "src/main/data/admins.ser";

    /**
     * Search the list of admin accounts for an account with the given userId. 
     * @param userId userId of admin acccount.
     * @return admin account.
     */
    public static Admin searchUserId(String userId) {
        for (Admin account : adminAccounts) {
            if (account.getUserId().equalsIgnoreCase(userId))
                return account;
        }
        return null;
    }
    
    /** 
     * Adds an admin account to the list of admin accounts.
     * @param userId userId of admin account to add.
     * @param password password of admin account to add.
     */
    public static void addAdmin(String userId, String password) {
        adminAccounts.add(new Admin(userId, password));
    }

    /**
     * Deserialises the list of admin accounts.
     */
    public static void loadAdminAccounts() {
        adminAccounts = loadData(FILEPATH);
    }

    /**
     * Serialises the list of admin accounts.
     */
    public static void saveAdminAccounts() {
        saveData(adminAccounts, FILEPATH);
    }
}
