package com.healthmed.cognito;

import com.amazonaws.services.cognitoidp.model.NotAuthorizedException;
import com.amazonaws.services.cognitoidp.model.SignUpResult;
import com.healthmed.application.adapters.controllers.exception.BadRequestException;
import com.healthmed.application.adapters.controllers.exception.InternalServerErrorException;
import com.healthmed.domain.User;
import com.healthmed.domain.dtos.AuthDTO;
import com.healthmed.domain.ports.interfaces.AuthServicePort;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthServiceImp implements AuthServicePort {

    private final CognitoClient client;

    public AuthServiceImp(CognitoClient client) {
        this.client = client;
    }

    @Override
    public AuthDTO login(String email, String password) {
        try {
            return client.login(email, password);
        } catch (NotAuthorizedException exception) {
            throw new BadRequestException("login ou senha inválidos");
        } catch (Exception exception) {
            throw new InternalServerErrorException(exception.getMessage());
        }
    }

    @Override
    public SignUpResult signUp(User user) {
        try{
            return client.signUp(user);
        } catch (Exception exception) {
            throw new InternalServerErrorException(exception.getMessage());
        }
    }

}
