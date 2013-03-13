/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.facebookTest;

import com.restfb.FacebookClient;
import com.restfb.types.User;
import database.DatabaseConnection;
import domain.Adres;
import domain.notifications.AbstractNotification;
import domain.notifications.NotificationFactory;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import jfx.messagebox.MessageBox;
import social.facebook.FacebookAdapter;
import social.facebook.FacebookConnectionBuilder;

/**
 * FXML Controller class
 *
 * @author Pieter De Bie
 */
public class FacebookTestController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    Label lblStatus;
    @FXML
    TextField txtStad, txtStraat, txtNR;
    @FXML
    TextArea txtDescription;
    @FXML
    Button btnPost,btnSave;
    @FXML
    ComboBox cbxType = new ComboBox();

    /**
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getStatus();
        ObservableList<String> options = FXCollections.observableArrayList(
                "Gevaarlijke situatie",
                "Wegdek",
                "Vuil",
                "Buurt evenement",
                "Buurt actie");
        cbxType.setItems(options);
    }

    private void getStatus() {
        try {
            FacebookClient fbClient = FacebookConnectionBuilder.createTestConnection();
            User user = fbClient.fetchObject("me", User.class);
            lblStatus.setText("Loged in as " + user.getName());
        } catch (Exception e) {
            lblStatus.setText("Unable to log in: " + e.getMessage());
            btnPost.setDisable(true);
        }
    }

    @FXML
    private void saveToDatabase(ActionEvent event) {
        DatabaseConnection dbCon = DatabaseConnection.getInstance();
        Adres tempAdres = fetchAdres();
        AbstractNotification tempNotification = NotificationFactory.createNotification(NotificationFactory.Type.DANGEROUSSITUATION, tempAdres, txtDescription.getText());
        
        dbCon.opslaanEvent(tempNotification, true);
    }
    
    @FXML
    private void postToFacebook(ActionEvent event) {
        Adres tempAdres = fetchAdres();
        AbstractNotification tempNotification = NotificationFactory.createNotification(
                NotificationFactory.Type.DANGEROUSSITUATION, tempAdres, txtDescription.getText());

        FacebookAdapter tempAdapter = new FacebookAdapter(tempNotification);
        try {
            tempAdapter.publish();
                        MessageBox.show(null,
                    "Het bericht is succesvol op facebook geplaatst",
                    "Bericht verzonden",
                    MessageBox.ICON_INFORMATION | MessageBox.OK);
        } catch (Exception e) {
            MessageBox.show(null,
                    "Het bericht kon niet verzonden worden. Controlleer of je"
                    + " wel degelijk ingelogd bent bij facebook en onze app"
                    + " nog steeds toegang heeft.",
                    "Fout bij het posten naar facebook",
                    MessageBox.ICON_INFORMATION | MessageBox.OK);
        }
    }
    private Adres fetchAdres() {
        try {
        final Adres tempAdres = new Adres();
        tempAdres.setStad(txtStad.getText());
        tempAdres.setStraat(txtStraat.getText());
        tempAdres.setHuisnummer(txtNR.getText());
        return tempAdres;
        } catch (Exception e) {
            MessageBox.show(null,
                    "Fout bij het adresveld: \n" +
                    e.getMessage(),
                    "Fout",
                    MessageBox.ICON_ERROR | MessageBox.OK);
        }
        return null;        
    }
}
