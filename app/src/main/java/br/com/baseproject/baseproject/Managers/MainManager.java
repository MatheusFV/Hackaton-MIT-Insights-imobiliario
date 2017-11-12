package br.com.baseproject.baseproject.Managers;

import br.com.baseproject.baseproject.Activities.MainActivity;

/**
 * Created by giova on 12/11/2017.
 */

public class MainManager {

    public interface MainFeedback {
        void didUpdateUser();
    }

    private MainFeedback feedback;

    public MainManager(MainFeedback feedback) {
        this.feedback = feedback;
    }
}
