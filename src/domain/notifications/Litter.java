/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.notifications;

/**
 *
 * @author Pieter De Bie
 */
class Litter extends AbstractNotification {

    @Override
    public String getType() {
        return "Vuil op de weg";
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isHazard() {
        return true;
    }

    @Override
    public boolean isEvent() {
        return false;
    }
    
}
