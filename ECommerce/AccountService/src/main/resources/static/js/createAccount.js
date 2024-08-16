document.getElementById('createAccountForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    // Data from the form for account creation
    const accountData = {
        email: document.getElementById('email').value,
        username: document.getElementById('username').value,
        password: document.getElementById('password').value,
        shippingAddress: document.getElementById('shippingAddress').value,
        billingAddress: document.getElementById('billingAddress').value,
        paymentMethod: document.getElementById('paymentMethod').value,
    };

    // First, create the account
    let response = await fetch('/api/accounts', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(accountData),
    });

    let result = await response.json();
    if (response.ok) {
        alert('Account created successfully!');

        // Data for the payment creation
        const paymentData = {
            userId: result.id, // Assuming the API returns the userId of the newly created account
            paymentMethod: accountData.paymentMethod,
            amount: 1000.00
        };

        // Then, create the payment
        response = await fetch('http://localhost:8082/api/v1/payments', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(paymentData),
        });

        if (response.ok) {
            alert('Payment method added successfully!');
            window.location.href = 'index.html'; // Redirect to the main page
        } else {
            // Handle errors specifically for the payment creation
            result = await response.json();
            alert('Error adding payment method: ' + result.message);
        }

    } else {
        document.getElementById('createAccountMessage').innerText = 'Error creating account: ' + result.message;
    }
});