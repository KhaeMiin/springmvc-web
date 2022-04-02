package hello.itemservice.web.form;

import hello.itemservice.domain.item.DeliveryCode;
import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.domain.item.ItemType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/form/items")
@RequiredArgsConstructor
public class FormItemController {

    private final ItemRepository itemRepository;

    @ModelAttribute("regions") //컨트롤러가 호출되면 regions를 이름으로 모델에 자동으로 무조건 담긴다. (단점: r계속 호출이 돼서 생성이 되니 성능 생각해야함)
    public Map<String, String> regions() {
        Map<String, String> regions = new LinkedHashMap<>();//LinkedHashMap: 순서대로 들어감
        regions.put("SEOUL", "서울");
        regions.put("BUSAN", "부산");
        regions.put("JEJU", "제주");
        return regions;

    }

    @ModelAttribute("itemTypes")
    public ItemType[] itemTypes() {
        ItemType[] values = ItemType.values(); //배열로 넘겨줌
        return values;
//        return ItemType.values(); //이렇게 간략하게 한줄로 쓸 수 있음

    }

    @ModelAttribute("deliveryCodes")
    public List<DeliveryCode> deliveryCodes() {
        List<DeliveryCode> deliveryCode = new ArrayList<>();
        deliveryCode.add(new DeliveryCode("FAST", "빠른 배송"));
        deliveryCode.add(new DeliveryCode("NORMAL", "일반 배송"));
        deliveryCode.add(new DeliveryCode("SLOW", "느린 배송"));
        return deliveryCode;
    }

    @GetMapping
    public String items(Model model) {

        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "form/items";
    }

    /**
     * PathVariable 사용 (쿼리 파라미터 방식과 다른 방식)
     * 변수명이 같으면 생략 가능
     * @PathVariable long itemId
     * "/itemA"
     */
    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "form/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());

        return "/form/addForm";
    }

//    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model
    ) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);

        return "/form/item";
    }

//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item) {

        itemRepository.save(item);

//        model.addAttribute("item", item); //@ModelAttribute("item"):자동으로 모델에 넣어준다.("지정된 이름")으로

        return "/form/item";
    }

//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item) {

        itemRepository.save(item);

//        model.addAttribute("item", item); //@ModelAttribute: 지정된 이름이 없으면 클래스명 첫글자만 소문자로 바꿔서 저장된다.

        return "/form/item";
    }

//    @PostMapping("/add")
    public String addItemV4(Item item) {

        itemRepository.save(item);
//        model.addAttribute("item", item); //@ModelAttribute: 생략이 된다.
        return "/form/item";
    }

//    @PostMapping("/add")
    public String addItemV5(Item item) {
        itemRepository.save(item);
        return "redirect:/form/items/" + item.getId();
    }

    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
        log.info("item.open={}", item.getOpen());
        log.info("item.regions={}", item.getRegions());
        log.info("item.itemType={}", item.getItemType());
        log.info("item.deliveryCode={}", item.getDeliveryCode());

        Item saveItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", saveItem.getId());
        redirectAttributes.addAttribute("status",true); //쿼리 파라미터로 들어감
        return "redirect:/form/items/{itemId}"; //redirectAttributes로 넣은 itemId가 여기로 들어감
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "/form/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/form/items/{itemId}";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct//시작시 의존관계 주입 후 실행
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemA", 20000, 20));
    }



}
