package dtn.ServiceScore.services.impl;

import dtn.ServiceScore.dtos.RoleDTO;
import dtn.ServiceScore.model.Role;
import dtn.ServiceScore.repositories.RoleRepository;
import dtn.ServiceScore.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public void createRole(RoleDTO roleDTO) throws RuntimeException {
        Role role = Role.builder()
                .name(roleDTO.getName())
                .build();
        roleRepository.save(role);
    }
}
