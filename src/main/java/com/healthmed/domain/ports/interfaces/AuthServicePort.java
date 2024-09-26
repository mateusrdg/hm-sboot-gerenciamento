package com.healthmed.domain.ports.interfaces;

import com.amazonaws.services.cognitoidp.model.SignUpResult;
import com.healthmed.domain.User;
import com.healthmed.domain.dtos.AuthDTO;

public interface AuthServicePort {
    AuthDTO login(String email, String password);
    SignUpResult signUp(User user);
}
