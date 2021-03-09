/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.servidorhilos;

/**
 *
 * @author manue
 */
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author manue
 */
public class ArranqueMotor extends Thread {
public final static String SUBVERDE="\u001B[42;30m";
public final static String SUBROJO="\033[41;34m";
public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String SUBBLANCO="\u001B[47;30m";
    boolean activado = true;
    int id;
    int sensor;

    public ArranqueMotor(int id, int sensor) {
        this.id = id;
        this.sensor = sensor;
    }

    @Override
    public void run() {
        CamaraDAO cdao = new CamaraDAO();
        ArrayList<Camara> camaras = new ArrayList<>();
        camaras = (ArrayList<Camara>) cdao.load();
        do {
            if (this.sensor == 1) {
                try {
                         sleep(2000);
                    LeerSensor1(this.id);
                    System.out.println("-----------------------");
                      sleep(30000);
                } catch (IOException ex) {
                    Logger.getLogger(ArranqueMotor.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ArranqueMotor.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (this.sensor == 2) {
                try {
                 sleep(2000);
                    LeerSensor2(this.id);
                    System.out.println("-----------------------");
                       sleep(30000);
                } catch (IOException ex) {
                    Logger.getLogger(ArranqueMotor.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ArranqueMotor.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("Elija un sensor valido");
            }
        } while (activado);
    }

    public void LeerSensor1(int id_camara) throws IOException {

  
   CamaraDAO cdao = new CamaraDAO();
       Camara c = new Camara();
       c=CargarCamara(id_camara);
        System.out.println(ANSI_BLACK+c+ANSI_RESET);
  
                if (c.getValorS1() > c.getTempMaxima() && c.isMotor()==false) {
                   
                    System.out.println(SUBROJO+"Temperatura inadecuada  en el Sensor 1"+ANSI_RESET);
                    System.out.println("Comprobando puerta");
                    if (c.isPuerta()==true) {
                        System.out.println(" La puerta esta abierta,Cerrando puerta");
                        c.setPuerta(0);
                        System.out.println("Arrancando motor");
                        c.setMotor(1);
                             cdao.update(c);
                    } else {

                        System.out.println(" La puerta esta cerrada,Arrancando motor");
                        c.setMotor(1);
                             cdao.update(c);
                    }
                } else {
                    System.out.println(SUBVERDE+"Situacion correcta S1"+ANSI_RESET);
                }

                cdao.update(c);
            

    }

    public void LeerSensor2(int id_camara) throws IOException {

     
   CamaraDAO cdao = new CamaraDAO();
       Camara c = new Camara();
       c=CargarCamara(id_camara);
  
                if (c.getValorS2() > c.getTempMaxima() && c.isMotor()==false) {
                   
                    System.out.println(SUBROJO+"Temperatura inadecuada  en el Sensor 2"+ANSI_RESET);
                    System.out.println("Comprobando puerta");
                    if (c.isPuerta()==true) {
                        System.out.println(" La puerta esta abierta,Cerrando puerta");
                        c.setPuerta(0);
                        System.out.println("Arrancando motor");
                        c.setMotor(1);
                             cdao.update(c);
                    } else {

                        System.out.println(" La puerta esta cerrada,Arrancando motor");
                        c.setMotor(1);
                             cdao.update(c);
                    }
                } else {
                    System.out.println(SUBVERDE+"Situacion correcta S2"+ANSI_RESET);
                }

                cdao.update(c);
            

    }
    
    public Camara CargarCamara(int id){
        Camara c = new Camara();
         CamaraDAO cdao = new CamaraDAO();
        ArrayList<Camara> Camaras = new ArrayList<>();
        Camaras = (ArrayList<Camara>) cdao.load();
         for (Camara ca : Camaras) {
           if (ca.getCodigo_camara() == id) {
               c=ca;
            }
         }
            
        return c;
    }

    public void Detener() {
        activado = false;
    }
}
