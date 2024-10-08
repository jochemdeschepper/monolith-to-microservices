package com.example.monolithtomicroservices.application.order;

import com.example.monolithtomicroservices.application.common.UseCase;
import com.example.monolithtomicroservices.application.exception.ResourceNotFoundException;
import com.example.monolithtomicroservices.domain.Order;
import com.example.monolithtomicroservices.domain.OrderId;
import org.springframework.stereotype.Service;

@Service
public class GetOrderByIdUseCase implements UseCase<OrderId, Order> {
    private final GetOrderByIdPort getOrderByIdPort;

    public GetOrderByIdUseCase(GetOrderByIdPort getOrderByIdPort) {
        this.getOrderByIdPort = getOrderByIdPort;
    }

    @Override
    public Order handle(OrderId orderId) {
        return getOrderByIdPort.getOrderById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }
}
