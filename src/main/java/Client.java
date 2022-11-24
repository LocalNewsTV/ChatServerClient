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
    private LocalMessageCache messageCache;
    private BufferedReader in;
    private Socket socket;
    private PrintWriter out;
    private String userName;
    private boolean activeConnection;
    private String PASSWORD;
    
    public Client(String host, int port, String userName, String password, javax.swing.JTextArea jWindow){
        messageCache = new LocalMessageCache(100);
        HOST = host;
        PORT = port;
        PASSWORD = password;
        this.userName = userName;
        this.jWindow = jWindow;
        
    }
    public void writeToChat(String message){
        out.println(message);
    }
    public void readChat(Socket socket){
        try{in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        activeConnection = true;
        out.println(PASSWORD);
        out.println(userName);
        while(activeConnection){
            try {
                boolean holder = false;
                while(in.ready()){
                    messageCache.store(in.readLine());
                    holder = true;
                }
                if(holder){
                    String messageSet = messageCache.retrieve();
                    jWindow.setText(messageSet);
                    jWindow.setCaretPosition(messageSet.length() - 1);
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
        socket.close();
        }catch(Exception ex){
            disconnect();
            System.out.println(ex);
        }
    }
    public void disconnect(){
        out.println(userName + " has disconnected");
        activeConnection = false;
        
    }
    public void connect(){
        try{
            socket = new Socket(HOST, PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
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
