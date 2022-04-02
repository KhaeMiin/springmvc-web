package hello.itemservice.domain.item;

import lombok.Data;

import java.util.List;

@Data//data를 쓰기엔 위험
//@Getter @Setter
public class Item {

    private  Long id;
    private String itemName;
    private Integer price;//Integer: 안들어갈 때도 있다고 가정
    private Integer quantity;//수량

    private Boolean open; //판매 여부
    private List<String> regions; //등록 지역
    private ItemType itemType; //상품 종류
    private String deliveryCode; //배송 방식

    public Item() {

    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
