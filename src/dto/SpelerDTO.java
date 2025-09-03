package dto;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import domein.Speler;

public record SpelerDTO(String gebruikersnaam, Year geboortejaar, List<OntwikkelingskaartDTO> kaartenInBezit,
		List<EdelsteenDTO> edelstenenInBezit, List<EdeleDTO> edelenInBezit, int prestigepunten) {
	public SpelerDTO(Speler speler) {
		this(speler.getGebruikersnaam(), speler.getGeboortejaar(), new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), speler.getAantalPrestigepunten());
		speler.getKaarten().forEach((kaart) -> this.kaartenInBezit.add(new OntwikkelingskaartDTO(kaart)));
		speler.getEdelstenen().forEach((edelsteen) -> this.edelstenenInBezit.add(new EdelsteenDTO(edelsteen)));
		speler.getEdelen().forEach((edele) -> this.edelenInBezit.add(new EdeleDTO(edele)));
	}

	public SpelerDTO(String gebruikersnaam, Year geboortejaar, List<OntwikkelingskaartDTO> kaartenInBezit,
			List<EdelsteenDTO> edelstenenInBezit, List<EdeleDTO> edelenInBezit, int prestigepunten) {
		this.gebruikersnaam = gebruikersnaam;
		this.geboortejaar = geboortejaar;
		this.kaartenInBezit = kaartenInBezit;
		this.edelstenenInBezit = edelstenenInBezit;
		this.edelenInBezit = edelenInBezit;
		this.prestigepunten = prestigepunten;
	}

	public SpelerDTO(String gebruikersnaam, Year geboortejaar) {
		this(gebruikersnaam, geboortejaar, null, null, null, 0);
	}

	// Gebruiken voor scoreboard
	@Override
	public String toString() {
		return gebruikersnaam;
	}

	@Override
	public int hashCode() {
		return Objects.hash(geboortejaar, gebruikersnaam);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SpelerDTO other = (SpelerDTO) obj;
		return Objects.equals(geboortejaar, other.geboortejaar) && Objects.equals(gebruikersnaam, other.gebruikersnaam);
	}
}
