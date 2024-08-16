document.addEventListener('DOMContentLoaded', async () => {
    // Parse the query parameters from the URL
    const urlParams = new URLSearchParams(window.location.search);
    const itemId = urlParams.get('id');
    const userId = urlParams.get('userId');
    const username = urlParams.get('userName');

    if (!itemId || !userId || !username) {
        console.error('Item ID, User ID, or User Name not found in the URL');
        return;
    }

    try {
        // Fetch the item details using the itemId
        const response = await fetch(`http://localhost:8080/api/v1/items/${itemId}`);
        const item = await response.json();

        if (response.ok) {
            const itemDetails = document.getElementById('itemDetails');
            itemDetails.innerHTML = `
                <h2>${item.name}</h2>
                <img src="${item.imageUrl}" alt="${item.name}" width="200" height="200">
                <p><strong>Description:</strong> ${item.description}</p>
                <p><strong>Price:</strong> ${item.price}</p>
                <p><strong>UPC:</strong> ${item.upc}</p>
                <p><strong>Available Units:</strong> ${item.availableUnits}</p>
                <p><strong>Category:</strong> ${item.category}</p>
            `;

            // Optionally, display user information
            const userInfoDiv = document.getElementById('userInfo');
            userInfoDiv.innerHTML = `
                <p>User ID: ${userId}</p>
                <p>User Name: ${username}</p>
            `;

            // Set up the back button with userId and userName
            const backButton = document.getElementById('backButton');
            backButton.addEventListener('click', () => {
                window.location.href = `itemList.html?userId=${encodeURIComponent(userId)}&userName=${encodeURIComponent(username)}`;
            });
        } else {
            console.error('Failed to fetch item details:', response.status);
        }
    } catch (error) {
        console.error('Error fetching item details:', error);
    }
});