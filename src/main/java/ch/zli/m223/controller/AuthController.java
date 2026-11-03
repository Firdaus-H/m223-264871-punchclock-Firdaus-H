package ch.zli.m223.controller;

import ch.zli.m223.dto.LoginDto;
import ch.zli.m223.service.AuthService;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.TEXT_PLAIN)
public class AuthController {

    @Inject
    AuthService authService;

    @POST
    @Path("/login")
    public String login(@Valid LoginDto dto) {
        return authService.login(dto);
    }
}