package org.cwm3.mgrsystem.service.impl;

import org.cwm3.mgrsystem.mapper.RoleMapper;
import org.cwm3.mgrsystem.model.Role;
import org.cwm3.mgrsystem.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**

 * @作者 cwm3

 * @时间 2019-10-01 19:41
 */
@Service
public class RoleService implements IRoleService {
    @Autowired
    RoleMapper roleMapper;

    @Override
    public List<Role> getAllRoles() {
        return roleMapper.getAllRoles();
    }

    @Override
    public Integer addRole(Role role) {
        if (!role.getName().startsWith("ROLE_")) {
            role.setName("ROLE_" + role.getName());
        }
        return roleMapper.insert(role);
    }

    @Override
    public Integer deleteRoleById(Integer rid) {
        return roleMapper.deleteByPrimaryKey(rid);
    }
}
