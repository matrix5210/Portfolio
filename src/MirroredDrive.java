import components.naturalnumber.NaturalNumber;

/**
 * RAID-1 mirrored storage array using two {@code HardDrive} components in its
 * representation to provide data redundancy and automatic sector recovery. You
 * can consider this another proof of concept.
 *
 * RAID-1 basically takes data from one hard drive and mirrors it onto another;
 * if one drive dies, at least the other one is still up. A very common backup
 * method in storage solutions.
 *
 * @author Petros Yohannes
 */
public class MirroredDrive {

    /**
     * Primary storage drive.
     */
    private HardDrive primary;

    /**
     * Backup mirror drive.
     */
    private HardDrive mirror;

    /**
     * Constructs a mirrored drive pair with the given number of sectors.
     *
     * @param sectors
     *            the number of sectors for each drive
     * @requires sectors is not null
     */
    public MirroredDrive(NaturalNumber sectors) {
        assert sectors != null : "Violation of: sectors is not null";
        NaturalNumber primaryCopy = sectors.newInstance();
        primaryCopy.copyFrom(sectors);
        NaturalNumber mirrorCopy = sectors.newInstance();
        mirrorCopy.copyFrom(sectors);

        this.primary = new HardDrive1(primaryCopy);
        this.mirror = new HardDrive1(mirrorCopy);
    }

    /**
     * Writes a byte to the given sector on both drives.
     *
     * @param sector
     *            the target sector index
     * @param data
     *            the byte of data to write
     * @updates this
     * @requires 0 <= sector < this.primary.sectorCount()
     */
    public void writeMirroredData(NaturalNumber sector, byte data) {
        assert sector != null : "Violation of: sector is not null";
        this.primary.writeData(sector, data);
        this.mirror.writeData(sector, data);
    }

    /**
     * Reads a byte from the primary drive, falling back to the mirror drive if
     * the primary sector is empty.
     *
     * @param sector
     *            the target sector index
     * @return the byte stored at {@code sector}
     * @requires 0 <= sector < this.primary.sectorCount()
     */
    public byte readMirroredData(NaturalNumber sector) {
        assert sector != null : "Violation of: sector is not null";
        byte value = this.primary.readData(sector);
        if (value < 0) {
            value = this.mirror.readData(sector);
        }
        return value;
    }

}
