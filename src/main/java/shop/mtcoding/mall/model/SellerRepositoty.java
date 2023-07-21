package shop.mtcoding.mall.model;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Repository
public class SellerRepositoty {

    @Autowired
    private EntityManager em;

    // seller 등록
    public void save(String name, String email){
        Query query = em.createNativeQuery("insert into seller_tb(name, email) values( :name, :email )");
        query.setParameter("name", name);
        query.setParameter("email", email);
        query.executeUpdate();
    }

}
