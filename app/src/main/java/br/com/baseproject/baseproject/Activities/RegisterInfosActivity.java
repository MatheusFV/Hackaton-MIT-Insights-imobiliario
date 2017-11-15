package br.com.baseproject.baseproject.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import br.com.baseproject.baseproject.Models.User;
import br.com.baseproject.baseproject.R;
import br.com.baseproject.baseproject.Utils.Utils;

public class RegisterInfosActivity extends AppCompatActivity implements RegisterManager.RegisterFeedback{

    Button register;
    Spinner gender;
    Spinner animals;
    Spinner age;
    Spinner ocupation;
    Spinner smoke;

    String name;
    String phone;
    String email;
    String password;
    Uri image;
    String genderSelected;
    String ageSelected;
    String animalSelected;
    String ocupationSelected;
    String smokeSelected;

    //Firebase
    private DatabaseReference database;
    private FirebaseAuth mAuth;


    RegisterManager registerManager;

    KProgressHUD progressHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_infos);

        Utils.setStatusBarColor(RegisterInfosActivity.this, R.color.colorAccent);

        //Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //Firebase Database
        database = FirebaseDatabase.getInstance().getReference();

        progressHUD = KProgressHUD.create(this).setDimAmount(0.5F).setCancellable(false);

        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        phone = getIntent().getStringExtra("phone");
        image = getIntent().getParcelableExtra("image");
        getLayoutIds();
        setButtonActions();
        setupManager();
        setSpinners();
    }

    private OnCompleteListener<AuthResult> onSignUpComplete = new OnCompleteListener<AuthResult>(){
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {

            if (!task.isSuccessful()) {
                Toast.makeText(RegisterInfosActivity.this, "Erro ao cadastrar", Toast.LENGTH_LONG).show();
            } else {
                DatabaseReference ref = database.child("users").child(mAuth.getCurrentUser().getUid());
                ref.setValue(createUser(mAuth.getCurrentUser().getUid()));
                try{
                    if (image != null){
                        final InputStream imageStream = getContentResolver().openInputStream(image);
                        final Bitmap bitmapImage = BitmapFactory.decodeStream(imageStream);
                        saveImageOnFirebase(bitmapImage);
                    }
                }catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(RegisterInfosActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }

            }
        }
    };

    private User createUser(String id){
        return new User(
                name,
                phone,
                email,
                genderSelected,
                ageSelected,
                animalSelected.equals("Sim"),
                ocupationSelected,
                smokeSelected.equals("Sim")
        );
    }
    private void getLayoutIds() {
        gender = findViewById(R.id.register_gender);
        animals = findViewById(R.id.register_animals);
        age = findViewById(R.id.register_age);
        ocupation = findViewById(R.id.register_ocupation);
        smoke = findViewById(R.id.register_smoke);
        register = findViewById(R.id.register_button_register);
    }

    private void setButtonActions() {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelectedSpinnerItems();
            }
        });
    }

    private void saveImageOnFirebase(Bitmap bitmap) {

        if (image != null){
            if (mAuth.getCurrentUser() == null)
                return;

            //Get UID
            final String uid = mAuth.getCurrentUser().getUid();

        progressHUD.show();

            //Get Storage Reference
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference imgRef = storage.getReference().child("usersPhoto").child(uid+".jpg");

            //Compress to JPEG
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            //Upload
            imgRef.putBytes(data).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                progressHUD.dismiss();
                Toast.makeText(RegisterInfosActivity.this, "Ocorreu um erro", Toast.LENGTH_LONG).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressHUD.dismiss();
                String photoUrl = taskSnapshot.getDownloadUrl().toString();
                database.child("users").child(uid).child("photoUrl").setValue(photoUrl);
                //TODO: cadastro completo - redirecionar
                Intent intent = new Intent(RegisterInfosActivity.this, TabsMainActivity.class);
                startActivity(intent);
                }
            });
        }
    }

    private void setSelectedSpinnerItems(){
        genderSelected = gender.getSelectedItem().toString();
        ageSelected = age.getSelectedItem().toString();
        smokeSelected = smoke.getSelectedItem().toString();
        ocupationSelected = ocupation.getSelectedItem().toString();
        animalSelected = animals.getSelectedItem().toString();
        finishRegister();
    }

    private void setupManager() {
        registerManager = new RegisterManager(RegisterInfosActivity.this);
    }

    private void setSpinners(){
        setSpinnerAdapter(gender, R.array.genders);
        setSpinnerAdapter(animals, R.array.yes_no_animal);
        setSpinnerAdapter(age, R.array.age);
        setSpinnerAdapter(smoke, R.array.yes_no_smoke);
        setSpinnerAdapter(ocupation, R.array.ocupation);
    }

    private void setSpinnerAdapter(Spinner spinner, int itens){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                itens, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void finishRegister(){
        if (!smokeSelected.equals(getResources().getStringArray(R.array.yes_no_smoke)[0]) &&
                !ageSelected.equals(getResources().getStringArray(R.array.age)[0])  &&
                !genderSelected.equals(getResources().getStringArray(R.array.genders)[0])  &&
                !animalSelected.equals(getResources().getStringArray(R.array.yes_no_animal)[0])  &&
                !ocupationSelected.equals(getResources().getStringArray(R.array.ocupation)[0]) ){
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(onSignUpComplete);
        }else{
            Toast.makeText(RegisterInfosActivity.this, "Por favor, selecione todos os campos", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void registerSuccess() {

    }

    @Override
    public void registerError(Error error) {

    }
}
