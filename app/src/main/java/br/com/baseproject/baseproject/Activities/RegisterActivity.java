package br.com.baseproject.baseproject.Activities;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.baseproject.baseproject.Managers.LoginManager;
import br.com.baseproject.baseproject.Managers.RegisterManager;
import br.com.baseproject.baseproject.Models.User;
import br.com.baseproject.baseproject.R;

public class RegisterActivity extends AppCompatActivity implements RegisterManager.RegisterFeedback {

    private TextInputEditText nameEditText;
    private TextInputEditText phoneEditText;
    private TextInputEditText emailEditText;
    private TextInputEditText passwordEditText;
    private TextInputEditText confirmPasswordEditText;

    private Button registerButton;

    private RegisterManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getLayoutIds();
        setButtonActions();
        setupManager();
    }

    private void getLayoutIds() {

        nameEditText = findViewById(R.id.activity_register_field_name);
        phoneEditText = findViewById(R.id.activity_register_field_phone);
        emailEditText = findViewById(R.id.activity_register_field_email);
        passwordEditText = findViewById(R.id.activity_register_field_password);
        confirmPasswordEditText = findViewById(R.id.activity_register_field_confirm_password);

        registerButton = findViewById(R.id.activity_register_button_register);
    }

    private void setButtonActions() {

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                manager.register(new User("", name, phone, email), password);
            }
        });
    }

    private void setupManager() {
        manager = new RegisterManager(this);
    }

    @Override
    public void registerSuccess() {

    }

    @Override
    public void registerError(Error error) {

    }
}
