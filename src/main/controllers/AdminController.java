package main.controllers;

import java.util.List;

import main.models.Admin;

public class AdminController extends Controller {
    private static List<Admin> adminAccounts;
    private static final String FILEPATH = "src/main/data/admins.ser";

    /** 
     * @param userId
     * @return Admin
     */
    public static Admin searchUserId(String userId) {
        for (Admin account : adminAccounts) {
            if (account.getUserId().equalsIgnoreCase(userId))
                return account;
        }
        return null;
    }

    
    /** 
     * @param userId
     * @param password
     */
    public static void addAdmin(String userId, String password) {
        adminAccounts.add(new Admin(userId, password));
    }

    public static void loadAdminAccounts() {
        adminAccounts = loadData(FILEPATH);
    }

    public static void saveAdminAccounts() {
        saveData(adminAccounts, FILEPATH);
    }
}
