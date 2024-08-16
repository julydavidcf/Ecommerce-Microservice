package com.example.ecomerse.Controller;

import com.example.ecomerse.Entity.Item;
import com.example.ecomerse.Payload.ItemDTO;
import com.example.ecomerse.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemDTO> addItem(@RequestBody ItemDTO itemDTO) {
        ItemDTO response = this.itemService.createItem(itemDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ItemDTO>> getAllItems() {
        List<ItemDTO> response = this.itemService.getAllItems();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable(name = "id") String id) {
        ItemDTO response = this.itemService.getItemById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ItemDTO>> getItemsByCategory(@PathVariable(name = "category") String category) {
        List<ItemDTO> response = this.itemService.getItemsByCategory(category);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}/inventory")
    public ResponseEntity<Boolean> checkInventory(@PathVariable(name = "id") String id,@RequestParam int requestAmount) {
        boolean response = this.itemService.checkInventory(id,requestAmount);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemDTO> updateItem(@PathVariable(name = "id") String id, @RequestParam int addAmount) {
        ItemDTO response = this.itemService.updateInventory(id, addAmount);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable(name = "id") String id) {
        this.itemService.deleteItem(id);
        return new ResponseEntity<>("Item deleted", HttpStatus.OK);
    }
}
