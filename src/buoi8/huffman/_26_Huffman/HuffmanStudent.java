package buoi8.huffman._26_Huffman;

import buoi8.huffman._27_Huffman_UNICODE.Huffman;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class HuffmanStudent {
    public static void main(String[] args) throws FileNotFoundException {
        String basePath = "src/buoi8/huffman/";
        String inputFileName = basePath + "student.txt"; // File text gốc
        String outputFileName = basePath + "output.txt";

        System.setIn(new FileInputStream(inputFileName));
        System.setOut(new PrintStream(outputFileName));

        Huffman.compress();

        BinaryStdIn.close();
        BinaryStdOut.close();
    }
}
