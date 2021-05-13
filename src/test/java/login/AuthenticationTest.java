package login;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationTest {

    private static Authentication authentication;

    @BeforeAll
    public static void init () {
        authentication = Authentication.getInstance ();
    }

    @Test
    public void authenticateTest () {
        User user1 = new User ("user1", "1");
        User user2 = new User ("user2", "2");
        authentication.authenticate ("user1", "1");
        assertTrue (authentication.userIsActive());
        assertTrue (authentication.userIsAuthenticated ());
        assertEquals (user1, authentication.getAuthenticatedUser());
        assertEquals (user1, authentication.getActiveUser ());
        authentication.setActiveUser ("user1");
        assertTrue (authentication.userIsActive());
        assertEquals (user1, authentication.getAuthenticatedUser());
        assertEquals (user1, authentication.getActiveUser ());
        authentication.setActiveUser ("user2");
        assertTrue (authentication.userIsActive());
        assertNull (authentication.getAuthenticatedUser());
        assertEquals (user2, authentication.getActiveUser ());
        authentication.setActiveUser ("user1");
        assertTrue (authentication.userIsActive());
        assertNull (authentication.getAuthenticatedUser());
        assertEquals (user1, authentication.getActiveUser ());
        authentication.authenticate ("user1", "1");
        assertTrue (authentication.userIsActive());
        assertTrue (authentication.userIsAuthenticated());
        assertEquals (user1, authentication.getAuthenticatedUser());
        assertEquals (user1, authentication.getActiveUser ());
    }
}