import components.map.Map;
import components.map.Map1L;
import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 *
 * ProofOfConcept.
 */
public final class ProofOfConcept {
    /**
     * No-argument private constructor to prevent instantiation.
     */
    private ProofOfConcept() {
    }

    /**
     * Main method to test basic HardDrive functionality.
     *
     * @param args
     *            command-line arguments
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();
        final int totalSectors1 = 100;
        final int totalSectors2 = 1000;
        final int totalSectors3 = 65536;

        final byte sampleByte1 = 10;
        final byte sampleByte2 = 30;
        final byte sampleByte3 = 120;

        HardDriveProof hdd1 = new HardDriveProof(
                new NaturalNumber1L(totalSectors1));
        HardDriveProof hdd2 = new HardDriveProof(
                new NaturalNumber1L(totalSectors2));
        HardDriveProof hdd3 = new HardDriveProof(
                new NaturalNumber1L(totalSectors3));

        final int ten = 10;
        final int twenty = 20;
        final int thirty = 30;
        hdd1.writeData(new NaturalNumber1L(ten), sampleByte1);
        hdd1.writeData(new NaturalNumber1L(twenty), sampleByte2);
        out.println(hdd1.readData(new NaturalNumber1L(ten)));
        out.println(hdd1.readData(new NaturalNumber1L(twenty)));

        hdd2.writeData(new NaturalNumber1L(twenty), sampleByte2);
        out.println(hdd2.readData(new NaturalNumber1L(twenty)));

        hdd3.writeData(new NaturalNumber1L(thirty), sampleByte3);
        out.println(hdd3.readData(new NaturalNumber1L(thirty)));

        out.close();
    }

}

/**
 *
 * HardDrive1's proof of concept sample class with some implementations.
 *
 */
class HardDriveProof {
    /**
     * Total number of sectors on the drive.
     */
    private NaturalNumber sectors;

    /**
     * Map of sector indices to their stored byte values.
     */
    private Map<NaturalNumber, Byte> sectorData;

    /**
     * Constructor with initial sector count.
     *
     * @param sec
     *            the total number of sectors
     * @requires sec is not null
     */
    HardDriveProof(NaturalNumber sec) {
        this.sectors = sec.newInstance();
        this.sectors.copyFrom(sec);
        this.sectorData = new Map1L<NaturalNumber, Byte>();
    }

    /**
     * Reads the byte stored at the given sector.
     *
     * @param sec
     *            the sector index to read from
     * @return the byte at {@code sec}
     * @requires sec is in domain(this.sectorData)
     */
    public byte readData(NaturalNumber sec) {
        byte data = this.sectorData.value(sec);
        return data;
    }

    /**
     * Writes a byte of data to the given sector.
     *
     * @param sec
     *            the sector index to write to
     * @param data
     *            the byte value to store
     * @requires sec is not null
     */
    public void writeData(NaturalNumber sec, byte data) {
        if (this.sectorData.hasKey(sec)) {
            this.sectorData.replaceValue(sec, data);
        } else {
            this.sectorData.add(sec, data);
        }

    }

    /**
     * Returns the total number of sectors on the drive.
     *
     * @return the sector count
     */
    public NaturalNumber sectorCount() {
        NaturalNumber count = this.sectors.newInstance();
        count.copyFrom(this.sectors);
        return count;
    }

    /**
     * Returns the total storage capacity in bytes.
     *
     * @return total capacity in bytes
     */
    public NaturalNumber storageCapacity() {
        final int bytesPerSector = 512;
        NaturalNumber capacity = this.sectorCount();
        capacity.multiply(new NaturalNumber1L(bytesPerSector));
        return capacity;
    }

    /**
     * Erases all stored data on the hard drive.
     */
    public void format() {
        this.sectorData.clear();
    }

}
