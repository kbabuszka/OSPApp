package net.babuszka.osp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.babuszka.osp.model.Role;
import net.babuszka.osp.repository.RoleRepository;

@Service
public class RoleService {

	RoleRepository roleRepository;

	@Autowired
	public void setRoleRepository(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	
	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}
	
	public Role getRole(Integer id) {
		return roleRepository.getOne(id);
	}
}
