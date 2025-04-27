package ar.com.dontar.demo.exception.handler;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomError {
    private String errorMessage;
    private Integer codeError;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Integer getCodeError() {
        return codeError;
    }

    public void setCodeError(Integer codeError) {
        this.codeError = codeError;
    }
}
