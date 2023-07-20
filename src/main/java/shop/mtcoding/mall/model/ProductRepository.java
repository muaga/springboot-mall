package shop.mtcoding.mall.model;

// DAO = Repository
// DAO는 DB에서만 접근이 가능해서, 조금 더 넒은 의미인 Repository를 쓴다.

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
// 메모리에 new 된다. -> 없으면 @Autowired가 오류난다.

public class ProductRepository {

    @Autowired
    // 의존성 주입
    private EntityManager em;
    // entityManager : entity 객체의 영속성 관리와 데이터베이스 트랜잭션 관리를 담당한다

    @Transactional
    public void save(String name, int price, int qty){
        Query query = em.createNativeQuery("insert into product_tb(name, price, qty) values(:name, :price, :qty)");
        // query : 프로토콜이 된 buffer의 역할.
        query.setParameter("name", name);
        query.setParameter("price", price);
        query.setParameter("qty", qty);
        query.executeUpdate();
    }

    public List<Product> findAll() {
        Query query = em.createNativeQuery("select * from product_tb", Product.class);
        List<Product> productList = query.getResultList();
        // = executequery();
        return productList;
    }

    // int id -> 클라이언트로 부터 받은 id를 Controller가 여기로 가지고 와준다.
    public Product findById(int id) {
        Query query = em.createNativeQuery("select * from product_tb where id = :id", Product.class);
        query.setParameter("id", id);
        Product product = (Product) query.getSingleResult();
        // getSingleResult() : Object 타입으로, 다운캐스팅을 해줘야한다.
        return product;
    }

    // 상품삭제
    @Transactional
    public void deleteById(int id) {
        Query query = em.createNativeQuery("delete from product_tb where id = :id", Product.class);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    // 상품 수정
    @Transactional
    public void update(String name, int price, int qty, int id ){
        Query query = em.createNativeQuery("update product_tb set name = :name, price = :price, qty = :qty where id = :id", Product.class);
        query.setParameter("name", name);
        query.setParameter("price", price);
        query.setParameter("qty", qty);
        query.setParameter("id", id);
        query.executeUpdate();

    }
}
