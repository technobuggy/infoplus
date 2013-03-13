package social.facebook;

import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;
import domain.notifications.AbstractNotification;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Een adapter die van notificatieobjecten facebookobjecten maakt. Qua
 * attributen is dit quasi hetzelfde maar het voegt postfunctionaliteit toe.
 * 
 * @author Pieter De Bie
 * @see AbstractNotification
 * @version 1.1 
 * Getter en setter voor notification toegevoegd
 * Attributen private gezet
 * Javadoc comments toegevoegd
 * De deprecated methode isHazard() vervangen door isEvent()
 * StringBuilder gebruikt in plaats vaan String + String
 */
public class FacebookAdapter {

    /**
     * Het facebook connectie object, gebouwd door de facebookConnectionBuilder.
     * Deze maakt gebruik van de restFB library.
     * Deze is transient omdat ze niet geserialiseerd hoeft te worden.
     */
    private final transient FacebookClient fbConnection =
            FacebookConnectionBuilder.createTestConnection();
    /**
     * De notificatie die het object moet "adapten".
     */
    private AbstractNotification notification;

    /**
     * Parameter constructor.
     *
     * @param initNotification De initiële AbstractNotification
     */
    public FacebookAdapter(AbstractNotification initNotification) {
        notification = initNotification;
    }

    /**
     * Notification getter.
     *
     * @return de huidige AbstractNotification.
     */
    public AbstractNotification getNotification() {
        return notification;
    }

    /**
     * Notification setter.
     *
     * @param newNotification De nieuwe AbstractNotification
     */
    public final void setNotification(AbstractNotification newNotification) {
        notification = newNotification;
    }

    /**
     * Post afhankelijk van het type een bericht of event op facebook.
     */
    public final void publish() {
        if (notification.isEvent()) {
            System.out.println("FacebookAdapter: posting event");
            postEvent();
            
        } else {
            System.out.println("FacebookAdapter: posting message");
            postMessage();
        }
    }

    /**
     * Post een bericht naar facebook.
     *
     * @see publish()
     */
    private void postMessage() {
        final StringBuilder tempMessage = new StringBuilder();
        tempMessage.append("Opgelet: ");
        tempMessage.append(notification.getType());
        tempMessage.append("\n\n");
        tempMessage.append(notification.getAdres().toString());
        tempMessage.append("\n\n");
        tempMessage.append(notification.getDescription());
        fbConnection.publish("me/feed", FacebookType.class,
                Parameter.with("message", tempMessage.toString()));
    }

    /**
     * Post een event naar facebook <b>Deze methode werpt een
     * NotImplementedException omdat deze nog niet geimplementeerd is! Deze is
     * voor toekomstige implementatie.</b>
     *
     * @see publish()
     */
    private void postEvent() {
        throw new NotImplementedException();
    }
}
