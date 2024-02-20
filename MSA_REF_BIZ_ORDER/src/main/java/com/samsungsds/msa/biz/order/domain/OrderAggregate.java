package com.samsungsds.msa.biz.order.domain;


import java.util.List;

public class OrderAggregate {

    private OrderRepository orderRepository;

    public OrderAggregate(OrderRepository orderRepository){

        this.orderRepository = orderRepository;
    }

    public void createOrder(OrderVO orderVO){
        orderRepository.createOrder(orderVO);
    }

    public List<OrderVO> readOrder() {

        return orderRepository.readOrder();
    }

    public void updateOrder(OrderVO orderVO){
        System.out.println("OrderAggregate::updateOrder");
        orderRepository.updateOrder(orderVO);
    }

    public void deleteOrder(OrderVO orderVO){
        orderRepository.deleteOrder(orderVO);
    }
//    public static void main(String[] args){
//        OrderAggregate orderAggregate = new OrderAggregate();
//        OrderVO orderVO = new OrderVO();
//        orderVO.setId(1);
//        orderVO.setOrigin("Colombia");
//        orderVO.setCount(1);
//        orderVO.setType("americano");
//        orderVO.setCost(100);
//
//        System.out.println("===========================");
//
//        OrderRepository orderRepository1 = new OrderRepository() {
//            @Override
//            public void saveOrder(OrderVO orderVO) {
//                System.out.println("----------orderVO");
//                System.out.println(orderVO.getCount());
//            }
//
//            @Override
//            public List<OrderVO> orders() {
//                return null;
//            }
//        };
//        orderRepository1.saveOrder(orderVO);
//
//        //        orderAggregate.saveOrder(orderVO);
//    }
}