package login;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorizationTest {

    private static Authorization authorization;
    private static Authentication authentication;

    @BeforeAll
    public static void init () {
        authentication = Authentication.getInstance ();
        authorization = Authorization.getInstance ();
    }

    @Test
    public void authorizeTest () {

        authentication.authenticate ("user1", "1");
        assertFalse (authorization.isAuthorized("product"));
        assertTrue (login.isAuthorized("customer"));
        assertTrue (login.isAuthorized("invoice"));

        login.authenticate ("user2", "2");
        assertTrue (login.isAuthorized("product"));
        assertFalse (login.isAuthorized("customer"));
        assertTrue (login.isAuthorized("invoice"));

        login.authenticate ("user3", "3");
        assertTrue (login.isAuthorized("product"));
        assertTrue (login.isAuthorized("customer"));
        assertTrue (login.isAuthorized("invoice"));

        login.logout ();
        assertFalse (login.isAuthorized("invoice"));
        assertFalse (login.isAuthorized("customer"));
        assertFalse (login.isAuthorized("product"));
    }
}