
document.addEventListener('DOMContentLoaded', async () => {
    const detailsDiv = document.getElementById('pet-details');
    const params = new URLSearchParams(window.location.search);
    const petId = params.get('id');

    if (!petId) {
        detailsDiv.innerHTML = '<p>Pet not found.</p>';
        return;
    }

    try {
        const pet = await PetAPI.getPetById(petId);
        if (!pet) {
            detailsDiv.innerHTML = '<p>Pet not found.</p>';
            return;
        }
        detailsDiv.innerHTML = `
            <img src="${pet.imageUrl || 'images/default.jpg'}" alt="${pet.name}">
            <h2>${pet.name}</h2>
            <div class="pet-details-info">
                <div><strong>Breed:</strong> ${pet.breed}</div>
                <div><strong>Age:</strong> ${pet.age} years</div>
                <div><strong>Gender:</strong> ${pet.gender}</div>
                <div><strong>Location:</strong> ${pet.location}</div>
                <div><strong>Category:</strong> ${pet.category}</div>
            </div>
            <div class="pet-details-desc">${pet.description || ''}</div>
            <div class="pet-details-actions">
             <button class="details-btn" id="back-btn">Back</button>
                <button class="details-btn" id="apply-btn">Apply for Adoption</button>

            </div>
        `;
        document.getElementById('back-btn').addEventListener('click', () => {
            window.history.length > 1 ? window.history.back() : window.location.href = 'index.html';
        });
        document.getElementById('apply-btn').addEventListener('click', () => {
            localStorage.setItem('selectedPetId', pet.petId);
            window.location.href = `application.html?id=${pet.petId}`;
        });


    } catch {
        detailsDiv.innerHTML = '<p>Could not load pet details.</p>';
    }
});
