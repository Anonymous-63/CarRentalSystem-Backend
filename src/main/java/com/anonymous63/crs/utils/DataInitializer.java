package com.anonymous63.crs.utils;

import com.anonymous63.crs.exceptions.ResourceNotFoundException;
import com.anonymous63.crs.models.Privilege;
import com.anonymous63.crs.models.Role;
import com.anonymous63.crs.repositories.PrivilegeRepo;
import com.anonymous63.crs.repositories.RoleRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepo roleRepo;
    private final PrivilegeRepo privilegeRepo;

    public DataInitializer(RoleRepo roleRepo, PrivilegeRepo privilegeRepo) {
        this.roleRepo = roleRepo;
        this.privilegeRepo = privilegeRepo;
    }

    @Override
    public void run(String... args) throws Exception {

        List<Privilege> privileges = Arrays.asList(
                new Privilege(1L, "CREATE"),
                new Privilege(2L, "READ"),
                new Privilege(3L, "UPDATE"),
                new Privilege(4L, "DELETE"),
                new Privilege(5L, "DISABLE"),
                new Privilege(6L, "ENABLE"),
                new Privilege(7L, "FIND_ALL"),
                new Privilege(8L, "FIND_BY_ID"),
                new Privilege(9L, "IMPORT"),
                new Privilege(10L, "EXPORT")
        );
        this.privilegeRepo.saveAll(privileges);

        Privilege createPrivilege = this.privilegeRepo.findById(1L).orElseThrow(() -> new ResourceNotFoundException(Privilege.class.getSimpleName(), 1L));
        Privilege readPrivilege = this.privilegeRepo.findById(2L).orElseThrow(() -> new ResourceNotFoundException(Privilege.class.getSimpleName(), 2L));
        Privilege updatePrivilege = this.privilegeRepo.findById(3L).orElseThrow(() -> new ResourceNotFoundException(Privilege.class.getSimpleName(), 3L));
        Privilege deletePrivilege = this.privilegeRepo.findById(4L).orElseThrow(() -> new ResourceNotFoundException(Privilege.class.getSimpleName(), 4L));
        Privilege disablePrivilege = this.privilegeRepo.findById(5L).orElseThrow(() -> new ResourceNotFoundException(Privilege.class.getSimpleName(), 5L));
        Privilege enablePrivilege = this.privilegeRepo.findById(6L).orElseThrow(() -> new ResourceNotFoundException(Privilege.class.getSimpleName(), 6L));
        Privilege fineAllPrivilege = this.privilegeRepo.findById(7L).orElseThrow(() -> new ResourceNotFoundException(Privilege.class.getSimpleName(), 7L));
        Privilege findByIdPrivilege = this.privilegeRepo.findById(8L).orElseThrow(() -> new ResourceNotFoundException(Privilege.class.getSimpleName(), 8L));
        Privilege importPrivilege = this.privilegeRepo.findById(9L).orElseThrow(() -> new ResourceNotFoundException(Privilege.class.getSimpleName(), 9L));
        Privilege exportPrivilege = this.privilegeRepo.findById(10L).orElseThrow(() -> new ResourceNotFoundException(Privilege.class.getSimpleName(), 10L));


        // Create roles
        Role adminRole = createRole(1L, "ROLE_ADMIN", Arrays.asList(createPrivilege, readPrivilege, updatePrivilege, deletePrivilege, disablePrivilege, enablePrivilege, fineAllPrivilege, findByIdPrivilege, importPrivilege, exportPrivilege));
        Role userRole = createRole(2L, "ROLE_USER", Arrays.asList(readPrivilege, fineAllPrivilege, findByIdPrivilege));
        Role ownerRole = createRole(2L, "ROLE_Owner", Arrays.asList(createPrivilege, readPrivilege, updatePrivilege, findByIdPrivilege));

        // Save roles
        roleRepo.save(adminRole);
        roleRepo.save(userRole);

    }

    private Role createRole(Long id, String name, List<Privilege> privileges) {
        Role role = new Role();
        role.setId(id);
        role.setName(name);
        role.getPrivileges().addAll(privileges);
        return role;
    }
}
