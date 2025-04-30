package ar.com.dontar.demo.configuration;

import java.util.HashSet;
import java.util.Set;

public class PublicRoutesConfig {

    public static final Set<String> PUBLIC_ROUTES = new HashSet<>();

    static {
        PUBLIC_ROUTES.add("/auth/login");
        PUBLIC_ROUTES.add("/auth/forgot-password");
        PUBLIC_ROUTES.add("/patient/register");
        PUBLIC_ROUTES.add("/professional/all-professionals");
    }
}
