document.getElementById('loginForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const data = {
        email: document.getElementById('loginEmail').value,
        password: document.getElementById('loginPassword').value,
    };

    const response = await fetch('/api/accounts/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    });

    const result = await response.json(); // Assuming the API returns user data as JSON
    if (response.ok) {
        // Extract userId and username from the response
        const userId = result.user.id;
        const userName = result.user.username;

        // Redirect to the item list page with userId and username as query parameters
        window.location.href = `http://localhost:8080/itemList.html?userId=${encodeURIComponent(userId)}&userName=${encodeURIComponent(userName)}`;
    } else {
        document.getElementById('loginMessage').innerText = 'Login failed: ' + result.message;
    }
});