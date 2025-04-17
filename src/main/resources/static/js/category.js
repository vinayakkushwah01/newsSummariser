 const BASE_URL = "http://localhost:8080/api/news";

async function fetchCategoryNews(category) {
    const fullURL = `${BASE_URL}${category}`;
   console.log("Requesting category news from:", fullURL);

    try {
        const response = await fetch(fullURL);

        if (!response.ok) {
            throw new Error(`Error fetching category news: ${response.statusText}`);
        }
        const data = await response.json();
        let headline = document.getElementById("headline_text");

// Remove leading '/' and capitalize each word
let formattedCategory = category.replace(/^\/+/, '')  // Remove leading slashes
                                .replace(/\b\w/g, char => char.toUpperCase());  // Capitalize each word

headline.textContent = formattedCategory;
        //console.log(data);
        await updateTopNews(data);
        return data;
    } catch (error) {
        console.error("Error fetching category news:", error);
        return [];
    }
}


// ✅ 2. updateTopNews
// This function removes old .news-card elements and creates 6 new ones:

async function updateTopNews(newsList) {
    console.log("update top news Called ")
    const topNewsContainer = document.getElementById("top-news");

    if (!newsList.length) return;
    

    topNewsContainer.innerHTML = "";

    newsList.forEach(news => {
        console.log(news);
        const card = document.createElement("div");
        card.classList.add("news-card");

        card.innerHTML = `
            <img src="${'https://'+news.imageUrl}" alt="News Image" th:onerror="src=@{/Assets/default.jpg};">
            <div class="card-content">
                <h3>${news.headline}</h3>
                <a href="${news.articleLink}" target="_blank">Read More</a>
            </div>
        `;

        topNewsContainer.appendChild(card);
    });
}
