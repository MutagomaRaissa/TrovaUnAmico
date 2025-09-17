
document.addEventListener('DOMContentLoaded', async function() {
    const loginMsg = document.getElementById('login-message');
    const formContainer = document.getElementById('application-form-container');
    const logoutBtn = document.getElementById('logout-btn');
    const form = document.getElementById('application-form');
    const petTitle = document.getElementById('pet-title');
    const petIdInput = document.getElementById('petId');

    // Auth check
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
        formContainer.style.display = 'none';
        return;
    }
    loginMsg.style.display = 'none';
    formContainer.style.display = '';

    // Logout logic
    logoutBtn.addEventListener('click', async () => {
        await fetch('/logout', { method: 'POST', credentials: 'include' });
        window.location.href = 'index.html';
    });

    // Get petId from query or localStorage
    const params = new URLSearchParams(window.location.search);
    let petId = params.get('id') || localStorage.getItem('selectedPetId');
    if (petId) {
        petIdInput.value = petId;
        // Fetch pet name for title
        if (window.PetAPI && PetAPI.getPetById) {
            try {
                const pet = await PetAPI.getPetById(petId);
                petTitle.textContent = `Apply for ${pet.name}`;
            } catch {
                petTitle.textContent = 'Apply for Adoption';
            }
        }
    }

    // Form submit
    form.addEventListener('submit', async function(e) {
        e.preventDefault();
        const data = {
            petId: petIdInput.value,
            firstName: form.firstName.value,
            lastName: form.lastName.value,
            email: form.email.value,
            phoneNumber: form.phoneNumber.value,
            city: form.city.value,
            description: form.description.value
        };
        try {
            const resp = await fetch(`/api/applications/pet/${data.petId}`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                credentials: 'include',
                body: JSON.stringify(data)
            });
            if (resp.ok) {
                window.location.href = 'thank-you.html';
            } else if (resp.status === 401) {
                alert('You must be logged in to apply.');
                window.location.href = '/oauth2/authorization/google?redirect_uri=application.html';
            } else {
                alert('Failed to submit application.');
            }
        } catch {
            alert('Failed to submit application.');
        }
    });
});
document.getElementById('back-btn').addEventListener('click', function() {
    window.history.length > 1 ? window.history.back() : window.location.href = 'index.html';
});