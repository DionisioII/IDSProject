package auth;

/**
 * Created by domenico on 23/05/16.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ortigiaenterprises.idsproject.R;
import server.InterfacciaServer;
import storage.LoginUtils;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private EditText emailText;
    private EditText passwordText;
    private AppCompatButton loginButton;
    private TextView registraLink;
    private InterfacciaServer server;
    private String token;
    private ProgressDialog progressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        server = InterfacciaServer.getInstance(this);
        token = LoginUtils.getFromPrefs(getApplicationContext(),LoginUtils.PREFS_LOGIN_TOKEN_KEY, null);
        if (token!=null)
        {

            progressDialog = new ProgressDialog(LoginActivity.this,
                    R.style.AppTheme_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Autenticazione in corso...");
            progressDialog.show();

            LoginTask loginTask =new LoginTask(progressDialog,getBaseContext());

            loginTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,token);

        }

        emailText = (EditText) findViewById(R.id.input_email);
        passwordText = (EditText) findViewById(R.id.input_password);
        loginButton = (AppCompatButton) findViewById(R.id.btn_login);
        registraLink = (TextView) findViewById(R.id.link_registra);


        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        registraLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), RegistraActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        loginButton.setEnabled(false);

        progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Autenticazione in corso...");
        progressDialog.show();

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.
        // On complete call either onLoginSuccess or onLoginFailed

        LoginTask loginTask =new LoginTask(progressDialog,getBaseContext());

        loginTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,email,password);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                // TODO: Implement successful signup logic here

                if (data!=null)
                {
                    String token = data.getStringExtra("TOKEN");
                    LoginUtils.saveToPrefs(getApplicationContext(),LoginUtils.PREFS_LOGIN_TOKEN_KEY, token);
                    token=null;
                }

                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess(String token) {
        //Toast.makeText(getBaseContext(), token, Toast.LENGTH_SHORT).show();
        if (!token.equals("ok"))
            LoginUtils.saveToPrefs(getApplicationContext(),LoginUtils.PREFS_LOGIN_TOKEN_KEY, token);
        token=null;
        this.token=null;
        loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login fallito", Toast.LENGTH_SHORT).show();
        loginButton.setEnabled(true);
    }

    public void skipLogin() {
        Toast.makeText(getBaseContext(), "offline", Toast.LENGTH_SHORT).show();
        token=null;
        loginButton.setEnabled(true);
        finish();
    }

    public boolean validate() {
        boolean valid = true;

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("inserisci un indirizzo email valido");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 8 || password.length() > 24) {
            passwordText.setError("tra 8 e 24 caratteri");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }

    private class LoginTask extends AsyncTask<String, Void, String>
    {
        private ProgressDialog progressDialog = null;
        private Context context=null;


        public LoginTask(ProgressDialog progressDialog,Context context) {
            this.progressDialog = progressDialog;
            this.context=context;
        }


        @Override
        protected String doInBackground(String... arrayDiAutenticazione)
        {
            // params comes from the execute() call: params[0] is the url.

            if (arrayDiAutenticazione.length!=0)
                if (arrayDiAutenticazione.length==1)
                    return server.autenticaToken(arrayDiAutenticazione[0]);
                else
                    return server.autentica(arrayDiAutenticazione[0],arrayDiAutenticazione[1]);
            else
                return "Invocazione non valida dell'autenticazione";

        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result)
        {
            //Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
            if (result.equals("non valido"))
            {
                emailText.setError("Token o credenziali non valide");
                LoginUtils.deleteFromPrefs(getApplicationContext(),LoginUtils.PREFS_LOGIN_TOKEN_KEY);
                onLoginFailed();
            }
            else
            {
                if (result.equals("Modalità offline"))
                    skipLogin();
                else
                    onLoginSuccess(result);
            }

            if (progressDialog.isShowing())
                progressDialog.dismiss();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Se ne mettiamo di più di pulsanti usare switch case
        // su item.getItemId() con default il return super.onOption...
        int id = item.getItemId();

        if (id == R.id.action_skip) {
            skipLogin();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
