document.getElementById('deleteAccountForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    // Show a confirmation dialog
    const userConfirmed = confirm("Are you sure you want to delete your account? This action cannot be undone.");

    if (!userConfirmed) {
        // User canceled the deletion
        return;
    }

    try {
        // Step 1: Verify the password using the login endpoint
        const verifyResponse = await fetch('/api/accounts/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ email, password }),
        });

        if (!verifyResponse.ok) {
            document.getElementById('deleteAccountMessage').innerText = 'Invalid email or password.';
            return;
        }

        // Step 2: Fetch the account ID using the email
        const accountResponse = await fetch(`/api/accounts/email?email=${encodeURIComponent(email)}`);

        if (!accountResponse.ok) {
            document.getElementById('deleteAccountMessage').innerText = 'Account not found.';
            return;
        }

        const account = await accountResponse.json();

        // Step 3: Delete the account using the account ID
        const deleteResponse = await fetch(`/api/accounts/${account.id}`, {
            method: 'DELETE',
        });

        if (deleteResponse.ok) {
            alert('Account deleted successfully!');
            window.location.href = 'index.html'; // Redirect to the main page
        } else {
            document.getElementById('deleteAccountMessage').innerText = 'Error deleting account.';
        }
    } catch (error) {
        document.getElementById('deleteAccountMessage').innerText = 'An error occurred.';
        console.error('Error:', error);
    }
});