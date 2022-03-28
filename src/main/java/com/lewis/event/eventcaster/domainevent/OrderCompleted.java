package com.lewis.event.eventcaster.domainevent;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Louis
 * @ModuleOwner: Louis
 * @Date:03/24/2022 17:48
 * @Description:
 */
@Data
@NoArgsConstructor
public class OrderCompleted extends BaseDomainEvent {

    private String orderId;

    @Override
    public String getEventName() {
        return "order-completed";
    }
}
