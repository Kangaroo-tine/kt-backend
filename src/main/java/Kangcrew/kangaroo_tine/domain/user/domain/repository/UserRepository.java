package Kangcrew.kangaroo_tine.domain.user.domain.repository;

import Kangcrew.kangaroo_tine.domain.user.domain.entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByKakaoId(Long kakaoId);
}
