package hello.itemservice.domain.item;

import lombok.Data;

@Data//data를 쓰기엔 위험
//@Getter @Setter
public class Item {

    private  Long id;
    private String itemName;
    private Integer price;//Integer: 안들어갈 때도 있다고 가정
    private Integer quantity;//수량

    public Item() {

    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
