package br.com.baseproject.baseproject.Activities;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.baseproject.baseproject.Managers.LoginManager;
import br.com.baseproject.baseproject.R;

public class LoginActivity extends AppCompatActivity implements LoginManager.LoginFeedback {

    private TextInputEditText emailEditText;
    private TextInputEditText passwordEditText;
    private Button loginButton;

    private LoginManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getLayoutIds();
        setButtonActions();
        setupManager();
    }

    private void getLayoutIds() {

        emailEditText = findViewById(R.id.activity_login_field_email);
        passwordEditText = findViewById(R.id.activity_login_field_password);

        loginButton = findViewById(R.id.activity_login_button_login);
    }

    private void setButtonActions() {

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                manager.login(email, password);
            }
        });
    }

    private void setupManager() {
        manager = new LoginManager(this);
    }


    @Override
    public void loginSuccess() {

    }

    @Override
    public void loginError(Error error) {

    }
}
