import components.map.Map;
import components.map.Map1L;
import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber1L;

/**
 * Simplified representation of a mechanical hard disk drive, with data
 * represented by a Map of NaturalNumber keys and Byte values.
 */
public class HardDrive1 extends HardDriveSecondary {
    /**
     * Total number of sectors on the drive.
     */
    private NaturalNumber sectors;

    /**
     * Map of sector indices to their stored byte values.
     */
    private Map<NaturalNumber, Byte> rep;

    /**
     * Create initial representation.
     */
    private void createNewRep() {
        this.rep = new Map1L<NaturalNumber, Byte>();
        this.sectors = new NaturalNumber1L();
    }

    /**
     * Constructor with user-set sector count.
     *
     * @param sec
     *            the number of sectors in {@code this}
     * @requires sec is not null
     */
    public HardDrive1(NaturalNumber sec) {
        assert sec != null : "Violation of: sec is not null";
        this.createNewRep();
        this.sectors.transferFrom(sec);
    }

    /**
     * Private no-argument constructor for {@code newInstance}.
     */
    private HardDrive1() {
        this.createNewRep();
    }

    /**
     * Kernel methods.
     */

    /**
     * Reads the byte stored at the given sector.
     *
     * @param sec
     *            the sector index to read from
     * @return the byte at {@code sec}
     * @requires sec is in domain(this.sectorData)
     */
    @Override
    public byte readData(NaturalNumber sec) {
        assert sec != null : "Violation of: sec is not null";
        byte data = -1;
        if (this.rep.hasKey(sec)) {
            data = this.rep.value(sec);
        }
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
    @Override
    public void writeData(NaturalNumber sec, byte data) {
        assert sec != null : "Violation of: sec is not null";
        assert sec.compareTo(
                this.sectors) <= 0 : "Violation of: sec < this.sectors";
        if (data < 0) {
            if (this.rep.hasKey(sec)) {
                this.rep.remove(sec);
            }
        } else if (this.rep.hasKey(sec)) {
            this.rep.replaceValue(sec, data);
        } else {
            NaturalNumber secCopy = sec.newInstance();
            secCopy.copyFrom(sec);
            this.rep.add(secCopy, data);
        }
    }

    /**
     * Returns the total number of sectors on the drive.
     *
     * @return the sector count
     */
    @Override
    public NaturalNumber sectorCount() {
        NaturalNumber count = this.sectors.newInstance();
        count.copyFrom(this.sectors);
        return count;
    }

    /**
     * Secondary methods.
     */

    /**
     * The standard methods.
     */

    @Override
    public final HardDrive newInstance() {
        try {
            return this.getClass().getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public final void clear() {
        this.createNewRep();
    }

    /**
     *
     * @param source
     */
    @Override
    public final void transferFrom(HardDrive source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof HardDrive1 : "Violation of: source is of dynamic type HardDrive1";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case.
         */
        HardDrive1 localSource = (HardDrive1) source;
        this.rep = localSource.rep;
        this.sectors = localSource.sectors;
        localSource.createNewRep();

    }

}
