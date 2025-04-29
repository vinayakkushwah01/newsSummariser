document.addEventListener("DOMContentLoaded", async () => {
    const categoriesContainer = document.getElementById("navbar-categories");
    const sidebarCategories = document.getElementById("sidebar-categories");
    const params = new URLSearchParams(window.location.search);
    const newsId = params.get("id");
    const newsUrl = params.get("url");

    if (categoriesContainer) await fetchCategories(categoriesContainer); // Navbar
    if (sidebarCategories) await fetchCategories(sidebarCategories);     // Sidebar

    if (newsId && newsUrl) {
        fetchBreakingNewsDetails(newsId, newsUrl);
    } else {
        console.error("Missing ID or URL in query parameters.");
    }
});

// Fetch and render category links
async function fetchCategories(targetElement) {
    const categories = [
        { category: "Home", url: "/" },
        { category: "देश", url: "/india-news" },
        { category: "दुनिया", url: "/world" },
        { category: "मनोरंजन", url: "/entertainment" },
        { category: "क्रिकेट", url: "/cricket" },
        { category: "कारोबार", url: "/business" },
        { category: "नौकरी", url: "/jobs" },
        { category: "शिक्षा", url: "/education" },
        { category: "टेक्नोलॉजी", url: "/technology" },
        { category: "ऑटो", url: "/automobiles" },
        { category: "ज्योतिष", url: "/astrology" },
        { category: "खेल", url: "/sports" },
        { category: "हेल्थ एंड फिटनेस", url: "/fitness" },
        { category: "फैशन", url: "/fashion" },
        { category: "शक्ति", url: "/shakti" },
        { category: "आस्था", url: "/spirituality" },
        { category: "बॉलीवुड", url: "/bollywood" },
        { category: "इंदौर", url: "/indore" },
        { category: "मध्य प्रदेश", url: "/madhya-pradesh" },
        { category: "मुंबई", url: "/mumbai" },
        { category: "दिल्ली", url: "/delhi" },
        { category: "भोपाल", url: "/bhopal" },
    ];

    targetElement.innerHTML = "";

    categories.forEach(({ category, url }) => {
        const categoryItem = document.createElement("a");
        categoryItem.href = url;
        categoryItem.classList.add("category-item");
        categoryItem.textContent = category;

        categoryItem.addEventListener("click", async (event) => {
            event.preventDefault();
            console.log("Clicked:", url);
            history.pushState({ url }, "", url);
            await fetchCategoryNews(url);
            document.getElementById("main-content")?.scrollIntoView({ behavior: "smooth" });
        });

        targetElement.appendChild(categoryItem);
    });
}

// Fetch breaking news details
async function fetchBreakingNewsDetails(newsId, newsUrl) {
    try {
        const encodedUrl = encodeURIComponent(newsUrl);
        const response = await fetch(`http://localhost:8080/api/breakingnews/${newsId}?url=${encodedUrl}`);
        const data = await response.json();

        document.getElementById("breaking-news-details").innerHTML = `
            <h2>${data.headline || "No Headline"}</h2>
            <img src="${data.imageUrl || 'Assets/default.jpg'}" alt="News Image">
            <p>${data.detailedNews || "No details available."}</p>
            <a href="${newsUrl}" target="_blank">Read Full Article</a>
        `;
    } catch (error) {
        console.error("Error fetching breaking news details:", error);
    }
}

// Handle browser navigation (back/forward)
window.onpopstate = (event) => {
    console.log("onpopstate fired!");

    let path = window.location.pathname;

    if (path.endsWith("index.html")) path = "/";

    if (path === "/") {
        console.log("Returned to Home");
        fetchTopNews(); // Implement this to load default homepage content
    } else if (event.state?.url) {
        console.log("Back/forward to:", event.state.url);
        fetchCategoryNews(event.state.url);
    } else {
        console.log("No state found, fallback to homepage");
        fetchTopNews();
    }
};

// Placeholder: implement these as needed
async function fetchCategoryNews(url) {
    console.log("Fetching category news for:", url);
    // Implement your fetch/render logic for category-based news
}

async function fetchTopNews() {
    console.log("Fetching top news for homepage...");
    // Implement your homepage fetch logic
}
