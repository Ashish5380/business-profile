package com.personal.businessprofile.service.impl;

import com.personal.businessprofile.bo.ProductBO;
import com.personal.businessprofile.constant.ProductConstant;
import com.personal.businessprofile.dto.request.ProductRequest;
import com.personal.businessprofile.entity.ProductEntity;
import com.personal.businessprofile.exception.InvalidProductException;
import com.personal.businessprofile.mapper.ProductMapper;
import com.personal.businessprofile.repository.ProductDataAccess;
import com.personal.businessprofile.service.ProductService;
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
