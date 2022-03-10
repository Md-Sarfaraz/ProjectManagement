package com.sarfaraz.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sarfaraz.management.model.Role;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

    enum Roles {
        ROLE_ADMIN("Admin can access to all EndPoints"),
        ROLE_MANAGER("Manager can manage users, projects and tickets"),
        ROLE_TESTER("Tester can raise a bug or request for features, access all the tickets" +
                " of projects which is issued by manager"),
        ROLE_DEVELOPER("Developer can accept and deal with tickets of the issued projects"),
        ROLE_PUBLIC("Public can Only learn About the project and cant interact with databases");

        private String desc;

        Roles(String desc) {
            this.desc = desc;
          
        }

        public String getDetails() {
            return this.desc;
        }

    }

}
