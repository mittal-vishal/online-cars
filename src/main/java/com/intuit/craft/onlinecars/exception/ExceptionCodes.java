package com.intuit.craft.onlinecars.exception;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionCodes {

    //Access related exception codes
    public ExceptionCode A101 = new ExceptionCode("A101", "Access Denied.", false);

    //Validation related exception codes
    public ExceptionCode V101 = new ExceptionCode("V101", "Validation error occurred.", false);
    public ExceptionCode V102 = new ExceptionCode("V102", "Car not found.", false);
}
