package persistentie;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import domein.Edelsteen;
import domein.Ontwikkelingskaart;
import util.EdelsteenType;

public class OntwikkelingskaartMapper {
	public OntwikkelingskaartMapper() {
	}

	public List<Ontwikkelingskaart> maakNiveauEenKaartenAan() throws FileNotFoundException {
		File niveauEenKaartenFile = new File("src/persistentie/NiveauEenKaartenData.txt");
		List<Ontwikkelingskaart> kaarten = kaartenDataParser(niveauEenKaartenFile);
		Collections.shuffle(kaarten);
		return kaarten;
	}

	public List<Ontwikkelingskaart> maakNiveauTweeKaartenAan() throws FileNotFoundException {
		File niveauTweeKaartenFile = new File("src/persistentie/NiveauTweeKaartenData.txt");
		List<Ontwikkelingskaart> kaarten = kaartenDataParser(niveauTweeKaartenFile);
		Collections.shuffle(kaarten);
		return kaarten;
	}

	public List<Ontwikkelingskaart> maakNiveauDrieKaartenAan() throws FileNotFoundException {
		File niveauDrieKaartenFile = new File("src/persistentie/NiveauDrieKaartenData.txt");
		List<Ontwikkelingskaart> kaarten = kaartenDataParser(niveauDrieKaartenFile);
		Collections.shuffle(kaarten);
		return kaarten;
	}

	// custom parser
	private List<Ontwikkelingskaart> kaartenDataParser(File file) throws FileNotFoundException {
		List<Ontwikkelingskaart> kaartenArray = new ArrayList<>();
		Scanner scanner = new Scanner(file);

		while (scanner.hasNextLine()) {
			List<Edelsteen> prijzen = new ArrayList<>();
			String lijn = scanner.nextLine();
			String[] kaartData = lijn.split(",");

			int prestigepunt = Integer.parseInt(kaartData[0]);
			EdelsteenType edelsteenType = EdelsteenType.valueOf(kaartData[1]);

			String[] tweedeDeel = Arrays.copyOfRange(kaartData, 2, kaartData.length);
			for (String prijs : tweedeDeel) {
				String[] priceEnum = prijs.split("-");

				int aantal = Integer.parseInt(priceEnum[0]);
				EdelsteenType type = EdelsteenType.valueOf(priceEnum[1]);
				prijzen.add(new Edelsteen(aantal, type));
			}
			kaartenArray.add(new Ontwikkelingskaart(prijzen, prestigepunt, new Edelsteen(1, edelsteenType)));
		}
		scanner.close();
		return kaartenArray;
	}
}
