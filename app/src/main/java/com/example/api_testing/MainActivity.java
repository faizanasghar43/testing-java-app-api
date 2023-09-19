package com.example.api_testing;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    // creating variables for our edittext,
    // button, textview and progressbar.
    private EditText nameEditText;
    private EditText rollNumberEditText;
    private EditText emailSignUpEditText;
    private EditText passwordSignUpEditText;
    private Button doSignUpButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializing our views
        nameEditText = findViewById(R.id.nameEditText);
        rollNumberEditText = findViewById(R.id.rollNumberEditText);
        emailSignUpEditText = findViewById(R.id.emailSignUpEditText);
        passwordSignUpEditText = findViewById(R.id.passwordSignUpEditText);
        doSignUpButton = findViewById(R.id.doSignUpButton);
        // adding on click listener to our button.
        doSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validating if the text field is empty or not.
//                if (emailSignUpEditText.getText().toString().isEmpty() && rollNumberEditText.getText().toString().isEmpty()) {
//                    Toast.makeText(MainActivity.this, "Please enter both the values", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                String name = nameEditText.getText().toString();
                String rollNumber = rollNumberEditText.getText().toString();
                String email = emailSignUpEditText.getText().toString();
                String password = passwordSignUpEditText.getText().toString();
                // calling a method to post the data and passing our name and job.
                postData(name, email, rollNumber, password);
            }
        });
    }

    private void postData(String name, String email, String roll, String pass) {

        // below line is for displaying our progress bar.

        // on below line we are creating a retrofit
        // builder and passing our base url
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://map-backend-1.onrender.com/")
                // as we are sending data in json format so
                // we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // at last we are building our retrofit builder.
                .build();
        // below line is to create an instance for our retrofit api class.
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        // passing data from our text fields to our modal class.
        DataModel modal = new DataModel(name,email,roll, pass);

        // calling a method to create a post and passing our modal class.
        Call<DataModel> call = retrofitAPI.createPost(modal);

        // on below line we are executing our method.
        call.enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                // this method is called when we get response from our api.
                Toast.makeText(MainActivity.this, "Data added to API", Toast.LENGTH_SHORT).show();

                // below line is for hiding our progress bar.

                // on below line we are setting empty text
                // to our both edit text.

                // we are getting response from our body
                // and passing it to our modal class.

                // on below line we are getting our data from modal class and adding it to our string.
                String responseString = "Response Code : " + response.code() ;

                // below line we are setting our
                // string to our text view.
                nameEditText.setText(responseString);
            }

            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                nameEditText.setText("Error found is : " + t.getMessage());
            }
        });
    }
}
