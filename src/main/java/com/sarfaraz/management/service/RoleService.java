package com.sarfaraz.management.service;

import com.sarfaraz.management.model.Role;
import com.sarfaraz.management.model.User;
import com.sarfaraz.management.repository.RoleRepo;
import com.sarfaraz.management.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private RoleRepo repo;

    @Autowired
    public RoleService(RoleRepo repo) {
        this.repo = repo;
    }

    public List<Role> listAll() {
        return repo.findAll();
    }

    public void save(Role role) {
        repo.save(role);
    }

    public Optional<Role> get(Long id) {
        return repo.findById(id);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
