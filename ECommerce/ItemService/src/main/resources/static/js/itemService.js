const API_BASE_URL = 'http://localhost:8080/api/v1/items';

function createItem(item) {
    return fetch(`${API_BASE_URL}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(item)
    })
        .then(response => response.json());
}

function loadItems() {
    return fetch(`${API_BASE_URL}`)
        .then(response => response.json());
}

function getItemById(itemId) {
    return fetch(`${API_BASE_URL}/${itemId}`)
        .then(response => response.json());
}

function deleteItemById(itemId) {
    return fetch(`${API_BASE_URL}/${itemId}`, {
        method: 'DELETE'
    });
}

function updateInventory(itemId, units) {
    return fetch(`${API_BASE_URL}/${itemId}/inventory`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ units: units })
    })
        .then(response => response.json());
}