package Kangcrew.kangaroo_tine.member.repository;

import Kangcrew.kangaroo_tine.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByProviderAndOauthId(String provider, String oauthId);
}


