package language;

import java.util.ResourceBundle;

public class Language {

	public static ResourceBundle resourceBundle;
//	String[] talen = {"NL", "EN"};

	public static ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	public static void setRecourseBudel(ResourceBundle resourceBundle) {
		Language.resourceBundle = resourceBundle;
	}

	public static String geefVertaling(String key) {
		return resourceBundle.getString(key);
	}

}
