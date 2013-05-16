/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.sun.jersey.api.json.JSONWithPadding;
import domain.Adres;
import domain.notifications.AbstractNotification;
import domain.notifications.Notification;
import domain.notifications.NotificationFactory;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import javax.swing.JOptionPane;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 *
 * @author tom
 */

@Stateless
@Path("notifications")
public class NotificationService {
    
    @Resource(name = "jdbc/onzebuurt")
    private DataSource source;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<AbstractNotification> getNotifications() throws Exception {
        List<AbstractNotification> gelezenNotificaties =  new ArrayList();
        
		try (Connection conn = source.getConnection()) 
		{

	            
	            Statement stat = conn.createStatement();
	            ResultSet rs;
	            
	            	rs = stat.executeQuery("SELECT * FROM event ORDER BY eventnr");
	            
	            	
	            while (rs.next()) 
	            {   
	            	int eventnr = rs.getInt("eventnr");
	                String eventtype = rs.getString("eventtype");
	                String gemeente = rs.getString("gemeente");	 
	                String straat = rs.getString("straat");
	                String huisnr = rs.getString("nr");              
	                Date datum = rs.getDate("datum");
	                String opmerking = rs.getString("opmerking");
	                int feedbacknr = rs.getInt("feedbacknr");
                        int radius = rs.getInt("radius");
                        int visible = rs.getInt("visible");
                        String afbeelding = rs.getString("afbeelding");
	                Adres adres = new Adres();
                        adres.setStad(gemeente);
                        adres.setStraat(straat);
                        adres.setHuisnummer(huisnr);
                        adres.setMapsPos(new Point2D.Double(
                                rs.getDouble("latitude"),
                                rs.getDouble("longitude")
                                ));
                        NotificationFactory nFact = NotificationFactory.getInstance();
                        NotificationFactory.Type tempType = nFact.stringToType(eventtype);
                        gelezenNotificaties.add(nFact.createNotification(eventnr, tempType, adres, opmerking,visible,afbeelding));
	                //gelezenNotificaties.add(new Notification(eventnr, eventtype, adres, opmerking,radius));
	            }
	            rs.close();
	    } 
		catch (SQLException ex)
		{
	            for (Throwable t : ex) 
	            {
	                t.printStackTrace();
	            }
	    }
		return gelezenNotificaties;
        
    }
    
    /**
     *
     * @param callback
     * @return
     * @throws Exception
     */
    @GET
    @Path("jsonp")
    @Produces({"application/javascript"})
    @Deprecated
    public JSONWithPadding getNotificationsJsonP(@QueryParam("callback") String callback) throws Exception {
        List<Notification> gelezenNotificaties =  new ArrayList();
        
		try (Connection conn = source.getConnection()) 
		{

	            
	            Statement stat = conn.createStatement();
	            ResultSet rs;
	            
	            	rs = stat.executeQuery("SELECT * FROM event ORDER BY eventnr");
	            
	            	
	            while (rs.next()) 
	            {   
	            	int eventnr = rs.getInt("eventnr");
	                String eventtype = rs.getString("eventtype");
	                String gemeente = rs.getString("gemeente");	 
	                String straat = rs.getString("straat");
	                String huisnr = rs.getString("nr");              
	                Date datum = rs.getDate("datum");
	                String opmerking = rs.getString("opmerking");
	                int feedbacknr = rs.getInt("feedbacknr");
                        int radius = rs.getInt("radius");
	                Adres adres = new Adres();
                        adres.setStad(gemeente);
                        adres.setStraat(straat);
                        adres.setHuisnummer(huisnr);
	                gelezenNotificaties.add(new Notification(eventnr, eventtype, adres, opmerking,radius));
	            }
	            rs.close();
	            //System.out.println(lijstStudent.get(0).toString());
	    } 
		catch (SQLException ex)
		{
	            for (Throwable t : ex) 
	            {
	                t.printStackTrace();
	            }
	    }
		return new JSONWithPadding(gelezenNotificaties,callback);
        
    }
    
    
    
    /*
     * Een bestaand bericht opvragen op basis van zijn nr.
     */
    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public AbstractNotification getNotification(@PathParam("id") int id) throws Exception {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement("SELECT * FROM event WHERE eventnr = ?")) {
                stat.setInt(1, id);
                try (ResultSet rs = stat.executeQuery()) {
                    if (rs.next()) {
                        /*Notification notification = new Notification();
                        notification.setEventNr(rs.getInt("eventnr"));
                        notification.setEventNaam(rs.getString("eventtype"));
                        notification.setAdres(new Adres(rs.getString("straat"),rs.getString("gemeente"),rs.getString("nr")));
                        notification.setOpmerking("opmerking");
                        notification.setRadius(rs.getInt("radius"));
                        return notification;*/
                        int eventnr = rs.getInt("eventnr");
                        String eventtype = rs.getString("eventtype");          
	                Date datum = rs.getDate("datum");
	                String opmerking = rs.getString("opmerking");
	                int feedbacknr = rs.getInt("feedbacknr");
                        int radius = rs.getInt("radius");
                        int visible = rs.getInt("visible");
                        String afbeelding = rs.getString("afbeelding");
	                Adres adres = new Adres();
                        adres.setStad(rs.getString("gemeente"));
                        adres.setStraat(rs.getString("straat"));
                        adres.setHuisnummer(rs.getString("nr"));
                        adres.setMapsPos(new Point2D.Double(
                                rs.getDouble("latitude"),
                                rs.getDouble("longitude")
                                ));
                        NotificationFactory nFact = NotificationFactory.getInstance();
                        NotificationFactory.Type tempType = nFact.stringToType(eventtype);
                        return (nFact.createNotification(eventnr, tempType, adres, opmerking,visible,afbeelding));
                        
                    } else {
                        throw new WebApplicationException(Response.Status.NOT_FOUND);
                    }
                }
               
            }
            
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }
    
    @GET
    @Path("{id}/jsonp")
    @Produces({"application/javascript"})
        @Deprecated
    public JSONWithPadding getNotificationJsonP(@PathParam("id") int id, @QueryParam("callback") String callback) {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement("SELECT * FROM event WHERE eventnr = ?")) {
                stat.setInt(1, id);
                try (ResultSet rs = stat.executeQuery()) {
                    if (rs.next()) {
                        Notification notification = new Notification();
                        notification.setEventNr(rs.getInt("eventnr"));
                        notification.setEventNaam(rs.getString("eventtype"));
                        notification.setAdres(new Adres(rs.getString("straat"),rs.getString("gemeente"),rs.getString("nr")));
                        notification.setOpmerking("opmerking");
                        notification.setRadius(rs.getInt("radius"));
                        return new JSONWithPadding(notification, callback );
                    } else {
                        throw new WebApplicationException(Response.Status.NOT_FOUND);
                    }
                }
               
            }
            
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }
    
      /**
     *
     * @param id
     * @return
     */
    @Path("{radius}/radius")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<AbstractNotification> getNotificationRadius(@PathParam("radius") double radius) throws Exception {
        List<AbstractNotification> gelezenNotificaties =  new ArrayList();
        try (Connection conn = source.getConnection()) {
            PreparedStatement stat = conn.prepareStatement("SELECT * FROM event");
                ResultSet rs = stat.executeQuery();
                    while (rs.next()) {
                        /*Notification notification = new Notification();
                        notification.setEventNr(rs.getInt("eventnr"));
                        notification.setEventNaam(rs.getString("eventtype"));
                        notification.setAdres(new Adres(rs.getString("straat"),rs.getString("gemeente"),rs.getString("nr"),rs.getDouble("latitude"),rs.getDouble("longitude")));
                        notification.setOpmerking("opmerking");*/int eventnr = rs.getInt("eventnr");
	                String eventtype = rs.getString("eventtype");
	                String gemeente = rs.getString("gemeente");	 
	                String straat = rs.getString("straat");
	                String huisnr = rs.getString("nr");              
	                Date datum = rs.getDate("datum");
	                String opmerking = rs.getString("opmerking");
	                int feedbacknr = rs.getInt("feedbacknr");
                        int visible = rs.getInt("visible");
                        String afbeelding = rs.getString("afbeelding");
	                Adres adres = new Adres();
                        adres.setStad(gemeente);
                        adres.setStraat(straat);
                        adres.setHuisnummer(huisnr);
                        adres.setMapsPos(new Point2D.Double(
                                rs.getDouble("latitude"),
                                rs.getDouble("longitude")
                                ));
                        double latitude = rs.getDouble("latitude");
                        double longitude = rs.getDouble("longitude");
                        NotificationFactory nFact = NotificationFactory.getInstance();
                            NotificationFactory.Type tempType = nFact.stringToType(eventtype);
                        if (computeDistance(latitude,longitude, 50.937595, 4.033542)< radius)
                            //gelezenNotificaties.add(notification);
                            
                            gelezenNotificaties.add(nFact.createNotification(eventnr, tempType , adres, opmerking,visible,afbeelding));
                        
                    }  
                    rs.close(); 
	   } 
               catch (SQLException ex)
		{
	            for (Throwable t : ex) 
	            {
	                t.printStackTrace();
	            }
	    }
		return gelezenNotificaties;
		
        
    }
    
    /*
     * Een bestaand bericht met het opgegeven ID verwijderen.
     */
    @Path("{id}")
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
    
    
    @Path("{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateNotification(@PathParam("id") int id, Notification notification) {
        try (Connection conn = source.getConnection()) {
            if (notification.getEventNaam() == null) {
                throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity("Een bericht moet een naam hebben.").build());
            }
            
            /*try (PreparedStatement stat = conn.prepareStatement("SELECT * event WHERE eventnr = ?")) {
                stat.setInt(1, id);
                try (ResultSet rs = stat.executeQuery()) {
                    if (!rs.next()) {
                        throw new WebApplicationException(Response.Status.NOT_FOUND);
                    }
                }
            }*/
            
            try (PreparedStatement stat = conn.prepareStatement("UPDATE event SET eventnr = ?, eventtype = ?, gemeente = ?, straat = ?, nr =  ?, opmerking = ?,afbeelding = ? WHERE eventnr = ?")) {
                stat.setInt(1, notification.getEventNr());
                stat.setString(2, notification.getEventNaam());
                stat.setString(3, notification.getAdres().getStad());
                stat.setString(4, notification.getAdres().getStraat());
                stat.setString(5, notification.getAdres().getHuisnummer());
                stat.setString(6, notification.getOpmerking());
                stat.setString(7, notification.getAfbeelding());
                stat.setInt(8, id);
                stat.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }
    
   
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addNotification(Notification notification) {
        try (Connection conn = source.getConnection()) {
            
            if (notification.getEventNaam()== null) {
                throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity("Een bericht moet naam hebben.").build());
            }
            
            notification.setEventNr(getEventAantal()+1);
            
            try (PreparedStatement stat = conn.prepareStatement(
                    "INSERT INTO event"
                    + "(eventnr,eventtype,gemeente,straat,nr,datum, opmerking, feedbacknr,afbeelding,latitude,longitude,radius) "
                    + "VALUES(?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
                stat.setInt(1, getEventAantal() + 1);
                stat.setString(2, notification.getEventNaam());
                stat.setString(3, notification.getAdres().getStad());
                stat.setString(4, notification.getAdres().getStraat());
                stat.setString(5, notification.getAdres().getHuisnummer());
                stat.setDate(6, new java.sql.Date(new java.util.Date().getTime()));
                stat.setString(7, notification.getOpmerking());
                stat.setInt(8, 1);
                stat.setString(9, notification.getAfbeelding());
                stat.setDouble(10, notification.getAdres().getMapsPos().getX());
                stat.setDouble(11, notification.getAdres().getMapsPos().getY());
                stat.setInt(12,1);
                stat.executeUpdate();
            }
            
            return Response.created(URI.create("/" + notification.getEventNr())).build();
            
        } catch (SQLException sqlEx) {
                throw new WebApplicationException(
                        Response.status(Response.Status.BAD_REQUEST)
                        .entity(sqlEx.getMessage()).build());
        }
    }
    
  
    
    public int getEventAantal() {
        int aantal = 0;
        
		try (Connection conn = source.getConnection()) 
		{

	            
	            Statement stat = conn.createStatement();
	            ResultSet rs;
	            
	            	rs = stat.executeQuery("SELECT * FROM event ORDER BY eventnr");
	            
	            	
	            while (rs.next()) 
	            {   
	            	aantal = rs.getInt("eventnr");
	                	                
	            }
	            rs.close();
	            
	    } 
		catch (SQLException ex)
		{
	            for (Throwable t : ex) 
	            {
	                t.printStackTrace();
	            }
	    }
		
        return aantal;
    }
    
    public double  computeDistance(double startCoordsLatitude, double startCoordsLongitude, double destCoordsLatitude, double destCoordsLongitude) {
        double startLatRads = degreesToRadians(startCoordsLatitude);
        double startLongRads = degreesToRadians(startCoordsLongitude);
        double destLatRads = degreesToRadians(destCoordsLatitude);
        double destLongRads = degreesToRadians(destCoordsLongitude);
        int Radius = 6371; // radius of the Earth in km
        double distance = Math.acos(Math.sin(startLatRads) * Math.sin(destLatRads) +
        Math.cos(startLatRads) * Math.cos(destLatRads) *
        Math.cos(startLongRads - destLongRads)) * Radius;
        return distance;
    }

    private double degreesToRadians(double degrees) {
        double radians = (degrees * Math.PI)/180;
        return radians;
    }

   

        
    
}
