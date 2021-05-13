package invoice;

public class Login {

    public static boolean isActive () {
        return login.Authentication.getInstance ().userIsActive ();
    }

    public static boolean isAuthenticated () {
        return login.Authentication.getInstance ().userIsAuthenticated ();
    }

    public static boolean isAuthorized (String roleName) {
        return login.Authorization.getInstance ().isAuthorized (roleName);
    }
}
