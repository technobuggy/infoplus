package rest;

import domain.Adres;
import domain.Gebruiker;
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
import java.util.List;
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
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author Sam
 */
@Stateless
@Path("gebruiker")
public class GebruikerService 
{
    
    @Resource(name = "jdbc/onzebuurt")
    private DataSource source;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Gebruiker> getGebruikers() throws Exception {
        List<Gebruiker> gelezenGebruikers =  new ArrayList();
        
		try (Connection conn = source.getConnection()) 
		{

	            
	            Statement stat = conn.createStatement();
	            ResultSet rs;
	            
	            	rs = stat.executeQuery("SELECT * FROM gebruiker ORDER BY gebruikerid");
	            
	            	
	            while (rs.next()) 
	            {   
                        int gebruikernr = rs.getInt("gebruikerid");
	            	String email = rs.getString("email");
	                String naam = rs.getString("naam");
                        boolean mapsview = rs.getBoolean("mapsview");
                        int radius = rs.getInt("radius");
	                String wachtwoord = rs.getString("wachtwoord");
	                
                       
	                
                        //Gebruiker gebruiker = new Gebruiker();
	                
                        //gebruiker.setGebruikernr(gebruikernr);
                        //gebruiker.setEmail(email);
                        //gebruiker.setNaam(naam);
                        //gebruiker.setWachtwoord(wachtwoord);
	                gelezenGebruikers.add(new Gebruiker(gebruikernr, email, naam, wachtwoord, radius, mapsview));
                        //gelezenGebruikers.add(gebruiker);
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
		System.out.println(gelezenGebruikers.toString());
                return gelezenGebruikers;
        
    }
    
    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Gebruiker getGebruiker(@PathParam("id") int id) {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement("SELECT * FROM gebruiker WHERE gebruikerid = ?")) {
                stat.setInt(1, id);
                try (ResultSet rs = stat.executeQuery()) {
                    if (rs.next()) {
                        Gebruiker gebruiker = new Gebruiker();
                        gebruiker.setGebruikernr(rs.getInt("gebruikerid"));
                        gebruiker.setEmail(rs.getString("email"));
                        gebruiker.setNaam(rs.getString("naam"));
                        gebruiker.setWachtwoord(rs.getString("wachtwoord"));
                        gebruiker.setRadius(rs.getInt("radius"));
                        gebruiker.setMapsview(rs.getBoolean("mapsview"));
                        return gebruiker;
                    } else {
                        throw new WebApplicationException(Response.Status.NOT_FOUND);
                    }
                }
               
            }
            
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }
    /*
    @Path("{login}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Gebruiker loginGebruiker(@PathParam("email") String email, @PathParam("pass") String pass) {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement(
                    "SELECT * FROM gebruiker WHERE email = ? AND wachtwoord =?")) {
                stat.setString(1, email);
                stat.setString(2, pass);
                try (ResultSet rs = stat.executeQuery()) {
                    if (rs.next()) {
                        Gebruiker gebruiker = new Gebruiker();
                        gebruiker.setGebruikernr(rs.getInt("gebruikernr"));
                        gebruiker.setEmail(rs.getString("email"));
                        gebruiker.setNaam(rs.getString("naam"));
                        gebruiker.setWachtwoord(rs.getString("wachtwoord"));
                        gebruiker.setRadius(rs.getInt("radius"));
                        gebruiker.setMapsview(rs.getBoolean("mapsview"));
                        return gebruiker;
                    } else {
                        throw new WebApplicationException(Response.Status.NOT_FOUND);
                    }
                }
               
            }
            
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }*/
    
    @Path("{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateGebruiker(@PathParam("id") int id, Gebruiker gebr) {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement("SELECT * FROM gebruiker WHERE gebruikerid = ?")) {
                stat.setInt(1, id);
                try (ResultSet rs = stat.executeQuery()) {
                    if (!rs.next()) {
                        throw new WebApplicationException(Status.NOT_FOUND);
                    }
                }
            }
            try (PreparedStatement stat = conn.prepareStatement("UPDATE gebruiker SET gebruikerid = ?,  radius = ?, mapsview = ? WHERE gebruikerid = ?")) {
                stat.setInt(1, id);
                //stat.setString(2, gebr.getEmail());
                //stat.setString(3, gebr.getNaam());
                stat.setInt(2, gebr.getRadius());
                stat.setBoolean(3, gebr.getMapsview());
                //stat.setString(6, gebr.getWachtwoord());
                stat.setInt(4, id);
                stat.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }
    
    /*
     * Een bestaande gebruiker met het opgegeven ID verwijderen.
     */
    @Path("{id}")
    @DELETE
    public void removeGebruiker(@PathParam("id") int id) {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement("SELECT * FROM gebruiker WHERE gebruikernr = ?")) {
                stat.setInt(1, id);
                try (ResultSet rs = stat.executeQuery()) {
                    if (!rs.next()) {
                        throw new WebApplicationException(Status.NOT_FOUND);
                    }
                }
            }
            try (PreparedStatement stat = conn.prepareStatement("DELETE FROM gebruiker WHERE gebruikernr = ?")) {
                stat.setInt(1, id);
                stat.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }
    /**
     * @author Pieter De Bie
     * @param user gebruiker email
     * @param pass gebruiker wachtwoord
     * @return gebruiker 
     * @throws Exception 
     */
    @Path("login/{user}/{pass}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Gebruiker loginUser(
            @PathParam("user") String user,
            @PathParam("pass") String pass) throws Exception {

        Gebruiker result = new Gebruiker();
        try (Connection conn = source.getConnection()) {
            Statement stat = conn.createStatement();
            ResultSet rs;

            String query = "SELECT * "
                    + "FROM gebruiker "
                    + "WHERE email = '" + user + "' "
                    + "AND wachtwoord = '" + pass + "';";
            rs = stat.executeQuery(query);

            if (rs.next()) {
                result.setGebruikernr(rs.getInt("gebruikerID"));
                result.setEmail(rs.getString("email"));
                result.setNaam(rs.getString("naam"));
                result.setWachtwoord(rs.getString("EmptyForSafetyReasons")); 
                result.setRadius(rs.getInt("radius"));
                result.setMapsview(rs.getBoolean("mapsview"));
            } else {
                result = null;
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
    /**
     *
     */
    @POST
    @Path("new")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(Gebruiker gebruiker){
        try (final Connection conn = source.getConnection();) {
            String sql = "INSERT INTO gebruiker (gebruikerID,email,naam,wachtwoord,radius,mapsview) "
                    + "VALUES(?,?,?,?,?,?);";
            try (PreparedStatement stat = conn.prepareStatement(sql)){
                stat.setInt(1, getNewID());
                stat.setString(2, gebruiker.getEmail());
                stat.setString(3, gebruiker.getNaam());
                stat.setString(4, gebruiker.getWachtwoord());
                stat.setInt(5, gebruiker.getRadius());
                stat.setBoolean(6, gebruiker.getMapsview());
            }
        } catch (SQLException ex) {
            Logger.getLogger(GebruikerService.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return Response.created(URI.create("/" + gebruiker.getEmail())).build();
    }
    
    private int getNewID(){
        int newID = -1;
        try (Connection conn = source.getConnection()) {
            PreparedStatement stat = conn.prepareStatement("SELECT MAX(gebruikerID) AS 'id' FROM gebruiker;");
                ResultSet rs = stat.executeQuery();
                    while (rs.next()) {
                         newID = rs.getInt("id");
                         newID++;
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
        return newID;
    }


}
