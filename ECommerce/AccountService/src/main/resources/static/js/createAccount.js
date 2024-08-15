document.getElementById('createAccountForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const data = {
        email: document.getElementById('email').value,
        username: document.getElementById('username').value,
        password: document.getElementById('password').value,
        shippingAddress: document.getElementById('shippingAddress').value,
        billingAddress: document.getElementById('billingAddress').value,
        paymentMethod: document.getElementById('paymentMethod').value,
    };

    const response = await fetch('/api/accounts', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    });

    const result = await response.json();
    if (response.ok) {
        alert('Account created successfully!');
        window.location.href = 'index.html'; // Redirect to the main page
    } else {
        document.getElementById('createAccountMessage').innerText = 'Error creating account: ' + result.message;
    }
});