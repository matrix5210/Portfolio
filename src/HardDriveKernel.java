import components.naturalnumber.NaturalNumber;
import components.standard.Standard;

/**
 * Kernel methods for {@code HardDrive}.
 *
 * @author Petros Yohannes
 */
public interface HardDriveKernel extends Standard<HardDrive> {
    /**
     * Reads the byte at the given sector of {@code this}.
     *
     * @param sector
     *            the target sector from which data will be read
     * @return the byte of data at the given sector
     * @requires 0 <= sector < this.sectorCount()
     * @ensures readData = [the byte stored at sector in this]
     */
    byte readData(NaturalNumber sector);

    /**
     * Writes data to the given sector of {@code this}.
     *
     * @param sector
     *            the target sector to which data will be written
     * @param byteData
     *            the byte of data to be written to the sector
     * @updates this
     * @requires 0 <= sector < this.sectorCount()
     * @ensures [sector in this is updated to hold byteData]
     */
    void writeData(NaturalNumber sector, byte byteData);

    /**
     * Gives the hard drive's maximum amount of sectors.
     *
     * @return the maximum number of sectors of {@code this}
     * @ensures sectorCount = [the maximum number of sectors of this]
     */
    NaturalNumber sectorCount();
}
