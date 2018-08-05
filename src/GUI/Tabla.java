package GUI;
import com.placeholder.PlaceHolder;
import logica.Acciones;
import logica.Datos;
import logica.Ecuaciones;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
public class Tabla extends JPanel implements ItemListener{
    private int i,guion,fllenar;
    private JButton add,del,fill,graph,clean,ing,aceptar;
    private JTable table;
    private DefaultTableModel model;
    private ArrayList<JComponent> components;
    private JComboBox cuartil,decentil,percentil;
    private Acciones acciones;
    private DecimalFormat format;
    private JDialog dialog;
    private JTextArea area;
    private PlaceHolder holder;
    private DefaultCategoryDataset ojivaSet,histogramaSet,poligonoSet;
    private JFrame frv;
    public Tabla(JFrame frv){
        this.frv=frv;
        guion=i=fllenar=0;
        format=new DecimalFormat("######.##");
        acciones=new Acciones();
        setLayout(new GridBagLayout());
        init();
    }
    private void init() {
        JLabel label=new JLabel("Tabla de Frecuencias - ChartFre",JLabel.CENTER);
        label.setFont(new Font("Arial",Font.BOLD,26));
        model=new Model();
        model.setColumnIdentifiers(new String[]{"","Intervalo","<html>f<SUB>i</SUB></html>","<html>Fi<SUB>i</SUB><html>","<html>f<SUB>i</SUB> / n</html>","<html>F<SUB>i</SUB> / n</html>","<html>X<SUB>i</SUB></html>","<html>f<SUB>i</SUB> X<SUB>i</SUB></html>","<html>X<SUB>i</SUB> - X</html>","<html>(X<SUB>i</SUB> - X)<sup>2</sup>"});
        model.setRowCount(3);
        model.setValueAt(String.valueOf(++i),i-1,0);
        model.setValueAt(String.valueOf(++i),i-1,0);
        model.setValueAt(String.valueOf(++i),i-1,0);
        table=new Table();
        table.setDragEnabled(false);
        table.setModel(model);
        TableColumn tcol=table.getColumn("");
        tcol.setPreferredWidth(30);
        tcol.setMinWidth(30);
        tcol.setMaxWidth(30);
        table.getColumnModel().getColumn(0).setCellRenderer(table.getTableHeader().getDefaultRenderer());
        table.getColumnModel().getColumn(1).setPreferredWidth(120);
        for (int j = 2; j < 10; j++) table.getColumnModel().getColumn(j).setCellRenderer(new RenderRight());
        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar()!='-' && e.getKeyChar()!='1' && e.getKeyChar()!='2' && e.getKeyChar()!='3' && e.getKeyChar()!='4' && e.getKeyChar()!='5' && e.getKeyChar()!='6' && e.getKeyChar()!='7' && e.getKeyChar()!='8' && e.getKeyChar()!='9' && e.getKeyChar()!='0'){
                    getToolkit();
                    e.consume();
                }
            }
        });
        JScrollPane pane=new JScrollPane(table);
        JPanel panel=new JPanel(new GridBagLayout());
        Constrains.addComp(pane,panel,0,1,2,1,1,1,5,5,5,5,GridBagConstraints.CENTER,GridBagConstraints.BOTH);
        Constrains.addCompXIx(botones(),panel,0,2,2,1,0.6,5,5,5,5,155,GridBagConstraints.NORTHWEST,GridBagConstraints.HORIZONTAL);
        Constrains.addCompX(label,panel,0,0,2,1,1,10,10,10,10,GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL);
        Constrains.addComp(panel, this,0,0,1,3,1,1,5,5,5,5,GridBagConstraints.CENTER,GridBagConstraints.BOTH);
        Constrains.addComp(graficas(),this,1,0,1,1,1,1,5,5,5,8,GridBagConstraints.CENTER,GridBagConstraints.BOTH);
        Constrains.addCompX(new JSeparator(SwingConstants.HORIZONTAL),this,1,1,1,1,1,5,5,5,5,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL);
        Constrains.addComp(subDatos(),this,1,2,1,1,1,1,5,5,5,8,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL);
    }
    private void datosTabla() {
        dialog=new JDialog();
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.setTitle("Ingresar Datos");
        dialog.getContentPane().setLayout(new CardLayout());
        JPanel panel=new JPanel(new GridBagLayout());
        panel.setFont(new Font("Arial",Font.BOLD,24));
        JButton clases=new JButton("Clases");
        clases.setFont(new Font("Arial",Font.BOLD,15));
        clases.setBorder(new RoundedBorder(7));
        clases.setToolTipText("Ingresa el Intervalo y el número de Clases");
        Constrains.addCompX(clases,panel,0,0,1,1,1,5,200,5,200,GridBagConstraints.SOUTH,GridBagConstraints.HORIZONTAL);
        clases.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl=(CardLayout)(dialog.getContentPane().getLayout());
                cl.show(dialog.getContentPane(),"1");
            }
        });
        JButton rango=new JButton("Rango");
        rango.setFont(new Font("Arial",Font.BOLD,15));
        rango.setBorder(new RoundedBorder(7));
        rango.setToolTipText("<html>Ingresa el X<sub>max</sub>, X<sub>min</sub> y número de frecuencias</html>");
        Constrains.addCompX(rango,panel,0,1,1,1,1,5,200,5,200,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL);
        rango.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl=(CardLayout)(dialog.getContentPane().getLayout());
                cl.show(dialog.getContentPane(),"2");
            }
        });
        JButton texto=new JButton("Texto");
        texto.setFont(new Font("Arial",Font.BOLD,15));
        texto.setBorder(new RoundedBorder(7));
        texto.setToolTipText("Ingresa los datos desorganizados de las frecuencias");
        Constrains.addCompX(texto,panel,0,2,1,1,1,5,200,5,200,GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL);
        texto.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl=(CardLayout)(dialog.getContentPane().getLayout());
                cl.show(dialog.getContentPane(),"3");
            }
        });
        dialog.add(panel,"0");
        dialog.add(clases(),"1");
        dialog.add(rango(),"2");
        dialog.add(texto(),"3");
        dialog.pack();
        dialog.setLocationRelativeTo(frv);
        dialog.setResizable(false);
        dialog.setVisible(true);
    }
    private void fillFrecuencia(String n) throws NullPointerException{
        try{
            ArrayList<Datos> list=acciones.intervalo(n);
            clean();
            for (int j = 0; j < list.size(); j++) {
                addRow();
                model.setValueAt(((int)list.get(j).getIntervalo1())+" - "+((int)list.get(j).getIntervalo2()),j,1);
                model.setValueAt(((int)list.get(j).getFi()),j,2);
            }
            fill(list);
            table.updateUI();
            repaint();
            add.setEnabled(false);
            del.setEnabled(false);
            fill.setEnabled(false);
            ing.setEnabled(false);
            graph.setEnabled(true);
            area.setText("");
            dialog.dispose();
        }catch (NullPointerException e){
            JOptionPane.showMessageDialog(frv,"Los datos proporcionados no son suficientes para llenar la tabla","Error de llenado de Tabla",JOptionPane.ERROR_MESSAGE);
            area.setText("");
            dialog.dispose();
            cleanTable();
        }
    }
    private void cleanTable() {
        while (model.getRowCount()>0) model.removeRow(0);
        model.setRowCount(3);
        for (i = 0; i < 3; i++) model.setValueAt(String.valueOf(i+1),i,0);
        table.updateUI();
        acciones = new Acciones();
        add.setEnabled(true);
        del.setEnabled(true);
        fill.setEnabled(true);
        graph.setEnabled(false);
        ing.setEnabled(true);
        ojivaSet.clear();
        poligonoSet.clear();
        histogramaSet.clear();
        cuartil.setEnabled(false);
        decentil.setEnabled(false);
        percentil.setEnabled(false);
        cuartil.setSelectedIndex(-1);
        decentil.setSelectedIndex(-1);
        percentil.setSelectedIndex(-1);
        for (int j = 0; j < 34; j++) if (!(j%2==0)) ((JLabel)components.get(j)).setText("0");
        fllenar=0;
        repaint();
    }
    private void clean() {
        while (model.getRowCount()>0) model.removeRow(0);
        i=0;
        table.updateUI();
        repaint();
    }
    private void graphTable() {
        ArrayList<Datos> datos=acciones.getList();
        ArrayList<Double> Fi=acciones.Fi(),Xi=acciones.Xi();
        int i = 0;
        ojivaSet.addValue(0,"Fi",format.format(datos.get(0).getIntervalo1()));
        while (i!=datos.size()){
            histogramaSet.addValue(datos.get(i).getFi(),"fi",format.format(datos.get(i).getIntervalo1())+" - "+format.format(datos.get(i).getIntervalo2()));
            poligonoSet.addValue(datos.get(i).getFi(),"fi",format.format(Xi.get(i)));
            ojivaSet.addValue(Fi.get(i),"Fi",format.format((i==datos.size()-1)?datos.get(i).getIntervalo2():datos.get(i+1).getIntervalo1()));
            i++;
        }
        repaint();
        graph.setEnabled(false);
    }
    private void fillTable() {
        ArrayList<Datos> list=new ArrayList<Datos>();
        int i = 0;
        while (i<Tabla.this.i && model.getValueAt(i,2)!=null && model.getValueAt(i,1)!=null){
            list.add(new Datos(Double.parseDouble(table.getValueAt(i,1).toString().trim().substring(0,table.getValueAt(i,1).toString().trim().indexOf("-")).trim()),Double.parseDouble(table.getValueAt(i,1).toString().trim().substring(table.getValueAt(i,1).toString().trim().indexOf("-")+1,table.getValueAt(i,1).toString().trim().length()).trim()), Double.parseDouble(table.getValueAt(i,2).toString().trim())));
            i++;
        }
        fill(list);
    }
    private void delRow() {
        if (i!=0) {
            model.removeRow(i-1);
            i--;
            table.updateUI();
            repaint();
            if (i==0) del.setEnabled(false);
        }
    }
    private void addRow() {
        Object d[]=new Object[model.getColumnCount()];
        model.addRow(d);
        model.setValueAt(String.valueOf(++i),i-1,0);
        table.updateUI();
        repaint();
        del.setEnabled(true);
    }
    private void ecuaciones(){
        ((JLabel)components.get(1)).setText(format.format(acciones.getN()));
        ((JLabel)components.get(3)).setText(format.format(Ecuaciones.varianza(acciones.getXiX2(), (int) (acciones.getN()-1))));
        ((JLabel)components.get(5)).setText(format.format(acciones.getXmax()));
        ((JLabel)components.get(7)).setText(format.format(Ecuaciones.desviacionEstandarMuestra(Ecuaciones.varianza(acciones.getXiX2(), (int) (acciones.getN()-1)))));
        ((JLabel)components.get(9)).setText(format.format(acciones.getXmin()));
        ((JLabel)components.get(11)).setText(format.format(Ecuaciones.coeficienteVarianza(Ecuaciones.desviacionEstandarMuestra(Ecuaciones.varianza(acciones.getXiX2(), (int) (acciones.getN()-1))),Ecuaciones.media(acciones.getFiXi(), (int) acciones.getN()))));
        ((JLabel)components.get(13)).setText(format.format(acciones.getR()));
        ((JLabel)components.get(15)).setText(format.format(Ecuaciones.coeficienteSesgo(acciones.fiXiX3(), (int) acciones.getN(),Ecuaciones.desviacionEstandarMuestra(Ecuaciones.varianza(acciones.getXiX2(), (int) (acciones.getN()-1))))));
        ((JLabel)components.get(17)).setText(format.format(acciones.getK()));
        ((JLabel)components.get(19)).setText(format.format(Ecuaciones.coeficienteCurtosis(acciones.fiXiX4(), (int) acciones.getN(),Ecuaciones.desviacionEstandarMuestra(Ecuaciones.varianza(acciones.getXiX2(), (int) (acciones.getN()-1))))));
        ((JLabel)components.get(21)).setText(format.format(acciones.getA()));
        ((JLabel)components.get(25)).setText(format.format(Ecuaciones.media(acciones.getFiXi(), (int) acciones.getN())));
        try{
            ((JLabel)components.get(29)).setText(format.format(Ecuaciones.moda(acciones.getList().get(acciones.posLm()).getIntervalo1()-0.5,acciones.Lm(),acciones.getList().get(acciones.posLm()-1).getFi(),acciones.getList().get(acciones.posLm()+1).getFi(),Ecuaciones.rango((int)acciones.getList().get(0).getIntervalo1(),(int)acciones.getList().get(0).getIntervalo2())+1)));
        }catch (IndexOutOfBoundsException e){
            try{
                ((JLabel)components.get(29)).setText(format.format(Ecuaciones.moda(acciones.getList().get(acciones.posLm()).getIntervalo1()-0.5,acciones.Lm(),0,acciones.getList().get(acciones.posLm()+1).getFi(),Ecuaciones.rango((int)acciones.getList().get(0).getIntervalo1(),(int)acciones.getList().get(0).getIntervalo2())+1)));
            }catch (IndexOutOfBoundsException e1){
                try{
                    ((JLabel)components.get(29)).setText(format.format(Ecuaciones.moda(acciones.getList().get(acciones.posLm()).getIntervalo1()-0.5,acciones.Lm(),acciones.getList().get(acciones.posLm()-1).getFi(),0,Ecuaciones.rango((int)acciones.getList().get(0).getIntervalo1(),(int)acciones.getList().get(0).getIntervalo2())+1)));
                }catch (Exception e2){
                    JOptionPane.showMessageDialog(this,"La Moda no se puede calcular ya que hay un dato erroneo en la Tabla","Error Moda",JOptionPane.ERROR_MESSAGE);
                    cleanTable();
                    return;
                }
            }

        }
        try{
            ((JLabel)components.get(33)).setText(format.format(Ecuaciones.mediana(acciones.getList().get(acciones.posLmMediana()).getIntervalo1()-0.5,acciones.getN(),acciones.Fi().get(acciones.posLmMediana()-1),acciones.getList().get(acciones.posLmMediana()).getFi(),Ecuaciones.rango((int)acciones.getList().get(0).getIntervalo1(),(int)acciones.getList().get(0).getIntervalo2())+1)));
        }catch (IndexOutOfBoundsException e){
            ((JLabel)components.get(33)).setText(format.format(Ecuaciones.mediana(acciones.getList().get(acciones.posLmMediana()).getIntervalo1()-0.5,acciones.getN(),0,acciones.getList().get(acciones.posLmMediana()).getFi(),Ecuaciones.rango((int)acciones.getList().get(0).getIntervalo1(),(int)acciones.getList().get(0).getIntervalo2())+1)));
        }
    }
    private void fill(ArrayList<Datos> list){
        try{
            if (list.isEmpty()) {
                JOptionPane.showMessageDialog(frv,"Tabla Vacia","Tabla Vacia",JOptionPane.ERROR_MESSAGE);
                return;
            }
            fllenar=1;
            acciones.setList(list);
            ArrayList<Double> Fi=acciones.Fi(),fin=acciones.fin(),Fin=acciones.Fin(),Xi=acciones.Xi(),fiXi=acciones.fiXi(),XiX=acciones.XiX(),XiX2=acciones.XiX2();
            int i=0;
            while (i!=list.size()){
                model.setValueAt(format.format(Fi.get(i)),i,3);
                model.setValueAt(format.format(fin.get(i)),i,4);
                model.setValueAt(format.format(Fin.get(i)),i,5);
                model.setValueAt(format.format(Xi.get(i)),i,6);
                model.setValueAt(format.format(fiXi.get(i)),i,7);
                model.setValueAt(format.format(XiX.get(i)),i,8);
                model.setValueAt(format.format(XiX2.get(i)),i,9);
                i++;
            }
            model.addRow(new Object[]{"Σ",null,format.format(acciones.getN()),null,format.format(acciones.getFin()),null,null,format.format(acciones.getFiXi()),null,format.format(acciones.getXiX2())});
            table.updateUI();
            fill.setEnabled(false);
            graph.setEnabled(true);
            add.setEnabled(false);
            del.setEnabled(false);
            ing.setEnabled(false);
            cuartil.setEnabled(true);
            decentil.setEnabled(true);
            percentil.setEnabled(true);
            ecuaciones();
            repaint();
        }catch (Exception e){
            JOptionPane.showMessageDialog(frv,"Hubo un Error al Llenar la Tabla","Error",JOptionPane.ERROR_MESSAGE);
            cleanTable();
            return;
        }
    }
    private JPanel subDatos(){
        JPanel panel=new JPanel(new GridLayout(9,4));
        panel.setFont(new Font("Arial",Font.BOLD,14));
        components=new ArrayList<>();
        components.add(new JLabel("n:"));
        components.add(new JLabel("0"));
        components.add(new JLabel("<html>σ<sup>2</sup>:</html>"));
        components.add(new JLabel("0"));
        components.add(new JLabel("<html>X<sub>max</sub>:</html>"));
        components.add(new JLabel("0"));
        components.add(new JLabel("σ:"));
        components.add(new JLabel("0"));
        components.add(new JLabel("<html>X<sub>min</sub>:</html>"));
        components.add(new JLabel("0"));
        components.add(new JLabel("c.v:"));
        components.add(new JLabel("0"));
        components.add(new JLabel("R:"));
        components.add(new JLabel("0"));
        components.add(new JLabel("c.s:"));
        components.add(new JLabel("0"));
        components.add(new JLabel("K:"));
        components.add(new JLabel("0"));
        components.add(new JLabel("c.c:"));
        components.add(new JLabel("0"));
        components.add(new JLabel("A:"));
        components.add(new JLabel("0"));
        Box box=Box.createHorizontalBox();
        box.add(new JLabel("QK:"));
        cuartil = new JComboBox(new String[]{"1","2","3"});
        cuartil.setSelectedIndex(-1);
        cuartil.setEnabled(false);
        cuartil.addItemListener(this);
        box.add(Box.createHorizontalStrut(5));
        box.add(cuartil);
        components.add(box);
        components.add(new JLabel("0"));
        components.add(new JLabel("media:"));
        components.add(new JLabel("0"));
        Box box1=Box.createHorizontalBox();
        box1.add(new JLabel("DK:"));
        decentil = new JComboBox(new String[]{"1","2","3","4","5","6","7","8","9"});
        decentil.setSelectedIndex(-1);
        decentil.addItemListener(this);
        decentil.setEnabled(false);
        box1.add(Box.createHorizontalStrut(5));
        box1.add(decentil);
        components.add(box1);
        components.add(new JLabel("0"));
        components.add(new JLabel("moda:"));
        components.add(new JLabel("0"));
        Box box2=Box.createHorizontalBox();
        box2.add(new JLabel("PK:"));
        String[] v=new String[99];
        for (int j = 0; j < v.length; j++) v[j]=String.valueOf(j+1);
        percentil = new JComboBox(v);
        percentil.setSelectedIndex(-1);
        percentil.setEnabled(false);
        percentil.addItemListener(this);
        box2.add(Box.createHorizontalStrut(5));
        box2.add(percentil);
        components.add(box2);
        components.add(new JLabel("0"));
        components.add(new JLabel("mediana:"));
        components.add(new JLabel("0"));
        for (int j = 0; j < components.size(); j++) panel.add(components.get(j));
        return panel;
    }
    private JPanel texto(){
        JPanel panel=new JPanel();
        panel.setLayout(new GridBagLayout());
        JLabel label = new JLabel("Ingrese los datos separados por coma.");
        label.setFont(new Font("Arial",Font.PLAIN,17));
        Constrains.addComp(label,panel, 0,0,1,1,0,0,2,2,2,2,GridBagConstraints.NORTHWEST,GridBagConstraints.HORIZONTAL);
        area=new JTextArea();
        area.setColumns(50);
        area.setRows(10);
        area.setLineWrap(true);
        area.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar()!=',' && e.getKeyChar()!='1' && e.getKeyChar()!='2' && e.getKeyChar()!='3' && e.getKeyChar()!='4' && e.getKeyChar()!='5' && e.getKeyChar()!='6' && e.getKeyChar()!='7' && e.getKeyChar()!='8' && e.getKeyChar()!='9' && e.getKeyChar()!='0' || (e.getKeyChar() == ',' && area.getText().trim().endsWith(",")) || (e.getKeyChar() == ',' && area.getText().trim().length()==0)){
                    getToolkit();
                    e.consume();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                if (area.getText().trim().isEmpty()) aceptar.setEnabled(false);
                else aceptar.setEnabled(true);
            }
        });
        Constrains.addComp(new JScrollPane(area),panel, 0,1,1,1,0,0,5,5,5,5,GridBagConstraints.CENTER,GridBagConstraints.BOTH);
        Box box=Box.createHorizontalBox();
        aceptar=new JButton("Llenar");
        aceptar.setEnabled(false);
        aceptar.setBorder(new RoundedBorder(6));
        aceptar.setFont(new Font("Arial",Font.PLAIN,12));
        aceptar.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String n;
                if (area.getText().trim().endsWith(",")) n=area.getText().trim().substring(0,area.getText().trim().length()-2);
                else n=area.getText().trim();
                fillFrecuencia(n);
            }
        });
        JButton cancelar=new JButton("Cancelar");
        cancelar.setFont(new Font("Arial",Font.PLAIN,12));
        cancelar.setBorder(new RoundedBorder(6));
        cancelar.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                area.setText("");
                dialog.dispose();
            }
        });
        box.add(aceptar);
        box.add(Box.createHorizontalStrut(15));
        box.add(cancelar);
        Constrains.addComp(box,panel, 0,2,1,1,0,0,5,5,5,5,GridBagConstraints.SOUTH,GridBagConstraints.NONE);
        return panel;
    }
    private JPanel rango(){
        dialog.setTitle("Rango");
        JPanel panel=new JPanel(new GridBagLayout());
        JLabel label1=new JLabel("<html>X<sub>max</sub>:</html>");
        label1.setFont(new Font("Arial",Font.BOLD,12));
        Constrains.addCompX(label1,panel,0,0,1,1,1,5,5,5,5,GridBagConstraints.EAST,GridBagConstraints.NONE);
        JTextField field=new JTextField(10);
        holder=new PlaceHolder(field,"0");
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar()!='1' && e.getKeyChar()!='2' && e.getKeyChar()!='3' && e.getKeyChar()!='4' && e.getKeyChar()!='5' && e.getKeyChar()!='6' && e.getKeyChar()!='7' && e.getKeyChar()!='8' && e.getKeyChar()!='9' && e.getKeyChar()!='0') {
                    getToolkit();
                    e.consume();
                }
            }
        });
        Constrains.addCompX(field,panel,1,0,1,1,1,5,5,5,5,GridBagConstraints.WEST,GridBagConstraints.NONE);
        JLabel label2=new JLabel("<html>X<sub>min</sub>:</html>");
        label2.setFont(new Font("Arial",Font.BOLD,12));
        Constrains.addCompX(label2,panel,0,1,1,1,1,5,5,5,5,GridBagConstraints.EAST,GridBagConstraints.NONE);
        JTextField field1=new JTextField(10);
        holder=new PlaceHolder(field1,"0");
        field1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar()!='1' && e.getKeyChar()!='2' && e.getKeyChar()!='3' && e.getKeyChar()!='4' && e.getKeyChar()!='5' && e.getKeyChar()!='6' && e.getKeyChar()!='7' && e.getKeyChar()!='8' && e.getKeyChar()!='9' && e.getKeyChar()!='0') {
                    getToolkit();
                    e.consume();
                }
            }
        });
        Constrains.addCompX(field1,panel,1,1,1,1,1,5,5,5,5,GridBagConstraints.WEST,GridBagConstraints.NONE);
        JLabel label3=new JLabel("n:");
        label2.setFont(new Font("Arial",Font.BOLD,12));
        Constrains.addCompX(label3,panel,0,2,1,1,1,5,5,5,5,GridBagConstraints.EAST,GridBagConstraints.NONE);
        JTextField field2=new JTextField(10);
        holder=new PlaceHolder(field2,"0");
        field2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar()!='1' && e.getKeyChar()!='2' && e.getKeyChar()!='3' && e.getKeyChar()!='4' && e.getKeyChar()!='5' && e.getKeyChar()!='6' && e.getKeyChar()!='7' && e.getKeyChar()!='8' && e.getKeyChar()!='9' && e.getKeyChar()!='0') {
                    getToolkit();
                    e.consume();
                }
            }
        });
        Constrains.addCompX(field2,panel,1,2,1,1,1,5,5,5,5,GridBagConstraints.WEST,GridBagConstraints.NONE);
        JButton button=new JButton("Llenar");
        button.setBorder(new RoundedBorder(6));
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Integer.parseInt(field.getText().trim())<=Integer.parseInt(field1.getText().trim()) || Integer.parseInt(field.getText().trim())<=0 || Integer.parseInt(field1.getText().trim())<0 || Integer.parseInt(field2.getText().trim())<=0) {
                    JOptionPane.showMessageDialog(dialog,"Error en uno de los datos.","Dato mal Ingresado",JOptionPane.ERROR_MESSAGE);
                    field.setText("");
                    field1.setText("");
                    field2.setText("");
                    return;
                }
                acciones=new Acciones();
                acciones.setN(Double.parseDouble(field2.getText().trim()));
                acciones.setK(Ecuaciones.k((int)acciones.getN()));
                acciones.setA(Ecuaciones.amplitud(Ecuaciones.rango(Integer.parseInt(field1.getText().trim()), Integer.parseInt(field.getText().trim())),acciones.getK()));
                acciones.setR(Ecuaciones.rango(Integer.parseInt(field1.getText().trim()), Integer.parseInt(field.getText().trim())));
                acciones.setXmax(Integer.parseInt(field.getText().trim()));
                acciones.setXmin(Integer.parseInt(field1.getText().trim()));
                ArrayList<Datos> fill=acciones.fillTable(true,true,Integer.parseInt(field1.getText().trim()),Integer.parseInt(field.getText().trim()));
                int j = 0;
                clean();
                while (j!=fill.size()){
                    addRow();
                    model.setValueAt((int)fill.get(j).getIntervalo1()+" - "+(int)fill.get(j).getIntervalo2(),j,1);
                    j++;
                }
                table.updateUI();
                repaint();
                ing.setEnabled(false);
                add.setEnabled(false);
                del.setEnabled(false);
                field.setText("");
                field1.setText("");
                field2.setText("");
                dialog.dispose();
            }
        });
        Constrains.addCompX(button,panel,1,3,1,1,1,5,5,5,5,GridBagConstraints.WEST,GridBagConstraints.NONE);
        JButton button1=new JButton("Cancelar");
        button1.setBorder(new RoundedBorder(6));
        button1.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                field.setText("");
                field1.setText("");
                field2.setText("");
                dialog.dispose();
            }
        });
        Constrains.addCompX(button1,panel,1,4,1,1,1,5,5,5,5,GridBagConstraints.WEST,GridBagConstraints.NONE);
        return panel;
    }
    private JPanel clases(){
        guion=0;
        dialog.setTitle("Clases");
        JPanel panel=new JPanel(new GridBagLayout());
        JLabel label1=new JLabel("Intervalo:");
        label1.setFont(new Font("Arial",Font.BOLD,12));
        Constrains.addCompX(label1,panel,0,0,1,1,1,5,5,5,5,GridBagConstraints.EAST,GridBagConstraints.NONE);
        JTextField field=new JTextField(10);
        holder=new PlaceHolder(field,"0 - 0");
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar()=='-') {
                    guion++;
                }
                if ((!field.getText().trim().contains("-") && guion>1) || field.getText().trim().isEmpty()) {
                    guion = 0;
                }
                if (e.getKeyChar()!='-' && e.getKeyChar()!='1' && e.getKeyChar()!='2' && e.getKeyChar()!='3' && e.getKeyChar()!='4' && e.getKeyChar()!='5' && e.getKeyChar()!='6' && e.getKeyChar()!='7' && e.getKeyChar()!='8' && e.getKeyChar()!='9' && e.getKeyChar()!='0' || (field.getText().trim().length()==0 && e.getKeyChar()=='-') || (guion>1 && e.getKeyChar()=='-')) {
                    getToolkit();
                    e.consume();
                }
            }
        });
        Constrains.addCompX(field,panel,1,0,1,1,1,5,5,5,5,GridBagConstraints.WEST,GridBagConstraints.NONE);
        JLabel label2=new JLabel("Clases:");
        label2.setFont(new Font("Arial",Font.BOLD,12));
        Constrains.addCompX(label2,panel,0,1,1,1,1,5,5,5,5,GridBagConstraints.EAST,GridBagConstraints.NONE);
        JTextField field1=new JTextField(10);
        holder=new PlaceHolder(field1,"0");
        field1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar()!='1' && e.getKeyChar()!='2' && e.getKeyChar()!='3' && e.getKeyChar()!='4' && e.getKeyChar()!='5' && e.getKeyChar()!='6' && e.getKeyChar()!='7' && e.getKeyChar()!='8' && e.getKeyChar()!='9' && e.getKeyChar()!='0') {
                    getToolkit();
                    e.consume();
                }
            }
        });
        Constrains.addCompX(field1,panel,1,1,1,1,1,5,5,5,5,GridBagConstraints.WEST,GridBagConstraints.NONE);
        JButton button=new JButton("Llenar");
        button.setBorder(new RoundedBorder(6));
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (guion!=1 || field.getText().trim().endsWith("-") || field.getText().trim().startsWith("-") || Integer.parseInt(field1.getText().trim())<=0 || Integer.parseInt(field.getText().trim().substring(field.getText().trim().indexOf("-")+1,field.getText().trim().length()).trim())<=Integer.parseInt(field.getText().trim().substring(0,field.getText().trim().indexOf("-")).trim())) {
                    JOptionPane.showMessageDialog(dialog,"Error en uno de los datos.","Dato mal Ingresado",JOptionPane.ERROR_MESSAGE);
                    field.setText("");
                    field1.setText("");
                    guion=0;
                    return;
                }
                int j = 0,n= Integer.parseInt(field1.getText().trim()),k1= Integer.parseInt(field.getText().trim().substring(0,field.getText().trim().indexOf("-")).trim()),k2= Integer.parseInt(field.getText().trim().substring(field.getText().trim().indexOf("-")+1,field.getText().trim().length()).trim()),a=k2-k1;
                clean();
                while (j!=n){
                    addRow();
                    model.setValueAt(k1+" - "+k2,j,1);
                    k1+=(a+1);
                    k2+=(a+1);
                    j++;
                }
                table.updateUI();
                repaint();
                ing.setEnabled(false);
                add.setEnabled(false);
                del.setEnabled(false);
                field.setText("");
                field1.setText("");
                dialog.dispose();
            }
        });
        Constrains.addCompX(button,panel,1,3,1,1,1,5,5,5,5,GridBagConstraints.WEST,GridBagConstraints.NONE);
        JButton button1=new JButton("Cancelar");
        button1.setBorder(new RoundedBorder(6));
        button1.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                field.setText("");
                field1.setText("");
                dialog.dispose();
            }
        });
        Constrains.addCompX(button1,panel,1,4,1,1,1,5,5,5,5,GridBagConstraints.WEST,GridBagConstraints.NONE);
        return panel;
    }
    private JPanel botones(){
        JPanel panel=new JPanel(new FlowLayout(FlowLayout.CENTER,5,5));
        add=new JButton("+");
        del=new JButton("-");
        fill=new JButton("Llenar");
        graph=new JButton("Graficar");
        clean=new JButton("Limpiar");
        ing=new JButton("Ingresar Datos");
        try {
            add=new JButton(new ImageIcon(ImageIO.read(getClass().getResource("/image/add.png")).getScaledInstance(16,16,Image.SCALE_DEFAULT)));
            del=new JButton(new ImageIcon(ImageIO.read(getClass().getResource("/image/del.png")).getScaledInstance(16,16,Image.SCALE_DEFAULT)));
        } catch (IOException e) {

        }
        add.setFont(new Font("Arial",Font.BOLD,15));
        add.setBorder(new RoundedBorder(6));
        add.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRow();
            }
        });
        add.setToolTipText("Añadir nueva fila a la tabla");
        add.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER || e.getKeyCode()==KeyEvent.VK_SPACE) addRow();
            }
        });
        del.setFont(new Font("Arial",Font.BOLD,15));
        del.setToolTipText("Eliminar ultima fila de la tabla");
        del.setBorder(new RoundedBorder(6));
        del.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delRow();
            }
        });
        del.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER || e.getKeyCode()==KeyEvent.VK_SPACE) delRow();
            }
        });
        fill.setFont(new Font("Arial",Font.BOLD,15));
        fill.setToolTipText("Llenar tabla y ecuaciones");
        fill.setBorder(new RoundedBorder(6));
        fill.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fllenar==0) fillTable();
            }
        });
        fill.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER || e.getKeyCode()==KeyEvent.VK_SPACE) if (fllenar==0) fillTable();
            }
        });
        graph.setFont(new Font("Arial",Font.BOLD,15));
        graph.setEnabled(false);
        graph.setBorder(new RoundedBorder(6));
        graph.setToolTipText("Graficar frecuencias");
        graph.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graphTable();
            }
        });
        graph.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER || e.getKeyCode()==KeyEvent.VK_SPACE) graphTable();
            }
        });
        clean.setFont(new Font("Arial",Font.BOLD,15));
        clean.setToolTipText("Limpiar tabla");
        clean.setBorder(new RoundedBorder(6));
        clean.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cleanTable();
            }
        });
        clean.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER || e.getKeyCode()==KeyEvent.VK_SPACE) cleanTable();
            }
        });
        ing.setFont(new Font("Arial",Font.BOLD,15));
        ing.setToolTipText("Ingresar datos por texto");
        ing.setBorder(new RoundedBorder(6));
        ing.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                datosTabla();
            }
        });
        ing.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER || e.getKeyCode()==KeyEvent.VK_SPACE) datosTabla();
            }
        });
        panel.add(add);
        panel.add(del);
        panel.add(new JSeparator(JSeparator.VERTICAL));
        panel.add(fill);
        panel.add(graph);
        panel.add(new JSeparator(JSeparator.VERTICAL));
        panel.add(clean);
        panel.add(new JSeparator(JSeparator.VERTICAL));
        panel.add(ing);
        return panel;
    }
    private ChartPanel poligono() {
        poligonoSet=new DefaultCategoryDataset();
        JFreeChart chart= ChartFactory.createLineChart("Poligono","Xi","fi",poligonoSet, PlotOrientation.VERTICAL,true,true,false);
        ChartPanel panel = new ChartPanel(chart);
        return panel;
    }
    private ChartPanel histograma() {
        histogramaSet=new DefaultCategoryDataset();
        JFreeChart chart= ChartFactory.createBarChart("Histograma","intervalos","fi",histogramaSet, PlotOrientation.VERTICAL,true,true,false);
        ChartPanel panel = new ChartPanel(chart);
        CategoryPlot plot=chart.getCategoryPlot();
        plot.setRangeGridlinePaint(Color.BLUE);
        return panel;
    }
    private ChartPanel ojiva() {
        ojivaSet = new DefaultCategoryDataset();
        JFreeChart chart= ChartFactory.createLineChart("Ojiva","intervalo","Fi",ojivaSet, PlotOrientation.VERTICAL, true,true,false);
        ChartPanel panel = new ChartPanel(chart);
        return panel;
    }
    private JTabbedPane graficas() {
        JTabbedPane pane=new JTabbedPane();
        pane.add("Histograma",histograma());
        pane.add("Ojiva",ojiva());
        pane.add("Poligono",poligono());
        return pane;
    }
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource()==cuartil && cuartil.getSelectedIndex()!=-1) {
            try{
                ((JLabel)components.get(23)).setText(format.format(Ecuaciones.cuartiles(acciones.getList().get(acciones.posLmCuartil(cuartil.getSelectedIndex()+1)).getIntervalo1()-0.5,cuartil.getSelectedIndex()+1.0,acciones.getN(),acciones.Fi().get(acciones.posLmCuartil(cuartil.getSelectedIndex()+1)-1),acciones.getList().get(acciones.posLmCuartil(cuartil.getSelectedIndex()+1)).getFi(),Ecuaciones.rango((int)acciones.getList().get(0).getIntervalo1(),(int)acciones.getList().get(0).getIntervalo2())+1)));
            }catch (IndexOutOfBoundsException e1){
                ((JLabel)components.get(23)).setText(format.format(Ecuaciones.cuartiles(acciones.getList().get(acciones.posLmCuartil(cuartil.getSelectedIndex()+1)).getIntervalo1()-0.5,cuartil.getSelectedIndex()+1.0,acciones.getN(),0,acciones.getList().get(acciones.posLmCuartil(cuartil.getSelectedIndex()+1)).getFi(),Ecuaciones.rango((int)acciones.getList().get(0).getIntervalo1(),(int)acciones.getList().get(0).getIntervalo2())+1)));
            }
        }
        else if (e.getSource()==decentil && decentil.getSelectedIndex()!=-1) {
            try{
                ((JLabel)components.get(27)).setText(format.format(Ecuaciones.deciles(acciones.getList().get(acciones.posLmDecentil(decentil.getSelectedIndex()+1)).getIntervalo1()-0.5,decentil.getSelectedIndex()+1.0,acciones.getN(),acciones.Fi().get(acciones.posLmDecentil(decentil.getSelectedIndex()+1)-1),acciones.getList().get(acciones.posLmDecentil(decentil.getSelectedIndex()+1)).getFi(),Ecuaciones.rango((int)acciones.getList().get(0).getIntervalo1(),(int)acciones.getList().get(0).getIntervalo2())+1)));
            }catch (IndexOutOfBoundsException e1){
                ((JLabel)components.get(27)).setText(format.format(Ecuaciones.deciles(acciones.getList().get(acciones.posLmDecentil(decentil.getSelectedIndex()+1)).getIntervalo1()-0.5,decentil.getSelectedIndex()+1.0,acciones.getN(),0,acciones.getList().get(acciones.posLmDecentil(decentil.getSelectedIndex()+1)).getFi(),Ecuaciones.rango((int)acciones.getList().get(0).getIntervalo1(),(int)acciones.getList().get(0).getIntervalo2())+1)));
            }
        }
        else if (e.getSource()==percentil && percentil.getSelectedIndex()!=-1) {
            try{
                ((JLabel)components.get(31)).setText(format.format(Ecuaciones.persentiles(acciones.getList().get(acciones.posLmPercentil(percentil.getSelectedIndex()+1)).getIntervalo1()-0.5,percentil.getSelectedIndex()+1.0,acciones.getN(),acciones.Fi().get(acciones.posLmPercentil(percentil.getSelectedIndex()+1)-1),acciones.getList().get(acciones.posLmPercentil(percentil.getSelectedIndex()+1)).getFi(),Ecuaciones.rango((int)acciones.getList().get(0).getIntervalo1(),(int)acciones.getList().get(0).getIntervalo2())+1)));
            }catch (IndexOutOfBoundsException e1){
                ((JLabel)components.get(31)).setText(format.format(Ecuaciones.persentiles(acciones.getList().get(acciones.posLmPercentil(percentil.getSelectedIndex()+1)).getIntervalo1()-0.5,percentil.getSelectedIndex()+1.0,acciones.getN(),0,acciones.getList().get(acciones.posLmPercentil(percentil.getSelectedIndex()+1)).getFi(),Ecuaciones.rango((int)acciones.getList().get(0).getIntervalo1(),(int)acciones.getList().get(0).getIntervalo2())+1)));
            }
        }
        repaint();
    }
    private class Table extends JTable{
        Table(){
            setAutoCreateColumnsFromModel(true);
            setAutoscrolls(true);
            setVisible(true);
            JTableHeader tableHeader=getTableHeader();
            tableHeader.setDefaultRenderer(new HeaderCellRenderer());
            setTableHeader(tableHeader);
            setSelectionBackground(new Color(231,247,252));
            setSelectionForeground(new Color(0,0,0));
            setGridColor(new Color(221,221,221));
            setDefaultRenderer(Object.class,new CellRenderer());
            setVisible(true);
        }
        @Override
        public void changeSelection(int rowIndex,int columnIndex,boolean toggle,boolean extend){
            if (columnIndex==0) super.changeSelection(rowIndex,columnIndex+1,toggle,extend);
            else super.changeSelection(rowIndex,columnIndex,toggle,extend);
        }
        @Override
        public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
            Component component= super.prepareRenderer(renderer, row, column);
            component.setBackground(Color.WHITE);
            component.setForeground(Color.BLACK);
            if ((Double.class.equals(getColumnClass(column))) && (getValueAt(row,column)!=null)){
                if (Double.parseDouble(getValueAt(row,2).toString().trim())<0.0){
                    component.setBackground(Color.RED);
                    component.setForeground(Color.WHITE);
                    fill.setEnabled(false);
                }else fill.setEnabled(true);
            }
            if ((String.class.equals(getColumnClass(column))) && (getValueAt(row,column)!=null)){
                try{
                    guion=0;
                    for (int w = 0; w < table.getValueAt(row,1).toString().trim().length(); w++) if (table.getValueAt(row,1).toString().trim().charAt(w)=='-') guion++;
                    if (guion!=1 || (Integer.parseInt(table.getValueAt(row,column).toString().trim().substring(0,table.getValueAt(row,column).toString().trim().indexOf("-")).trim())>= Integer.parseInt(table.getValueAt(row,column).toString().trim().substring(table.getValueAt(row,column).toString().trim().indexOf("-")+1,table.getValueAt(row,column).toString().trim().length()).trim())) || (row>0 && ((Integer.parseInt(table.getValueAt(row,1).toString().trim().substring(table.getValueAt(row,1).toString().trim().indexOf("-")+1,table.getValueAt(row,1).toString().trim().length()).trim())- Integer.parseInt(table.getValueAt(row,1).toString().trim().substring(0,table.getValueAt(row,1).toString().trim().indexOf("-")).trim()))!=(Integer.parseInt(table.getValueAt(0,1).toString().trim().substring(table.getValueAt(0,1).toString().trim().indexOf("-")+1,table.getValueAt(0,1).toString().trim().length()).trim())- Integer.parseInt(table.getValueAt(0,1).toString().trim().substring(0,table.getValueAt(0,1).toString().trim().indexOf("-")).trim())))) || (row>0 && (Integer.parseInt(table.getValueAt(row,1).toString().trim().substring(0,table.getValueAt(row,1).toString().trim().indexOf("-")).trim()))!= Integer.parseInt(table.getValueAt(row-1,1).toString().trim().substring(table.getValueAt(row-1,1).toString().trim().indexOf("-")+1,table.getValueAt(row-1,1).toString().trim().length()).trim())+1) || (table.getValueAt(row,1).toString().trim().length()<3)){
                        component.setBackground(Color.RED);
                        component.setForeground(Color.WHITE);
                        fill.setEnabled(false);
                    }else fill.setEnabled(true);
                }catch (IndexOutOfBoundsException e){

                }catch (NumberFormatException e){
                    component.setBackground(Color.RED);
                    component.setForeground(Color.WHITE);
                    fill.setEnabled(false);
                }
            }
            return component;
        }
    }
    private class Model extends DefaultTableModel{
        boolean[] canEdit=new boolean[]{false,true,true,false,false,false,false,false,false,false};
        @Override
        public boolean isCellEditable(int row, int column) {
            return canEdit[column];
        }
        Class[] types = new Class [] {null,String.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class};
        @Override
        public Class getColumnClass(int columnIndex) {
            return types [columnIndex];
        }
    }
    private class CellRenderer extends DefaultTableCellRenderer{
        @Override
        public Component getTableCellRendererComponent(JTable table,Object value,boolean selected,boolean focused,int row,int column){
            setEnabled(table==null || table.isEnabled());
            setBackground(Color.WHITE);
            table.setFont(new Font("Arial",Font.PLAIN,12));
            setBackground((row%2==1)?new Color(255,255,255):new Color(249,249,249));
            super.getTableCellRendererComponent(table,value,selected,focused,row,column);
            return this;
        }
    }
    private class HeaderCellRenderer implements TableCellRenderer{
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JComponent component=new JLabel();
            if (value instanceof String){
                component=new JLabel(String.valueOf(value));
                ((JLabel)component).setHorizontalAlignment(SwingConstants.CENTER);
                ((JLabel)component).setSize(60,component.getWidth());
                ((JLabel)component).setPreferredSize(new Dimension(7,component.getWidth()));
            }
            component.setEnabled(true);
            component.setBorder(BorderFactory.createMatteBorder(0,0,1,1,new Color(174, 196,221)));
            component.setOpaque(true);
            component.setBackground(Color.WHITE);
            component.setToolTipText("Columna "+(column+1));
            return component;
        }
    }
    private class RenderRight extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel l=(JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            l.setHorizontalAlignment(SwingConstants.RIGHT);
            return l;
        }
    }
}