package Class;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;
import java.util.Vector;


 
public class Receive {
 
 //   public static  byte[] BUFFER = new byte[4096];
    MulticastSocket socket = null ;
    DatagramPacket outPacket = null; 
    public static final int PORT = 9999;

    DatagramPacket inPacket = null;
    InetAddress address = null ;
    Scanner key ; 
    Vector<String> contact = new Vector<String>() ; 
    public Receive() {
    	 try {
    		 key = new Scanner(System.in); 
             // Get the address that we are going to connect to.
    		  address = InetAddress.getByName(Sender.GROUP_ADDRESS);
			socket = new MulticastSocket(this.PORT);
            socket.joinGroup(address);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 
    }
    public void receive() {
    	try {
    		 byte[] BUFFER = new byte[4096];
    		 // Receive the information and print it.
            inPacket = new DatagramPacket(BUFFER, BUFFER.length);
			socket.receive(inPacket);
			Packet packet = (Packet) deserialize(inPacket.getData()) ;
			
			if(packet.getMess().equals("&&&batdauketnoi")) {
				contact.add(packet.getUsename()); 
			}else if(packet.getMess().equals("show contact")) {
				System.out.println(":" + contact);
			}else {
				String[] arr = packet.getMess().split(" "); 
				System.out.println(arr.length);
				System.out.println(arr[2]); 

					String messSend = ""; 
					for (int i = 3 ; i < arr.length; i ++) {
						messSend = messSend + arr[i] + " "; 
					}
					sendMess(new Packet(packet.getUsename(), arr[2], messSend));
			
			}
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void sendMess(Packet packet) {

        try {
        	//serialize((Object) packet);
        	
            outPacket = new DatagramPacket(serialize((Object) packet), serialize((Object) packet).length, address, Sender.PORT );
			socket.send(outPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	
    }
    public static void main(String[] args) {
       
    	Receive receive = new Receive() ; 
        new Thread(new Runnable() {		
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					while(true) {
						receive.receive(); 

					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

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