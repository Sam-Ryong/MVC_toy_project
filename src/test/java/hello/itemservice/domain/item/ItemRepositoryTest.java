package hello.itemservice.domain.item;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {
    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach(){
        itemRepository.clearStore();
    }

    @Test
    void save(){
        Item item = new Item("itemA", 10000, 10);
        Item savedItem = itemRepository.save(item);
        Item findItem = itemRepository.findById(item.getId());
        org.assertj.core.api.Assertions.assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void findAll(){
        Item item1 = new Item("item1", 10000, 10);
        Item item2 = new Item("item2", 20000, 20);

        itemRepository.save(item1);
        itemRepository.save(item2);

        List<Item> result = itemRepository.findAll();

        org.assertj.core.api.Assertions.assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void updateItem(){
        Item item = new Item("item1", 10000, 10);

        Item savedItem = itemRepository.save(item);
        Long itemId = savedItem.getId();

        Item updateParam = new Item("item2", 20000, 20);
        itemRepository.update(itemId, updateParam);

        Item findItem = itemRepository.findById(itemId);
        org.assertj.core.api.Assertions.assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
    }

}