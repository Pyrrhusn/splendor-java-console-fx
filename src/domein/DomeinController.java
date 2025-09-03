package domein;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dto.EdelsteenDTO;
import dto.SpelDTO;
import dto.SpelerDTO;
import util.EdelsteenType;

/**
 * Representeert de klasse die de brug vormt tussen de UI en het speldomein. Een
 * object van deze klasse is te beschouwen als een <em>entry node</em>.
 */
public class DomeinController {
	private Spel spel;
	private SpelerRepository spelerRepo;
	private OntwikkelingskaartRepository ontwikkelingskaartRepo;
	private EdeleRepository edeleRepo;

	/**
	 * Construeert een {@link DomeinController} object en roept de constructor van
	 * {@link SpelerRepository} op om deze in een geldige staat aan te maken.
	 */
	public DomeinController() {
		spelerRepo = new SpelerRepository();
	}

	/**
	 * Maakt en stelt in een instantie object van {@link EdeleRepository},
	 * {@link OntwikkelingskaartRepository} en {@link Spel}.
	 * 
	 * @throws FileNotFoundException indien een data bestand voor aanmaken van
	 *                               ontwikkelingskaarten ontbreekt
	 */
	public void maakSpelAan() throws FileNotFoundException {
		edeleRepo = new EdeleRepository();
		ontwikkelingskaartRepo = new OntwikkelingskaartRepository();
		spel = new Spel(spelerRepo.getSpelers(), vulEdelenAanSpel(spelerRepo.getSpelers().size()),
				vulEdelstenenAanSpel(spelerRepo.getSpelers().size()), ontwikkelingskaartRepo.deelKaartUit(1, 4),
				ontwikkelingskaartRepo.deelKaartUit(2, 4), ontwikkelingskaartRepo.deelKaartUit(3, 4));
	}

	/**
	 * Methode om de gewenste speler op te halen uit de databank en toe te voegen
	 * aan de spelers repository
	 * 
	 * @param speler een {@link SpelerDTO} object die de op te halen speler
	 *               voorstelt via de <em>naam</em> en <em>geboortejaar</em>
	 * @return true - speler is correct gevonden en toegevoegd; false - speler werd
	 *         al eerder gekozen
	 * @throws NullPointerException     als een speler niet bestaat/niet werd
	 *                                  gevonden
	 * @throws SQLException             als er iets misgaat met bij het verbinden
	 *                                  met/ophalen van resultaten van de database
	 * @throws IllegalArgumentException als er een fout optreedt bij het creëren van
	 *                                  een {@link Speler} object
	 */
	public boolean selecteerSpeler(SpelerDTO speler)
			throws NullPointerException, SQLException, IllegalArgumentException {
		return spelerRepo.selecteerSpeler(speler.gebruikersnaam(), speler.geboortejaar());
	}

	/**
	 * Ophalen van een hoeveelheid edelen afhankelijk van aantal spelers in het spel
	 * 
	 * @param aantalSpelers aantal spelers in het spel
	 * @return een lijst van willekeurige edelen
	 */
	private List<Edele> vulEdelenAanSpel(int aantalSpelers) {
		return edeleRepo.vulEdelenAanSpel(aantalSpelers);
	}

	/**
	 * Ophalen van een hoeveelheid edelstenen van elk kleur afhankelijk van aantal
	 * spelers in het spel
	 * 
	 * @param aantalSpelers aantal spelers in het spel
	 * @return een lijst van elk kleur van edelstenenfiches
	 */
	private List<Edelsteen> vulEdelstenenAanSpel(int aantalSpelers) {
		List<Edelsteen> edelstenen = new ArrayList<>();
		int aantalFiches = aantalSpelers == 2 ? 4 : aantalSpelers == 3 ? 5 : 7;

		for (EdelsteenType type : EdelsteenType.values()) {
			edelstenen.add(new Edelsteen(aantalFiches, type));
		}

		return edelstenen;
	}

	/**
	 * De eerste/bovenste kaart van een aangegeven niveau van de stapel halen en in
	 * het spel bijvullen
	 * 
	 * @param niveau niveau van de stapel kaarten
	 * @param index  de plaats/index voor de nieuwe kaart in zijn niveau stapel
	 */
	private void voegZichtbaarKaartToe(int niveau, int index) {
		if (ontwikkelingskaartRepo.geefAantalKaartenInStapel(niveau) != 0) {
			spel.voegZichtbaarKaartToe(niveau, index, ontwikkelingskaartRepo.deelKaartUit(niveau, 1).get(0));
		}
	}

	/**
	 * Getter methode voor alle kaarten met een gegeven niveau in het spel.
	 * 
	 * @param niveau stapel kaarten die correspondeert met de niveau
	 * @return een lijst van de gevraagde stapel kaarten
	 */
	public List<Ontwikkelingskaart> getOntwikkelingskaarten(int niveau) {
		return spel.getOntwikkelingskaarten(niveau);
	}

	/**
	 * Getter methode om de edelen in het spel op te halen
	 * 
	 * @return een lijst van edelen
	 */
	public List<Edele> getEdelen() {
		return spel.getEdelen();
	}

	/**
	 * Getter methode om de edelsteenfiches in het spel op te halen
	 * 
	 * @return een lijst van edelsteenfiches
	 */
	public List<Edelsteen> getEdelstenen() {
		return spel.getEdelstenen();
	}

	/**
	 * Getter methode om de spelers in het spel op te halen
	 * 
	 * @return een lijst van spelers
	 */
	public List<Speler> getSpelers() {
		return spel.getSpelers();
	}

	/**
	 * Gekozen spelers voor aanvang van het spel ophalen om ze in de menu te tonen
	 * 
	 * @return lijst van spelers als {@link SpelerDTO}
	 */
	public List<SpelerDTO> geefGekozenSpelers() {
		return spelerRepo.getSpelers().stream().map((speler) -> new SpelerDTO(speler))
				.collect(Collectors.toUnmodifiableList());
	}

	/**
	 * Geeft het aantal resterende kaarten van een bepaalde niveau stapel
	 * 
	 * @param niveau niveau van de stapel van de gedekte stapel
	 * @return aantal kaarten in een stapel
	 */
	public int geefAantalKaartenInStapel(int niveau) {
		return ontwikkelingskaartRepo.geefAantalKaartenInStapel(niveau);
	}

	/**
	 * Zet het spel om naar een {@link SpelDTO} object om door te geven naar de UI
	 * en om de huidige toestand van het spel (in de UI) te updaten.
	 * 
	 * @return het spel als een {@link SpelDTO} object
	 */
	public SpelDTO getSpel() {
		return new SpelDTO(spel);
	}

	/**
	 * Methode die transacties omtrent edelstenen waarbij er optelling of aftrekking
	 * gebeurt uitvoert. De <em>target</em> is het doel van de transactie en de
	 * <em>edelsteen</em> is de basis van deze verandering.
	 * 
	 * @param target    <code>true</code> - transactie van edelsteen gebeurt in
	 *                  richting spel -> speler vorraad; <code>false</code> -
	 *                  transactie van edelsteen gebeurt in richting speler -> spel
	 *                  vorraad
	 * 
	 * @param edelsteen de toe te voegen of terug te krijgen edelsteen van de target
	 */
	public void actieEdelsteenVoegEnAf(boolean target, EdelsteenDTO edelsteen) {
		spel.actieEdelsteenVoegEnAf(target, new Edelsteen(edelsteen.aantal(), edelsteen.kleur()));
	}

	/**
	 * De speler aan de beurt instellen door de index ervan in de lijst van spelers,
	 * in het spel
	 * 
	 * @param index index van speler in de lijst van spelers
	 */
	public void setSpelSpelerAanBeurt(int index) {
		spel.setSpelerAanBeurt(index);
	}

	/**
	 * Controleren of een ontwikkelingskaart kan gekocht worden door de speler die
	 * aan beurt is en daarna een nieuwe kaart op diezelfde plaats zetten, indien
	 * mogelijk, door {@link #voegZichtbaarKaartToe(int, int)}.
	 * 
	 * @param niveau niveau van de gekozen kaart
	 * @param index  plaats van de gekozen kaart uit de (vier) zichtbare kaarten
	 * @return true - gewenste kaart werd aangekocht; false - gewenste kaart kan
	 *         niet gekocht worden
	 */
	public boolean actieSpelerKaartKopen(int niveau, int index) {
		if (spel.actieSpelerKaartKopen(niveau, index)) {
			voegZichtbaarKaartToe(niveau, index);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * De edele op de gegeven index in de lijst van edelen brengt bezoek aan de
	 * speler aan beurt. Deze edele wordt uit het spel gehaald en in de voorraad van
	 * de speler geplaatst.
	 * 
	 * @param index plaats/index van de edele
	 */
	public void eventSpecialeTegel(int index) {
		spel.eventSpecialeTegel(index);
	}

	/**
	 * Controleert als het spel aan zijn laatste ronde is omdat iemand 15 of meer
	 * prestigepunten heeft.
	 */
	public void eventIsLaatsteRonde() {
		spel.eventIsLaatsteRonde();
	}
}
