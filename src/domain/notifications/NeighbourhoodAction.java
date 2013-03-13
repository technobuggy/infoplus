/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.notifications;

/**
 *
 * @author Pieter De Bie
 */
class NeighbourhoodAction extends AbstractNotification {

    @Override
    public String getType() {
        return "Actie in uw buurt";
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
