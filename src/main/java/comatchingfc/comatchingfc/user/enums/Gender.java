package comatchingfc.comatchingfc.user.enums;

import lombok.Getter;

@Getter
public enum Gender {
    MALE("MALE"),
    FEMALE("FEMALE");

    private final String value;

    Gender(String value){
        this.value = value;
    }
}
