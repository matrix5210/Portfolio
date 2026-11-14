import components.naturalnumber.NaturalNumber;

/**
 * Layered implementations of secondary methods for {@code HardDrive}.
 *
 * @author Petros Yohannes
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

    /**
     * toString() represents the hard drive's storage and capacity the way it is
     * intended; it upscales the single byte stored at each sector into a full
     * gigabyte (though we're not actually storing 1 GB per sector) just for the
     * display to fall better into line with modern hard drives.
     */
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
                "\n-----------------\nStorage capacity: %.0f GB (%.3f TB)\n"
                        + "Used space: %.0f GB (%.3f TB)\n-----------------\n",
                totalGb, totalGb / gbPerTb, usedGb, usedGb / gbPerTb);
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public boolean equals(Object obj) {
        boolean areEqual = false;
        if (obj instanceof HardDrive) {
            HardDrive objHardDrive = (HardDrive) obj;

            if (objHardDrive.sectorCount().compareTo(this.sectorCount()) == 0) {
                NaturalNumber index = this.sectorCount().newInstance();
                // Assume all data is equal until proven otherwise
                boolean allDataEqual = true;

                while (index.compareTo(this.sectorCount()) < 0
                        && allDataEqual) {
                    if (this.readData(index) != objHardDrive.readData(index)) {
                        allDataEqual = false;
                    }
                    index.increment();
                }
                if (allDataEqual) {
                    areEqual = true;
                }
            }
        }

        return areEqual;
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public int hashCode() {
        final int prime = 31;
        final int sampleCount = 2;
        int result = this.sectorCount().hashCode();
        NaturalNumber index = this.sectorCount().newInstance();
        NaturalNumber limit = this.sectorCount().newInstance();
        limit.setFromInt(sampleCount);
        if (this.sectorCount().compareTo(limit) < 0) {
            limit.copyFrom(this.sectorCount());
        }
        while (index.compareTo(limit) < 0) {
            result = prime * result + this.readData(index);
            index.increment();
        }
        return result;
    }

}
