package login;

import java.util.ArrayList;
import java.util.Scanner;

public class Authentication {

    private static final Scanner scanner = new Scanner (System.in);
    private static Authentication singleton;
    private ArrayList<User> users;
    private ArrayList<Role> roles;

    private Authentication() {

        users = new ArrayList<> ();
        users.add (new User("user1", "1"));
        users.add (new User("user2", "2"));
        users.add (new User("user3", "3"));
    }

    public static Authentication getInstance () {

        if (singleton == null) {
            singleton = new Authentication();
        }

        return singleton;
    }

    protected User getUser (String userName) {

        for (User user : users) {

            if (user.getUserName().equals (userName)) {
                return user;
            }
        }

        return null;
    }

    public User getActiveUser () {

        for (User user : users) {

            if (user.isActive ()) {
                return user;
            }
        }

        return null;
    }

    public String getUserNameOfActiveUser () {

        User activeUser = getActiveUser ();

        if (activeUser != null) {
            return activeUser.getUserName ();
        }
        else {
            return "unknown";
        }
    }

    public User getAuthenticatedUser () {

        for (User user : users) {

            if (user.isAuthenticated ()) {
                return user;
            }
        }

        return null;
    }

    private String readUserName () {
        System.out.print ("Voer uw gebruikersnaam in: ");
        return scanner.nextLine ();
    }

    private String readPassword () {
        System.out.print ("Voer uw password in: ");
        return scanner.nextLine ();
    }

    private void printMessage (String message) {

        System.out.println ("=".repeat (40));
        int numberOfSpaces = message.length () > 36 ? 0 : 40 - message.length ();
        System.out.printf ("= %s%s =", message, " ".repeat (numberOfSpaces));
        System.out.println ("=".repeat (40));
    }

    private boolean activate () {

        if (getActiveUser () != null) {
            return true;
        }
        else {

            for (int i = 0; i < 3; i++) {

                String userName = readUserName();

                if (getUser (userName) != null) {
                    return true;
                }
                else {
                    printMessage ("Unknown user. " + (2 - i) + " attempts left.");
                }
            }
        }

        return false;
    }

    public boolean userIsActive () {

        if (getActiveUser () != null) {
            return true;
        }
        else {
            return activate ();
        }
    }

    public void setActiveUser (String userName) {

        User activeUser = getActiveUser ();
        User newUser = getUser (userName);

        if (newUser != null) {

            if (newUser != activeUser) {
                logout ();
            }

            newUser.setActive ();
        }
    }

    private boolean authenticate () {

        User activeUser = getActiveUser ();

        if ((activeUser != null) && activeUser.isAuthenticated ()) {
            return true;
        }
        else {

            for (int i = 0; i < 3; i++) {

                String userName;
                User user = activeUser;

                if (user == null) {
                    userName = readUserName();
                    user = getUser (userName);
                }

                String password = readPassword ();

                if ((user != null) && user.authenticate (password)) {
                    return true;
                }
                else {
                    printMessage ("Unknown user or incorrect password. " + (2 - i) + " attempts left.");
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

    public boolean authenticate (String userName, String password) {

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

            if (user.authenticate (password)) {
                return true;
            }
            else {
                user.setActive ();
                return authenticate ();
            }
        }
    }

    public boolean authenticate (String userName) {
        return authenticate (userName, null);
    }

    public void logout () {

        User user = getActiveUser ();

        if (user != null) {
            user.logout ();
        }
    }
}