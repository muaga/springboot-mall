package shop.mtcoding.mall.model;

import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Setter
@Table(name = "seller_tb")
@Entity
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String email;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
