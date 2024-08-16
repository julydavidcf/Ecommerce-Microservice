document.getElementById('createItemForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const itemName = document.getElementById('itemName').value;
    const itemDescription = document.getElementById('itemDescription').value;
    const itemPrice = document.getElementById('itemPrice').value;
    const itemUpc = document.getElementById('itemUpc').value;
    const itemImageUrl = document.getElementById('itemImageUrl').value;
    const itemAvailableUnits = parseInt(document.getElementById('itemAvailableUnits').value, 10);
    const itemCategory = document.getElementById('itemCategory').value;

    try {
        const response = await fetch('http://localhost:8080/api/v1/items', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                name: itemName,
                description: itemDescription,
                price: itemPrice,
                upc: itemUpc,
                imageUrl: itemImageUrl,
                availableUnits: itemAvailableUnits,
                category: itemCategory
            }),
        });

        if (response.ok) {
            document.getElementById('createItemMessage').innerText = 'Item created successfully!';
            document.getElementById('createItemForm').reset();  // Clear form fields
        } else {
            document.getElementById('createItemMessage').innerText = 'Error creating item.';
        }
    } catch (error) {
        document.getElementById('createItemMessage').innerText = 'An error occurred.';
        console.error('Error:', error);
    }
});

// Redirect to the main page
document.getElementById('returnToMainPage').addEventListener('click', () => {
    window.location.href = 'index.html';
});