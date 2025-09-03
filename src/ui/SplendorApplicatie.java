package ui;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.Year;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import domein.DomeinController;
import dto.EdeleDTO;
import dto.EdelsteenDTO;
import dto.OntwikkelingskaartDTO;
import dto.SpelDTO;
import dto.SpelerDTO;
import language.Language;
import util.EdelsteenType;

public class SplendorApplicatie {
	private DomeinController dc;
	private final Scanner sc = new Scanner(System.in);

	public SplendorApplicatie(DomeinController dc) {
		this.dc = dc;
	}

	public void start() {
		int keuze;
		String taal;
		boolean nogFout = true;
		int aantalSpelers = 0;

		do {
			try {
				System.out.printf(
						"Kies de gewenste taal/Choose your language!%n'NL' voor Nederlands en 'EN' for English: ");
				taal = sc.next().toUpperCase();

				Locale l;
				if (taal.equals("NL")) {
					l = new Locale("NL");
				} else if (taal.equals("EN")) {
					l = new Locale("EN");
				} else
					throw new IllegalArgumentException("\n!!Ongeldig invoer/Invalid input!!\n");

				Language.setRecourseBudel(ResourceBundle.getBundle("language/Language", l));
				nogFout = false;

			} catch (IllegalArgumentException iae) {
				System.out.println(iae.getMessage());
				sc.nextLine();
			}
		} while (nogFout);

		System.out.println("");
		System.out.println(Language.geefVertaling("welcome_nl_en"));
		System.out.println("-".repeat(Language.geefVertaling("welcome_nl_en").length()));

		do {
			String choosePlayersStatus = Language.geefVertaling("minPlayersRequired_nl_en");
			try {
				System.out.println(Language.geefVertaling("choice_nl_en"));

				System.out.println("1. " + Language.geefVertaling("selecteer_nl_en"));
				System.out.println("2. " + Language.geefVertaling("stop_nl_en"));
				System.out.println("3. " + Language.geefVertaling("startGame_nl_en"));

				choosePlayersStatus = aantalSpelers >= 2 ? Language.geefVertaling("gameStatusReady_nl_en")
						: aantalSpelers > 0 ? Language.geefVertaling("gameStatusOne_nl_en") : choosePlayersStatus;
				System.out.printf("%n%s%n", choosePlayersStatus);

				if (aantalSpelers != 0) {
					System.out.println("\n" + Language.geefVertaling("chosenPlayersTitel_nl_en"));
					System.out.println(getFormattedChosenSpelers());
				}
				System.out.print("\n> ");
				keuze = sc.nextInt();
				// meer user friendly maken door niet naar hoofdmenu te gaan, nodig???

				if (keuze == 1) {
					if (selecteerSpeler()) {
						aantalSpelers++;
					}
				} else if (keuze == 2) {
					System.exit(0);
				} else if (keuze == 3) {
					System.out.println(choosePlayersStatus);
					System.out.println("-".repeat(choosePlayersStatus.length()));
					if (aantalSpelers >= 2) {
						break;
					} else {
						continue;
					}
				} else {
					throw new NoSuchElementException();
				}
			} catch (NoSuchElementException nse) {
				System.out.println("\n" + Language.geefVertaling("choiceInputError_nl_en"));
				System.out.println("-".repeat(Language.geefVertaling("choiceInputError_nl_en").length()));
				sc.nextLine();
			}
		} while (aantalSpelers != 4);

		try {
			dc.maakSpelAan();
		} catch (FileNotFoundException fnfe) {
			System.out.println(fnfe.getMessage());
		} catch (IndexOutOfBoundsException indexe) {
			System.out.println(Language.geefVertaling("somethingWrongCardData_nl_en"));
		}

		SpelDTO spel = dc.getSpel();
		System.out.println(Language.geefVertaling("gameOKStart_nl_en"));

		do {
			System.out.println("\n•" + "·".repeat(32) + "|" + Language.geefVertaling("startRoundTitle_nl_en") + "|"
					+ "·".repeat(32) + "•");

			for (int s = 0; s < spel.spelers().size(); s++) {
				dc.setSpelSpelerAanBeurt(s);
				spel = dc.getSpel();

				System.out.println(Language.geefVertaling("gameSituationTitle_nl_en"));
				System.out.println(Language.geefVertaling("gameEdelstenenVoorraadTtile_nl_en"));
				bouwEdelstenenOverzicht(spel.edelstenen());

				System.out.println("\n" + Language.geefVertaling("gameEdelenVoorraadTtile_nl_en"));
				bouwEdelenOverzicht(spel.edelen());

				System.out.println("\n" + Language.geefVertaling("gameKaartenVoorraadTtile_nl_en"));
				bouwKaartenOverzicht(3, dc.geefAantalKaartenInStapel(3), spel.zichtbaarKaartenDrie());
				bouwKaartenOverzicht(2, dc.geefAantalKaartenInStapel(2), spel.zichtbaarKaartenTwee());
				bouwKaartenOverzicht(1, dc.geefAantalKaartenInStapel(1), spel.zichtbaarKaartenEen());

				System.out.println("\n" + Language.geefVertaling("gameOverzichtSpelersTitle_nl_en"));
				for (SpelerDTO spelerOverzicht : spel.spelers()) {
					bouwPerSpelerOverzicht(spelerOverzicht, spel.spelerAanBeurt(), spel.startSpeler());
				}

				int actieKeuze = 0;
				boolean actieGelukt = false;
				do {
					System.out.printf("%s %s %s%n%n", Language.geefVertaling("playerWord_nl_en"), spel.spelerAanBeurt(),
							Language.geefVertaling("aanBeurt_nl_en"));
					System.out.printf("%s%n%s%n%s%n%s%n%s%n", Language.geefVertaling("gameActiesTitle_nl_en"),
							"1. " + Language.geefVertaling("gameActieEen_nl_en"),
							"2. " + Language.geefVertaling("gameActieTwee_nl_en"),
							"3. " + Language.geefVertaling("gameActieDrie_nl_en"),
							"4. " + Language.geefVertaling("gameActieVier_nl_en"));

					try {
						System.out.print("> ");
						actieKeuze = sc.nextInt();
						if (actieKeuze == 1 || actieKeuze == 2) {
							actieGelukt = actieEdelstenenFiches(actieKeuze, spel.edelstenen());

							spel = dc.getSpel();
							int totaalAantalFichesSpeler = spel.spelerAanBeurt().edelstenenInBezit().stream()
									.collect(Collectors.summingInt(edelsteen -> edelsteen.aantal()));
							if (totaalAantalFichesSpeler > 10) {
								int aantalTerug = 0;
								List<EdelsteenDTO> lijstGekozenFiches = new ArrayList<>();

								do {
									aantalTerug = 0;
									lijstGekozenFiches.clear();
									System.out.println(Language.geefVertaling("gameTienEdelstenen_nl_en"));
									bouwEdelstenenOverzicht(spel.spelerAanBeurt().edelstenenInBezit());
									System.out.println(Language.geefVertaling("gameEdelstenenTerugTitle_nl_en"));

									try {
										String kleurenTypesString = sc.next();
										kleurenTypesString += sc.nextLine();
										List<EdelsteenType> parsedTypes = Arrays
												.stream(kleurenTypesString.trim().split(" "))
												.map(typeString -> EdelsteenType.getTypeKleurOfBenaming(typeString))
												.distinct().collect(Collectors.toCollection(ArrayList::new));

										for (EdelsteenType kleur : parsedTypes) {
											System.out.print(Language.geefVertaling("gameHoeveelTerug_nl_en") + " "
													+ kleur + "? ");
											int aantal = sc.nextInt();
											aantalTerug += aantal;
											lijstGekozenFiches.add(new EdelsteenDTO(aantal, kleur));
										}
									} catch (InputMismatchException ime) {
										System.out.println(Language.geefVertaling("genericInputError_nl_en"));
										sc.nextLine();
									} catch (NoSuchElementException | IllegalArgumentException iae) {
										System.out.println(Language.geefVertaling("gameUnknownColour_nl_en"));
									}
									System.out.println();
								} while (totaalAantalFichesSpeler - aantalTerug != 10);
								lijstGekozenFiches.forEach(dto -> dc.actieEdelsteenVoegEnAf(false, dto));
							}
						} else if (actieKeuze == 3) {
							actieGelukt = actieOntwikkelingskaartKopen(spel.zichtbaarKaartenEen(),
									spel.zichtbaarKaartenTwee(), spel.zichtbaarKaartenDrie());
							spel = dc.getSpel();
						} else if (actieKeuze == 4) {
							actieGelukt = true;
						} else {
							throw new IllegalArgumentException(Language.geefVertaling("choiceInputError_nl_en") + "\n");
						}
					} catch (IllegalArgumentException iae) {
						System.out.println(iae.getMessage());
						sc.nextLine();
					} catch (NoSuchElementException nse) {
						System.out.println(Language.geefVertaling("choiceInputError_nl_en"));
						sc.nextLine();
					}
				} while (actieKeuze < 1 || actieKeuze > 4 || !actieGelukt);
				System.out.println(
						"─".repeat(15) + " " + Language.geefVertaling("gameEndTurn_nl_en") + " " + "─".repeat(15));

				eventSpecialeTegel(spel.edelen(), spel.spelerAanBeurt().kaartenInBezit());
			}
			dc.eventIsLaatsteRonde();
			spel = dc.getSpel();
		} while (!spel.isLaatsteRonde());
		sc.close();

		System.out.println("\r\n" + "  ▄████  ▄▄▄       ███▄ ▄███▓▓█████     ▒█████   ██▒   █▓▓█████  ██▀███  \r\n"
				+ " ██▒ ▀█▒▒████▄    ▓██▒▀█▀ ██▒▓█   ▀    ▒██▒  ██▒▓██░   █▒▓█   ▀ ▓██ ▒ ██▒\r\n"
				+ "▒██░▄▄▄░▒██  ▀█▄  ▓██    ▓██░▒███      ▒██░  ██▒ ▓██  █▒░▒███   ▓██ ░▄█ ▒\r\n"
				+ "░▓█  ██▓░██▄▄▄▄██ ▒██    ▒██ ▒▓█  ▄    ▒██   ██░  ▒██ █░░▒▓█  ▄ ▒██▀▀█▄  \r\n"
				+ "░▒▓███▀▒ ▓█   ▓██▒▒██▒   ░██▒░▒████▒   ░ ████▓▒░   ▒▀█░  ░▒████▒░██▓ ▒██▒\r\n"
				+ " ░▒   ▒  ▒▒   ▓▒█░░ ▒░   ░  ░░░ ▒░ ░   ░ ▒░▒░▒░    ░ ▐░  ░░ ▒░ ░░ ▒▓ ░▒▓░\r\n"
				+ "  ░   ░   ▒   ▒▒ ░░  ░      ░ ░ ░  ░     ░ ▒ ▒░    ░ ░░   ░ ░  ░  ░▒ ░ ▒░\r\n"
				+ "░ ░   ░   ░   ▒   ░      ░      ░      ░ ░ ░ ▒       ░░     ░     ░░   ░ \r\n"
				+ "      ░       ░  ░       ░      ░  ░       ░ ░        ░     ░  ░   ░     \r\n"
				+ "                                                     ░                   \r\n" + "");
		System.out.println(Language.geefVertaling("gameEnd_nl_en"));
		List<SpelerDTO> winnaars = spel.spelers().stream().filter(speler -> speler.prestigepunten() >= 15)
				.sorted(Comparator.comparing(SpelerDTO::prestigepunten).reversed()
						.thenComparing(speler -> speler.kaartenInBezit().size()))
				.toList();
		SpelerDTO referenceWinnaar = winnaars.get(0);
		for (SpelerDTO speler : winnaars) {
			if (speler.prestigepunten() == referenceWinnaar.prestigepunten()
					&& speler.kaartenInBezit().size() == referenceWinnaar.kaartenInBezit().size()) {
				System.out.println("- " + speler + " " + Language.geefVertaling("gameWinnersWith_nl_en") + " "
						+ speler.prestigepunten() + ".");
			}
		}
	}

	private boolean selecteerSpeler() {
		boolean nogFout = true;
		boolean boolSpelerSelect = false;
		String hl = "-".repeat(Math.max(Language.geefVertaling("fillInPlayerName_nl_en").length(),
				Language.geefVertaling("fillInBirthYear_nl_en").length()));
		System.out.println(hl);
		System.out.println(Language.geefVertaling("fillInPlayerName_nl_en"));
		System.out.print("> ");
		String naam = sc.next();
		do {
			try {
				System.out.println(Language.geefVertaling("fillInBirthYear_nl_en"));
				System.out.print("> ");
				Year geboortejaar = Year.parse(sc.next("[^-]{4}")); // "[^-]*" voor alle lengtes van getallen invoer
				System.out.println();

				try {
					boolSpelerSelect = dc.selecteerSpeler(new SpelerDTO(naam, geboortejaar));
					System.out.println(boolSpelerSelect ? Language.geefVertaling("playerSelectOK_nl_en")
							: Language.geefVertaling("playerSelectNOK_nl_en"));
				} catch (NullPointerException npe) {
					System.out.println(Language.geefVertaling("selectPlayerNull_nl_en"));
				} catch (SQLException sqle) {
					System.out.println(Language.geefVertaling("queryResult_nl_en"));
				} catch (IllegalArgumentException iae) {
					System.out.println(iae.getMessage());
				}
				nogFout = false;
			} catch (NoSuchElementException | DateTimeParseException dtpe) {
				System.out.println(Language.geefVertaling("yearOfBirthMisMatch_nl_en"));
				sc.nextLine();
			} finally {
				System.out.println(hl);
			}
		} while (nogFout);

		return boolSpelerSelect;
	}

	private String getFormattedChosenSpelers() {
		String namen = " | ";
		for (SpelerDTO speler : dc.geefGekozenSpelers()) {
			namen += speler.gebruikersnaam();
			namen += " | ";
		}
		return namen;
	}

	private void bouwEdelenOverzicht(List<EdeleDTO> edelen) {
		System.out.println(" ╭──────────────╮ ".repeat(edelen.size()));
		edelen.forEach((edele) -> System.out.printf(" │%d%13s│ ", edele.prestigepunt(),
				Language.geefVertaling("edele_nl_en")));
		System.out.println();
		System.out.println(" │         ─────┤ ".repeat(edelen.size()));
		int maxEdelstenenEntries = 0;
		for (EdeleDTO edele : edelen) {
			if (edele.vereisteBonus().size() > maxEdelstenenEntries) {
				maxEdelstenenEntries = edele.vereisteBonus().size();
			}
		}
		for (int i = 0; i < maxEdelstenenEntries; i++) {
			for (EdeleDTO edele : edelen) {
				String prijs = "";
				if (i < edele.vereisteBonus().size()) {
					prijs += edele.vereisteBonus().get(i);
				} else {
					prijs += " ";
				}
				System.out.printf(" │%-14s│ ", prijs);
			}
			System.out.println();
		}
		System.out.println(" ╰──────────────╯ ".repeat(edelen.size()));
	}

	private void bouwKaartenOverzicht(int niveau, int aantalInStapel, List<OntwikkelingskaartDTO> kaarten) {
		System.out.print(niveau == 0 ? "" : " ╔" + "═".repeat(14) + "╗ ");
		System.out.println(" ╒══════════════╕ ".repeat(kaarten.size()));
		System.out.print(niveau == 0 ? "" : String.format(" ║%8s%-6s║ ", "x" + aantalInStapel, ""));
		kaarten.forEach((kaart) -> System.out.printf(" │%d%13s│ ", kaart.prestigepunt(), kaart.bonus().kleur()));
		System.out.println();
		System.out.print(niveau == 0 ? "" : String.format(" ║%14s║ ", " "));
		System.out.println(" ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┤ ".repeat(kaarten.size()));
		int maxEdelstenenEntries = 0;
		for (OntwikkelingskaartDTO kaart : kaarten) {
			if (kaart.prijs().size() > maxEdelstenenEntries) {
				maxEdelstenenEntries = kaart.prijs().size();
			}
		}
		for (int i = 0; i < maxEdelstenenEntries; i++) {
			System.out.print(niveau == 0 ? ""
					: String.format(" ║%3s%8s%-3s║ ", "",
							i == 0 ? "Splendor"
									: i != maxEdelstenenEntries - 1 ? " "
											: Language.geefVertaling("niveau_nl_en") + " " + niveau,
							""));

			for (OntwikkelingskaartDTO kaart : kaarten) {
				String prijs = "";
				if (i < kaart.prijs().size()) {
					prijs += kaart.prijs().get(i);
				} else {
					prijs += " ";
				}
				System.out.printf(" │%-14s│ ", prijs);
			}
			System.out.println();
		}
		System.out.print(niveau == 0 ? "" : " ╚" + "═".repeat(14) + "╝ ");
		System.out.println(" ╘══════════════╛ ".repeat(kaarten.size()));
	}

	private void bouwEdelstenenOverzicht(List<EdelsteenDTO> edelstenen) {
		edelstenen
				.forEach((edelsteen) -> System.out.print(" ╭" + "╌".repeat(edelsteen.toString().length() + 2) + "╮ "));
		System.out.println();
		edelstenen.forEach((edelsteen) -> System.out.print(" ├╴" + edelsteen + "╶┤ "));
		System.out.println();
		edelstenen
				.forEach((edelsteen) -> System.out.print(" ╰" + "╌".repeat(edelsteen.toString().length() + 2) + "╯ "));
		System.out.println();
	}

	private void bouwPerSpelerOverzicht(SpelerDTO speler, SpelerDTO spelerAanBeurt, SpelerDTO startSpeler) {
		System.out.printf("• %s %s%n• %s %d%n• %s %s%n• %s %s%n", Language.geefVertaling("username_nl_en"),
				speler.gebruikersnaam(), Language.geefVertaling("playerTotalPrestige_nl_en"), speler.prestigepunten(),
				Language.geefVertaling("isAanBeurt_nl_en"),
				spelerAanBeurt.equals(speler) ? Language.geefVertaling("yes_nl_en")
						: Language.geefVertaling("no_nl_en"),
				Language.geefVertaling("startSpeler_nl_en"),
				startSpeler.equals(speler) ? Language.geefVertaling("yes_nl_en") : Language.geefVertaling("no_nl_en"));

		System.out.print("• " + Language.geefVertaling("kaartenBezit_nl_en") + " ");
		if (speler.kaartenInBezit().size() != 0) {
			System.out.println();
			AtomicInteger increment = new AtomicInteger();
			// kaarten groeperen per 5 anders worden alle kaarten op een lijn getekend,
			// kaarten zijn sorteerd per kleur en grote van prijs lijst in ascending order
			List<List<OntwikkelingskaartDTO>> kaartenVerdeeld = new ArrayList<>(speler.kaartenInBezit().stream()
					.collect(Collectors.groupingBy(kaart -> increment.getAndIncrement() / 5)).values());
// om kaarten te groeperen per soort, zorgt voor betere ux maar neemt te veel van console in om kaarten te tonen
//			List<List<OntwikkelingskaartDTO>> kaartenVerdeeld = new ArrayList<>(speler.kaartenInBezit().stream()
//					.collect(Collectors.groupingBy(OntwikkelingskaartDTO::bonus)).values());
			for (List<OntwikkelingskaartDTO> kaarten : kaartenVerdeeld) {
				bouwKaartenOverzicht(0, 0, kaarten);
			}
		} else {
			System.out.println(Language.geefVertaling("geen_nl_en"));
		}

		System.out.print("• " + Language.geefVertaling("edelstenenBeizt_nl_en") + " ");
		if (speler.edelstenenInBezit().size() != 0) {
			System.out.println();
			bouwEdelstenenOverzicht(speler.edelstenenInBezit());
		} else {
			System.out.println(Language.geefVertaling("geen_nl_en"));
		}

		System.out.print("• " + Language.geefVertaling("edelenBezit_nl_en") + " ");
		if (speler.edelenInBezit().size() != 0) {
			System.out.println();
			bouwEdelenOverzicht(speler.edelenInBezit());
		} else {
			System.out.println(Language.geefVertaling("geen_nl_en"));
		}
		System.out.println("=".repeat(40));
	}

	private boolean actieEdelstenenFiches(int actiekeuze, List<EdelsteenDTO> edelstenen) {
		System.out.println(Language.geefVertaling("actieEdelstenenBeschikbaar_nl_en"));
		bouwEdelstenenOverzicht(edelstenen);

		System.out.println(Language.geefVertaling("chooseEdelstenen_nl_en"));
		if (actiekeuze == 1 && Math.toIntExact(edelstenen.stream().filter(fiche -> fiche.aantal() != 0).count()) >= 3) {
			EdelsteenType vorigeKleur = null;
			String invoer;
			List<EdelsteenDTO> gekozenEdelstenenLijst = new ArrayList<>();

			for (int i = 0; i < 3; i++) {
				boolean nogFout = true;
				do {
					System.out.println(Language.geefVertaling("colorEdelsteenFirst_nl_en") + " " + (i + 1) + " "
							+ Language.geefVertaling("colorEdelsteenSecond_nl_en") + " "
							+ Language.geefVertaling("colorEdelsteenTip_nl_en"));
					invoer = sc.next().toUpperCase();
					if (invoer.equalsIgnoreCase("menu")) {
						return false;
					}
					if (invoer.equalsIgnoreCase("stop")) {
						gekozenEdelstenenLijst.forEach(dto -> dc.actieEdelsteenVoegEnAf(true, dto));
						return true;
					}
					try {
						EdelsteenType gekozenKleur = EdelsteenType.getTypeKleurOfBenaming(invoer);
						if (gekozenKleur == vorigeKleur
								|| edelstenen.stream().filter(edelsteen -> edelsteen.kleur() == gekozenKleur)
										.anyMatch(edelsteen -> edelsteen.aantal() == 0)) {
							throw new IllegalStateException(Language.geefVertaling("cannotTakeEdelsteen_nl_en"));
						}
						gekozenEdelstenenLijst.add(new EdelsteenDTO(1, gekozenKleur));
						vorigeKleur = gekozenKleur;
						nogFout = false;
					} catch (IllegalArgumentException iae) {
						System.out.println(Language.geefVertaling("gameUnknownColour_nl_en"));
						sc.nextLine();
					} catch (IllegalStateException ise) {
						System.out.println(ise.getMessage());
						sc.nextLine();
					}
				} while (nogFout);
			}
			gekozenEdelstenenLijst.forEach(dto -> dc.actieEdelsteenVoegEnAf(true, dto));

		} else if (actiekeuze == 2) {
			boolean nogFout = true;

			do {
				System.out.println(Language.geefVertaling("colorEdelsteenFirst_nl_en") + " "
						+ Language.geefVertaling("colorEdelsteenSecond_nl_en"));
				try {
					EdelsteenType gekozenKleur = EdelsteenType.getTypeKleurOfBenaming(sc.next().toUpperCase());
					if (edelstenen.stream().filter(fiche -> fiche.kleur() == gekozenKleur)
							.anyMatch(fiche -> fiche.aantal() >= 4)) {
						dc.actieEdelsteenVoegEnAf(true, new EdelsteenDTO(2, gekozenKleur));
						return true;
					}
					nogFout = false;
				} catch (IllegalArgumentException iae) {
					System.out.println(Language.geefVertaling("gameUnknownColour_nl_en"));
					sc.nextLine();
				}
			} while (nogFout);
			System.out.println(Language.geefVertaling("cannotTakeEdelsteenSpecifiek_nl_en"));
			return false;
		} else {
			System.out.println(Language.geefVertaling("cannotAction_nl_en"));
			return false;
		}
		return true;
	}

	private boolean actieOntwikkelingskaartKopen(List<OntwikkelingskaartDTO> niveauEen,
			List<OntwikkelingskaartDTO> niveauTwee, List<OntwikkelingskaartDTO> niveauDrie) {
		boolean nogFout = true;
		System.out.println();
		System.out.println(Language.geefVertaling("actieKaartenBeschikbaar_nl_en"));
		for (int i = 3; i > 0; i--) {
			List<OntwikkelingskaartDTO> lijst = i == 3 ? niveauDrie : i == 2 ? niveauTwee : niveauEen;
//			if (!lijst.isEmpty()) {
			System.out.print("─".repeat(18));
			for (int o = 1; o <= lijst.size(); o++) { // o <= lijst.size()
				System.out.print("─".repeat(8) + o + "─".repeat(9)); // 8 9
			}
			System.out.println();
			bouwKaartenOverzicht(i, dc.geefAantalKaartenInStapel(i), lijst);
//			}
		}

		if (!niveauEen.isEmpty() || !niveauTwee.isEmpty() || !niveauDrie.isEmpty()) {
			System.out.println(Language.geefVertaling("chooseKaart_nl_en"));
			int niveau = 0;
			int index = 0;
			do {
				try {
					System.out.print(Language.geefVertaling("inputChoiceCard_nl_en") + " ");
					niveau = sc.nextInt();
					if (niveau < 1 || niveau > 3) {
						throw new IllegalArgumentException(Language.geefVertaling("inputValidNumber_nl_en"));
					}

					System.out.print(Language.geefVertaling("inputIndexCard_nl_en") + " ");
					index = sc.nextInt() - 1;
					if (index < 0 || index > 3) {
						throw new IllegalArgumentException(Language.geefVertaling("inputValidNumber_nl_en"));
					}
					nogFout = false;
				} catch (InputMismatchException ime) {
					System.out.println(Language.geefVertaling("choiceInputError_nl_en"));
					sc.nextLine();
				} catch (IllegalArgumentException iae) {
					System.out.println(iae.getMessage());
					sc.nextLine();
				}
			} while (nogFout);

			List<OntwikkelingskaartDTO> kaartenLijst = niveau == 1 ? niveauEen : niveau == 2 ? niveauTwee : niveauDrie;
			if (index < kaartenLijst.size()) {
//				try {
				if (dc.actieSpelerKaartKopen(niveau, index)) {
					System.out.println(Language.geefVertaling("kaartKopenGelukt_nl_en"));
					return true;
				} else {
					System.out.println(Language.geefVertaling("notEnoughGemsCard_nl_en"));
				}
//				} catch (NoSuchElementException nsee) {
//					System.out.println("Niet voldoende edelstenen om deze kaart te kopen.");
//				}
			} else {
				System.out.println(Language.geefVertaling("notAviableCard_nl_en"));
			}
		} else {
			System.out.println(Language.geefVertaling("notAviableCard_nl_en"));
		}

		System.out.println("=".repeat(40));
		return false;
	}

	private void eventSpecialeTegel(List<EdeleDTO> edelen, List<OntwikkelingskaartDTO> kaartenInBezit) {
		List<EdeleDTO> keuzeEdelen = edelen
				.stream().filter(
						edele -> edele.vereisteBonus().stream()
								.allMatch(edelsteen -> edelsteen.aantal() <= Math.toIntExact(kaartenInBezit.stream()
										.filter(kaart -> kaart.bonus().kleur() == edelsteen.kleur()).count())))
				.toList();

		if (!keuzeEdelen.isEmpty()) {
			if (keuzeEdelen.size() == 1) {
				dc.eventSpecialeTegel(edelen.indexOf(keuzeEdelen.get(0)));
			} else {
				System.out.println(Language.geefVertaling("chooseEdele_nl_en"));
				bouwEdelenOverzicht(keuzeEdelen);
				for (int e = 1; e <= keuzeEdelen.size(); e++) {
					System.out.print("─".repeat(8) + e + "─".repeat(9));
				}
				System.out.println();
				boolean nogFout = true;
				do {
					System.out.print(Language.geefVertaling("inputChoiceEdele_nl_en") + " ");
					try {
						int index = sc.nextInt() - 1;
						if (index < 0 || index >= keuzeEdelen.size()) {
							throw new InputMismatchException();
						}
						dc.eventSpecialeTegel(index);
						nogFout = false;
					} catch (InputMismatchException ime) {
						System.out.println(Language.geefVertaling("inputValidNumber_nl_en"));
					}
				} while (nogFout);
			}
		}
	}
}
