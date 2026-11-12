import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber1L;

/**
 * Layered implementations of secondary methods for {@code HardDrive}.
 */
public abstract class HardDriveSecondary implements HardDrive {
    /**
     * Private members.
     */
    private static final int BYTES_PER_SECTOR = 512;

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public byte storageCapacity() {
        byte capacity = (byte) (this.sectorCount().toInt() * BYTES_PER_SECTOR);
        return capacity;
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public void format() {
        NaturalNumber index = new NaturalNumber1L(0);
        NaturalNumber targetIndex = new NaturalNumber1L(this.sectorCount());
        while (index.compareTo(targetIndex) < 0) {
            this.writeData(index, (byte) 0);
            index.increment();
        }
    }

}
