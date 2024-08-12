package com.example.ecomerse.Payload;

public class ItemDTO {

    private String itemId;

    private String name;

    private String description;

    private String price;

    private String upc;

    private String imageUrl;

    private int availableUnits;

    private String category;

    public ItemDTO() {
    }

    public ItemDTO(String category, int availableUnits, String imageUrl, String upc, String price, String description, String name, String itemId) {
        this.category = category;
        this.availableUnits = availableUnits;
        this.imageUrl = imageUrl;
        this.upc = upc;
        this.price = price;
        this.description = description;
        this.name = name;
        this.itemId = itemId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getAvailableUnits() {
        return availableUnits;
    }

    public void setAvailableUnits(int availableUnits) {
        this.availableUnits = availableUnits;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
