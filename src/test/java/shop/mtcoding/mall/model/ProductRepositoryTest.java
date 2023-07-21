package shop.mtcoding.mall.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@Import({
        ProductRepository.class,
        SellerRepositoty.class
}) // Test할 클래스를 Import하기
@DataJpaTest // 톰캣 -> DS -> Controller -> ( Repositpry -> DB )
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SellerRepositoty sellerRepositoty;

    // findById를 테스트하기 위해, 더미데이터 만들기
    // 1. h2 넣고 (가짜 DB를 생성한 후)
    // 2. 테스트하고 싶은 클래스의 파일 @Autowired
    // 3. 테스트 클래스 _ test 진행
    // 테스트는 메소드가 끝날 때, 롤백을 시켜줘야 한다.
    // 아래의 테스트를 단위테스트라고 한다.

    // seller_id 포함 상품등록 테스트
    @Test
    public void saveWithFk_test(){
        // given
        sellerRepositoty.save("홍길동", "dddd");

        // when
        productRepository.saveWithFK("딸기", 10000, 12, 1);

        // then
        Product product = productRepository.findById(1);
        System.out.println(product.getId());
    }

    // product - seller join(ORM)
    @Test
    public void findByIdJoinSeller_test(){
        //given
        sellerRepositoty.save("이은지", "eunji@naver.com");
        productRepository.saveWithFK("블루베리", 5000, 1, 1);

        // when
        Product product = productRepository.findByIdJoinSeller(1);

        // then
        System.out.println(product.getSeller().getName());
        System.out.println(product.getName());
    }





    @Test
    public void findByIdDTO_test(){
        // given
        productRepository.save("바나나", 5000, 50);

        ProductDTO productDTO = productRepository.findByIdDTO(1);

        System.out.println(productDTO.getName());
        System.out.println(productDTO.getPrice());
        System.out.println(productDTO.getQty());
        System.out.println(productDTO.getDes());
    }



    @Test
    public void findById_test() {
        // given (테스트 하기 위해서 필요한 데이터 만들기) - h2로 가짜 DB 데이터 생성
        productRepository.save("바나나", 5000, 50);

        // when (테스트 진행)
        Product product = productRepository.findById(1);

        // then (테스트 확인)
        System.out.println(product.getId());
        System.out.println(product.getName());
        System.out.println(product.getPrice());
        System.out.println(product.getQty());

    }

    @Test
    public void findAll_test(){
        // given
        productRepository.save("바나나", 5000, 50);
        productRepository.save("딸기", 5000, 50);

        // when
        List<Product> productList = productRepository.findAll();

        // then
        for (Product product : productList) {
            System.out.println("==================");
            System.out.println(product.getId());
            System.out.println(product.getName());
            System.out.println(product.getPrice());
            System.out.println(product.getQty());

        }
    }
}
