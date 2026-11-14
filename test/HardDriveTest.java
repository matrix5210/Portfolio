import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber1L;

/**
 * JUnit test fixture for {@code HardDriveSecondary}'s secondary methods.
 *
 * @author Petros Yohannes
 */
public class HardDriveTest {

    /**
     * Common sector count for standard tests.
     */
    private static final int DEFAULT_SECTORS = 5;

    /**
     * Sample byte values for testing secondary methods.
     */
    private static final byte SAMPLE_BYTE = 10;

    /**
     * Sample byte value 2.
     */
    private static final byte SECOND_BYTE = 25;

    /**
     * Sample byte value 3.
     */
    private static final byte THIRD_BYTE = 99;

    /**
     * Helper method to create a {@code HardDrive} with the given sector count
     * and optional initial byte values at specified indices.
     *
     * @param sectorCount
     *            total number of sectors on the drive
     * @param entries
     *            alternating pairs of (sectorIndex, byteValue)
     * @return a configured {@code HardDrive1} instance
     */
    private HardDrive createDrive(int sectorCount, int... entries) {
        final int pairStep = 2;
        NaturalNumber sectors = new NaturalNumber1L(sectorCount);
        HardDrive drive = new HardDrive1(sectors);
        for (int i = 0; i < entries.length; i += pairStep) {
            NaturalNumber sec = new NaturalNumber1L(entries[i]);
            drive.writeData(sec, (byte) entries[i + 1]);
        }
        return drive;
    }

    /**
     * Test storageCapacity on a 0-sector drive and verify state is unchanged.
     */
    @Test
    public final void testStorageCapacityZero() {
        HardDrive drive = this.createDrive(0);
        HardDrive expectedDrive = this.createDrive(0);

        NaturalNumber capacity = drive.storageCapacity();

        assertEquals(new NaturalNumber1L(0), capacity);
        assertEquals(expectedDrive, drive);
    }

    /**
     * Test storageCapacity on a multi-sector drive.
     */
    @Test
    public final void testStorageCapacityMultiSector() {
        final int secThree = 3;

        HardDrive drive = this.createDrive(DEFAULT_SECTORS, 0, SAMPLE_BYTE,
                secThree, SECOND_BYTE);
        HardDrive expectedDrive = this.createDrive(DEFAULT_SECTORS, 0,
                SAMPLE_BYTE, secThree, SECOND_BYTE);
        NaturalNumber expectedCapacity = new NaturalNumber1L();
        expectedCapacity.setFromString("5000000000");

        NaturalNumber capacity = drive.storageCapacity();

        assertEquals(expectedCapacity, capacity);
        assertEquals(expectedDrive, drive);
    }

    /**
     * Test format on a partially filled drive erases all sectors while keeping
     * sector count intact.
     */
    @Test
    public final void testFormatPartiallyFilled() {
        final int secTwo = 2;
        final int secFour = 4;

        HardDrive drive = this.createDrive(DEFAULT_SECTORS, 0, SAMPLE_BYTE,
                secTwo, SECOND_BYTE, secFour, THIRD_BYTE);
        HardDrive expectedDrive = this.createDrive(DEFAULT_SECTORS);

        drive.format();

        assertEquals(expectedDrive, drive);
    }

    /**
     * Test toString on a partially used drive and verify state is unchanged.
     */
    @Test
    public final void testToStringPartiallyUsed() {
        final int count = 500;
        final int midSec = 250;
        final int lastSec = 499;

        HardDrive drive = this.createDrive(count, 0, SAMPLE_BYTE, midSec,
                SECOND_BYTE, lastSec, THIRD_BYTE);
        HardDrive expectedDrive = this.createDrive(count, 0, SAMPLE_BYTE,
                midSec, SECOND_BYTE, lastSec, THIRD_BYTE);
        String expectedString = "\n-----------------\n"
                + "Storage capacity: 500 GB (0.50 TB)\n"
                + "Used space: 3 GB (0.00 TB)\n-----------------\n";

        String actual = drive.toString();

        assertEquals(expectedString, actual);
        assertEquals(expectedDrive, drive);
    }

    /**
     * Test equals and hashCode when two drives are identical.
     */
    @Test
    public final void testEqualsTrue() {
        final int secFour = 4;

        HardDrive d1 = this.createDrive(DEFAULT_SECTORS, 1, SAMPLE_BYTE,
                secFour, SECOND_BYTE);
        HardDrive d2 = this.createDrive(DEFAULT_SECTORS, 1, SAMPLE_BYTE,
                secFour, SECOND_BYTE);

        assertTrue(d1.equals(d2));
        assertTrue(d2.equals(d1));
        assertEquals(d1.hashCode(), d2.hashCode());
    }

    /**
     * Test equals returns false when data differs, sector counts differ, or
     * compared to null.
     */
    @Test
    public final void testEqualsFalse() {
        final int otherCount = 6;
        final int secTwo = 2;

        HardDrive d1 = this.createDrive(DEFAULT_SECTORS, 0, SAMPLE_BYTE, secTwo,
                SECOND_BYTE);
        HardDrive dDiffData = this.createDrive(DEFAULT_SECTORS, 0, SAMPLE_BYTE,
                secTwo, THIRD_BYTE);
        HardDrive dDiffSize = this.createDrive(otherCount, 0, SAMPLE_BYTE,
                secTwo, SECOND_BYTE);

        assertFalse(d1.equals(dDiffData));
        assertFalse(d1.equals(dDiffSize));
        assertFalse(d1.equals(null));
    }

}
