package me.caua.egiftstore.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.SecurityContext;
import me.caua.egiftstore.dto.other.EmailDTO;
import me.caua.egiftstore.dto.other.StringDTO;
import me.caua.egiftstore.model.User;
import me.caua.egiftstore.repository.UserRepository;
import me.caua.egiftstore.validation.ValidationException;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    @Inject
    UserRepository userRepository;
    @Inject
    HashService hashService;

    @Transactional
    @Override
    public void updateName(StringDTO stringDTO, SecurityContext securityContext) {
        String email = securityContext.getUserPrincipal().getName();

        User user = userRepository.findByEmail(email);

        if (user == null)
            throw new ValidationException("user", "user not found.");

        user.setName(stringDTO.content());
    }

    @Transactional
    @Override
    public void updateEmail(EmailDTO emailDTO, SecurityContext securityContext) {
        String email = securityContext.getUserPrincipal().getName();

        User user = userRepository.findByEmail(email);

        if (user == null)
            throw new ValidationException("user", "user not found.");

        user.setEmail(emailDTO.content());
    }

    @Transactional
    @Override
    public void updatePass(StringDTO stringDTO, SecurityContext securityContext) {
        String email = securityContext.getUserPrincipal().getName();

        User user = userRepository.findByEmail(email);

        if (user == null)
            throw new ValidationException("user", "user not found.");

        user.setPassword(hashService.getHashSenha(stringDTO.content()));
    }

}
