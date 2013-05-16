package domain;

/**
 *
 * @author Sam
 */
public class Gebruiker 
{
    private int gebruikernr;
    private String email;
    private String naam;
    private String wachtwoord;
    private int radius;
    private boolean mapsview;
    
    public Gebruiker()
    {
        gebruikernr = 1;
        email = "onbekend@live.com";
        naam = "onbekend";
        wachtwoord = "******";
        radius = 180;
        mapsview = true;
    }
    
    public Gebruiker(int newGebruikernr, String newEmail, String newNaam, String newWachtwoord, int newRadius, boolean newMapsview)
    {
        gebruikernr = newGebruikernr;
        email = newEmail;
        naam = newNaam;
        wachtwoord = newWachtwoord;
        radius = newRadius;
        mapsview = newMapsview;
    }
    
    public Gebruiker(boolean newMapsview, int newRadius)
    {
        this.radius = newRadius;
        this.mapsview = newMapsview;
    }

    public void setGebruikernr(int gebruikernr)
    {
        this.gebruikernr = gebruikernr;
    }
    
    public int getGebruikernr()
    {
        return gebruikernr;
    }
    
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    public String getEmail()
    {
        return email;
    }
    
    public void setWachtwoord(String ww)
    {
        this.wachtwoord = ww;
    }
    
    public String getWachtwoord()
    {
        return wachtwoord;
    }
    
    public void setNaam(String naam)
    {
        this.naam = naam;
    }
    
    public String getNaam()
    {
        return naam;
    }
    
    public void setRadius(int radius)
    {
        this.radius = radius;
    }
    
    public int getRadius()
    {
        return radius;
    }
    
    public void setMapsview(boolean mapsview)
    {
        this.mapsview = true;
    }
    
    public boolean getMapsview()
    {
        return mapsview;
    }
    
    
}
