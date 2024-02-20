package com.samsungsds.msa.biz.order.application;

import com.samsungsds.msa.biz.order.MsaRefBizOrderApplication;
import com.samsungsds.msa.biz.order.domain.OrderAggregate;
import com.samsungsds.msa.biz.order.domain.OrderRepository;
import com.samsungsds.msa.biz.order.domain.OrderVO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = MsaRefBizOrderApplication.class)
public class DomainBeanConfiguration {
    @Bean
    OrderAggregate orderAggregate(OrderRepository orderRepository){
        return new OrderAggregate(orderRepository);
    }
    @Bean
    OrderVO orderVO(){
        return new OrderVO();
    }
}
