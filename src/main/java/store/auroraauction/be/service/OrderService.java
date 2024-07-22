package store.auroraauction.be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.auroraauction.be.Models.OrderResponse;
import store.auroraauction.be.Models.RequestBuyRequest;
import store.auroraauction.be.entity.Jewelry;
import store.auroraauction.be.entity.Order;
import store.auroraauction.be.entity.RequestBuy;
import store.auroraauction.be.enums.RequestBuyEnum;
import store.auroraauction.be.repository.JewelryRepository;
import store.auroraauction.be.repository.OrderRepository;
import store.auroraauction.be.utils.AccountUtils;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private AccountUtils accountUtils;
    @Autowired
    private JewelryRepository jewelryRepository;
    @Autowired
    private OrderRepository orderRepository;

    public String deleteOrder(long id) {
        orderRepository.deleteById(id) ;
        return "Order delteted";
    }

    public List<Order> getAllOrder(){
        List<Order> order = orderRepository.findAll();
        return order;
    }
    public Order getOrder(long id) {
        Order order = orderRepository.findById(id).get();
        return order;
    }

}
