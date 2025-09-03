package dto;

import java.util.ArrayList;
import java.util.List;

import domein.Spel;

public record SpelDTO(List<SpelerDTO> spelers, List<EdeleDTO> edelen, List<EdelsteenDTO> edelstenen,
		List<OntwikkelingskaartDTO> zichtbaarKaartenEen, List<OntwikkelingskaartDTO> zichtbaarKaartenTwee,
		List<OntwikkelingskaartDTO> zichtbaarKaartenDrie, SpelerDTO spelerAanBeurt, SpelerDTO startSpeler,
		boolean isLaatsteRonde) {
	public SpelDTO(Spel spel) {
		this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), new SpelerDTO(spel.getSpelerAanBeurt()), new SpelerDTO(spel.getStartSpeler()),
				spel.isLaatsteRonde());
		spel.getSpelers().forEach((speler) -> this.spelers.add(new SpelerDTO(speler)));
		spel.getEdelen().forEach((edele) -> this.edelen.add(new EdeleDTO(edele)));
		spel.getEdelstenen().forEach((edelsteen) -> this.edelstenen.add(new EdelsteenDTO(edelsteen)));
		spel.getOntwikkelingskaarten(1)
				.forEach((kaart) -> this.zichtbaarKaartenEen.add(new OntwikkelingskaartDTO(kaart)));
		spel.getOntwikkelingskaarten(2)
				.forEach((kaart) -> this.zichtbaarKaartenTwee.add(new OntwikkelingskaartDTO(kaart)));
		spel.getOntwikkelingskaarten(3)
				.forEach((kaart) -> this.zichtbaarKaartenDrie.add(new OntwikkelingskaartDTO(kaart)));
	}
}
