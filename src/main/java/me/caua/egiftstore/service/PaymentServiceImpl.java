package me.caua.egiftstore.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import me.caua.egiftstore.dto.payment.PaymentDTO;
import me.caua.egiftstore.dto.payment.PaymentResponseDTO;
import me.caua.egiftstore.enums.PaymentStatus;
import me.caua.egiftstore.model.Payment;
import me.caua.egiftstore.repository.PaymentRepository;
import me.caua.egiftstore.validation.ValidationException;

import java.util.List;

@ApplicationScoped
public class PaymentServiceImpl implements PaymentService {

    @Inject
    PaymentRepository paymentRepository;

    @Transactional
    @Override
    public void update(Long id, @Valid PaymentDTO paymentDTO) {
        validatePaymentExists(id);

        Payment payment = paymentRepository.findById(id);
        payment.setTotalPrice(paymentDTO.totalPrice());
        payment.setPaymentStatus(paymentDTO.paymentStatus());
        payment.setPaymentGateway(paymentDTO.paymentGateway());
    }

    @Override
    public PaymentResponseDTO findById(Long id) {
        validatePaymentExists(id);
        return PaymentResponseDTO.valueOf(paymentRepository.findById(id));
    }

    @Override
    public List<PaymentResponseDTO> findAll() {
        return paymentRepository.findAll().stream().map(PaymentResponseDTO::valueOf).toList();
    }

    public void validatePaymentExists(Long id) {
        if (paymentRepository.findById(id) == null)
            throw new ValidationException("id", "There is no payment with this id");
    }
}
