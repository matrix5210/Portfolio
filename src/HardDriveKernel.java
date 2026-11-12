import components.naturalnumber.NaturalNumber;
import components.standard.Standard;

/**
 * Simplified representation of a mechanical hard disk drive, with data
 * represented by bytes at each sector.
 *
 *
 * @author Petros Yohannes
 */
public interface HardDriveKernel extends Standard<HardDriveKernel> {
    /**
     * Reads the byte at the given sector of {@code this}.
     *
     * @param sector
     *            the target sector from which data will be read
     * @return the byte of data at the given sector
     */
    byte readData(NaturalNumber sector);

    /**
     * Writes data to the given sector of {@code this}.
     *
     * @param sector
     *            the target sector to which data will be written
     * @param byteData
     *            the byte of data to be written to the sector
     */
    void writeData(NaturalNumber sector, byte byteData);

    /**
     * Gives the hard drive's maximum amount of sectors.
     *
     * @return {@code this.sectors}
     */
    NaturalNumber sectorCount();
}
