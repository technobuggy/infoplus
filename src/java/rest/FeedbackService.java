/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import domain.Feedback;
import java.sql.Connection;
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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author DeVidts
 */


@Stateless
@Path("feedback")
public class FeedbackService 
{
    @Resource(name = "jdbc/onzebuurt")
    private DataSource source;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Feedback> getAllFeedback() throws Exception 
    {
        List<Feedback> alleFeedback =  new ArrayList();
        try (Connection conn = source.getConnection()) 
	{
            Statement stat = conn.createStatement();
	    ResultSet rs;
	    rs = stat.executeQuery("SELECT * FROM feedback ORDER BY gebruikernr");
	         	
	    while (rs.next()) 
	    {
                int notID = rs.getInt("notificatienr");
                int userID = rs.getInt("gebruikernr");
	        boolean like = rs.getBoolean("goed");          
	        alleFeedback.add(new Feedback(notID, userID, like));
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
	return alleFeedback;
    }
    
    /*@Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Feedback> getFeedbackOnUserID(@PathParam("id") int ID) throws Exception
    {
        List<Feedback> feedbackUserID =  new ArrayList();
        try (Connection conn = source.getConnection()) 
	{
            try(PreparedStatement stat = conn.prepareStatement("SELECT * FROM feedback WHERE gebruikernr = ?"))
            {       
                stat.setInt(1, ID);
                try (ResultSet rs = stat.executeQuery())
                {
                    while (rs.next()) 
	    {
                int notID = rs.getInt("notificatienr");
                int userID = rs.getInt("gebruikernr");
	        boolean like = rs.getBoolean("goed");          
	        feedbackUserID.add(new Feedback(notID, userID, like));
	    }
            rs.close();
                }
                return feedbackUserID;
            }
          } 
        catch (SQLException ex)
	{
	    throw new WebApplicationException(ex);
        }
        }*/
    
    @Path("{notid}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Feedback> getFeedbackOnNotificationTrue(@PathParam("notid") int ID) throws Exception
    {
        List<Feedback> feedbackNotification =  new ArrayList();
        try (Connection conn = source.getConnection()) 
	{
            try(PreparedStatement stat = conn.prepareStatement("SELECT * FROM feedback WHERE notificatienr = ? order by notificatienr, gebruikernr"))
            {       
                stat.setInt(1, ID);
                try (ResultSet rs = stat.executeQuery())
                {
                    while (rs.next()) 
	    {
                int userID = rs.getInt("gebruikernr");
                int notID = rs.getInt("notificatienr");
	        boolean like = rs.getBoolean("goed"); 
                if (like == true)
                    feedbackNotification.add(new Feedback(userID, notID, like));
	    }
            rs.close();
                }
                return feedbackNotification;
            }
          } 
        catch (SQLException ex)
	{
	    throw new WebApplicationException(ex);
        }
        }
    
    @Path("{id}")
    @DELETE
    public void removeFeedback(@PathParam("id") int gebruikerNr, int notificatieNr) {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement("SELECT * FROM feedback WHERE gebruikernr = ?, notificatienr = ?")) {
                stat.setInt(1, gebruikerNr);
                stat.setInt(2, notificatieNr);
                try (ResultSet rs = stat.executeQuery()) {
                    if (rs.next()) {
                        throw new WebApplicationException(Response.Status.NOT_FOUND);
                    }
                }
            }
            
            try (PreparedStatement stat = conn.prepareStatement("DELETE FROM feedback WHERE feedbackid = ?")) {
                stat.setInt(1, gebruikerNr);
                stat.setInt(2, notificatieNr);
                stat.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }
    
    @Path("{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateFeedback(@PathParam("id") int userID, int notID, Feedback feedback) 
    {
        try (Connection conn = source.getConnection()) 
        {
            try (PreparedStatement stat = conn.prepareStatement("UPDATE feedback SET gebruikernr = ?, notificatienr = ?, goed = ? WHERE gebruikernr = ?, notificatienr = ?")) {
                stat.setInt(1, feedback.getUserID());
                stat.setInt(2, feedback.getNotID());
                stat.setBoolean(3, feedback.getLike());
                stat.setInt(4, userID);
                stat.setInt(5, notID);
                stat.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void postFeedback(Feedback feedback) 
    {
        if(controleBestaatAlTrue(feedback)== false){
            try (Connection conn = source.getConnection()) 
            {
                try (PreparedStatement stat = conn.prepareStatement("INSERT INTO feedback (gebruikernr, notificatienr,goed) VALUES (?, ?,?)")) {
                    System.out.println(feedback.toString());
                    stat.setInt(1, feedback.getUserID());
                    stat.setInt(2, feedback.getNotID());
                    stat.setBoolean(3, feedback.getLike());
                    //stat.setInt(1, 3);
                    //stat.setInt(2, 3);
                    //stat.setBoolean(3, true);
                    stat.executeUpdate();
                }
            } catch (SQLException ex) {
                throw new WebApplicationException(ex);
            }
        }
    }
    
     public boolean controleBestaatAlTrue(Feedback feedback)
    {
        
        int aantal = 0;
		try (Connection conn = source.getConnection()) 
		{

	            
	            PreparedStatement stat = conn.prepareStatement("SELECT * FROM feedback where gebruikernr = ? and notificatienr = ? and goed = true");
	            stat.setInt(1, feedback.getUserID());
                    stat.setInt(2, feedback.getNotID());
                    try (ResultSet rs = stat.executeQuery())
                    {
                        while (rs.next()) 
                        {
                        aantal = 1;
                        }
                        rs.close();
                    }
                    
                    
	            
	    } 
		catch (SQLException ex)
		{
	            for (Throwable t : ex) 
	            {
	                t.printStackTrace();
	            }
	    }
            System.out.println("aantal keer voorkomen : " + aantal);
           if (aantal == 0)
                        return false;
                    else 
                        return true;
        
        
       
    }
    
    }

   