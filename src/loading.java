
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Meluleki
 */
public class loading {

    JFrame frame;
    JPanel panel;
    JProgressBar pBar;
    JLabel label;
    Timer timer;
    private boolean done;

    public loading() {
        done = false;
        pBar = new JProgressBar(0, 100);
        frame = new JFrame("Disconnecting from server");
        panel = new JPanel();
        label = new JLabel("Please wait while we disconnect you from the server!");
        setUPGUI();
    }

    private void setUPGUI() {
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);
        frame.setLocationRelativeTo(null);
        if (!done) {
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        }
        GridLayout layout = new GridLayout(2, 1);
        panel.setLayout(layout);
        panel.add(label);
        panel.add(pBar);
        frame.add(panel);
        frame.pack();
    }

    void updateBar(int newVal) {
        pBar.setValue(newVal);
        pBar.setName("" + newVal);
    }

    void countDown() {
        timer = new Timer();
        TimerTask tt = new TimerTask() {
            final int max = 100;
            private int count = 0;

            @Override
            public void run() {
                if (count == max) {
                    timer.cancel();
                    System.exit(0);
                } else {
                    count += 10;
                    pBar.setValue(getProgress());
                }
            }

            int getProgress() {
                return count;
            }
        };
        timer.schedule(tt, 300, 300);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(loading.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) throws IOException {
        ChatGUI cg = new ChatGUI();
        JFileChooser fileChooser = new JFileChooser();
        int retval = fileChooser.showSaveDialog(cg);
        if (retval == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (file == null) {
            }
//                if (file.isDirectory())
////                 
//                }
        }
    }
}
