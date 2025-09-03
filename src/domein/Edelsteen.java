package domein;

import java.util.Objects;

import util.EdelsteenType;

/**
 * Klasse die een edelsteen representeert, zoals de soort/kleur en het aantal
 * ervan.
 */
public class Edelsteen implements Comparable<Edelsteen> {
	private int aantal;
	private final EdelsteenType kleur;

	/**
	 * Construeert een {@link Edelsteen} object met hoeveel er zijn van een
	 * kleur/soort.
	 * 
	 * @param aantal hoeveelheid fiches van die soort/kleur
	 * @param kleur  soort/kleur van een edelsteen, gesteund op enum waarden van
	 *               {@link EdelsteenType}
	 */
	public Edelsteen(int aantal, EdelsteenType kleur) {
		this.aantal = aantal;
		this.kleur = kleur;
	}

	/**
	 * Getter methode om het aantal van een edelstenen te krijgen.
	 * 
	 * @return aantal fiches van een edelsteen (soort)
	 */
	public int getAantal() {
		return this.aantal;
	}

	/**
	 * Getter methode om de kleur/soort te krijgen
	 * 
	 * @return de soort/kleur van een edelsteen, ondersteund door enum waarden van
	 *         {@linkplain EdelsteenType}
	 */
	public EdelsteenType getKleur() {
		return this.kleur;
	}

	/**
	 * Aantal (fiches) van een edelsteen aan te passen. Het aantal wordt niet
	 * overgeschreven maar wordt herwerkt op een incrementeel/afname manier door het
	 * aantal door te geven dat wordt opteld met de orginele aantal.
	 * 
	 * @param aantalBijOfAf aantal om op te tellen, een negatieve getal zorgt voor
	 *                      aftrekking en positieve voor een toename
	 */
	public void setAantal(int aantalBijOfAf) {
		aantal += aantalBijOfAf;
	}

	/**
	 * Afdrukken van edelsteen in een mooiere en door mensen leesbaardere formaat
	 * 
	 * @return tekstuele voorstelling van een edelsteen met het aantal en de
	 *         soort/kleur
	 */
	@Override
	public String toString() {
		return String.format("%dx %s", getAantal(), getKleur());
	}

	/**
	 * Retourneert een hash code voor dit object gebaseerd op de kleur/soort.
	 * 
	 * @return berekende hash code
	 */
	@Override
	public int hashCode() {
		return Objects.hash(kleur);
	}

	/**
	 * Gelijkenis controleren met een andere object, gebaseerd op kleur.
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
		Edelsteen other = (Edelsteen) obj;
		return kleur == other.kleur;
	}

	/**
	 * Om een edelsteen te vergelijken met een andere, vergelijkenis is gebaseerd op
	 * de soort/kleur ervan.
	 * 
	 * @param edst de andere edelsteen
	 * @return een getal groter dan of kleiner dan of gelijk aan 0
	 */
	@Override
	public int compareTo(Edelsteen edst) {
		return getKleur().compareTo(edst.getKleur());
	}
}
