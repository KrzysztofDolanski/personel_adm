package sample.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.dto.OperatorCredentialsDto;
import sample.factory.PopupFactory;
import sample.rest.Authenticator;
import sample.rest.AuthenticatorImpl;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {


    private PopupFactory popupFactory;
    private Authenticator auth;

    public LoginController() {
        popupFactory = new PopupFactory();
        auth = new AuthenticatorImpl();
    }

    @FXML
    private TextField loginTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private AnchorPane loginAnchorPane;
    @FXML
    private Button confirmButton;
    @FXML
    private Button exitButton;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        exitButton();
        loginButton();
    }

    private void loginButton() {
        confirmButton.setOnAction(x ->
                performAuth());
    }

    private void performAuth() {
        Stage waitingPopup = popupFactory.createWaitingPopup("Connecting....");
        waitingPopup.show();

        OperatorCredentialsDto user = new OperatorCredentialsDto();
        user.setLogin(loginTextField.getText());
        user.setPassword(passwordTextField.getText());
        auth.authenticate(user, (authentication) -> {
            Platform.runLater(() -> {
                waitingPopup.close();
                System.out.println(
                        authentication.isAuthenticated() +
                                " " + authentication.toString());
            });
        });
    }

    private void exitButton() {
        exitButton.setOnAction((x) ->
                getStage().close());
    }


    private Stage getStage() {
        return (Stage) loginAnchorPane.getScene().getWindow();
    }
}
