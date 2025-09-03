package guitest;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import language.Language;
import domein.DomeinController;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class HomeScreen extends BorderPane{
	
	private DomeinController controller; 
	private BorderPane MainMenu;
	private BorderPane LoginMenu;
	private GridPane gameScreen;
	
	public HomeScreen(DomeinController controller) throws IOException {
		this.controller = controller;
		this.MainMenu = new MainMenuController(this, controller);
		this.LoginMenu = new LoginMenuController(this, controller);
		this.showMainMenu();
	}
	
	public DomeinController getDomeinController() {
		return this.controller;
	}
	
	
	public void showMainMenu() throws IOException{
		// TODO Auto-generated method stub
		this.MainMenu = new MainMenuController(this, this.controller);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenuController.fxml"));
		loader.setController(this.MainMenu);
		this.MainMenu = loader.load();
		this.setCenter(MainMenu);
	}
	
	public void showSelectionMenu() throws IOException {
		this.LoginMenu = new LoginMenuController(this, this.controller);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginMenuController.fxml"));
		loader.setController(this.LoginMenu);
		this.LoginMenu = loader.load();
		this.setCenter(LoginMenu);
	}
	
	public void showGameScreen() throws IOException {
		setCenter(gameScreen);
	}	
	
}


