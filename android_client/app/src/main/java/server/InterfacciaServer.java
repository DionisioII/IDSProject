package server;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Iterator;


//Debug:
import android.util.Log;

import storage.MyDBHandler;
import ortigiaenterprises.idsproject.R;

/**
 * Created by domenico on 11/05/16.
 */
public class InterfacciaServer {

    private static InterfacciaServer interfacciaServer = null;
    private static String serverProtocol = "http";
    //private static String serverIP="192.168.1.10";
    //private static String serverIP="192.168.43.2:8000/";
    private static String serverIP = "172.23.166.217";
    //private static String serverIP = "192.168.150.10";
    private static int serverPort=8000;
    private static String serverAddress=serverProtocol+"://"+serverIP+":"+serverPort+"/";


    private static String loginEndpoint = "login";
    private static String tokenEndpoint = "token";
    private static String registraEndpoint = "registra";
    private static String getKEndpoint = "getK";
    private static String localizzaEndpoint = "localizza";
    private static String emergenzaEndpoint= "getStato";
    private static String aggiornamentiEndpoint= "aggiornamenti";
    private static String aggiornaMappa = "aggiorna/";

    public boolean EMERGENZA = false;



    private Context mContext;
    private MyDBHandler DB;




    public InterfacciaServer(Context context)
    {
        this.mContext = context;
        DB = MyDBHandler.getInstance(context);
    }

    public static InterfacciaServer getInstance(Context context)
    {
        if (interfacciaServer == null)
            interfacciaServer = new InterfacciaServer(context.getApplicationContext());

        return interfacciaServer;
    }



    public boolean pingServer()
    {
        Runtime runtime = Runtime.getRuntime();
        try
        {
            // -c NUMEROPACCHETTI -w DEADLINE in secondi
            Process proc = runtime.exec("ping -c 1 -w 2 "+serverAddress);
            try
            {
                proc.waitFor();
                int exit = proc.exitValue();
                if (exit == 0)
                    return true;
            }
            catch(InterruptedException e){}
        }
        catch (IOException e){}
        return false;
    }

    public boolean checkServer()
    {

        try
        {
            SocketAddress sockaddr = new InetSocketAddress(serverIP, serverPort);
            // Create an unbound socket
            Socket sock = new Socket();
            // This method will block no more than timeoutMs.
            // If the timeout occurs, SocketTimeoutException is thrown.
            int timeoutMs = 2000;   // 2 seconds
            sock.connect(sockaddr, timeoutMs);
            return true;
        }catch(Exception e){}

        return false;

    }

    //PROBLEMA: SU WINDOWS SONO DISABILITATI DI DEFAULT SIA IL PING CHE I SOCKET!

    public boolean checkConnection()
    {
        ConnectivityManager connMgr = (ConnectivityManager)
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;

        //Toast.makeText(mContext, "Connessione non disponibile.",
                //Toast.LENGTH_SHORT).show();
        return false;
    }



    public void getK()
    {

        if (checkConnection())
        {

            ServerTask asyncTask =new ServerTask(new AsyncResponse() {

                @Override
                public void processFinish(String output)
                {
                    try
                    {
                        parseKUpdates(output);
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            });
            asyncTask.execute(serverAddress+getKEndpoint);
        }

    }

    public ArrayList checkIntegrity(String data)
    {
        ArrayList<Object> ritorno = new ArrayList<Object>();
        if (data != null)
        {
            if (data.equals("Impossibile ricevere la risorsa. L'URL potrebbe non essere valido o il server è offline"))
            {
                ritorno.add(0,true);
                ritorno.add(1,"OFF");
                return ritorno;
            }

            JSONObject risposta;
            try
            {
                risposta = new JSONObject(data);
                if (risposta.has("stato")&& !risposta.isNull("stato") && risposta.has("risultato") && !risposta.isNull("risultato"))
                {
                    String stato = (String) risposta.get("stato");
                    try{
                        JSONArray jsonArray = (JSONArray) risposta.get("risultato");
                        if (stato.equals("OK") || stato.equals("KO"))
                        {
                            ritorno.add(0,true);
                            ritorno.add(1,stato);
                            ritorno.add(2,jsonArray);
                            return ritorno;
                        }
                    }catch(Exception e)
                    {
                        if (stato.equals("OK") || stato.equals("KO"))
                        {
                            ritorno.add(0,true);
                            ritorno.add(1,stato);
                            ritorno.add(2,null);
                            return ritorno;
                        }
                    }

                }

            }
            catch (JSONException e) {}
        }
        ritorno.add(0,false);
        return ritorno;
    }

    public void parseKUpdates(String data) throws JSONException
    {
        ArrayList<Object> check = checkIntegrity(data);
        if ((boolean)check.get(0) && ((String)check.get(1)).equals("OK"))
        {
            JSONArray tuple = (JSONArray) check.get(2);
            for (int i = 0; i < tuple.length(); i++)
            {
                JSONObject arco = tuple.getJSONObject(i);
                DB.aggiornaArco(arco.getString(DB.TAG_ID_MAP),arco.getString(DB.TAG_NODO_1),arco.getString(DB.TAG_NODO_2),arco.getDouble(DB.TAG_K));
            }
            //Toast.makeText(mContext, "K aggiornati!", Toast.LENGTH_SHORT).show();
        }
    }




    public void localizzaDefinitivo(String id_map, String nodo1, String nodo2, String token)
    {
        JSONObject json = new JSONObject();
        try {
            json.put(DB.TAG_ID_MAP, id_map);
            json.put(DB.TAG_NODO_1, nodo1);
            json.put(DB.TAG_NODO_2, nodo2);
            json.put("token",token);
            //Toast.makeText(mContext,"json da passare al server: "+json.toString(),Toast.LENGTH_SHORT).show();

            if (checkConnection())
            {
                ServerTask asyncTask = new ServerTask(new AsyncResponse()
                {
                    @Override
                    public void processFinish(String output)
                    {

                        //Toast.makeText(mContext, output,Toast.LENGTH_SHORT).show();
                        /*try
                        {
                            parseKUpdates(output);
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }*/

                    }
                });
                String contenutoPost = json.toString();
                asyncTask.execute(serverAddress+localizzaEndpoint, contenutoPost);


            }
            else
            {
                //Toast.makeText(mContext, "Connessione non disponibile.",
                        //Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String autentica(String username, String password)
    {
        JSONObject json = new JSONObject();
        try {
            json.put("email", username);
            json.put("password", password);

            JSONObject risposta;
            try {
                risposta = new JSONObject(postBloccante(json, serverAddress+loginEndpoint));
            } catch (JSONException e) {
                e.printStackTrace();
                return "errore";
            }

            String esito;
            try {
                esito = (String) risposta.get("token");
            } catch (JSONException e) {
                return "non valido";
            }

            return esito;

        } catch (JSONException e) {
            e.printStackTrace();
            return "errore";
        }
    }


    public String autenticaToken(String token)
    {
        JSONObject json = new JSONObject();
        try {
            json.put("token", token);


            JSONObject risposta;
            try {
                risposta = new JSONObject(postBloccante(json, serverAddress+tokenEndpoint));
            } catch (JSONException e) {
                e.printStackTrace();
                return "errore";
            }

            String esito;
            try {
                esito = (String) risposta.get("token");
            } catch (JSONException e) {
                return "non valido";
            }

            return esito;

        } catch (JSONException e) {
            e.printStackTrace();
            return "errore";
        }
    }


    public String registra(String nome, String cognome, String email, String password)
    {
        JSONObject json = new JSONObject();
        try {
            json.put("nome", nome);
            json.put("cognome", cognome);
            json.put("email", email);
            json.put("password", password);
            Log.d("sto mandando json: ", json.toString());
            return postBloccante(json, serverAddress+registraEndpoint);
        } catch (JSONException e) {
            e.printStackTrace();
            return "errore";
        }
    }

    public String postBloccante(JSONObject json, String serverAddressPOST)
    {
        if (checkConnection())
        {
            ServerTask asyncTask = new ServerTask(new AsyncResponse() {@Override public void processFinish(String output) {}});

            asyncTask.execute(serverAddressPOST, json.toString());
            String output;

            try {
                output = asyncTask.get();
            } catch (Exception e) {
                e.printStackTrace();
                return "errore";
            }


            if (output.equals("Impossibile ricevere la risorsa. L'URL potrebbe non essere valido o il server è offline"))
                return "offline";


            Log.d("sto rispendendo json: ", output);
            return output;

        } else {
            return "offline";
        }
    }

    public void getEmergenza()
    {
        if (checkConnection())
        {

            ServerTask asyncTask =new ServerTask(new AsyncResponse() {

                @Override
                public void processFinish(String output) {
                    //Toast.makeText(mContext, output,
                      //      Toast.LENGTH_SHORT).show();


                    try {
                        JSONObject risposta = new JSONObject(output);
                        String esito = (String) risposta.get("stato");
                        if (esito.equals("OK"))
                        {
                            //if (EMERGENZA)
                                //Toast.makeText(mContext,"Emergenza terminata\nTutto a posto :)",Toast.LENGTH_SHORT).show();
                            EMERGENZA=false;
                        }
                        else if (esito.equals("KO"))
                        {
                            //if (!EMERGENZA)
                                //Toast.makeText(mContext,"C'è un'emergenza!!",Toast.LENGTH_SHORT).show();
                            EMERGENZA=true;
                        }
                    } catch (JSONException e) {e.printStackTrace();}

                }
            });
            asyncTask.execute(serverAddress+emergenzaEndpoint);

        }
    }

    public void cercaAggiornamenti(final Activity activity)
    {

        if (checkConnection())
        {
            JSONArray versioniMappe;
            //
            try
            {
                versioniMappe = DB.versioniMappe();
            } catch (JSONException e) {e.printStackTrace();return;}

            //Toast.makeText(mContext,"json da passare al server: "+versioniMappe.toString(),Toast.LENGTH_SHORT).show();


            ServerTask asyncTask = new ServerTask(new AsyncResponse()
            {
                @Override
                public void processFinish(String output)
                {

                    //Toast.makeText(mContext, output,Toast.LENGTH_SHORT).show();

                    //TODO: FARE IL PARSING E AGGIORNARE IL DB!!!
                    final ArrayList<Object> check = checkIntegrity(output);
                    if (activity !=null)
                    {
                        if ((boolean) check.get(0))
                        {
                            if (((String)check.get(1)).equals("OK"))
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.MyAlertDialogStyle);
                                builder.setTitle("Aggiornamenti trovati!");
                                builder.setMessage("Si vuole procedere con l'aggiornamento delle mappe?\nNB: sara' necessario riavviare l'applicazione al termine della procedura.");
                                builder.setPositiveButton("Si",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                JSONArray tuple = (JSONArray) check.get(2);
                                                for (int i = 0; i < tuple.length(); i++) {
                                                    try {
                                                        JSONObject obj = tuple.getJSONObject(i);
                                                        //JSONObject row = risposta.getJSONObject(i);
                                                        Iterator keys = obj.keys();
                                                        while (keys.hasNext()) {
                                                            // loop to get the dynamic key
                                                            String currentDynamicKey = (String) keys.next();

                                                            //prendere il JSONArray!
                                                            String currentDynamicValue = obj.get(currentDynamicKey).toString();
                                                            JSONArray array = (JSONArray) obj.get(currentDynamicKey);
                                                            switch (currentDynamicKey) {
                                                                case "archi":
                                                                    //Toast.makeText(mContext, "Archi: "+currentDynamicValue, Toast.LENGTH_SHORT).show();
                                                                    //questo è un JSONARRAY!!! ciclarlo come viene fatto con le tuple!
                                                                    for (int j = 0; j < array.length(); j++) {
                                                                        JSONObject arco = array.getJSONObject(j);


                                                                        DB.aggiornaArco(arco.getString(DB.TAG_ID_MAP), arco.getString(DB.TAG_NODO_1), arco.getString(DB.TAG_NODO_2), arco.getDouble(DB.TAG_K), arco.getDouble(DB.TAG_AREA), arco.getDouble(DB.TAG_LUNGHEZZA));

                                                                    }


                                                                    break;
                                                                case "nodi":
                                                                    //Toast.makeText(mContext, "Nodi: "+currentDynamicValue, Toast.LENGTH_SHORT).show();


                                                                    for (int j = 0; j < array.length(); j++) {
                                                                        JSONObject nodo = array.getJSONObject(j);
                                                                        DB.aggiornaNodo(nodo.getString(DB.TAG_ID_NODO), nodo.getInt(DB.TAG_CORDX), nodo.getInt(DB.TAG_CORDY), nodo.getInt(DB.TAG_USCITA));
                                                                    }

                                                                    break;
                                                                case "mappe":
                                                                    //Toast.makeText(mContext, "Mappe: "+currentDynamicValue, Toast.LENGTH_SHORT).show();

                                                                    for (int j = 0; j < array.length(); j++) {
                                                                        JSONObject mappa = array.getJSONObject(j);
                                                                        getImage(mappa.getInt(DB.TAG_ID_MAP), mappa.getInt(DB.TAG_VERSIONE));
                                                                    }

                                                                    break;
                                                            }
                                                        }
                                                        //Toast.makeText(mContext, "Aggiornamento terminato!", Toast.LENGTH_SHORT).show();

                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }


                                                }


                                                dialog.cancel();
                                            }
                                        });
                                builder.setNegativeButton("No, grazie", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                                builder.show();

                            }
                            else if (((String)check.get(1)).equals("KO"))
                            {
                                showAlertDialog(activity, "Non ci sono aggiornamenti","Non sono stati trovati aggiornamenti.");
                            }
                            else if (((String)check.get(1)).equals("OFF"))
                            {
                                showAlertDialog(activity, "Server offline!","Non e' stato possibile raggiungere il server; riprovare più tardi.");
                            }
                        }
                        else
                        {
                            showAlertDialog(activity, "Server offline!","Non e' stato possibile raggiungere il server; riprovare più tardi.");
                        }

                    }
                }
            });
            String contenutoPost = versioniMappe.toString();
            asyncTask.execute(serverAddress+aggiornamentiEndpoint, contenutoPost);


        }
        else
        {
            Toast.makeText(mContext, "Connessione non disponibile.",
                    Toast.LENGTH_SHORT).show();
        }


    }

    public void getImage(final int quota, final int versione)
    {

        //asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,eserverAddress+quota);

        DownloadImageTask asyncTask =new DownloadImageTask(new ImageResponse() {

            @Override
            public void saveImage(Bitmap immagine)
            {
                if (immagine!=null){
                    DB.aggiornaImmagine(quota, immagine, 826, versione);
                }
            }
        });
        asyncTask.execute(serverAddress+aggiornaMappa+quota);
        Toast.makeText(mContext, "Aggiornata mappa: "+quota, Toast.LENGTH_SHORT).show();

    }

    public void showAlertDialog(Activity activity, String titolo, String messaggio)
    {
        if (activity!=null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.MyAlertDialogStyle);
            builder.setTitle(titolo);
            builder.setMessage(messaggio);
            builder.setNeutralButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            builder.show();
        }
    }

}
