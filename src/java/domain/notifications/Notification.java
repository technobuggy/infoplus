/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.notifications;

import domain.Adres;

/**
 *
 * @author Tom
 */
public class Notification {
    
    private int eventNr;
    private String eventNaam;
    private Adres adres;
    private String opmerking;
    private int radius;
    private String afbeelding;
    private int feedbacknr;

    

    public Notification() {
    
    }
    
    

    public Notification(int eventNr, String eventNaam, Adres adres, String opmerking, int radius) {
        this.eventNr = eventNr;
        this.eventNaam = eventNaam;
        this.adres = adres;
        this.opmerking = opmerking;
        this.radius = radius;
    }

    public int getEventNr() {
        return eventNr;
    }

    public void setEventNr(int eventNr) {
        this.eventNr = eventNr;
    }

    public String getEventNaam() {
        return eventNaam;
    }

    public void setEventNaam(String eventNaam) {
        this.eventNaam = eventNaam;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public String getOpmerking() {
        return opmerking;
    }

    public void setOpmerking(String opmerking) {
        this.opmerking = opmerking;
    }
    
    

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public String getAfbeelding() {
        return afbeelding;
    }

    public void setAfbeelding(String afbeelding) {
        this.afbeelding = afbeelding;
    }

    public int getFeedbacknr() {
        return feedbacknr;
    }

    public void setFeedbacknr(int feedbacknr) {
        this.feedbacknr = feedbacknr;
    }
    
    
    
}
