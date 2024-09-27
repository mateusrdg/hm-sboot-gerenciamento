package com.healthmed.cognito;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.*;
import com.healthmed.domain.User;
import com.healthmed.domain.dtos.AuthDTO;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class CognitoClient {

    private AWSCognitoIdentityProvider client ;

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;
    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;
    @Value("${cloud.aws.cognito.client-id}")
    private String clientId;
    @Value("${cloud.aws.cognito.userpool}")
    private String userPool;
    @Value("${cloud.aws.cognito.client-secret}")
    private String clientSecret;
    private final String ALGORITHM = "HmacSHA256";

    @PostConstruct
    private void init() {
        this.client = createCognitoClient();
    }


    private AWSCognitoIdentityProvider createCognitoClient() {
        AWSCredentials cred = new BasicAWSCredentials(accessKey, secretKey);
        AWSCredentialsProvider credProvider = new AWSStaticCredentialsProvider(cred);
        return AWSCognitoIdentityProviderClientBuilder.standard()
                .withCredentials(credProvider)
                .withRegion(Regions.US_EAST_2)
                .build();
    }

    public SignUpResult signUp(User user) {
        SignUpRequest request = new SignUpRequest().withClientId(clientId).withUsername(user.getEmail()).withPassword(user.getPassword())
                .withSecretHash(calculateSecretHash(user.getEmail()));

        SignUpResult result = client.signUp(request);
        addToGroup(user);
        return result;
    }

    private void addToGroup(User user) {
        AdminAddUserToGroupRequest addUserToGroupRequest = new AdminAddUserToGroupRequest()
                .withUserPoolId(userPool)
                .withUsername(user.getEmail())
                .withGroupName(user.getUserType().name());
        client.adminAddUserToGroup(addUserToGroupRequest);
    }

    @SneakyThrows
    public String calculateSecretHash(String username) {
        String message = username + clientId;
        SecretKeySpec signingKey = new SecretKeySpec(clientSecret.getBytes(StandardCharsets.UTF_8), ALGORITHM);
        Mac mac = Mac.getInstance(ALGORITHM);
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(rawHmac);
    }

    public AuthDTO login(String email, String password) {
        var response = new AuthDTO();
        Map<String, String> authParams = new LinkedHashMap<>() {{
            put("USERNAME", email);
            put("PASSWORD", password);
            put("SECRET_HASH", calculateSecretHash(email));
        }};

        InitiateAuthRequest authRequest = new InitiateAuthRequest()
                .withAuthFlow(AuthFlowType.USER_PASSWORD_AUTH)
                .withClientId(clientId)
                .withAuthParameters(authParams);
        InitiateAuthResult authResult = client.initiateAuth(authRequest);
        response.setAccessToken(authResult.getAuthenticationResult().getAccessToken());
        return response;

    }
}
