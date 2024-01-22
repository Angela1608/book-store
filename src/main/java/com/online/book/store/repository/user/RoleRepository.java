package com.online.book.store.repository.user;

import com.online.book.store.model.Role;
import com.online.book.store.model.Role.RoleName;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Set<Role> findAllByRoleNameIn(Set<RoleName> roleNames);

}
