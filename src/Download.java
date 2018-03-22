
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Meluleki
 */
public class Download implements Runnable {

    File fileObj;
    String Path;

    public Download(File fileObj, String Path) {
        this.Path = Path;
        this.fileObj = fileObj;
    }
    public Download(){
       fileObj= new File("try");
    }

    @Override
    public void run() {
        FileInputStream in = null;
        FileOutputStream out = null;
        try {

            in = new FileInputStream(fileObj);
            out = new FileOutputStream(Path);//fileNAme as well should be included in the path

            int c;
            try {
                while ((c = in.read()) != -1) {
                    out.write(c);
                }
            } catch (IOException ex) {
                Logger.getLogger(Download.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        catch (FileNotFoundException ex) {
            Logger.getLogger(Download.class.getName()).log(Level.SEVERE, null, ex);
        } 
        finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(Download.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    Logger.getLogger(Download.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        

    }

}
