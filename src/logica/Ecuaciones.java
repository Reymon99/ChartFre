package logica;
public class Ecuaciones {
    public static int rango(int Xmin,int Xmax){
        return Xmax-Xmin;
    }
    public static double k(int n){
        return 1+(3.322*Math.log10(n));
    }
    public static double amplitud(int rango,double k){
        return rango/k;
    }
    public static double media(double fiXi,int n){
        return fiXi/n;
    }
    public static double moda(double Lm, double d1,double d2,double d3,double c){
        return Lm+(((d1-d2)/((d1-d2)+(d1-d3)))*c);
    }
    public static double mediana(double Lm,double n,double Fm,double fm,double c){
        return Lm+((((n/2)-Fm)/fm)*c);
    }
    public static double varianza(double XiX2,int n){
        return XiX2/n;
    }
    public static double desviacionEstandarMuestra(double s2){
        return Math.sqrt(s2);
    }
    public static double cuartiles(double Lk,double k,double n,double Fk,double fk,double c){
        return Lk+((((k*(n/4))-Fk)/fk)*c);
    }
    public static double deciles(double Lk,double k,double n,double Fk,double fk,double c){
        return Lk+((((k*(n/10))-Fk)/fk)*c);
    }
    public static double persentiles(double Lk,double k,double n,double Fk,double fk,double c){
        return Lk+((((k*(n/100))-Fk)/fk)*c);
    }
    public static double coeficienteVarianza(double s,double x){
        return (s/x)*100;
    }
    public static double coeficienteSesgo(double XiX3,int n,double s){
        return (XiX3/n)/Math.pow(s,3);
    }
    public static double coeficienteCurtosis(double XiX4,int n,double s){
        return (XiX4/n)/Math.pow(s,4);
    }
}