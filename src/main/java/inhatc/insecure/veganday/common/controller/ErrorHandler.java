package inhatc.insecure.veganday.common.controller;

import inhatc.insecure.veganday.common.model.ResponseFmt;
import inhatc.insecure.veganday.common.model.ResponseMessage;
import inhatc.insecure.veganday.common.model.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;


@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public Object handleConstraintViolationException(ConstraintViolationException e) {
        List<String> errorList = new ArrayList<>();

        e.getConstraintViolations().forEach(errors -> {
            errorList.add(errors.getMessage());
        });

        return new ResponseEntity(ResponseFmt.res(StatusCode.BAD_REQUEST, ResponseMessage.WRITE_CONST_ERROR, errorList), HttpStatus.OK);
    }
}
