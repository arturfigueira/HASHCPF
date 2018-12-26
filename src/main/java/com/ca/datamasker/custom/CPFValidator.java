package com.ca.datamasker.custom;

public class CPFValidator {

    private final static int CPF_LENGTH = 11;

    private CPFValidator(){}

    /**
     * Check if a given {@param cpf} is valid according with
     * Brazilian specifications
     * @param cpf a CPF with its check digit. Only numbers allowed
     * @return True if its considered valid, or false otherwise
     */
    public static boolean isValid(final String cpf){
        boolean isValid = false;
        if(cpf != null && !cpf.isEmpty() && cpf.length() == CPF_LENGTH && !hasSequences(cpf)){
            final CPF cpf1 = new CPF(cpf.substring(0, cpf.length() - 2));
            final String checkDigit = cpf.substring(cpf.length() - 2);
            isValid = cpf1.getCheckDigit().equals(checkDigit);
        }
        return isValid;
    }

    /**
     * Check if the given CPF, with check digit, has a sequence of repeated numbers,
     * ex: 11111111111 should be considered invalid
     * @param cpf
     * @return True if it has sequences or false otherwise
     */
    private static boolean hasSequences(final String cpf){
        return cpf.matches("^(\\d)\\1{10}$");
    }
}
