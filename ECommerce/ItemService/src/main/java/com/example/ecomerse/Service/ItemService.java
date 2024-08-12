package com.example.ecomerse.Service;

import com.example.ecomerse.Payload.ItemDTO;

import java.util.List;

public interface ItemService {
    ItemDTO createItem(ItemDTO itemDTO);

    List<ItemDTO> getAllItems();

    ItemDTO getItemById(int id);

    void deleteItem(int id);

    ItemDTO updateInventory(long id, int units);

    List<ItemDTO> getItemsByCategory(String category);

    public boolean checkInventory(Long itemId);

}
