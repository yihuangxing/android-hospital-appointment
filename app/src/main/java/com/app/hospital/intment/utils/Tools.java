package com.app.hospital.intment.utils;

import java.util.Random;

/**
 * desc   :
 */
public class Tools {

    public static String getRandom(int length) {
        StringBuilder val = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            val.append(String.valueOf(random.nextInt(10)));
        }
        return val.toString();
    }
}
