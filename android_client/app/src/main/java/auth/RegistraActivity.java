package auth;

/**
 * Created by domenico on 23/05/16.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import ortigiaenterprises.idsproject.R;
import server.InterfacciaServer;


public class RegistraActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    private EditText nameText;
    private EditText cognomeText;
    private EditText emailText;
    private EditText passwordText;
    private Button registraButton;
    private TextView loginLink;

    private InterfacciaServer server;
    private String token;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registra);

        nameText = (EditText) findViewById(R.id.input_name);
        cognomeText = (EditText) findViewById(R.id.input_cognome);
        emailText = (EditText) findViewById(R.id.input_email);
        passwordText = (EditText) findViewById(R.id.input_password);
        registraButton = (Button) findViewById(R.id.btn_registra);
        loginLink = (TextView) findViewById(R.id.link_login);

        server = InterfacciaServer.getInstance(this);


        registraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            //da creare campo errori!!
            onSignupFailed(null);

            return;
        }

        registraButton.setEnabled(false);

        ProgressDialog progressDialog = new ProgressDialog(RegistraActivity.this,
                R.style.AppTheme_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creando l'account...");
        progressDialog.show();

        String nome = nameText.getText().toString();
        String cognome = cognomeText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();


        RegistraTask registraTask =new RegistraTask(progressDialog,getBaseContext());
        registraTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,nome, cognome, email,password);

    }


    public void onSignupSuccess(JSONArray risposta)
    {
        if (risposta!=null)
        {
            String token=null;
            for (int i = 0; i < risposta.length(); i++)
            {
                try
                {
                    JSONObject row = risposta.getJSONObject(i);
                    Iterator keys = row.keys();
                    while (keys.hasNext())
                    {
                        // loop to get the dynamic key
                        String currentDynamicKey = (String) keys.next();
                        // get the value of the dynamic key
                        //JSONObject currentDynamicValue = row.getJSONObject(currentDynamicKey);
                        String currentDynamicValue = row.get(currentDynamicKey).toString();
                        switch (currentDynamicKey)
                        {
                            case "token":
                                token=currentDynamicValue.toString();
                                break;
                        }
                    }
                }
                catch (JSONException e) {}
            }

            registraButton.setEnabled(true);
            Intent risultato=new Intent();
            risultato.putExtra("TOKEN",token);
            setResult(RESULT_OK, risultato);
            finish();

        }

        registraButton.setEnabled(true);

    }

    public void onSignupFailed(JSONArray errori) {
        Toast.makeText(getBaseContext(), "Registrazione fallita", Toast.LENGTH_SHORT).show();

        if (errori!=null)
        {
            for (int i = 0; i < errori.length(); i++)
            {
                try
                {
                    JSONObject row = errori.getJSONObject(i);
                    Iterator keys=row.keys();
                    while(keys.hasNext())
                    {
                        // loop to get the dynamic key
                        String currentDynamicKey = (String)keys.next();
                        // get the value of the dynamic key
                        //JSONObject currentDynamicValue = row.getJSONObject(currentDynamicKey);
                        String currentDynamicValue= row.get(currentDynamicKey).toString();
                        switch (currentDynamicKey)
                        {
                            case "nome":
                                nameText.setError(currentDynamicValue.toString());
                                break;
                            case "cognome":
                                cognomeText.setError(currentDynamicValue.toString());
                                break;
                            case "email":
                                emailText.setError(currentDynamicValue.toString());
                                break;
                            case "password":
                                passwordText.setError(currentDynamicValue.toString());
                                break;
                        }


                    }

                }
                catch (JSONException e) {}

            }
        }

        registraButton.setEnabled(true);
    }

    public void skipSignup() {
        registraButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public boolean validate() {
        boolean valid = true;

        String nome = nameText.getText().toString();
        String cognome = cognomeText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (nome.isEmpty() || nome.length() < 3) {
            nameText.setError("Almeno 3 caratteri");
            valid = false;
        } else {
            nameText.setError(null);
        }

        if (cognome.isEmpty() || cognome.length() < 3) {
            cognomeText.setError("Almeno 3 caratteri");
            valid = false;
        } else {
            cognomeText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Inserisci un indirizzo email valido");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 8 || password.length() > 24) {
            passwordText.setError("Tra 8 e 24 caratteri");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
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
            skipSignup();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private class RegistraTask extends AsyncTask<String, Void, String>
    {
        private ProgressDialog progressDialog = null;
        private Context context=null;


        public RegistraTask(ProgressDialog progressDialog,Context context) {
            this.progressDialog = progressDialog;
            this.context=context;
        }


        @Override
        protected String doInBackground(String... arrayDiRegistrazione)
        {
            // params comes from the execute() call: params[0] is the url.

            if (arrayDiRegistrazione.length==4)
                return server.registra(arrayDiRegistrazione[0],arrayDiRegistrazione[1],arrayDiRegistrazione[2],arrayDiRegistrazione[3]);
            else
                return "Invocazione non valida della registrazione";

        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result)
        {
            if (result != null)
            {
                //Toast.makeText(context, "RegistraActivity:result: "+result, Toast.LENGTH_SHORT).show();
                JSONObject risposta;
                try
                {
                    risposta = new JSONObject(result);
                    if (risposta.has("stato")&& !risposta.isNull("stato") && risposta.has("risultato") && !risposta.isNull("risultato"))
                    {
                        String stato = (String) risposta.get("stato");
                        JSONArray jsonArray = (JSONArray) risposta.get("risultato");
                        if (stato.equals("OK"))
                            {
                                onSignupSuccess(jsonArray);
                            }
                            else if (stato.equals("KO"))
                            {
                                onSignupFailed(jsonArray);
                            }
                            else
                            {
                                Toast.makeText(context, "Risposta sconosciuta: " + result, Toast.LENGTH_SHORT).show();
                                skipSignup();
                            }
                    }
                    else
                    {
                        Toast.makeText(context, "JSON Non valido: " + result, Toast.LENGTH_SHORT).show();
                        skipSignup();
                    }

                }
                catch (JSONException e)
                {
                    Toast.makeText(context, "JSON Non valido: " + result, Toast.LENGTH_SHORT).show();
                    skipSignup();
                }
            }
            else
            {
                skipSignup();
            }
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        }




    }


}