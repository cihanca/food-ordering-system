package org.food.ordering.system.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public abstract class BaseEntity<ID> {

    private ID id;

}
