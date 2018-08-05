package logica;
import java.util.ArrayList;
import java.util.TreeSet;
public class Acciones {
    private ArrayList<Datos> list;
    private int R,Xmin,Xmax;
    private double fiXi,XiX2,fin,n,XiX,K,A;
    public Acciones(){
        this(null);
    }
    public Acciones(ArrayList<Datos> list) {
        this.list = list;
        R=Xmin=Xmax=0;
        fiXi=XiX2=fin=n=XiX=K=A=0.0;
    }
    public ArrayList<Double> Fi(){
         ArrayList<Double> list= new ArrayList<Double>();
         int i=0;
         n=0.0;
         while ((i++)!=this.list.size())
             list.add((n+=this.list.get(i-1).getFi()));
         return list;
    }
    public ArrayList<Double> fin(){
        ArrayList<Double> list=new ArrayList<Double>();
        int i=0;
        fin=0.0;
        while ((i++)!=this.list.size()){
            fin+=Double.valueOf(this.list.get(i-1).getFi()/n);
            list.add(Double.valueOf(this.list.get(i-1).getFi()/n));
        }
        return list;
    }
    public ArrayList<Double> Fin(){
        ArrayList<Double> list=new ArrayList<Double>(),Fi=Fi();
        int i=0;
        while ((i++)!=this.list.size()) {
            list.add(Double.valueOf(Fi.get(i-1)/n));
        }
        return list;
    }
    public ArrayList<Double> Xi(){
        ArrayList<Double> list=new ArrayList<Double>();
        int i=0;
        while ((i++)!=this.list.size()) {
            list.add(Double.valueOf((this.list.get(i-1).getIntervalo1()+this.list.get(i-1).getIntervalo2())/2.0));
        }
        return list;
    }
    public ArrayList<Double> fiXi(){
        ArrayList<Double> list=new ArrayList<Double>(),Xi=Xi();
        fiXi = 0.0;
        int i=0;
        while ((i++)!=this.list.size()) {
            fiXi+=this.list.get(i-1).getFi()*Xi.get(i-1);
            list.add(this.list.get(i-1).getFi()*Xi.get(i-1));
        }
        return list;
    }
    public ArrayList<Double> XiX(){
        ArrayList<Double> list=new ArrayList<Double>(),Xi=Xi();
        int i=0;
        XiX=0.0;
        while ((i++)!=Xi.size()){
            XiX+=Xi.get(i-1)-Ecuaciones.media(fiXi,(int)n);
            list.add(Xi.get(i-1)-Ecuaciones.media(fiXi,(int)n));
        }
        return list;
    }
    public ArrayList<Double> XiX2(){
        ArrayList<Double> list=new ArrayList<Double>(),XiX=XiX();
        XiX2 = 0.0;
        int i=0;
        while ((i++)!=XiX.size()){
            XiX2+=Math.pow(XiX.get(i-1),2);
            list.add(Math.pow(XiX.get(i-1),2));
        }
        return list;
    }
    public ArrayList<Datos> intervalo(String n) throws NullPointerException{
        TreeSet<Frecuencias> set=frecuencias(n);
        this.n=n.split(",").length;
        Xmin=set.first().getDato();
        Xmax=set.last().getDato();
        R=Ecuaciones.rango(Xmin,Xmax);
        K=Ecuaciones.k((int)this.n);
        A=Ecuaciones.amplitud(R,K);
        return fillFrecuencia(fillTable(true,true,set.first().getDato(),set.last().getDato()),set);
    }
    private TreeSet<Frecuencias> frecuencias(String n){
        String s[]=n.split(",");
        TreeSet<Frecuencias> set=new TreeSet<>();
        for (int i = 0; i < s.length; i++) {
            if (set.isEmpty()) {
                set.add(new Frecuencias(Integer.parseInt(s[i])));
            } else{
                if (set.contains(new Frecuencias(Integer.parseInt(s[i])))) {
                    set.ceiling(new Frecuencias(Integer.parseInt(s[i]))).incrementar();
                } else {
                    set.add(new Frecuencias(Integer.parseInt(s[i])));
                }
            }
        }
        return set;
    }
    public ArrayList<Datos> fillTable(boolean b,boolean b1,int i1,int i2) throws NullPointerException{
        double k=(!b)?Math.ceil(K):Math.floor(K);
        double a=(!b1)?Math.ceil(A):Math.floor(A);
        ArrayList<Datos> list=new ArrayList<>();
        Datos datos;
        for (int i = 0; i < k; i++) {
            if (list.isEmpty()) datos=new Datos(i1,i1+a);
            else datos=new Datos(list.get(i-1).getIntervalo2()+1,list.get(i-1).getIntervalo2()+1+a);
            list.add(datos);
            if (list.get(i).getIntervalo2()>=i2) break;
        }
        if (list.size()==k && list.get(list.size()-1).getIntervalo2()>=i2) return list;
        else{
            if (b && b1) return fillTable(true,false,i1,i2);
            else if (b && !b1) return fillTable(false,true,i1,i2);
            else if (!b && b1) return fillTable(false,false,i1,i2);
            else return null;
        }
    }
    private ArrayList<Datos> fillFrecuencia(ArrayList<Datos> list,TreeSet<Frecuencias> set) throws NullPointerException{
        try{
            int n=0;
            for (int i = 0; i < list.size(); i++) {
                for (Frecuencias fr:set) if (fr.getDato()>=list.get(i).getIntervalo1() && fr.getDato()<=list.get(i).getIntervalo2()) n+=fr.getI();
                list.get(i).setFi(n);
                n=0;
            }
            return list;
        }catch (NullPointerException e){
            return null;
        }
    }
    public ArrayList<Datos> getList() {
        return list;
    }
    public double fiXiX3(){
        double n=0.0;
        int i=0;
        ArrayList<Double> d=XiX();
        while ((i++)!=list.size()) n+=list.get(i-1).getFi()*Math.pow(d.get(i-1),3);
        return n;
    }
    public double fiXiX4(){
        double n=0.0;
        int i=0;
        ArrayList<Double> d=XiX();
        while ((i++)!=list.size()) n+=list.get(i-1).getFi()*Math.pow(d.get(i-1),4);
        return n;
    }
    public double Lm(){
        double Lm=list.get(0).getFi();
        for (int i = 1; i < list.size(); i++) if (list.get(i).getFi()>Lm) Lm=list.get(i).getFi();
        return Lm;
    }
    private double LmMediana(){
        ArrayList<Double> Fi=Fi();
        for (int i = 0; i < Fi.size(); i++) if (Fi.get(i)>=(n/2)) return Fi.get(i);
        return 0;
    }
    private double LmCuartil(int i){
        ArrayList<Double> Fi=Fi();
        for (int j = 0; j < Fi.size(); j++) if (Fi.get(j)>=(i*(n/4))) return Fi.get(j);
        return 0;
    }
    private double LmDecentil(int i){
        ArrayList<Double> Fi=Fi();
        for (int j = 0; j < Fi.size(); j++) if (Fi.get(j)>=(i*(n/10))) return Fi.get(j);
        return 0;
    }
    private double LmPercentil(int i){
        ArrayList<Double> Fi=Fi();
        for (int j = 0; j < Fi.size(); j++) if (Fi.get(j)>=(i*(n/100))) return Fi.get(j);
        return 0;
    }
    public int posLm(){
        int i;
        for (i = 0; i < list.size(); i++) if (list.get(i).getFi()==Lm()) return i;
        return -1;
    }
    public int posLmMediana(){
        ArrayList<Double> Fi=Fi();
        for (int i = 0; i < Fi.size(); i++) if (Fi.get(i)==LmMediana()) return i;
        return -1;
    }
    public int posLmCuartil(int n){
        ArrayList<Double> Fi=Fi();
        for (int i = 0; i < Fi.size(); i++) if (Fi.get(i)==LmCuartil(n)) return i;
        return -1;
    }
    public int posLmDecentil(int n){
        ArrayList<Double> Fi=Fi();
        for (int i = 0; i < Fi.size(); i++) if (Fi.get(i)==LmDecentil(n)) return i;
        return -1;
    }
    public int posLmPercentil(int n){
        ArrayList<Double> Fi=Fi();
        for (int i = 0; i < Fi.size(); i++) if (Fi.get(i)==LmPercentil(n)) return i;
        return -1;
    }
    public void setList(ArrayList<Datos> list) {
        this.list = list;
    }
    public double getN() {
        return n;
    }
    public void setN(double n) {
        this.n = n;
    }
    public double getFiXi() {
        return fiXi;
    }
    public void setFiXi(double fiXi) {
        this.fiXi = fiXi;
    }
    public double getXiX2() {
        return XiX2;
    }
    public void setXiX2(double xiX2) {
        XiX2 = xiX2;
    }
    public double getFin() {
        return fin;
    }
    public void setFin(double fin) {
        this.fin = fin;
    }
    public double getXiX() {
        return XiX;
    }
    public void setXiX(double xiX) {
        XiX = xiX;
    }
    public int getR() {
        return R;
    }
    public void setR(int r) {
        R = r;
    }
    public double getK() {
        return K;
    }
    public void setK(double k) {
        K = k;
    }
    public double getA() {
        return A;
    }
    public void setA(double a) {
        A = a;
    }
    public int getXmin() {
        return Xmin;
    }
    public void setXmin(int xmin) {
        Xmin = xmin;
    }
    public int getXmax() {
        return Xmax;
    }
    public void setXmax(int xmax) {
        Xmax = xmax;
    }
}