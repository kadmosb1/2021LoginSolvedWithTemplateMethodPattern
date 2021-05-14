package invoice;

public class Login {

    public static boolean isActive () {
        return login.AuthenticationSimple.getInstance ().userIsAuthenticated ();
    }

    public static boolean isAuthenticated () {
        return login.AuthenticationNormal.getInstance ().userIsAuthenticated ();
    }

    public static boolean isAuthorized (String roleName) {
        return login.Authorization.getInstance ().isAuthorized (roleName);
    }
}
