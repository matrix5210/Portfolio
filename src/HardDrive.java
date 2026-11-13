import components.naturalnumber.NaturalNumber;

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
     * @ensures storageCapacity = this.sectorCount() * [bytes per sector]
     */
    NaturalNumber storageCapacity();

    /**
     * Erases all stored data on the hard drive.
     *
     * @updates {@code this}
     * @ensures [all sectors in this are marked as empty]
     */
    void format();

}
