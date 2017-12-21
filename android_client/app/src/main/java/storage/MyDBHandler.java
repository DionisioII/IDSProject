package storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import dijkstra.Arco;
import dijkstra.Mappa;
import dijkstra.Nodo;

/**
 * Created by DionisioII on 27/03/2015.
 */
public class MyDBHandler extends SQLiteOpenHelper{

    private static String DB_PATH = "";

    private static final int DATABASE_VERSION = 1;

    private static String DB_NAME = "University.db";

    public static SQLiteDatabase myDataBase;
    private static MyDBHandler myDBHelper = null;

    private final Context myContext;

    private static String TABLE_MAPPE = "Mappe";
    private static String TABLE_NODI = "Nodi";
    private static String TABLE_ARCHI = "Archi";


    public static final String TAG_ID_MAP = "id_map";
    public static final String TAG_NODO_1 = "nodo1";
    public static final String TAG_NODO_2 = "nodo2";
    public static final String TAG_K = "K";
    public static final String TAG_AREA = "area";
    public static final String TAG_LUNGHEZZA = "lunghezza";


    public static final String TAG_ID_NODO = "id_nodo";
    public static final String TAG_CORDX = "cordX";
    public static final String TAG_CORDY = "cordY";
    public static final String TAG_USCITA = "uscita";

    public static final String TAG_WIDTH = "width";
    public static final String TAG_QUOTA = "quota";
    public static final String TAG_MAPPAJPEG = "mappaJpeg";
    public static final String TAG_VERSIONE = "versione";



    /**
     * Constructor Takes and keeps a reference of the passed context in order to
     * access to the application assets and resources.
     *
     * @param context
     */
    public MyDBHandler(Context context) {

        super(context, DB_NAME, null, 1);
        this.myContext = context;
        DB_PATH = myContext.getDatabasePath(DB_NAME).getPath();
        try {
            openDataBase();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            Log.i("ERRORE","errore");
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static MyDBHandler getInstance(Context context) {
        if (myDBHelper == null) {
            myDBHelper = new MyDBHandler(context.getApplicationContext());
        }

        return myDBHelper;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own
     * database.
     * */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if (dbExist) {
            // do nothing - database already exist
        } else {

            // By calling this method and empty database will be created into
            // the default system path
            // of your application so we are gonna be able to overwrite that
            // database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each
     * time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH;
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READONLY
                            | SQLiteDatabase.NO_LOCALIZED_COLLATORS);
            int DB_EXIST_VERSION = PreferenceManager
                    .getDefaultSharedPreferences(myContext).getInt(
                            "DB_VERSION", 0);
            if (DATABASE_VERSION != DB_EXIST_VERSION) {
                checkDB = null;
            }

        } catch (SQLiteException e) {

            // database does't exist yet.

        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException {

        // Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH;

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
        PreferenceManager.getDefaultSharedPreferences(myContext).edit()
                .putInt("DB_VERSION", DATABASE_VERSION).commit();
    }

    public void openDataBase() throws SQLException, IOException {
        createDataBase();
        // Open the database
        String myPath = DB_PATH;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READWRITE
                        | SQLiteDatabase.NO_LOCALIZED_COLLATORS);

    }

    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();
        myDataBase = null;
        myDBHelper = null;

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /*********************************************
     * Method to execute a SQL statemtn and return a custom adapter. Use only to
     * search Station Codes
     *********************************************/
    public Cursor executeSQLStatement(String SQLStatement) {

        Cursor c = null;
        try {
            if (myDataBase != null) {
                c = myDataBase.rawQuery(SQLStatement, new String[] {});
                if (c != null)
                    c.moveToFirst();

            }
        }

        catch (Exception e) {
            e.printStackTrace();

        }

        return c;
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Mappa getMappa(int id_mappa){


        int quota;
        Bitmap mappaJpeg;
        Nodo[] nodi;
        Arco[] archi;


        String query= "SELECT * FROM "+TABLE_MAPPE+" where `quota` = \"" + id_mappa + "\"";

        Cursor c = executeSQLStatement(query);


        quota =c.getInt(c.getColumnIndex("quota"));
        mappaJpeg = getImage(id_mappa);
        nodi = getNodi(quota);

        archi = getArchi(quota,nodi);




        return  new Mappa(quota,archi,nodi,mappaJpeg);
    }

    // Trova tutti i nodi presenti nella mappa in base all'id della mappa
    public Nodo[] getNodi(int id_mappa){

        String query= "SELECT * FROM "+TABLE_NODI + " "; // where `id_map` = \"" + id_mappa+"\"";

        Cursor c = executeSQLStatement(query);
        Nodo[] nodi = new Nodo[c.getCount()];

        int i = 0;
        c.moveToFirst();

        while(!c.isAfterLast() && i<=c.getCount()) {
            if (c.getString(c.getColumnIndex("id_nodo")) != null) {

                nodi[i]=(   new Nodo(
                        c.getString(c.getColumnIndex("id_nodo")),
                        c.getInt(c.getColumnIndex("cordX")),
                        c.getInt(c.getColumnIndex("cordY")),
                        c.getInt(c.getColumnIndex("id_map")),
                        c.getInt(c.getColumnIndex("scala"))!=0, ///getboolean value
                        c.getInt(c.getColumnIndex("uscita"))!=0
                       )
                );
            }
            i++;

            c.moveToNext();
        }
        c.close();

        return nodi;
    }


    public Nodo getNodo(String id_nodo){
        Nodo nodo = null;

        String query= "SELECT * FROM "+TABLE_NODI+" where `id_nodo` = \"" + id_nodo+"\"";

        Cursor c = executeSQLStatement(query);
        //Log.i("Tag: ",id_nodo +" ");

        if(c.moveToFirst()){
            nodo = new Nodo(c.getString(c.getColumnIndex("id_nodo")),
                    c.getInt(c.getColumnIndex("cordX")),
                    c.getInt(c.getColumnIndex("cordY")),
                    c.getInt(c.getColumnIndex("id_map")),
                    c.getInt(c.getColumnIndex("scala"))!=0, ///getboolean value
                    c.getInt(c.getColumnIndex("uscita"))!=0

            );
        }

        c.close();

        return nodo;
    }


    // Trova tutti gli archi presenti nella mappa in base all'id della mappa
    public Arco[] getArchi(int id_mappa,Nodo[] nodi){

        String query= "SELECT * FROM "+TABLE_ARCHI + " ";// where `id_map` = \"" + id_mappa+"\"";

        Cursor c = executeSQLStatement(query);
        Arco[] archi = new Arco[c.getCount()];

        int i = 0;
        c.moveToFirst();
        while(!c.isAfterLast() && i<=c.getCount()){
            if (!c.getString(c.getColumnIndex("nodo1")).equals(null)) {
                archi[i]=(   new Arco(
                        c.getInt(c.getColumnIndex("id_map")),
                        findNodo(c.getString(c.getColumnIndex("nodo1")),nodi),
                        findNodo(c.getString(c.getColumnIndex("nodo2")),nodi),
                        c.getInt(c.getColumnIndex("K")),
                        c.getFloat(c.getColumnIndex("lunghezza"))
                ));
                i++;
            }
        c.moveToNext();

        }
        c.close();

        return archi;
    }

    Nodo findNodo(String id,Nodo[] nodi){
        Nodo nodo = null;
        for(int i =0;i<nodi.length;i++){
            if(nodi[i].getID_nodo().equals(id)){
                nodo=nodi[i];
            }
        }
        return nodo;
    }

    public String[] listaNodi(int piano){
        String[] nodi;
        Cursor c;
        String query;
        if(piano == 0) {
             query = "SELECT * FROM " + TABLE_NODI + " ";
        }
            else{
                 query= "SELECT * FROM "+TABLE_NODI+" where `id_map` = \"" + piano+"\"";
            }
            c = executeSQLStatement(query);
            nodi = new String[c.getCount()+1];
            nodi[0] = " ";
            for(int i =1;!c.isAfterLast() && i<c.getCount()+1;i++) {
                nodi[i]=c.getString(c.getColumnIndex("id_nodo"));
                c.moveToNext();
            }
            return nodi;
        }




        public String[] listaMappe(){
            String[] mappe;
            Cursor c;
            String query="SELECT * FROM "+TABLE_MAPPE+" ";
            c = executeSQLStatement(query);
            mappe = new String[c.getCount()];
            for(int i =0;!c.isAfterLast() && i<c.getCount();i++) {
                mappe[i]=Integer.toString(c.getInt(c.getColumnIndex("quota")));
                c.moveToNext();
            }


            return mappe;
        }



    public void insertImg(int id , Bitmap img, int width) {

        byte[] data = getBitmapAsByteArray(img);

        ContentValues values = new ContentValues();

        //values.put("width", 826);
        values.put("width", width);
        values.put("quota", id);
        values.put("mappaJpeg", data);

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert("Mappe", null, values);
        db.close();
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public Bitmap getImage(int i){

        String qu = "select mappaJpeg from Mappe where quota=" + i ;
        Cursor cur = executeSQLStatement(qu);

        if (cur.moveToFirst()){
            byte[] imgByte = cur.getBlob(0);
            cur.close();
            return BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
        }
        if (cur != null && !cur.isClosed()) {
            cur.close();
        }

        return null ;
    }

    public void restoreK(){
        String qu = "UPDATE"+TABLE_ARCHI+" SET `K` = `lunghezza`";
        Cursor c = executeSQLStatement(qu);
    }


    public void aggiornaArco(String id, String nodo1, String nodo2, Double k)
    {

        String query = "UPDATE "+TABLE_ARCHI+" SET "+TAG_K+"='"+k+"' WHERE "+TAG_ID_MAP+"="+id+" AND "+TAG_NODO_1+"='"+nodo1+"' AND "+TAG_NODO_2+"='"+nodo2+"' ";
        Log.d("QUERYMIA",query);
        Cursor c = executeSQLStatement(query);

    }

    public void aggiornaArco(String id, String nodo1, String nodo2, Double k, Double area, Double lunghezza)
    {
        String query = "UPDATE "+TABLE_ARCHI+" SET "+TAG_K+"='"+k+"', "+TAG_AREA+"='"+area+"', "+TAG_LUNGHEZZA+"='"+lunghezza+"' WHERE "+TAG_ID_MAP+"="+id+" AND "+TAG_NODO_1+"='"+nodo1+"' AND "+TAG_NODO_2+"='"+nodo2+"' ";
        Log.d("QUERYMIA",query);
        Cursor c2 = executeSQLStatement(query);
    }

    public void aggiornaNodo(String id, int cordx, int cordy, int uscita)
    {

        String query = "UPDATE "+TABLE_NODI+" SET "+TAG_CORDX+"='"+cordx+"', "+TAG_CORDX+"='"+cordy+"', "+TAG_USCITA+"='"+uscita+"' WHERE "+TAG_ID_NODO+"='"+id+"' ";

        Log.d("QUERYMIA",query);
        Cursor c2 = executeSQLStatement(query);
    }

    public JSONArray versioniMappe() throws JSONException {
        String[] mappe;
        Cursor c;
        String query="SELECT * FROM "+TABLE_MAPPE+" ";
        c = executeSQLStatement(query);
        JSONArray tuple = new JSONArray();;

        for(int i =0;!c.isAfterLast() && i<c.getCount();i++)
        {
            JSONObject json = new JSONObject();
            json.put(TAG_ID_MAP,c.getInt(c.getColumnIndex(TAG_QUOTA)));
            json.put(TAG_VERSIONE,c.getInt(c.getColumnIndex(TAG_VERSIONE)));
            tuple.put(json);
            c.moveToNext();
        }

        return tuple;
    }

    public void aggiornaImmagine(int id , Bitmap img, int width, int versione) {

        byte[] data = getBitmapAsByteArray(img);

        ContentValues values = new ContentValues();

        values.put(TAG_WIDTH, width);
        values.put(TAG_MAPPAJPEG, data);
        values.put(TAG_VERSIONE, versione);

        String where = TAG_QUOTA+"=?";
        String[] whereArgs = new String[] {String.valueOf(id)};

        SQLiteDatabase db = this.getWritableDatabase();

        db.update(TABLE_MAPPE, values,where,whereArgs);
        db.close();

    }





}