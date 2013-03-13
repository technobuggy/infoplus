package social.facebook;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;

/**
 * Bevat de details om een facebookconnectie aan te maken.
 *
 * @author Pieter De Bie
 * @since 20-02-13
 * @version 1.0
 */
public class FacebookConnectionBuilder {
     /**
     * Een test token om met facebook te verbinden.
     */
    private static final String ACCESSTOKEN =
            "AAAD0sD1NSgwBAGEoW4GJaW90lGZAoAg4CSoBVgcrxE4Y07KheTdVqgNuSy4ptJd4i7F2TfZCvCbkc0dd90r0ZBOesVIutqB9dUI9bYwxlbmKdSn93VS";
    /**
     * Maakt een connectie aan via de ACCESSTOKEN test token.
     * die zich op onze app inschrijft.
     *
     * @return Het FacebookClient object waarmee fb acties uitgevoerd
     * kunnen worden.
     */
    public static FacebookClient createTestConnection() {
        return new DefaultFacebookClient(ACCESSTOKEN);
    }
    /**
     * Maakt een connectie aan via de doorgegeven token.
     *
     * @param token Het OAth token dat facebook aanmaakt per gebruiker
     * die zich op onze app inschrijft.
     * @return Het FacebookClient object waarmee fb acties uitgevoerd
     * kunnen worden.
     */
    public static FacebookClient createTokenedConnection(final String token) {
        return new DefaultFacebookClient(token);
    }
}
