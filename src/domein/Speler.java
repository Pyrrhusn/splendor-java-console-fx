package domein;

import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import language.Language;

/**
 * Klasse die een speler representeert.
 */
public class Speler {

	private String gebruikersnaam;
	private Year geboortejaar;
	private List<Ontwikkelingskaart> kaartenInBezit;
	private List<Edelsteen> edelstenenInBezit;
	private List<Edele> edelenInBezit;

	/**
	 * Construeer een speler met adhv zijn gebruikersnaam en geboortejaar die door
	 * setter worden ingesteld. Maakt het object ook klaar voor
	 * ontwikkelingskaarten, edelstenen en edelen die een speler in het spel zou
	 * bezitten.
	 * 
	 * @param gebruikersnaam naam van de speler
	 * @param geboortejaar   jaar waarin de speler is geboren
	 */
	public Speler(String gebruikersnaam, Year geboortejaar) {
		setGebruikersnaam(gebruikersnaam);
		setGeboortejaar(geboortejaar);
		kaartenInBezit = new ArrayList<>();
		edelstenenInBezit = new ArrayList<>();
		edelenInBezit = new ArrayList<>();
	}

	/**
	 * Getter methode die de gebruikersnaam retourneert.
	 * 
	 * @return gebruikersnaam
	 */
	public String getGebruikersnaam() {
		return gebruikersnaam;
	}

	/**
	 * Setter methode die de gebruikersnaam correct instelt.
	 * 
	 * @param gebruikersnaam in te stellen naam
	 */
	private void setGebruikersnaam(String gebruikersnaam) {

		if (gebruikersnaam == null || gebruikersnaam.isEmpty()) {
			throw new IllegalArgumentException(Language.geefVertaling("playerNameRequired_nl_en"));
		}

		if (!Character.isLetter(gebruikersnaam.charAt(0))) {
			throw new IllegalArgumentException(Language.geefVertaling("playerCheckNameLetter_nl_en"));
		}

		if (!gebruikersnaam.matches("^[a-zA-Z\\d\\s_]+$")) {
			throw new IllegalArgumentException(Language.geefVertaling("playerNameRegex_nl_en"));
		}

		this.gebruikersnaam = gebruikersnaam;
	}

	/**
	 * Getter methode die de geboortejaar retourneert.
	 * 
	 * @return geboortejaar als een {@link Year} object
	 */
	public Year getGeboortejaar() {
		return geboortejaar;
	}

	/**
	 * Setter methode die de geboortejaar correct instelt.
	 * 
	 * @param geboortejaar in te stellen geboortejaar
	 */
	private void setGeboortejaar(Year geboortejaar) {

		Year huidigJaar = Year.now();

		if (geboortejaar.isAfter(huidigJaar.minusYears(6))) {
			throw new IllegalArgumentException(Language.geefVertaling("playerYearMin_nl_en"));
		}
		if (geboortejaar.isAfter(huidigJaar)) {
			throw new IllegalArgumentException(Language.geefVertaling("playerYearGeldig_nl_en"));
		}
		this.geboortejaar = geboortejaar;
	}

	/**
	 * Getter methode die over alle ontwikkelingskaart en edelen in bezit van de
	 * speler gaat, de prestigepunten optelt en retourneert.
	 * 
	 * @return prestigepunten
	 */
	public int getAantalPrestigepunten() { // optimalisatie mogelijk door niet elk keer over alle kaarten te gaan maar
											// direct in actie van aankoop van kaart en edele, deze aangepaste code aan
											// te roepen die de prestigepunten optelt bij een variable
		int aantal = 0;
		for (Ontwikkelingskaart kaart : kaartenInBezit) {
			aantal += kaart.getPrestigepunt();
		}
		for (Edele edele : edelenInBezit) {
			aantal += edele.getPrestigepunt();
		}

		return aantal;
	}

	/**
	 * Getter methode die de ontwikkelingskaarten in bezit retourneert.
	 * 
	 * @return een lijst van {@link Ontwikkelingskaart} objecten, van de kaarten in
	 *         bezit
	 */
	public List<Ontwikkelingskaart> getKaarten() {
		kaartenInBezit.sort(
				Comparator.comparing(Ontwikkelingskaart::getBonus).thenComparing(kaart -> kaart.getPrijs().size()));
		return kaartenInBezit;
	}

	/**
	 * Getter methode die de edelstenen in bezit retourneert.
	 * 
	 * @return een lijst van {@link Edelsteen} objecten, van de edelstenen in bezit
	 */
	public List<Edelsteen> getEdelstenen() {
		Collections.sort(edelstenenInBezit);
		return edelstenenInBezit;
	}

	/**
	 * Getter methode die de edelen in bezit retourneert.
	 * 
	 * @return een lijst van {@link Edele} objecten, van de edelen in bezit
	 */
	public List<Edele> getEdelen() {
		return edelenInBezit;
	}

	/**
	 * Retourneert een hash code voor dit object gebaseerd op de gebruikersnaam en
	 * geboortejaar.
	 * 
	 * @return berekende hash code
	 */
	@Override
	public int hashCode() {
		return Objects.hash(geboortejaar, gebruikersnaam);
	}

	/**
	 * Gelijkenis controleren met een andere object, gebaseerd op gebruikersnaam en
	 * geboortejaar.
	 * 
	 * @param obj de andere object met te vergelijken
	 * @return <code>true</code> als dit object hetzelfde is als het obj argument;
	 *         <code>false</code> anders.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Speler other = (Speler) obj;
		return Objects.equals(geboortejaar, other.geboortejaar) && Objects.equals(gebruikersnaam, other.gebruikersnaam);
	}

	/**
	 * Afdrukken van de gebruikersnaam van de speler (by default)
	 * 
	 * @return tekstuele voorstelling van een speler
	 */
	@Override
	public String toString() {
		return gebruikersnaam;
	}
}
