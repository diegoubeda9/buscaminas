/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author Diego Ubeda-Romero Alonso
 */
public class VentanaBuscaMinas extends javax.swing.JFrame {
    
    int filas = 15;
    int columnas = 20;
    Icon bomba = new ImageIcon(getClass().getResource("/images/bomba.png"));
    Icon bandera = new ImageIcon(getClass().getResource("/images/bandera.png"));
    
    
    Boton[][] arrayBotones = new Boton[filas][columnas];
    
    
    
    
    
    

    /**
     * Creates new form VentanaBuscaMinas
     */
    public VentanaBuscaMinas() {
        initComponents();
        setSize(800,600);
        getContentPane().setLayout(new GridLayout(filas, columnas));
        for (int i=0; i<filas; i++){
            for (int j=0; j<columnas; j++){
                Boton boton = new Boton(i,j);          
                getContentPane().add(boton);
                arrayBotones[i][j]= boton;
                boton.addMouseListener(new MouseAdapter() {
                    
                    @Override
                    public void mousePressed(MouseEvent evt){
                        botonPulsado(evt);
                        
                    }
                
                
                });
                
                
                
            }
        }
       
        
        ponMinas(10);
        cuentaMinas();
        
    }
    
    private void botonPulsado(MouseEvent e){
        Boton miBoton = (Boton) e.getComponent();
        if(e.getButton()==MouseEvent.BUTTON3 && miBoton.isEnabled()){
            miBoton.setIcon(bandera);

        } if(e.getButton()== MouseEvent.BUTTON1 && miBoton.getText().equals("")){
 
            if(miBoton.getMina()==1){
               
                todoVisible();
                JOptionPane.showMessageDialog(null, "has perdido");
       getContentPane().setEnabled(true);
                
                
                
            }
            else if(miBoton.getNumeroMinasAlrededor()==0){
                miBoton.setFocusPainted(false);
                casillasCero(miBoton);
                
                
            }
            else{
                miBoton.setText(String.valueOf(miBoton.getNumeroMinasAlrededor()));
                miBoton.setEnabled(false);
                 miBoton.setFocusPainted(false);
            }

        }
//        JOptionPane.showMessageDialog(null, "has perdido");
//        getContentPane().setEnabled(true);
    }
    
    
    
    
    private void casillasCero(Boton boton){
      
         
         
         
        if(boton.getNumeroMinasAlrededor()==0){
            boton.setEnabled(false);
            
            for(int k = -1; k<2; k++){
                    for(int m = -1; m<2; m++){
                           if((boton.getI() + k >= 0)&&(boton.getJ() + m >= 0)&&(boton.getI() + k < filas) && (boton.getJ() + m < columnas)){
                               if(arrayBotones[boton.getI() + k][boton.getJ() + m].isEnabled()){
                                    if(arrayBotones[boton.getI() + k][boton.getJ() + m].getNumeroMinasAlrededor() == 0){
                                      arrayBotones[boton.getI() + k][boton.getJ() + m].setEnabled(false);
                                      casillasCero(arrayBotones[boton.getI() + k][boton.getJ() + m]);
                                      
                                    }else {
                                        arrayBotones[boton.getI() + k][boton.getJ() + m].setEnabled(false);
                                       arrayBotones[boton.getI() + k][boton.getJ() + m]
                                               .setText(String.valueOf(arrayBotones[boton.getI() + k][boton.getJ() + m].getNumeroMinasAlrededor()));
                                        
                                    }
                               }
                            }
                        }
                    }
           


        }
        
        
        
        
        
    }
    
    
    private void todoVisible(){
       
        for(int i=0; i<filas; i++){
            for(int j=0;j<columnas; j++){
                
                if(arrayBotones[i][j].getMina() == 0){
                    if(arrayBotones[i][j].getNumeroMinasAlrededor()!=0){
                  arrayBotones[i][j].setText(String.valueOf(arrayBotones[i][j].getNumeroMinasAlrededor()));
                
                    }
                }
                if (arrayBotones[i][j].getMina() == 1) {
                   arrayBotones[i][j].setIcon(bomba);
                   
//                JOptionPane.showMessageDialog(null, "has perdido");
////        getContentPane().setEnabled(true);
       
                }
                 
                
                
                
                
//                arrayBotones[i][j].setEnabled(false);
                
            
                
            }
        }
        
        
        
        
    }
    
   
    
    
    
    
    
    
    
    
    
    private void ponMinas(int numeroMinas){
        Random r = new Random();
        for(int i=0; i<numeroMinas; i++){
            int f = r.nextInt(filas);
            int c = r.nextInt(columnas);
            
            //TODO hay que hacer una version que chequee si en la casilla
            //seleccionada ya hay una mina, porque en ese caso tiene que
            //buscar otra
            arrayBotones[f][c].setMina(1);
            
           
            
            
            
        }
        
    }
    
    //es un metodo que para cada boton calcula el numero de minas
    //que tiene alrededor
    private void cuentaMinas(){
        
        //TODO falta calcular los bordes
        int minas = 0;
        
        
        
        
        for(int i=0; i<filas; i++){
            for(int j=0;j<columnas; j++){
                //botones que no son bordes
                if(arrayBotones[i][j].getText().equals("")){
                if((i>0) && (j>0) && (i < filas-1) && (j<columnas-1)){
                    
                    minas += arrayBotones[i-1][j-1].getMina();//la mina de arriba izq
                    minas += arrayBotones[i][j-1].getMina();//la mina de arriba 
                    minas += arrayBotones[i+1][j-1].getMina();//la mina de la abajo izquierda
                    
                    
                    minas += arrayBotones[i-1][j].getMina();//la mina de encima
                    minas += arrayBotones[i+1][j].getMina();//la mina de abajo
                    
                    
                    minas += arrayBotones[i-1][j+1].getMina();//la mina de arriba derecha
                    minas += arrayBotones[i][j+1].getMina();//la mina de la derecha 
                    minas += arrayBotones[i+1][j+1].getMina();//la mina de la abajo derecha
                    
                }
                //esquina izq arriba
              if((i==0)&&(j==0)){
                   minas += arrayBotones[i][j+1].getMina();//la mina der
                   minas += arrayBotones[i+1][j+1].getMina();//la mina de abajo der
                   minas += arrayBotones[i+1][j].getMina();//la mina de la abajo 
              }
              //esquina der arriba
              if((i==0)&&(j==columnas-1)){
                   minas += arrayBotones[i][j-1].getMina();//la mina izq
                   minas += arrayBotones[i+1][j-1].getMina();//la mina de abajo izq
                   minas += arrayBotones[i+1][j].getMina();//la mina de la abajo 
              }
              //esquina izq abajo
              if((i==filas-1)&&(j==0)){
                   minas += arrayBotones[i-1][j].getMina();//la mina arriba
                   minas += arrayBotones[i-1][j+1].getMina();//la mina de arriba der
                   minas += arrayBotones[i][j+1].getMina();//la mina de la derecha
                  
              }
              
              
              //borde arriba
              if((i==0)&&(j>0)&&(j<columnas-1)){
                   minas += arrayBotones[i][j+1].getMina();//la mina der
                   minas += arrayBotones[i][j-1].getMina();//la mina izq
                   minas += arrayBotones[i+1][j-1].getMina();//la mina de abajo izq
                   minas += arrayBotones[i+1][j].getMina();//la mina de abajo
                   minas += arrayBotones[i+1][j+1].getMina();//la mina de la abajo der
                  
              }
              
              //borde izq
              if((j==0)&& (i>0)&&(i<filas-1)){
                  
                   minas += arrayBotones[i-1][j].getMina();//la mina arriba
                   minas += arrayBotones[i-1][j+1].getMina();//la mina arriba der
                   minas += arrayBotones[i][j+1].getMina();//la mina der
                   minas += arrayBotones[i+1][j].getMina();//la mina de abajo
                   minas += arrayBotones[i+1][j+1].getMina();//la mina de la abajo der
                
              }
              
              //borde abajo
              if((i==filas-1)&&(j>0)&&(j<columnas-1) ){
                  
                   minas += arrayBotones[i][j-1].getMina();//la mina izq
                   minas += arrayBotones[i-1][j-1].getMina();//la mina arriba izq
                   minas += arrayBotones[i-1][j].getMina();//la mina arriba
                   minas += arrayBotones[i-1][j+1].getMina();//la mina de arriba der
                   minas += arrayBotones[i][j+1].getMina();//la mina der
                
              }
              
              //borde der
              if((j==columnas-1)&&(i>0)&&(i<filas-1) ){
                  
                   minas += arrayBotones[i-1][j].getMina();//la mina arriba
                   minas += arrayBotones[i-1][j-1].getMina();//la mina arriba izq
                   minas += arrayBotones[i][j-1].getMina();//la mina izq
                   minas += arrayBotones[i+1][j-1].getMina();//la mina de abajo izq
                   minas += arrayBotones[i+1][j].getMina();//la mina abajo
                
              }
              
              
                
                arrayBotones[i][j].setNumeroMinasAlrededor(minas);
                
                
                
                
                //TODO comentar la siguiente parte para que no aparezca los numeros al iniciar la partida
//                if(arrayBotones[i][j].getMina()==0){
//                    arrayBotones[i][j].setText(String.valueOf(minas));
//                }
                minas=0;
                
                } 
            }
        }
                
                
                
    }
    
    
    
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaBuscaMinas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaBuscaMinas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaBuscaMinas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaBuscaMinas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                new VentanaBuscaMinas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
