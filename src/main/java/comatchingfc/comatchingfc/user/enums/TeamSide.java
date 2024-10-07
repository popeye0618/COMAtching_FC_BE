package comatchingfc.comatchingfc.user.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum TeamSide {
	HOME("HOME"),
	AWAY("AWAY");

	private final String value;

	TeamSide(String value){
		this.value = value;
	}

	@JsonCreator
	public static TeamSide from(String value) {
		for (TeamSide teamSide : TeamSide.values()) {
			if (teamSide.getValue().equals(value)) {
				return teamSide;
			}
		}
		return null;
	}

	@JsonValue
	public String getValue() {
		return value;
	}
}
