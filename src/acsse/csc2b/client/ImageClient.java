/**
 * 
 */
package acsse.csc2b.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Image Client Class
 * @author Thalukanyo
 *
 */
public class ImageClient extends Application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ImageClientHandler client = new ImageClientHandler();
		client.connect();
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		ImageClientHandler grid = new ImageClientHandler(primaryStage);
        Scene scene = new Scene(grid, 700, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Upload Your Favourite Traditional Dishes");
        primaryStage.show();
		
	}

}
