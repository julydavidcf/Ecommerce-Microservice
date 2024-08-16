document.addEventListener('DOMContentLoaded', () => {
    const urlParams = new URLSearchParams(window.location.search);
    const userId = urlParams.get('userId');
    const username = urlParams.get('userName');

    if (userId && username) {
        const userInfoDiv = document.getElementById('userInfo');
        userInfoDiv.innerHTML = `<p>Welcome, ${username}!</p>`;
    } else {
        console.error('User information not found in the URL');
    }

    let currentPage = 1;
    const itemsPerPage = 4;

    // Check that the pagination controls exist before trying to add event listeners
    const prevPageButton = document.getElementById('prevPage');
    const nextPageButton = document.getElementById('nextPage');
    const paginationControls = document.getElementById('paginationControls');

    if (prevPageButton && nextPageButton && paginationControls) {
        prevPageButton.addEventListener('click', () => {
            if (currentPage > 1) {
                currentPage--;
                fetchItems(userId, username, currentPage, itemsPerPage);
            }
        });

        nextPageButton.addEventListener('click', () => {
            currentPage++;
            fetchItems(userId, username, currentPage, itemsPerPage);
        });

        fetchItems(userId, username, currentPage, itemsPerPage);
    } else {
        console.error('Pagination controls not found in the DOM');
    }
});

async function fetchItems(userId, userName, page, itemsPerPage) {
    try {
        const response = await fetch('http://localhost:8080/api/v1/items');
        const items = await response.json();

        const itemList = document.getElementById('itemList');
        const paginationControls = document.getElementById('paginationControls');

        itemList.innerHTML = '';

        if (Array.isArray(items) && items.length > 0) {
            const startIndex = (page - 1) * itemsPerPage;
            const endIndex = Math.min(startIndex + itemsPerPage, items.length);

            for (let i = startIndex; i < endIndex; i++) {
                const item = items[i];
                const listItem = document.createElement('li');
                listItem.innerHTML = `
                    <img src="${item.imageUrl}" alt="${item.name}" width="120" height="auto">
                    <strong>${item.name}</strong> - Price: $${item.price}
                    <a href="itemDetails.html?id=${item.itemId}&userId=${encodeURIComponent(userId)}&userName=${encodeURIComponent(userName)}">View Details</a>
                `;
                itemList.appendChild(listItem);
            }

            if (paginationControls) {
                paginationControls.style.display = 'block';
                document.getElementById('pageNumber').innerText = `Page ${page}`;
            }
        } else {
            itemList.innerText = 'No items found.';
            if (paginationControls) {
                paginationControls.style.display = 'none';
            }
        }
    } catch (error) {
        console.error('Error fetching items:', error);
    }
}