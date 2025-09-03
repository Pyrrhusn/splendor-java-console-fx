package persistentie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import domein.Edele;
import domein.Edelsteen;
import util.EdelsteenType;

public class EdeleMapper {
	public EdeleMapper() {

	}

	public List<Edele> maakEdelenAan() {
		List<Edele> edelen = new ArrayList<>();
		edelen.add(new Edele(3, new ArrayList<>(
				List.of(new Edelsteen(4, EdelsteenType.ONYX), new Edelsteen(4, EdelsteenType.DIAMANT)))));
		edelen.add(new Edele(3, new ArrayList<>(List.of(new Edelsteen(3, EdelsteenType.ONYX),
				new Edelsteen(3, EdelsteenType.ROBIJN), new Edelsteen(3, EdelsteenType.DIAMANT)))));
		edelen.add(new Edele(3, new ArrayList<>(List.of(new Edelsteen(3, EdelsteenType.ONYX),
				new Edelsteen(3, EdelsteenType.SAFFIER), new Edelsteen(3, EdelsteenType.DIAMANT)))));
		edelen.add(new Edele(3, new ArrayList<>(List.of(new Edelsteen(3, EdelsteenType.SMARAGD),
				new Edelsteen(3, EdelsteenType.SAFFIER), new Edelsteen(3, EdelsteenType.DIAMANT)))));
		edelen.add(new Edele(3, new ArrayList<>(List.of(new Edelsteen(3, EdelsteenType.ONYX),
				new Edelsteen(3, EdelsteenType.ROBIJN), new Edelsteen(3, EdelsteenType.SMARAGD)))));
		edelen.add(new Edele(3, new ArrayList<>(List.of(new Edelsteen(3, EdelsteenType.SMARAGD),
				new Edelsteen(3, EdelsteenType.SAFFIER), new Edelsteen(3, EdelsteenType.ROBIJN)))));
		edelen.add(new Edele(3, new ArrayList<>(
				List.of(new Edelsteen(4, EdelsteenType.SAFFIER), new Edelsteen(4, EdelsteenType.SMARAGD)))));
		edelen.add(new Edele(3, new ArrayList<>(
				List.of(new Edelsteen(4, EdelsteenType.ONYX), new Edelsteen(4, EdelsteenType.ROBIJN)))));
		edelen.add(new Edele(3, new ArrayList<>(
				List.of(new Edelsteen(4, EdelsteenType.SAFFIER), new Edelsteen(4, EdelsteenType.DIAMANT)))));
		edelen.add(new Edele(3, new ArrayList<>(
				List.of(new Edelsteen(4, EdelsteenType.ROBIJN), new Edelsteen(4, EdelsteenType.SMARAGD)))));
		Collections.shuffle(edelen);
		return edelen;
	}
}
