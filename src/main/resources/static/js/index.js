document.addEventListener('DOMContentLoaded', () => {
    fetchPets();
});

function fetchPets(category) {
    let url = '/api/pets';
    if (category) url += `?category=${encodeURIComponent(category)}`;
    fetch(url)
        .then(res => res.json())
        .then(displayPets)
        .catch(() => {
            document.getElementById('pet-list').innerHTML = '<p>Could not load pets.</p>';
        });
}

function displayPets(pets) {
    const petList = document.getElementById('pet-list');
    if (!pets || pets.length === 0) {
        petList.innerHTML = '<p>No pets available.</p>';
        return;
    }
    petList.innerHTML = pets.map(renderPetCard).join('');
}

function renderPetCard(pet) {
    return `
        <div class="pet-card">
            <img src="${pet.imageUrl || 'default.jpg'}" alt="${pet.name}">
            <div class="pet-info">
                <h3>${pet.name}</h3>
                <p>${pet.breed} - ${pet.age} years</p>
                <button class="details-btn" onclick="viewPetDetails(${pet.petId})">View Details</button>
            </div>
        </div>
    `;
}

window.viewCategory = function(category) {
    window.location.href = `category.html?category=${encodeURIComponent(category)}`;
};

window.viewPetDetails = function(id) {
    window.location.href = `pet-details.html?id=${id}`;
};
