package com.ca.datamasker.custom;

import javax.swing.text.MaskFormatter;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * class that represents a CPF
 */
public class CPF{
    private final String number;
    private final String checkDigit;

    /**
     * Creates a new CPF
     * @param number a Number without checkDigit. Format Mask is allowed
     * @param checkDigit the number checkDigit. WARNING: The constructor will not validate if the CD is valid
     */
    public CPF(final String number, final String checkDigit) {
        this.number = number;
        this.checkDigit = checkDigit;
    }

    /**
     * Creates a new CPF where the checkDigit will be automatically calculated by the constructor
     * @param number CPF number without checkDigit. Format Mask is allowed
     */
    public CPF(final String number){
        this.number = number;
        final String formatMaskRemoved = number.replaceAll("\\D+", "");
        this.checkDigit = this.calcCheckDigit(formatMaskRemoved);
    }

    public String getNumber() {
        return number;
    }

    public String getCheckDigit() {
        return checkDigit;
    }

    private String calcCheckDigit(final String number){
        final String[] split = number.split("(?!^)");
        final List<Integer> cpfColumns = Arrays.stream(split).map(s -> Integer.valueOf(s)).collect(Collectors.toList());

        final int firstCheckDigit = sumColumnsByWeight(cpfColumns);
        cpfColumns.add(firstCheckDigit);

        final int secondCheckDigit = sumColumnsByWeight(cpfColumns);
        return ""+firstCheckDigit+secondCheckDigit;
    }

    private int sumColumnsByWeight(final List<Integer> cpfColumns) {
        int sumColumns = 0;
        for(int i=0; i<cpfColumns.size(); i++){
            sumColumns+=(cpfColumns.size() + 1 - i) * cpfColumns.get(i);
        }
        return modular(sumColumns);
    }


    private int modular(final int number){
        final int modular = (number * 10) % 11;
        return (modular == 10) ? 0 : modular;
    }

    @Override
    public String toString() {
        return String.join("", getNumber(),getCheckDigit());
    }

    public String toString(final String formatMask) {
        String formattedCPF;
        try {
            final MaskFormatter maskFormatter = new MaskFormatter(formatMask);
            maskFormatter.setValueContainsLiteralCharacters(false);
            formattedCPF = maskFormatter.valueToString(this.toString());
        } catch (ParseException e) {
            formattedCPF = "";
        }
        return formattedCPF;
    }
}
