package site.metacoding.white.util;

import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
class Product {
    private Integer id;
    private String name;
    private Integer price;
    private Integer qty;
    private String mcp; // 제조사
}

@Setter // 데이터를 변경, history, 변화할 때 씀
@Getter
class ProductDto {
    private String name;
    private Integer price;
    private Integer qty;
}

public class MepperTest {

    @Test
    public void 매핑하기() {
        // 1. Produdct 객체생성 (디폴트)
        Product product = new Product();

        // 2. 값 넣기
        product.setId(1);
        product.setName("바나나");
        product.setPrice(1000);
        product.setQty(100);
        product.setMcp("삼성");

        // 3. ProdudctDto 객체생성 (디폴트)
        ProductDto productDto = new ProductDto();

        // 4. Product -> ProductDto로 옮기기
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setQty(product.getQty());

        // 5. ProductDto -> product 변경
        Product p2 = new Product();
        p2.setName(productDto.getName());
        p2.setPrice(productDto.getPrice());
        p2.setQty(productDto.getQty());
    }

}
