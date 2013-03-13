/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.completeGUI;

import database.DatabaseConnection;
import domain.Adres;
import domain.notifications.AbstractNotification;
import domain.notifications.NotificationFactory;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import jfx.messagebox.MessageBox;
import social.facebook.FacebookAdapter;

/**
 * FXML Controller class
 *
 * @author Pieter De Bie
 */
public class CompleteGUIController implements Initializable {

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @FXML
    private WebView mapViewBrowser = new WebView();
    @FXML
    private Button btnOpslaan, btnFacebook, btnAfbeelding;
    @FXML
    private TextField txtGemeente, txtStraat, txtNr;
    @FXML
    private TextArea txtOmschrijving;
    @FXML
    private ComboBox<NotificationFactory.Type> cbxType;
    @FXML
    private ImageView imageView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        mapViewBrowser.getEngine().load("http://infoplus.pieterdb.be/googleMap.html");
        System.out.println("test: " + mapViewBrowser.getEngine().getTitle());

        btnOpslaan = new Button();
        btnFacebook = new Button();
        btnAfbeelding = new Button();
        //imageView = new ImageView();
        ObservableList<NotificationFactory.Type> cbxList = FXCollections.observableArrayList(
                NotificationFactory.Type.DANGEROUSSITUATION,
                NotificationFactory.Type.LITTER,
                NotificationFactory.Type.NEIGHBOURHOODACTION,
                NotificationFactory.Type.NEIGHBOURHOODEVENT,
                NotificationFactory.Type.ROADSURFACE);
        cbxType.setItems(cbxList);
    }

    @FXML
    public void saveNotificaton() {
        try {
            DatabaseConnection dbConnection = DatabaseConnection.getInstance();
            dbConnection.opslaanEvent(fetchNotification(), true);
            MessageBox.show(null,
                    "Het bericht is succesvol opgeslaan",
                    "Succes",
                    MessageBox.ICON_INFORMATION | MessageBox.OK);
        } catch (Exception e) {
            MessageBox.show(null,
                    "Er is een fout opgetreden bij het connecteren met de "
                    + "database, probeer later opnieuw.",
                    "Fout",
                    MessageBox.ICON_ERROR | MessageBox.OK);
        }
    }
    @FXML
    public void postToFacebook() {
        final FacebookAdapter fbObject =
                new FacebookAdapter(fetchNotification());
        try {
            fbObject.publish();
            MessageBox.show(null,
                    "Het bericht is succesvol op facebook gepubliceerd",
                    "Succes",
                    MessageBox.ICON_INFORMATION | MessageBox.OK);
        } catch (Exception e) {
            MessageBox.show(null,
                    "Het bericht kon niet op facebook gepubliceerd worden.\n"
                    + "controlleer of u de applicatie voldoende machtigingen"
                    + "gegeven heeft en of u ingelogd bent op facebook.",
                    "Fout",
                    MessageBox.ICON_ERROR | MessageBox.OK);
        }

    }
    
    public void voegAfbeeldingToe(){
        try {       
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("."));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
            chooser.setFileFilter(filter);
            chooser.showOpenDialog(null);
            File file = chooser.getSelectedFile();
            DatabaseConnection dbConnection = DatabaseConnection.getInstance();
            dbConnection.opslaanTijdelijkAfbeelding(file);
            BufferedImage bufferedImage = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);       
            imageView.setImage(image);
            imageView.setPreserveRatio(true);
            imageView.setCache(true);
        } catch (IOException ex) {
            Logger.getLogger(CompleteGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        

        
    }

    private AbstractNotification fetchNotification() {
        NotificationFactory nf = NotificationFactory.getInstance();
        return nf.createNotification(cbxType.getValue(), fetchAdres(), 
                txtOmschrijving.getText());
    }

    private Adres fetchAdres() {
        return new Adres(txtStraat.getText(), txtGemeente.getText(), 
                txtNr.getText());
    }
}
