package guru.springframework.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import guru.springframework.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {
	
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = NumberFormatException.class)
    public ModelAndView handle400BadRequest(Exception ex) {
    	log.error("Handling 400 bad request error!");

    	ModelAndView mav = new ModelAndView();

    	mav.addObject("exception", ex);
		mav.setViewName("400error");

    	return mav;
    }
    
    @ExceptionHandler(value = NotFoundException.class)
    public ModelAndView handle404(Exception ex) {
    	log.error("Handling 404 error!");
    	ModelAndView mav = new ModelAndView();

    	mav.addObject("exception",ex);
		mav.setViewName("404error");

    	
    	return mav;
    }
}
