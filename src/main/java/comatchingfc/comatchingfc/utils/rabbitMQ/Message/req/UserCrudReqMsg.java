package comatchingfc.comatchingfc.utils.rabbitMQ.Message.req;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import comatchingfc.comatchingfc.user.enums.CheerPropensityEnum;
import comatchingfc.comatchingfc.user.enums.Gender;
import comatchingfc.comatchingfc.user.enums.TeamSide;
import comatchingfc.comatchingfc.user.enums.UserCrudType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserCrudReqMsg {
	private UserCrudType type;
	private String uuid;
	private Gender gender;
	private int age;
	private CheerPropensityEnum cheerPropensityEnum;
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
}
