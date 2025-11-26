package me.caua.egiftstore.service;

import me.caua.egiftstore.dto.user.UserResponseDTO;

public interface JwtService {
    String generateJwt(UserResponseDTO user, int type);
}
