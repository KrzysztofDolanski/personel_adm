package sample.rest;

import sample.dto.UserCredentialsDTO;

public class AuthenticatorImpl implements Authenticator {


    private final String LOGIN = "abc";
    private final String PASSWORD = "123";

    @Override
    public void authenticate(UserCredentialsDTO userCredentialsDTO, AuthenticationResultHandler authenticationResultHandler) {
        Runnable authenticationTask = createAuthenticationTask(userCredentialsDTO, authenticationResultHandler);
        Thread authThread = new Thread(authenticationTask);
        authThread.setDaemon(true);
        authThread.start();
    }

    private Runnable createAuthenticationTask(UserCredentialsDTO userCredentialsDTO, AuthenticationResultHandler authenticationResultHandler) {
        return () ->
        {
            try {
                Thread.sleep(1000);
                boolean userAuthorisationChecker = userCredentialsDTO.getLogin().equals(LOGIN) &&
                        userCredentialsDTO.getPassword().equals(PASSWORD);
                authenticationResultHandler.handle(userAuthorisationChecker);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }
}
