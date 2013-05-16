/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.notifications;

import domain.Feedback;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Proxy voor Feedback over de notificatie.
 * In plaats van simpelweg een feedbacknummer
 * bevat deze klasse gebruikerID's die positief en negatief
 * gestemt hebben, zo kan iedereen slechts 1 keer feedback geven
 * @author Pieter De Bie
 */
public class Appreciation {
    /*
     * Een positieve beoordeling toevoegen (+1).
     * 
     * Kijkt of de user al een beoordeling gegeven heeft. Zoja wordt deze
     * beoordeling ongedaan gemaakt. Als deze nog geen oordeel gegeven heeft
     * wordt dit oordeel toegevoegd.
     */
    
    @Resource(name = "jdbc/onzebuurt")
    private DataSource source;

    public void positive(int userID, int notID)throws Exception
    {
        try(Connection conn = source.getConnection())
        {
            Statement stat = conn.createStatement();
            ResultSet rs;
            rs = stat.executeQuery("SELECT gebruikernr,notificatienr FROM feedback ORDER BY gebruikernr");
            
            while(rs.next())
            {
                if (exists(userID, notID)) 
                {
                    try (PreparedStatement stat2 = conn.prepareStatement("DELETE FROM feedback WHERE gebruikernr = ? AND notificatienr = ?")) 
                    {
                        stat2.setInt(1, userID);
                        stat2.setInt(2, notID);
                        stat2.executeUpdate();
                    }
                    catch (SQLException ex) 
                    {
                        throw new WebApplicationException(ex);
                    }            
                } 
            }
            rs.close();
            
            try (PreparedStatement stat3 = conn.prepareStatement("INSERT INTO feedback(gebruikernr, notificatienr, goed) VALUES(?, ?, ?)")) 
            {
                stat3.setInt(1, userID);
                stat3.setInt(2, notID);
                stat3.setBoolean(3, true);
                stat3.executeUpdate();
            }
        }
        catch (SQLException ex)
	{
	    for (Throwable t : ex)
	    {
	        t.printStackTrace();
	    }
	}
    }
    
    public void negative(int userID, int notID)
    {
        try(Connection conn = source.getConnection())
        {
            Statement stat = conn.createStatement();
            ResultSet rs;
            rs = stat.executeQuery("SELECT gebruikernr,notificatienr FROM feedback ORDER BY gebruikernr");
            
            while(rs.next())
            {
                if (exists(userID, notID)) 
                {
                    try (PreparedStatement stat2 = conn.prepareStatement("DELETE FROM feedback WHERE gebruikernr = ? AND notificatienr = ?")) 
                    {
                        stat2.setInt(1, userID);
                        stat2.setInt(2, notID);
                        stat2.executeUpdate();
                    }
                    catch (SQLException ex) 
                    {
                        throw new WebApplicationException(ex);
                    }            
                } 
            }
            rs.close();
            
            try (PreparedStatement stat3 = conn.prepareStatement("INSERT INTO feedback(gebruikernr, notificatienr, goed) VALUES(?, ?, ?)")) 
            {
                stat3.setInt(1, userID);
                stat3.setInt(2, notID);
                stat3.setBoolean(3, false);
                stat3.executeUpdate();
            }
        }
        catch (SQLException ex)
	{
	    for (Throwable t : ex)
	    {
	        t.printStackTrace();
	    }
	}
    }
    
    private boolean exists(int userID, int notID) 
    {
        boolean exist = true;
        try(Connection conn = source.getConnection())
            {
                try(PreparedStatement stat = conn.prepareStatement("SELECT gebruikernr,notificatienr FROM feedback WHERE gebruikernr = ? AND notificatienr = ?"))
                {       
                    stat.setInt(1, userID);
                    stat.setInt(2, notID);
                    try (ResultSet rs = stat.executeQuery())
                    {
                        if(rs.next())
                        {
                            exist = true;
                        }
                        else
                        {
                            exist = false;
                        }
                    }
                }
            }
            
            catch (SQLException ex)
            {
                for (Throwable t : ex) 
                {
                    t.printStackTrace();
                }
            }
        return exist;
    }

    public int getCountPositive(int notID) 
    {
        int positive = 0;
        try(Connection conn = source.getConnection())
            {
                try(PreparedStatement stat = conn.prepareStatement("SELECT * FROM feedback WHERE goed = ?, notificatienr = ?"))
                {       
                    stat.setInt(1, 1);
                    stat.setInt(2, notID);
                    try (ResultSet rs = stat.executeQuery())
                    {
                        while(rs.next())
                        {
                            positive++;
                        }
                        rs.close();
                    }
                }
            }
            
            catch (SQLException ex)
            {
                for (Throwable t : ex) 
                {
                    t.printStackTrace();
                }
            }
        return positive;
    }
    
    public int getCountNegative(int notID) 
    {
        int negative = 0;
        try(Connection conn = source.getConnection())
            {
                try(PreparedStatement stat = conn.prepareStatement("SELECT * FROM feedback WHERE goed = ?, notificatienr = ?"))
                {       
                    stat.setInt(1, 0);
                    stat.setInt(2, notID);
                    try (ResultSet rs = stat.executeQuery())
                    {
                        while(rs.next())
                        {
                            negative++;
                        }
                        rs.close();
                    }
                }
            }
            
            catch (SQLException ex)
            {
                for (Throwable t : ex) 
                {
                    t.printStackTrace();
                }
            }
        return negative;
    }
}

