package dijkstra;

/**
 * Created by DionisioII on 22/12/2015.
 */
public class Arco {
    private int idMap;

    private Nodo nodoIniziale;
    private Nodo nodoFinale;

    private float K;

    private float Peso;
    private float V;
    private float Plos;
    private float C;

    public float getLunghezza() {
        return lunghezza;
    }

    public void setLunghezza(float lunghezza) {
        this.lunghezza = lunghezza;
    }

    private float lunghezza;

    public Arco(int idMap, Nodo nodoIniziale, Nodo nodoFinale, float K) {
        this.idMap = idMap;
        this.nodoIniziale = nodoIniziale;
        this.nodoFinale = nodoFinale;
        this.K=K;

    }

    public Arco(int idMap, Nodo nodoIniziale, Nodo nodoFinale,float K,float lunghezza) {
        this.idMap = idMap;
        this.nodoIniziale = nodoIniziale;
        this.nodoFinale = nodoFinale;
        this.K=K;
        this.lunghezza = lunghezza;
    }

    public int getIdMap() {
        return idMap;
    }

    public void setIdMap(int idMap) {
        this.idMap = idMap;
    }

    public Nodo getNodoIniziale() {
        return nodoIniziale;
    }

    public void setNodoIniziale(Nodo nodoIniziale) {
        this.nodoIniziale = nodoIniziale;
    }

    public Nodo getNodoFinale() {
        return nodoFinale;
    }

    public void setNodoFinale(Nodo nodoFinale) {
        this.nodoFinale = nodoFinale;
    }

    public float getPeso() {
        return Peso;
    }

    public void setPeso(float peso) {
        Peso = peso;
    }

    public float getV() {
        return V;
    }

    public void setV(float v) {
        V = v;
    }

    public float getK() {
        return K;
    }

    public void setK(float k) {
        K = k;
    }
}
