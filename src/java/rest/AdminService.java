/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import domain.Adres;
import domain.notifications.AbstractNotification;
import domain.notifications.Notification;
import domain.notifications.NotificationFactory;
import java.awt.geom.Point2D;
import java.net.URI;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Pieter De Bie
 */
@Stateless
@Path("admin")
public class AdminService {

    private final String LOREMIPSUM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla egestas sollicitudin libero, id imperdiet lorem sodales a. Donec molestie dapibus porttitor. Cras sit amet nulla et sapien consectetur dictum et eget magna. Aliquam porta consequat nisl at vestibulum. Maecenas nec lobortis lectus. Fusce pharetra urna vel purus hendrerit sit amet facilisis tortor congue. In commodo venenatis justo, id adipiscing turpis cursus non. Aliquam erat volutpat.";
    Logger log = Logger.getLogger(AdminService.class.getName());
    @Resource(name = "jdbc/onzebuurt")
    private DataSource source;

    @Path("all/{gemeente}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<AbstractNotification> getAdminNotification(@PathParam("gemeente") String gemeente) throws Exception {

        List<AbstractNotification> gelezenNotificaties = new ArrayList();
        try (Connection conn = source.getConnection()) {
            Statement stat = conn.createStatement();
            ResultSet rs;

            String query = "SELECT * FROM event WHERE gemeente = '" + gemeente + "' ORDER BY eventnr";
            rs = stat.executeQuery(query);

            while (rs.next()) {
                //attributen
                int eventnr = rs.getInt("eventnr");
                String eventtype = rs.getString("eventtype");
                String straat = rs.getString("straat");
                String huisnr = rs.getString("nr");
                Date datum = rs.getDate("datum");
                String opmerking = rs.getString("opmerking");
                int visible = rs.getInt("visible");
                String afbeelding = rs.getString("afbeelding");
                //adres
                Adres adres = new Adres();
                adres.setStad(gemeente);
                adres.setStraat(straat);
                adres.setHuisnummer(huisnr);
                adres.setMapsPos(new Point2D.Double(
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude")));

                //Notification aanmaken
                NotificationFactory nFact = NotificationFactory.getInstance();
                NotificationFactory.Type tempType = nFact.stringToType(eventtype);
                gelezenNotificaties.add(
                        nFact.createNotification(
                        eventnr, tempType, adres, opmerking, visible, afbeelding));

            }
            rs.close();
            //System.out.println(lijstStudent.get(0).toString());
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
        return gelezenNotificaties;
    }

    @Path("new/{gemeente}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<AbstractNotification> getNewAdminNotification(@PathParam("gemeente") String gemeente) throws Exception {
        List<AbstractNotification> gelezenNotificaties = new ArrayList();
        try (Connection conn = source.getConnection()) {
            Statement stat = conn.createStatement();
            ResultSet rs;

            String query = "SELECT * FROM event WHERE gemeente = '" + gemeente + "' AND visible = '0' ORDER BY eventnr";
            rs = stat.executeQuery(query);

            while (rs.next()) {
                //attributen
                int eventnr = rs.getInt("eventnr");
                String eventtype = rs.getString("eventtype");
                String straat = rs.getString("straat");
                String huisnr = rs.getString("nr");
                Date datum = rs.getDate("datum");
                String opmerking = rs.getString("opmerking");
                int visible = rs.getInt("visible");
                String afbeelding = rs.getString("afbeelding");
                //adres
                Adres adres = new Adres();
                adres.setStad(gemeente);
                adres.setStraat(straat);
                adres.setHuisnummer(huisnr);
                adres.setMapsPos(new Point2D.Double(
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude")));

                //Notification aanmaken
                NotificationFactory nFact = NotificationFactory.getInstance();
                NotificationFactory.Type tempType = nFact.stringToType(eventtype);
                gelezenNotificaties.add(
                        nFact.createNotification(
                        eventnr, tempType, adres, opmerking, visible, afbeelding));

            }
            rs.close();
            //System.out.println(lijstStudent.get(0).toString());
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
        return gelezenNotificaties;
    }

    @Path("old/{gemeente}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<AbstractNotification> getOldAdminNotification(@PathParam("gemeente") String gemeente) throws Exception {
        List<AbstractNotification> gelezenNotificaties = new ArrayList();
        try (Connection conn = source.getConnection()) {
            Statement stat = conn.createStatement();
            ResultSet rs;

            String query = "SELECT * FROM event WHERE gemeente = '" + gemeente + "' AND visible = '1' ORDER BY eventnr";
            rs = stat.executeQuery(query);

            while (rs.next()) {
                //attributen
                int eventnr = rs.getInt("eventnr");
                String eventtype = rs.getString("eventtype");
                String straat = rs.getString("straat");
                String huisnr = rs.getString("nr");
                Date datum = rs.getDate("datum");
                String opmerking = rs.getString("opmerking");
                int visible = rs.getInt("visible");
                String afbeelding = rs.getString("afbeelding");
                //adres
                Adres adres = new Adres();
                adres.setStad(gemeente);
                adres.setStraat(straat);
                adres.setHuisnummer(huisnr);
                adres.setMapsPos(new Point2D.Double(
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude")));

                //Notification aanmaken
                NotificationFactory nFact = NotificationFactory.getInstance();
                NotificationFactory.Type tempType = nFact.stringToType(eventtype);
                gelezenNotificaties.add(
                        nFact.createNotification(
                        eventnr, tempType, adres, opmerking, visible, afbeelding));

            }
            rs.close();
            //System.out.println(lijstStudent.get(0).toString());
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
        return gelezenNotificaties;
    }

    @Path("login/{user}/{pass}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getOldAdminNotification(
            @PathParam("user") String user,
            @PathParam("pass") String pass) throws Exception {

        String result = "otherError";
        try (Connection conn = source.getConnection()) {
            Statement stat = conn.createStatement();
            ResultSet rs;

            String query = "SELECT gemeente "
                    + "FROM admin "
                    + "WHERE email = '" + user + "' "
                    + "AND password = '" + pass + "';";
            rs = stat.executeQuery(query);

            if (rs.next()) {
                result = rs.getString("gemeente");

            } else {
                result = "notFound";
            }

            rs.close();
            //System.out.println(lijstStudent.get(0).toString());
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
        return result;
    }

    @Path("accept/{id}")
    @PUT
    public void accept(@PathParam("id") int id) {
        try (Connection conn = source.getConnection()) {
            try (
                    PreparedStatement stat = conn.prepareStatement("UPDATE event SET visible = '1' WHERE eventnr = ?")) {
                stat.setInt(1, id);
                stat.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }

    @Path("reject/{id}")
    @PUT
    public void reject(@PathParam("id") int id) {
        try (Connection conn = source.getConnection()) {
            try (
                    PreparedStatement stat = conn.prepareStatement("UPDATE event SET visible = '0' WHERE eventnr = ?")) {
                stat.setInt(1, id);
                stat.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }

    @Path("remove/{id}")
    @DELETE
    public void removeNotification(@PathParam("id") int id) {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement("SELECT * FROM event WHERE eventnr = ?")) {
                stat.setInt(1, id);
                try (ResultSet rs = stat.executeQuery()) {
                    if (!rs.next()) {
                        throw new WebApplicationException(Response.Status.NOT_FOUND);
                    }
                }
            }

            try (PreparedStatement stat = conn.prepareStatement("DELETE FROM event WHERE eventnr = ?")) {
                stat.setInt(1, id);
                stat.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }

    @Path("updateDescription/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateDescription(@PathParam("id") int id, String omschrijving) {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement("UPDATE event SET opmerking = ? WHERE eventnr = ?")) {
                stat.setString(1, omschrijving);
                stat.setInt(2, id);
                stat.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }

    @Path("stats/{gemeente}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public statisticSet getStatistics(@PathParam("gemeente") String gemeente) {
        //debugging aid
        Logger log = Logger.getLogger(AdminService.class.getName());
        log.info("Started logging");

        //variables
        List<String> querySet = new ArrayList<>();
        List<Object> statResults = new ArrayList<>();
        ResultSet rs;
        statisticSet statSet;
        int iteration = 0;

        log.info("Gemeente = " + gemeente);
        //Queries
        querySet.add("SELECT MAX(datum) FROM event WHERE gemeente like '" + gemeente + "'");//date
        querySet.add("SELECT count(*) FROM event WHERE visible = 0 AND gemeente like '" + gemeente + "'"); //unpublished
        querySet.add("SELECT count(*) FROM event WHERE visible = 1 AND gemeente like '" + gemeente + "'"); //published
        querySet.add("SELECT count(*) FROM event WHERE eventtype like 'DangerousSituation' AND gemeente like '" + gemeente + "'"); //DangerousSituation
        querySet.add("SELECT count(*) FROM event WHERE eventtype like 'Litter' AND gemeente like '" + gemeente + "'"); //Litter
        querySet.add("SELECT count(*) FROM event WHERE eventtype like 'NeighbourhoodAction' AND gemeente like '" + gemeente + "'"); //NBaction
        querySet.add("SELECT count(*) FROM event WHERE eventtype like 'NeighbourhoodEvent' AND gemeente like '" + gemeente + "'"); //NBE
        querySet.add("SELECT count(*) FROM event WHERE eventtype like 'RoadSurface' AND gemeente like '" + gemeente + "'"); //Roadsurface

        log.info("Creating connection");
        //Connection
        try (Connection conn = source.getConnection()) {
            try {
                log.info("Connection created");
                Statement stat = conn.createStatement();
                for (String currentQuery : querySet) {
                    log.info("Executing statement " + iteration);
                    rs = stat.executeQuery(currentQuery);
                    if (rs.next()) {

                        statResults.add(rs.getObject(1));
                        log.info("statement " + iteration + " succeeded with result: " + rs.getObject(1));
                    } else {
                        statResults.add("notFound");
                    }
                    iteration++;
                }
            } catch (SQLException ex) {
                log.severe(ex.getMessage());
            }
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                log.severe(t.getMessage());
            }
        }
        log.info("Statistic result: ");
        for (Object result : statResults) {
            log.info("\t" + result);
        }
        log.info("Creating object");
        statSet = new statisticSet(statResults.iterator());
        return statSet;
    }

    @Path("dateRange/{gemeente}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<java.sql.Date> getDates(@PathParam("gemeente") String gemeente) {
        //debugging aid
        log.info("Fetching dates");

        //vars
        String query = "SELECT DISTINCT datum FROM event WHERE gemeente LIKE '" + gemeente + "' ORDER BY datum ASC";
        ArrayList<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
        ResultSet rs;

        //connection
        try (Connection conn = source.getConnection()) {
            try {
                Statement stat = conn.createStatement();
                rs = stat.executeQuery(query);
                while (rs.next()) {
                    dateList.add(rs.getDate(1));
                }
            } catch (Exception e) {
                log.severe(e.getMessage());
            }

        } catch (SQLException ex) {
            log.severe("Connection: " + ex.getMessage());
        }

        return dateList;
    }

    @Path("createDummy/{count}/{gemeente}")
    @GET
    public void createDummy(@PathParam("count") int count, @PathParam("gemeente") String gemeente) {
        try (Connection conn = source.getConnection()) {
            for (int i = 0; i < count; i++) {
                try (PreparedStatement stat = conn.prepareStatement(
                                "INSERT INTO event"
                                + "(eventnr,eventtype,gemeente,straat,nr,datum, opmerking, feedbacknr,afbeelding,latitude,longitude,radius,visible) "
                                + "VALUES(?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
                    stat.setInt(1, getEventAantal() + 1);
                    stat.setString(2, getRandomEventType());
                    stat.setString(3, gemeente);
                    stat.setString(4, "dummy straat");
                    stat.setString(5, "0");
                    stat.setDate(6, getRandomDate());
                    stat.setString(7, LOREMIPSUM);
                    stat.setInt(8, 1);
                    stat.setString(9, null);
                    stat.setDouble(10, 0.0d);
                    stat.setDouble(11, 0.0d);
                    stat.setInt(12, 1);
                    stat.setBoolean(13, false);
                    stat.executeUpdate();
                    log.info("Created dummy " + i + " / " + count);
                } catch (SQLException sqlEx) {
                    log.severe(sqlEx.getMessage());
                }
            }
        } catch (Exception e) {
            log.severe(e.getMessage());
        }
    }

    @Path("removeDummies")
    @DELETE
    public void removeDummies() {
        try {
            Connection conn = source.getConnection();
            Statement stat = conn.createStatement();
            stat.executeUpdate("DELETE FROM event WHERE straat LIKE 'dummy straat';");
        } catch (Exception e) {
            log.severe(e.getMessage());
        }
    }
    @Path("removeBefore/{date}")
    @DELETE
    public void removeBefore(@PathParam("date") String date) {
        try {
            Connection conn = source.getConnection();
            Statement stat = conn.createStatement();
            stat.executeUpdate("DELETE FROM event WHERE datum < \"" + date + "\"");
        } catch (Exception e) {
            log.severe(e.getMessage());
        }
    }

    public int getEventAantal() {
        int aantal = 0;

        try (Connection conn = source.getConnection()) {


            Statement stat = conn.createStatement();
            ResultSet rs;

            rs = stat.executeQuery("SELECT * FROM event ORDER BY eventnr");


            while (rs.next()) {
                aantal = rs.getInt("eventnr");

            }
            rs.close();

        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        }

        return aantal;
    }

    private String getRandomEventType() {
        String[] types = {"DangerousSituation", "RoadSurface", "NeighbourhoodEvent", "NeighbourhoodAction", "Litter"};
        try {

            Random rnd = new Random();
            return types[rnd.nextInt(5)];
        } catch (Exception e) {
            log.warning("Random event failed, returning \"DangerousSituation\"\nError: " + e.getMessage());
            return types[0];
        }

    }

    private java.sql.Date getRandomDate() {
        Random rnd = new Random();
        // Get today as a Calendar  
        Calendar today = Calendar.getInstance();
        // Subtract 1 day  
        today.add(Calendar.DATE, -rnd.nextInt(356));
        // Make an SQL Date out of that  
        java.sql.Date randomDate = new java.sql.Date(today.getTimeInMillis());
        log.info("Random date: (yyyy-mm-dd) = " + randomDate.toString());
        return randomDate;


    }
}
