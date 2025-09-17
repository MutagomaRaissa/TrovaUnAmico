const API_BASE = "/api";


const PetAPI = {
    getAllPets: async () => (await fetch(`${API_BASE}/pets`)).json(),
    getPetById: async (id) => (await fetch(`${API_BASE}/pets/${id}`)).json(),
    getPetsByCategory: async (category) => (await fetch(`${API_BASE}/pets/category/${category}`)).json(),
    getPetsByCategoryWithFilters: async (category, breed, gender, age, location) => {
        const params = new URLSearchParams();
        if (breed) params.append("breed", breed);
        if (gender) params.append("gender", gender);
        if (age) params.append("age", age);
        if (location) params.append("location", location);
        const url = `${API_BASE}/pets/category/${category}/filter?${params.toString()}`;
        return (await fetch(url)).json();
    }
};


const ApplicationAPI = {
    async saveApplication(petId, appData) {
        const res = await fetch(`${API_BASE}/applications/pet/${petId}`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(appData),
            credentials: "include"
        });
        if (!res.ok) throw new Error("Failed to save application");
        return res.json();
    },
    async getApplicationById(appId) {
        const res = await fetch(`${API_BASE}/applications/${appId}`, { credentials: "include" });
        if (!res.ok) throw new Error("Failed to fetch application");
        return res.json();
    },
    async getMyApplications() {
        const res = await fetch(`${API_BASE}/applications/my`, { credentials: "include" });
        if (!res.ok) throw new Error("Failed to fetch applications");
        return res.json();
    }
};
