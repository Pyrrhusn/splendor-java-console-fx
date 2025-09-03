package dto;

import java.util.ArrayList;
import java.util.List;

import domein.Edele;
import language.Language;

public record EdeleDTO(int prestigepunt, List<EdelsteenDTO> vereisteBonus) {
	public EdeleDTO(Edele edele) {
		this(edele.getPrestigepunt(), new ArrayList<>());
//		for (Edelsteen edelsteen : edele.getPrijs()) {
//			this.vereisteBonus.add(new EdelsteenDTO(edelsteen.getAantal(), edelsteen.getKleur()));
//		}
		edele.getPrijs().forEach((edelsteen) -> this.vereisteBonus.add(new EdelsteenDTO(edelsteen)));
	}

	@Override
	public String toString() {
		String overzicht = String.format("%n ╭──────────────╮ %n │%d        %5s│ %n │         ─────┤ %n",
				prestigepunt(), Language.geefVertaling("edele_nl_en"));

		for (EdelsteenDTO edelsteenBonus : vereisteBonus()) {
			overzicht += String.format(" │%-14s│ %n", edelsteenBonus);
		}
		overzicht += String.format(" ╰──────────────╯ %n");

		return overzicht;
	}
}
