package com.agricultural.domains;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Alexey on 26.02.2017.
 */
public class RegExp {

    public static boolean allNumbers(String wage){

        Pattern pattern = Pattern.compile("^[0-9]+$");
        Matcher matcher = pattern.matcher(wage);
        return matcher.matches();
    }

}
