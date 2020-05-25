package org.cwm3.mgrsystem.service;

import org.cwm3.mgrsystem.model.Role;

import java.util.List;

/**
 * @Classname IRoleService
 * @Author chengweiming
 * @Date 2020/5/25 11:15
 */
public interface IRoleService {

    List<Role> getAllRoles();

    Integer addRole(Role role);

    Integer deleteRoleById(Integer rid);

}
