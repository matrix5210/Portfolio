import components.naturalnumber.NaturalNumber;

/**
 * Layered implementations of secondary methods for {@code HardDrive}.
 */
public abstract class HardDriveSecondary implements HardDrive {
    /**
     * Private members.
     */
    private static final int BYTES_PER_SECTOR = 1000000000;

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public final NaturalNumber storageCapacity() {
        NaturalNumber capacity = this.sectorCount().newInstance();
        capacity.copyFrom(this.sectorCount());
        NaturalNumber bytesPerSector = this.sectorCount().newInstance();
        bytesPerSector.setFromInt(BYTES_PER_SECTOR);

        capacity.multiply(bytesPerSector);
        return capacity;
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public final void format() {
        NaturalNumber index = this.sectorCount().newInstance();
        NaturalNumber targetIndex = (this.sectorCount().newInstance());
        targetIndex.add(this.sectorCount());

        while (index.compareTo(targetIndex) < 0) {
            this.writeData(index, (byte) -1);
            index.increment();
        }
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public String toString() {
        final double gbPerTb = 1000.0;

        NaturalNumber sectors = this.sectorCount();
        NaturalNumber usedSectors = sectors.newInstance();
        NaturalNumber index = sectors.newInstance();

        while (index.compareTo(sectors) < 0) {
            if (this.readData(index) >= 0) {
                usedSectors.increment();
            }
            index.increment();
        }

        double totalGb = Double.parseDouble(this.storageCapacity().toString())
                / BYTES_PER_SECTOR;
        double usedGb = Double.parseDouble(usedSectors.toString());

        return String.format(
                "\n-----------------\nStorage capacity: %.0f GB (%.2f TB)\n"
                        + "Used space: %.0f GB (%.2f TB)\n-----------------\n",
                totalGb, totalGb / gbPerTb, usedGb, usedGb / gbPerTb);
    }

}
