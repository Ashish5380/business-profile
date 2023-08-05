package com.personal.businessprofile.service.impl;

import com.personal.businessprofile.bo.BusinessProfileRevisionBO;
import com.personal.businessprofile.service.RevisionObserver;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Log4j2
@Service
public class RevisionObserverRegistry {

    // The RevisionObserver to be registered and notified when revisions occur.
    private final RevisionObserver revisionObserver;

    // A mapping of businessProfileId to a set of externalProductId(s) representing the registered observers.
    private final Map<String /* businessProfileId */, Set<String/* externalProductId */>> observersMap;

    @Autowired
    public RevisionObserverRegistry(RevisionObserver revisionObserver) {
        this.revisionObserver = revisionObserver;
        this.observersMap = new HashMap<>();
    }

    /**
     * Adds an observer (externalProductId) to the observer registry for a specific business profile.
     *
     * @param businessProfileId The ID of the business profile to which the observer is being added.
     * @param externalProductId The ID of the observer (product) being added.
     */
    public void addObserver(final String businessProfileId, final String externalProductId) {
        log.info("Adding observer to observer-registry for business-profile :: {}, observer(product) id :: {}",
          businessProfileId, externalProductId);
        observersMap
          .computeIfAbsent(businessProfileId, k -> new HashSet<>())
          .add(externalProductId);
    }

    /**
     * Removes an observer (externalProductId) from the observer registry for a specific business profile.
     *
     * @param businessProfileId The ID of the business profile from which the observer is being removed.
     * @param externalProductId The ID of the observer (product) being removed.
     */
    public void removeObserver(final String businessProfileId, final String externalProductId) {
        log.info("Removing observer form observer-registry for business-profile :: {}, observer(product) id :: {}",
          businessProfileId, externalProductId);
        Optional.ofNullable(observersMap.get(businessProfileId))
          .ifPresent(productObservers -> productObservers.remove(externalProductId));
    }

    /**
     * Notifies all registered observers for a specific business profile about the profile revision.
     *
     * @param businessProfileId The ID of the business profile for which the observers are being notified.
     * @param revision The BusinessProfileRevisionBO representing the revision information.
     */
    public void notifyObservers(final String businessProfileId,
                                final BusinessProfileRevisionBO revision) {
        log.info("Request to notify observer for business-profile with Id :: {}, for business-profile revision :: {}",
          businessProfileId, revision);
        Optional.ofNullable(observersMap.get(businessProfileId))
          .ifPresent(productObservers ->
            productObservers
              .forEach(observer -> revisionObserver.update(businessProfileId, revision)));
    }

    public Map<String, Set<String>> getObserversMap(){
        return observersMap;
    }

}
