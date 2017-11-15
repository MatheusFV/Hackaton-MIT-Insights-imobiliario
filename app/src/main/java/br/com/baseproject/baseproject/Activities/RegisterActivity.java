package br.com.baseproject.baseproject.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.theartofdev.edmodo.cropper.CropImage;

import br.com.baseproject.baseproject.Models.User;
import br.com.baseproject.baseproject.R;
import br.com.baseproject.baseproject.Utils.ImageUtils;
import br.com.baseproject.baseproject.Utils.Utils;
import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity implements RegisterManager.RegisterFeedback {

    private TextInputEditText nameEditText;
    private TextInputEditText phoneEditText;
    private TextInputEditText emailEditText;
    private TextInputEditText passwordEditText;
    private TextInputEditText confirmPasswordEditText;
    private CircleImageView userPhoto;

    private Button registerButton;

    private ImageUtils imageUtilsHelper;
    private Uri image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Utils.setStatusBarColor(RegisterActivity.this, R.color.colorAccent);
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
        userPhoto = findViewById(R.id.activity_register_field_photo);
        registerButton = findViewById(R.id.activity_register_button_register);

        // Set phone mask
        phoneEditText.addTextChangedListener(applyMask());
    }

    public static String unmask(String s) {
        return s.replaceAll("[^0-9]*", "");
    }

    public TextWatcher applyMask() {
        return new TextWatcher() {
            boolean isUpdating;
            String old = "";

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = unmask(s.toString());
                String mask = "(##)#####-####";

                String mascara = "";
                if (isUpdating) {
                    old = str;
                    isUpdating = false;
                    return;
                }
                int i = 0;
                for (char m : mask.toCharArray()) {
                    if ((m != '#' && str.length() > old.length()) || (m != '#' && str.length() < old.length() && str.length() != i)) {
                        mascara += m;
                        continue;
                    }

                    try {
                        mascara += str.charAt(i);
                    } catch (Exception e) {
                        break;
                    }
                    i++;
                }
                isUpdating = true;
                phoneEditText.setText(mascara);
                phoneEditText.setSelection(mascara.length());
            }

            public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
            public void afterTextChanged(Editable s) {}
        };
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
                Intent intent = new Intent(RegisterActivity.this, RegisterInfosActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("phone", phone);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                intent.putExtra("image", image);
                startActivity(intent);
            }
        });

        userPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageUtilsHelper.getAndCropImage(RegisterActivity.this);
            }
        });
    }

    private void setupManager() {
        imageUtilsHelper = new ImageUtils();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
//                try{
                    Uri resultUri = result.getUri();
//                    final InputStream imageStream = getContentResolver().openInputStream(resultUri);
//                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    image = resultUri;
                    userPhoto.setImageURI(resultUri);
//                    userPhoto.setImageBitmap(selectedImage);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                    Toast.makeText(RegisterActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
//                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
