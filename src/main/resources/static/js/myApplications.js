
document.addEventListener('DOMContentLoaded', async () => {
    const loginMsg = document.getElementById('login-message');
    const appList = document.getElementById('application-list');
    const logoutBtn = document.getElementById('logout-btn');

    let userOk = false;
    try {
        const res = await fetch('/user', { credentials: 'include' });
        if (res.ok && await res.json()) {
            userOk = true;
            logoutBtn.style.display = '';
        }
    } catch {}
    if (!userOk) {
        loginMsg.style.display = '';
        appList.style.display = 'none';
        return;
    }
    loginMsg.style.display = 'none';
    appList.style.display = '';

    logoutBtn.addEventListener('click', async () => {
        await fetch('/logout', { method: 'POST', credentials: 'include' });
        window.location.href = 'index.html';
    });

    try {
        const apps = await ApplicationAPI.getMyApplications();
        if (!apps.length) {
            appList.innerHTML = '<p>No applications found.</p>';
            return;
        }
        appList.innerHTML = apps.map(app => `
            <div class="application-card">
                <h4>${app.pet?.name || 'Unknown Pet'}</h4>
                <p><strong>City:</strong> ${app.city}</p>
                <p><strong>Status:</strong> ${app.status}</p>
            </div>
        `).join('');
    } catch {
        appList.innerHTML = '<p>Could not load your applications.</p>';
    }
});

function goBackHome() {
    window.location.href = 'index.html';
}
