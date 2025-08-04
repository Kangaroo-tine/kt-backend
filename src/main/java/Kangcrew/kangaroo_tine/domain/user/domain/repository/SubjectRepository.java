package Kangcrew.kangaroo_tine.domain.user.domain.repository;

import Kangcrew.kangaroo_tine.domain.user.domain.entitiy.Subject;
import Kangcrew.kangaroo_tine.domain.user.domain.entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findByUser(User user);
    Optional<Subject> findByUserId(Long userId);
}
