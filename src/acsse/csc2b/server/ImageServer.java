/**
 * 
 */
package acsse.csc2b.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Thalukanyo
 *
 */
public class ImageServer {
	private ServerSocket serverSocket;
	private boolean cont;
	
	/**
	 * Image server constructor
	 */
	public ImageServer(int port)
	{
		try {
			serverSocket = new ServerSocket(port);
			cont = true;
			establishServer();
		}catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	
	/**
	 * void method for starting server
	 */
	private void establishServer() {
		System.out.println("Establishing Image Server");
		while(cont)
		{
			try {
				Socket incomingConnection = serverSocket.accept();
				System.out.println("Client Connected");
				ImageHandler imageHandler = new ImageHandler(incomingConnection);
				Thread thread = new Thread(imageHandler);
				thread.start();
			}catch(IOException ioe)
			{
				ioe.printStackTrace();
			}
			
		}
	}
	
	public static void main(String[] args) {
		ImageServer server = new ImageServer(7007);
	}

}
