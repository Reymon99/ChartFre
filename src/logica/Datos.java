package logica;
public class Datos {
    private double intervalo1,intervalo2,fi;
    public Datos(double intervalo1, double intervalo2) {
        this(intervalo1,intervalo2,0);
    }
    public Datos(double intervalo1, double intervalo2, double fi) {
        this.intervalo1 = intervalo1;
        this.intervalo2 = intervalo2;
        this.fi = fi;
    }
    public double getIntervalo1() {
        return intervalo1;
    }
    public void setIntervalo1(double intervalo1) {
        this.intervalo1 = intervalo1;
    }
    public double getIntervalo2() {
        return intervalo2;
    }
    public void setIntervalo2(double intervalo2) {
        this.intervalo2 = intervalo2;
    }
    public double getFi() {
        return fi;
    }
    public void setFi(double fi) {
        this.fi = fi;
    }
    @Override
    public String toString() {
        return "intervalo1=" + intervalo1 + ", intervalo2=" + intervalo2 + ", fi=" + fi ;
    }
}