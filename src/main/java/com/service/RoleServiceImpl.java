package com.service;

import com.model.Role;
import com.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

	@Override
	public void init() {
		Role r1 = new Role();
		Role r2 = new Role();
		Role r3 = new Role();
		r1.setId(1);
		r2.setId(2);
		r3.setId(3);
		r1.setName("ADMIN");
		r2.setName("USER");
		r3.setName("AUTHOR"); 
		roleRepository.save(r1);
		roleRepository.save(r2);
		roleRepository.save(r3);
	}
}
