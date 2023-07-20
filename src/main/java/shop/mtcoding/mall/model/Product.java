package shop.mtcoding.mall.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
// model만으로 자동으로 Mysql에 테이블이 생성되는 방법


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
}
