package ar.com.dontar.demo.exception.handler;

import ar.com.dontar.demo.exception.*;
import jakarta.annotation.Nullable;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@ControllerAdvice
public class DontarResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Map<Class<? extends Exception>, Integer> exeptionToErrorCodeMap = new HashMap<>();

    static{


        //BAD_REQUEST
        exeptionToErrorCodeMap.put(CancellationTimeExceededException.class, 1000);
        exeptionToErrorCodeMap.put(IncorrectPaswordException.class, 1001);
        exeptionToErrorCodeMap.put(InvalidRequestBodyException.class, 1002);


        //NOT_FOUND
        exeptionToErrorCodeMap.put(AppoinmentNotGenerateException.class, 2000);
        exeptionToErrorCodeMap.put(AppointmentNotExistsException.class, 2001);
        exeptionToErrorCodeMap.put(ScheduleNotExistsException.class, 2002);
        exeptionToErrorCodeMap.put(SpecialityNotExistsException.class, 2003);
        exeptionToErrorCodeMap.put(UserNotExistsException.class, 2004);


        //CONFLIC
        exeptionToErrorCodeMap.put(EmailAlreadyRegisteredException.class, 3000);
        exeptionToErrorCodeMap.put(PatientAlreadyExistsException.class, 3001);
        exeptionToErrorCodeMap.put(ProfessionalAlreadyExistsException.class, 3002);
        exeptionToErrorCodeMap.put(ScheduleAlreadyExistsException.class, 3003);
        exeptionToErrorCodeMap.put(SpecialityAlreadyExistsException.class, 3004);

        //UNAUTHORIZED
        exeptionToErrorCodeMap.put(AuthenticationException.class, 4000);
        exeptionToErrorCodeMap.put(ExtractInfoUserFromTokenException.class, 4001);

        //FORBIDDEN
        exeptionToErrorCodeMap.put(AccessDeniedException.class, 5000);

    }

    @ExceptionHandler(value = {
            AppoinmentNotGenerateException.class,
            AppointmentNotExistsException.class,
            CancellationTimeExceededException.class,
            IncorrectPaswordException.class,
            PatientAlreadyExistsException.class,
            ProfessionalAlreadyExistsException.class,
            ScheduleAlreadyExistsException.class,
            ScheduleNotExistsException.class,
            SpecialityAlreadyExistsException.class,
            SpecialityNotExistsException.class,
            UserNotExistsException.class,
            EmailAlreadyRegisteredException.class,
            AuthenticationException.class,
            ExtractInfoUserFromTokenException.class,
            AccessDeniedException.class,
            InvalidRequestBodyException.class

    })
    protected ResponseEntity<Object> handleSpecificExceptions(Exception ex, WebRequest request) {
        int errorCode = exeptionToErrorCodeMap.getOrDefault(ex.getClass(), 0);
        HttpStatus status = determineHttpStatus(errorCode);

        CustomError error = new CustomError();
        error.setErrorMessage(ex.getMessage());
        error.setCodeError(errorCode);

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(" | "));

        CustomError error = new CustomError();
        error.setErrorMessage(errorMessage);
        error.setCodeError(1002);

        return handleExceptionInternal(ex, error, headers, HttpStatus.BAD_REQUEST, request);
    }

    // Determina el HttpStatus basado en el código de error
    private HttpStatus determineHttpStatus(int errorCode) {

        if (errorCode >= 1000 && errorCode < 2000) {
            return HttpStatus.BAD_REQUEST;
        } else if (errorCode >= 2000 && errorCode < 3000) {
            return HttpStatus.NOT_FOUND;
        } else if (errorCode >= 3000 && errorCode < 4000) {
            return HttpStatus.CONFLICT;
        } else if (errorCode >= 5000 && errorCode < 6000) {
            return HttpStatus.UNAUTHORIZED;
        } else if (errorCode >= 6000 && errorCode < 7000) {
            return HttpStatus.FORBIDDEN;
        }else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode status, WebRequest request){

        if(body == null){
            CustomError error = new CustomError();
            error.setErrorMessage(ex.getMessage());
            body = error;

        }
        return new ResponseEntity<>(body, headers, status);
    }
}
