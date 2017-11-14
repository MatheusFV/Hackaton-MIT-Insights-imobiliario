package br.com.baseproject.baseproject.Managers;

import br.com.baseproject.baseproject.Activities.MainActivity;
import br.com.baseproject.baseproject.Models.User;
import br.com.baseproject.baseproject.Services.UserService;

/**
 * Created by giova on 12/11/2017.
 */

public class MainManager implements UserService.FDUserFeedback {

    public interface MainFeedback {
        void didSaveUser(boolean success);
        void didGetUser(String name, String email, boolean success);
    }

    private UserService service = new UserService();
    private MainFeedback feedback;
    private String id;

    public MainManager(MainFeedback feedback) {
        this.feedback = feedback;
    }

    public void saveUser(String name, String email) {
        User user = new User(id, name, email);
        if (id == null) {
            service.createUser(user, this);
        } else {
            service.updateUser(user, this);
        }
    }

    public void getUser() {
        service.getUser(id, this);
    }

    @Override
    public void didCreateUser(String id, boolean success) {
        this.id = id;
        feedback.didSaveUser(success);
    }

    @Override
    public void didUpdateUser(boolean success) {
        feedback.didSaveUser(success);
    }

    @Override
    public void didGetUser(User user, boolean success) {
        feedback.didGetUser(user.name, user.email, success);
    }
}
