package com.ca.datamasker.custom;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HashCPFTest {

    private HashCPF hasher;

    @Before
    public void setUp() throws Exception {
        hasher = new HashCPF();
    }

    @Test
    public void testHashedCPFIsValid() {
        final String validCPF = new MockCPFFactory().getACPF();
        final String hashedCPF = hasher.hashCPF(validCPF);
        assertTrue("Hashing a valid CPF should generate a valid CPF too", CPFValidator.isValid(hashedCPF));
    }

    @Test
    public void testHashedCPFWithMaskIsValid() {
        final String validCPF = new MockCPFFactory(FormatMask.WITHMASK, CPFSize.REGULAR).getACPF();
        final String hashedCPF = hasher.hashCPF(validCPF);
        assertTrue("Hashing a valid CPF should generate a valid CPF too", CPFValidator.isValid(hashedCPF.replaceAll("\\D+","")));
    }

    @Test
    public void testHasherMaintainIntegry() {
        final String validCPF = new MockCPFFactory().getACPF();
        final String hashedCPF = hasher.hashCPF(validCPF);
        final String hashedAgainCPF = hasher.hashCPF(validCPF);
        assertEquals("Hashing value twice should return same hashed value", hashedCPF, hashedAgainCPF);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidSmallLength() {
        final String validCPF = new MockCPFFactory(FormatMask.NOTMASK, CPFSize.SMALLER).getACPF();
        hasher.hashCPF(validCPF);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidLargeLength() {
        final String validCPF = new MockCPFFactory(FormatMask.NOTMASK, CPFSize.LARGER).getACPF();
        hasher.hashCPF(validCPF);
    }
}