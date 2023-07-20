package shop.mtcoding.mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import shop.mtcoding.mall.model.Product;
import shop.mtcoding.mall.model.ProductRepository;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    // ProductRepository 클래스를 호출해서, 그 속의 메소드를 사용한다 = new


    @PostMapping("/product/update")
    public String update(String name, int price, int qty, int id) throws IOException {
        productRepository.update(name, price, qty, id);
        return "redirect:/";
    }

    @PostMapping("/product/delete")
    public String delete(int id) throws IOException {
        productRepository.deleteById(id);
        System.out.println("성공");
        return "redirect:/";
        // 버튼을 눌리고, 원래 페이지(home)로 돌아가려고 해서 "home"-Controller를 이용하려고 했는데
        // 아무 데이터도 안 뜬다면, request 객체가 없어서 데이터 자체가 null이기 때문이다.
        // 기존 페이지 ≠ 돌아간 페이지 다를 수가 있다는 것이다.

    }

    // 패스밸류 : {} 1~100까지 호출가능하다. /뒤에 들어오는 값을 자동으로 호출해준다.
    @GetMapping("/product/{id}")
    public String detail(@PathVariable int id, HttpServletRequest request){
        System.out.println("id : " + id);
        Product product = productRepository.findById(id);
        System.out.println(product.getId());
        System.out.println(product.getName());
        System.out.println(product.getPrice());
        System.out.println(product.getQty());
        request.setAttribute("p", product);
        // 각 id에 맞는 데이터를 화면에 보이려면, 일단 request 객체에 데이터를 저장하고 view를 실행해야 한다.
        // findById는 1행을 select하는 메소드로, List타입이 아닌 하나의 객체 Product라는 가방을 만들어서 여기에 request 객체(데이터)를 담는다.
        // request.setAttribute : request를 EL표기법으로 view하겠다
        // detail.jsp에서 c:if test : product를 해도 되고, setAttribute("p", product)해도 된다.
        return "detail";
        // 위의 내용을 detail.jsp랑 연결시켜서 view하려고 한다.
    }

    @GetMapping("/")
    public String home(HttpServletRequest request){
       List<Product> productList = productRepository.findAll();
        for (Product product : productList) {
            System.out.println(product.getId());
            System.out.println(product.getName());
        }
       // view는 home 파일을 모두 읽어서, 리턴하는 것. 즉 메소드 호출이 아니다.
        // 템플릿엔진을 사용해야 한다.
        // 1. request에 데이터를 담는다.
        // 2. home.jsp에 가서 request
        request.setAttribute("productList", productList);
        return "home";
    }

    @GetMapping("/write")
    public String writePage(){
        return "write";
        // return "write"는 @Controller로 리턴되는 파일인 write.jsp를 찾아낸다.
    }

    // * 엔드포인트가 같아도, method가 다르면 다른 기능을 한다.
    // 엔드포인트의 이름은, method의 기능과 관련되게 지어야 한다.
    @PostMapping("/product")
    public void write(String name, int price, int qty, HttpServletResponse response) throws IOException {
        // void를 하는 이유는, 모두 String 타입이 아니기 때문에
        // value는 매개변수랑 같은 타입 값을 넣어줘야 한다.
        System.out.println("name : " + name);
        System.out.println("price : " + price);
        System.out.println("qty : " + qty);
        // key는 정해놓은 key 이름과 요청하는 key 이름과 서로 일치하게 써야한다.

//        return "write";

        productRepository.save(name, price, qty);
        response.sendRedirect("/");
//      또다른 방법 : String 타입으로 변경 ->  return "redirect:/";
    }

}
