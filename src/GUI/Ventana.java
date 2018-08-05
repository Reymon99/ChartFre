package GUI;
import javax.swing.*;
import java.awt.*;
public class Ventana extends JFrame /*1360 v0.2*/{
    public Ventana(){
        super("ChartFre");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setContentPane(new Tabla(this));
        pack();
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/image/line-chart.png")).getImage());
    }
    public static void main(String[] args) {
    	new Ventana().setVisible(true);
    }
}