/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rawrg
 */
public class Client {
    private final String HOST;
    private final int PORT;
    private javax.swing.JTextArea jWindow;
    private BufferedReader in;
    private Socket socket;
    private PrintWriter out;
    
    public Client(String host, int port, javax.swing.JTextArea jWindow){
        HOST = host;
        PORT = port;
        this.jWindow = jWindow;
        
    }
    public void writeToChat(String message){
        out.println(message);
    }
    public void readChat(Socket socket){
        try{in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        while(true){
            try {
                String messages = "";
                boolean holder = false;
                Thread.sleep(100);
                while(in.ready()){
                    messages += in.readLine() + "\n";
                    holder = true;
                }
                if(holder){
                    jWindow.setText(messages);
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
    public void connect(){
        try{
            socket = new Socket(HOST, PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            /**Do some magic shit here*/
            Runnable read = () -> this.readChat(socket);
            Thread t = new Thread(read);
            t.start();

        } catch (UnknownHostException e){
            System.err.println(e);
            System.exit(-1);
        } catch (IOException e) {
            System.err.println(e);
            System.exit(-2);
        } catch (SecurityException e) {
            System.err.println(e);
            System.exit(-3);
        } catch (IllegalArgumentException e) {
            System.err.println(e);
            System.exit(-4);
        }
    }
}
