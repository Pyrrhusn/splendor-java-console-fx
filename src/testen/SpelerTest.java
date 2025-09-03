//package testen;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.NullAndEmptySource;
//import org.junit.jupiter.params.provider.ValueSource;
//
//import domein.Speler;
//
//class SpelerTest {
//
//	private static final String GELDIGE_NAAM = "Jens";
//	private Speler speler;
//	private static final int GELDIGE_GEBOORTEDATUM = 2003;
//
//	@BeforeEach
//	public void setUp() {
//		speler = new Speler(GELDIGE_NAAM, GELDIGE_GEBOORTEDATUM);
//	}
//
//	@ParameterizedTest
//	@ValueSource(strings = { " ", "\t \t " })
//	public void maakSpeler_naamIsLeegGeboorteDatumGeldig_throwException(String waarden) {
//		Assertions.assertThrows(IllegalArgumentException.class, () -> new Speler(waarden, GELDIGE_GEBOORTEDATUM));
//	}
//
//	@ParameterizedTest
//	@ValueSource(strings = { "1naam", "_naam" })
//	public void maakSpeler_naamBegintNietMetLetterGeboorteDatumGeldig_throwException(String waarden) {
//		Assertions.assertThrows(IllegalArgumentException.class, () -> new Speler(waarden, GELDIGE_GEBOORTEDATUM));
//	}
//
//	@NullAndEmptySource
//	@ParameterizedTest
//	@ValueSource(strings = { "naam$", "naamù", "naam@", "naam#", "naam!", "naam?" })
//	public void maakSpeler_naamBevatOngeldigeKaraktersGeboorteDatumGeldig_throwException(String waarden) {
//		Assertions.assertThrows(IllegalArgumentException.class, () -> new Speler(waarden, GELDIGE_GEBOORTEDATUM));
//	}
//
//	@ParameterizedTest
//	@ValueSource(ints = { 2019, 2025, 2029 })
//	public void maakSpeler_geldigeNaamGeboorteDatumTeJong_throwsException(int waarde) {
//		Assertions.assertThrows(IllegalArgumentException.class, () -> new Speler(GELDIGE_NAAM, waarde));
//	}
//
//	@ParameterizedTest
//	@ValueSource(strings = { GELDIGE_NAAM })
//	public void maakSpeler_naamGeldigGeboorteDatumGeldig_doesNotThrowException(String geldigeNaam) {
//		Assertions.assertDoesNotThrow(() -> new Speler(geldigeNaam, GELDIGE_GEBOORTEDATUM));
//	}
//
//}
