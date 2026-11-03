package ch.zli.m223.service;

import java.util.Set;

import ch.zli.m223.dto.LoginDto;
import ch.zli.m223.model.ApplicationUser;
import ch.zli.m223.repository.ApplicationUserRepository;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotAuthorizedException;

@ApplicationScoped
public class AuthService {

    @Inject
    ApplicationUserRepository applicationUserRepository;

    public String login(LoginDto dto) {

        ApplicationUser user = applicationUserRepository.findByUsername(dto.getUsername());

        if (user == null) {
            throw new NotAuthorizedException("Invalid credentials");
        }

        boolean validPassword = BcryptUtil.matches(
                dto.getPassword(),
                user.getPasswordHash());

        if (!validPassword) {
            throw new NotAuthorizedException("Invalid credentials");
        }

        return Jwt.upn(user.getUsername())
                .subject(user.getId().toString())
                .issuer("https://example.com/issuer")
                .groups(Set.of(user.getRole()))
                .sign();
    }
}