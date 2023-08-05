package com.personal.businessprofile.scheduler;

import com.personal.businessprofile.bo.BusinessProfileResponseBO;
import com.personal.businessprofile.bo.BusinessProfileRevisionBO;
import com.personal.businessprofile.bo.ProfileMappingBO;
import com.personal.businessprofile.constant.StringConstants;
import com.personal.businessprofile.enums.ValidationStatus;
import com.personal.businessprofile.exception.DatabaseException;
import com.personal.businessprofile.model.ProfileMapping;
import com.personal.businessprofile.service.BusinessProfileRevisionManager;
import com.personal.businessprofile.service.ProfileMappingService;
import com.personal.businessprofile.service.impl.RevisionObserverRegistry;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BusinessProfileUpdateScheduler {
    private final RevisionObserverRegistry revisionObserverRegistry;

    private final ProfileMappingService profileMappingService;

    private final BusinessProfileRevisionManager businessProfileRevisionManager;

    @Scheduled(cron = "0 */5 * * * *")
    public void businessProfileUpdaterJob() {
        Map<String, Set<String>> obeserverMap = revisionObserverRegistry.getObserversMap();
        Map<BusinessProfileResponseBO, List<ProfileMappingBO>> businessProfileToMappingMap =
          fetchMappingForBusinessProfiles(obeserverMap);

        businessProfileToMappingMap.keySet()
          .forEach(bo -> validateAndCallBusinessProfileUpdate(bo, businessProfileToMappingMap.get(bo)));
    }

    /**
     * Fetches profile mappings for business profiles based on the given observer map.
     * <p>
     * This method takes an observer map as input, where each entry represents a set of observers
     * associated with a specific business profile. It then fetches profile mappings for each business
     * profile using the provided proxy and service methods, and returns the result as a map, where the
     * keys are BusinessProfileResponseBO objects representing the business profiles, and the values
     * are lists of ProfileMappingBO objects containing the corresponding profile mappings.
     *
     * @param observerMap A map containing business profile IDs as keys and sets of observer IDs as values.
     *                    The observer IDs represent observers associated with each business profile.
     * @return A map containing BusinessProfileResponseBO objects as keys and lists of ProfileMappingBO
     * objects as values, representing the profile mappings for the business profiles.
     */
    private Map<BusinessProfileResponseBO, List<ProfileMappingBO>> fetchMappingForBusinessProfiles(
      @NonNull Map<String, Set<String>> observerMap) {
        Map<BusinessProfileResponseBO, List<ProfileMappingBO>> profileToMappingSet = observerMap.keySet()
          .stream()
          .collect(Collectors.toMap(
            businessProfileRevisionManager::getBusinessProfile,
            profileMappingService::getAllLatestValidMappingForBusiness));

        return profileToMappingSet;
    }

    /**
     * Validates the business profile update and calls the appropriate business profile update method.
     * <p>
     * This method takes a BusinessProfileResponseBO object and a list of ProfileMappingBO objects as input.
     * It first extracts the revisions from the profileMappings list and validates them against the latest
     * valid revision of the business profile (bo). If the version upgrade is valid, it proceeds to update
     * the business profile using the corresponding BusinessProfileRevisionBO object fetched from the proxy.
     *
     * @param bo              The BusinessProfileResponseBO object representing the business profile to be updated.
     * @param profileMappings A list of ProfileMappingBO objects representing the profile mappings for the
     *                        business profile.
     * @throws DatabaseException If the validation status of the first profile mapping in the list is not
     *                           set to VALID, indicating an invalid validation state.
     */
    private void validateAndCallBusinessProfileUpdate(
      BusinessProfileResponseBO bo, List<ProfileMappingBO> profileMappings) {

        // extract the revisions from the profileMappings list and
        List<Integer> mappingRevisions = profileMappings.stream()
          .map(ProfileMappingBO::getRevision)
          .toList();

        if (validateVersionUpgrade(bo.getLatestValidRevision(), mappingRevisions)) {

            // validate against the latest valid revision of the business profile
            ProfileMapping mapping = Optional.ofNullable(CollectionUtils.firstElement(profileMappings))
              .filter(firstMapping -> firstMapping.getValidationStatus().equals(ValidationStatus.VALID))
              .orElseThrow(() -> new DatabaseException(StringConstants.Error.INVALID_VALIDATION_STATE,
                HttpStatus.BAD_REQUEST));

            // Fetch a given revision from the revision collection
            BusinessProfileRevisionBO revision = businessProfileRevisionManager.fetchBusinessProfileRevision(
              bo.getId().toString(), mapping.getRevision());

            // Update Business profile if validation is successful
            businessProfileRevisionManager.updateBusinessProfile(revision);
        }
    }

    /**
     * Validates the version upgrade for a business profile.
     * <p>
     * This method checks whether there are any revisions in the 'newRevisionList' that indicate an upgrade
     * compared to the 'lastValidRevision'. If any of the revisions in the 'newRevisionList' are greater than
     * the 'lastValidRevision', it returns true, indicating a valid version upgrade. Otherwise, it returns
     * false, indicating that there is no version upgrade.
     *
     * @param lastValidRevision The last valid revision of the business profile.
     * @param newRevisionList   A list of integer revisions representing the revisions to be validated for upgrade.
     * @return true if there is a version upgrade, false otherwise.
     */
    private Boolean validateVersionUpgrade(Integer lastValidRevision, List<Integer> newRevisionList) {
        if (newRevisionList.isEmpty()) {
            return false;
        }
        return newRevisionList.stream()
          .anyMatch(revision -> revision > lastValidRevision);
    }
}


