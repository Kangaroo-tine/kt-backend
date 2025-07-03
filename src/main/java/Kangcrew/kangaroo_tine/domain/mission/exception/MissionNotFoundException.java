package Kangcrew.kangaroo_tine.domain.mission.exception;

public class MissionNotFoundException extends RuntimeException{
    public MissionNotFoundException() {
        super("오늘의 미션 정보가 존재하지 않습니다.");
    }
}
