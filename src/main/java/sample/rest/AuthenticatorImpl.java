package sample.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;
import sample.dto.OperatorAuthenticationResultDto;
import sample.dto.OperatorCredentialsDto;



public class AuthenticatorImpl implements Authenticator {

    private static final String AUTH_URL = "http://localhost:8080/verify_operator_credentials";
    private final RestTemplate restTemplate;

    public AuthenticatorImpl() {
        restTemplate = new RestTemplate();
    }

    @Override
    public void authenticate(OperatorCredentialsDto operatorCredentialsDTO, AuthenticationResultHandler authenticationResultHandler) {
        Runnable authenticationTask = ()-> processAuthentication(operatorCredentialsDTO, authenticationResultHandler);
        Thread authThread = new Thread(authenticationTask);
        authThread.setDaemon(true);
        authThread.start();
    }

    private @ResponseBody void processAuthentication(OperatorCredentialsDto operatorCredentialsDTO, AuthenticationResultHandler authenticationResultHandler) {
        ResponseEntity<OperatorAuthenticationResultDto> responseEntity = restTemplate.postForEntity(AUTH_URL, operatorCredentialsDTO, OperatorAuthenticationResultDto.class);
        System.out.println(responseEntity.getBody());
        authenticationResultHandler.handle(responseEntity.getBody());
        }


}




