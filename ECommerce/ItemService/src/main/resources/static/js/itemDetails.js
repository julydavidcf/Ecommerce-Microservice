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
                <button id="createOrderButton">Create Order</button> <!-- Added button to create order -->
            `;

            // Display user information
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

            // Set up the create order button
            const createOrderButton = document.getElementById('createOrderButton');
            createOrderButton.addEventListener('click', async () => {
                const confirmOrder = confirm("Do you want to place this order?");
                if (confirmOrder) {
                    // Calculate total amount (this is just a placeholder; adjust based on your requirements)
                    const totalAmount = item.price; // Assuming item.price is a BigDecimal value

                    const orderData = {
                        userId: userId,
                        itemIds: [itemId], // Assuming a single item for simplicity
                        totalAmount: totalAmount,
                        status: "Pending" // Initial status, adjust as necessary
                    };

                    try {
                        const orderResponse = await fetch('http://localhost:8081/api/v1/orders', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json',
                            },
                            body: JSON.stringify(orderData),
                        });

                        if (orderResponse.ok) {
                            alert('Order placed successfully!');
                            // Optionally redirect or update UI
                        } else {
                            alert('Error placing order!');
                        }
                    } catch (error) {
                        console.error('Error placing order:', error);
                    }
                }
            });

        } else {
            console.error('Failed to fetch item details:', response.status);
        }
    } catch (error) {
        console.error('Error fetching item details:', error);
    }
});