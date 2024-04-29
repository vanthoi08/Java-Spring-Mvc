package vn.hoidanit.laptopshop.service.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.Product_;

public class ProductSpecs {
    
  public  static Specification<Product> nameLike(String name){
        return (root, query, criteriaBuilder) 
        -> criteriaBuilder.like(root.get(Product_.NAME), "%"+name+"%");
    }

    // Case 1:
// public static Specification<Product> minPrice(double min) {
//     return (root, query, criteriaBuilder) 
//     -> criteriaBuilder.ge(root.get(Product_.PRICE),min);
// }

// Case 2:
public static Specification<Product> maxPrice(double max) {
    return (root, query, criteriaBuilder) 
    -> criteriaBuilder.le(root.get(Product_.PRICE),max);
}



// Case 3:
// public static Specification<Product> matchFactory(String factory) {
//     return (root, query, criteriaBuilder) 
//     -> criteriaBuilder.equal(root.get(Product_.FACTORY),factory);
// }

// Case 4:

public static Specification<Product> matchListFactory(List<String> factory) {
    return (root, query, criteriaBuilder) 
    -> criteriaBuilder.in(root.get(Product_.FACTORY)).value(factory);
}

// Case 4:

public static Specification<Product> matchListTarget(List<String> target) {
    return (root, query, criteriaBuilder) 
    -> criteriaBuilder.in(root.get(Product_.TARGET)).value(target);
}

// Case 5:
public static Specification<Product> matchPrice(double min, double max) {
    return (root, query, criteriaBuilder) 
    -> criteriaBuilder.and(
        criteriaBuilder.gt(root.get(Product_.price), min),
        criteriaBuilder.le(root.get(Product_.price), max)
    );
}

// Case 6:
public static Specification<Product> matchMultiplePrice(double min, double max) {
    return (root, query, criteriaBuilder) 
    -> criteriaBuilder.between(root.get(Product_.PRICE),min,max);
}


}
