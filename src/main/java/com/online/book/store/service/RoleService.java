package com.online.book.store.service;

import com.online.book.store.model.Role;
import com.online.book.store.model.Role.RoleName;
import java.util.Set;

public interface RoleService {

    Set<Role> findAllByRoleNames(Set<RoleName> roleNames);
}
