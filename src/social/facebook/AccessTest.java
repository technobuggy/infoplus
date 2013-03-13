/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package social.facebook;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;
import com.restfb.types.User;

/**
 *
 * @author Pieter De Bie
 * @deprecated 
 */
@Deprecated
public class AccessTest {

    private static final String ACCESSTOKEN = "AAAHZCyfOYoIMBAABaAs2XMsc91EAxWvgFVDPU2OvOgKw5wdMGbRDb62LLA9fINnbhNnletgIWRg1m5pZBigzdGBhfqskYnJHD3zHlCgQOhTZAEqWixh";
    private static FacebookClient facebookClient;

    private static void initialize() {
        facebookClient = new DefaultFacebookClient(ACCESSTOKEN);
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        initialize();
        User gebruiker = facebookClient.fetchObject("me", User.class);
        System.out.println("User name = " + gebruiker.getLastName() + gebruiker.getName());
        FacebookType publishMessageResponse =
                facebookClient.publish("me/feed", FacebookType.class,
                Parameter.with("message", "RestFB test"));

    }
}
