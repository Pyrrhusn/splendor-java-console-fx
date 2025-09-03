package domein;

import java.util.List;

/**
 * Klasse die een ontwikkelingskaart representeert met de prestigepunten, kost
 * van de abstarcte superklasse {@link Fiche} en een bonus edelsteen.
 */
public class Ontwikkelingskaart extends Fiche {
	private final Edelsteen bonus;

	/**
	 * Construeert een {@link Ontwikkelingskaart} object met aantal prestigepunten,
	 * een bonus edelsteen object en de lijst van edelsteen objecten die de kost
	 * voorstelt.
	 * 
	 * @param prijs        lijst van edelsteen objecten
	 * @param prestigepunt aantal prestigepunten, een getal
	 * @param bonus        een edelsteen object
	 */
	public Ontwikkelingskaart(List<Edelsteen> prijs, int prestigepunt, Edelsteen bonus) {
		super(prestigepunt, prijs);
		this.bonus = bonus;
	}

	/**
	 * Implementatie van de abstracte methode uit {@link Fiche}. Getter methode om
	 * de kost van deze kaart te krijgen.
	 * 
	 * @return lijst van edelstenen van het prijs/vereiste bonus van deze edele
	 */
	@Override
	public List<Edelsteen> getPrijs() {
		return super.prijs;
	}

	/**
	 * Implementatie van de abstracte methode uit {@link Fiche}. Getter methode om
	 * de prestigepunten van deze kaart te krijgen.
	 * 
	 * @return aantal prestigepunten
	 */
	@Override
	public int getPrestigepunt() {
		return super.prestigepunt;
	}

	/**
	 * Retourneert een edelsteen object dat de bonus is van deze kaart.
	 * 
	 * @return bonus, een edelsteen object
	 */
	public Edelsteen getBonus() {
		return bonus;
	}

	/**
	 * Afdrukken van ontwikkelingskaart in een mooiere en door mensen leesbaardere
	 * formaat
	 * 
	 * @return tekstuele voorstelling van een kaart met de prestigepunten, de kost
	 *         en bonus
	 */
	@Override
	public String toString() {
		String overzicht = String.format("%n ╒══════════════╕ %n │%d%13s│ %n ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┤ %n", getPrestigepunt(),
				getBonus().getKleur());

		for (Edelsteen edelsteen : getPrijs()) {
			overzicht += String.format(" │%-14s│ %n", edelsteen);
		}
		overzicht += String.format(" ╘══════════════╛ %n");

		return overzicht;
	}
}
