package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {
    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items",items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable("itemId") long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

    //@PostMapping("/add")
    public String addItemV1(@RequestParam("itemName") String itemName,
                            @RequestParam("price") int price,
                            @RequestParam("quantity") Integer quantity,
                            Model model){
        Item item = new Item(itemName, price, quantity);
        itemRepository.save(item);
        model.addAttribute("item",item);
        return "basic/item";
    }

    //@PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item){
        itemRepository.save(item);
        //model.addAttribute("item",item); 자동으로 이거까지 해줌 오오오..
        return "basic/item";
    }

   // @PostMapping("/add")
    public String addItemV3(Item item){ //String 이런거 말고는 디폴트로 모델 어트리뷰트가 적용된다고 했엇음.
        itemRepository.save(item);
        //model.addAttribute("item",item); 자동으로 이거까지 해줌 오오오..
        return "basic/item";
    }
    //@PostMapping("/add")
    public String addItemRedirect(Item item){
        itemRepository.save(item);

        return "redirect:/basic/items/"+item.getId();
    }

    @PostMapping("/add")
    public String addItemRedirectV2(Item item, RedirectAttributes redirectAttributes){
        Item save = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", save.getId());
        redirectAttributes.addAttribute("status", true);
        // 리다이렉트 url에 안들어간 어트리뷰트는 쿼리 파라미터로 들어감 ?status=true

        return "redirect:/basic/items/{itemId}";
    }



    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable("itemId") Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return  "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(Item item){
        itemRepository.update(item.getId(),item);
        return  "redirect:/basic/items/{itemId}";
    }

    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
