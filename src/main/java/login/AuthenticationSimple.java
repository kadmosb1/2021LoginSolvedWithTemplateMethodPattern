package login;

import java.util.ArrayList;
import java.util.Scanner;

public class AuthenticationSimple extends Authentication {

    private static final Scanner scanner = new Scanner (System.in);

    private AuthenticationSimple() {
        super ();
    }

    private AuthenticationSimple (ArrayList<User> users) {
        this.users = users;
    }

    public static Authentication getInstance () {

        if (singleton == null) {
            singleton = new AuthenticationSimple ();
        }
        else if (singleton.getClass () != AuthenticationSimple.class) {
            ArrayList<User> users = singleton.users;
            singleton = new AuthenticationSimple (users);
        }

        return singleton;
    }

    public User getAuthenticatedUser () {
        return getActiveUser ();
    }

    private boolean authenticate () {

        for (int i = 0; i < 3; i++) {

            User user = getUser (readUserName ());

            if (user != null) {
                user.setActive();
                return true;
            }
            else {
                printMessage ("Unknown user. " + (2 - i) + " attempts left.");
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
            user.setActive();

            if (password.length == 1) {
                user.authenticate (password [0]);
            }

            return true;
        }
    }
}