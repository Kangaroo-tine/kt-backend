package Kangcrew.kangaroo_tine.domain.user.application;

import Kangcrew.kangaroo_tine.domain.user.converter.UserConverter;
import Kangcrew.kangaroo_tine.domain.user.domain.entitiy.Guardian;
import Kangcrew.kangaroo_tine.domain.user.domain.entitiy.Subject;
import Kangcrew.kangaroo_tine.domain.user.domain.entitiy.User;
import Kangcrew.kangaroo_tine.domain.user.domain.repository.GuardianRepository;
import Kangcrew.kangaroo_tine.domain.user.domain.repository.SubjectRepository;
import Kangcrew.kangaroo_tine.domain.user.domain.repository.UserRepository;
import Kangcrew.kangaroo_tine.domain.user.dto.request.UserRequestDTO;
import Kangcrew.kangaroo_tine.domain.user.dto.response.UserResponseDTO;
import Kangcrew.kangaroo_tine.global.error.code.status.ErrorStatus;
import Kangcrew.kangaroo_tine.global.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final GuardianRepository guardianRepository;
    private final SubjectRepository subjectRepository;

    @Override
    @Transactional
    public void setUserRole(Long userId, UserRequestDTO.SetUserRoleDTO request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        user.assignRole(request.getRole());
        userRepository.save(user);

        switch (request.getRole()) {
            case GUARDIAN -> {
                Guardian guardian = UserConverter.toGuardianFromUser(user);
                guardianRepository.save(guardian);
            }
            case SUBJECT -> {
                Subject subject = UserConverter.toSubjectFromUser(user);
                subjectRepository.save(subject);
            }
        }
    }

    @Override
    @Transactional
    public void saveGuardianPhone(Long userId, UserRequestDTO.SaveGuardianPhoneDTO request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        Subject subject = subjectRepository.findByUser(user)
                .orElseThrow(() -> new GeneralException(ErrorStatus.SUBJECT_NOT_FOUND));

        if (subject.getGuardian() != null) {
            throw new GeneralException(ErrorStatus.SUBJECT_ALREADY_CONNECTED);
        }

        subject.updateGuardianPhone(request.getPhone());

    }

    @Override
    @Transactional
    public UserResponseDTO.ConnectCodeDTO generateConnectCode(Long userId) {
        Subject subject = subjectRepository.findByUserId(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.SUBJECT_NOT_FOUND));

        String newCode = generateRandomCode();
        subject.updateConnectCode(newCode);
        subjectRepository.save(subject);

        return UserConverter.toConnectCodeDTO(newCode);
    }

    private String generateRandomCode() {
        return RandomStringUtils.randomAlphanumeric(6).toUpperCase();
    }
}
