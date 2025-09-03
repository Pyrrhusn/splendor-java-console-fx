package gui;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import language.Language;

public class TaalScherm extends StackPane {

	private Image engelsVlag;
	private Image nederlandsVlag;
//	private ImageView achtergrondZicht;
	private ImageView engZicht;
	private ImageView nedZicht;
	private Locale localeTaal;

	public TaalScherm() {
		this.getStylesheets().add(this.getClass().getResource("gui.css").toExternalForm());
		fotos();
		vlag();
		knop();

	}

	private void fotos() {
//		achtergrond = new Image(getClass().getResourceAsStream("/images/mountains.png"));
		engelsVlag = new Image(getClass().getResourceAsStream("/images/Vlaguk.png"));
		nederlandsVlag = new Image(getClass().getResourceAsStream("/images/Vlagned.png"));

//		achtergrondZicht = new ImageView(achtergrond);
		engZicht = new ImageView(engelsVlag);
		nedZicht = new ImageView(nederlandsVlag);

		engZicht.setLayoutX(1);
		engZicht.setLayoutY(400);
		engZicht.setFitHeight(300);
		engZicht.setFitWidth(500);

		nedZicht.setLayoutX(1);
		nedZicht.setLayoutY(400);
		nedZicht.setFitHeight(300);
		nedZicht.setFitWidth(500);

		Rectangle2D screen = Screen.getPrimary().getBounds();
//		achtergrondZicht.setFitHeight(screen.getHeight());
//		achtergrondZicht.setFitWidth(screen.getWidth());
//		this.getChildren().add(achtergrondZicht);

	}

	private void vlag() {
		Language.setRecourseBudel(ResourceBundle.getBundle("language/Language"));

		engZicht.setOnMouseClicked(e -> {
			Stage stage = (Stage) getScene().getWindow();
			MainMenu menu = new MainMenu();
			Scene scene = new Scene(menu);
			stage.setScene(scene);

		});

		nedZicht.setOnMouseClicked(e -> {
			Stage stage = (Stage) getScene().getWindow();
			MainMenu menu = new MainMenu();
			Scene scene = new Scene(menu);
			stage.setScene(scene);

		});

		HBox vlag = new HBox(60);
		vlag.setAlignment(Pos.CENTER);
		vlag.setPadding(new Insets(0, 400, 50, 300));
		vlag.getChildren().addAll(nedZicht, engZicht);
		BorderPane.setAlignment(vlag, Pos.CENTER);
		this.getChildren().add(vlag);
	}

	private void knop() {
		Button nedbutton = new Button("Nederlands");
		nedbutton.setMinHeight(40);
		nedbutton.setMinWidth(300);
		nedbutton.setStyle(
				"-fx-background-color: linear-gradient(#ff9933, #b36600), radial-gradient(center 50% 50%, radius 75%, #ffcc99, #ffb84d); -fx-background-radius: 30; -fx-background-insets: 0; -fx-text-fill: black; -fx-font-family: 'Press Start 2P', sans-serif; -fx-font-size: 22px;");
		nedbutton.setStyle(
				"-fx-background-color: linear-gradient(#D8D8D8, #D8D8D8), radial-gradient(center 50% 50%, radius 75%, #D8D8D8, #D8D8D8); -fx-background-radius: 30; -fx-background-insets: 0; -fx-text-fill: black; -fx-font-family: 'Press Start 2P', sans-serif; -fx-font-size: 22px;");
		nedbutton.setOnMouseEntered(e -> {
			nedbutton.setStyle(
					"-fx-background-color: linear-gradient(#2d2d2d, #1a1a1a), radial-gradient(center 50% 50%, radius 75%, #999999, #666666); -fx-background-radius: 30; -fx-background-insets: 0; -fx-text-fill: white; -fx-font-family: 'Press Start 2P', sans-serif; -fx-font-size: 20px;");
		});
		nedbutton.setOnMouseExited(e -> {
			nedbutton.setStyle(
					"-fx-background-color: linear-gradient(#D8D8D8, #D8D8D8), radial-gradient(center 50% 50%, radius 75%, #D8D8D8, #D8D8D8); -fx-background-radius: 30; -fx-background-insets: 0; -fx-text-fill: black; -fx-font-family: 'Press Start 2P', sans-serif; -fx-font-size: 22px;");
		});
		nedbutton.setOnAction(e -> {
			localeTaal = new Locale("NL");
			Language.setRecourseBudel(ResourceBundle.getBundle("language/Language_nl", localeTaal));
			Stage stage = (Stage) getScene().getWindow();
			MainMenu Menu = new MainMenu();
			Scene scene = new Scene(Menu, 1600, 840);
			String css = getClass().getResource("gui.css").toExternalForm();
			scene.getStylesheets().add(css);
			stage.setScene(scene);
			stage.show();
			stage.setFullScreen(true);
		});

		Button engbutton = new Button("English");
		engbutton.setMinWidth(300);
		engbutton.setMinHeight(40);
		engbutton.setStyle(
				"-fx-background-color: linear-gradient(#D8D8D8, #D8D8D8), radial-gradient(center 50% 50%, radius 75%, #D8D8D8, #D8D8D8); -fx-background-radius: 30; -fx-background-insets: 0; -fx-text-fill: black; -fx-font-family: 'Press Start 2P', sans-serif; -fx-font-size: 22px;");
		engbutton.setOnMouseEntered(e -> {
			engbutton.setStyle(
					"-fx-background-color: linear-gradient(#2d2d2d, #1a1a1a), radial-gradient(center 50% 50%, radius 75%, #999999, #666666); -fx-background-radius: 30; -fx-background-insets: 0; -fx-text-fill: white; -fx-font-family: 'Press Start 2P', sans-serif; -fx-font-size: 20px;");
		});
		engbutton.setOnMouseExited(e -> {
			engbutton.setStyle(
					"-fx-background-color: linear-gradient(#D8D8D8, #D8D8D8), radial-gradient(center 50% 50%, radius 75%, #D8D8D8, #D8D8D8); -fx-background-radius: 30; -fx-background-insets: 0; -fx-text-fill: black; -fx-font-family: 'Press Start 2P', sans-serif; -fx-font-size: 22px;");
		});

		engbutton.setOnAction(e -> {
			localeTaal = new Locale("EN");
			Language.setRecourseBudel(ResourceBundle.getBundle("language/Language_en", localeTaal));
			Stage stage = (Stage) getScene().getWindow();
			MainMenu Menu = new MainMenu();
			Scene scene = new Scene(Menu, 1600, 840);
			String css = getClass().getResource("gui.css").toExternalForm();
			scene.getStylesheets().add(css);
			stage.setScene(scene);
			stage.show();
			stage.setFullScreen(true);
		});

		// posities
		HBox buttonBox = new HBox(230);
		buttonBox.setPadding(new Insets(400, 450, 50, 300));
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.getChildren().addAll(nedbutton, engbutton);
		StackPane.setAlignment(buttonBox, Pos.BOTTOM_CENTER);
		this.getChildren().add(buttonBox);
	}

}
