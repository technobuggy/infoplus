/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.notifications;

import domain.Adres;

/**
 * @version 1.4
 * Converted to singleton
 * @version 1.3
 * toString toegevoegd aan de enum
 * @version 1.2
 * Fixed else case
 * @version 1.1
 * Fixed neighbourhoodaction
 * @author Pieter De Bie
 */
public final class NotificationFactory {
    /**
     * De unieke instantie van een NotificationFactory.
     */
    private static NotificationFactory uniqueInstance;
    /**
     * Alle types van notificaties.
     */
    public enum Type {
        /**
         *
         */
        DANGEROUSSITUATION("Gevaarlijke situatie"),
        /**
         *
         */
        ROADSURFACE("Probleem met het wegdek"),
        /**
         *
         */
        LITTER("Rondslingerend afval"),
        /**
         *
         */
        NEIGHBOURHOODEVENT("Buurtevenement"),
        /**
         *
         */
        NEIGHBOURHOODACTION("Buurtactie");
        /**
         * Default constructor van de Type enumerator.
         * @param initTypeName de toString variabele van de enumerator.
         */
        private Type(final String initTypeName) {
            typeName = initTypeName;
        }
        /**
         * De naam van de enumerator.
         */
        private final String typeName;
        /**
         * De toString methode van de enumerator geeft de naam weer.
         * @return de naam.
         */
        @Override
        public String toString() {
            return typeName;
        }
    }
    /**
     * private default constructor.
     */
    private NotificationFactory() {
        super();
    }
    /**
     * De singleton wordt lazy opgeroepen.
     * @return de unieke instantie van NotificationFactory
     */
    public static NotificationFactory getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new NotificationFactory();
        }
        return uniqueInstance;
    }
    /**
     * Geeft de juiste subklasse weer.
     * @param type type van de notificatie
     * @param adres plaats van de notificatie
     * @param description beschrijving van de notificatie
     * @throws UnsupportedOperationException als het type (nog) niet bestaat
     * @return De juiste notificatie
     */
    public static AbstractNotification createNotification(final Type type,
            final Adres adres, final String description) {
        AbstractNotification tempNotification;
        if (type == Type.DANGEROUSSITUATION) {
            tempNotification = new DangerousSituation();
        } else if (type == Type.ROADSURFACE) {
            tempNotification = new RoadSurface();
        } else if (type == Type.LITTER) {
            tempNotification = new Litter();
        } else if (type == Type.NEIGHBOURHOODEVENT) {
            tempNotification = new NeighbourhoodEvent();
        } else if (type == Type.NEIGHBOURHOODACTION) {
            tempNotification = new NeighbourhoodAction();
        } else {
            //Een exception werpen als het type nog niet gekend is.
            throw new UnsupportedOperationException(
                    "This type of notification is not implemented (yet)");
        }
        tempNotification.setAdres(adres);
        tempNotification.setDescription(description);
        return tempNotification;
    }
}
