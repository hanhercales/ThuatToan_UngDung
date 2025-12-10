package buoi3._10_11_TSTForRate;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Student {
    private final String name;
    private final TST<Double> bangDiem;

    public Student(String name) {
        this.name = name;
        bangDiem = new TST<>();
    }

    public Iterable<Double> getBangDiem() {
        Iterable<String> iterable = bangDiem.keys();
        List<Double> list = new ArrayList<>();
        for(String s: iterable){
            list.add(bangDiem.get(s));
        }
        return list;
    }

    public void putBangDiem(String key, Double value) {
        bangDiem.put(key, value);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(name);
        for(String s: bangDiem.keys()){
            double diem = bangDiem.get(s);
            stringBuilder.append(" ").append(s).append(" ").append(diem);
        }

        return stringBuilder.toString();
    }

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/buoi3/student.csv");
        Scanner sc = new Scanner(file);
        TST<Student> tst = new TST<>();

        while (sc.hasNextLine()) {
            String[] line = sc.nextLine().split(",");
            String msv = line[0];
            String name = line[1];
            Student student = new Student(name);
            tst.put(msv, student);
            student.putBangDiem("Toán", Double.parseDouble(line[2]));
            student.putBangDiem("Văn", Double.parseDouble(line[3]));
            student.putBangDiem("Anh", Double.parseDouble(line[4]));
            student.putBangDiem("Tin", Double.parseDouble(line[5]));

        }

        for (String key : tst.keys()) {
            Student student = tst.get(key);
            System.out.println(student);
        }
    }
}
