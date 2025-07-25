package Kangcrew.kangaroo_tine.domain.mission.exception;

public class SubjectNotFoundException extends RuntimeException{
    public SubjectNotFoundException() {
        super("존재하지 않는 대상자입니다.");
    }
}
