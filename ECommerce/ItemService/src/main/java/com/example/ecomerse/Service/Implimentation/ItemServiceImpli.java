package com.example.ecomerse.Service.Implimentation;

import com.example.ecomerse.Dao.ItemsRepository;
import com.example.ecomerse.Entity.Item;
import com.example.ecomerse.Payload.ItemDTO;
import com.example.ecomerse.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class ItemServiceImpli implements ItemService {
    @Autowired
    private ItemsRepository itemsRepository;

    @Override
    public ItemDTO createItem(@RequestBody ItemDTO itemDTO) {
        Item newItem = new Item();
        newItem.setName(itemDTO.getName());
        newItem.setPrice(itemDTO.getPrice());
        newItem.setCategory(itemDTO.getCategory());
        newItem.setDescription(itemDTO.getDescription());
        newItem.setAvailableUnits(itemDTO.getAvailableUnits());
        newItem.setImageUrl(itemDTO.getImageUrl());
        newItem.setUpc(itemDTO.getUpc());

        Item savedItem = this.itemsRepository.save(newItem);
        ItemDTO response = itemToItemDTO(savedItem);

        return response;
    }

    @Override
    public List<ItemDTO> getAllItems() {
        return List.of();
    }

    @Override
    public ItemDTO getItemById(int id) {
        return null;
    }

    @Override
    public void deleteItem(int id) {

    }

    @Override
    public ItemDTO updateInventory(long id, int units) {
        return null;
    }

    @Override
    public List<ItemDTO> getItemsByCategory(String category) {
        return List.of();
    }

    @Override
    public boolean checkInventory(Long itemId) {
        return false;
    }

    private ItemDTO itemToItemDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO(item.getCategory(), item.getAvailableUnits(), item.getImageUrl(), item.getUpc(), item.getPrice(), item.getDescription(), item.getName(), item.getItemId());
        return itemDTO;
    }
}
