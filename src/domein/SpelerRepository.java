package domein;

import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;

import language.Language;
import persistentie.SpelerMapper;

/**
 * Deze repository klasse houdt alle {@link Speler} objecten bij die
 * geselecteerd werden in het begin van het spel.
 */
public class SpelerRepository {

	private final SpelerMapper spelerMapper;
	private Collection<Speler> spelers;

	/**
	 * Construeert een object van {@link SpelerRepository} waarbij het een speler
	 * mapper aanmaakt en een lijst klaarzet waarin de gekozen spelers zullen
	 * terecht komen.
	 */
	public SpelerRepository() {
		spelerMapper = new SpelerMapper();
		spelers = new LinkedHashSet<>();
	}

	/**
	 * Methode om de gewenste speler op te halen uit de databank en toe te voegen
	 * aan de spelers repository.
	 * 
	 * @param naam         gebruikersnaam van de gewenste speler
	 * @param geboortejaar geboortejaar van de gewenste speler
	 * @return <code>true</code> - speler is correct gevonden en toegevoegd;
	 *         <code>false</code> - speler werd al eerder gekozen
	 * @throws NullPointerException     als een speler niet bestaat/niet werd
	 *                                  gevonden
	 * @throws SQLException             als er iets misgaat met bij het verbinden
	 *                                  met/ophalen van resultaten van de database
	 * @throws IllegalArgumentException als er een fout optreedt bij het creëren van
	 *                                  een {@link Speler} object
	 */
	public boolean selecteerSpeler(String naam, Year geboortejaar)
			throws SQLException, NullPointerException, IllegalArgumentException {
		if (naam == null || naam.isEmpty() || geboortejaar == null) {
			throw new IllegalArgumentException(Language.geefVertaling("nameYearFout_nl_en"));
		}

		Speler speler = spelerMapper.geefSpeler(naam, geboortejaar);
		if (speler == null) {
			throw new NullPointerException();
		}
		return spelers.add(speler);
	}

	/**
	 * Getter methode om de spelers uit de speler repository op te halen. Spelers
	 * worden eerst gesorteerd volgens de regels.
	 * 
	 * @return een lijst van spelers gesorteerd volgens beurt
	 */
	public List<Speler> getSpelers() {
		List<Speler> spelersList = new ArrayList<>(spelers);
		if (spelers.size() > 1) {
			List<Speler> spelersListSorted = new ArrayList<>(spelers);
			Collections.sort(spelersListSorted, Comparator.comparing(Speler::getGeboortejaar)
					.thenComparing((s1, s2) -> s1.getGebruikersnaam().length() - s2.getGebruikersnaam().length())
					.thenComparing(Speler::getGebruikersnaam).reversed());
			spelersList.remove(spelersListSorted.get(0));
			spelersList.add(0, spelersListSorted.get(0));
		}
		return spelersList;
	}
}
