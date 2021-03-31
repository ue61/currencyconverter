package ru.bober.currencyconverter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bober.currencyconverter.entity.Users;

@Repository
public interface RegistrationRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
}
