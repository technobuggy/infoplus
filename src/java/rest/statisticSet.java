/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import java.sql.Date;
import java.util.Iterator;

/**
 *
 * @author Pieter De Bie
 */
public class statisticSet {

    private java.sql.Date lastNotification;
    private long unpublishedCount;
    private long publishedCount;
    private long DangerousSituationCount;
    private long LitterCount;
    private long NeighbourhoodActionCount;
    private long NeighbourhoodEventCount;
    private long roadsurface;    
    
    public statisticSet(Iterator<Object> results) {
        lastNotification = (java.sql.Date) results.next();
        unpublishedCount = (long) results.next();
        publishedCount = (long) results.next();
        DangerousSituationCount = (long) results.next();
        LitterCount = (long) results.next();
        NeighbourhoodActionCount = (long) results.next();
        NeighbourhoodEventCount = (long) results.next();
        roadsurface = (long) results.next();
    }

    public Date getLastNotification() {
        return lastNotification;
    }

    public void setLastNotification(Date lastNotification) {
        this.lastNotification = lastNotification;
    }

    public long getUnpublishedCount() {
        return unpublishedCount;
    }

    public void setUnpublishedCount(long unpublishedCount) {
        this.unpublishedCount = unpublishedCount;
    }

    public long getPublishedCount() {
        return publishedCount;
    }

    public void setPublishedCount(long publishedCount) {
        this.publishedCount = publishedCount;
    }

    public long getDangerousSituationCount() {
        return DangerousSituationCount;
    }

    public void setDangerousSituationCount(long DangerousSituationCount) {
        this.DangerousSituationCount = DangerousSituationCount;
    }

    public long getLitterCount() {
        return LitterCount;
    }

    public void setLitterCount(long LitterCount) {
        this.LitterCount = LitterCount;
    }

    public long getNeighbourhoodActionCount() {
        return NeighbourhoodActionCount;
    }

    public void setNeighbourhoodActionCount(long NeighbourhoodActionCount) {
        this.NeighbourhoodActionCount = NeighbourhoodActionCount;
    }

    public long getNeighbourhoodEventCount() {
        return NeighbourhoodEventCount;
    }

    public void setNeighbourhoodEventCount(long NeighbourhoodEventCount) {
        this.NeighbourhoodEventCount = NeighbourhoodEventCount;
    }

    public long getRoadsurface() {
        return roadsurface;
    }

    public void setRoadsurface(long roadsurface) {
        this.roadsurface = roadsurface;
    }
    
}
