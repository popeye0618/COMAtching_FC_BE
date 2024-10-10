package comatchingfc.comatchingfc.utils.rabbitMQ.Message.req;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import comatchingfc.comatchingfc.user.entity.CheerPropensity;
import comatchingfc.comatchingfc.user.entity.UserAiInfo;
import comatchingfc.comatchingfc.user.entity.UserFeature;
import comatchingfc.comatchingfc.user.enums.CheerPropensityEnum;
import comatchingfc.comatchingfc.user.enums.Gender;
import comatchingfc.comatchingfc.user.enums.TeamSide;
import comatchingfc.comatchingfc.user.enums.UserCrudType;
import comatchingfc.comatchingfc.utils.uuid.UUIDUtil;
import lombok.Getter;

@Getter
public class UserCrudReqMsg {
	private UserCrudType type;
	private String uuid;
	private Gender gender;
	private int age;
	private CheerPropensityEnum propensity;
	private int propensity1;
	private int propensity2;
	private int propensity3;
	private int propensity4;
	private int propensity5;
	private int propensity6;
	private TeamSide teamSide;

	public String toJsonString() {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public UserCrudReqMsg(UserFeature userFeature, List<CheerPropensity> cheerPropensities, UserAiInfo userAiInfo){
		this.uuid = UUIDUtil.bytesToHex(userAiInfo.getUuid());
		this.gender = userFeature.getGender();
		this.age = userFeature.getAge();
	 	this.propensity = userFeature.getPropensity();
		for(CheerPropensity cheerPropensity : cheerPropensities) {
			switch (cheerPropensity.getCheerPropensityEnum()) {
				case 열정형:
					this.propensity1 = cheerPropensity.getScore();
				case 집중형:
					this.propensity2 = cheerPropensity.getScore();
				case 축린이형:
					this.propensity3 = cheerPropensity.getScore();
				case 축잘알형:
					this.propensity4 = cheerPropensity.getScore();
				case 먹방형:
					this.propensity5 = cheerPropensity.getScore();
				case 인싸형:
					this.propensity6 = cheerPropensity.getScore();
			}
		}
		this.teamSide = userFeature.getTeamSide();
	}

	public void addType(UserCrudType type){
		this.type = type;
	}
}
