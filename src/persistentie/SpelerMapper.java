package persistentie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;

import domein.Speler;

public class SpelerMapper {
	public Speler geefSpeler(String gebruikersnaam, Year geboortejaar) throws SQLException, IllegalArgumentException {
		Speler speler = null;

		try (Connection connection = DriverManager.getConnection(Connectie.JDBC_URL)) {
			PreparedStatement query = connection.prepareStatement(
					"SELECT Gebruikersnaam, Geboortejaar FROM ID399541_g36.Speler WHERE Gebruikersnaam = ? AND Geboortejaar = ? ;");
			query.setString(1, gebruikersnaam);
			query.setInt(2, geboortejaar.getValue());

			ResultSet result = query.executeQuery();
			if (result.next()) {
				gebruikersnaam = result.getString(1);
				geboortejaar = Year.of(result.getInt(2));
				speler = new Speler(gebruikersnaam, geboortejaar);
			}
		} catch (SQLException sqle) {
			throw sqle;
		} catch (IllegalArgumentException iae) {
			throw iae;
		}
		return speler;
	}
}