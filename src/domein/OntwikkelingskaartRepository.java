package domein;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import language.Language;
import persistentie.OntwikkelingskaartMapper;

/**
 * Deze repository klasse houdt alle {@link Ontwikkelingskaart} objecten bij die
 * opgedeeld zijn in drie niveaus.
 */
public class OntwikkelingskaartRepository {
	private final OntwikkelingskaartMapper okm;
	private List<Ontwikkelingskaart> niveauEenKaarten;
	private List<Ontwikkelingskaart> niveauTweeKaarten;
	private List<Ontwikkelingskaart> niveauDrieKaarten;

	/**
	 * Construeert een object van {@link OntwikkelingskaartRepository} waarbij het
	 * een ontwikkelingskaart mapper aanmaakt en methodes aanroept om de kaarten van
	 * alle drie niveaus te maken.
	 * 
	 * @throws FileNotFoundException indien een bestand met de data voor het
	 *                               aanmaken van kaarten niet bestaat/werd gevonden
	 */
	public OntwikkelingskaartRepository() throws FileNotFoundException {
		this.okm = new OntwikkelingskaartMapper();
		maakNiveauEenKaartenAan();
		maakNiveauTweeKaartenAan();
		maakNiveauDrieKaartenAan();
	}

	/**
	 * Methode om de kaarten van niveau een aan te maken en in deze repository toe
	 * te voegen.
	 * 
	 * @throws FileNotFoundException indien kaarten niveau een data bestand niet
	 *                               werd gevonden/bestaat
	 */
	private void maakNiveauEenKaartenAan() throws FileNotFoundException {
		try {
			niveauEenKaarten = okm.maakNiveauEenKaartenAan();
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException(Language.geefVertaling("cardDataFileNotFoundFirst_nl_en") + " 1 "
					+ Language.geefVertaling("cardDataFileNotFoundSecond_nl_en"));
		}
	}

	/**
	 * Methode om de kaarten van niveau twee aan te maken en in deze repository toe
	 * te voegen.
	 * 
	 * @throws FileNotFoundException indien kaarten niveau twee data bestand niet
	 *                               werd gevonden/bestaat
	 */
	private void maakNiveauTweeKaartenAan() throws FileNotFoundException {
		try {
			niveauTweeKaarten = okm.maakNiveauTweeKaartenAan();
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException(Language.geefVertaling("cardDataFileNotFoundFirst_nl_en") + " 2 "
					+ Language.geefVertaling("cardDataFileNotFoundSecond_nl_en"));
		}
	}

	/**
	 * Methode om de kaarten van niveau drie aan te maken en in deze repository toe
	 * te voegen.
	 * 
	 * @throws FileNotFoundException indien kaarten niveau drie data bestand niet
	 *                               werd gevonden/bestaat
	 */
	private void maakNiveauDrieKaartenAan() throws FileNotFoundException {
		try {
			niveauDrieKaarten = okm.maakNiveauDrieKaartenAan();
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException(Language.geefVertaling("cardDataFileNotFoundFirst_nl_en") + " 3 "
					+ Language.geefVertaling("cardDataFileNotFoundSecond_nl_en"));
		}
	}

	/**
	 * Retourneert hoeveel kaarten resteren in een bepaalde niveau van stapel van
	 * kaarten.
	 * 
	 * @param niveau niveau van de stapel/kaart
	 * @return aantal kaarten in de stapel (omgedraaid)
	 */
	public int geefAantalKaartenInStapel(int niveau) {
		return niveau == 1 ? niveauEenKaarten.size()
				: niveau == 2 ? niveauTweeKaarten.size() : niveauDrieKaarten.size();
	}

	/**
	 * Aantal bovenste kaarten uit de stapel van bepaalde niveau uitdelen en daarna
	 * verwijderen.
	 * 
	 * @param niveau        niveau van de stapel/kaart
	 * @param aantalKaarten hoveel kaarten uitgedeeld moeten worden
	 * @return een lijst van ontwikkelingskaarten bestaande uit de aangegeven
	 *         hoeveelheid kaarten van de niveau stapel.
	 */
	public List<Ontwikkelingskaart> deelKaartUit(int niveau, int aantalKaarten) {
		List<Ontwikkelingskaart> teDelenKaarten;
		if (niveau == 1) {
			teDelenKaarten = new ArrayList<>(niveauEenKaarten.subList(0, aantalKaarten));
			niveauEenKaarten.subList(0, aantalKaarten).clear();
		} else if (niveau == 2) {
			teDelenKaarten = new ArrayList<>(niveauTweeKaarten.subList(0, aantalKaarten));
			niveauTweeKaarten.subList(0, aantalKaarten).clear();
		} else {
			teDelenKaarten = new ArrayList<>(niveauDrieKaarten.subList(0, aantalKaarten));
			niveauDrieKaarten.subList(0, aantalKaarten).clear();
		}

		return teDelenKaarten;
	}
}
