package tr.edu.ozu.ozunaviclient;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class SignupActivity extends AppCompatActivity {

    String url = "http://192.168.1.63:8080";
    private postXMLTask mAuthTask = null;

    private View mProgressView;
    private View mSignupFormView;

    private EditText editTextName;
    private EditText editTextSurname;
    private Spinner spinnerGender;
    private EditText editTextAge;
    private EditText editTextEmail;
    private Spinner spinnerFaculty;
    private Spinner spinnerStudentclass;
    private EditText editTextPassword;
    private Button buttonDone;

    private String name;
    private String surname;
    private String gender;
    private int age;
    private String email;
    private String faculty;
    private Integer studentclass;
    private String password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mProgressView = findViewById(R.id.signup_progress);
        mSignupFormView = findViewById(R.id.signup_form);

        editTextName = (EditText) findViewById(R.id.name);
        editTextSurname = (EditText) findViewById(R.id.surname);
        spinnerGender = (Spinner) findViewById(R.id.gender);
        editTextAge = (EditText) findViewById(R.id.age);
        editTextEmail = (EditText) findViewById(R.id.email);
        spinnerFaculty = (Spinner) findViewById(R.id.faculty);
        spinnerStudentclass = (Spinner) findViewById(R.id.studentclass);
        editTextPassword = (EditText)findViewById(R.id.password);
        buttonDone = (Button) findViewById(R.id.done);

        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSignUp();
            }
        });


    }

    private void attemptSignUp(){
        name = editTextName.getText().toString();
        surname = editTextSurname.getText().toString();
        gender = spinnerGender.getSelectedItem().toString();
        age = Integer.valueOf(editTextAge.getText().toString());
        email = editTextEmail.getText().toString();
        faculty = spinnerFaculty.getSelectedItem().toString();
        studentclass = Integer.valueOf(spinnerStudentclass.getSelectedItem().toString());
        password = editTextPassword.getText().toString();


        if(name == null && name.isEmpty()){
            editTextName.setError("This field cannot be empty.");
            editTextName.requestFocus();
        }else if(surname == null && surname.isEmpty()){
            editTextSurname.setError("This field cannot be empty.");
            editTextSurname.requestFocus();
        }else if(gender != null && gender.isEmpty()){
            TextView errorText = (TextView)spinnerGender.getSelectedView();
            errorText.setError("Please choose gender.");
            spinnerGender.requestFocus();
        }else if(age == 0){
            editTextAge.setError("This field cannot be empty.");
            editTextAge.requestFocus();
        }else if(email == null && email.isEmpty()){
            editTextEmail.setError("This field cannot be empty.");
            editTextEmail.requestFocus();
        }else if(faculty == null && faculty.isEmpty()){
            TextView errorText2 = (TextView)spinnerFaculty.getSelectedView();
            errorText2.setError("Please choose your faculty.");
            spinnerFaculty.requestFocus();
        }else if(studentclass == null){
            TextView errorText3 = (TextView)spinnerStudentclass.getSelectedView();
            errorText3.setError("Please choose your class.");
            spinnerStudentclass.requestFocus();
        }else if(password == null && password.isEmpty()){
            editTextPassword.setError("This field cannot be empty.");
            editTextPassword.requestFocus();
        }else{
            signup();
        }
    }

    private void signup(){
        showProgress(true);
        mAuthTask = new SignupActivity.postXMLTask();
        mAuthTask.execute(new String[]{url + "/Ozu/Signup"});
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mSignupFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mSignupFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mSignupFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mSignupFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }



    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    private class postXMLTask extends AsyncTask<String, Void, String> {


        public postXMLTask(){
        }

        @Override
        protected String doInBackground(String... urls) {
            String output = null;
            for (String url : urls) {
                output = getOutputFromUrl(url);
            }
            return output;
        }

        private String getOutputFromUrl(String url) {
            String output = null;
            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);

                ArrayList<NameValuePair> postParameters;


                postParameters = new ArrayList<NameValuePair>();
                postParameters.add(new BasicNameValuePair("name", name));
                postParameters.add(new BasicNameValuePair("surname", surname));
                postParameters.add(new BasicNameValuePair("gender", gender));
                postParameters.add(new BasicNameValuePair("age", Integer.toString(age)));
                postParameters.add(new BasicNameValuePair("mail", email));
                postParameters.add(new BasicNameValuePair("password", Base64.encodeToString(password.getBytes(), Base64.NO_WRAP)));
                postParameters.add(new BasicNameValuePair("faculty", faculty));
                postParameters.add(new BasicNameValuePair("studentclass", Integer.toString(studentclass)));

                httpPost.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));


                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                output = EntityUtils.toString(httpEntity);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return output;
        }

        @Override
        protected void onPostExecute(String output) {
            if(output!=null) {
                try {
                    JSONObject model = new JSONObject(output);

                    int status = model.getInt("Status");
                    if(status == 0){
                        Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
                        startActivity(intent);
                    }else{
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.w("OUTPUT", output);
            }
            showProgress(false);
        }
    }
}
