package util;

import exception.InvalidInputException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

// Utility input agar aman (menghindari nextInt + nextLine bentrok)
public class InputUtil {

    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String inputString(Scanner sc, String label) {
        System.out.print(label);
        // 2e String: trim()
        return sc.nextLine().trim();
    }

    public static int inputInt(Scanner sc, String label) {
        System.out.print(label);
        String s = sc.nextLine().trim();
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Input harus angka (int).");
        }
    }

    public static long inputLong(Scanner sc, String label) {
        System.out.print(label);
        String s = sc.nextLine().trim();
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Input harus angka (long).");
        }
    }

    public static double inputDouble(Scanner sc, String label) {
        System.out.print(label);
        String s = sc.nextLine().trim();
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Input harus angka (double).");
        }
    }

    public static LocalDate inputDate(Scanner sc, String label) {
        System.out.print(label + " (yyyy-MM-dd): ");
        String s = sc.nextLine().trim();
        try {
            // 2e Date
            return LocalDate.parse(s, DF);
        } catch (DateTimeParseException e) {
            throw new InvalidInputException("Format tanggal salah. Contoh: 2025-12-29");
        }
    }
}

