/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.servidorhilos;

import com.mycompany.servidorhilos.CamaraDAO;
import com.mycompany.servidorhilos.GESCON;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author manue
 */
public class ServidorApp {
    public static void main(String[] args) throws IOException {
      
        CamaraDAO cdao = new CamaraDAO();
        Scanner teclado = new Scanner(System.in);
        String msn = "";

      ServerSocket server = null;
        Socket socket = null;
        GESCON thread = null;
        final int port = 55000;
        
             try{
            server = new ServerSocket(port);
            System.out.println("Esperando conexiones en puerto: " + port);


            while(true) {
                socket = server.accept();
                System.out.println("Se ha conectado un cliente: " + socket.getRemoteSocketAddress());
                thread = new GESCON(socket);
                thread.run();

            }



        }catch (IOException e){
            e.printStackTrace();
        }
    }
    }     

  
    
    
    

