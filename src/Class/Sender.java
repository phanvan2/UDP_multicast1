package Class;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.Vector;

 
public class Sender {
 
    public static final String GROUP_ADDRESS = "224.0.0.1";
    public static final int PORT = 8888;
    MulticastSocket socket = null;
    DatagramPacket outPacket = null;
    DatagramPacket inPacket = null;
    byte[] BUFFER = new byte[4096];
    Scanner key ; 
    InetAddress address ; 
    
    private String username ; 
    Vector<String> contact = new Vector<String>(); 
    
    public Sender() {
         key = new Scanner(System.in); 
         
        System.out.print("nhập tên: " );
        this.username = key.nextLine(); 
       
        System.out.println("Nhập mess: ");
    	try {
    		 DatagramPacket inPacket = null;
    	     
			 address = InetAddress.getByName(GROUP_ADDRESS);
			 socket = new MulticastSocket(Sender.PORT);
	            socket.joinGroup(address);
	            sendMess(new Packet(this.username, "&&&batdauketnoi"));
	           
		} catch (Exception e) {
			e.printStackTrace();
		}
    	 
    	           
    }
    public Sender(String username) {
    	this.username = username; 
         key = new Scanner(System.in); 
    	try {
    		 DatagramPacket inPacket = null;
    	     
			 address = InetAddress.getByName(GROUP_ADDRESS);
			 socket = new MulticastSocket(Sender.PORT);
	            socket.joinGroup(address);
	            sendMess(new Packet(this.username, "&&&batdauketnoi"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 
    	           
    }
    public void sendMess(String msg) {
    	//System.out.println("Client: ");
//    	String msg = key.nextLine();
    	Packet packet = new Packet(this.username, msg);

        try {
        	//serialize((Object) packet);

            outPacket = new DatagramPacket(serialize((Object) packet), serialize((Object) packet).length, address, Receive.PORT );
			socket.send(outPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void sendMess(Packet packet) {
    	//System.out.println("Client: ");

        try {
            outPacket = new DatagramPacket(serialize((Object) packet), serialize((Object) packet).length, address, Receive.PORT );
			socket.send(outPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    

    public static void main(String[] args) {
    	Sender send = new Sender() ; 
//    	
//        new Thread(new Runnable() {		
//    		@Override
//    		public void run() {
//    			// TODO Auto-generated method stub
//    			try {
//    				while(true) {
//    					send.receive(); 
//
//    				}
//    			} catch (Exception e) {
//    				// TODO Auto-generated catch block
//    				e.printStackTrace();
//    			}
//    		}
//    	}).start();
    }

    public void receive() {
    	try {
    		 byte[] BUFFER = new byte[4096];
    		 // Receive the information and print it.
            inPacket = new DatagramPacket(BUFFER, BUFFER.length);
			socket.receive(inPacket);
			Packet packet = (Packet) deserialize(inPacket.getData()) ;
         
			
			if( !this.username.equals(packet.getUsename()) ) {
				if(packet.getNameReceive().equals("*")) {
					System.out.println( packet.getUsename()+ "( "+inPacket.getAddress()+"): " + packet.getMess());
				}else if(this.username.equals(packet.getNameReceive()) )
					System.out.println( packet.getUsename()+ "( "+inPacket.getAddress()+"): " + packet.getMess());
				
			}
		

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public String receiveMess() {
    	try {
    		 byte[] BUFFER = new byte[4096];
    		 // Receive the information and print it.
            inPacket = new DatagramPacket(BUFFER, BUFFER.length);
			socket.receive(inPacket);
			Packet packet = (Packet) deserialize(inPacket.getData()) ;
		
			
			
			
			if( !this.username.equals(packet.getUsename()) ) {
				System.out.println("hahaha");
				int i = (int)(packet.getNameReceive().charAt(0)); 
				if(i  == 42 ) {
					return ( packet.getUsename()+ "( "+inPacket.getAddress()+"): " + packet.getMess());
				}else if(this.username.equals(packet.getNameReceive()) )
					return ( packet.getUsename()+ "( "+inPacket.getAddress()+"): " + packet.getMess());
				
			}
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return null ; 
		
    }
    
    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }
    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }
}