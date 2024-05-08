package backend.models.enums;

public enum SkinCondition {
    FACTORY_NEW("Recién fabricado"),
    MINIMAL_WEAR("Casi nuevo"),
    FIELD_TESTED("Algo desgastado"),
    WELL_WORN("Bastante desgastado"),
    BATTLE_SCARRED("Deplorable");

    private final String condition;
    SkinCondition(String condition){
        this.condition = condition;
    }
    public String getCondition(){
        return condition;
    }

    public static boolean isValidValue(String input) {
        for (SkinCondition condition : values()) {
            if (condition.getCondition().equals(input)) {
                return true;
            }
        }
        return false;
    }

}
