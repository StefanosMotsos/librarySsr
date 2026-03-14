package cf.library.libraryapp.validator;

import cf.library.libraryapp.dto.UserInsertDTO;
import cf.library.libraryapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserInsertValidator implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {return UserInsertDTO.class == clazz;}

    @Override
    public void validate(Object target, Errors errors) {
        UserInsertDTO dto = (UserInsertDTO) target;

        if (dto.username() != null && userRepository.findByUsername(dto.username()).isPresent()) {
            log.warn("User with username={} already exists", dto.username());
            errors.rejectValue("username", "username.user.exists");
        }

    }
}
