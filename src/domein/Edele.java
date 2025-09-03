package domein;

import java.util.List;

import language.Language;

/**
 * Klasse die een Edele representeert met als de abstracte superklaase
 * {@link Fiche} voor de properties: prestigepunten en de vereiste bonussen.
 */
public class Edele extends Fiche {
	/**
	 * Construeert een edele object met de aangegeven prestigepunen en een lijst van
	 * bonussen van ontwikkelingskaarten die een speler in bezit moet hebben.
	 * 
	 * @param prestigepunt  aantal prestigepunten van deze edele
	 * @param vereisteBonus lijst van {@link Edelsteen} objecten voor het 'prijs'
	 */
	public Edele(int prestigepunt, List<Edelsteen> vereisteBonus) {
		super(prestigepunt, vereisteBonus);
	}

	/**
	 * Implementatie van de abstracte methode uit {@link Fiche}. Getter methode om
	 * de prestigepunten van deze edele te krijgen.
	 * 
	 * @return aantal prestigepunten
	 */
	@Override
	public int getPrestigepunt() {
		return super.prestigepunt;
	}

	/**
	 * Implementatie van de abstracte methode uit {@link Fiche}. Getter methode om
	 * de vereiste bonus van deze edele te krijgen.
	 * 
	 * @return lijst van edelstenen van het prijs/vereiste bonus van deze edele
	 */
	@Override
	public List<Edelsteen> getPrijs() {
		return super.prijs;
	}

	/**
	 * Afdrukken van edele in een mooiere en door mensen leesbaardere formaat
	 * 
	 * @return tekstuele voorstelling van een edele
	 */
	@Override
	public String toString() {
		String overzicht = String.format("%n ╭──────────────╮ %n │%d        %5s│ %n │         ─────┤ %n",
				getPrestigepunt(), Language.geefVertaling("edele_nl_en"));

		for (Edelsteen edelsteenBonus : getPrijs()) {
			overzicht += String.format(" │%-14s│ %n", edelsteenBonus);
		}
		overzicht += String.format(" ╰──────────────╯ %n");

		return overzicht;
	}
}