package gui;

import java.sql.SQLException;
import java.time.Year;
import java.util.List;

import domein.DomeinController;
import domein.Speler;
import dto.SpelerDTO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import language.Language;

public class LoginScherm extends GridPane {
	private Label lblMessage;
	// private final DomeinController dc;

	private final TextField textfieldnaam = new TextField();
	private final TextField textfieldgeboortejaar = new TextField();
	private final Label foutbericht = new Label();
	private Label Label;
	private Label Name;
	private List<Speler> geselcteerdeSpelers;
	private DomeinController dc;
//	private String spelerNaam;
//	private String spelerGeboorteJaar;
	private Button btnSpelen;
	private Button btnSignIn;

	public LoginScherm()// throws SQLException
	{

		this.dc = new DomeinController();
		// this.geselcteerdeSpelers=dc.getSpelers(); //deze methode werkt niet pushwant

		// Labels en textfields
		Label lblWelkom = new Label("Vul uw gegevens in!");
		lblWelkom.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 30));
		lblWelkom.getStyleClass().add("lblWelkom");

		this.add(lblWelkom, 0, 0, 2, 1); // eerste voor kolom tweede voor rij
		setStyle("-fx-text-fill:\"white\";");

		add(Name = new Label("Speler Naam:"), 0, 1, 1, 1);
		add(textfieldnaam, 1, 1, 1, 1);

		add(Label = new Label("Geboortejaar:"), 0, 2, 1, 1);
		add(textfieldgeboortejaar, 1, 2, 1, 1);

		Name.setStyle("-fx-text-fill:\"white\";");
		Name.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 15));

		Label.setStyle("-fx-text-fill:\"white\";");
		Label.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 15));

		lblWelkom.setStyle("-fx-text-fill:\"white\";");
		lblWelkom.setTextFill(Color.WHITE);

		// Button Aaanmelden
		btnSignIn = new Button("Aanmelden");
		btnSignIn.setDefaultButton(true);
		foutbericht.getStyleClass().add("foutbericht");
		HBox controls = new HBox(btnSignIn, foutbericht);
		controls.setSpacing(10);
		add(controls, 0, 3, 2, 1);
		btnSignIn.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 15));
		btnSignIn.setStyle("-fx-background-color: #87CEFA; -fx-text-fill: #FFFFFF");

		// button SpelSpelen
		btnSpelen = new Button("Spel Spelen");
		btnSpelen.setDefaultButton(true);
		foutbericht.getStyleClass().add("foutbericht");
		HBox control2 = new HBox(btnSpelen, foutbericht);
		control2.setSpacing(10);
		add(control2, 2, 5, 4, 4);
		btnSpelen.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 15));
		btnSpelen.setStyle("-fx-background-color: #87CEFA; -fx-text-fill: #FFFFFF");
		btnSpelen.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				Stage stage = (Stage) getScene().getWindow();
				SpelScherm Menu = new SpelScherm(dc);
				Scene scene = new Scene(Menu, 1600, 840);
				String css = this.getClass().getResource("gui.css").toExternalForm();
				scene.getStylesheets().add(css);
				stage.setScene(scene);
				stage.setFullScreen(true);
				stage.show();
			}
		});

		// Layout
		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(20, 20, 20, 20));

		// Laatste Label
		lblMessage = new Label("");
		this.add(lblMessage, 1, 5);
		lblMessage.setStyle("-fx-text-fill:\"white\";");
		foutbericht.setStyle("-fx-text-fill:\"white\";");

		// speler inloggen activeren
		btnSignIn.setOnAction(event -> {

			try {
				Spelerselect();

			} catch (NullPointerException | SQLException e) {
				e.printStackTrace();
			}
		});
	}

	private void Spelerselect() throws NullPointerException, SQLException {
		int bDayToInt;
		Year year;
		String spelerNaam = textfieldnaam.getText();
		String spelerGeboorteJaar = textfieldgeboortejaar.getText();

		// alert maken als speler wordt ingelogd
		Alert alert = new Alert(Alert.AlertType.NONE);

		if (spelerNaam.isEmpty() || spelerGeboorteJaar.isEmpty()) {
			alert.setHeaderText("");
			alert.setContentText(Language.geefVertaling("emptyNameOrBirthDay"));
			alert.showAndWait();
			return;
		}

		try {

			bDayToInt = Integer.parseInt(spelerGeboorteJaar);
			year = Year.of(bDayToInt);
		} catch (NumberFormatException e) {
			alert.setHeaderText(Language.geefVertaling("error"));
			alert.setContentText(Language.geefVertaling("BdmustInteger"));
			alert.showAndWait();
			return;
		}

		// hier wordt deingelogde speler getoond met sysout
		try {
			System.out.println(spelerNaam + " " + spelerGeboorteJaar);
			dc.selecteerSpeler(new SpelerDTO(spelerNaam, year));

			// System.out.println(spelerNaam+ " "+spelerGeboorteJaar);
			// System.out.println(dc.geefSpelers().get(0).getGebruikersnaam());
			System.out.println("hier is al geselcteerde spelers");
			for (SpelerDTO speler : dc.geefGekozenSpelers()) {

				System.out.println(speler.gebruikersnaam());
			}

			// max 4 spelers
			if (dc.geefGekozenSpelers().size() == 5) {

				alert.setHeaderText(Language.geefVertaling("Select4players"));
				alert.setContentText(Language.geefVertaling("maxPlayers_nl_en"));
				alert.showAndWait();
				btnSignIn.setDisable(true);

				btnSpelen.setDisable(true);
			}
		} catch (IllegalArgumentException e) {
			alert.setHeaderText(Language.geefVertaling("error"));
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}

		// methode alert2

		Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
		alert2.setHeaderText("");
		alert2.setContentText("Speler geselecteerd!");
		alert2.showAndWait();
/////////////////////////////////////////////////////////////////////////////////////	        

		// dc vragen voor de speler
		List<SpelerDTO> geselecteerdeSpelers = dc.geefGekozenSpelers();

		if (geselecteerdeSpelers.isEmpty()) {

			alert.setContentText(Language.geefVertaling("error"));
			alert.setContentText(Language.geefVertaling("Select maximum 4 players."));
			alert.showAndWait();
			return;
		}

		// geselecteerde spelers in de UI
		GridPane SpelerPane = new GridPane();
		SpelerPane.setAlignment(Pos.CENTER);
		SpelerPane.setHgap(10);
		SpelerPane.setVgap(10);
		SpelerPane.setPadding(new Insets(25));

		for (int i = 0; i < geselecteerdeSpelers.size(); i++) {
			if (geselecteerdeSpelers.size() < 2 || geselecteerdeSpelers.size() > 4) {
				btnSpelen.setDisable(true);
			} else {
				btnSpelen.setDisable(false);
			}
			String name = geselecteerdeSpelers.get(i).gebruikersnaam();
			Year year2 = geselecteerdeSpelers.get(i).geboortejaar();
			int yr = year2.getValue();
			String birthDay = String.format("%d", yr);

			Label GeselecteerdeSpelers = new Label("Geselecteerde spelers:");
			GeselecteerdeSpelers.setFont(Font.font("Arial", FontWeight.BOLD, 14));
			GeselecteerdeSpelers.setTextFill(Color.WHITE);

			// Voeg de label in de SpelerPane -> GridPane
			SpelerPane.add(GeselecteerdeSpelers, 0, 0, 2, 1);

			TextField SpelerNaam = new TextField(name);
			TextField SpelerGeboortedatum = new TextField(birthDay);
			SpelerNaam.setMinWidth(400);
			SpelerNaam.setMinHeight(50);
			SpelerNaam.setEditable(false);
			SpelerGeboortedatum.setMinWidth(400);
			SpelerGeboortedatum.setMinHeight(50);
			SpelerGeboortedatum.setEditable(false);
			SpelerPane.add(SpelerNaam, 0, i + 1);
			SpelerPane.add(SpelerGeboortedatum, 1, i + 1);

		}
		textfieldnaam.clear();
		textfieldgeboortejaar.clear();

	}
}