
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Rawrg
 */
public class LocalMessageCache {
    private String[] messageCache;
    
    public LocalMessageCache(int size){
        messageCache = new String[size];
    }
    
    public void store(String message){
        for(int i = 0; i < messageCache.length -1; i++){
            messageCache[i] = messageCache[i+1];
        }
        messageCache[messageCache.length -1] = message;
    }
    public String retrieve(){
        String collatedMessages = "";
        for(String message : messageCache){
            if(message != null){
                collatedMessages += message + "\n";
            }
        }
        return collatedMessages;
    }
}
