package dijkstra;

/**
 * Created by DionisioII on 22/12/2015.
 */
public class Nodo {
    private String ID_nodo;
    private float X;
    private float Y;
    private int idMap;

    private boolean scala;
    private boolean uscita;



    public Nodo(String ID,float x, float y, int idMap,boolean scala,boolean uscita) {
        ID_nodo=ID;
        X = x;
        Y = y;
        this.scala=scala;
        this.uscita = uscita;

        this.idMap = idMap;
    }

    public float getX() {
        return X;
    }

    public void setX(float x) {
        X = x;
    }

    public float getY() {
        return Y;
    }

    public String getID_nodo() {
        return ID_nodo;
    }

    public void setID_nodo(String ID_nodo) {
        this.ID_nodo = ID_nodo;
    }

    public void setY(float y) {
        Y = y;
    }

    public int getIdMap() {
        return idMap;
    }

    public void setIdMap(int idMap) {
        this.idMap = idMap;
    }

    public boolean isScala() {
        return scala;
    }

    public void setScala(boolean scala) {
        this.scala = scala;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
       Nodo other = (Nodo) obj;
        if (ID_nodo == null) {
            if (other.ID_nodo != null)
                return false;
        } else if (!ID_nodo.equals(other.ID_nodo))
            return false;
        return true;

    }



    public boolean isUscita() {
        return uscita;
    }

    public void setUscita(boolean uscita) {
        this.uscita = uscita;
    }
}
