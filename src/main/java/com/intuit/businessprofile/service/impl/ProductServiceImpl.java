package com.intuit.businessprofile.service.impl;

import com.intuit.businessprofile.bo.ProductBO;
import com.intuit.businessprofile.constant.ProductConstant;
import com.intuit.businessprofile.dto.request.ProductRequest;
import com.intuit.businessprofile.entity.ProductEntity;
import com.intuit.businessprofile.exception.InvalidProductException;
import com.intuit.businessprofile.mapper.ProductMapper;
import com.intuit.businessprofile.repository.ProductDataAccess;
import com.intuit.businessprofile.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductDataAccess productDataAccess;

    @Override
    public ProductBO getProduct(String externalProductId) {
        List<ProductEntity> productList = productDataAccess.getProductByExternalId(externalProductId);
        log.info("Found products for externalProductId :: {}, in DB :: {}", externalProductId, productList);
        return Optional.ofNullable(CollectionUtils.firstElement(productList))
          .map(ProductMapper::mapToResponse)
          .orElseThrow(() -> new InvalidProductException(ProductConstant.INVALID_PRODUCT, HttpStatus.BAD_REQUEST));
    }

    @Override
    public ProductBO createProduct(ProductRequest request) {
        // TODO :: VALIDATE IF PRODUCT EXIST
        ProductEntity entity = productDataAccess.save(ProductMapper.map(request));
        log.info("Successfully created product :: {}", entity);
        return Optional.ofNullable(entity)
          .map(ProductMapper::mapToResponse)
          .orElseThrow(() -> new InvalidProductException(ProductConstant.INVALID_PRODUCT, HttpStatus.BAD_REQUEST));
    }
}
