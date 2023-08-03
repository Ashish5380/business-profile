package com.intuit.businessprofile.service;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.intuit.businessprofile.bo.BusinessProfileResponseBO;
import com.intuit.businessprofile.bo.BusinessProfileRevisionBO;
import com.intuit.businessprofile.bo.ProfileMappingBO;
import com.intuit.businessprofile.cache.Locker;
import com.intuit.businessprofile.dto.request.BusinessProfileCreateRequest;
import com.intuit.businessprofile.dto.request.BusinessProfileUpdateRequest;
import com.intuit.businessprofile.enums.TaxIdentifierType;
import com.intuit.businessprofile.exception.ResourceOccupiedException;
import com.intuit.businessprofile.model.TaxIdentifier;
import com.intuit.businessprofile.model.address.BusinessAddress;
import com.intuit.businessprofile.model.address.LegalAddress;
import com.intuit.businessprofile.service.BusinessProfileRevision;
import com.intuit.businessprofile.service.BusinessProfileRevisionManager;
import com.intuit.businessprofile.service.BusinessProfileService;
import com.intuit.businessprofile.service.ProfileMappingService;
import com.intuit.businessprofile.service.impl.BusinessProfileRevisionManagerImpl;
import com.intuit.businessprofile.service.impl.RevisionObserverRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
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
@ContextConfiguration(classes = {BusinessProfileRevisionManagerImpl.class})
public class BusinessProfileRevisionManagerTest {

  @MockBean
  private BusinessProfileRevision businessProfileRevision;

  @MockBean
  private BusinessProfileService businessProfileService;

  @MockBean
  private Locker locker;

  @MockBean
  private ProfileMappingService profileMappingService;

  @MockBean
  private RevisionObserverRegistry revisionObserverRegistry;

  @Autowired
  private BusinessProfileRevisionManager businessProfileRevisionManager;

  /**
   * Method under test: {@link BusinessProfileRevisionManagerImpl#getBusinessProfile(String)}
   */
  @Test
  void testGetBusinessProfile() {
    when(businessProfileService.fetchBusinessProfile(Mockito.<String>any())).thenReturn(null);
    assertNull(businessProfileRevisionManager.getBusinessProfile("42"));
    verify(businessProfileService).fetchBusinessProfile(Mockito.<String>any());
  }

  /**
   * Method under test: {@link BusinessProfileRevisionManagerImpl#getBusinessProfile(String)}
   */
  @Test
  void testGetBusinessProfile2() {
    when(businessProfileService.fetchBusinessProfile(Mockito.<String>any()))
        .thenThrow(new ResourceOccupiedException("An error occurred", HttpStatus.CONTINUE));
    assertThrows(ResourceOccupiedException.class,
        () -> businessProfileRevisionManager.getBusinessProfile("42"));
    verify(businessProfileService).fetchBusinessProfile(Mockito.<String>any());
  }

  /**
   * Method under test: {@link BusinessProfileRevisionManagerImpl#getAllBusinessProfile()}
   */
  @Test
  void testGetAllBusinessProfile() {
    ArrayList<BusinessProfileResponseBO> businessProfileResponseBOList = new ArrayList<>();
    when(businessProfileService.getAllBusinessProfile()).thenReturn(businessProfileResponseBOList);
    List<BusinessProfileResponseBO> actualAllBusinessProfile = businessProfileRevisionManager
        .getAllBusinessProfile();
    assertSame(businessProfileResponseBOList, actualAllBusinessProfile);
    assertTrue(actualAllBusinessProfile.isEmpty());
    verify(businessProfileService).getAllBusinessProfile();
  }

  /**
   * Method under test: {@link BusinessProfileRevisionManagerImpl#getAllBusinessProfile()}
   */
  @Test
  void testGetAllBusinessProfile2() {
    when(businessProfileService.getAllBusinessProfile())
        .thenThrow(new ResourceOccupiedException("An error occurred", HttpStatus.CONTINUE));
    assertThrows(ResourceOccupiedException.class,
        () -> businessProfileRevisionManager.getAllBusinessProfile());
    verify(businessProfileService).getAllBusinessProfile();
  }

  /**
   * Method under test:
   * {@link BusinessProfileRevisionManagerImpl#createBusinessProfile(BusinessProfileCreateRequest)}
   */
  @Test
  void testCreateBusinessProfile2() {
    when(businessProfileService.createBusinessProfile(Mockito.<BusinessProfileCreateRequest>any()))
        .thenThrow(new ResourceOccupiedException("An error occurred", HttpStatus.CONTINUE));
    when(locker.hasLock(Mockito.<String>any(), anyLong(), Mockito.<TimeUnit>any())).thenReturn(
        true);
    assertThrows(ResourceOccupiedException.class,
        () -> businessProfileRevisionManager.createBusinessProfile(
            new BusinessProfileCreateRequest()));
    verify(businessProfileService).createBusinessProfile(
        Mockito.<BusinessProfileCreateRequest>any());
    verify(locker).hasLock(Mockito.<String>any(), anyLong(), Mockito.<TimeUnit>any());
  }

  /**
   * Method under test:
   * {@link BusinessProfileRevisionManagerImpl#createBusinessProfile(BusinessProfileCreateRequest)}
   */
  @Test
  void testCreateBusinessProfile4() {
    when(locker.hasLock(Mockito.<String>any(), anyLong(), Mockito.<TimeUnit>any())).thenReturn(
        false);
    assertThrows(ResourceOccupiedException.class,
        () -> businessProfileRevisionManager.createBusinessProfile(
            new BusinessProfileCreateRequest()));
    verify(locker).hasLock(Mockito.<String>any(), anyLong(), Mockito.<TimeUnit>any());
  }

  /**
   * Method under test:
   * {@link BusinessProfileRevisionManagerImpl#createBusinessProfile(BusinessProfileCreateRequest)}
   */
  @Test
  void testCreateBusinessProfile5() {
    when(businessProfileRevision.createBusinessProfileRevision(
        Mockito.<BusinessProfileRevisionBO>any()))
        .thenReturn(new BusinessProfileRevisionBO());

    BusinessAddress businessAddress = new BusinessAddress();
    businessAddress.setCity("Oxford");
    businessAddress.setCountry("GB");
    businessAddress.setLine1("Line1");
    businessAddress.setLine2("Line2");
    businessAddress.setPinCode("Pin Code");
    businessAddress.setState("MD");

    LegalAddress legalAddress = new LegalAddress();
    legalAddress.setCity("Oxford");
    legalAddress.setCountry("GB");
    legalAddress.setLine1("Line1");
    legalAddress.setLine2("Line2");
    legalAddress.setPinCode("Pin Code");
    legalAddress.setState("MD");
    BusinessProfileResponseBO businessProfileResponseBO = mock(BusinessProfileResponseBO.class);
    when(businessProfileResponseBO.getTaxIdentifier()).thenReturn(
        new TaxIdentifier(TaxIdentifierType.PAN, "42"));
    when(businessProfileResponseBO.getBusinessAddress()).thenReturn(businessAddress);
    when(businessProfileResponseBO.getLegalAddress()).thenReturn(legalAddress);
    when(businessProfileResponseBO.getCompanyName()).thenReturn("Company Name");
    when(businessProfileResponseBO.getEmail()).thenReturn("jane.doe@example.org");
    when(businessProfileResponseBO.getLegalName()).thenReturn("Legal Name");
    when(businessProfileResponseBO.getWebsite()).thenReturn("Website");
    when(businessProfileResponseBO.getId()).thenReturn(ObjectId.get());
    when(businessProfileService.createBusinessProfile(Mockito.<BusinessProfileCreateRequest>any()))
        .thenReturn(businessProfileResponseBO);
    doNothing().when(locker).releaseLock(Mockito.<String>any());
    when(locker.hasLock(Mockito.<String>any(), anyLong(), Mockito.<TimeUnit>any())).thenReturn(
        true);
    businessProfileRevisionManager.createBusinessProfile(new BusinessProfileCreateRequest());
    verify(businessProfileRevision).createBusinessProfileRevision(
        Mockito.<BusinessProfileRevisionBO>any());
    verify(businessProfileService).createBusinessProfile(
        Mockito.<BusinessProfileCreateRequest>any());
    verify(businessProfileResponseBO).getTaxIdentifier();
    verify(businessProfileResponseBO).getBusinessAddress();
    verify(businessProfileResponseBO).getLegalAddress();
    verify(businessProfileResponseBO).getCompanyName();
    verify(businessProfileResponseBO).getEmail();
    verify(businessProfileResponseBO).getLegalName();
    verify(businessProfileResponseBO).getWebsite();
    verify(businessProfileResponseBO, atLeast(1)).getId();
    verify(locker).hasLock(Mockito.<String>any(), anyLong(), Mockito.<TimeUnit>any());
    verify(locker).releaseLock(Mockito.<String>any());
  }

  /**
   * Method under test:
   * {@link BusinessProfileRevisionManagerImpl#createBusinessProfile(BusinessProfileCreateRequest)}
   */
  @Test
  void testCreateBusinessProfile6() {
    when(businessProfileRevision.createBusinessProfileRevision(
        Mockito.<BusinessProfileRevisionBO>any()))
        .thenReturn(new BusinessProfileRevisionBO());

    BusinessAddress businessAddress = new BusinessAddress();
    businessAddress.setCity("Oxford");
    businessAddress.setCountry("GB");
    businessAddress.setLine1("Line1");
    businessAddress.setLine2("Line2");
    businessAddress.setPinCode("Pin Code");
    businessAddress.setState("MD");

    LegalAddress legalAddress = new LegalAddress();
    legalAddress.setCity("Oxford");
    legalAddress.setCountry("GB");
    legalAddress.setLine1("Line1");
    legalAddress.setLine2("Line2");
    legalAddress.setPinCode("Pin Code");
    legalAddress.setState("MD");
    BusinessProfileResponseBO businessProfileResponseBO = mock(BusinessProfileResponseBO.class);
    when(businessProfileResponseBO.getTaxIdentifier()).thenReturn(
        new TaxIdentifier(TaxIdentifierType.PAN, "42"));
    when(businessProfileResponseBO.getBusinessAddress()).thenReturn(businessAddress);
    when(businessProfileResponseBO.getLegalAddress()).thenReturn(legalAddress);
    when(businessProfileResponseBO.getCompanyName()).thenReturn("Company Name");
    when(businessProfileResponseBO.getEmail()).thenReturn("jane.doe@example.org");
    when(businessProfileResponseBO.getLegalName()).thenReturn("Legal Name");
    when(businessProfileResponseBO.getWebsite()).thenReturn("Website");
    when(businessProfileResponseBO.getId()).thenReturn(ObjectId.get());
    when(businessProfileService.createBusinessProfile(Mockito.<BusinessProfileCreateRequest>any()))
        .thenReturn(businessProfileResponseBO);
    doThrow(new ResourceOccupiedException("An error occurred", HttpStatus.CONTINUE)).when(locker)
        .releaseLock(Mockito.<String>any());
    when(locker.hasLock(Mockito.<String>any(), anyLong(), Mockito.<TimeUnit>any())).thenReturn(
        true);
    assertThrows(ResourceOccupiedException.class,
        () -> businessProfileRevisionManager.createBusinessProfile(
            new BusinessProfileCreateRequest()));
    verify(businessProfileRevision).createBusinessProfileRevision(
        Mockito.<BusinessProfileRevisionBO>any());
    verify(businessProfileService).createBusinessProfile(
        Mockito.<BusinessProfileCreateRequest>any());
    verify(businessProfileResponseBO).getTaxIdentifier();
    verify(businessProfileResponseBO).getBusinessAddress();
    verify(businessProfileResponseBO).getLegalAddress();
    verify(businessProfileResponseBO).getCompanyName();
    verify(businessProfileResponseBO).getEmail();
    verify(businessProfileResponseBO).getLegalName();
    verify(businessProfileResponseBO).getWebsite();
    verify(businessProfileResponseBO, atLeast(1)).getId();
    verify(locker).hasLock(Mockito.<String>any(), anyLong(), Mockito.<TimeUnit>any());
    verify(locker).releaseLock(Mockito.<String>any());
  }

  /**
   * Method under test:
   * {@link BusinessProfileRevisionManagerImpl#updateBusinessProfileRevisionForProduct(String,
   * String, BusinessProfileUpdateRequest)}
   */
  @Test
  void testUpdateBusinessProfileRevisionForProduct2() {
    when(businessProfileService.fetchBusinessProfile(Mockito.<String>any())).thenReturn(null);
    when(
        profileMappingService.getLatestProfileMapping(Mockito.<String>any(), Mockito.<String>any()))
        .thenThrow(new ResourceOccupiedException("An error occurred", HttpStatus.CONTINUE));
    when(locker.hasLock(Mockito.<String>any(), anyLong(), Mockito.<TimeUnit>any())).thenReturn(
        true);
    assertThrows(ResourceOccupiedException.class, () -> businessProfileRevisionManager
        .updateBusinessProfileRevisionForProduct("42", "42", new BusinessProfileUpdateRequest()));
    verify(businessProfileService).fetchBusinessProfile(Mockito.<String>any());
    verify(profileMappingService).getLatestProfileMapping(Mockito.<String>any(),
        Mockito.<String>any());
    verify(locker).hasLock(Mockito.<String>any(), anyLong(), Mockito.<TimeUnit>any());
  }

  /**
   * Method under test:
   * {@link BusinessProfileRevisionManagerImpl#updateBusinessProfileRevisionForProduct(String,
   * String, BusinessProfileUpdateRequest)}
   */
  @Test
  void testUpdateBusinessProfileRevisionForProduct3() {
    when(locker.hasLock(Mockito.<String>any(), anyLong(), Mockito.<TimeUnit>any())).thenReturn(
        false);
    assertThrows(ResourceOccupiedException.class, () -> businessProfileRevisionManager
        .updateBusinessProfileRevisionForProduct("42", "42", new BusinessProfileUpdateRequest()));
    verify(locker).hasLock(Mockito.<String>any(), anyLong(), Mockito.<TimeUnit>any());
  }

  /**
   * Method under test:
   * {@link BusinessProfileRevisionManagerImpl#updateBusinessProfileRevisionForProduct(String,
   * String, BusinessProfileUpdateRequest)}
   */
  @Test
  void testUpdateBusinessProfileRevisionForProduct7() {
    when(businessProfileRevision.getBusinessProfileRevision(Mockito.<String>any(),
        Mockito.<Integer>any()))
        .thenReturn(new BusinessProfileRevisionBO());
    when(businessProfileService.fetchBusinessProfile(Mockito.<String>any())).thenReturn(null);
    ProfileMappingBO profileMappingBO = mock(ProfileMappingBO.class);
    when(profileMappingBO.getRevision()).thenReturn(1);
    when(
        profileMappingService.getLatestProfileMapping(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(profileMappingBO);
    when(locker.hasLock(Mockito.<String>any(), anyLong(), Mockito.<TimeUnit>any())).thenReturn(
        true);

    BusinessAddress businessAddress = new BusinessAddress();
    businessAddress.setCity("Oxford");
    businessAddress.setCountry("GB");
    businessAddress.setLine1("Line1");
    businessAddress.setLine2("Line2");
    businessAddress.setPinCode("Pin Code");
    businessAddress.setState("MD");

    LegalAddress legalAddress = new LegalAddress();
    legalAddress.setCity("Oxford");
    legalAddress.setCountry("GB");
    legalAddress.setLine1("Line1");
    legalAddress.setLine2("Line2");
    legalAddress.setPinCode("Pin Code");
    legalAddress.setState("MD");
    BusinessProfileUpdateRequest request = mock(BusinessProfileUpdateRequest.class);
    when(request.getEmail()).thenThrow(
        new ResourceOccupiedException("An error occurred", HttpStatus.CONTINUE));
    when(request.getLegalAddress()).thenReturn(legalAddress);
    when(request.getBusinessAddress()).thenReturn(businessAddress);
    when(request.getWebsite()).thenReturn("Website");
    when(request.getCompanyName()).thenReturn("Company Name");
    when(request.getTaxIdentifier()).thenReturn(new TaxIdentifier(TaxIdentifierType.PAN, "42"));
    when(request.getLegalName()).thenReturn("Legal Name");
    assertThrows(ResourceOccupiedException.class,
        () -> businessProfileRevisionManager.updateBusinessProfileRevisionForProduct("42", "42",
            request));
    verify(businessProfileRevision).getBusinessProfileRevision(Mockito.<String>any(),
        Mockito.<Integer>any());
    verify(businessProfileService).fetchBusinessProfile(Mockito.<String>any());
    verify(profileMappingService).getLatestProfileMapping(Mockito.<String>any(),
        Mockito.<String>any());
    verify(profileMappingBO).getRevision();
    verify(locker).hasLock(Mockito.<String>any(), anyLong(), Mockito.<TimeUnit>any());
    verify(request, atLeast(1)).getTaxIdentifier();
    verify(request, atLeast(1)).getBusinessAddress();
    verify(request, atLeast(1)).getLegalAddress();
    verify(request, atLeast(1)).getCompanyName();
    verify(request).getEmail();
    verify(request, atLeast(1)).getLegalName();
    verify(request, atLeast(1)).getWebsite();
  }

  /**
   * Method under test:
   * {@link BusinessProfileRevisionManagerImpl#updateBusinessProfile(BusinessProfileRevisionBO)}
   */
  @Test
  void testUpdateBusinessProfile() {
    doNothing().when(businessProfileService)
        .updateBusinessProfileForRevision(Mockito.<BusinessProfileRevisionBO>any());
    businessProfileRevisionManager.updateBusinessProfile(new BusinessProfileRevisionBO());
    verify(businessProfileService).updateBusinessProfileForRevision(
        Mockito.<BusinessProfileRevisionBO>any());
  }

  /**
   * Method under test:
   * {@link BusinessProfileRevisionManagerImpl#updateBusinessProfile(BusinessProfileRevisionBO)}
   */
  @Test
  void testUpdateBusinessProfile2() {
    doThrow(new ResourceOccupiedException("An error occurred", HttpStatus.CONTINUE)).when(
            businessProfileService)
        .updateBusinessProfileForRevision(Mockito.<BusinessProfileRevisionBO>any());
    assertThrows(ResourceOccupiedException.class,
        () -> businessProfileRevisionManager.updateBusinessProfile(
            new BusinessProfileRevisionBO()));
    verify(businessProfileService).updateBusinessProfileForRevision(
        Mockito.<BusinessProfileRevisionBO>any());
  }

  /**
   * Method under test:
   * {@link BusinessProfileRevisionManagerImpl#fetchLatestBusinessProfileForProduct(String,
   * String)}
   */
  @Test
  void testFetchLatestBusinessProfileForProduct2() {
    when(profileMappingService.getLatestValidMappingForBusinessAndProduct(Mockito.<String>any(),
        Mockito.<String>any()))
        .thenThrow(new ResourceOccupiedException("An error occurred", HttpStatus.CONTINUE));
    assertThrows(ResourceOccupiedException.class,
        () -> businessProfileRevisionManager.fetchLatestBusinessProfileForProduct("42", "42"));
    verify(profileMappingService).getLatestValidMappingForBusinessAndProduct(Mockito.<String>any(),
        Mockito.<String>any());
  }

  /**
   * Method under test:
   * {@link BusinessProfileRevisionManagerImpl#fetchLatestBusinessProfileForProduct(String,
   * String)}
   */
  @Test
  void testFetchLatestBusinessProfileForProduct3() {
    BusinessProfileRevisionBO businessProfileRevisionBO = new BusinessProfileRevisionBO();
    when(businessProfileRevision.getBusinessProfileRevision(Mockito.<String>any(),
        Mockito.<Integer>any()))
        .thenReturn(businessProfileRevisionBO);
    ProfileMappingBO profileMappingBO = mock(ProfileMappingBO.class);
    when(profileMappingBO.getRevision()).thenReturn(1);
    when(profileMappingService.getLatestValidMappingForBusinessAndProduct(Mockito.<String>any(),
        Mockito.<String>any()))
        .thenReturn(profileMappingBO);
    assertSame(businessProfileRevisionBO,
        businessProfileRevisionManager.fetchLatestBusinessProfileForProduct("42", "42"));
    verify(businessProfileRevision).getBusinessProfileRevision(Mockito.<String>any(),
        Mockito.<Integer>any());
    verify(profileMappingService).getLatestValidMappingForBusinessAndProduct(Mockito.<String>any(),
        Mockito.<String>any());
    verify(profileMappingBO, atLeast(1)).getRevision();
  }

  /**
   * Method under test:
   * {@link BusinessProfileRevisionManagerImpl#fetchBusinessProfileRevision(String, Integer)}
   */
  @Test
  void testFetchBusinessProfileRevision() {
    BusinessProfileRevisionBO businessProfileRevisionBO = new BusinessProfileRevisionBO();
    when(businessProfileRevision.getBusinessProfileRevision(Mockito.<String>any(),
        Mockito.<Integer>any()))
        .thenReturn(businessProfileRevisionBO);
    assertSame(businessProfileRevisionBO,
        businessProfileRevisionManager.fetchBusinessProfileRevision("42", 1));
    verify(businessProfileRevision).getBusinessProfileRevision(Mockito.<String>any(),
        Mockito.<Integer>any());
  }

  /**
   * Method under test:
   * {@link BusinessProfileRevisionManagerImpl#fetchBusinessProfileRevision(String, Integer)}
   */
  @Test
  void testFetchBusinessProfileRevision2() {
    when(businessProfileRevision.getBusinessProfileRevision(Mockito.<String>any(),
        Mockito.<Integer>any()))
        .thenThrow(new ResourceOccupiedException("An error occurred", HttpStatus.CONTINUE));
    assertThrows(ResourceOccupiedException.class,
        () -> businessProfileRevisionManager.fetchBusinessProfileRevision("42", 1));
    verify(businessProfileRevision).getBusinessProfileRevision(Mockito.<String>any(),
        Mockito.<Integer>any());
  }
}

