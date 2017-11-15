package br.com.baseproject.baseproject.Managers;

import br.com.baseproject.baseproject.Models.User;

/**
 * Created by luciana on 14/11/17.
 */

public class RegisterManager {

    public interface RegisterFeedback {
        void registerSuccess();
        void registerError(Error error);
    }

    private RegisterFeedback feedback;

    public RegisterManager(RegisterFeedback feedback) { this.feedback = feedback; }

    public void register(User user, String password) {

    }
}
