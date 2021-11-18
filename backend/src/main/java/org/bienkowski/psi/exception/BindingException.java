package org.bienkowski.psi.exception;

import org.springframework.validation.BindingResult;

public class BindingException extends RuntimeException {

    public BindingException(BindingResult bindingResult) {

    }
}
