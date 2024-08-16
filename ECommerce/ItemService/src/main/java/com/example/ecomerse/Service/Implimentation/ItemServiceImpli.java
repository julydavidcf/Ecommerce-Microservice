package com.example.ecomerse.Service.Implimentation;

import com.example.ecomerse.Dao.ItemsRepository;
import com.example.ecomerse.Entity.Item;
import com.example.ecomerse.Exception.ItemNotFoundException;
import com.example.ecomerse.Payload.ItemDTO;
import com.example.ecomerse.Service.ItemService;
import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        List<Item> items = this.itemsRepository.findAll();
        List<ItemDTO> response = items.stream().map(item -> itemToItemDTO(item)).collect(Collectors.toList());
        return response;
    }

    @Override
    public ItemDTO getItemById(String id) {
        Item item = this.itemsRepository.findItemByItemId(id);
        if(item == null) {
            throw new ItemNotFoundException(id);
        }
        return itemToItemDTO(item);

    }

    @Override
    public void deleteItem(String id) {
        long result = this.itemsRepository.deleteItemByItemId(id);
        if (result == 0) {
            throw new ItemNotFoundException(id);
        }
    }

    @Override
    public ItemDTO updateInventory(String id, int units) {
        Item oldItem = this.itemsRepository.findItemByItemId(id);
        if(oldItem == null) {
            throw new ItemNotFoundException(id);
        }
        oldItem.setAvailableUnits(oldItem.getAvailableUnits() + units);
        this.itemsRepository.save(oldItem);
        return itemToItemDTO(oldItem);
    }

    @Override
    public List<ItemDTO> getItemsByCategory(String category) {
        List<Item> items = this.itemsRepository.findItemsByCategory(category);
        List<ItemDTO> response = items.stream().map(item -> itemToItemDTO(item)).collect(Collectors.toList());
        return response;
    }

    @Override
    public boolean checkInventory(String itemId, int requestAmount) {
        Item item = this.itemsRepository.findItemByItemId(itemId);
        return item!=null && item.getAvailableUnits() >= requestAmount;
    }

    private ItemDTO itemToItemDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO(item.getCategory(), item.getAvailableUnits(), item.getImageUrl(), item.getUpc(), item.getPrice(), item.getDescription(), item.getName(), item.getItemId());
        return itemDTO;
    }
}
