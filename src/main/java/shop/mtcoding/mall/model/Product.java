package shop.mtcoding.mall.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
// model만으로 자동으로 Mysql에 테이블이 생성되는 방법
// product - seller -> n:1관계

@Setter
@Table(name = "product_tb")
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer price;
    private Integer qty;

    @ManyToOne
    // 테이블 간의 관계에서 상품이 n이기 때문에 ManyToOne이고,
    // 1쪽인 seller를 오브젝트로 만들어서 column을 생성하면
    // seller_id라고 자동완성이 된다.
    private Seller seller;



    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getQty() {
        return qty;
    }

    public Seller getSeller() {
        return seller;
    }
}
