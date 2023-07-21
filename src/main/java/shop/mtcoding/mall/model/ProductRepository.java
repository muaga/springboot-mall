package shop.mtcoding.mall.model;

// DAO = Repository
// DAO는 DB에서만 접근이 가능해서, 조금 더 넒은 의미인 Repository를 쓴다.

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository // 컴포넌트 스캔
public class ProductRepository {

    @Autowired
    // 의존성 주입
    private EntityManager em;
    // entityManager : entity 객체의 영속성 관리와 데이터베이스 트랜잭션 관리를 담당한다
    // Entity만 매핑해준다. (Entity = table) -> 매핑이 되면, 클래스.class가 가능하고, 안되면 직접 매핑해야 한다.
    // 이런 경우는, 테이블이 없는 DTO에 매핑할 때 발생하는 경우이다.

    // seller와 join 하기
    public Product findByIdJoinSeller(int id) {
        Query query = em.createNativeQuery("select * \n" +
                "from product_tb as p \n" +
                "inner join seller_tb as s \n" +
                "on p.seller_id = s.id \n" +
                "where s.id = :id", Product.class);
        query.setParameter("id", id);
        Product product = (Product) query.getSingleResult();
        return product;
    }

    // 추가된 seller_id까지 함께 상품 등록
    @Transactional
    public void saveWithFK(String name, int price, int qty, int sellerId){
        Query query = em.createNativeQuery("insert into product_tb(name, price, qty, seller_id) values(:name, :price, :qty, :sellerId)");
        query.setParameter("name", name);
        query.setParameter("price", price);
        query.setParameter("qty", qty);
        query.setParameter("sellerId", sellerId);
        query.executeUpdate();
    }

    // 상품 상세보기에서, model에는 없는 데이터 매핑하기
    public ProductDTO findByIdDTO(int id) {
        Query query = em.createNativeQuery("select id, name, price, qty, '설명' des from product_tb where id = :id");
        // product에 매핑을 하면 des를 담지 못해서, DTO에 담을 것이다.
        query.setParameter("id", id);

        // 직접 오브젝트 매핑하기 - QLRM
        JpaResultMapper mapper = new JpaResultMapper();
        ProductDTO productDTO = mapper.uniqueResult(query, ProductDTO.class);

        return productDTO;
    }

    // 상품등록
    @Transactional
    public void save(String name, int price, int qty) {
        Query query = em.createNativeQuery("insert into product_tb(name, price, qty) values(:name, :price, :qty)");
        // query : 프로토콜이 된 buffer의 역할.
        query.setParameter("name", name);
        query.setParameter("price", price);
        query.setParameter("qty", qty);
        query.executeUpdate();
    }

    //  상품조회
    public List<Product> findAll() {
        Query query = em.createNativeQuery("select * from product_tb", Product.class);
        List<Product> productList = query.getResultList();
        // = executequery();
        return productList;

        // * product.class -> select할 때만 매핑한다.
    }

    // 상품상세조회
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
        Query query = em.createNativeQuery("delete from product_tb where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    // 상품수정
    @Transactional
    public void update(String name, int price, int qty, int id) {
        Query query = em.createNativeQuery("update product_tb set name = :name, price = :price, qty = :qty where id = :id");
        query.setParameter("name", name);
        query.setParameter("price", price);
        query.setParameter("qty", qty);
        query.setParameter("id", id);
        query.executeUpdate();

    }
}
