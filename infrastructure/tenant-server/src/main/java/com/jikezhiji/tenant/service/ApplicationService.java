package com.jikezhiji.tenant.service;

import com.jikezhiji.commons.DiscoveryClients;
import com.jikezhiji.tenant.domain.Application;
import com.jikezhiji.tenant.domain.ApplicationDependency;
import com.jikezhiji.tenant.enumeration.EntityStatus;
import com.jikezhiji.tenant.repository.ApplicationDependencyRepository;
import com.jikezhiji.tenant.repository.ApplicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.data.domain.Example;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.ConnectException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.jikezhiji.commons.DiscoveryConstants.*;

/**
 * Created by liusizuo on 2017/5/8.
 */
@Service
@Transactional
public class ApplicationService {
    private Logger log = LoggerFactory.getLogger(getClass());

    private final ApplicationRepository applicationRepository;

    private final ApplicationDependencyRepository dependencyRepository;

    @Autowired
    public ApplicationService(
                              ApplicationRepository applicationRepository,
                              ApplicationDependencyRepository dependencyRepository) {
        this.applicationRepository = applicationRepository;
        this.dependencyRepository = dependencyRepository;
    }

    @Scheduled(initialDelay = 3 * 1000, fixedDelay = 2 * 60 * 60 * 1000)
    public void updateApplicationFromRegistry(){
        try {
            List<ServiceInstance> masterInstances = DiscoveryClients.getAllBusinessServiceMasterInstances();
            for (ServiceInstance masterInstance : masterInstances) {
                Application master = transfer(masterInstance);
                Application application = applicationRepository.findOne(masterInstance.getServiceId());
                if(application == null) {
                    application = master;
                } else {
                    BeanUtils.copyProperties(master,application,"id","createTime");
                    dependencyRepository.deleteByApplicationId(masterInstance.getServiceId());
                    dependencyRepository.save(getDependencies(application));
                }
                applicationRepository.save(application);
            }
            List<String> ids = masterInstances.stream()
                    .map(ServiceInstance::getServiceId)
                    .collect(Collectors.toList());

            if(ids.size() > 0) {
                applicationRepository.findByIdNotIn(ids).forEach(app->{
                    app.setStatus(EntityStatus.UNAVAILABLE);
                    applicationRepository.save(app);
                });
            }
        } catch (Exception e){
            boolean connectRefused = false;
            while(e.getCause() != null) {
                if(e.getCause() instanceof ConnectException) {
                    connectRefused = true;
                }
            }
            if(connectRefused) {
                log.warn("无法连接到注册中心");
            } else {
                throw e;
            }
        }
    }


    private Application transfer(ServiceInstance instance) {
        Application application = new Application();
        application.setCreateTime(new Date());
        application.setId(instance.getServiceId());
        application.setName(instance.getMetadata().get(APP_NAME));
        application.setGroup(instance.getMetadata().get(APP_GROUP_NAME));
        application.setDescription(instance.getMetadata().get(DESCRIPTION));
        application.setVersion(instance.getMetadata().get(VERSION));


        String dependencies = instance.getMetadata().get(DEPENDENCIES);
        if(dependencies!= null) {
            application.setDependencies(Arrays.asList(dependencies.split(",")));
        }

        if("UP".equals(instance.getMetadata().get(STATUS))) {
            application.setStatus(EntityStatus.AVAILABLE);
        } else {
            application.setStatus(EntityStatus.UNAVAILABLE);
        }

        return application;
    }

    private List<ApplicationDependency> getDependencies(Application application){
        return application.getDependencies().stream()
                .map(dependencyId -> new ApplicationDependency(application.getId(),dependencyId))
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<Application> getAllApplications(){
        List<Application> applications = applicationRepository.findAll();
        applications.forEach(application -> {
            ApplicationDependency dependency = new ApplicationDependency(application.getId(),null);
            application.setDependencies(dependencyRepository
                    .findAll(Example.of(dependency)).stream()
                    .map(ApplicationDependency::getDependencyApplicationId)
                    .collect(Collectors.toList()));
        });
        return applications;
    }

    @Transactional(readOnly = true)
    public Application getApplicationById(String applicationId){
        return applicationRepository.findOne(applicationId);
    }
}
