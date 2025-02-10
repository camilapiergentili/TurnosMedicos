package ar.com.dontar.demo.service;

import ar.com.dontar.demo.model.User;
import ar.com.dontar.demo.model.exception.UserNoExistException;
import ar.com.dontar.demo.model.exception.IncorrectPaswordException;

public interface UserService {
    User login(String email, String password) throws UserNoExistException, IncorrectPaswordException;

}
