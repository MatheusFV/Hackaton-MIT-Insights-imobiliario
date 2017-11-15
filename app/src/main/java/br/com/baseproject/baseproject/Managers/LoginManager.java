package br.com.baseproject.baseproject.Managers;

/**
 * Created by luciana on 14/11/17.
 */

public class LoginManager {

    public interface LoginFeedback {
        void loginSuccess();
        void loginError(Error error);
    }

    private LoginFeedback feedback;

    public LoginManager(LoginFeedback feedback) { this.feedback = feedback; }

    public void login(String email, String password) {

    }
}
