package main.utils;

import java.util.ArrayList;
import java.util.List;

import main.models.Admin;

public class PopulateAdmins extends Populator {
    public static void main(String[] args) {
        List<Admin> admins = new ArrayList<>();
        admins.add(new Admin("admin", "admin"));
        serialize(admins, "admins.ser");
    }
}
