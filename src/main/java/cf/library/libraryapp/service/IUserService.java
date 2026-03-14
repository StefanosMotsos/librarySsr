package cf.library.libraryapp.service;

import cf.library.libraryapp.core.exceptions.EntityAlreadyExistsException;
import cf.library.libraryapp.core.exceptions.EntityInvalidArgumentException;
import cf.library.libraryapp.dto.UserInsertDTO;
import cf.library.libraryapp.dto.UserReadOnlyDTO;

public interface IUserService {

    UserReadOnlyDTO saveUser(UserInsertDTO userInsertDTO)
        throws EntityAlreadyExistsException, EntityInvalidArgumentException;

}
