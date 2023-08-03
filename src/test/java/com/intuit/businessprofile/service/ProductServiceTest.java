package com.intuit.businessprofile.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.intuit.businessprofile.bo.ProductBO;
import com.intuit.businessprofile.dto.request.ProductRequest;
import com.intuit.businessprofile.entity.ProductEntity;
import com.intuit.businessprofile.exception.InvalidProductException;
import com.intuit.businessprofile.repository.ProductDataAccess;
import com.intuit.businessprofile.service.ProductService;
import com.intuit.businessprofile.service.impl.ProductServiceImpl;
import java.time.LocalDate;
import java.util.ArrayList;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ProductServiceImpl.class})
class ProductServiceTest {

  @MockBean
  private ProductDataAccess productDataAccess;

  @Autowired
  private ProductService productService;

  /**
   * Method under test: {@link ProductServiceImpl#getProduct(String)}
   */
  @Test
  void testGetProduct() {
    when(productDataAccess.getProductByExternalId(Mockito.<String>any())).thenReturn(
        new ArrayList<>());
    assertThrows(InvalidProductException.class, () -> productService.getProduct("42"));
    verify(productDataAccess).getProductByExternalId(Mockito.<String>any());
  }

  /**
   * Method under test: {@link ProductServiceImpl#getProduct(String)}
   */
  @Test
  void testGetProduct2() {
    ProductEntity productEntity = new ProductEntity();
    productEntity.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    productEntity.setDescription("The characteristics of someone or something");
    productEntity.setExternalProductId("42");
    ObjectId id = ObjectId.get();
    productEntity.setId(id);
    productEntity.setName("Found products for externalProductId :: {}, in DB :: {}");
    productEntity.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());

    ArrayList<ProductEntity> productEntityList = new ArrayList<>();
    productEntityList.add(productEntity);
    when(productDataAccess.getProductByExternalId(Mockito.<String>any())).thenReturn(
        productEntityList);
    ProductBO actualProduct = productService.getProduct("42");
    assertNull(actualProduct.getCreatedAt());
    assertNull(actualProduct.getUpdatedAt());
    assertEquals("Found products for externalProductId :: {}, in DB :: {}",
        actualProduct.getName());
    assertSame(id, actualProduct.getId());
    assertEquals("42", actualProduct.getExternalProductId());
    assertEquals("The characteristics of someone or something", actualProduct.getDescription());
    verify(productDataAccess).getProductByExternalId(Mockito.<String>any());
  }

  /**
   * Method under test: {@link ProductServiceImpl#getProduct(String)}
   */
  @Test
  void testGetProduct3() {
    when(productDataAccess.getProductByExternalId(Mockito.<String>any()))
        .thenThrow(new InvalidProductException("An error occurred", HttpStatus.CONTINUE));
    assertThrows(InvalidProductException.class, () -> productService.getProduct("42"));
    verify(productDataAccess).getProductByExternalId(Mockito.<String>any());
  }

  /**
   * Method under test: {@link ProductServiceImpl#createProduct(ProductRequest)}
   */
  @Test
  void testCreateProduct() {
    ProductEntity productEntity = new ProductEntity();
    productEntity.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    productEntity.setDescription("The characteristics of someone or something");
    productEntity.setExternalProductId("42");
    ObjectId id = ObjectId.get();
    productEntity.setId(id);
    productEntity.setName("Name");
    productEntity.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(productDataAccess.save(Mockito.<ProductEntity>any())).thenReturn(productEntity);
    ProductBO actualCreateProductResult = productService
        .createProduct(
            new ProductRequest("42", "Name", "The characteristics of someone or something"));
    assertNull(actualCreateProductResult.getCreatedAt());
    assertNull(actualCreateProductResult.getUpdatedAt());
    assertEquals("Name", actualCreateProductResult.getName());
    assertSame(id, actualCreateProductResult.getId());
    assertEquals("42", actualCreateProductResult.getExternalProductId());
    assertEquals("The characteristics of someone or something",
        actualCreateProductResult.getDescription());
    verify(productDataAccess).save(Mockito.<ProductEntity>any());
  }
}

