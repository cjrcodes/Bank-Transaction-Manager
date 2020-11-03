package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

/**
 * Main class, runs the execution of Project 3. Connects the FXML document with the controller and forms a scene to run on the primary stage.
 * 
 * @author Christian Rodriguez, Yazhini Shanmugam
 *
 */
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("TransactionManager.fxml"));

			FXMLLoader loader = new FXMLLoader(getClass().getResource("TransactionManager.fxml"));
			
			Controller controller = new Controller();
			loader.setController(controller);
			
			//controller.initialize();
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Transaction Manager (Project 3)");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
