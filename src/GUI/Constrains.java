package GUI;
import javax.swing.*;
import java.awt.*;
public abstract class Constrains {
    public static void addCompX(JComponent component,Container container, int x, int y, int width, int height, double weightx, int top, int left, int bottom, int right, int place, int stretch){
        GridBagConstraints grid=new GridBagConstraints();
        grid.gridx=x;
        grid.gridy=y;
        grid.gridwidth=width;
        grid.gridheight=height;
        grid.weightx=weightx;
        grid.insets=new Insets(top,left,bottom,right);
        grid.anchor=place;
        grid.fill=stretch;
        container.add(component,grid);
    }
    public static void addCompY(JComponent component,Container container,int x,int y,int width,int height,double weighty,int top,int left,int bottom,int right,int place,int stretch){
        GridBagConstraints grid=new GridBagConstraints();
        grid.gridx=x;
        grid.gridy=y;
        grid.gridwidth=width;
        grid.gridheight=height;
        grid.weighty=weighty;
        grid.insets=new Insets(top,left,bottom,right);
        grid.anchor=place;
        grid.fill=stretch;
        container.add(component,grid);
    }
    public static void addComp(JComponent component,Container container,int x,int y,int width,int height,double weightx,double weighty,int top,int left,int bottom,int right,int place,int stretch){
        GridBagConstraints grid=new GridBagConstraints();
        grid.gridx=x;
        grid.gridy=y;
        grid.gridwidth=width;
        grid.gridheight=height;
        grid.weightx=weightx;
        grid.weighty=weighty;
        grid.insets=new Insets(top,left,bottom,right);
        grid.anchor=place;
        grid.fill=stretch;
        container.add(component,grid);
    }
    public static void addCompIx(JComponent component,Container container,int x,int y,int width,int height,double weightx,double weighty,int top,int left,int bottom,int right,int ipadx,int place,int stretch) {
        GridBagConstraints grid=new GridBagConstraints();
        grid.gridx=x;
        grid.gridy=y;
        grid.gridwidth=width;
        grid.gridheight=height;
        grid.weightx=weightx;
        grid.weighty=weighty;
        grid.insets=new Insets(top,left,bottom,right);
        grid.ipadx=ipadx;
        grid.anchor=place;
        grid.fill=stretch;
        container.add(component,grid);
    }
    public static void addCompXIx(JComponent component,Container container,int x,int y,int width,int height,double weightx,int top,int left,int bottom,int right,int ipadx,int place,int stretch) {
        GridBagConstraints grid=new GridBagConstraints();
        grid.gridx=x;
        grid.gridy=y;
        grid.gridwidth=width;
        grid.gridheight=height;
        grid.weightx=weightx;
        grid.insets=new Insets(top,left,bottom,right);
        grid.ipadx=ipadx;
        grid.anchor=place;
        grid.fill=stretch;
        container.add(component,grid);
    }
}