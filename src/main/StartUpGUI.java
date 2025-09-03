package main;

import domein.DomeinController;
import gui.MainMenu;
import gui.TaalScherm;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartUpGUI extends Application{

	@Override
	public void start(Stage primaryStage)  {
		
		try
		{
			DomeinController dc = new DomeinController();
			MainMenu root = new MainMenu();
			TaalScherm ro = new TaalScherm();
		
			Scene scene = new Scene (ro, 1500, 800);
			
			/*scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
			String css = this.getClass().getResource("style.css").toExternalForm();
			scene.getStylesheets().add(css);*/
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Welcome in Splendor!");
			primaryStage.show();
			primaryStage.setFullScreen(true);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
