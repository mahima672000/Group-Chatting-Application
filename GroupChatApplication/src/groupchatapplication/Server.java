package groupchatapplication;
import java.net.*;//net package for socket or ServerSocket
import java.io.*;
import java.util.*;

public class Server  implements Runnable{ //class
    //whenever we implement any Interface in our class then we gotta override all the abstract methods of that Interface..if u won't do that u gotta make ur class abstract
    //but this will disallow us to create objects of this abstract class
    //so we will override the method run (which is a method inside Runnable Interface)
    Socket socket; //globally store
    
    public static Vector client = new Vector();
    
    public Server( Socket socket){
        try{
            this.socket = socket;//socket globally initialised
        }catch (Exception e){ //exception handling
            e.printStackTrace();
        }
    }
    
    public void run() {//overriding run method 
        //this is a multi-threading fn so to call this we won't use run(); we gotta use start method (which calls Thread class' object)
        try{
        //read msgs which client sends using BufferedReader Class & write msgs using BufferedWriterClass
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));//here socket helps in taking input from clients
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));///send msgs to the socket
            
           
            client.add(writer);//add all the msgs from the output stream to the vector client
            
            
            while(true){//infinte loop ..as msgs can keep coming ..&this is for broadcasting the msgs
                String data =reader.readLine().trim();//msgs will come from reader and if there is whitespaces in the front & beginning we can trim it with trim fn
                System.out.println("Received" + data);//printing the msg received
                
                
                
                for(int i=0; i<client.size();i++){//for all the clients ..do try catch
                    
                    try{
                        BufferedWriter bw =  (BufferedWriter) client.get(i);//for every client client.get(i) will get all the clients and BufferedWriter will be used to typecast and store the clients in bw it
                        bw.write(data); //broadcasting
                        bw.write("\r\n");
                        bw.flush();//to send the data
                        
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
                
            }
        
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) throws Exception { //method
        ServerSocket s = new ServerSocket(2003); //creating the object s & 2003 is the port number
        while(true){ //infinite while loop coz we don't know how many clients will be there ... and to connect all the clients
            Socket socket = s.accept(); //to make client we have to create Socket class' object "socket" 7 accepting all the clients with the help of "s"
            Server server = new Server(socket);//making server class' object and socket is passed and hence constructor is called
        //now i have to use multithreading concept to run multiple clients simultaneously
        //and for that i have to create multiple threads
        //and with the help of those threads we can broadcast our messages
        //there are 2 ways to use multithreading concept 
        //1st is we can extend thread class 2nd is we can implement Runnable interface
        //here I am doing the 2nd way ..ie to implement Runnable interface
        //now after this we have to explicitly create an object for thread class
            Thread thread = new Thread(server);//pass Server class' object through Thread class' object...to connect clients to server
            thread.start();//it calls run method..which is a multi-threading method & what id does is 
            //if a client sends a msg it goes to the server, server will read the msg and it will broadcast it to all the clients
            //so what run method will do is to read and then write those msgs
        
        
        }
        
    }
}
