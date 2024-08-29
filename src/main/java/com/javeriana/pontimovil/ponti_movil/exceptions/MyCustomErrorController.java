package com.javeriana.pontimovil.ponti_movil.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice // AOP
public class MyCustomErrorController {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ModelAndView handleErrorNoParam(Model model, MethodArgumentTypeMismatchException exception) {
        model.addAttribute("exceptionText", exception.toString());
        return new ModelAndView("error/pagina-error");
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ModelAndView handleErrorNoParam(Model model, NoResourceFoundException exception) {
        model.addAttribute("exceptionText", exception.toString());
        return new ModelAndView("error/pagina-error");
    }

}
