package login;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class Authentication {

    private static final Scanner scanner = new Scanner (System.in);
    protected static Authentication singleton;
    protected ArrayList<User> users;

    protected Authentication() {
        users = new ArrayList<>();
        users.add (new User ("user1", "1"));
        users.add (new User ("user2", "2"));
        users.add (new User ("user3", "3"));
    }

    protected User getUser (String userName) {

        for (User user : users) {

            if (user.getUserName().equals (userName)) {
                return user;
            }
        }

        return null;
    }

    protected String readUserName () {
        System.out.print ("Voer uw gebruikersnaam in: ");
        return scanner.nextLine ();
    }

    protected String readPassword () {
        System.out.print ("Voer uw password in: ");
        return scanner.nextLine ();
    }

    protected void printMessage (String message) {
        System.out.println ("=".repeat (40));
        int numberOfSpaces = message.length () > 36 ? 0 : 40 - message.length ();
        System.out.printf ("= %s%s =", message, " ".repeat (numberOfSpaces));
        System.out.println ("=".repeat (40));
    }

    protected User getActiveUser () {

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

    public void logout () {

        User user = getActiveUser ();

        if (user != null) {
            user.logout ();
        }
    }

    protected abstract User getAuthenticatedUser ();
    public abstract boolean userIsAuthenticated ();
    public abstract boolean authenticate (String userName, String... password);
}