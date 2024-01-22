package com.online.book.store.service.impl;

import com.online.book.store.model.Role;
import com.online.book.store.model.Role.RoleName;
import com.online.book.store.repository.user.RoleRepository;
import com.online.book.store.service.RoleService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Set<Role> findAllByRoleNames(Set<RoleName> roleNames) {
        return roleRepository.findAllByRoleNameIn(roleNames);
    }

}
