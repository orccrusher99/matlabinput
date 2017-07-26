/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matlabinput;

import com.jmatio.io.MatFileReader;
import com.jmatio.types.MLArray;
import com.jmatio.types.MLDouble;
import com.jmatio.types.MLStructure;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author justinjlee99
 */
public class Matlabinput {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            MatFileReader mfr = new MatFileReader("L1C1SPOT-300um1sec100percent-02-15-22PM.mat");
            Map<String, MLArray> mlArrayRetrived = mfr.getContent();
            MLStructure x = (MLStructure) mfr.getContent().get("x");
            MLDouble voltMAT = (MLDouble) x.getField("voltage");
            double[][] temp = voltMAT.getArray();
            double[] voltages = temp[0];

            double[] xs = new double[voltages.length];
            double[] deltas = new double[voltages.length];
            ArrayList<Integer> sigs = new ArrayList();
            for (int i = 0; i < voltages.length; i++) {
                xs[i] = i;
                if (i != voltages.length - 1) {
                    double d = voltages[i + 1] - voltages[i];
                    deltas[i] = d;
                    if(d >= .001) {
                        sigs.add(i);
                    }
                }
            }
            for (Integer sig : sigs) {
                System.out.println(sig);
            }
            
            showGraph("Time", xs, "Voltage", voltages);
            showGraph("Time", xs, "Deltas", deltas);
            
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IOException");
        }

    }

    public static void showGraph(String XString, double[] x, String YString, double[] y) {
        try {
            XYLineChart_AWT chart = new XYLineChart_AWT(XString, x, YString, y);
            chart.setSize(new java.awt.Dimension(1920, 1080));
            RefineryUtilities.centerFrameOnScreen(chart);
            chart.setVisible(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
}
