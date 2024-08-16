package com.example.ecomerse.Service;

import com.example.ecomerse.Payload.ItemDTO;

import java.util.List;

public interface ItemService {
    ItemDTO createItem(ItemDTO itemDTO);

    List<ItemDTO> getAllItems();

    ItemDTO getItemById(String id);

    void deleteItem(String id);

    ItemDTO updateInventory(String id, int units);

    List<ItemDTO> getItemsByCategory(String category);

    boolean checkInventory(String itemId,int requestAmount);

}
