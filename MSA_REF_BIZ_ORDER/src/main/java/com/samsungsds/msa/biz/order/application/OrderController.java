package com.samsungsds.msa.biz.order.application;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.List;

@RestController
@RequestMapping("/rest/api/v1/order")
@Log4j2
public class OrderController {

    private final Logger logger = LogManager.getLogger(OrderController.class);

    private final OrderService orderService;

    OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping
    public void createOrder(OrderDTO orderDTO, @RequestHeader HttpHeaders httpHeaders){
        orderService.createOrder(orderDTO);
//        String authAccessToken = httpHeaders.get("Authorization").get(0);
//        logger.debug(authAccessToken);

        orderService.createOrder(orderDTO);
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> readOrder(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        return new ResponseEntity<>(orderService.readOrder(), headers, HttpStatus.OK);
    }

    @PutMapping
    public void updateOrder(OrderDTO orderDTO){
        orderService.updateOrder(orderDTO);
    }

    @DeleteMapping
    public void deleteOrder(OrderDTO orderDTO){
        orderService.deleteOrder(orderDTO);
    }
}
