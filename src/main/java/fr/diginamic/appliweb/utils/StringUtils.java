package fr.diginamic.appliweb.utils;

public class StringUtils {

    public static String formatNombre(int nombre) {
        return String.format("%,d", nombre).replace(",", " ");
    }
}
