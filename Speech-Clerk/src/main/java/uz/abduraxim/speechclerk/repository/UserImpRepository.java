package uz.abduraxim.speechclerk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.abduraxim.speechclerk.model.UserImp;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserImpRepository extends JpaRepository<UserImp, UUID> {

    boolean existsByEmail(String email);

    UserImp findUserImpByEmail(String email);

    Optional<UserImp> findByProviderAndProviderId(String provider, String providerId);

    Optional<UserImp> findByEmail(String email);
}
