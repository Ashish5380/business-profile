package com.personal.businessprofile.service;

import com.personal.businessprofile.bo.ProductBO;
import com.personal.businessprofile.dto.request.ProductRequest;

public interface ProductService {

    /**
     * Retrieves saved product information w.r.t. a given unique product identifier
     * @param externalProductId The unique identifier of product information.
     * @return {@link ProductBO} saved product information w.r.t. unique identifier
     */
    ProductBO getProduct(String externalProductId);

    /**
     * Recieves request to create Product information from {@link ProductRequest}
     * @param request {@link ProductRequest} request to create product information
     * @return {@link ProductBO} saved product response
     */
    ProductBO createProduct(ProductRequest request);
}
