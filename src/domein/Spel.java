package domein;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Klasse die een spel en spel logica representeert.
 */
public class Spel {
	private List<Speler> spelers;
	private List<Edele> edelen;
	private List<Edelsteen> edelstenen;
	private List<Ontwikkelingskaart> zichtbaarKaartenEen;
	private List<Ontwikkelingskaart> zichtbaarKaartenTwee;
	private List<Ontwikkelingskaart> zichtbaarKaartenDrie;
	private Speler spelerAanBeurt;
	private final Speler startSpeler;
	private boolean isLaatsteRonde = false;

	/**
	 * Construeert een spel met de gekozen spelers uit de speler repository, edelen
	 * uit de edele repostiory, edelstenen, en kaarten van alle drie niveaus uit de
	 * ontwikkelingskaart repository en voegt ze toe aan het spel voorraad.
	 * 
	 * @param spelers              lijst van de gekozen spelers
	 * @param edelen               lijst van edele objecten
	 * @param edelstenen           lijst van edelsteen objecten
	 * @param zichtbaarKaartenEen  lijst van ontwikkelingskaarten van niveau een
	 * @param zichtbaarKaartenTwee lijst van ontwikkelingskaarten van niveau twee
	 * @param zichtbaarKaartenDrie lijst van ontwikkelingskaarten van niveau drie
	 */
	public Spel(List<Speler> spelers, List<Edele> edelen, List<Edelsteen> edelstenen,
			List<Ontwikkelingskaart> zichtbaarKaartenEen, List<Ontwikkelingskaart> zichtbaarKaartenTwee,
			List<Ontwikkelingskaart> zichtbaarKaartenDrie) {
		this.spelers = spelers;
		this.edelen = edelen;
		this.edelstenen = edelstenen;
		this.zichtbaarKaartenEen = zichtbaarKaartenEen;
		this.zichtbaarKaartenTwee = zichtbaarKaartenTwee;
		this.zichtbaarKaartenDrie = zichtbaarKaartenDrie;
		startSpeler = spelers.get(0);
		spelerAanBeurt = startSpeler;
	}

	/**
	 * Getter methode om de spelers in het spel op te halen
	 * 
	 * @return een lijst van {@link Speler} objecten
	 */
	public List<Speler> getSpelers() {
		return this.spelers;
	}

	/**
	 * Getter methode om de edelen in het spel op te halen
	 * 
	 * @return een lijst van {@link Edele} objecten
	 */
	public List<Edele> getEdelen() {
		return this.edelen;
	}

	/**
	 * Getter methode om de edelstenen in het spel op te halen
	 * 
	 * @return een lijst van {@link Edelsteen} objecten
	 */
	public List<Edelsteen> getEdelstenen() {
		return this.edelstenen;
	}

	/**
	 * Getter methode om alle kaarten met een gegeven niveau op te halen.
	 * 
	 * @param niveau niveau van stapel kaarten
	 * @return een lijst van (zichtbare) ontwikkelingskaarten van de aangegeven
	 *         niveau
	 */
	public List<Ontwikkelingskaart> getOntwikkelingskaarten(int niveau) {
		return niveau == 1 ? this.zichtbaarKaartenEen
				: niveau == 2 ? this.zichtbaarKaartenTwee : this.zichtbaarKaartenDrie;
	}

	/**
	 * Aangegeven kaart toevoegen aan de lijst die correspondeert met de niveau op
	 * de plaats van de index.
	 * 
	 * @param niveau niveau van de (lijst van) kaart
	 * @param index  de plaats/index voor de nieuwe kaart in de niveau stapel
	 * @param kaart  toe te voegen {@link Ontwikkelingskaart} object kaart
	 */
	public void voegZichtbaarKaartToe(int niveau, int index, Ontwikkelingskaart kaart) {
		switch (niveau) {
		case 1 -> zichtbaarKaartenEen.add(index, kaart);
		case 2 -> zichtbaarKaartenTwee.add(index, kaart);
		case 3 -> zichtbaarKaartenDrie.add(index, kaart);
		}
	}

	/**
	 * Getter methode om de speler die aan beurt is te krijgen.
	 * 
	 * @return {@link Speler} object; speler die aan de beurt is
	 */
	public Speler getSpelerAanBeurt() {
		return spelerAanBeurt;
	}

	/**
	 * Getter methode om de start speler, die eerst begon aan het spel, te krijgen.
	 * 
	 * @return {@link Speler} object; start speler, die als eerst aan het spel begon
	 */
	public Speler getStartSpeler() {
		return startSpeler;
	}

	/**
	 * Getter methode die retourneert als het spel aan de laatste ronde is.
	 * 
	 * @return <code>true</code> als het spel aan laatste ronde is anders
	 *         <code>false</code>
	 */
	public boolean isLaatsteRonde() {
		return isLaatsteRonde;
	}

	/**
	 * Methode die transacties omtrent edelstenen waarbij er optelling of aftrekking
	 * gebeurt uitvoert. De <em>target</em> is het doel van de transactie en de
	 * <em>edelsteen</em> is de basis van deze verandering.
	 * 
	 * @param targetSpelerSpel   <code>true</code> - transactie van edelsteen
	 *                           gebeurt in richting spel -> speler vorraad;
	 *                           <code>false</code> - transactie van edelsteen
	 *                           gebeurt in richting speler -> spel vorraad
	 * 
	 * @param aangepastEdelsteen de toe te voegen aan of terug te krijgen edelsteen
	 *                           van de target
	 */
	public void actieEdelsteenVoegEnAf(boolean targetSpelerSpel, Edelsteen aangepastEdelsteen) {
		this.edelstenen.stream().filter(edelsteen -> edelsteen.getKleur() == aangepastEdelsteen.getKleur()).findFirst()
				.get().setAantal(aangepastEdelsteen.getAantal() * (targetSpelerSpel ? -1 : 1));

		if (spelerAanBeurt.getEdelstenen().contains(aangepastEdelsteen)) {
			spelerAanBeurt.getEdelstenen().stream()
					.filter(edelsteen -> edelsteen.getKleur() == aangepastEdelsteen.getKleur()).findFirst().get()
					.setAantal(aangepastEdelsteen.getAantal() * (targetSpelerSpel ? 1 : -1));
		} else {
			spelerAanBeurt.getEdelstenen().add(aangepastEdelsteen);
		}

		if (!targetSpelerSpel) {
			spelerAanBeurt.getEdelstenen().removeIf(edelsteen -> edelsteen.getAantal() == 0);
		}
	}

	/**
	 * De speler aan de beurt instellen door de index in de lijst van spelers te
	 * gebruiken
	 * 
	 * @param index index van een speler in de lijst van spelers
	 */
	public void setSpelerAanBeurt(int index) {
		spelerAanBeurt = spelers.get(index);
	}

	/**
	 * Controleren of een ontwikkelingskaart kan gekocht worden door de speler die
	 * aan beurt is.
	 * 
	 * @param niveau niveau van de gekozen kaart
	 * @param index  plaats van de gekozen kaart uit de (vier) zichtbare kaarten
	 * @return <code>true</code> - gewenste kaart werd aangekocht;
	 *         <code>false</code> - gewenste kaart kan niet gekocht worden
	 */
	public boolean actieSpelerKaartKopen(int niveau, int index) {
		Ontwikkelingskaart teKopenKaart = getOntwikkelingskaarten(niveau).get(index);
		List<Edelsteen> teKopenKaartPrijs = teKopenKaart.getPrijs();
		// bonussen aftrekken van kaart prijs
		teKopenKaartPrijs.forEach(edelsteen -> edelsteen.setAantal(-1 * Math.toIntExact(spelerAanBeurt.getKaarten()
				.stream().filter(kaart -> kaart.getBonus().getKleur() == edelsteen.getKleur()).count())));

		try {
			if (teKopenKaartPrijs.stream()
					.collect(Collectors.summingInt(edelsteen -> Math.max(0, edelsteen.getAantal()))) == 0) {
			} else if (teKopenKaartPrijs.stream().filter(edelsteen -> edelsteen.getAantal() > 0)
					.allMatch(edelsteen -> edelsteen.getAantal() <= spelerAanBeurt.getEdelstenen().stream()
							.filter(spelerEdelsteen -> spelerEdelsteen.getKleur() == edelsteen.getKleur()).findAny()
							.orElseThrow().getAantal())) {
				// edelstenen aftrekken en terug storten in spel
				teKopenKaartPrijs.stream().filter(edelsteen -> edelsteen.getAantal() > 0)
						.forEach(edelsteen -> actieEdelsteenVoegEnAf(false, edelsteen));
			} else {
				teKopenKaartPrijs.forEach(edelsteen -> edelsteen.setAantal(Math.toIntExact(spelerAanBeurt.getKaarten()
						.stream().filter(kaart -> kaart.getBonus().getKleur() == edelsteen.getKleur()).count())));
				return false;
			}
		} catch (NoSuchElementException nsee) {
			teKopenKaartPrijs.forEach(edelsteen -> edelsteen.setAantal(Math.toIntExact(spelerAanBeurt.getKaarten()
					.stream().filter(kaart -> kaart.getBonus().getKleur() == edelsteen.getKleur()).count())));
//			throw new NoSuchElementException();
			return false;
		}

		teKopenKaartPrijs.forEach(edelsteen -> edelsteen.setAantal(Math.toIntExact(spelerAanBeurt.getKaarten().stream()
				.filter(kaart -> kaart.getBonus().getKleur() == edelsteen.getKleur()).count())));
		spelerAanBeurt.getKaarten().add(getOntwikkelingskaarten(niveau).remove(index));
		return true;
	}

	/**
	 * De edele op de gegeven index in de lijst van edelen brengt bezoek aan de
	 * speler aan beurt. Deze edele wordt uit het spel gehaald en in de voorraad van
	 * de speler geplaatst.
	 * 
	 * @param index plaats/index van de edele
	 */
	public void eventSpecialeTegel(int index) {
		spelerAanBeurt.getEdelen().add(edelen.remove(index));
	}

	/**
	 * Controleert als het spel aan zijn laatste ronde is omdat iemand 15 of meer
	 * prestigepunten heeft en stelt het spel zo in.
	 */
	public void eventIsLaatsteRonde() {
		isLaatsteRonde = spelers.stream().anyMatch(speler -> speler.getAantalPrestigepunten() >= 15);
	}
}
