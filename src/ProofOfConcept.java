import components.map.Map;
import components.map.Map1L;
import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber1L;

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
        final int sectorIndex = 20;
        final int totalSectors = 10;
        final byte sampleByte = 10;

        NaturalNumber twenty = new NaturalNumber1L(sectorIndex);
        HardDrive1 hdd = new HardDrive1(new NaturalNumber1L(totalSectors));
        hdd.writeData(twenty, sampleByte);
        System.out.println(hdd.readData(twenty));
    }

}

/**
 *
 * HardDrive1's proof of concept sample class with some implementations.
 *
 */
class HardDrive1 {
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
    HardDrive1(NaturalNumber sec) {
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
