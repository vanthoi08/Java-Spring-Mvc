package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.repository.OrderDetailRepository;
import vn.hoidanit.laptopshop.repository.OrderRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public List<Order> fetchAllOrders(){
        return this.orderRepository.findAll();
    }

    public Optional<Order> fetchOrderById(long id) {
        return this.orderRepository.findById(id);
    }

    public void updateOrder(Order order) {
      Optional<Order> orderOptional = this.fetchOrderById(order.getId());
      if(orderOptional.isPresent()){
        Order currentOrder = orderOptional.get();
        currentOrder.setStatus(order.getStatus());
        this.orderRepository.save(currentOrder);
      }
        
    }

    public void deleteOrder(long id) {
      Optional<Order> orderOptional = this.fetchOrderById(id);
      if (orderOptional.isPresent()) {
        Order currentOrder = orderOptional.get();
          // delete order detail
        List<OrderDetail> orderDetails = currentOrder.getOrderDetails();
        for(OrderDetail od:orderDetails){
          this.orderDetailRepository.deleteById(od.getId());
        }

        // delete order
        this.orderRepository.deleteById(currentOrder.getId());
      }
    }
    
}
