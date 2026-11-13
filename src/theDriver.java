import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber1L;;

public class theDriver {
    public static void main(String[] args) {
        NaturalNumber s = new NaturalNumber1L(256);
        HardDrive hdd = new HardDrive1(s);

        for (int i = 0; i < 256; i++) {
            hdd.writeData(new NaturalNumber1L(i),
                    (byte) ((10 * Math.random() + i) % 128));
        }

        System.out.println(hdd.readData(new NaturalNumber1L(35)));

        System.out.println(hdd.toString());
    }
}
