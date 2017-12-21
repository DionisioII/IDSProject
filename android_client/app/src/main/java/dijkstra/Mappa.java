package dijkstra;

import android.graphics.Bitmap;

import dijkstra.Arco;
import dijkstra.Nodo;

/**
 * Created by DionisioII on 22/12/2015.
 */
public class Mappa {
    private int idMap;
    private Arco[] Archi;
    private Nodo[] Nodi;

    private Bitmap mappaJpeg;

    public Mappa(int idMap, Arco[] archi, Nodo[] nodi) {
        this.idMap = idMap;
        Archi = archi;
        Nodi = nodi;
        mappaJpeg = null;
    }

    public Mappa(int idMap, Arco[] archi, Nodo[] nodi,Bitmap bitmap) {
        this.idMap = idMap;
        Archi = archi;
        Nodi = nodi;
        mappaJpeg = bitmap;
    }

    public int getIdMap() {
        return idMap;
    }

    public void setIdMap(int idMap) {
        this.idMap = idMap;
    }

    public Arco[] getArchi() {
        return Archi;
    }

    public void setArchi(Arco[] archi) {
        Archi = archi;
    }

    public Nodo[] getNodi() {
        return Nodi;
    }

    public void setNodi(Nodo[] nodi) {
        Nodi = nodi;
    }

    public Bitmap getMappaJpeg() {
        return mappaJpeg;
    }

    public void setMappaJpeg(Bitmap mappaJpeg) {
        this.mappaJpeg = mappaJpeg;
    }


}
