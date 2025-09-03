package guitest;

import java.io.IOException;

import domein.DomeinController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class MainMenuController extends BorderPane{

	public DomeinController dc;
	public HomeScreen homescreen;
	
	public MainMenuController(HomeScreen homeScreen, DomeinController controller) throws IOException {
		this.homescreen = homeScreen;
		this.dc = controller;
		}
	
	public MainMenuController(DomeinController controller) {
		// TODO Auto-generated constructor stub
	}

	public void LoginMenu() throws IOException {
		this.homescreen.showSelectionMenu();
	}
	
	public void showGameScreen() throws IOException {
		this.homescreen.showGameScreen();
	}
	
	public void closeApllication() throws IOException {
		Platform.exit();
	}

}
