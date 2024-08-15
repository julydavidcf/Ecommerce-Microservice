document.getElementById('changePasswordForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const email = document.getElementById('email').value;
    const oldPassword = document.getElementById('oldPassword').value;
    const newPassword = document.getElementById('newPassword').value;

    try {
        // Step 1: Get account details by email
        const accountResponse = await fetch(`/api/accounts/email?email=${encodeURIComponent(email)}`);

        if (!accountResponse.ok) {
            document.getElementById('changePasswordMessage').innerText = 'Account not found.';
            return;
        }

        const account = await accountResponse.json();

        // Step 2: Verify the old password
        const verifyResponse = await fetch('/api/accounts/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ email, password: oldPassword }),
        });

        if (!verifyResponse.ok) {
            document.getElementById('changePasswordMessage').innerText = 'Old password is incorrect.';
            return;
        }

        // Step 3: Change the password
        const changePasswordResponse = await fetch(`/api/accounts/${account.id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ password: newPassword }),
        });

        if (changePasswordResponse.ok) {
            alert('Password changed successfully!');
            window.location.href = 'index.html'; // Redirect to the main page
        } else {
            document.getElementById('changePasswordMessage').innerText = 'Error changing password.';
        }
    } catch (error) {
        document.getElementById('changePasswordMessage').innerText = 'An error occurred.';
        console.error('Error:', error);
    }
});