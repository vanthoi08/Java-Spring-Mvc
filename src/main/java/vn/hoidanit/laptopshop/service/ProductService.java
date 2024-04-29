package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.domain.dto.ProductCriteriaDTO;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;
import vn.hoidanit.laptopshop.repository.CartRepository;
import vn.hoidanit.laptopshop.repository.OrderDetailRepository;
import vn.hoidanit.laptopshop.repository.OrderRepository;
import vn.hoidanit.laptopshop.repository.ProductRepository;
import vn.hoidanit.laptopshop.service.specification.ProductSpecs;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;
    private final OrderRepository  orderRepository;
    private final OrderDetailRepository orderDetailRepository;


    public ProductService(ProductRepository productRepository,
            CartRepository cartRepository,
            CartDetailRepository cartDetailRepository,
            UserService userService,
            OrderRepository  orderRepository,
            OrderDetailRepository orderDetailRepository ) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userService = userService;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public Product createProduct(Product product) {
        return this.productRepository.save(product);

    }

  

    public Page<Product> fetchProducts(Pageable page) {
        return this.productRepository.findAll(page);
    }
    // Query with name
    public Page<Product> fetchProductsWithSpec(Pageable page, ProductCriteriaDTO  productCriteriaDTO) {
        // check điều kiện lúc mới vào trang
        if(productCriteriaDTO.getFactory()== null && productCriteriaDTO.getTarget()==null && productCriteriaDTO.getPrice()==null){
        return this.productRepository.findAll(page);

        }

        Specification<Product> combinedSpec = Specification.where(null);

        if(productCriteriaDTO.getTarget()!=null && productCriteriaDTO.getTarget().isPresent()){
        Specification<Product> currentSpecs = ProductSpecs.matchListTarget(productCriteriaDTO.getTarget().get());
        combinedSpec = combinedSpec.and(currentSpecs);
        }

        if(productCriteriaDTO.getFactory()!=null && productCriteriaDTO.getFactory().isPresent()){
            Specification<Product> currentSpecs = ProductSpecs.matchListFactory(productCriteriaDTO.getFactory().get());
         combinedSpec = combinedSpec.and(currentSpecs);
            }
        if(productCriteriaDTO.getPrice()!=null && productCriteriaDTO.getPrice().isPresent()){
            Specification<Product> currentSpecs = this.buildPriceSpecification(productCriteriaDTO.getPrice().get());
         combinedSpec = combinedSpec.and(currentSpecs);

        }

        return this.productRepository.findAll(combinedSpec, page);
    }

    // Case 1: 
        // public Page<Product> fetchProductsWithSpec(Pageable page, double min) {
        //     return this.productRepository.findAll( ProductSpecs.minPrice(min), page);
        // }

       // Case 2: 
    //    public Page<Product> fetchProductsWithSpec(Pageable page, double max) {
    //     return this.productRepository.findAll( ProductSpecs.maxPrice(max), page);
    // }

         // Case 3: 
        //  public Page<Product> fetchProductsWithSpec(Pageable page, String factory) {
        //     return this.productRepository.findAll( ProductSpecs.matchFactory(factory), page);
        // }

   // Case 4: 
        // public Page<Product> fetchProductsWithSpec(Pageable page, List<String> factory) {
        //     return this.productRepository.findAll( ProductSpecs.matchListFactory(factory), page);
        // }
// Case 5:
        // public Page<Product> fetchProductsWithSpec(Pageable page, String price) {
        //     // eg: price 10-toi-15-trieu
        //     if(price.equals("10-toi-15-trieu")){
        //         double min = 10000000;
        //         double max = 15000000;
        //         return this.productRepository.findAll( ProductSpecs.matchPrice(min,max), page);
        //     } else if(price.equals("15-toi-30-trieu")){
        //         double min = 15000000;
        //         double max = 30000000;
        //         return this.productRepository.findAll( ProductSpecs.matchPrice(min,max), page);
        //     } else{
        //         return this.productRepository.findAll(page);
        //     }
        // }

// Case 6:
public Specification<Product> buildPriceSpecification(List<String> price) {
    // Khởi tạo combinedSpec để tránh lần đầu tiên giá trị = null
//   Specification<Product> combinedSpec = (root, query, criteriaBuilder) -> criteriaBuilder.disjunction();
  Specification<Product> combinedSpec = Specification.where(null); // disjunction()
  for(String p:price){  
    double min = 0;
    double max = 0;

    // Set các case
    switch (p) {
        case "duoi-10-trieu":
            min = 0;
            max = 10000000;
            break;

        case "10-15-trieu":
            min = 10000000;
            max = 15000000;
            break;

        case "15-20-trieu":
            min = 15000000;
            max = 20000000;
            break;

        case "tren-20-trieu":
            min = 20000000;
            max = 200000000;
            break;
        // Add more cases as needed
      
    }

    if(min!=0 && max!=0){
        Specification<Product> rangeSpec = ProductSpecs.matchMultiplePrice(min,max);
        combinedSpec = combinedSpec.or(rangeSpec);
    }

  }
return combinedSpec;

}
    


  

    public Optional<Product> fetchProductById(long id) {
        return this.productRepository.findById(id);
    }
  

    public void deleteProduct(long id) {
        this.productRepository.deleteById(id);
    }

    public void handleAddProductToCart(String email, long productId, HttpSession session, long quantity) {
        User user = this.userService.getUserByEmail(email);
        if (user != null) {
            // Check user đã có cart chưa ? nếu chưa => tạo mới
            Cart cart = this.cartRepository.findByUser(user);
            if (cart == null) {
                // Tạo mới cart
                Cart otherCart = new Cart();
                otherCart.setUser(user);
                otherCart.setSum(0);

                cart = this.cartRepository.save(otherCart);
            }

            // save cart_detail
            // tìm product by id

            Optional<Product> producOptional = this.productRepository.findById(productId);
            if (producOptional.isPresent()) {
                Product realProduct = producOptional.get(); // Lấy ra đối tượng
                // Check sản phẩm đã từng được thêm vào giỏ hàng trước đây chưa ?
                CartDetail oldDetail=  this.cartDetailRepository.findByCartAndProduct(cart, realProduct);

                // 
                if(oldDetail== null){
                    CartDetail cd = new CartDetail();
                    cd.setCart(cart);
                    cd.setProduct(realProduct);
                    cd.setPrice(realProduct.getPrice());
                    cd.setQuantity(quantity);
                    this.cartDetailRepository.save(cd);
                    // update cart (sum)
                    int s = cart.getSum() + 1;
                    cart.setSum(s);
                    this.cartRepository.save(cart);
                    session.setAttribute("sum", s);
                } else{
                    oldDetail.setQuantity(oldDetail.getQuantity()+ quantity);
                    this.cartDetailRepository.save(oldDetail);

                }
            }

        }
    }

    public Cart fetchByUser(User user){
        return this.cartRepository.findByUser(user);
    }

    public void handleRemoveCartDetail(long cartDetailId, HttpSession session) {
        Optional<CartDetail> cartDetailOptional = this.cartDetailRepository.findById(cartDetailId);
        if(cartDetailOptional.isPresent()){
            CartDetail cartDetail = cartDetailOptional.get();

            Cart currentCart = cartDetail.getCart();
            // delete cart-detail
            this.cartDetailRepository.deleteById(cartDetailId);

            // update cart
            if(currentCart.getSum() > 1){
                // update current cart
                int s = currentCart.getSum()-1;
                currentCart.setSum(s);
                session.setAttribute("sum", s);
                this.cartRepository.save(currentCart);
            }else{
                // delete cart (sum = 1)
                this.cartRepository.deleteById(currentCart.getId());
                session.setAttribute("sum", 0);
                // this.cartRepository.save(currentCart);
            }
        }
    }

    public void handleUpdateCartBeforeCheckout(List<CartDetail> cartDetails) {
        for(CartDetail cartDetail : cartDetails){
            Optional<CartDetail> cdOptional = this.cartDetailRepository.findById(cartDetail.getId());
            if(cdOptional.isPresent()){
                CartDetail currentCartDetail = cdOptional.get();
                currentCartDetail.setQuantity(cartDetail.getQuantity());
                this.cartDetailRepository.save(currentCartDetail);
            }
        }
    }

    public void handlePlaceOrder(
        User user,  HttpSession session,
        String receiverName, String receiverAddress, String receiverPhone){



          // Step 1: get cart by user
          Cart cart = this.cartRepository.findByUser(user);
          if(cart != null){
            List<CartDetail> cartDetails = cart.getCartDetails();
            if(cartDetails != null){
                // create order
                Order order = new Order();
                order.setUser(user);
                order.setReceiverName(receiverName);
                order.setReceiverAddress(receiverAddress);
                order.setReceiverPhone(receiverPhone);
                order.setStatus("PENDING");

                double sum = 0;
                for(CartDetail cd: cartDetails){
                    sum += cd.getPrice()*cd.getQuantity();
                }
                order.setTotalPrice(sum);
                 // hứng order 
                 order =  this.orderRepository.save(order);


                // create orderDetail
                for(CartDetail cd:cartDetails){
                    OrderDetail  orderDetail = new OrderDetail();
                    orderDetail.setOrder(order);
                    orderDetail.setProduct(cd.getProduct());
                    orderDetail.setPrice(cd.getPrice());
                    orderDetail.setQuantity(cd.getQuantity());

                    this.orderDetailRepository.save(orderDetail);
                }

                // Step 2: delete cart_detail and cart
                // Xóa thằng con cart_detail
                for(CartDetail cd : cartDetails){
                    this.cartDetailRepository.deleteById(cd.getId());
                }

                // delete cart
                this.cartRepository.deleteById(cart.getId());

                // step 3: update session reset lại giỏ hàng
                session.setAttribute("sum", 0);
            }
          }
    }



   
}
