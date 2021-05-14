package login;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationTest {

    @Test
    public void authenticateTest () {
        User user1 = new User ("user1", "1");
        User user2 = new User ("user2", "2");

        Authentication authentication = AuthenticationNormal.getInstance ();
        authentication.authenticate ("user1", "1");
        assertTrue (authentication.userIsAuthenticated ());
        assertEquals (user1, authentication.getAuthenticatedUser());
        assertEquals (user1, authentication.getActiveUser ());

        authentication = AuthenticationSimple.getInstance ();
        assertTrue (authentication.userIsAuthenticated ());
        assertEquals (user1, authentication.getAuthenticatedUser());
        assertEquals (user1, authentication.getActiveUser ());

        authentication.authenticate ("user2");
        assertTrue (authentication.userIsAuthenticated ());
        assertEquals (user2, authentication.getAuthenticatedUser ());
        assertEquals (user2, authentication.getActiveUser ());

        authentication = AuthenticationNormal.getInstance ();
        assertNull (authentication.getAuthenticatedUser());
        assertEquals (user2, authentication.getActiveUser ());

        authentication.authenticate ("user1", "1");
        assertTrue (authentication.userIsAuthenticated());
        assertEquals (user1, authentication.getAuthenticatedUser());
        assertEquals (user1, authentication.getActiveUser ());
    }
}