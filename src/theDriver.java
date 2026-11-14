import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber1L;;

/**
 * theDriver is just my personal testing sandbox for the HardDrive1 class.
 * Please don't judge this file as part of my grade for the Portfolio project.
 * 
 * @author Petros Yohannes
 */
public class theDriver {
    public static void main(String[] args) {
        NaturalNumber s = new NaturalNumber1L(256);
        HardDrive hdd = new HardDrive1(s);

        for (int i = 0; i < 256; i++) {
            //hdd.writeData(new NaturalNumber1L(i),
            //(byte) ((10 * Math.random() + i) % 128));
            hdd.writeData(new NaturalNumber1L(i), (byte) 1);
        }

        System.out.println(hdd.readData(new NaturalNumber1L(35)));

        System.out.println(hdd.toString());
        HardDrive anotherHDD = hdd.newInstance();

        for (int i = 0; i < 256; i++) {
            //anotherHDD.writeData(new NaturalNumber1L(i),
            //(byte) ((10 * Math.random() + i) % 128));
            anotherHDD.writeData(new NaturalNumber1L(i), (byte) 1);
        }

        System.out.println(hdd.equals(anotherHDD));
    }
}
