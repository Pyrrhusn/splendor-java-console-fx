package gui;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MainMenu extends GridPane {
	public MainMenu() {

		// Titel
		Label lblWelkom = new Label("Splendor");
		TranslateTransition rt = new TranslateTransition(javafx.util.Duration.seconds(2), lblWelkom);
		rt.setDelay(javafx.util.Duration.millis(50));
		rt.setToY(-25);
		rt.setAutoReverse(true);
		rt.setCycleCount(Animation.INDEFINITE);
		rt.play();
		setHalignment(lblWelkom, HPos.CENTER);
		this.add(lblWelkom, 0, 0);
		lblWelkom.getStyleClass().add("lblWelkom");

		// Button Aanmelden
		Button btnlogin = new Button("Login");
		setHalignment(btnlogin, HPos.CENTER);
		this.add(btnlogin, 0, 3);
		btnlogin.setMinSize(500, 40);
		btnlogin.getStyleClass().add("button");
		btnlogin.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 20));

		// Button Stop
		Button btnStop = new Button("Exit");
		setHalignment(btnStop, HPos.CENTER);
		this.add(btnStop, 0, 7);
		btnStop.setMinSize(500, 40);
		btnStop.getStyleClass().add("button");
		btnStop.setOnAction(e -> System.exit(0));
		btnStop.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 20));

		// Layout
		this.setAlignment(Pos.CENTER);
		this.setHgap(20);
		this.setVgap(10);
		this.setPadding(new Insets(25, 25, 25, 25));

		btnlogin.setOnAction(this::buttonLoginIn);

	}

	// ButtonVoorAanmelden
	private void buttonLoginIn(ActionEvent event) {
		LoginScherm loginScherm = new LoginScherm();
		Scene LoginScene = new Scene(loginScherm, 1550, 800);
		String css = getClass().getResource("gui.css").toExternalForm();
		LoginScene.getStylesheets().add(css);

		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(LoginScene);
		stage.show();
		stage.setFullScreen(true);
	}
}
