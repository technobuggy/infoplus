/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.notifications;

import domain.Adres;
import java.util.Date;
/**
 * Abstracte notification klasse. Deze klasse is abstract omdat alle
 * notificaties (gevaren en events) veel gemeenschappelijke eigenschappen
 * hebben.
 *
 * @author Pieter De Bie
 * @since 19-02-13
 * @version 1.2 Deprecated isHazard(); Added isEvent() for database purposes
 * @version 1.1 Added date
 * @version 1.0
 */
public abstract class AbstractNotification {
    /**
     * De plaats van de notificatie.
     */
    protected Adres adres;
    /**
     * Een korte beschrijving van de notificatie.
     */
    protected String description;
    /**
     * De datum waarop deze notificatie gemaakt werd.
     * Deze heeft bewust geen modificator omdat het systeem zelf de datum
     * toewijst.
     */
    private final Date created = new Date();
    /**
     * Feedback nummer.
     * Voor toekomstige implementatie met sociale netwerken. (bv: "like").
     */
    protected int feedbacknr;
    /**
     * Feedbacknummer getter.
     * 
     * @return Het feedbacknummer.
     */
    public int getFeedbacknr() {
        return feedbacknr;
    }

    public void setFeedbacknr(int feedbacknr) {
        this.feedbacknr = feedbacknr;
    }
    /**
     * Datum getter
     * @return De automatisch geretourneerde datum. Deze datum wordt automatisch
     * gegenereerd en kan niet geset worden.
     */
    public Date getCreated() {
        return created;
    }
    //Algemene methoden die elke subklasse erft, deze zijn final omdat ze niet
    //meer gewijzigd moeten worden
    /**
     * Adres getter.
     *
     * @return Het adres object.
     */
    final public Adres getAdres() {
        return adres;
    }

    /**
     * Adres setter.
     *
     * @param newAdres zet de plaats van de notificatie.
     */
    final public void setAdres(Adres newAdres) {
        this.adres = newAdres;
    }

    /**
     * Omschrijving getter.
     *
     * @return de omschrijving van de notificatie
     */
    public final String getDescription() {
        return description;
    }

    /**
     * Omschrijving setter.
     *
     * @param newDescription de nieuwe omschrijving
     */
    public final void setDescription(String newDescription) {
        description = newDescription;
    }
    //abstracte methoden

    /**
     * Elke subklasse retourneerd zijn eigen type.
     *
     * @return het type
     */
    public abstract String getType();
    /**
     * Retourneerd of de notificatie een gevaar is of niet. Elke subklasse
     * kan dit hardcoded specificiëren of kan dit dynamisch doen.
     * @return Of het een gevaar is of niet.
     * @deprecated Sinds v1.2 wegens onoverzichtelijke naamgeving
     * @see {@link #isEvent() }
     */
    @Deprecated
    public abstract boolean isHazard();
    /**
     * Retourneerd of de notificatie een evenement is of niet om deze makkelijk
     * naar de juiste database tabel te kunnen wegschrijven. Een klasse
     * kan dit hardcoded specificiëren of kan dit dynamisch doen.
     * @return Of het een evenement is of niet.
     */
    public abstract boolean isEvent();
}
