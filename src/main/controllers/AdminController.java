package main.controllers;

import java.util.List;

import main.models.Admin;
import main.ui.AdminMenuUI;

public class AdminController extends Controller {
    private static List<Admin> adminAccounts;
    private static final String FILEPATH = "src/main/data/admins.ser";

    public static Admin searchUserId(String userId) {
        for (Admin account : adminAccounts) {
            if (account.getUserId().equalsIgnoreCase(userId))
                return account;
        }
        return null;
    }

    public static void loadAdminAccounts() {
        adminAccounts = loadData(FILEPATH);
    }

    public static void saveAdminAccounts() {
        saveData(adminAccounts, FILEPATH);
    }
}
