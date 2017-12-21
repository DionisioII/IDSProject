package dijkstra;


import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.os.Handler;


import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import auth.LoginActivity;
import storage.LoginUtils;
import storage.MyDBHandler;
import ortigiaenterprises.idsproject.R;
import ui.TouchImageView;
import ui.TouchInterface;
import server.InterfacciaServer;


public class Navigation extends AppCompatActivity {

    private int default_piano=145;

    private TouchImageView mappa;



    private Nodo partenza;

    private Nodo arrivo;

    private Nodo destinazioneTemporanea;

    private Mappa mappaPiano;



    private ArrayList<Arco> percorso1;

    private int rangeonTouchX = 15;

    private int rangeonTouchY = 15;

    //Spinners
    private Spinner positionMapSpinner;
    private String[] arrayPositionMap;
    private ArrayAdapter<String> adapterPositionMap;

    private Spinner destinationMapSpinner;
    private String[] arrayDestinationMap;
    private ArrayAdapter<String> adapterDestinationMap;

    private Spinner positionLocationSpinner;
    private String[] arrayPositionLocation;
    private ArrayAdapter<String> adapterPositionLocation;

    private Spinner destinationLocationSpinner;
    private String[] arrayDestinationLocation;
    private ArrayAdapter<String> adapterDestinationLocation;

    //DB
    MyDBHandler DB;


    //dijstra variables

    private Set<Nodo> settledNodes;
    private Set<Nodo> unSettledNodes;
    private Map<Nodo, Nodo> predecessors;
    private Map<Nodo, Float> distance;




    LinkedList<Nodo> path;
    LinkedList<Nodo> path2;



	//Server
	private InterfacciaServer server;
	public boolean offline=true;
    public boolean loggato=true;
    private int mInterval = 20000; // 30 seconds by default, can be changed later
    private Handler mHandler;

    private boolean emergenza;
    private String token;

    Toolbar toolbar;
    Window window;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
		
		//provo login
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        //provo login

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
		setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);



        server = InterfacciaServer.getInstance(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calcolaPercorso(partenza,arrivo);

            }
        });

        FloatingActionButton qr = (FloatingActionButton) findViewById(R.id.qr);
        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanQR(view);
            }
        });


        positionLocationSpinner = (Spinner) findViewById(R.id.positionLocationSpinner);
        destinationLocationSpinner = (Spinner) findViewById(R.id.destinationLocationSpinner);
        positionMapSpinner = (Spinner) findViewById(R.id.positionMapSpinner);
        destinationMapSpinner = (Spinner) findViewById(R.id.destinationMapSpinner);

        DB = MyDBHandler.getInstance(this);


        mappaPiano = DB.getMappa(default_piano);


        mappa = (TouchImageView) findViewById(R.id.mappaView);

        mappa.setImageBitmap(mappaPiano.getMappaJpeg());


        path= new LinkedList<Nodo>();
        path = new LinkedList<Nodo>();

        mappa.setPercorso(new ArrayList<Arco>(Arrays.asList(mappaPiano.getArchi())));

        emergenza=false;

        mHandler = new Handler();
        startRepeatingTask();
		
		




    }

    @Override
    protected void onStart() {
        super.onStart();
        initSpinners(default_piano);
        percorso1 = new ArrayList<Arco>();

        mappa.setNodi(mappaPiano.getNodi()
        );


        mappa.inizializzaInterfaccia(new TouchInterface() {
            @Override
            public void impostaLuogo(int x, int y, boolean isPartenza) {

                Log.d("tikitaka", x + " " + y);
                Nodo nodo = onTouchLocate(x,y);
                if(nodo != null){
                    if (isPartenza){
                        partenza = nodo;
                        setSpinnerValue(positionLocationSpinner, arrayPositionLocation, partenza.getID_nodo());
                        Toast.makeText(getApplicationContext(),"partenza: "+nodo.getID_nodo(),Toast.LENGTH_SHORT).show();


                    }
                    else {
                        arrivo = nodo;
                        setSpinnerValue(destinationMapSpinner,arrayDestinationMap,Integer.toString(arrivo.getIdMap()));
                        setSpinnerValue(destinationLocationSpinner, arrayDestinationLocation, arrivo.getID_nodo());
                        Toast.makeText(getApplicationContext(),"destinazione: "+nodo.getID_nodo(),Toast.LENGTH_SHORT).show();

                    }

                }
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_navigation, menu);

        //se non Ã¨ loggato e il server Ã¨ online allora mostra la freccia per tornare indietro
        //ed effettuare il login!
        getSupportActionBar().setDisplayHomeAsUpEnabled(!loggato && !offline);
        //NB: quando cambia lo stato di offline CHIAMARE LA SEGUENTE FUNZIONE
        //invalidateOptionsMenu();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId())
        {
            case android.R.id.home:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                //TODO: da cambiare!!!
                server.cercaAggiornamenti(this);
                //server.showAlertDialog(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private Nodo localizzaPosizione(String idNodo){


        for (int i =0; i<mappaPiano.getNodi().length;i++){
            if (mappaPiano.getNodi()[i]!=null && idNodo.equals(mappaPiano.getNodi()[i].getID_nodo()) )
                return mappaPiano.getNodi()[i];
            }
        return null;

    }


    private void changeMap_floor(int piano_id){



        mappaPiano.setMappaJpeg(DB.getImage(piano_id));
        mappaPiano.setIdMap(piano_id);
        mappa.setImageBitmap(mappaPiano.getMappaJpeg());
        mappa.setPiano(piano_id);

        updateMap(piano_id);


    }

    private void updateMap(int piano_id){

        mappa.setPath(null);
        mappa.setPath2(null);


        if(path!=null && path.size()>0)
            mappa.setPath(path.toArray(new Nodo[path.size()]));

        if(path2!=null && path2.size()>0)
            mappa.setPath2(path2.toArray(new Nodo[path2.size()]));




        mappa.invalidate();

    }




    ///////Spinners Section////////////

    private void initSpinners(int piano){
        arrayPositionMap = DB.listaMappe();
        adapterPositionMap = new ArrayAdapter<String>(this,
                R.layout.spinner, arrayPositionMap);
        positionMapSpinner.setAdapter(adapterPositionMap);
        setSpinnerValue(positionMapSpinner, arrayPositionMap, Integer.toString(piano));



        arrayPositionLocation = DB.listaNodi(piano);
        adapterPositionLocation = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.spinner, arrayPositionLocation);
        positionLocationSpinner.setAdapter(adapterPositionLocation);


        arrayDestinationMap = DB.listaMappe();
        //Log.i("TAG",arrayPositionMap[0]);
        adapterDestinationMap = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.spinner, arrayDestinationMap);
        destinationMapSpinner.setAdapter(adapterDestinationMap);
        setSpinnerValue(destinationMapSpinner, arrayDestinationMap, Integer.toString(piano));


        arrayDestinationLocation=DB.listaNodi(piano);
        adapterDestinationLocation = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.spinner, arrayDestinationLocation);
        destinationLocationSpinner.setAdapter(adapterDestinationLocation);

        setSpinnersListers();

    }

    private void setSpinnersListers(){
        positionMapSpinner.setOnItemSelectedListener(positionMapListener);
        positionLocationSpinner.setOnItemSelectedListener(positionLocationListener);
        destinationMapSpinner.setOnItemSelectedListener(destinationMapListener);
        destinationLocationSpinner.setOnItemSelectedListener(destinationLocationListener);
    }


    AdapterView.OnItemSelectedListener positionMapListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            int piano = Integer.parseInt((String) positionMapSpinner.getSelectedItem());
            arrayPositionLocation = DB.listaNodi(piano);
            adapterPositionLocation = new ArrayAdapter<String>(getApplicationContext(),
                    R.layout.spinner, arrayPositionLocation);
            positionLocationSpinner.setAdapter(adapterPositionLocation);
            changeMap_floor(piano);

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    AdapterView.OnItemSelectedListener destinationMapListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            int piano = Integer.parseInt((String) destinationMapSpinner.getSelectedItem());
            arrayDestinationLocation = DB.listaNodi(piano);
            adapterDestinationLocation=new ArrayAdapter<String>(getApplicationContext(),
                    R.layout.spinner, arrayDestinationLocation);
            destinationLocationSpinner.setAdapter(adapterDestinationLocation);

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AdapterView.OnItemSelectedListener positionLocationListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            String idNodo = (String) positionLocationSpinner.getSelectedItem();
            if(!idNodo.equals( " "))
            partenza = localizzaPosizione(idNodo);


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AdapterView.OnItemSelectedListener destinationLocationListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            String idNodo = (String) destinationLocationSpinner.getSelectedItem();
            if(!idNodo .equals( " "))
            arrivo = localizzaPosizione(idNodo);

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };




    private void setSpinnerValue(Spinner spinner,String[] array, String value){
        int index = 0;

        for(int i =0; i<array.length;i++){
            if (value.equals(array[i])) index=i;
        }

        spinner.setSelection(index);

    }







    //////////DIJSTRA   SECTION///////////////////////////////////////////

    public void execute(Nodo source) {
        settledNodes = new HashSet<Nodo>();
        unSettledNodes = new HashSet<Nodo>();
        distance = new HashMap<Nodo, Float>();
        predecessors = new HashMap<Nodo, Nodo>();
        distance.put(source, 0.0f);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            Nodo node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }

    }

    private void findMinimalDistances(Nodo node) {
        ArrayList<Nodo> adjacentNodes = getNeighbors(node);
        for(int i = 0; i< adjacentNodes.size();i++){
            if (getShortestDistance(adjacentNodes.get(i)) > getShortestDistance(node)
                    + getDistance(node, adjacentNodes.get(i))) {

                distance.put(adjacentNodes.get(i), getShortestDistance(node)
                        + getDistance(node, adjacentNodes.get(i)));
                predecessors.put(adjacentNodes.get(i), node);
                unSettledNodes.add(adjacentNodes.get(i));
        }
        }

    }

    private float getDistance(Nodo node, Nodo target) {
        for (Arco arco : mappaPiano.getArchi()) {

            if ((arco.getNodoIniziale()!= null && arco.getNodoIniziale().getID_nodo().equals( node.getID_nodo() )
                    && arco.getNodoFinale().getID_nodo().equals(target.getID_nodo()))

                    ||

                    (arco.getNodoFinale()!=null && arco.getNodoFinale().getID_nodo().equals(node.getID_nodo())
                        && arco.getNodoIniziale()!= null && arco.getNodoIniziale().getID_nodo().equals(target.getID_nodo()))


                    ) {
                return arco.getK();
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private ArrayList<Nodo> getNeighbors(Nodo node) {
        ArrayList<Nodo> neighbors = new ArrayList<Nodo>();
        for(int i =0; i<mappaPiano.getArchi().length;i++){
            if (mappaPiano.getArchi()[i].getNodoIniziale().getID_nodo().equals(node.getID_nodo())
                    && !isSettled(mappaPiano.getArchi()[i].getNodoFinale())) {
                neighbors.add(mappaPiano.getArchi()[i].getNodoFinale());
            }else if

                    (mappaPiano.getArchi()[i].getNodoFinale() != null && mappaPiano.getArchi()[i].getNodoFinale().getID_nodo().equals(node.getID_nodo())
                            && !isSettled(mappaPiano.getArchi()[i].getNodoIniziale())) {
                neighbors.add(mappaPiano.getArchi()[i].getNodoIniziale());
            }
        }

        return neighbors;
    }

    private Nodo getMinimum(Set<Nodo> nodi) {
        Nodo minimum = null;

        for (Nodo nodo : nodi) {
            if (minimum == null) {
                minimum = nodo;
            } else {
                if (getShortestDistance(nodo) < getShortestDistance(minimum)) {
                    minimum = nodo;
                }
            }
        }
        return minimum;
    }

    private boolean isSettled(Nodo nodo) {
        return settledNodes.contains(nodo);
    }

    private float getShortestDistance(Nodo destination) {
        Float d = distance.get(destination);
        if (d == null) {
            return Float.MAX_VALUE;
        } else {
            return d;
        }
    }


    public LinkedList<Nodo> getPath(Nodo target) {
        LinkedList<Nodo> path = new LinkedList<Nodo>();

        Nodo step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }

        path.add(step);

        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);

        for(int i=0;i<path.size();i++){
            int index = path.indexOf(target);
            if(i>index)
                path.remove(i);
        }



        return path;
    }



    public void calcolaPercorso(Nodo partenza,Nodo arrivo){

    if(!emergenza) {
        path2=null;

        if (partenza != null && arrivo != null) {
            path = Dijstra(partenza, arrivo);

            updateMap(mappaPiano.getIdMap());
        } else {
            if (partenza == null)
                Toast.makeText(getApplicationContext(), "Scegliere una partenza", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(), "Scegliere una destinazione", Toast.LENGTH_SHORT).show();
        }
    }
        else { // in caso di emergenza
        if(partenza!= null) {
            trovaUscita();
            updateMap(mappaPiano.getIdMap());
        }
        else
            Toast.makeText(getApplicationContext(),"Presto! Localizzati!!!!",Toast.LENGTH_SHORT).show();
    }

    }

    private LinkedList<Nodo> Dijstra(Nodo partenza,Nodo arrivo){
        if(partenza!=null && arrivo!= null && !partenza.getID_nodo().equals(arrivo.getID_nodo()))
        {
            execute(partenza);

            return  getPath(arrivo);

        }
        return  null;
    }



    public void printArchi(){
        for (Arco arco : mappaPiano.getArchi()){
            if(arco.getNodoFinale().getID_nodo()!= null || arco.getNodoIniziale().getID_nodo()!= null)
            Log.i("Arco:", arco.getNodoIniziale().getID_nodo() +"  "+ arco.getNodoFinale().getID_nodo());
        }
    }


    private Nodo onTouchLocate(int x, int y){
        Nodo result = null;
        for(Nodo i :mappaPiano.getNodi())
            if(i.getIdMap() == mappaPiano.getIdMap()) {

                if ((x >= i.getX() - rangeonTouchX && x <= i.getX() + rangeonTouchX)
                        && (y >= i.getY() - rangeonTouchY && y <= i.getY() + rangeonTouchY)) {
                    result = i;
                }
            }

        return result;
    }
    private int pathSize(LinkedList<Nodo> pat){
        if(pat!= null)
            return pat.size();
        else
            return 1000;
    }


    public void trovaUscita(){
        float lunghezza1=0f,Kp1=0f,Kl1=0f,lunghezza2=0f,Kp2=0f,Kl2=0f,lunghezza3=0f,Kp3=0f,Kl3=0f;
        float Ntr1,Ntr2,Ntr3;

        float originalKvalue1,originalKvalue2;

        if(partenza.isUscita()){
            Toast.makeText(getApplicationContext(), "Ti trovi già ad un uscita", Toast.LENGTH_SHORT).show();
            return;
        }

        LinkedList<Nodo> percorso1=null;

        for(Nodo uscita : mappaPiano.getNodi()){

            if(uscita.isUscita() ) {

                LinkedList<Nodo> fuga = Dijstra(partenza,uscita);
                if(pathSize(fuga)<pathSize(percorso1))
                    percorso1 = fuga;
            }

        }


        LinkedList<Nodo> percorso2 = null;
        LinkedList<Nodo> percorso3 = null;

        if(percorso1 != null){
            arrivo = percorso1.getLast();
            float[] valori =trovaLunghezzaeK(percorso1);
            lunghezza1=valori[0];   Kp1 =valori[1];     Ntr1=percorso1.size();

            originalKvalue1 =findArco(percorso1.get(0),percorso1.get(1)).getK();
            findArco(percorso1.get(0),percorso1.get(1)).setK(1000f);

            percorso2 = Dijstra(partenza,arrivo);


            if(percorso2 != null && !percorso1.equals(percorso2)){
                    valori =trovaLunghezzaeK(percorso2);
                    lunghezza2=valori[0];   Kp2 =valori[1];     Ntr2=percorso2.size();

                    originalKvalue2 =findArco(percorso2.get(0),percorso2.get(1)).getK();
                    Arco posizione=findArco(percorso2.get(0), percorso2.get(1));
                    posizione.setK(1000f);
                    if(token !=null)
                    server.localizzaDefinitivo(Integer.toString(posizione.getIdMap()),posizione.getNodoIniziale().getID_nodo(),posizione.getNodoFinale().getID_nodo(),token);

                    percorso3 = Dijstra(partenza,arrivo);

                    if(percorso3!= null && !percorso3.equals(percorso1) && !percorso3.equals(percorso2)){
                        valori =trovaLunghezzaeK(percorso3);
                        lunghezza3=valori[0];   Kp3 =valori[1];     Ntr3=percorso2.size();
                        float lunghezzaMinima = checkMinimalFloat(lunghezza1,lunghezza2,lunghezza3);

                        float K1 = Kp1/percorso1.size() + (lunghezza1/lunghezzaMinima-1);
                        float K2 = Kp2/percorso2.size() + (lunghezza2/lunghezzaMinima-1);
                        float K3 = Kp3/percorso3.size() + (lunghezza3/lunghezzaMinima-1);

                        path = percorso1;
                        if(K1<K2){
                            if(K1<K3){
                                path =percorso1;
                                if(K2<K3)
                                    path2=percorso2;
                                else
                                    path2=percorso3;
                            }
                        }
                        else{
                            if(K2<K3){
                                path=percorso2;
                                if(K1<K3)
                                    path2=percorso1;
                                else
                                    path2=percorso3;
                            }

                            else{
                                path=percorso3;
                                if(K1<K2)
                                    path2=percorso1;
                                else
                                    path2=percorso2;

                            }
                        }

                        LinkedList<Nodo> p= new LinkedList<Nodo>();
                        for(int i = 0; i<path2.size();i++){
                            p.add(path2.get(i));
                            if(path2.get(i).isUscita())
                                break;
                        }
                        path2=p;



                        findArco(percorso1.get(0),percorso1.get(1)).setK(originalKvalue1);
                        findArco(percorso2.get(0),percorso2.get(1)).setK(originalKvalue2);
                        Toast.makeText(getApplicationContext(), "Due di tre vie di fuga possibili", Toast.LENGTH_SHORT).show();



                    }else{
                        path=percorso1;
                        path2=percorso2;

                        LinkedList<Nodo> p= new LinkedList<Nodo>();
                        for(int i = 0; i<path2.size();i++){
                            p.add(path2.get(i));
                            if(path2.get(i).isUscita())
                                break;
                        }
                        path2=p;




                        findArco(percorso1.get(0), percorso1.get(1)).setK(originalKvalue1);
                        findArco(percorso2.get(0),percorso2.get(1)).setK(originalKvalue2);
                        Toast.makeText(getApplicationContext(),"Due vie di fuga possibili",Toast.LENGTH_SHORT).show();}



            }else{
                path = percorso1;
                path2=null;
                findArco(percorso1.get(0),percorso1.get(1)).setK(originalKvalue1);
                Toast.makeText(getApplicationContext(),"Solo una via di fuga",Toast.LENGTH_SHORT).show();}




        }else{
            path=null;
            path2=null;
            Toast.makeText(getApplicationContext(),"Non ci sono vie di fuga",Toast.LENGTH_SHORT).show();}





    }

    private float[] trovaLunghezzaeK(LinkedList<Nodo>percorso){
        float lunghezza=0,k=0;
        float[] result = new float[2];
        for(int i =0; i<percorso.size()-1;i++){
            Arco arco = findArco(percorso.get(i),percorso.get(i+1));
            lunghezza = lunghezza + arco.getLunghezza();
            k=k+arco.getK();
        }
        result[0] = lunghezza;
        result[1] = k;

        return result;

    }


    Arco findArco(Nodo nodo1,Nodo nodo2){
        Arco result = null;
        for (Arco arco : mappaPiano.getArchi()) {

            if ((arco.getNodoIniziale().getID_nodo().equals(nodo1.getID_nodo())
                    && arco.getNodoFinale().getID_nodo().equals(nodo2.getID_nodo()))

                    ||

                    (arco.getNodoFinale().getID_nodo().equals(nodo1.getID_nodo())
                            && arco.getNodoIniziale().getID_nodo().equals(nodo2.getID_nodo()))


                    ) {
                result = arco;
            }
        }
        return result;
    }


    private float checkMinimalFloat(float n1,float n2,float n3){
        float minimal = n1;
        if(n2<minimal)
            minimal =n2;
        if(n3<minimal)
            minimal = n3;
        return minimal;
    }


    //////SERVER SECTION////////////////////////////////
    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                //updateStatus(); //this function can change value of mInterval.
                verificaConnessione();
            } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }

    private void verificaConnessione()
    {
        offline = !server.checkConnection();
        if (!offline)
        {

            server.getEmergenza();

            token = LoginUtils.getFromPrefs(getApplicationContext(),LoginUtils.PREFS_LOGIN_TOKEN_KEY, null);
            if (token==null)
            {
                loggato=false;
            }
            else
            {
                loggato=true;
            }

        }

        if (server.EMERGENZA==true)
        {
            server.getK();
            mappaPiano = DB.getMappa(145);
            // TODO: AGGIUSTARE!!!!!!!!!!!!!!
            if (toolbar !=null && !emergenza )
            {
                toolbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFF4444")));
                window.setStatusBarColor(this.getResources().getColor(R.color.darkred));
                emergenza=true;
                showNotification();
                Toast.makeText(getApplicationContext(),"C'è un'emergenza!!",Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            if (toolbar !=null && emergenza)
            {
                toolbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3F51B5")));
                window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
                emergenza=false;
                cancelNotification();
                DB.restoreK();
                mappaPiano = DB.getMappa(145);
                Toast.makeText(getApplicationContext(),"Emergenza terminata\nTutto a posto :)",Toast.LENGTH_SHORT).show();
            }

        }
        invalidateOptionsMenu();

    }


    public void scanQR(View view) {
        new IntentIntegrator(this).setCaptureActivity(QRCaptureActivity.class).initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                //Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("MainActivity", "Scanned");
                //Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_SHORT).show();
                String stringaNodo = parsaQR(result.getContents());
                //Toast.makeText(this, "stringaNodo: " + stringaNodo, Toast.LENGTH_SHORT).show();
                if (stringaNodo != null)
                {
                    partenza = localizzaPosizione(stringaNodo);
                    if (partenza != null) {
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        setSpinnerValue(positionMapSpinner, arrayPositionMap, Integer.toString(partenza.getIdMap()));
                                        new android.os.Handler().postDelayed(
                                                new Runnable() {
                                                    public void run() {
                                                        setSpinnerValue(positionLocationSpinner, arrayPositionLocation, partenza.getID_nodo());
                                                    }
                                                }, 100);
                                    }
                                }, 100);
                    }
                }

            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private String parsaQR(String contenutoQR)
    {
        try
        {
            JSONObject obj = new JSONObject(contenutoQR);
            return(String) obj.get("Nodo");

        }
        catch (Throwable t)
        {
            Toast.makeText(this, "QR code non corretto!", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    private void showNotification()
    {
        // Set the icon, scrolling text and timestamp
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        //per nasconderla
        //builder.setAutoCancel(true);
        builder.setContentTitle("EMERGENZA!");
        builder.setTicker("C'e' un'emergenza in corso!!");
        builder.setSmallIcon(R.drawable.ic_red_alert);
        builder.setPriority(Notification.PRIORITY_HIGH);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);
        long[] pattern = {500,500,500};
        builder.setVibrate(pattern);
        builder.setStyle(new NotificationCompat.InboxStyle());
        builder.setColor(getResources().getColor(R.color.red));
        builder.setOngoing(true);
        Notification notification = builder.build();
        NotificationManager notificationManger =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManger.notify(01, notification);
    }

    public void cancelNotification()
    {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
        nMgr.cancelAll();
    }







}
