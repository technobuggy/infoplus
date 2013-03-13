/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.notifications;

/**
 *
 * @author Pieter De Bie
 */
class NeighbourhoodEvent extends AbstractNotification {

    @Override
    public String getType() {
        return "Evenement in uw buurt";
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isHazard() {
        return false;
    }

    @Override
    public boolean isEvent() {
        return true;
    }
    
}
