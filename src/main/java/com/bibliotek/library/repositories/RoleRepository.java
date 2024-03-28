package com.bibliotek.library.repositories;

import com.bibliotek.library.entities.ERole;
import com.bibliotek.library.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRole(ERole role);
}
