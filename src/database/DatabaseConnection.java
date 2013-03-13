/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import domain.Adres;
import domain.notifications.AbstractNotification;
import domain.notifications.NotificationFactory;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
/**
 * Een connectie tussen de database en de applicatie, momenteel nog een
 * simulatie.
 * @author Tom Van Buggenhoudt
 * @version 1.0
 * @author Pieter De Bie
 * @since 19-02-13
 * @version 0.1
 */

public final class DatabaseConnection {
    private DatabaseConnection() { }
    private static DatabaseConnection uniqueInstance = null;
    /**
     * De singleton wordt lazy opgeroepen. Zo wordt de connectie enkel geladen 
     * als de database echt gebruikt gaat worden.
     * @return
     */
    public static DatabaseConnection getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new DatabaseConnection();
        }
        return uniqueInstance;
    }
   
    /**
     * Schrijft alle notificaties naar de DB, momenteel enkel nog een
     * simulatie.
     * @param iterator de iterator met alle notificaties in
     */
    
    @Deprecated
    public final void writeSimulation(Iterator<AbstractNotification> iterator) {
        System.out.println("Start simulation\n");
        int i = 0;
        //elke item in de iterator overlopen en de gegevens weergeven
        while (iterator.hasNext()) {
            AbstractNotification tempNotification = iterator.next();
            System.out.println("Writing: " + ++i +
                    "\n\t" + tempNotification.getAdres().getStad() +
                    "\n\t" + tempNotification.getAdres().getStraat() +
                    "\n\t" + tempNotification.getDescription() +
                    "\n\t" + tempNotification.getType() +
                    "\n\tIs hazard: " + tempNotification.isHazard() +
                    "\nEnd of notification " + i
                ); //end println
        }//end while
        System.out.println("End simulation\n");
    }
    /**
     * Toekomstige functie om alle notifications uit de db te halen
     * @return Alle notificaties in een iterator
     */
    public Iterator<AbstractNotification> getNotifications() throws Exception {
        List<AbstractNotification> gelezenNotificaties =  new ArrayList();
        
		try (Connection conn = DriverManager.getConnection("jdbc:odbc:onzebuurt")) 
		{

	            
	            Statement stat = conn.createStatement();
	            ResultSet rs;
	            
	            	rs = stat.executeQuery("SELECT * FROM events ORDER BY eventsnr");
	            
	            	
	            while (rs.next()) 
	            {   
	            	int eventnr = rs.getInt("eventsnr");
	                String eventnaam = rs.getString("eventsnaam");
	                String gemeente = rs.getString("gemeente");	 
	                String straat = rs.getString("straat");
	                String huisnr = rs.getString("nr");              
	                Date datum = rs.getDate("datum");
	                String opmerking = rs.getString("opmerking");
	                int feedbacknr = rs.getInt("feedbacknr");
	                Adres adres = new Adres();
                        adres.setStad(gemeente);
                        adres.setStraat(straat);
                        adres.setHuisnummer(huisnr);
	                gelezenNotificaties.add(NotificationFactory.createNotification(NotificationFactory.Type.DANGEROUSSITUATION, adres, opmerking));
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
		return (Iterator<AbstractNotification>) gelezenNotificaties;
        
    }
    
   public int eventAantal() {
        int aantal = 0;
        
		try (Connection conn = DriverManager.getConnection("jdbc:odbc:onzebuurt")) 
		{

	            
	            Statement stat = conn.createStatement();
	            ResultSet rs;
	            
	            	rs = stat.executeQuery("SELECT * FROM event ORDER BY eventsnr");
	            
	            	
	            while (rs.next()) 
	            {   
	            	aantal = rs.getInt("eventsnr");
	                	                
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
		
        return aantal;
    }
   
   public int afbeeldingAantal(){
       int aantal = 0;
        
		try (Connection conn = DriverManager.getConnection("jdbc:odbc:onzebuurt")) 
		{

	            
	            Statement stat = conn.createStatement();
	            ResultSet rs;
	            
	            	rs = stat.executeQuery("SELECT * FROM afbeelding ORDER BY nr");
	            
	            	
	            while (rs.next()) 
	            {   
	            	aantal = rs.getInt("nr");
	                	                
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
		
        return aantal;
   }
   
      
   public boolean eventOpzoeken(int eventnr)  //land opzoeken adhv zijn landid
	{
		int eventNrOpzoeking = 0;
		try (Connection conn = DriverManager.getConnection("jdbc:odbc:onzebuurt")) 
		{
	            PreparedStatement opzoekenEvent = conn.prepareStatement("SELECT * FROM events where eventsnr = ?");
				opzoekenEvent.setInt(1, eventnr);
				ResultSet rs = opzoekenEvent.executeQuery();
	            while (rs.next()) 
	            {   
	                eventNrOpzoeking = rs.getInt("eventsnr");
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
		if (eventNrOpzoeking==0) 
                        return false;
                else         
                        return true;
	}
    
    
    public void opslaanEvent(AbstractNotification objMelding,boolean nieuw) //insert en update van student
	{
            int len;
            try (Connection conn = DriverManager.getConnection("jdbc:odbc:onzebuurt")) 
		{
			if (nieuw)
			{
				PreparedStatement nieuwEvent = conn.prepareStatement("INSERT INTO event VALUES (?,?, ?, ?,?,?,?,?,?)");
                                File file = new File("f:\\no_image.gif");
                            
                                FileInputStream fis;
                            try {
                                fis = new FileInputStream(file);
                                len = (int)file.length();
				nieuwEvent.setInt(1, eventAantal() + 1);
				nieuwEvent.setString(2, objMelding.getType());
				nieuwEvent.setString(3, objMelding.getAdres().getStad());
				nieuwEvent.setString(4, objMelding.getAdres().getStraat());
				nieuwEvent.setString(5, objMelding.getAdres().getHuisnummer());
				nieuwEvent.setDate(6, new java.sql.Date(objMelding.getCreated().getTime()));
				nieuwEvent.setString(7, objMelding.getDescription());
				nieuwEvent.setInt(8, 1);
                                if (afbeeldingAantal() == 1 ){
                                    nieuwEvent.setBinaryStream(9, fis, len);
                                } else {
                                    file = new File(uitlezenAfbeelding());
                                    fis = new FileInputStream(file);
                                    len = (int)file.length();
                                    nieuwEvent.setBinaryStream(9, fis, len);
                                    PreparedStatement verwijderAfbeelding = conn.prepareStatement("Delete from afbeelding where nr = ?");
                                    verwijderAfbeelding.setInt(1, afbeeldingAantal());
                                    verwijderAfbeelding.executeUpdate();
                                }
                                
				nieuwEvent.executeUpdate();
                                
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
                            }
                                
                           
                                
			}
			else
			{
				PreparedStatement updateEvent = conn.prepareStatement("UPDATE event SET eventsnaam = ? , gemeente = ? , straat = ?, nr = ?, datum = ? , opmerking = ?, feedbacknr = ? WHERE eventsnr = ?");
				
				updateEvent.setString(1, objMelding.getType());
				updateEvent.setString(2, objMelding.getAdres().getStad());
				updateEvent.setString(3, objMelding.getAdres().getStraat());
				updateEvent.setString(4, objMelding.getAdres().getHuisnummer());
				updateEvent.setDate(5, new java.sql.Date(objMelding.getCreated().getTime()));
				updateEvent.setString(6, objMelding.getDescription());
				updateEvent.setInt(7, 1);
                                updateEvent.setInt(8, 2);
				updateEvent.executeUpdate();
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
    
    public void opslaanTijdelijkAfbeelding(File file){
        int len;
        try {
            Connection conn = DriverManager.getConnection("jdbc:odbc:onzebuurt");
             PreparedStatement saveImage = conn.prepareStatement("Insert into afbeelding values(?,?,?)");
                
                         FileInputStream fis = new FileInputStream(file);
                         len = (int)file.length();
 
             			saveImage.setInt(1, afbeeldingAantal() + 1);
				saveImage.setBinaryStream(2, fis, len);
                                saveImage.setString(3, file.getAbsolutePath());
                                saveImage.executeUpdate();
      } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String uitlezenAfbeelding(){
         //BufferedImage image = null;
         String pad = "";
         try (Connection conn = DriverManager.getConnection("jdbc:odbc:onzebuurt")) {

            
                 
            
            
            
            PreparedStatement loadImage = conn.prepareStatement("SELECT * FROM afbeelding WHERE nr = ?");
            loadImage.setInt(1, afbeeldingAantal());
            try (ResultSet rs = loadImage.executeQuery()) {
                
                
                if (rs.next()) {
                    
                    
                    pad = rs.getString("pad");
                } else {
                    System.out.println("Afbeelding niet gevonden in databank.");
                }
            }
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
          
        }
         return pad;
    }
    
   
}
