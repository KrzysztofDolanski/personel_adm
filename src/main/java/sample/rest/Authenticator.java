package sample.rest;

import sample.dto.UserCredentialsDTO;

public interface Authenticator
//        extends JpaRepository<UserCredentialsDTO, Long>
{

    void authenticate(UserCredentialsDTO userCredentialsDTO, AuthenticationResultHandler authenticationResultHandler);
}
