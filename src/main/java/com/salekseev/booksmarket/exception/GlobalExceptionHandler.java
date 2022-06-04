package com.salekseev.booksmarket.exception;

import com.salekseev.booksmarket.dto.ErrorInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger("ERROR");

    @ExceptionHandler({ BadRequestException.class })
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInfo handleBadRequestException(Exception e) {
        String message = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
        logger.info(message);
        return new ErrorInfo(e.getMessage());
    }

}
