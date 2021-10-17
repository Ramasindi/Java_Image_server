package acsse.csc2b.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * @author Thalukanyo
 *
 */
public class ImageHandler implements Runnable{
	private Socket incomingConnection;
	private OutputStream os;
	private InputStream is;
	private PrintWriter pw;
	private BufferedReader br;
	private DataOutputStream dos;
	private DataInputStream dis;
	
	/**
	 * Image handler Constructor
	 *
	 */
	public ImageHandler(Socket socket)
	{
		this.incomingConnection = socket;
		try {
			os = incomingConnection.getOutputStream();
			is = incomingConnection.getInputStream();
			pw = new PrintWriter(os);
			br = new BufferedReader(new InputStreamReader(is));
			dos = new DataOutputStream(os);
			dis = new DataInputStream(is);
		}catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	@Override
	public void run() {
		
		System.out.println("Client request handling in process...");
		boolean processing = true;
		try {
			while(processing)
			{
				String message = br.readLine();
				System.out.println("Message: " + message);
				//Downloading
				StringTokenizer st = new StringTokenizer(message);
				String command = st.nextToken().toUpperCase();
				switch(command)
				{
				case "DOWNLOAD":
					String fileID = st.nextToken();
					String fileName = "";
					File fileList = new File("data/server/ImgList.txt");
					Scanner sc = new Scanner(fileList);
					String line = "";
					while(sc.hasNext())
					{
						line = sc.next();
						StringTokenizer tokeniser = new StringTokenizer(line);
						String id = "1";//tokeniser.nextToken();
						String fname = "french-croissant.jpg";//tokeniser.nextToken();
						if(id.equals(fileID))
						{
							fileName = fname;
						}
					}
					sc.close();
					System.out.println("Name of requested file: " + fileName );
					
					File fileToReturn = new File("data/server/" + fileName);
					if(fileToReturn.exists())
					{
						pw.println(fileToReturn.length());
						pw.flush();
						
						FileInputStream fis = new FileInputStream(fileToReturn);
						byte[] buffer = new byte[2048];
						int n = 0;
						while((n = fis.read(buffer))>0)
						{
							dos.write(buffer,0,n);
							dos.flush();
						}
						fis.close();
						System.out.println("File sent");
					}
					break;
				   default:
					   break;
				}
			}
		}catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}

}
