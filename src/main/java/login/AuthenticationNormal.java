package login;

import logging.Logging;

import java.util.ArrayList;
import java.util.Scanner;

public class AuthenticationNormal extends Authentication {

    private static final Scanner scanner = new Scanner (System.in);

    private AuthenticationNormal () {
        super ();
    }

    private AuthenticationNormal (ArrayList<User> users) {
        this.users = users;
    }

    public static Authentication getInstance () {

        if (singleton == null) {
            singleton = new AuthenticationNormal ();
        }
        else if (singleton.getClass () != AuthenticationNormal.class) {
            ArrayList<User> users = singleton.users;
            singleton = new AuthenticationNormal (users);
        }

        return singleton;
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

    private boolean authenticate () {

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

    public boolean userIsAuthenticated () {

        if (getAuthenticatedUser () != null) {
            return true;
        }
        else {
            return authenticate ();
        }
    }

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