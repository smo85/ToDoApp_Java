const listsUl = document.getElementById('listsUl');
const refreshBtn = document.getElementById('refreshListsBtn');
const listTitle = document.getElementById('listTitle');
const listDescription = document.getElementById('listDescription');
const itemsTable = document.getElementById('itemsTable');
const itemsTbody = document.getElementById('itemsTbody');

async function fetchJson(url) {
    const res = await fetch(url);
    if (!res.ok) throw new Error(`Request failed: ${res.status}`);
    return res.json();
}

async function loadLists() {
    listsUl.innerHTML = '<li>Loading...</li>';
    try {
        const lists = await fetchJson('/to-do-lists');
        if (!Array.isArray(lists) || lists.length === 0) {
            listsUl.innerHTML = '<li>No lists found</li>';
            return;
        }
        listsUl.innerHTML = '';
        lists.forEach(list => {
            const li = document.createElement('li');
            li.textContent = list.title || list.id;
            li.className = 'list-item';
            li.onclick = () => loadListDetails(list.id);
            listsUl.appendChild(li);
        });
    } catch (e) {
        listsUl.innerHTML = `<li class="error">${e.message}</li>`;
    }
}

async function loadListDetails(id) {
    listTitle.textContent = 'Loading...';
    listDescription.textContent = '';
    itemsTbody.innerHTML = '';
    itemsTable.classList.add('hidden');
    try {
        const list = await fetchJson(`/to-do-lists/${encodeURIComponent(id)}`);
        listTitle.textContent = list.title || '(Untitled)';
        listDescription.textContent = list.description || '';
        if (Array.isArray(list.toDoItems) && list.toDoItems.length > 0) {
            itemsTbody.innerHTML = '';
            list.toDoItems.forEach(item => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
          <td>${item.id ?? ''}</td>
            <td>${escapeHtml(item.title || '')}</td>
          <td>${item.completed ? '✔' : '✗'}</td>
        `;
                itemsTbody.appendChild(tr);
            });
            itemsTable.classList.remove('hidden');
        } else {
            itemsTbody.innerHTML = '<tr><td colspan="3"><em>No items</em></td></tr>';
            itemsTable.classList.remove('hidden');
        }
    } catch (e) {
        listTitle.textContent = 'Error loading list';
        listDescription.textContent = e.message;
    }
}

function escapeHtml(str) {
    return str.replace(/[&<>"']/g, c =>
        ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c])
    );
}

refreshBtn.addEventListener('click', loadLists);
document.addEventListener('DOMContentLoaded', loadLists);