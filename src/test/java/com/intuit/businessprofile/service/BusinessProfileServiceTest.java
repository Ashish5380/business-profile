package com.intuit.businessprofile.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.intuit.businessprofile.bo.BusinessProfileResponseBO;
import com.intuit.businessprofile.bo.BusinessProfileRevisionBO;
import com.intuit.businessprofile.dto.request.BusinessProfileCreateRequest;
import com.intuit.businessprofile.entity.BusinessProfileEntity;
import com.intuit.businessprofile.enums.TaxIdentifierType;
import com.intuit.businessprofile.exception.InvalidBusinessProfileException;
import com.intuit.businessprofile.model.TaxIdentifier;
import com.intuit.businessprofile.model.address.BusinessAddress;
import com.intuit.businessprofile.model.address.LegalAddress;
import com.intuit.businessprofile.repository.BusinessProfileDataAccess;
import com.intuit.businessprofile.service.BusinessProfileService;
import com.intuit.businessprofile.service.impl.BusinessProfileServiceImpl;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {BusinessProfileServiceImpl.class})
public class BusinessProfileServiceTest {

  @MockBean
  private BusinessProfileDataAccess businessProfileDataAccess;

  @Autowired
  private BusinessProfileService businessProfileService;

  /**
   * Method under test: {@link BusinessProfileServiceImpl#fetchBusinessProfile(String)}
   */
  @Test
  void testFetchBusinessProfile() {
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

    BusinessProfileEntity businessProfileEntity = new BusinessProfileEntity();
    businessProfileEntity.setBusinessAddress(businessAddress);
    businessProfileEntity.setCompanyName("Company Name");
    businessProfileEntity.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    businessProfileEntity.setEmail("jane.doe@example.org");
    ObjectId id = ObjectId.get();
    businessProfileEntity.setId(id);
    businessProfileEntity.setLatestValidRevision(1);
    businessProfileEntity.setLegalAddress(legalAddress);
    businessProfileEntity.setLegalName("Legal Name");
    TaxIdentifier taxIdentifier = new TaxIdentifier(TaxIdentifierType.PAN, "42");

    businessProfileEntity.setTaxIdentifier(taxIdentifier);
    businessProfileEntity.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    businessProfileEntity.setWebsite("Website");
    Optional<BusinessProfileEntity> ofResult = Optional.of(businessProfileEntity);
    when(businessProfileDataAccess.findById(Mockito.<String>any())).thenReturn(ofResult);
    BusinessProfileResponseBO actualFetchBusinessProfileResult = businessProfileService.fetchBusinessProfile(
        "42");
    assertSame(businessAddress, actualFetchBusinessProfileResult.getBusinessAddress());
    assertEquals("Website", actualFetchBusinessProfileResult.getWebsite());
    assertEquals("Company Name", actualFetchBusinessProfileResult.getCompanyName());
    assertEquals("jane.doe@example.org", actualFetchBusinessProfileResult.getEmail());
    assertEquals("Legal Name", actualFetchBusinessProfileResult.getLegalName());
    assertSame(taxIdentifier, actualFetchBusinessProfileResult.getTaxIdentifier());
    assertSame(id, actualFetchBusinessProfileResult.getId());
    assertNull(actualFetchBusinessProfileResult.getUpdatedAt());
    assertNull(actualFetchBusinessProfileResult.getCreatedAt());
    assertEquals(1, actualFetchBusinessProfileResult.getLatestValidRevision().intValue());
    assertSame(legalAddress, actualFetchBusinessProfileResult.getLegalAddress());
    verify(businessProfileDataAccess).findById(Mockito.<String>any());
  }

  /**
   * Method under test: {@link BusinessProfileServiceImpl#fetchBusinessProfile(String)}
   */
  @Test
  void testFetchBusinessProfile2() {
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

    BusinessAddress businessAddress2 = new BusinessAddress();
    businessAddress2.setCity("Oxford");
    businessAddress2.setCountry("GB");
    businessAddress2.setLine1("Line1");
    businessAddress2.setLine2("Line2");
    businessAddress2.setPinCode("Pin Code");
    businessAddress2.setState("MD");

    LegalAddress legalAddress2 = new LegalAddress();
    legalAddress2.setCity("Oxford");
    legalAddress2.setCountry("GB");
    legalAddress2.setLine1("Line1");
    legalAddress2.setLine2("Line2");
    legalAddress2.setPinCode("Pin Code");
    legalAddress2.setState("MD");
    BusinessProfileEntity businessProfileEntity = mock(BusinessProfileEntity.class);
    TaxIdentifier taxIdentifier = new TaxIdentifier(TaxIdentifierType.PAN, "42");

    when(businessProfileEntity.getTaxIdentifier()).thenReturn(taxIdentifier);
    when(businessProfileEntity.getBusinessAddress()).thenReturn(businessAddress2);
    when(businessProfileEntity.getLegalAddress()).thenReturn(legalAddress2);
    when(businessProfileEntity.getLatestValidRevision()).thenReturn(1);
    when(businessProfileEntity.getCompanyName()).thenReturn("Company Name");
    when(businessProfileEntity.getEmail()).thenReturn("jane.doe@example.org");
    when(businessProfileEntity.getLegalName()).thenReturn("Legal Name");
    when(businessProfileEntity.getWebsite()).thenReturn("Website");
    ObjectId getResult = ObjectId.get();
    when(businessProfileEntity.getId()).thenReturn(getResult);
    doNothing().when(businessProfileEntity).setCreatedAt(Mockito.<LocalDateTime>any());
    doNothing().when(businessProfileEntity).setId(Mockito.<ObjectId>any());
    doNothing().when(businessProfileEntity).setUpdatedAt(Mockito.<LocalDateTime>any());
    doNothing().when(businessProfileEntity).setLatestValidRevision(Mockito.<Integer>any());
    doNothing().when(businessProfileEntity).setBusinessAddress(Mockito.<BusinessAddress>any());
    doNothing().when(businessProfileEntity).setCompanyName(Mockito.<String>any());
    doNothing().when(businessProfileEntity).setEmail(Mockito.<String>any());
    doNothing().when(businessProfileEntity).setLegalAddress(Mockito.<LegalAddress>any());
    doNothing().when(businessProfileEntity).setLegalName(Mockito.<String>any());
    doNothing().when(businessProfileEntity).setTaxIdentifier(Mockito.<TaxIdentifier>any());
    doNothing().when(businessProfileEntity).setWebsite(Mockito.<String>any());
    businessProfileEntity.setBusinessAddress(businessAddress);
    businessProfileEntity.setCompanyName("Company Name");
    businessProfileEntity.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    businessProfileEntity.setEmail("jane.doe@example.org");
    businessProfileEntity.setId(ObjectId.get());
    businessProfileEntity.setLatestValidRevision(1);
    businessProfileEntity.setLegalAddress(legalAddress);
    businessProfileEntity.setLegalName("Legal Name");
    businessProfileEntity.setTaxIdentifier(new TaxIdentifier(TaxIdentifierType.PAN, "42"));
    businessProfileEntity.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    businessProfileEntity.setWebsite("Website");
    Optional<BusinessProfileEntity> ofResult = Optional.of(businessProfileEntity);
    when(businessProfileDataAccess.findById(Mockito.<String>any())).thenReturn(ofResult);
    BusinessProfileResponseBO actualFetchBusinessProfileResult = businessProfileService.fetchBusinessProfile(
        "42");
    assertSame(businessAddress2, actualFetchBusinessProfileResult.getBusinessAddress());
    assertEquals("Website", actualFetchBusinessProfileResult.getWebsite());
    assertEquals("Company Name", actualFetchBusinessProfileResult.getCompanyName());
    assertEquals("jane.doe@example.org", actualFetchBusinessProfileResult.getEmail());
    assertEquals("Legal Name", actualFetchBusinessProfileResult.getLegalName());
    assertSame(taxIdentifier, actualFetchBusinessProfileResult.getTaxIdentifier());
    assertSame(getResult, actualFetchBusinessProfileResult.getId());
    assertNull(actualFetchBusinessProfileResult.getUpdatedAt());
    assertNull(actualFetchBusinessProfileResult.getCreatedAt());
    assertEquals(1, actualFetchBusinessProfileResult.getLatestValidRevision().intValue());
    assertSame(legalAddress2, actualFetchBusinessProfileResult.getLegalAddress());
    verify(businessProfileDataAccess).findById(Mockito.<String>any());
    verify(businessProfileEntity).getTaxIdentifier();
    verify(businessProfileEntity).getBusinessAddress();
    verify(businessProfileEntity).getLegalAddress();
    verify(businessProfileEntity).getLatestValidRevision();
    verify(businessProfileEntity).getCompanyName();
    verify(businessProfileEntity).getEmail();
    verify(businessProfileEntity).getLegalName();
    verify(businessProfileEntity).getWebsite();
    verify(businessProfileEntity).getId();
    verify(businessProfileEntity).setCreatedAt(Mockito.<LocalDateTime>any());
    verify(businessProfileEntity).setId(Mockito.<ObjectId>any());
    verify(businessProfileEntity).setUpdatedAt(Mockito.<LocalDateTime>any());
    verify(businessProfileEntity).setLatestValidRevision(Mockito.<Integer>any());
    verify(businessProfileEntity).setBusinessAddress(Mockito.<BusinessAddress>any());
    verify(businessProfileEntity).setCompanyName(Mockito.<String>any());
    verify(businessProfileEntity).setEmail(Mockito.<String>any());
    verify(businessProfileEntity).setLegalAddress(Mockito.<LegalAddress>any());
    verify(businessProfileEntity).setLegalName(Mockito.<String>any());
    verify(businessProfileEntity).setTaxIdentifier(Mockito.<TaxIdentifier>any());
    verify(businessProfileEntity).setWebsite(Mockito.<String>any());
  }

  /**
   * Method under test:
   * {@link BusinessProfileServiceImpl#createBusinessProfile(BusinessProfileCreateRequest)}
   */
  @Test
  void testCreateBusinessProfile() {
    when(businessProfileDataAccess.getBusinessProfileByEmail(Mockito.<String>any())).thenReturn(
        new ArrayList<>());
    assertThrows(InvalidBusinessProfileException.class,
        () -> businessProfileService.createBusinessProfile(new BusinessProfileCreateRequest()));
    verify(businessProfileDataAccess).getBusinessProfileByEmail(Mockito.<String>any());
  }

  /**
   * Method under test:
   * {@link BusinessProfileServiceImpl#createBusinessProfile(BusinessProfileCreateRequest)}
   */
  @Test
  void testCreateBusinessProfile2() {
    BusinessAddress businessAddress = new BusinessAddress();
    businessAddress.setCity("Oxford");
    businessAddress.setCountry("GB");
    businessAddress.setLine1("Error creating business-profile as it already exist :: {}");
    businessAddress.setLine2("Error creating business-profile as it already exist :: {}");
    businessAddress.setPinCode("Error creating business-profile as it already exist :: {}");
    businessAddress.setState("MD");

    LegalAddress legalAddress = new LegalAddress();
    legalAddress.setCity("Oxford");
    legalAddress.setCountry("GB");
    legalAddress.setLine1("Error creating business-profile as it already exist :: {}");
    legalAddress.setLine2("Error creating business-profile as it already exist :: {}");
    legalAddress.setPinCode("Error creating business-profile as it already exist :: {}");
    legalAddress.setState("MD");

    BusinessProfileEntity businessProfileEntity = new BusinessProfileEntity();
    businessProfileEntity.setBusinessAddress(businessAddress);
    businessProfileEntity.setCompanyName(
        "Error creating business-profile as it already exist :: {}");
    businessProfileEntity.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    businessProfileEntity.setEmail("jane.doe@example.org");
    businessProfileEntity.setId(ObjectId.get());
    businessProfileEntity.setLatestValidRevision(1);
    businessProfileEntity.setLegalAddress(legalAddress);
    businessProfileEntity.setLegalName("Error creating business-profile as it already exist :: {}");
    businessProfileEntity.setTaxIdentifier(new TaxIdentifier(TaxIdentifierType.PAN, "42"));
    businessProfileEntity.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    businessProfileEntity.setWebsite("Error creating business-profile as it already exist :: {}");

    ArrayList<BusinessProfileEntity> businessProfileEntityList = new ArrayList<>();
    businessProfileEntityList.add(businessProfileEntity);

    BusinessAddress businessAddress2 = new BusinessAddress();
    businessAddress2.setCity("Oxford");
    businessAddress2.setCountry("GB");
    businessAddress2.setLine1("Line1");
    businessAddress2.setLine2("Line2");
    businessAddress2.setPinCode("Pin Code");
    businessAddress2.setState("MD");

    LegalAddress legalAddress2 = new LegalAddress();
    legalAddress2.setCity("Oxford");
    legalAddress2.setCountry("GB");
    legalAddress2.setLine1("Line1");
    legalAddress2.setLine2("Line2");
    legalAddress2.setPinCode("Pin Code");
    legalAddress2.setState("MD");

    BusinessProfileEntity businessProfileEntity2 = new BusinessProfileEntity();
    businessProfileEntity2.setBusinessAddress(businessAddress2);
    businessProfileEntity2.setCompanyName("Company Name");
    businessProfileEntity2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    businessProfileEntity2.setEmail("jane.doe@example.org");
    ObjectId id = ObjectId.get();
    businessProfileEntity2.setId(id);
    businessProfileEntity2.setLatestValidRevision(1);
    businessProfileEntity2.setLegalAddress(legalAddress2);
    businessProfileEntity2.setLegalName("Legal Name");
    TaxIdentifier taxIdentifier = new TaxIdentifier(TaxIdentifierType.PAN, "42");

    businessProfileEntity2.setTaxIdentifier(taxIdentifier);
    businessProfileEntity2.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    businessProfileEntity2.setWebsite("Website");
    when(businessProfileDataAccess.save(Mockito.<BusinessProfileEntity>any())).thenReturn(
        businessProfileEntity2);
    when(businessProfileDataAccess.getBusinessProfileByEmail(Mockito.<String>any()))
        .thenReturn(businessProfileEntityList);
    BusinessProfileResponseBO actualCreateBusinessProfileResult = businessProfileService
        .createBusinessProfile(new BusinessProfileCreateRequest());
    assertSame(businessAddress2, actualCreateBusinessProfileResult.getBusinessAddress());
    assertEquals("Website", actualCreateBusinessProfileResult.getWebsite());
    assertEquals("Company Name", actualCreateBusinessProfileResult.getCompanyName());
    assertEquals("jane.doe@example.org", actualCreateBusinessProfileResult.getEmail());
    assertEquals("Legal Name", actualCreateBusinessProfileResult.getLegalName());
    assertSame(taxIdentifier, actualCreateBusinessProfileResult.getTaxIdentifier());
    assertSame(id, actualCreateBusinessProfileResult.getId());
    assertNull(actualCreateBusinessProfileResult.getUpdatedAt());
    assertNull(actualCreateBusinessProfileResult.getCreatedAt());
    assertEquals(1, actualCreateBusinessProfileResult.getLatestValidRevision().intValue());
    assertSame(legalAddress2, actualCreateBusinessProfileResult.getLegalAddress());
    verify(businessProfileDataAccess).save(Mockito.<BusinessProfileEntity>any());
    verify(businessProfileDataAccess).getBusinessProfileByEmail(Mockito.<String>any());
  }

  /**
   * Method under test:
   * {@link BusinessProfileServiceImpl#createBusinessProfile(BusinessProfileCreateRequest)}
   */
  @Test
  void testCreateBusinessProfile3() {
    BusinessAddress businessAddress = new BusinessAddress();
    businessAddress.setCity("Oxford");
    businessAddress.setCountry("GB");
    businessAddress.setLine1("Error creating business-profile as it already exist :: {}");
    businessAddress.setLine2("Error creating business-profile as it already exist :: {}");
    businessAddress.setPinCode("Error creating business-profile as it already exist :: {}");
    businessAddress.setState("MD");

    LegalAddress legalAddress = new LegalAddress();
    legalAddress.setCity("Oxford");
    legalAddress.setCountry("GB");
    legalAddress.setLine1("Error creating business-profile as it already exist :: {}");
    legalAddress.setLine2("Error creating business-profile as it already exist :: {}");
    legalAddress.setPinCode("Error creating business-profile as it already exist :: {}");
    legalAddress.setState("MD");

    BusinessProfileEntity businessProfileEntity = new BusinessProfileEntity();
    businessProfileEntity.setBusinessAddress(businessAddress);
    businessProfileEntity.setCompanyName(
        "Error creating business-profile as it already exist :: {}");
    businessProfileEntity.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    businessProfileEntity.setEmail("jane.doe@example.org");
    businessProfileEntity.setId(ObjectId.get());
    businessProfileEntity.setLatestValidRevision(1);
    businessProfileEntity.setLegalAddress(legalAddress);
    businessProfileEntity.setLegalName("Error creating business-profile as it already exist :: {}");
    businessProfileEntity.setTaxIdentifier(new TaxIdentifier(TaxIdentifierType.PAN, "42"));
    businessProfileEntity.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    businessProfileEntity.setWebsite("Error creating business-profile as it already exist :: {}");

    ArrayList<BusinessProfileEntity> businessProfileEntityList = new ArrayList<>();
    businessProfileEntityList.add(businessProfileEntity);

    BusinessAddress businessAddress2 = new BusinessAddress();
    businessAddress2.setCity("Oxford");
    businessAddress2.setCountry("GB");
    businessAddress2.setLine1("Line1");
    businessAddress2.setLine2("Line2");
    businessAddress2.setPinCode("Pin Code");
    businessAddress2.setState("MD");

    LegalAddress legalAddress2 = new LegalAddress();
    legalAddress2.setCity("Oxford");
    legalAddress2.setCountry("GB");
    legalAddress2.setLine1("Line1");
    legalAddress2.setLine2("Line2");
    legalAddress2.setPinCode("Pin Code");
    legalAddress2.setState("MD");

    BusinessAddress businessAddress3 = new BusinessAddress();
    businessAddress3.setCity("Oxford");
    businessAddress3.setCountry("GB");
    businessAddress3.setLine1("Line1");
    businessAddress3.setLine2("Line2");
    businessAddress3.setPinCode("Pin Code");
    businessAddress3.setState("MD");

    LegalAddress legalAddress3 = new LegalAddress();
    legalAddress3.setCity("Oxford");
    legalAddress3.setCountry("GB");
    legalAddress3.setLine1("Line1");
    legalAddress3.setLine2("Line2");
    legalAddress3.setPinCode("Pin Code");
    legalAddress3.setState("MD");
    BusinessProfileEntity businessProfileEntity2 = mock(BusinessProfileEntity.class);
    TaxIdentifier taxIdentifier = new TaxIdentifier(TaxIdentifierType.PAN, "42");

    when(businessProfileEntity2.getTaxIdentifier()).thenReturn(taxIdentifier);
    when(businessProfileEntity2.getBusinessAddress()).thenReturn(businessAddress3);
    when(businessProfileEntity2.getLegalAddress()).thenReturn(legalAddress3);
    when(businessProfileEntity2.getLatestValidRevision()).thenReturn(1);
    when(businessProfileEntity2.getCompanyName()).thenReturn("Company Name");
    when(businessProfileEntity2.getEmail()).thenReturn("jane.doe@example.org");
    when(businessProfileEntity2.getLegalName()).thenReturn("Legal Name");
    when(businessProfileEntity2.getWebsite()).thenReturn("Website");
    ObjectId getResult = ObjectId.get();
    when(businessProfileEntity2.getId()).thenReturn(getResult);
    doNothing().when(businessProfileEntity2).setCreatedAt(Mockito.<LocalDateTime>any());
    doNothing().when(businessProfileEntity2).setId(Mockito.<ObjectId>any());
    doNothing().when(businessProfileEntity2).setUpdatedAt(Mockito.<LocalDateTime>any());
    doNothing().when(businessProfileEntity2).setLatestValidRevision(Mockito.<Integer>any());
    doNothing().when(businessProfileEntity2).setBusinessAddress(Mockito.<BusinessAddress>any());
    doNothing().when(businessProfileEntity2).setCompanyName(Mockito.<String>any());
    doNothing().when(businessProfileEntity2).setEmail(Mockito.<String>any());
    doNothing().when(businessProfileEntity2).setLegalAddress(Mockito.<LegalAddress>any());
    doNothing().when(businessProfileEntity2).setLegalName(Mockito.<String>any());
    doNothing().when(businessProfileEntity2).setTaxIdentifier(Mockito.<TaxIdentifier>any());
    doNothing().when(businessProfileEntity2).setWebsite(Mockito.<String>any());
    businessProfileEntity2.setBusinessAddress(businessAddress2);
    businessProfileEntity2.setCompanyName("Company Name");
    businessProfileEntity2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    businessProfileEntity2.setEmail("jane.doe@example.org");
    businessProfileEntity2.setId(ObjectId.get());
    businessProfileEntity2.setLatestValidRevision(1);
    businessProfileEntity2.setLegalAddress(legalAddress2);
    businessProfileEntity2.setLegalName("Legal Name");
    businessProfileEntity2.setTaxIdentifier(new TaxIdentifier(TaxIdentifierType.PAN, "42"));
    businessProfileEntity2.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    businessProfileEntity2.setWebsite("Website");
    when(businessProfileDataAccess.save(Mockito.<BusinessProfileEntity>any())).thenReturn(
        businessProfileEntity2);
    when(businessProfileDataAccess.getBusinessProfileByEmail(Mockito.<String>any()))
        .thenReturn(businessProfileEntityList);
    BusinessProfileResponseBO actualCreateBusinessProfileResult = businessProfileService
        .createBusinessProfile(new BusinessProfileCreateRequest());
    assertSame(businessAddress3, actualCreateBusinessProfileResult.getBusinessAddress());
    assertEquals("Website", actualCreateBusinessProfileResult.getWebsite());
    assertEquals("Company Name", actualCreateBusinessProfileResult.getCompanyName());
    assertEquals("jane.doe@example.org", actualCreateBusinessProfileResult.getEmail());
    assertEquals("Legal Name", actualCreateBusinessProfileResult.getLegalName());
    assertSame(taxIdentifier, actualCreateBusinessProfileResult.getTaxIdentifier());
    assertSame(getResult, actualCreateBusinessProfileResult.getId());
    assertNull(actualCreateBusinessProfileResult.getUpdatedAt());
    assertNull(actualCreateBusinessProfileResult.getCreatedAt());
    assertEquals(1, actualCreateBusinessProfileResult.getLatestValidRevision().intValue());
    assertSame(legalAddress3, actualCreateBusinessProfileResult.getLegalAddress());
    verify(businessProfileDataAccess).save(Mockito.<BusinessProfileEntity>any());
    verify(businessProfileDataAccess).getBusinessProfileByEmail(Mockito.<String>any());
    verify(businessProfileEntity2).getTaxIdentifier();
    verify(businessProfileEntity2).getBusinessAddress();
    verify(businessProfileEntity2).getLegalAddress();
    verify(businessProfileEntity2).getLatestValidRevision();
    verify(businessProfileEntity2).getCompanyName();
    verify(businessProfileEntity2).getEmail();
    verify(businessProfileEntity2).getLegalName();
    verify(businessProfileEntity2).getWebsite();
    verify(businessProfileEntity2).getId();
    verify(businessProfileEntity2).setCreatedAt(Mockito.<LocalDateTime>any());
    verify(businessProfileEntity2).setId(Mockito.<ObjectId>any());
    verify(businessProfileEntity2).setUpdatedAt(Mockito.<LocalDateTime>any());
    verify(businessProfileEntity2).setLatestValidRevision(Mockito.<Integer>any());
    verify(businessProfileEntity2).setBusinessAddress(Mockito.<BusinessAddress>any());
    verify(businessProfileEntity2).setCompanyName(Mockito.<String>any());
    verify(businessProfileEntity2).setEmail(Mockito.<String>any());
    verify(businessProfileEntity2).setLegalAddress(Mockito.<LegalAddress>any());
    verify(businessProfileEntity2).setLegalName(Mockito.<String>any());
    verify(businessProfileEntity2).setTaxIdentifier(Mockito.<TaxIdentifier>any());
    verify(businessProfileEntity2).setWebsite(Mockito.<String>any());
  }

  /**
   * Method under test: {@link BusinessProfileServiceImpl#getAllBusinessProfile()}
   */
  @Test
  void testGetAllBusinessProfile() {
    when(businessProfileDataAccess.findAll()).thenReturn(new ArrayList<>());
    assertTrue(businessProfileService.getAllBusinessProfile().isEmpty());
    verify(businessProfileDataAccess).findAll();
  }

  /**
   * Method under test: {@link BusinessProfileServiceImpl#getAllBusinessProfile()}
   */
  @Test
  void testGetAllBusinessProfile2() {
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

    BusinessProfileEntity businessProfileEntity = new BusinessProfileEntity();
    businessProfileEntity.setBusinessAddress(businessAddress);
    businessProfileEntity.setCompanyName("Company Name");
    businessProfileEntity.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    businessProfileEntity.setEmail("jane.doe@example.org");
    businessProfileEntity.setId(ObjectId.get());
    businessProfileEntity.setLatestValidRevision(1);
    businessProfileEntity.setLegalAddress(legalAddress);
    businessProfileEntity.setLegalName("Legal Name");
    businessProfileEntity.setTaxIdentifier(new TaxIdentifier(TaxIdentifierType.PAN, "42"));
    businessProfileEntity.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    businessProfileEntity.setWebsite("Website");

    ArrayList<BusinessProfileEntity> businessProfileEntityList = new ArrayList<>();
    businessProfileEntityList.add(businessProfileEntity);
    when(businessProfileDataAccess.findAll()).thenReturn(businessProfileEntityList);
    assertEquals(1, businessProfileService.getAllBusinessProfile().size());
    verify(businessProfileDataAccess).findAll();
  }

  /**
   * Method under test: {@link BusinessProfileServiceImpl#getAllBusinessProfile()}
   */
  @Test
  void testGetAllBusinessProfile3() {
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

    BusinessProfileEntity businessProfileEntity = new BusinessProfileEntity();
    businessProfileEntity.setBusinessAddress(businessAddress);
    businessProfileEntity.setCompanyName("Company Name");
    businessProfileEntity.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    businessProfileEntity.setEmail("jane.doe@example.org");
    businessProfileEntity.setId(ObjectId.get());
    businessProfileEntity.setLatestValidRevision(1);
    businessProfileEntity.setLegalAddress(legalAddress);
    businessProfileEntity.setLegalName("Legal Name");
    businessProfileEntity.setTaxIdentifier(new TaxIdentifier(TaxIdentifierType.PAN, "42"));
    businessProfileEntity.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    businessProfileEntity.setWebsite("Website");

    BusinessAddress businessAddress2 = new BusinessAddress();
    businessAddress2.setCity("London");
    businessAddress2.setCountry("GBR");
    businessAddress2.setLine1("42");
    businessAddress2.setLine2("42");
    businessAddress2.setPinCode("42");
    businessAddress2.setState("State");

    LegalAddress legalAddress2 = new LegalAddress();
    legalAddress2.setCity("London");
    legalAddress2.setCountry("GBR");
    legalAddress2.setLine1("42");
    legalAddress2.setLine2("42");
    legalAddress2.setPinCode("42");
    legalAddress2.setState("State");

    BusinessProfileEntity businessProfileEntity2 = new BusinessProfileEntity();
    businessProfileEntity2.setBusinessAddress(businessAddress2);
    businessProfileEntity2.setCompanyName("42");
    businessProfileEntity2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    businessProfileEntity2.setEmail("john.smith@example.org");
    businessProfileEntity2.setId(ObjectId.get());
    businessProfileEntity2.setLatestValidRevision(2);
    businessProfileEntity2.setLegalAddress(legalAddress2);
    businessProfileEntity2.setLegalName("42");
    businessProfileEntity2.setTaxIdentifier(new TaxIdentifier(TaxIdentifierType.PAN, "42"));
    businessProfileEntity2.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    businessProfileEntity2.setWebsite("42");

    ArrayList<BusinessProfileEntity> businessProfileEntityList = new ArrayList<>();
    businessProfileEntityList.add(businessProfileEntity2);
    businessProfileEntityList.add(businessProfileEntity);
    when(businessProfileDataAccess.findAll()).thenReturn(businessProfileEntityList);
    assertEquals(2, businessProfileService.getAllBusinessProfile().size());
    verify(businessProfileDataAccess).findAll();
  }

  /**
   * Method under test: {@link BusinessProfileServiceImpl#getAllBusinessProfile()}
   */
  @Test
  void testGetAllBusinessProfile4() {
    when(businessProfileDataAccess.findAll())
        .thenThrow(new InvalidBusinessProfileException("An error occurred", HttpStatus.CONTINUE));
    assertThrows(InvalidBusinessProfileException.class,
        () -> businessProfileService.getAllBusinessProfile());
    verify(businessProfileDataAccess).findAll();
  }

  /**
   * Method under test: {@link BusinessProfileServiceImpl#getAllBusinessProfile()}
   */
  @Test
  void testGetAllBusinessProfile5() {
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

    BusinessAddress businessAddress2 = new BusinessAddress();
    businessAddress2.setCity("Oxford");
    businessAddress2.setCountry("GB");
    businessAddress2.setLine1("Line1");
    businessAddress2.setLine2("Line2");
    businessAddress2.setPinCode("Pin Code");
    businessAddress2.setState("MD");

    LegalAddress legalAddress2 = new LegalAddress();
    legalAddress2.setCity("Oxford");
    legalAddress2.setCountry("GB");
    legalAddress2.setLine1("Line1");
    legalAddress2.setLine2("Line2");
    legalAddress2.setPinCode("Pin Code");
    legalAddress2.setState("MD");
    BusinessProfileEntity businessProfileEntity = mock(BusinessProfileEntity.class);
    when(businessProfileEntity.getTaxIdentifier()).thenReturn(
        new TaxIdentifier(TaxIdentifierType.PAN, "42"));
    when(businessProfileEntity.getBusinessAddress()).thenReturn(businessAddress2);
    when(businessProfileEntity.getLegalAddress()).thenReturn(legalAddress2);
    when(businessProfileEntity.getLatestValidRevision()).thenReturn(1);
    when(businessProfileEntity.getCompanyName()).thenReturn("Company Name");
    when(businessProfileEntity.getEmail()).thenReturn("jane.doe@example.org");
    when(businessProfileEntity.getLegalName()).thenReturn("Legal Name");
    when(businessProfileEntity.getWebsite()).thenReturn("Website");
    when(businessProfileEntity.getId()).thenReturn(ObjectId.get());
    doNothing().when(businessProfileEntity).setCreatedAt(Mockito.<LocalDateTime>any());
    doNothing().when(businessProfileEntity).setId(Mockito.<ObjectId>any());
    doNothing().when(businessProfileEntity).setUpdatedAt(Mockito.<LocalDateTime>any());
    doNothing().when(businessProfileEntity).setLatestValidRevision(Mockito.<Integer>any());
    doNothing().when(businessProfileEntity).setBusinessAddress(Mockito.<BusinessAddress>any());
    doNothing().when(businessProfileEntity).setCompanyName(Mockito.<String>any());
    doNothing().when(businessProfileEntity).setEmail(Mockito.<String>any());
    doNothing().when(businessProfileEntity).setLegalAddress(Mockito.<LegalAddress>any());
    doNothing().when(businessProfileEntity).setLegalName(Mockito.<String>any());
    doNothing().when(businessProfileEntity).setTaxIdentifier(Mockito.<TaxIdentifier>any());
    doNothing().when(businessProfileEntity).setWebsite(Mockito.<String>any());
    businessProfileEntity.setBusinessAddress(businessAddress);
    businessProfileEntity.setCompanyName("Company Name");
    businessProfileEntity.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    businessProfileEntity.setEmail("jane.doe@example.org");
    businessProfileEntity.setId(ObjectId.get());
    businessProfileEntity.setLatestValidRevision(1);
    businessProfileEntity.setLegalAddress(legalAddress);
    businessProfileEntity.setLegalName("Legal Name");
    businessProfileEntity.setTaxIdentifier(new TaxIdentifier(TaxIdentifierType.PAN, "42"));
    businessProfileEntity.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    businessProfileEntity.setWebsite("Website");

    ArrayList<BusinessProfileEntity> businessProfileEntityList = new ArrayList<>();
    businessProfileEntityList.add(businessProfileEntity);
    when(businessProfileDataAccess.findAll()).thenReturn(businessProfileEntityList);
    assertEquals(1, businessProfileService.getAllBusinessProfile().size());
    verify(businessProfileDataAccess).findAll();
    verify(businessProfileEntity).getTaxIdentifier();
    verify(businessProfileEntity).getBusinessAddress();
    verify(businessProfileEntity).getLegalAddress();
    verify(businessProfileEntity).getLatestValidRevision();
    verify(businessProfileEntity).getCompanyName();
    verify(businessProfileEntity).getEmail();
    verify(businessProfileEntity).getLegalName();
    verify(businessProfileEntity).getWebsite();
    verify(businessProfileEntity).getId();
    verify(businessProfileEntity).setCreatedAt(Mockito.<LocalDateTime>any());
    verify(businessProfileEntity).setId(Mockito.<ObjectId>any());
    verify(businessProfileEntity).setUpdatedAt(Mockito.<LocalDateTime>any());
    verify(businessProfileEntity).setLatestValidRevision(Mockito.<Integer>any());
    verify(businessProfileEntity).setBusinessAddress(Mockito.<BusinessAddress>any());
    verify(businessProfileEntity).setCompanyName(Mockito.<String>any());
    verify(businessProfileEntity).setEmail(Mockito.<String>any());
    verify(businessProfileEntity).setLegalAddress(Mockito.<LegalAddress>any());
    verify(businessProfileEntity).setLegalName(Mockito.<String>any());
    verify(businessProfileEntity).setTaxIdentifier(Mockito.<TaxIdentifier>any());
    verify(businessProfileEntity).setWebsite(Mockito.<String>any());
  }
}

