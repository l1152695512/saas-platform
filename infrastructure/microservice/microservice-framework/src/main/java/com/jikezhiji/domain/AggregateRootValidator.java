package com.jikezhiji.domain;

import org.springframework.validation.beanvalidation.CustomValidatorBean;

import javax.validation.Configuration;
import javax.validation.Validation;
import javax.validation.spi.ValidationProvider;

/**
 * Created by E355 on 2016/8/31.
 */
public class AggregateRootValidator extends CustomValidatorBean {

    @Override
    public boolean supports(Class<?> clazz) {
        return AggregateRoot.class.isAssignableFrom(clazz);

    }

    public AggregateRootValidator(Class<? extends ValidationProvider> validationProviderClass){
        this(validationProviderClass != null ?
                Validation.byProvider(validationProviderClass).configure() :
                Validation.byDefaultProvider().configure());
    }

    public AggregateRootValidator(Configuration configuration) {
        super.setValidatorFactory(configuration.buildValidatorFactory());
        super.afterPropertiesSet();
    }

}
