package comatchingfc.comatchingfc.utils.rabbitMQ.Message.req;

import static comatchingfc.comatchingfc.user.enums.CheerPropensityEnum.*;
import static comatchingfc.comatchingfc.user.enums.Gender.*;
import static comatchingfc.comatchingfc.user.enums.TeamSide.*;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import comatchingfc.comatchingfc.user.enums.Gender;
import comatchingfc.comatchingfc.user.enums.TeamSide;
import comatchingfc.comatchingfc.user.entity.CheerPropensity;
import comatchingfc.comatchingfc.user.entity.UserAiInfo;
import comatchingfc.comatchingfc.user.entity.UserFeature;
import comatchingfc.comatchingfc.utils.uuid.UUIDUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class MatchReqMsg {
	private String matcherUuid;
	private Gender myGender;
	private int myAge;
	private String myPropensity;
	private Integer myPropensity1;
	private Integer myPropensity2;
	private Integer myPropensity3;
	private Integer myPropensity4;
	private Integer myPropensity5;
	private Integer myPropensity6;
	private Gender genderOption;
	private TeamSide teamOption;


	public MatchReqMsg(UserFeature userFeature, UserAiInfo userAiInfo, List<CheerPropensity> cheerPropensities){
		this.matcherUuid = UUIDUtil.bytesToHex(userAiInfo.getUuid());
		this.myGender = userFeature.getGender();
		this.myAge = userFeature.getAge();

		for(CheerPropensity cheerPropensity : cheerPropensities){
			switch(cheerPropensity.getCheerPropensityEnum()){
				case 열정형:
					this.myPropensity1 = cheerPropensity.getScore();
				case 집중형:
					this.myPropensity2 = cheerPropensity.getScore();
				case 축알못형:
					this.myPropensity3 = cheerPropensity.getScore();
				case 축잘알형:
					this.myPropensity4 = cheerPropensity.getScore();
				case 먹방형:
					this.myPropensity5 = cheerPropensity.getScore();
				case 인싸형:
					this.myPropensity6 = cheerPropensity.getScore();
			}
		}

		this.teamOption = userFeature.getTeamSide();
	}

	public static MatchReqMsg majeTest(String uuid){
		return MatchReqMsg.builder()
			.matcherUuid(uuid)
			.myGender(FEMALE)
			.myAge(24)
			.myPropensity(인싸형.getValue())
			.myPropensity1(0)
			.myPropensity2(3)
			.myPropensity3(1)
			.myPropensity4(4)
			.myPropensity5(3)
			.myPropensity6(1)
			.teamOption(HOME)
			.genderOption(MALE)
			.build();
	}

	public String toJsonString() {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null; // 직렬화 실패 시 null을 반환하거나 예외를 처리할 수 있습니다.
		}
	}
}
