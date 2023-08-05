package com.personal.businessprofile.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.businessprofile.bo.SubscriptionBO;
import com.personal.businessprofile.dto.request.SubscriptionRequest;
import com.personal.businessprofile.entity.SubscriptionEntity;
import com.personal.businessprofile.exception.SubscriptionAlreadyExistException;
import com.personal.businessprofile.repository.SubscriptionDataAccess;
import com.personal.businessprofile.service.impl.RevisionObserverRegistry;
import com.personal.businessprofile.service.impl.SubscriptionServiceImpl;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SubscriptionServiceImpl.class, ObjectMapper.class,
    RevisionObserverRegistry.class})
@TestPropertySource("classpath:application.properties")
public class SubscriptionServiceImplTest {

  @MockBean
  private SubscriptionDataAccess subscriptionDataAccess;

  @MockBean
  private ProductService productService;

  @MockBean
  private ProfileMappingService profileMappingService;

  @MockBean
  private RevisionObserverRegistry revisionObserverRegistry;

  @MockBean
  private BusinessProfileRevisionManager businessProfileRevisionManager;

  @Autowired
  private SubscriptionService subscriptionService;

  private List<SubscriptionEntity> createSampleSubscriptionEntities() {
    List<SubscriptionEntity> subscriptionEntityList = new ArrayList<>();

    // Sample data 1
    SubscriptionEntity entity1 = new SubscriptionEntity();
    entity1.setExternalProductId("ABC123");
    entity1.setBusinessProfileId("BusinessProfile1");
    entity1.setIsActive(true);
    subscriptionEntityList.add(entity1);

    // Sample data 2
    SubscriptionEntity entity2 = new SubscriptionEntity();
    entity2.setExternalProductId("DEF456");
    entity2.setBusinessProfileId("BusinessProfile2");
    entity2.setIsActive(false);
    subscriptionEntityList.add(entity2);

    // Add more sample data as needed

    return subscriptionEntityList;
  }

  public List<SubscriptionBO> createSampleSubscriptionBOs() {
    List<SubscriptionBO> subscriptionBOList = new ArrayList<>();

    // Sample data 1
    SubscriptionBO bo1 = new SubscriptionBO();
    bo1.setExternalProductId("ABC123");
    bo1.setBusinessProfileId("BusinessProfile1");
    bo1.setIsActive(true);
    subscriptionBOList.add(bo1);

    // Sample data 2
    SubscriptionBO bo2 = new SubscriptionBO();
    bo2.setExternalProductId("DEF456");
    bo2.setBusinessProfileId("BusinessProfile2");
    bo2.setIsActive(false);
    subscriptionBOList.add(bo2);

    // Add more sample data as needed

    return subscriptionBOList;
  }

  @Test
  public void testGetSubscriptionEmptyList() {
    // Arrange
    String businessProfileId = "sampleBusinessProfileId";
    when(subscriptionDataAccess.getByBusinessProfileIdAndIsActive(businessProfileId, true))
        .thenReturn(Collections.emptyList());

    // Act
    List<SubscriptionBO> result = subscriptionService.getSubscription(businessProfileId);

    // Assert
    assertTrue(result.isEmpty());
    verify(subscriptionDataAccess).getByBusinessProfileIdAndIsActive(businessProfileId, true);
  }

  @Test
  public void testGetSubscriptionNonEmptyList() {
    // Arrange
    String businessProfileId = "sampleBusinessProfileId";
    List<SubscriptionEntity> subscriptionEntityList = createSampleSubscriptionEntities();
    when(subscriptionDataAccess.getByBusinessProfileIdAndIsActive(businessProfileId,
        true)).thenReturn(subscriptionEntityList);

    // Act
    List<SubscriptionBO> result = subscriptionService.getSubscription(businessProfileId);

    // Assert
    assertFalse(result.isEmpty());
    assertEquals(subscriptionEntityList.size(), result.size());
    // Add more specific assertions based on your mapper logic and data in subscriptionEntityList
    verify(subscriptionDataAccess).getByBusinessProfileIdAndIsActive(businessProfileId, true);
  }

  @Test
  public void testGetSubscriptionNullResult() {
    // Arrange
    String businessProfileId = "sampleBusinessProfileId";
    when(subscriptionDataAccess.getByBusinessProfileIdAndIsActive(businessProfileId,
        true)).thenReturn(null);

    // Act
    List<SubscriptionBO> result = subscriptionService.getSubscription(businessProfileId);

    // Assert
    assertNotNull(result);
    assertTrue(result.isEmpty());
    verify(subscriptionDataAccess).getByBusinessProfileIdAndIsActive(businessProfileId, true);
  }

  @Test
  public void testGetSubscriptionMapping() {
    // Arrange
    String businessProfileId = "sampleBusinessProfileId";
    List<SubscriptionEntity> subscriptionEntityList = createSampleSubscriptionEntities();
    when(subscriptionDataAccess.getByBusinessProfileIdAndIsActive(businessProfileId,
        true)).thenReturn(subscriptionEntityList);

    // Act
    List<SubscriptionBO> result = subscriptionService.getSubscription(businessProfileId);

    // Assert
    assertFalse(result.isEmpty());
    assertEquals(subscriptionEntityList.size(), result.size());
  }

  @Test
  public void testRemoveSubscriptionFoundAndInactive() {
    // Arrange
    String businessProfileId = "BusinessProfile1";
    String externalProductId = "ABC123";
    List<SubscriptionEntity> subscriptionEntityList = createSampleSubscriptionEntities();
    when(subscriptionDataAccess.getByBusinessProfileIdAndIsActive(businessProfileId,
        true)).thenReturn(subscriptionEntityList);

    // Act
    subscriptionService.removeSubscription(businessProfileId, externalProductId);

    // Assert
//    verify(subscriptionService).getSubscription(businessProfileId);
    verify(revisionObserverRegistry).removeObserver(businessProfileId, externalProductId);

    // Check if the upsertSubscription method is called with the expected entity and isActive is set to false
    ArgumentCaptor<SubscriptionEntity> argumentCaptor = ArgumentCaptor.forClass(SubscriptionEntity.class);
    verify(subscriptionDataAccess).upsertSubscription(argumentCaptor.capture());
    SubscriptionEntity capturedEntity = argumentCaptor.getValue();
    assertNotNull(capturedEntity);
    assertEquals(externalProductId, capturedEntity.getExternalProductId());
    assertEquals(businessProfileId, capturedEntity.getBusinessProfileId());
    assertFalse(capturedEntity.getIsActive());
  }

  @Test
  public void testRemoveSubscriptionNotFound() {
    // Arrange
    String businessProfileId = "sampleBusinessProfileId";
    String externalProductId = "nonExistentExternalProductId";
    when(subscriptionDataAccess.getByBusinessProfileIdAndIsActive(businessProfileId,
        true)).thenReturn(Collections.emptyList());

    // Act
    subscriptionService.removeSubscription(businessProfileId, externalProductId);

    // Assert
    verify(revisionObserverRegistry).removeObserver(businessProfileId, externalProductId);
    verify(subscriptionDataAccess, never()).upsertSubscription(any(SubscriptionEntity.class));
  }

  /**
   * Method under test: {@link SubscriptionServiceImpl#createSubscription(SubscriptionRequest)}
   */
  @Test
  void testCreateSubscription2() {
    when(subscriptionDataAccess.getByExternalProductIdAndBusinessProfileIdAndIsActive(
        Mockito.<String>any(),
        Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
    when(productService.getProduct(Mockito.<String>any())).thenReturn(null);
    when(businessProfileRevisionManager.getBusinessProfile(Mockito.<String>any()))
        .thenThrow(new SubscriptionAlreadyExistException("An error occurred", HttpStatus.CONTINUE));
    assertThrows(SubscriptionAlreadyExistException.class,
        () -> subscriptionService.createSubscription(new SubscriptionRequest("42", "42")));
    verify(subscriptionDataAccess).getByExternalProductIdAndBusinessProfileIdAndIsActive(
        Mockito.<String>any(),
        Mockito.<String>any(), Mockito.<Boolean>any());
    verify(productService).getProduct(Mockito.<String>any());
    verify(businessProfileRevisionManager).getBusinessProfile(Mockito.<String>any());
  }

  /**
   * Method under test: {@link SubscriptionServiceImpl#createSubscription(SubscriptionRequest)}
   */
  @Test
  void testCreateSubscription3() {
    SubscriptionEntity subscriptionEntity = new SubscriptionEntity();
    subscriptionEntity.setBusinessProfileId("42");
    subscriptionEntity.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    subscriptionEntity.setExternalProductId("42");
    subscriptionEntity.setId(ObjectId.get());
    subscriptionEntity.setIsActive(true);
    subscriptionEntity.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());

    ArrayList<SubscriptionEntity> subscriptionEntityList = new ArrayList<>();
    subscriptionEntityList.add(subscriptionEntity);
    when(subscriptionDataAccess.getByExternalProductIdAndBusinessProfileIdAndIsActive(
        Mockito.<String>any(),
        Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(subscriptionEntityList);
    assertThrows(SubscriptionAlreadyExistException.class,
        () -> subscriptionService.createSubscription(new SubscriptionRequest("42", "42")));
    verify(subscriptionDataAccess).getByExternalProductIdAndBusinessProfileIdAndIsActive(
        Mockito.<String>any(),
        Mockito.<String>any(), Mockito.<Boolean>any());
  }

}
