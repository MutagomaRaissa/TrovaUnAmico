document.addEventListener('DOMContentLoaded', async function() {
    const loginMsg = document.getElementById('login-message');
    const formContainer = document.getElementById('application-form-container');
    const logoutBtn = document.getElementById('logout-btn');
    const form = document.getElementById('application-form');
    const petTitle = document.getElementById('pet-title');
    const petIdInput = document.getElementById('petId');
    const emailInput = document.getElementById('email');

    // Check login
    let userOk = false;
    let userEmail = '';
    try {
        const res = await fetch('/user', { credentials: 'include' });
        if (res.ok) {
            const user = await res.json();
            if (user && user.email) {
                userOk = true;
                userEmail = user.email;
                emailInput.value = userEmail; // fill email
                logoutBtn.style.display = '';
            }
        }
    } catch {}

    if (!userOk) {
        loginMsg.style.display = '';
        formContainer.style.display = 'none';
        return;
    }

    loginMsg.style.display = 'none';
    formContainer.style.display = '';

    // Logout
    logoutBtn.addEventListener('click', async () => {
        await fetch('/logout', { method: 'POST', credentials: 'include' });
        window.location.href = 'index.html';
    });

    // Get petId from query params
    const params = new URLSearchParams(window.location.search);
    let petId = params.get('id') || localStorage.getItem('selectedPetId');
    if (petId) {
        petIdInput.value = petId;
        if (window.PetAPI && PetAPI.getPetById) {
            try {
                const pet = await PetAPI.getPetById(petId);
                petTitle.textContent = `Apply for ${pet.name}`;
            } catch {
                petTitle.textContent = 'Apply for Adoption';
            }
        }
    }

    // Submit application
    form.addEventListener('submit', async function(e) {
        e.preventDefault();
        const data = {
            firstName: form.firstName.value,
            lastName: form.lastName.value,
            phoneNumber: form.phoneNumber.value,
            city: form.city.value,
            description: form.description.value
        };

        try {
            const resp = await fetch(`/api/applications/pet/${petIdInput.value}`, {
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
                const err = await resp.text();
                alert('Failed to submit application: ' + err);
            }
        } catch (err) {
            alert('Failed to submit application: ' + err.message);
        }
    });

    // Back button
    document.getElementById('back-btn').addEventListener('click', function() {
        window.history.length > 1 ? window.history.back() : window.location.href = 'index.html';
    });
});