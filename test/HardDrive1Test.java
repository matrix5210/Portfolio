import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber1L;

/**
 * JUnit test fixture for {@code HardDrive1}'s constructor and kernel methods.
 *
 * @author Petros Yohannes
 */
public class HardDrive1Test {

    /**
     * Common sector count for standard tests.
     */
    private static final int DEFAULT_SECTORS = 4;

    /**
     * Sample byte value for testing reads and writes.
     */
    private static final byte SAMPLE_BYTE = 42;
    /**
     * Updated byte value for testing reads and writes.
     */
    private static final byte UPDATED_BYTE = 99;
    /**
     * Maximum byte value for testing reads and writes.
     */
    private static final byte MAX_BYTE = 127;

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
     * Test constructor with zero sectors.
     */
    @Test
    public final void testConstructorZeroSectors() {
        NaturalNumber sectors = new NaturalNumber1L(0);
        HardDrive drive = new HardDrive1(sectors);
        NaturalNumber expectedSectors = new NaturalNumber1L(0);

        assertEquals(expectedSectors, drive.sectorCount());
    }

    /**
     * Test constructor with multiple sectors and verify all start empty.
     */
    @Test
    public final void testConstructorMultipleSectors() {
        NaturalNumber sectors = new NaturalNumber1L(DEFAULT_SECTORS);
        HardDrive drive = new HardDrive1(sectors);
        NaturalNumber expectedSectors = new NaturalNumber1L(DEFAULT_SECTORS);

        assertEquals(expectedSectors, drive.sectorCount());
        for (int i = 0; i < DEFAULT_SECTORS; i++) {
            NaturalNumber index = new NaturalNumber1L(i);
            assertEquals((byte) -1, drive.readData(index));
        }
    }

    /**
     * Test readData on an empty sector and verify drive and argument are
     * unchanged.
     */
    @Test
    public final void testReadDataEmptySector() {
        final int targetSec = 2;
        HardDrive drive = this.createDrive(DEFAULT_SECTORS);
        HardDrive expectedDrive = this.createDrive(DEFAULT_SECTORS);
        NaturalNumber sec = new NaturalNumber1L(targetSec);
        NaturalNumber expectedSec = new NaturalNumber1L(targetSec);

        byte result = drive.readData(sec);

        assertEquals((byte) -1, result);
        assertEquals(expectedSec, sec);
        assertEquals(expectedDrive, drive);
    }

    /**
     * Test readData at boundary sectors (0 and sectors - 1).
     */
    @Test
    public final void testReadDataBoundaries() {
        final int count = 10;
        final int lastIdx = 9;

        HardDrive drive = this.createDrive(count, 0, SAMPLE_BYTE, lastIdx,
                MAX_BYTE);
        HardDrive expectedDrive = this.createDrive(count, 0, SAMPLE_BYTE,
                lastIdx, MAX_BYTE);

        NaturalNumber zero = new NaturalNumber1L(0);
        NaturalNumber last = new NaturalNumber1L(lastIdx);

        assertEquals(SAMPLE_BYTE, drive.readData(zero));
        assertEquals(MAX_BYTE, drive.readData(last));
        assertEquals(expectedDrive, drive);
    }

    /**
     * Test writeData adding a new entry and overwriting an existing entry.
     */
    @Test
    public final void testWriteDataAddAndOverwrite() {
        final int targetSec = 2;

        HardDrive drive = this.createDrive(DEFAULT_SECTORS);
        NaturalNumber sec = new NaturalNumber1L(targetSec);
        NaturalNumber expectedSec = new NaturalNumber1L(targetSec);

        drive.writeData(sec, SAMPLE_BYTE);
        assertEquals(this.createDrive(DEFAULT_SECTORS, targetSec, SAMPLE_BYTE),
                drive);

        drive.writeData(sec, UPDATED_BYTE);
        assertEquals(expectedSec, sec);
        assertEquals(this.createDrive(DEFAULT_SECTORS, targetSec, UPDATED_BYTE),
                drive);
    }

    /**
     * Test writeData with negative byte removes existing data from sector.
     */
    @Test
    public final void testWriteDataNegativeRemovesData() {
        final int keptSec = 3;
        HardDrive drive = this.createDrive(DEFAULT_SECTORS, 0, SAMPLE_BYTE,
                keptSec, UPDATED_BYTE);
        HardDrive expectedDrive = this.createDrive(DEFAULT_SECTORS, keptSec,
                UPDATED_BYTE);
        NaturalNumber sec = new NaturalNumber1L(0);

        drive.writeData(sec, (byte) -1);

        assertEquals((byte) -1, drive.readData(sec));
        assertEquals(expectedDrive, drive);
    }

    /**
     * Test newInstance returns an empty drive with matching type and sectors.
     */
    @Test
    public final void testNewInstance() {
        final int lastSec = 3;

        HardDrive drive = this.createDrive(DEFAULT_SECTORS, 0, SAMPLE_BYTE,
                lastSec, UPDATED_BYTE);
        HardDrive expectedOriginal = this.createDrive(DEFAULT_SECTORS, 0,
                SAMPLE_BYTE, lastSec, UPDATED_BYTE);
        HardDrive expectedFresh = this.createDrive(DEFAULT_SECTORS);

        HardDrive fresh = drive.newInstance();

        assertTrue(fresh instanceof HardDrive1);
        assertEquals(expectedFresh, fresh);
        assertEquals(expectedOriginal, drive);
    }

    /**
     * Test clear and transferFrom standard methods.
     */
    @Test
    public final void testClearAndTransferFrom() {
        final int sourceCount = 6;
        final int secFive = 5;

        HardDrive source = this.createDrive(sourceCount, 1, SAMPLE_BYTE,
                secFive, UPDATED_BYTE);
        HardDrive expectedDest = this.createDrive(sourceCount, 1, SAMPLE_BYTE,
                secFive, UPDATED_BYTE);
        HardDrive dest = this.createDrive(DEFAULT_SECTORS, 0, MAX_BYTE);

        dest.transferFrom(source);
        assertEquals(expectedDest, dest);
        assertEquals(new NaturalNumber1L(0), source.sectorCount());

        dest.clear();
        assertEquals(new NaturalNumber1L(0), dest.sectorCount());
    }

}
