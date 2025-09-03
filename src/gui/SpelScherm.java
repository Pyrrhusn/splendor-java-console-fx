package gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import domein.DomeinController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

public class SpelScherm extends BorderPane {

	private DomeinController domeincontroller;
	private List<ImageView> kaartTonen;
	private List<Integer> aantalkaarten;
	private ImageView KaartZien;
	private ImageView edelsteenZien;
	private List<ImageView> Ontwikkelingskaarten;
	private ImageView kaartImageZien;
	private Image KaartFoto;
	private Image edelsteenTonen;
	private Image niveauTonen;
	private ImageView niveauZien;
	private VBox edelsteenbox;
	private GridPane kaartGrid;
	private VBox kaartBox;

	public SpelScherm(DomeinController domeinController) {
		this.domeincontroller = domeincontroller;
		// spelscore();
		edele();
		edelsteen();
		niveau();
		maakOntwikkelingsKaarten();

	}

	// achtergrond
	private void achtergrond() {
		Image achtergrond = new Image(getClass().getResourceAsStream("/images/mountains.png"));
		ImageView achtergrondview = new ImageView(achtergrond);

		Rectangle2D screen = Screen.getPrimary().getBounds();
		achtergrondview.setFitHeight(screen.getHeight());
		achtergrondview.setFitWidth(screen.getWidth());
		this.getChildren().add(achtergrondview);
	}

	// edel
	private void edele() {
		kaartTonen = new ArrayList<>();

		aantalkaarten = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
		Collections.shuffle(aantalkaarten);
		HBox edelBox = new HBox(10);

		for (int i = 0; i < 5; i++) {
			int value = aantalkaarten.get(i);
			KaartFoto = new Image(getClass().getResourceAsStream("/images/edelen/2000" + value + ".jpg"));

			KaartZien = new ImageView(KaartFoto);
			KaartZien.setFitWidth(150);
			KaartZien.setFitHeight(150);
			kaartTonen.add(KaartZien);
		}

		edelBox.getChildren().addAll(kaartTonen);
		edelBox.setAlignment(Pos.TOP_LEFT);

		this.setTop(edelBox);
		BorderPane.setMargin(edelBox, new Insets(50, 0, 0, 300));
	}

	// edelsteen
	private void edelsteen() {

		edelsteenbox = new VBox(20);
		for (int i = 1; i <= 5; i++) {

			edelsteenTonen = new Image(getClass().getResourceAsStream("/images/edelstenen/00" + i + ".jpg"));

			edelsteenZien = new ImageView(edelsteenTonen);
			edelsteenZien.setFitWidth(100);
			edelsteenZien.setFitHeight(100);
			edelsteenbox.getChildren().add(edelsteenZien);
			edelsteenbox.setSpacing(25);

			edelsteenbox.setAlignment(Pos.CENTER_LEFT);
			this.setBottom(edelsteenbox);
			BorderPane.setMargin(edelsteenbox, new Insets(60, -0, 60, 0));

		}
	}

	// niveau's
	private void niveau() {
		VBox niveaubox = new VBox(20);
		for (int i = 3; i >= 1; i--) {

			niveauTonen = new Image(getClass().getResourceAsStream("/images/Kaartniveau/00" + i + ".jpg"));

			niveauZien = new ImageView(niveauTonen);
			niveauZien.setFitWidth(150);
			niveauZien.setFitHeight(200);
			niveaubox.getChildren().add(niveauZien);

			niveaubox.setAlignment(Pos.CENTER_RIGHT);
			this.setBottom(niveaubox);
			BorderPane.setMargin(niveaubox, new Insets(-60, 90, 60, 0));

			HBox Niveau_edelstenen = new HBox();
			Niveau_edelstenen.getChildren().addAll(edelsteenbox, niveaubox);
			this.setLeft(Niveau_edelstenen);
			BorderPane.setMargin(Niveau_edelstenen, new Insets(-0, 0, 60, 200));

		}
	}

	// ontwikkelingskaarten
	private GridPane maakOntwikkelingsKaarten() {
		GridPane niveauKaartenPane = new GridPane();
		niveauKaartenPane.setAlignment(Pos.CENTER);
		niveauKaartenPane.setHgap(10);
		niveauKaartenPane.setVgap(10);
		niveauKaartenPane.setPadding(new Insets(25));
		// GridPane.setMargin(kaartenPane, new Insets(-60,90,60,0));

		voegKaartenToeAanPane(niveauKaartenPane, 1, 1, 40, "/images/kaarten/niveau1/");
		voegKaartenToeAanPane(niveauKaartenPane, 2, 41, 70, "/images/kaarten/niveau2/");
		voegKaartenToeAanPane(niveauKaartenPane, 3, 71, 90, "/images/kaarten/niveau3/");

		this.setCenter(niveauKaartenPane);
		return niveauKaartenPane;
	}

	// kaarten in kolom zetten
	private void voegKaartenToeAanPane(GridPane pane, int rij, int beginIndex, int eindeIndex, String basisPad) {
		int kolom = 0;
		for (int i = beginIndex; i <= eindeIndex; i++) {
			String pad = basisPad + String.format("%03d", i) + ".jpg";
			ImageView kaartView = new ImageView(new Image(getClass().getResourceAsStream(pad)));
			kaartView.setFitWidth(120);
			kaartView.setFitHeight(160);
			pane.add(kaartView, kolom, rij - 1);

			kolom++;
			if (kolom >= 4) {
				kolom = 0;
			}
		}
	}

	// spelscore
	private void spelscore() {

	}

}