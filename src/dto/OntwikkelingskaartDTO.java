package dto;

import java.util.ArrayList;
import java.util.List;

import domein.Ontwikkelingskaart;

public record OntwikkelingskaartDTO(List<EdelsteenDTO> prijs, int prestigepunt, EdelsteenDTO bonus) {
	public OntwikkelingskaartDTO(Ontwikkelingskaart kaart) {
		this(new ArrayList<>(), kaart.getPrestigepunt(), new EdelsteenDTO(kaart.getBonus()));
		kaart.getPrijs().forEach((edelsteen) -> this.prijs.add(new EdelsteenDTO(edelsteen)));
	}

	@Override
	public String toString() {
		String overzicht = String.format("%n ╒══════════════╕ %n │%d%13s│ %n ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┤ %n", prestigepunt(),
				bonus().kleur());

		for (EdelsteenDTO edelsteen : prijs()) {
			overzicht += String.format(" │%-14s│ %n", edelsteen);
		}
		overzicht += String.format(" ╘══════════════╛ %n");

		return overzicht;
	}
}
