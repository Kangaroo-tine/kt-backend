package Kangcrew.kangaroo_tine.global.config.response;

public enum MissionCode implements BaseCode{
    MISSION_200("MISSION_200", "현재 수행할 미션 조회 성공");

    private final String code;
    private final String message;

    MissionCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
}
