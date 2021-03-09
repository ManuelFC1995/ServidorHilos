/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.servidorhilos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author manue
 */
public class GESCON extends Thread {

    private Socket connection;
    ObjectInputStream in;
    ObjectOutputStream out;
    private CamaraDAO cdao;

    public GESCON(Socket socket) throws IOException {
        this.connection = socket;
        this.cdao = cdao;
        this.in = new ObjectInputStream(connection.getInputStream());
        this.out = new ObjectOutputStream(connection.getOutputStream());
    }

    public void run() {
        super.run();
        try {
            Object o = in.readObject(); //recibimos el numero de la opcion
            System.out.println("Opcion elegida: " + o + " por: " + connection.getRemoteSocketAddress());

            switch ((int) o) {
                case 1:
                    ArranqueMotor();
                    break;
                case 2:
                    ArranquePuerta();
                    break;
                case 3:
                    Insertar();
                    break;
            }

            if (in.available() > 0) {
                in.close();
                out.close();
            }
            connection.close();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void ArranqueMotor() throws IOException {

        Integer id = in.readInt();//recibe id
        Integer sensor = in.readInt(); //recibe sensor 1 o 2
        Integer value = in.readInt(); //recibe valor
        cdao = new CamaraDAO();
        Camara c = new Camara();
        c = CargarCamara(id);

        boolean updated = false;
        if (c.getCodigo_camara() == id) {
            if (sensor == 1) {
                c.setValorS1(value); //recibe valor si sensor 1
            } else if (sensor == 2) {
                c.setValorS2(value); //recibe valor si sensor 2
            }
            updated = cdao.update(c);
            if (updated) {
                new ArranqueMotor(id, sensor).start();
            }
        }
        System.out.println("Respondemos: " + updated + " a: " + connection.getRemoteSocketAddress());
        out.writeBoolean(updated); //envia booleano para saber si actualizo
        out.flush();
    }

    private void ArranquePuerta() throws IOException {

        Integer id = in.readInt();//recibe id
        cdao = new CamaraDAO();
        Camara c = new Camara();
        c = CargarCamara(id);
        boolean updated = false;
        c.setPuerta(in.readInt()); //recibe valor puerta
        updated = cdao.update(c);
        if (updated) {
            new ArranquePuerta(id).start();
        }

        System.out.println("Respondemos: " + updated + " a: " + connection.getRemoteSocketAddress());
        out.writeBoolean(updated); //envia booleano para saber si actualizo
        out.flush();
    }

    private void Insertar() throws IOException, ClassNotFoundException {
        CamaraDAO cdao;
        Camara c;
        boolean done = false;
        out.writeBoolean(true); //aceptamos opcion
        out.flush();
        Object o = in.readObject(); //recibimos chamber
        if (o instanceof Camara) {
            c = (Camara) o;
            cdao = new CamaraDAO();
            done = cdao.create(c);
            out.writeBoolean(done); //decimos si esta o no creada
            System.out.println(c + " creado por: " + connection.getRemoteSocketAddress());

        }
    }

    public Camara CargarCamara(int id) {
        Camara c = new Camara();
        CamaraDAO cdao = new CamaraDAO();
        ArrayList<Camara> Camaras = new ArrayList<>();
        Camaras = (ArrayList<Camara>) cdao.load();
        for (Camara ca : Camaras) {
            if (ca.getCodigo_camara() == id) {
                c = ca;
            }
        }

        return c;
    }

}
