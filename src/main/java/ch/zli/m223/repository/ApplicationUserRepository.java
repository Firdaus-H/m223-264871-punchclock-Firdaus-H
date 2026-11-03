package ch.zli.m223.repository;

import ch.zli.m223.model.ApplicationUser;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ApplicationUserRepository
        implements PanacheRepository<ApplicationUser> {

    public ApplicationUser findByUsername(String username) {
        return find("username", username).firstResult();
    }
}