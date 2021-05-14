package login;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorizationTest {

    private static Authorization authorization;
    private static Authentication authentication;

    @BeforeAll
    public static void init () {
        authentication = AuthenticationNormal.getInstance ();
        authorization = Authorization.getInstance ();
    }

    @Test
    public void authorizeTest () {

        authentication.authenticate ("user1", "1");
        assertFalse (authorization.isAuthorized("product"));
        assertTrue (authorization.isAuthorized("customer"));
        assertTrue (authorization.isAuthorized("invoice"));

        authentication.authenticate ("user2", "2");
        assertTrue (authorization.isAuthorized("product"));
        assertFalse (authorization.isAuthorized("customer"));
        assertTrue (authorization.isAuthorized("invoice"));

        authentication.authenticate ("user3", "3");
        assertTrue (authorization.isAuthorized("product"));
        assertTrue (authorization.isAuthorized("customer"));
        assertTrue (authorization.isAuthorized("invoice"));

        authentication.logout ();
        assertFalse (authorization.isAuthorized("invoice"));
        assertFalse (authorization.isAuthorized("customer"));
        assertFalse (authorization.isAuthorized("product"));
    }
}