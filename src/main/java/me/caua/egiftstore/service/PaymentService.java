package me.caua.egiftstore.service;

import jakarta.validation.Valid;
import me.caua.egiftstore.dto.payment.PaymentDTO;
import me.caua.egiftstore.dto.payment.PaymentResponseDTO;

import java.util.List;

public interface PaymentService {

    void update(Long id, @Valid PaymentDTO paymentDTO);
    PaymentResponseDTO findById(Long id);
    List<PaymentResponseDTO> findAll();

}
