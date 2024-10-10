package comatchingfc.comatchingfc.user.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum CheerPropensityEnum {
    열정형("열정형", "뜨거운 불꽃응원"),
    집중형("집중형", "내가 내는 소리는 눈동자 굴러가는 소리 뿐"),
    축린이형("축린이형", "축구는 모르지만 이 분위기는 좋아"),
    축잘알형("축잘알형", "감독 심리 간파"),
    먹방형("먹방형", "입도 눈도 손도 바쁜"),
    인싸형("인싸형", "우리는 모두 친구");

    private final String value;
    private final String discription;

    CheerPropensityEnum(String value, String discription) {
        this.value = value;
        this.discription = discription;
    }

    @JsonCreator
    public static CheerPropensityEnum from(String value) {
        for (CheerPropensityEnum cheerPropensityEnum : CheerPropensityEnum.values()) {
            if (cheerPropensityEnum.getValue().equals(value)) {
                return cheerPropensityEnum;
            }
        }
        return null;
    }

    @JsonValue
    public String getValue() {
        return value;
    }


}
