package util;

import java.util.Arrays;

import language.Language;

public enum EdelsteenType {
	DIAMANT(Language.geefVertaling("DIAMANT_nl_en"), Language.geefVertaling("diamantKLEUR_nl_en")),
	SAFFIER(Language.geefVertaling("SAFFIER_nl_en"), Language.geefVertaling("saffierKLEUR_nl_en")),
	SMARAGD(Language.geefVertaling("SMARAGD_nl_en"), Language.geefVertaling("smaragdKLEUR_nl_en")),
	ROBIJN(Language.geefVertaling("ROBIJN_nl_en"), Language.geefVertaling("robijnKLEUR_nl_en")),
	ONYX(Language.geefVertaling("ONYX_nl_en"), Language.geefVertaling("onyxKLEUR_nl_en"));

	private final String typeVertaald;
	private final String kleurVertaald;

	private EdelsteenType(String typeVertaald, String kleurVertaald) {
		this.typeVertaald = typeVertaald;
		this.kleurVertaald = kleurVertaald;
	}

	public static EdelsteenType getTypeKleurOfBenaming(String kleurOfBenaming) {
		return Arrays.stream(EdelsteenType.values())
				.filter(type -> type.typeVertaald.equalsIgnoreCase(kleurOfBenaming)
						|| type.kleurVertaald.equalsIgnoreCase(kleurOfBenaming))
				.findFirst().orElseThrow(() -> new IllegalArgumentException());
	}

	@Override
	public String toString() {
		return this.typeVertaald;
	}
}