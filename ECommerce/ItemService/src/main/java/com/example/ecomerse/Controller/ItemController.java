package com.example.ecomerse.Controller;

import com.example.ecomerse.Entity.Item;
import com.example.ecomerse.Payload.ItemDTO;
import com.example.ecomerse.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/v1/items"})
public class ItemController {
    @Autowired
    private ItemService itemService;

    @PostMapping
    public ResponseEntity<Item> addItem(@RequestBody ItemDTO itemDTO) {
        ItemDTO response = this.itemService.createItem(itemDTO);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }

}
