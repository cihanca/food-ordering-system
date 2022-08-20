package org.food.ordering.system.domain.entity;

import lombok.Data;

@Data
public abstract class AggregateRoot<ID> extends BaseEntity<ID>{
}
