/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 *
 * @author Administrator
 */
public class Afbeelding {
    private File file ;
    
    private int nr;

    public Afbeelding(File file, int nr) {
        this.file = file;
        this.nr = nr;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    @Override
    public String toString() {
        return "Afbeelding{" + "file=" + file + ", nr=" + nr + '}';
    }
    
    
}
