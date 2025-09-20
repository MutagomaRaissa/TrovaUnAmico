document.addEventListener("DOMContentLoaded", async () => {
    const container = document.getElementById("category-pet-list");
    const params = new URLSearchParams(window.location.search);
    const category = params.get("category");
    const categoryTitle = document.getElementById("category-pet-title");

    if (!category) {
        container.innerHTML = "<p>No category selected.</p>";
        return;
    }

    categoryTitle.textContent = `Available ${category} pets`;

    const allPets = await PetAPI.getPetsByCategory(category);
    renderPets(allPets, container);

    populateFilterOptions(allPets, "breed", "breed-filter");
    populateFilterOptions(allPets, "location", "location-filter");

    document.getElementById("apply-filters-btn").addEventListener("click", async () => {
        const breed = document.getElementById("breed-filter").value || null;
        const gender = document.getElementById("gender-filter").value || null;
        const ageInput = document.getElementById("age-filter").value;
        let age = null;
        if (ageInput === "5") {
            age = "5+";
        } else if (ageInput) {
            age = parseInt(ageInput);
        }
        const location = document.getElementById("location-filter").value || null;

        try {
            const pets = await PetAPI.getPetsByCategoryWithFilters(category, breed, gender, age, location);
            renderPets(pets, container);
        } catch (err) {
            container.innerHTML = "<p>Could not load pets with filters.</p>";
        }
    });
});

function renderPets(pets, container) {
    if (!pets.length) {
        container.innerHTML = "<p>No pets found.</p>";
        return;
    }
    container.innerHTML = pets.map(pet => `
        <div class="pet-card">
            <img src="${pet.imageUrl || 'default.jpg'}" alt="${pet.name}">
            <div class="pet-info">
                <h3>${pet.name}</h3>
                <p>${pet.breed} - ${pet.age} years</p>
                <button class="details-btn" onclick="viewPetDetails(${pet.petId})">View Details</button>
            </div>
        </div>
    `).join("");
}

function populateFilterOptions(pets, key, selectId) {
    const select = document.getElementById(selectId);
    const uniqueValues = [...new Set(pets.map(pet => pet[key]))].sort();
    uniqueValues.forEach(value => {
        if (value) {
            const option = document.createElement("option");
            option.value = value;
            option.textContent = value;
            select.appendChild(option);
        }
    });
}

window.viewPetDetails = function(id) {
    window.location.href = `pet-details.html?id=${id}`;
};

function goBack() {
    window.location.href = "index.html";
}
