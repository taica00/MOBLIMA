package main.controllers;

import java.util.Scanner;

public class InputController extends Controller {
    private static Scanner scan = new Scanner(System.in);

    public static int getInt() {
        return scan.hasNextInt() ? scan.nextInt() : -1;
    }

    public static void clear() {
        scan.nextLine();
    }

    


}
