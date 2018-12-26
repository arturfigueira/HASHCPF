package com.ca.datamasker.custom;

import com.grid_tools.products.datamasker.Datamasker;
import com.grid_tools.products.datamasker.IMaskFunction;
import com.grid_tools.products.datamasker.randfunctions;

public class HashCPF implements IMaskFunction {

    @Override
    public Object mask(Object... objects) {
        final String cpfToBeMasked = (String) objects[0];
        String maskedValue = cpfToBeMasked;
        if(cpfToBeMasked != null && !cpfToBeMasked.isEmpty()){

            try{
                maskedValue = hashCPF(cpfToBeMasked);
            }catch (IllegalArgumentException e){
                Datamasker.processOutputs(Datamasker.formatMessage("m0345-hasherr", new String[] { "HASH" }));
                Datamasker.processErrors(Datamasker.formatMessage("m0345-hasherr", new String[] { "HASH" }));
                Datamasker.processOutputs(Datamasker.formatMessage("m0155-DBValue", new String[] { cpfToBeMasked }));
                System.exit(1);
            }
        }

        return maskedValue;
    }

    protected String hashCPF(final String cpf){
        if(!CPFValidator.isValid(cpf)){
            throw new IllegalArgumentException("This CPF is invalid, for this reason It cant be Masked");
        }

        final String number = cpf.substring(0, cpf.length() - 2);
        final String hashedValue = randfunctions.formatHash(number);
        final CPF hashedCPF = new CPF(hashedValue);
        return hashedCPF.toString();
    }
}
