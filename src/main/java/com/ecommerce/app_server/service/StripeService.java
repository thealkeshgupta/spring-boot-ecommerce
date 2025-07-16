package com.ecommerce.app_server.service;

import com.ecommerce.app_server.payload.StripePaymentDTO;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface StripeService {
    PaymentIntent paymentIntent(StripePaymentDTO stripePaymentDTO) throws StripeException;;
}
