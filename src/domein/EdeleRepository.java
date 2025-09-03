package domein;

import java.util.ArrayList;
import java.util.List;

import persistentie.EdeleMapper;

/**
 * Deze repository klasse houdt alle {@link Edele} objecten bij waarvan een
 * aantal in het spel zullen gebruikt worden.
 */
public class EdeleRepository {
	private final EdeleMapper em;
	private List<Edele> edelen;

	/**
	 * Construeert een object van de {@link EdeleRepository} en een mapper klasse,
	 * waarna alle edele objecten gemaakt worden.
	 */
	public EdeleRepository() {
		this.em = new EdeleMapper();
		maakEdelenAan();
	}

	/**
	 * De mapper klasse aanropen om alle edele objecten te maken en deze in de lijst
	 * van edelen stoppen.
	 */
	private void maakEdelenAan() {
		this.edelen = em.maakEdelenAan();
	}

	/**
	 * Methode om een lijst, waarvan de grote afhankelijk is van het aantal spelers
	 * in het spel, te ontvangen om deze in het edelen vorraad van spel toe te
	 * voegen.
	 * 
	 * @param aantalSpelers aantal spelers in het spel
	 * @return een lijst van willekeurige edele objecten
	 */
	public List<Edele> vulEdelenAanSpel(int aantalSpelers) {
		List<Edele> spelEdelen = new ArrayList<>();
		switch (aantalSpelers) {
		case 2 -> spelEdelen = edelen.subList(0, 3);
		case 3 -> spelEdelen = edelen.subList(0, 4);
		case 4 -> spelEdelen = edelen.subList(0, 5);
		}

		return spelEdelen;
	}

}
