package buoi6._19_BinaryDump;

import buoi6._20_RunLength.RunLength;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BinaryDump {
    public static void main(String[] args) throws FileNotFoundException {
        String fileOrigin = "src/buoi6/files/origin.txt";
        String fileCompressed = "src/buoi6/files/compressed.txt";
        String fileExpanded = "src/buoi6/files/expanded.txt";

        RunLength.compress(fileOrigin, fileCompressed);
        RunLength.expand(fileCompressed, fileExpanded);

        File file1 = new File(fileOrigin);
        File file2 = new File(fileExpanded);
        Scanner sc1 = new Scanner(file1);
        Scanner sc2 = new Scanner(file2);

        while (sc1.hasNextLine() && sc2.hasNextLine()) {
            String line1 = sc1.nextLine();
            String line2 = sc2.nextLine();

            if (line1.equals(line2)) {
                System.out.println(line1);
            }
        }

        sc1.close();
        sc2.close();
    }
}
