/**
 * {@code HardDriveKernel} enhanced with secondary methods.
 *
 * @author Petros Yohannes
 */
public interface HardDrive extends HardDriveKernel {
    /**
     * Returns the total storage capacity in bytes.
     *
     * @return total capacity in bytes
     */
    byte storageCapacity();

    /**
     * Erases all stored data on the hard drive.
     *
     * @updates {@code this.sectorData}
     */
    void format();

}
