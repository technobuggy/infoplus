package domain;

import com.sun.javaws.exceptions.InvalidArgumentException;
import java.awt.geom.Point2D;

/**
 * Een object met gegevens over de locatie waarover een notificatie handelt. De
 * klasse houdt
 * <code>straat</code>,
 * <code>stad</code>,
 * <code>huisnummer</code> en een 2D punt voor google maps integratie bij.
 *
 * @author Pieter De Bie
 * @since 20-2-2013
 * @version 1.4 Strictere encapsulatie
 * @version 1.3 ToString toegevoegd Extra constructor toegevoegd Javadoc
 * cleaning
 * @version 1.2 longitude en latitude toegevoegd voor google maps
 * @version 1.1
 */
public class Adres {
    /**
     * De straat van het adres. Dit moet meer dan 3 karakters bevatten en
     * mag enkel letters bevatten.
     */
    private String straat;
/**
     * De stad van het adres. Dit moet meer dan 3 karakters bevatten en
     * mag enkel letters bevatten.
     */
    private String stad;
    /**
     * Het huisnummer van het adres. Dit is een cijfer met eventueel een
     * kleine letter achter
     */
    private String huisnummer;
    /**
     * De locatiebepaling voor (google) maps integratie. 
     * <ul>
     * <li>X: Longitude waarde (tussen -180 en 180)</li>
     * <li>Y: Latitude waarde (tussen -90 en 90)</li>
     * </ul>
     */
    private Point2D mapsPos = new Point2D.Double();

    /**
     * Default constructor. Deze geeft voor de straat en stad attributen de
     * waarde "onbekend" voor huisnummer "1a" en als maps positie (0.0D,0.0D)
     * mee.
     */
    public Adres() {
        straat = "onbekend";
        stad = "onbekend";
        huisnummer = "1a";
        mapsPos = new Point2D.Double(0.0D, 0.0D);
    }
    /**
     * Parameter constructor.
     *
     * @param newStraat De straat waarin de notificatie zich afspeelt.
     * @param newStad De stad waarin de notificatie zich afspeelt.
     * @param newHuisnummer Het huisnummer waarin de notificatie zich afspeelt.
     * @param newMapsPos De longitude en latitude waarden van google maps.
     */
    public Adres(String newStraat, String newStad, String newHuisnummer,
            Point2D.Double newMapsPos) {
        straat = newStraat;
        stad = newStad;
        huisnummer = newHuisnummer;
        mapsPos = newMapsPos;
    }
    /**
     * Parameter constructor zonder maps support.
     *
     * @param newStraat De straat waarin de notificatie zich afspeelt.
     * @param newStad De stad waarin de notificatie zich afspeelt.
     * @param newHuisnummer Het huisnummer waarin de notificatie zich afspeelt.
     */
    public Adres(String newStraat, String newStad, String newHuisnummer) {
        straat = newStraat;
        stad = newStad;
        huisnummer = newHuisnummer;
        mapsPos = null;
    }
    /**
     * Simpele parameter constructor, deze zet zelf long en lat waarden om naar
     * een point2D.
     *
     * @param newStraat De straat waarin de notificatie zich afspeelt.
     * @param newStad De stad waarin de notificatie zich afspeelt.
     * @param newHuisnummer Het huisnummer waarin de notificatie zich afspeelt.
     * @param longitude De longitude (x) waarde van een positie
     * @param latitude De latitude (y) waarde van een positie
     */
    public Adres(String newStraat, String newStad, String newHuisnummer,
            double longitude, double latitude) {
        straat = newStraat;
        stad = newStad;
        huisnummer = newHuisnummer;
        mapsPos = new Point2D.Double(longitude, latitude);
    }

    /**
     * Maps positie getter. Longitude = X waarde, latitude = Y waarde
     *
     * @return De Latitude en longitude waarden waarmee maps diensten werken.
     */
    public Point2D getMapsPos() {
        return mapsPos;
    }

    /**
     * Maps positie setter. Geeft een fout als longtitude (x) niet tussen -180
     * en +180 ligt
     *
     * @param mapsPos De longitude en latitude waarden waarmee maps diensten
     * werken.
     * @throws Exception Als er foute waarden ingegeven worden.
     */
    public final void setMapsPos(final Point2D.Double mapsPos)
            throws Exception {
        //check
        if (mapsPos.x < -180 || mapsPos.x > 180) {
            throw new Exception
                    ("Longtitude waarden moeten tussen -180 en 180 liggen.");
        }
        if (mapsPos.y < -90 || mapsPos.y > 90) {
            throw new Exception
                    ("Latitude waarden moeten tussen -90 en 90 liggen.");
        }
        this.mapsPos = mapsPos;
    }

    /**
     * Straat getter.
     *
     * @return De straat waarin het Adres zich bevindt.
     */
    public final String getStraat() {
        return straat;
    }

    /**
     * Straat setter.
     *
     * @param newStraat Moet meer dan 3 karakters bevatten, en mag enkel letters
     * bevatten. De eerste letter wordt automatisch omgezet naar een
     * hoofdletter.
     * @throws Exception als de straatnaam niet aan de voorwaarden voldoet.
     */
    public final void setStraat(final String newStraat) throws Exception {
        if (newStraat.length() < 4) {
            throw new Exception("Straat moet meer dan 4 karakters bevatten");
        }
        if (!newStraat.matches("[a-zA-Z]*")) {
            throw new Exception("Straat moet tekstueel zijn");
        }
        this.straat = newStraat.substring(0, 1).toUpperCase()
                + newStraat.substring(1);
    }

    /**
     * Stad Getter.
     *
     * @return De stad of gemeente waarin het <code>Adres</code> zich bevindt.
     */
    public String getStad() {
        return stad;
    }

    /**
     * Stad Setter.
     *
     * @param newStad moet meer dan 3 karakters bevatten en mag enkel letters
     * bevatten. De eerste letter wordt automatisch naar een hoofdletter
     * omgezet.
     * @throws Exception Als de stadsnaam niet voldoet aan de normen.
     */
    public final void setStad(String newStad) throws Exception {
        if (newStad.length() < 4) {
            throw new Exception("Stad moet meer dan 4 karakters bevatten");
        }
        if (!newStad.matches("[a-zA-Z]*")) {
            throw new Exception("Stad moet tekstueel zijn");
        }
        this.stad = newStad.substring(0, 1).toUpperCase()
                + newStad.substring(1);
    }

    /**
     * HuisNummer getter.
     *
     * @
     * @return huisnummer
     */
    public final String getHuisnummer() {
        return huisnummer;
    }

    /**
     * Huisnummer setter.
     *
     * @param newHuisnummer Huisnummer moet een nummer zijn met eventueel een
     * kleine letter naast.
     * @throws Exception als het huisnummer niet conform de norm is.
     */
    public void setHuisnummer(String newHuisnummer) throws Exception {
        if (!newHuisnummer.matches("[0-9]*[a-z]?")) {
            throw new Exception("Huisnummer moeten cijfers zijn eventueel"
                    + "gevolgd door een kleine letter");
        }
        this.huisnummer = newHuisnummer;
    }

    @Override
    public String toString() {
        final StringBuilder tempString = new StringBuilder();
        tempString.append("Stad: \t");
        tempString.append(getStad());
        tempString.append("\nStraat: \t");
        tempString.append(getStraat());
        tempString.append("\nNr: \t");
        tempString.append(getHuisnummer());
        return tempString.toString();
    }
}
