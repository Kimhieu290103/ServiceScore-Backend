package dtn.ServiceScore.services;

import dtn.ServiceScore.dtos.RoleDTO;

public interface RoleService {
    void createRole(RoleDTO roleDTO) throws Exception;
}
