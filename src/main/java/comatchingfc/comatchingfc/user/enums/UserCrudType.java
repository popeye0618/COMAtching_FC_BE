package comatchingfc.comatchingfc.user.enums;

public enum UserCrudType {

	UPDATE("UPDATE"),
	CREATE("CREATE"),
	DELETE("DELETE");

	private final String value;

	UserCrudType(String value){
		this.value = value;
	}
}
