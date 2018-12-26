package com.ca.datamasker.custom;

import org.junit.Test;

import java.util.Collections;
import java.util.Random;

import static org.junit.Assert.*;

public class CPFValidatorTest {

    @Test
    public void testValidCPF() {
        assertTrue(CPFValidator.isValid("18327516000"));
    }

    @Test
    public void testInvalidCheckDigitCPF() {
        assertFalse("This checkDigits are invalid, according to the CPF algorithm", CPFValidator.isValid("12739783099"));
    }

    @Test
    public void testInvalidLageLengthCPF() {
        final MockCPFFactory cpfFactory = new MockCPFFactory(FormatMask.NOTMASK, CPFSize.LARGER);
        assertFalse("This CPF is larger than the allowed", CPFValidator.isValid(cpfFactory.getACPF()));
    }

    @Test
    public void testInvalidSmallLengthCPF() {
        final MockCPFFactory cpfFactory = new MockCPFFactory(FormatMask.NOTMASK, CPFSize.SMALLER);
        assertFalse("This CPF is smaller than the allowed", CPFValidator.isValid(cpfFactory.getACPF()));
    }

    @Test
    public void testSequenceCPF() {
        final int i = new Random().nextInt(10);
        final String sequence = String.join("", Collections.nCopies(11, Integer.toString(i)));

        assertFalse("Sequence of numbers, like 11111...is invalid", CPFValidator.isValid(sequence));
    }

    @Test
    public void testEmptyCPF() {
        assertFalse("Empty String should always give a false result", CPFValidator.isValid("   "));
    }

    @Test
    public void testNullCPF() {
        assertFalse("NULL should always give a false result", CPFValidator.isValid(null));
    }
}