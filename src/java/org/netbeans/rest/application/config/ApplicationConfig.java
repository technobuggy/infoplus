package org.netbeans.rest.application.config;

import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/*
 * Deze klasse wordt door NetBeans vanzelf gegenereerd. Pas ze dan ook niet aan,
 * met uitzondering van onderstaande annotatie. Deze geeft de relatieve URL
 * (binnen de applicatie) aan waarop de resources te vinden zijn.
 */
@ApplicationPath("resources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        return getRestResourceClasses();
    }

    /**
     * Do not modify this method. It is automatically generated by NetBeans REST support.
     */
    private Set<Class<?>> getRestResourceClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        resources.add(rest.NotificationService.class);
        resources.add(rest.GebruikerService.class);
        resources.add(rest.FeedbackService.class);
        resources.add(rest.AdminService.class);
        resources.add(rest.ImageService.class);
        try {
            Class<?> jacksonProvider = Class.forName("org.codehaus.jackson.jaxrs.JacksonJsonProvider");
            resources.add(jacksonProvider);
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return resources;
    }
    
    
}