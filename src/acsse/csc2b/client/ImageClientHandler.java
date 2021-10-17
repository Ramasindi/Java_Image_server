/**
 * 
 */
package acsse.csc2b.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * @author Thalukanyo
 *
 */
public class ImageClientHandler extends GridPane{

	private static Socket socket;
	private static OutputStream os;
	private static InputStream is;
	private static PrintWriter pw;
	private static BufferedReader br;
	private static DataOutputStream dos;
	private static DataInputStream dis;

	private Button btn = new Button("Click to upload Traditional Images");
	private Button btnImagelist = new Button("Click to request Image list");
	private Button btnRequestedImg = new Button("Click to Retrieve Image");
	private final Text actiontarget = new Text();
	 private TextArea messageBox = new TextArea();
	 private FileChooser fil_chooser = new FileChooser();
	 private TextField IDRequested = new TextField();
	 private TextField IDinput = new TextField();
	 private HBox hbBtnImagelist = new HBox(10);
	 private Text scenetitle = new Text("Welcome to Heritage Month Network");
	 private HBox hbBtn = new HBox(10);
	 
	public ImageClientHandler() {}
	public ImageClientHandler(Stage stage) {
		userInterface();
		btn.setOnAction((e)->{
			actiontarget.setFill(Color.FIREBRICK);
            File file = fil_chooser.showOpenDialog(stage);
            if (file != null) {
                
            	 actiontarget.setText(file.getName() 
                                    + "  selected");
            }
		});
		btnImagelist.setOnAction((e)->{
			
			File fileList = new File("data/server/ImgList.txt");
			Scanner sc = null;
			try {
				sc = new Scanner(fileList);
				String line = "";
				while(sc.hasNext())
				{
					line = sc.next();
					messageBox.appendText(line + "\n");
					
				}
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			sc.close();
			
		});
		btnRequestedImg.setOnAction((e)->{
			int idToGet = Integer.parseInt(IDinput.getText());
			pw.println("DOWNLOAD " + idToGet);
		 	pw.flush();
		 	String response = "";
		 	try {
		 		
		 		response = br.readLine();
		 		System.out.println(response);
		 		int filesize = Integer.parseInt(response);
		 		IDRequested.setText("Size: " + response + " Name: french-croissant.jpg");
		 		String fileToRecieve = "french-croissant.jpg";
		 		File fileDownloaded = new File("data/client/" + fileToRecieve);
		 		FileOutputStream fos = null;
		 		fos = new FileOutputStream(fileDownloaded);
		 		byte[] buffer = new byte[2048];
		 		int n = 0;
		 		int totalBytes = 0;
		 		
		 		while(totalBytes != filesize)
		 		{
		 			n = dis.read(buffer,0,buffer.length);
		 			fos.write(buffer,0,n);
		 			fos.flush();
		 			totalBytes +=n;
		 		}
		 		System.out.println("File Saved to Client");
		 		fos.close();
		 	}catch(IOException ioe1)
		 	{
		 		ioe1.printStackTrace();
		 	}
		});
		
	}	
	
	private void userInterface()
	{
		
	    setAlignment(Pos.TOP_CENTER);
	    setHgap(40);
	    setVgap(10);
	    setPadding(new Insets(5, 5, 5, 5));
	    
	    scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	    add(scenetitle, 0, 0, 2, 1);
	    
	    hbBtn.setAlignment(Pos.BOTTOM_LEFT);
	    hbBtn.getChildren().add(btn);
	    add(hbBtn, 0, 2);
	    add(actiontarget, 0, 3);
  
	    messageBox.setPrefWidth(300);
	    messageBox.setMaxSize(300, 300);
	    messageBox.setWrapText(true);
	    add(messageBox, 0, 5,1,5);
	   
	    
	    hbBtnImagelist.setAlignment(Pos.BOTTOM_LEFT);
	    hbBtnImagelist.getChildren().add(btnImagelist);
	    add(hbBtnImagelist, 0, 4);    
	    Label IDtoRetrieve = new Label("Enter ID To Retrieve an Image:");
	    add(IDtoRetrieve, 0, 10);   
	    add(IDinput, 0, 11);
	    add(IDRequested, 0, 13);

	   
	    HBox htbRequestedImg = new HBox(10);
	    htbRequestedImg.setAlignment(Pos.BOTTOM_LEFT);
	    htbRequestedImg.getChildren().add(btnRequestedImg);
	    add(htbRequestedImg, 0, 12);
	}
	

 public void connect() {
	//Client connection establishment, when then client runs it connects to the server port
		try {
			socket = new Socket("localhost", 7007);
			os = socket.getOutputStream();
			is = socket.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));
			pw = new PrintWriter(os);
			dis = new DataInputStream(is);
			dos = new DataOutputStream(os);
		}catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
 }
}
 
