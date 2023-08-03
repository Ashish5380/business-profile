package com.intuit.businessprofile.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.intuit.businessprofile.bo.BusinessProfileRevisionBO;
import com.intuit.businessprofile.entity.BusinessProfileRevisionEntity;
import com.intuit.businessprofile.enums.TaxIdentifierType;
import com.intuit.businessprofile.exception.InvalidBusinessProfileRevisionException;
import com.intuit.businessprofile.model.TaxIdentifier;
import com.intuit.businessprofile.model.address.BusinessAddress;
import com.intuit.businessprofile.model.address.LegalAddress;
import com.intuit.businessprofile.repository.BusinessProfileRevisionDataAccess;
import com.intuit.businessprofile.service.BusinessProfileRevision;
import com.intuit.businessprofile.service.impl.BusinessProfileRevisionImpl;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
@ContextConfiguration(classes = {BusinessProfileRevisionImpl.class})
public class BusinessProfileRevisionTest {

  @MockBean
  private BusinessProfileRevisionDataAccess businessProfileRevisionDataAccess;

  @Autowired
  private BusinessProfileRevision businessProfileRevision;

  /**
   * Method under test:
   * {@link BusinessProfileRevisionImpl#getBusinessProfileRevision(String, Integer)}
   */
  @Test
  void testGetBusinessProfileRevision() {
    when(businessProfileRevisionDataAccess.getBusinessProfileForRevision(Mockito.<String>any(),
        Mockito.<Integer>any()))
        .thenReturn(new ArrayList<>());
    assertThrows(InvalidBusinessProfileRevisionException.class,
        () -> businessProfileRevision.getBusinessProfileRevision("42", 1));
    verify(businessProfileRevisionDataAccess).getBusinessProfileForRevision(Mockito.<String>any(),
        Mockito.<Integer>any());
  }

  /**
   * Method under test:
   * {@link BusinessProfileRevisionImpl#getBusinessProfileRevision(String, Integer)}
   */
  @Test
  void testGetBusinessProfileRevision2() {
    BusinessAddress businessAddress = new BusinessAddress();
    businessAddress.setCity("Oxford");
    businessAddress.setCountry("GB");
    businessAddress.setLine1(
        "Business-profile revision for businessProfileId :: {}, revision :: {}");
    businessAddress.setLine2(
        "Business-profile revision for businessProfileId :: {}, revision :: {}");
    businessAddress.setPinCode(
        "Business-profile revision for businessProfileId :: {}, revision :: {}");
    businessAddress.setState("MD");

    LegalAddress legalAddress = new LegalAddress();
    legalAddress.setCity("Oxford");
    legalAddress.setCountry("GB");
    legalAddress.setLine1("Business-profile revision for businessProfileId :: {}, revision :: {}");
    legalAddress.setLine2("Business-profile revision for businessProfileId :: {}, revision :: {}");
    legalAddress.setPinCode(
        "Business-profile revision for businessProfileId :: {}, revision :: {}");
    legalAddress.setState("MD");

    BusinessProfileRevisionEntity businessProfileRevisionEntity = new BusinessProfileRevisionEntity();
    businessProfileRevisionEntity.setBusinessAddress(businessAddress);
    businessProfileRevisionEntity.setBusinessProfileId("42");
    businessProfileRevisionEntity
        .setCompanyName("Business-profile revision for businessProfileId :: {}, revision :: {}");
    businessProfileRevisionEntity.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    businessProfileRevisionEntity.setEmail("jane.doe@example.org");
    businessProfileRevisionEntity.setId(ObjectId.get());
    businessProfileRevisionEntity.setLegalAddress(legalAddress);
    businessProfileRevisionEntity.setLegalName(
        "Business-profile revision for businessProfileId :: {}, revision :: {}");
    businessProfileRevisionEntity.setRevision(1);
    TaxIdentifier taxIdentifier = new TaxIdentifier(TaxIdentifierType.PAN, "42");

    businessProfileRevisionEntity.setTaxIdentifier(taxIdentifier);
    businessProfileRevisionEntity.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    businessProfileRevisionEntity.setWebsite(
        "Business-profile revision for businessProfileId :: {}, revision :: {}");

    ArrayList<BusinessProfileRevisionEntity> businessProfileRevisionEntityList = new ArrayList<>();
    businessProfileRevisionEntityList.add(businessProfileRevisionEntity);
    when(businessProfileRevisionDataAccess.getBusinessProfileForRevision(Mockito.<String>any(),
        Mockito.<Integer>any()))
        .thenReturn(businessProfileRevisionEntityList);
    BusinessProfileRevisionBO actualBusinessProfileRevision = businessProfileRevision
        .getBusinessProfileRevision("42", 1);
    assertSame(businessAddress, actualBusinessProfileRevision.getBusinessAddress());
    assertEquals("Business-profile revision for businessProfileId :: {}, revision :: {}",
        actualBusinessProfileRevision.getWebsite());
    assertEquals("42", actualBusinessProfileRevision.getBusinessProfileId());
    assertNull(actualBusinessProfileRevision.getCreatedAt());
    assertEquals("Business-profile revision for businessProfileId :: {}, revision :: {}",
        actualBusinessProfileRevision.getLegalName());
    assertEquals(1, actualBusinessProfileRevision.getRevision().intValue());
    assertEquals("jane.doe@example.org", actualBusinessProfileRevision.getEmail());
    assertSame(taxIdentifier, actualBusinessProfileRevision.getTaxIdentifier());
    assertNull(actualBusinessProfileRevision.getUpdatedAt());
    assertEquals("Business-profile revision for businessProfileId :: {}, revision :: {}",
        actualBusinessProfileRevision.getCompanyName());
    assertNull(actualBusinessProfileRevision.getId());
    assertSame(legalAddress, actualBusinessProfileRevision.getLegalAddress());
    verify(businessProfileRevisionDataAccess).getBusinessProfileForRevision(Mockito.<String>any(),
        Mockito.<Integer>any());
  }

  /**
   * Method under test:
   * {@link BusinessProfileRevisionImpl#getBusinessProfileRevision(String, Integer)}
   */
  @Test
  void testGetBusinessProfileRevision3() {
    when(businessProfileRevisionDataAccess.getBusinessProfileForRevision(Mockito.<String>any(),
        Mockito.<Integer>any()))
        .thenThrow(
            new InvalidBusinessProfileRevisionException("An error occurred", HttpStatus.CONTINUE));
    assertThrows(InvalidBusinessProfileRevisionException.class,
        () -> businessProfileRevision.getBusinessProfileRevision("42", 1));
    verify(businessProfileRevisionDataAccess).getBusinessProfileForRevision(Mockito.<String>any(),
        Mockito.<Integer>any());
  }

  /**
   * Method under test:
   * {@link BusinessProfileRevisionImpl#getBusinessProfileRevision(String, Integer)}
   */
  @Test
  void testGetBusinessProfileRevision4() {
    BusinessAddress businessAddress = new BusinessAddress();
    businessAddress.setCity("Oxford");
    businessAddress.setCountry("GB");
    businessAddress.setLine1(
        "Business-profile revision for businessProfileId :: {}, revision :: {}");
    businessAddress.setLine2(
        "Business-profile revision for businessProfileId :: {}, revision :: {}");
    businessAddress.setPinCode(
        "Business-profile revision for businessProfileId :: {}, revision :: {}");
    businessAddress.setState("MD");

    LegalAddress legalAddress = new LegalAddress();
    legalAddress.setCity("Oxford");
    legalAddress.setCountry("GB");
    legalAddress.setLine1("Business-profile revision for businessProfileId :: {}, revision :: {}");
    legalAddress.setLine2("Business-profile revision for businessProfileId :: {}, revision :: {}");
    legalAddress.setPinCode(
        "Business-profile revision for businessProfileId :: {}, revision :: {}");
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
    BusinessProfileRevisionEntity businessProfileRevisionEntity = mock(
        BusinessProfileRevisionEntity.class);
    TaxIdentifier taxIdentifier = new TaxIdentifier(TaxIdentifierType.PAN, "42");

    when(businessProfileRevisionEntity.getTaxIdentifier()).thenReturn(taxIdentifier);
    when(businessProfileRevisionEntity.getBusinessAddress()).thenReturn(businessAddress2);
    when(businessProfileRevisionEntity.getLegalAddress()).thenReturn(legalAddress2);
    when(businessProfileRevisionEntity.getRevision()).thenReturn(1);
    when(businessProfileRevisionEntity.getBusinessProfileId()).thenReturn("42");
    when(businessProfileRevisionEntity.getCompanyName()).thenReturn("Company Name");
    when(businessProfileRevisionEntity.getEmail()).thenReturn("jane.doe@example.org");
    when(businessProfileRevisionEntity.getLegalName()).thenReturn("Legal Name");
    when(businessProfileRevisionEntity.getWebsite()).thenReturn("Website");
    doNothing().when(businessProfileRevisionEntity).setCreatedAt(Mockito.<LocalDateTime>any());
    doNothing().when(businessProfileRevisionEntity).setId(Mockito.<ObjectId>any());
    doNothing().when(businessProfileRevisionEntity).setUpdatedAt(Mockito.<LocalDateTime>any());
    doNothing().when(businessProfileRevisionEntity).setBusinessProfileId(Mockito.<String>any());
    doNothing().when(businessProfileRevisionEntity).setRevision(Mockito.<Integer>any());
    doNothing().when(businessProfileRevisionEntity)
        .setBusinessAddress(Mockito.<BusinessAddress>any());
    doNothing().when(businessProfileRevisionEntity).setCompanyName(Mockito.<String>any());
    doNothing().when(businessProfileRevisionEntity).setEmail(Mockito.<String>any());
    doNothing().when(businessProfileRevisionEntity).setLegalAddress(Mockito.<LegalAddress>any());
    doNothing().when(businessProfileRevisionEntity).setLegalName(Mockito.<String>any());
    doNothing().when(businessProfileRevisionEntity).setTaxIdentifier(Mockito.<TaxIdentifier>any());
    doNothing().when(businessProfileRevisionEntity).setWebsite(Mockito.<String>any());
    businessProfileRevisionEntity.setBusinessAddress(businessAddress);
    businessProfileRevisionEntity.setBusinessProfileId("42");
    businessProfileRevisionEntity
        .setCompanyName("Business-profile revision for businessProfileId :: {}, revision :: {}");
    businessProfileRevisionEntity.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    businessProfileRevisionEntity.setEmail("jane.doe@example.org");
    businessProfileRevisionEntity.setId(ObjectId.get());
    businessProfileRevisionEntity.setLegalAddress(legalAddress);
    businessProfileRevisionEntity.setLegalName(
        "Business-profile revision for businessProfileId :: {}, revision :: {}");
    businessProfileRevisionEntity.setRevision(1);
    businessProfileRevisionEntity.setTaxIdentifier(new TaxIdentifier(TaxIdentifierType.PAN, "42"));
    businessProfileRevisionEntity.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    businessProfileRevisionEntity.setWebsite(
        "Business-profile revision for businessProfileId :: {}, revision :: {}");

    ArrayList<BusinessProfileRevisionEntity> businessProfileRevisionEntityList = new ArrayList<>();
    businessProfileRevisionEntityList.add(businessProfileRevisionEntity);
    when(businessProfileRevisionDataAccess.getBusinessProfileForRevision(Mockito.<String>any(),
        Mockito.<Integer>any()))
        .thenReturn(businessProfileRevisionEntityList);
    BusinessProfileRevisionBO actualBusinessProfileRevision = businessProfileRevision
        .getBusinessProfileRevision("42", 1);
    assertSame(businessAddress2, actualBusinessProfileRevision.getBusinessAddress());
    assertEquals("Website", actualBusinessProfileRevision.getWebsite());
    assertEquals("42", actualBusinessProfileRevision.getBusinessProfileId());
    assertNull(actualBusinessProfileRevision.getCreatedAt());
    assertEquals("Legal Name", actualBusinessProfileRevision.getLegalName());
    assertEquals(1, actualBusinessProfileRevision.getRevision().intValue());
    assertEquals("jane.doe@example.org", actualBusinessProfileRevision.getEmail());
    assertSame(taxIdentifier, actualBusinessProfileRevision.getTaxIdentifier());
    assertNull(actualBusinessProfileRevision.getUpdatedAt());
    assertEquals("Company Name", actualBusinessProfileRevision.getCompanyName());
    assertNull(actualBusinessProfileRevision.getId());
    assertSame(legalAddress2, actualBusinessProfileRevision.getLegalAddress());
    verify(businessProfileRevisionDataAccess).getBusinessProfileForRevision(Mockito.<String>any(),
        Mockito.<Integer>any());
    verify(businessProfileRevisionEntity).getTaxIdentifier();
    verify(businessProfileRevisionEntity).getBusinessAddress();
    verify(businessProfileRevisionEntity).getLegalAddress();
    verify(businessProfileRevisionEntity).getRevision();
    verify(businessProfileRevisionEntity).getBusinessProfileId();
    verify(businessProfileRevisionEntity).getCompanyName();
    verify(businessProfileRevisionEntity).getEmail();
    verify(businessProfileRevisionEntity).getLegalName();
    verify(businessProfileRevisionEntity).getWebsite();
    verify(businessProfileRevisionEntity).setCreatedAt(Mockito.<LocalDateTime>any());
    verify(businessProfileRevisionEntity).setId(Mockito.<ObjectId>any());
    verify(businessProfileRevisionEntity).setUpdatedAt(Mockito.<LocalDateTime>any());
    verify(businessProfileRevisionEntity).setBusinessProfileId(Mockito.<String>any());
    verify(businessProfileRevisionEntity).setRevision(Mockito.<Integer>any());
    verify(businessProfileRevisionEntity).setBusinessAddress(Mockito.<BusinessAddress>any());
    verify(businessProfileRevisionEntity).setCompanyName(Mockito.<String>any());
    verify(businessProfileRevisionEntity).setEmail(Mockito.<String>any());
    verify(businessProfileRevisionEntity).setLegalAddress(Mockito.<LegalAddress>any());
    verify(businessProfileRevisionEntity).setLegalName(Mockito.<String>any());
    verify(businessProfileRevisionEntity).setTaxIdentifier(Mockito.<TaxIdentifier>any());
    verify(businessProfileRevisionEntity).setWebsite(Mockito.<String>any());
  }

  /**
   * Method under test:
   * {@link BusinessProfileRevisionImpl#createBusinessProfileRevision(BusinessProfileRevisionBO)}
   */
  @Test
  void testCreateBusinessProfileRevision() {
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

    BusinessProfileRevisionEntity businessProfileRevisionEntity = new BusinessProfileRevisionEntity();
    businessProfileRevisionEntity.setBusinessAddress(businessAddress);
    businessProfileRevisionEntity.setBusinessProfileId("42");
    businessProfileRevisionEntity.setCompanyName("Company Name");
    businessProfileRevisionEntity.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    businessProfileRevisionEntity.setEmail("jane.doe@example.org");
    businessProfileRevisionEntity.setId(ObjectId.get());
    businessProfileRevisionEntity.setLegalAddress(legalAddress);
    businessProfileRevisionEntity.setLegalName("Legal Name");
    businessProfileRevisionEntity.setRevision(1);
    businessProfileRevisionEntity.setTaxIdentifier(new TaxIdentifier(TaxIdentifierType.PAN, "42"));
    businessProfileRevisionEntity.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    businessProfileRevisionEntity.setWebsite("Website");
    when(businessProfileRevisionDataAccess.save(Mockito.<BusinessProfileRevisionEntity>any()))
        .thenReturn(businessProfileRevisionEntity);
    assertSame(businessProfileRevisionEntity,
        businessProfileRevision.createBusinessProfileRevision(new BusinessProfileRevisionBO()));
    verify(businessProfileRevisionDataAccess).save(Mockito.<BusinessProfileRevisionEntity>any());
  }
}

