import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Sample client program demonstrating basic operations on a {@code HardDrive}
 * component, adding and removing certain amounts of storage until formatting
 * back to 0 GB.
 *
 * @author Petros Yohannes
 */
public final class HardDriveDemo {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private HardDriveDemo() {
    }

    /**
     * Writes sequential bytes to sectors in the range {@code [start, end)}.
     *
     * @param drive
     *            the {@code HardDrive} to write to
     * @param start
     *            starting sector index (inclusive)
     * @param end
     *            ending sector index (exclusive)
     * @updates drive
     */
    private static void writeRange(HardDrive drive, int start, int end) {
        final int byteModulo = 128;
        NaturalNumber index = new NaturalNumber1L(start);
        for (int i = start; i < end; i++) {
            drive.writeData(index, (byte) (i % byteModulo));
            index.increment();
        }
    }

    /**
     * Main method demonstrating {@code HardDrive} usage across different
     * storage levels until a format.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        final int stepOneGb = 128;
        final int stepTwoGb = 256;
        final int totalGb = 512;
        final int removeStart = 500;

        SimpleWriter out = new SimpleWriter1L();

        NaturalNumber capacityInSectors = new NaturalNumber1L(totalGb);
        HardDrive drive = new HardDrive1(capacityInSectors);

        NaturalNumber secZero = new NaturalNumber1L(0);
        NaturalNumber secMid = new NaturalNumber1L(stepTwoGb);
        NaturalNumber secLast = new NaturalNumber1L(totalGb - 1);

        // 0 GB
        out.println("1. Initial state (0 GB):");
        out.println(drive.toString());

        // 128 GB
        writeRange(drive, 0, stepOneGb);
        out.println("2. After writing 128 GB:");
        out.println(
                "Byte at sector " + secZero + ": " + drive.readData(secZero));
        out.println(drive.toString());

        // 256 GB
        writeRange(drive, stepOneGb, stepTwoGb);
        out.println("3. After expanding to 256 GB:");
        out.println("Byte at sector " + (stepTwoGb - 1) + ": "
                + drive.readData(new NaturalNumber1L(stepTwoGb - 1)));
        out.println(drive.toString());

        // 512 GB
        writeRange(drive, stepTwoGb, totalGb);
        out.println("4. After filling to 512 GB:");
        out.println("Byte at sector " + secMid + ": " + drive.readData(secMid));
        out.println(
                "Byte at sector " + secLast + ": " + drive.readData(secLast));
        out.println(drive.toString());

        // Remove 12 GB (sectors 500..511 -> 500 GB remaining)
        NaturalNumber eraseIndex = new NaturalNumber1L(removeStart);
        for (int i = removeStart; i < totalGb; i++) {
            drive.writeData(eraseIndex, (byte) -1);
            eraseIndex.increment();
        }
        out.println("5. After removing 12 GB (500 GB remaining):");
        out.println("Byte at sector " + secLast + " (erased): "
                + drive.readData(secLast));
        out.println(drive.toString());

        // Format back to 0 GB
        drive.format();
        out.println("6. After formatting back to 0 GB:");
        out.println(
                "Byte at sector " + secZero + ": " + drive.readData(secZero));
        out.println("Byte at sector " + secMid + ": " + drive.readData(secMid));
        out.println(drive.toString());

        out.close();
    }

}
