package com.personal.businessprofile.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import com.personal.businessprofile.bo.BusinessProfileRevisionBO;
import com.personal.businessprofile.service.impl.RevisionObserverRegistry;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RevisionObserverRegistry.class})
@ExtendWith(SpringExtension.class)
class RevisionObserverRegistryTest {

  @MockBean
  private RevisionObserver revisionObserver;

  @Autowired
  private RevisionObserverRegistry revisionObserverRegistry;

  /**
   * Methods under test:
   *
   * <ul>
   *   <li>{@link RevisionObserverRegistry#RevisionObserverRegistry(RevisionObserver)}
   *   <li>{@link RevisionObserverRegistry#getObserversMap()}
   * </ul>
   */
  @Test
  void testConstructor() {
    assertTrue(
        (new RevisionObserverRegistry(mock(RevisionObserver.class))).getObserversMap().isEmpty());
  }

  /**
   * Method under test: {@link RevisionObserverRegistry#addObserver(String, String)}
   */
  @Test
  void testAddObserver() {
    revisionObserverRegistry.addObserver("42",
        "Adding observer to observer-registry for business-profile :: {}, observer(product) id :: {}");
    Map<String, Set<String>> observersMap = revisionObserverRegistry.getObserversMap();
    assertEquals(1, observersMap.size());
    assertEquals(1, observersMap.get("42").size());
  }

  /**
   * Method under test: {@link RevisionObserverRegistry#removeObserver(String, String)}
   */
  @Test
  void testRemoveObserver() {
    //   Diffblue Cover was unable to write a Spring test,
    //   so wrote a non-Spring test instead.
    //   Diffblue AI was unable to find a test

    // TODO: Complete this test.
    //   Diffblue AI was unable to find a test

    (new RevisionObserverRegistry(mock(RevisionObserver.class))).removeObserver("42", "42");
  }

  /**
   * Method under test: {@link RevisionObserverRegistry#removeObserver(String, String)}
   */
  @Test
  void testRemoveObserver2() {
    //   Diffblue Cover was unable to write a Spring test,
    //   so wrote a non-Spring test instead.
    //   Diffblue AI was unable to find a test

    RevisionObserverRegistry revisionObserverRegistry = new RevisionObserverRegistry(
        mock(RevisionObserver.class));
    revisionObserverRegistry.addObserver("42", "42");
    revisionObserverRegistry.removeObserver("42", "42");
    assertTrue(revisionObserverRegistry.getObserversMap().get("42").isEmpty());
  }

  /**
   * Method under test:
   * {@link RevisionObserverRegistry#notifyObservers(String, BusinessProfileRevisionBO)}
   */
  @Test
  void testNotifyObservers() {
    BusinessProfileRevisionBO revision = new BusinessProfileRevisionBO();
    revisionObserverRegistry.notifyObservers("42", revision);
    assertNull(revision.getBusinessAddress());
    assertNull(revision.getWebsite());
    assertNull(revision.getUpdatedAt());
    assertNull(revision.getTaxIdentifier());
    assertNull(revision.getRevision());
    assertNull(revision.getLegalName());
    assertNull(revision.getLegalAddress());
    assertNull(revision.getId());
    assertNull(revision.getEmail());
    assertNull(revision.getCreatedAt());
    assertNull(revision.getCompanyName());
    assertNull(revision.getBusinessProfileId());
  }

  /**
   * Method under test:
   * {@link RevisionObserverRegistry#notifyObservers(String, BusinessProfileRevisionBO)}
   */
  @Test
  void testNotifyObservers2() {
    BusinessProfileRevisionBO revision = new BusinessProfileRevisionBO();
    revisionObserverRegistry.notifyObservers(
        "Request to notify observer for business-profile with Id :: {}, for business-profile revision :: {}",
        revision);
    assertNull(revision.getBusinessAddress());
    assertNull(revision.getWebsite());
    assertNull(revision.getUpdatedAt());
    assertNull(revision.getTaxIdentifier());
    assertNull(revision.getRevision());
    assertNull(revision.getLegalName());
    assertNull(revision.getLegalAddress());
    assertNull(revision.getId());
    assertNull(revision.getEmail());
    assertNull(revision.getCreatedAt());
    assertNull(revision.getCompanyName());
    assertNull(revision.getBusinessProfileId());
  }
}

