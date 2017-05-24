package com.jikezhiji.domain.command.repository;

import com.jikezhiji.domain.AggregateRootRepository;
import com.jikezhiji.domain.IdIncrementEntityRepository;
import com.jikezhiji.domain.command.repository.EventRepository;

/**
 * Created by E355 on 2016/8/26.
 */
public interface CommandRepositorySupport extends AggregateRootRepository,IdIncrementEntityRepository,EventRepository{

}
