package com.intuit.businessprofile.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.intuit.businessprofile.bo.BusinessProfileRevisionBO;
import com.intuit.businessprofile.config.KafkaProducer;
import com.intuit.businessprofile.service.impl.BusinessRevisionObserver;
import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {BusinessRevisionObserver.class, KafkaProducer.class, String.class})
class BusinessRevisionObserverTest {

  @Autowired
  private BusinessRevisionObserver businessRevisionObserver;

  @MockBean
  private KafkaTemplate kafkaTemplate;

  /**
   * Method under test: {@link BusinessRevisionObserver#update(String, BusinessProfileRevisionBO)}
   */
  @Test
  void testUpdate() {
    when(kafkaTemplate.send(Mockito.<String>any(), Mockito.<Object>any())).thenReturn(
        new CompletableFuture<>());
    businessRevisionObserver.update("42", new BusinessProfileRevisionBO());
    verify(kafkaTemplate).send(Mockito.<String>any(), Mockito.<Object>any());
  }

  /**
   * Method under test: {@link BusinessRevisionObserver#update(String, BusinessProfileRevisionBO)}
   */
  @Test
  void testUpdate2() {
    BusinessProfileRevisionBO revision = mock(BusinessProfileRevisionBO.class);
    when(revision.getCreatedAt()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(revision.getId()).thenReturn(ObjectId.get());
    businessRevisionObserver.update("42", revision);
    verify(revision).getCreatedAt();
    verify(revision).getId();
  }
}

