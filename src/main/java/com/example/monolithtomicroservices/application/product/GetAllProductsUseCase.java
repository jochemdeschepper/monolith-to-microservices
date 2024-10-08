package com.example.monolithtomicroservices.application.product;

import com.example.monolithtomicroservices.application.common.UseCase;
import com.example.monolithtomicroservices.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
public class GetAllProductsUseCase implements UseCase<ProductRequest, Page<Product>> {
    private final GetAllProductsPort getAllProductsPort;

    public GetAllProductsUseCase(GetAllProductsPort getAllProductsPort) {
        this.getAllProductsPort = getAllProductsPort;
    }

    @Override
    public Page<Product> handle(ProductRequest request) {
        return getAllProductsPort.all(request);
    }
}
