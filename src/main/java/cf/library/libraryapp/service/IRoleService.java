package cf.library.libraryapp.service;

import cf.library.libraryapp.dto.RoleReadOnlyDTO;

import java.util.List;

public interface IRoleService {

    List<RoleReadOnlyDTO> findAllRolesSortedByName();
}
