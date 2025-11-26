package me.caua.egiftstore.service;

import jakarta.validation.Valid;
import jakarta.ws.rs.core.SecurityContext;
import me.caua.egiftstore.dto.other.EmailDTO;
import me.caua.egiftstore.dto.other.StringDTO;
import me.caua.egiftstore.dto.payment.PaymentDTO;
import me.caua.egiftstore.dto.payment.PaymentResponseDTO;

import java.util.List;

public interface UserService {

    void updateName(@Valid StringDTO stringDTO, SecurityContext securityContext);
    void updateEmail(@Valid EmailDTO emailDTO, SecurityContext securityContext);
    void updatePass(@Valid StringDTO stringDTO, SecurityContext securityContext);

}
