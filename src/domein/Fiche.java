package domein;

import java.util.List;

/**
 * Abstracte klasse die een algemeen fiche of kaart voorstelt, het heeft een
 * aantal prestigepunten en een bepaalde prijs/kost om deze fiche om te kopen.
 *
 */
public abstract class Fiche {
	protected final int prestigepunt;
	protected final List<Edelsteen> prijs;

	/**
	 * Construeert een Fiche object met een aantal prestigepunten en een lijst van
	 * {@link Edelsteen} objecten als de kost
	 * 
	 * @param prestigepunt aantal prestigepunt
	 * @param prijs        een lijst van edelstenen
	 */
	public Fiche(int prestigepunt, List<Edelsteen> prijs) {
		this.prestigepunt = prestigepunt;
		this.prijs = prijs;
	}

	/**
	 * Abstracte getter methode te implementeren, die de aantal prestigepunten
	 * retourneert.
	 * 
	 * @return aantal prestigepunten van fiche
	 */
	public abstract int getPrestigepunt();

	/**
	 * Abstracte getter methode te implementeren, die de lijst van kost/prijs
	 * retourneert.
	 * 
	 * @return lijst van edelstenen
	 */
	public abstract List<Edelsteen> getPrijs();
}
