package comatchingfc.comatchingfc.user.dto;

import comatchingfc.comatchingfc.user.enums.CheerPropensityEnum;
import lombok.Getter;

@Getter
public class SurveyResult {
    private int passionType;
    private int focusType;
    private int soccerNoviceType;
    private int soccerExpertType;
    private int mukbangType;
    private int socialType;

    /**
     * 가장 높은 점수를 가진 CheerPropensityEnum을 반환하는 메소드
     * @return 가장 높은 점수를 가진 CheerPropensityEnum, 모든 점수가 0인 경우 null 반환
     */
    public CheerPropensityEnum getHighestCheerPropensityType() {
        CheerPropensityEnum maxType = null;
        int maxScore = Integer.MIN_VALUE;

        for (CheerPropensityEnum type : CheerPropensityEnum.values()) {
            int score = getScoreByType(type);
            if (score > maxScore) {
                maxScore = score;
                maxType = type;
            }
        }

        return maxType;
    }

    /**
     * 주어진 CheerPropensityEnum에 해당하는 점수를 반환하는 헬퍼 메소드
     * @param type CheerPropensityEnum 타입
     * @return 해당 타입의 점수
     */
    private int getScoreByType(CheerPropensityEnum type) {
        switch(type) {
            case 열정형:
                return passionType;
            case 집중형:
                return focusType;
            case 축알못형:
                return soccerNoviceType;
            case 축잘알형:
                return soccerExpertType;
            case 먹방형:
                return mukbangType;
            case 인싸형:
                return socialType;
            default:
                return 0;
        }
    }
}
