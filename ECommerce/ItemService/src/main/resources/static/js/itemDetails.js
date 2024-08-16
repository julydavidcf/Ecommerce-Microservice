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
            // Fetch the user's payment information to get the balance
            const paymentResponse = await fetch(`http://localhost:8082/api/v1/payments/user/${userId}`);
            const payment = await paymentResponse.json();

            let userBalance = 'Unavailable';
            if (paymentResponse.ok) {
                userBalance = payment.amount;
            } else {
                console.error('Failed to fetch user balance:', paymentResponse.status);
            }

            const itemDetails = document.getElementById('itemDetails');
            itemDetails.innerHTML = `
                <h2>${item.name}</h2>
                <img src="${item.imageUrl}" alt="${item.name}" width="200" height="200">
                <p><strong>Description:</strong> ${item.description}</p>
                <p><strong>Price:</strong> $${item.price}</p>
                <p><strong>UPC:</strong> ${item.upc}</p>
                <p><strong>Available Units:</strong> ${item.availableUnits}</p>
                <p><strong>Category:</strong> ${item.category}</p>
                <button id="createOrderButton">Create Order</button>
                <button id="addFundsButton">Add Funds</button>
            `;

            // Display user information with balance
            const userInfoDiv = document.getElementById('userInfo');
            userInfoDiv.innerHTML = `
                <p>User Name: ${username}</p>
                <p>Balance: $${userBalance}</p>
            `;

            // Set up the back button with userId and userName
            const backButton = document.getElementById('backButton');
            backButton.addEventListener('click', () => {
                window.location.href = `itemList.html?userId=${encodeURIComponent(userId)}&userName=${encodeURIComponent(username)}`;
            });

            // Set up the create order button
            const createOrderButton = document.getElementById('createOrderButton');
            createOrderButton.addEventListener('click', async () => {
                if (parseFloat(item.price) > parseFloat(userBalance)) {
                    alert('Insufficient balance to place the order.');
                    return;
                }

                const confirmOrder = confirm("Do you want to place this order?");
                if (confirmOrder) {
                    const orderData = {
                        userId: userId,
                        itemIds: [itemId], // Assuming a single item for simplicity
                        totalAmount: item.price,
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

                        const orderResult = await orderResponse.json(); // Parse the response JSON

                        if (orderResponse.ok && orderResult.status === "CREATED") {
                            alert('Order placed successfully!');

                            // Fetch the updated user balance from the database
                            const updatedPaymentResponse = await fetch(`http://localhost:8082/api/v1/payments/user/${userId}`);
                            if (updatedPaymentResponse.ok) {
                                const updatedPayment = await updatedPaymentResponse.json();
                                userBalance = updatedPayment.amount;
                                userInfoDiv.innerHTML = `
                                    <p>User Name: ${username}</p>
                                    <p>Balance: $${userBalance}</p>
                                `;
                                // alert('Balance updated successfully!');
                            } else {
                                console.error('Error fetching updated balance:', updatedPaymentResponse.status);
                            }

                            // Fetch the updated item inventory from the database
                            const updatedItemResponse = await fetch(`http://localhost:8080/api/v1/items/${itemId}`);
                            if (updatedItemResponse.ok) {
                                const updatedItem = await updatedItemResponse.json();
                                itemDetails.innerHTML = `
                                    <h2>${updatedItem.name}</h2>
                                    <img src="${updatedItem.imageUrl}" alt="${updatedItem.name}" width="200" height="200">
                                    <p><strong>Description:</strong> ${updatedItem.description}</p>
                                    <p><strong>Price:</strong> $${updatedItem.price}</p>
                                    <p><strong>UPC:</strong> ${updatedItem.upc}</p>
                                    <p><strong>Available Units:</strong> ${updatedItem.availableUnits}</p>
                                    <p><strong>Category:</strong> ${updatedItem.category}</p>
                                    <button id="createOrderButton">Create Order</button>
                                    <button id="addFundsButton">Add Funds</button>
                                `;
                                // alert('Inventory updated successfully!');
                            } else {
                                console.error('Error fetching updated inventory:', updatedItemResponse.status);
                            }

                        } else {
                            alert('Error placing order: ' + orderResult.message);
                        }
                    } catch (error) {
                        console.error('Error placing order:', error);
                    }
                }
            });

            // Set up the add funds button
            const addFundsButton = document.getElementById('addFundsButton');
            addFundsButton.addEventListener('click', async () => {
                const addAmount = prompt("Enter the amount to add to your balance:");
                if (addAmount && !isNaN(addAmount) && parseFloat(addAmount) > 0) {
                    try {
                        const addFundsResponse = await fetch(`http://localhost:8082/api/v1/payments/user/${userId}?addAmount=${encodeURIComponent(addAmount)}`, {
                            method: 'PUT',
                            headers: {
                                'Content-Type': 'application/json',
                            }
                        });

                        if (addFundsResponse.ok) {
                            const updatedPayment = await addFundsResponse.json();
                            userBalance = updatedPayment.amount;
                            userInfoDiv.innerHTML = `
                                <p>User Name: ${username}</p>
                                <p>Balance: $${userBalance}</p>
                            `;
                            alert('Funds added successfully!');
                        } else {
                            const result = await addFundsResponse.json();
                            alert('Error adding funds: ' + result.message);
                        }
                    } catch (error) {
                        console.error('Error adding funds:', error);
                    }
                } else {
                    alert("Please enter a valid amount.");
                }
            });

        } else {
            console.error('Failed to fetch item details:', response.status);
        }
    } catch (error) {
        console.error('Error fetching item details:', error);
    }
});