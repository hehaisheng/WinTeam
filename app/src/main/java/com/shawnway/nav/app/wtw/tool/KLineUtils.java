package com.shawnway.nav.app.wtw.tool;


public class KLineUtils {
    /**
     * Prevent class instantiation.
     */
    private KLineUtils() {
    }

    public static String getVolUnit(float num) {

        int e = (int) Math.floor(Math.log10(num));

        if (e >= 8) {
            return "亿手";
        } else if (e >= 4) {
            return "万手";
        } else {
            return "手";
        }


    }
}
