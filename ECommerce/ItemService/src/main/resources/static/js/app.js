document.getElementById('createItemForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const item = {
        name: document.getElementById('createName').value,
        description: document.getElementById('createDescription').value,
        // ... other fields
    };

    createItem(item).then(data => {
        alert('Item created successfully!');
        loadItems().then(displayItems);
    });
});

function displayItems(items) {
    const itemsList = document.getElementById('itemsList');
    itemsList.innerHTML = '';
    items.forEach(item => {
        const li = document.createElement('li');
        li.textContent = `${item.name} - ${item.price} - ${item.availableUnits} units`;
        itemsList.appendChild(li);
    });
}

// Load items on page load
window.onload = function() {
    loadItems().then(displayItems);
};