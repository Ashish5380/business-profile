package com.personal.businessprofile.api.fallback;

import com.personal.businessprofile.dto.request.ProductRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.RedirectView;

@Log4j2
@Component
public class ProductFallback {

    public RedirectView fallbackForCreateProduct(ProductRequest request){
       log.error("Fallback initiated for create product request :: {}", request);
       return new RedirectView("/error/api-failure");
    }
}
