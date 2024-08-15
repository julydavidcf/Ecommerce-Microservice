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

    const result = await response.text();
    if (response.ok) {
        alert(result); // Typically, you'd handle a token or session here
        window.location.href = 'index.html'; // Redirect to the main page
    } else {
        document.getElementById('loginMessage').innerText = 'Login failed: ' + result;
    }
});
