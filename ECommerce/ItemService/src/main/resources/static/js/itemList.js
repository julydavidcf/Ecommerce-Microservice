document.addEventListener('DOMContentLoaded', () => {
    // Parse the query parameters from the URL
    const urlParams = new URLSearchParams(window.location.search);
    const userId = urlParams.get('userId');
    const username = urlParams.get('userName');

    if (userId && username) {
        // Display the user information
        const userInfoDiv = document.getElementById('userInfo');
        userInfoDiv.innerHTML = `
            <p>Welcome, ${username}!</p>
        `;
    } else {
        console.error('User information not found in the URL');
    }

    // Fetch and display the item list
    fetchItems(userId, username);  // Pass userId and userName to the fetchItems function
});

async function fetchItems(userId, userName) {
    try {
        const response = await fetch('http://localhost:8080/api/v1/items');
        const items = await response.json();

        const itemList = document.getElementById('itemList');
        itemList.innerHTML = '';

        if (Array.isArray(items)) {
            items.forEach(item => {
                const listItem = document.createElement('li');
                listItem.innerHTML = `
                    <img src="${item.imageUrl}" alt="${item.name}" width="120" height="auto">
                    <strong>${item.name}</strong> - Price: $ ${item.price}
                    <a href="itemDetails.html?id=${item.itemId}&userId=${encodeURIComponent(userId)}&userName=${encodeURIComponent(userName)}">View Details</a>
                `;
                itemList.appendChild(listItem);
            });
        } else {
            itemList.innerText = 'No items found.';
        }
    } catch (error) {
        console.error('Error fetching items:', error);
    }
}