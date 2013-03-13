/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.notifications;

/**
 *
 * @author Pieter De Bie
 */
class DangerousSituation extends AbstractNotification {

    @Override
    public String getType() {
        return "Gevaarlijke situatie";
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
