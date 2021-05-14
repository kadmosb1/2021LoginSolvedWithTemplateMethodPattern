package login;

import logging.Logging;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Scanner;

public class AuthenticationNormal extends Authentication {

    private static final Scanner scanner = new Scanner (System.in);

    private AuthenticationNormal () {
        super ();
    }

    public static Authentication getInstance () {
        return getInstance (new AuthenticationNormal ());
    }

    @Override
    public User getAuthenticatedUser () {

        User user = getActiveUser();

        if ((user != null) && user.isAuthenticated ()) {
            return user;
        }
        else {
            return null;
        }
    }

    @Override
    protected boolean authenticate () {

        User activeUser = getActiveUser ();

        if ((activeUser != null) && activeUser.isAuthenticated ()) {
            return true;
        }
        else {

            for (int i = 0; i < 3; i++) {

                String userName = "";
                User user = activeUser;

                if (user == null) {
                    userName = readUserName();
                    user = getUser (userName);
                }

                if (user != null) {
                    String password = readPassword ();
                    return user.authenticate (password);
                }
                else {
                    printMessage ("Unknown user or incorrect password. " + (2 - i) + " attempts left.");
                    Logging.getInstance ().printLog (String.format ("Unknown user/incorrect password for user '%s'", userName));
                }
            }
        }

        return false;
    }

    @Override
    public boolean authenticate (String userName, String... password) {

        User user = getAuthenticatedUser ();

        if ((user != null) && (user.getUserName ().equals (userName))) {
            return true;
        }

        user = getUser (userName);

        if (user == null) {
            return false;
        }
        else {

            logout ();

            if ((password.length == 1) && user.authenticate (password [0])) {
                return true;
            }
            else {
                user.setActive ();
                return authenticate ();
            }
        }
    }
}