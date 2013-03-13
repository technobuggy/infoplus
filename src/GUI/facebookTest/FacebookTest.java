/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.facebookTest;

import domain.Adres;
import domain.notifications.AbstractNotification;
import domain.notifications.NotificationFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Pieter De Bie
 */
public class FacebookTest extends Application {

    /**
     *
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("facebookTest.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        AbstractNotification testAdres = NotificationFactory.createNotification(NotificationFactory.Type.LITTER, new Adres(), "test");
        testAdres.isHazard();
    }
}