package server;

import android.os.AsyncTask;
import android.util.Log;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by domenico on 13/05/16.
 */
public class ServerTask extends AsyncTask<String, Void, String> {

    public AsyncResponse delegate = null;//Call back interface

    public ServerTask(AsyncResponse asyncResponse) {
        delegate = asyncResponse;//Assigning call back interfacethrough constructor
    }


    @Override
    protected String doInBackground(String... urls) {

        // params comes from the execute() call: params[0] is the url.
        try {
            if (urls.length!=0)
                if (urls.length==1)
                    return downloadUrl(urls[0]);
                else
                    return postTask(urls[0],urls[1]);
            else
                return "Invocazione non valida del metodo execute";
        } catch (IOException e) {
            Log.d("IOException: ",e.toString());
            return "Impossibile ricevere la risorsa. L'URL potrebbe non essere valido o il server è offline";
        }
    }
    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        delegate.processFinish(result);

    }

    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        // Only display the first 2000 characters of the retrieved
        // web page content.
        int len = 5000;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(20000);
            conn.setConnectTimeout(30000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            is = conn.getInputStream();

            // Convert the InputStream into a string
            Log.d("RAW_OUTPUT",is.toString());
            //String contentAsString = readIt(is, len);
            String contentAsString = convertStreamToString(is);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }

    }

    private String postTask(String myurl, String data) throws IOException {
        InputStream is = null;
        // Only display the first 2000 characters of the retrieved
        // web page content.
        int len = 5000;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            Log.d("visitando URL: ",myurl.toString());
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Type", "application/json");
            byte[] outputBytes = data.getBytes("UTF-8");
            OutputStream os = conn.getOutputStream();
            os.write(outputBytes);


            // Starts the query
            //conn.connect();
            int response = conn.getResponseCode();
            is = conn.getInputStream();

            // Convert the InputStream into a string
            //String contentAsString = readIt(is, len);
            String contentAsString = convertStreamToString(is);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }

    }

    public String convertStreamToString(InputStream is) throws IOException
    {
        // To convert the InputStream to String we use the
        // Reader.read(char[] buffer) method. We iterate until the
        // Reader return -1 which means there's no more data to
        // read. We use the StringWriter class to produce the string.
        if (is != null)
        {
            Writer writer = new StringWriter();

            char[] buffer = new char[5120];
            try
            {
                Reader reader = new BufferedReader(
                        new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1)
                {
                    writer.write(buffer, 0, n);
                }
            } finally
            {
                is.close();
            }
            return writer.toString();
        }
        return "";
    }

}



