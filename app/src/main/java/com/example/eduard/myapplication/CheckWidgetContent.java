package com.example.eduard.myapplication;

/**
 * Created by PSN on 17.06.2017.
 */

public class CheckWidgetContent {

    /**
     * Checks, if a email-address is basically valid
     *
     * @param email email-address
     * @return boolean valid
     */
    static public boolean checkEmail(String email) {
        boolean returnBoolean = false;
        returnBoolean = email.matches("[a-zA-Z0-9]+[_a-zA-Z0-9\\.-]*[a-z0-9]+@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]+)");
        return returnBoolean;
    }

    static public boolean checkPassword(String password) {
        boolean returnBoolean = false;
        returnBoolean = password.matches("[0-9]{6}");
        return returnBoolean;
    }
}
