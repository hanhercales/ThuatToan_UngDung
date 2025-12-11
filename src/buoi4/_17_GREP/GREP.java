package buoi4._17_GREP;

import edu.princeton.cs.algs4.NFA;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.io.*;
public class GREP {

    // do not instantiate
    private GREP() { }

    public static void main(String[] args) throws IOException { 
        System.setIn(new FileInputStream(new File("src/buoi4/tinyL.txt")));
//        String regexp = "(.*" + args[0] + ".*)";
        String regexp = "(.*" + "(A*B|AC)D" + ".*)";
        NFA nfa = new NFA(regexp);
        while (StdIn.hasNextLine()) {
            String line = StdIn.readLine();
            if (nfa.recognizes(line)) {
                StdOut.println(line);
            }
        }
    } 
}