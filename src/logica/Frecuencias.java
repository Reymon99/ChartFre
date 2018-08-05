package logica;
public class Frecuencias implements Comparable<Frecuencias>{
    private int dato,i;
    public Frecuencias(int dato) {
        this.dato = dato;
        i=1;
    }
    public void incrementar(){
        i++;
    }
    public int getDato() {
        return dato;
    }
    public void setDato(int dato) {
        this.dato = dato;
    }
    public int getI() {
        return i;
    }
    public void setI(int i) {
        this.i = i;
    }
    @Override
    public int compareTo(Frecuencias o) {
        return dato-o.dato;
    }
    @Override
    public String toString() {
        return "dato=" + dato + ", i=" + i ;
    }
}