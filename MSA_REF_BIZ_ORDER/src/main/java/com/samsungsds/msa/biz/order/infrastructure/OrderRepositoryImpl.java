package com.samsungsds.msa.biz.order.infrastructure;

import com.samsungsds.msa.biz.order.domain.OrderRepository;
import com.samsungsds.msa.biz.order.domain.OrderVO;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final Logger logger = LogManager.getLogger(OrderRepositoryImpl.class);
   @Autowired
   H2Repository h2Repository;

    @Override
    public void createOrder(OrderVO orderVO) {
        OrderDVO orderDVO = new OrderDVO();
        orderDVO.setId(orderVO.getId());
        orderDVO.setType(orderVO.getType());
        orderDVO.setOrigin(orderVO.getOrigin());
        orderDVO.setCost(orderVO.getCost());
        orderDVO.setCount(orderVO.getCount());

        h2Repository.save(orderDVO);
    }

    @Override
    public List<OrderVO> readOrder() {

        List<OrderVO> orderVOList = new ArrayList<>();
        List<OrderDVO> orderDVOList = h2Repository.findAll();
        for(OrderDVO orderDVO: orderDVOList){
            OrderVO orderVO = new OrderVO();
            orderVO.setId(orderDVO.getId());
            orderVO.setType(orderDVO.getType());
            orderVO.setOrigin(orderDVO.getOrigin());
            orderVO.setCost(orderDVO.getCost());
            orderVO.setCount(orderDVO.getCount());
            orderVOList.add(orderVO);
        }
        return orderVOList;
    }

    @Override
    public void updateOrder(OrderVO orderVO) {
        OrderDVO orderDVO = getOrderDVO(orderVO);
        h2Repository.save(orderDVO);
    }

    @Override
    public void deleteOrder(OrderVO orderVO) {
        OrderDVO orderDVO = getOrderDVO(orderVO);
        orderDVO.setId(orderVO.getId());
        h2Repository.delete(orderDVO);
    }
    private OrderDVO getOrderDVO(OrderVO orderVO){
        OrderDVO orderDVO = new OrderDVO();
        orderDVO.setId(orderVO.getId());
        orderDVO.setOrigin(orderVO.getOrigin());
        orderDVO.setType(orderVO.getType());
        orderDVO.setCount(orderVO.getCount());
        orderDVO.setCost(orderVO.getCost());
        return orderDVO;
    }
}
