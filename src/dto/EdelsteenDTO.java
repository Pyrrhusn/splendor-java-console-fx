package dto;

import domein.Edelsteen;
import util.EdelsteenType;

public record EdelsteenDTO(int aantal, EdelsteenType kleur) {
	public EdelsteenDTO(Edelsteen edelsteen) {
		this(edelsteen.getAantal(), edelsteen.getKleur());
	}

	@Override
	public String toString() {
		return String.format("%dx %s", aantal(), kleur());
	}
}
