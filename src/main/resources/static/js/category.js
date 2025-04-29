//  const BASE_URL = "http://localhost:8080/api/news";

// async function fetchCategoryNews(category) {
//     const fullURL = `${BASE_URL}${category}`;
//    console.log("Requesting category news from:", fullURL);

//     try {
//         const response = await fetch(fullURL);

//         if (!response.ok) {
//             throw new Error(`Error fetching category news: ${response.statusText}`);
//         }
//         const data = await response.json();
//         let headline = document.getElementById("headline_text");

// // Remove leading '/' and capitalize each word
// let formattedCategory = category.replace(/^\/+/, '')  // Remove leading slashes
//                                 .replace(/\b\w/g, char => char.toUpperCase());  // Capitalize each word

// headline.textContent = formattedCategory;
//         //console.log(data);
//         await updateTopNews(data);
//         return data;
//     } catch (error) {
//         console.error("Error fetching category news:", error);
//         return [];
//     }
// }


// // ✅ 2. updateTopNews
// // This function removes old .news-card elements and creates 6 new ones:

// async function updateTopNews(newsList) {
//     console.log("update top news Called ")
//     const topNewsContainer = document.getElementById("top-news");

//     if (!newsList.length) return;
    

//     topNewsContainer.innerHTML = "";

//     newsList.forEach(news => {
//         console.log(news);
//         const card = document.createElement("div");
//         card.classList.add("news-card");

//         card.innerHTML = `
//             <img src="${'https://'+news.imageUrl}" alt="News Image" th:onerror="src=@{/Assets/default.jpg};">
//             <div class="card-content">
//                 <h3>${news.headline}</h3>
//                 <a href="${news.articleLink}" target="_blank">Read More</a>
//             </div>
//         `;

//         topNewsContainer.appendChild(card);
//     });
// }

// let currentPage = 1;
// window.addEventListener('scroll', () => {
//     const scrollTop = window.scrollY;
//     const windowHeight = window.innerHeight;
//     const documentHeight = document.documentElement.scrollHeight;

//     if (scrollTop + windowHeight >= documentHeight - 10) { 
//         // 🔥 User has scrolled to (or very near to) the bottom
//         console.log('Reached end of page!');
//        currentPage++;
       
//        const currentUrl = window.location.href;
//        const urlObj = new URL(currentUrl);
//        const pathParts = urlObj.pathname.split("/").filter(Boolean); // only path, no domain
//        let lastPart = pathParts.length > 0 ? pathParts[pathParts.length - 1] : "home";
       
//        if (lastPart === "api" || lastPart === "category" || lastPart === "news") {
//            lastPart = "home"; // fallback if URL is incomplete
//        }
       
//        console.log(lastPart);
       
//        console.log("last part " +lastPart);
//     // console.log(fullURL);
//         // Your customf action here:
//         // For example, load more data
//         fechNewsByPage(currentPage , lastPart);
//     }
// });

// async function fechNewsByPage(currentPage ,lastPart){
//     const BASE_URL = "http://localhost:8080/api/news";
//     const topNews = await fetch(`${BASE_URL}/${lastPart}?page=${currentPage}`);
// const topNewsContainer = document.getElementById("top-news");

// if (!topNews.length) return;


// topNews.forEach(news => {
//     const card = document.createElement("div");
//     card.classList.add("news-card");

//     card.innerHTML = `
//         <img src="${news.image}" alt="News Image" th:onerror="this.src=@{/Assets/default.jpg};>
//         <div class="card-content">
//             <h3>${news.title}</h3>
//             <a href="${news.url}" target="_blank">Read More</a>
//         </div>
//     `;

//     topNewsContainer.appendChild(card);
// });

// }

const BASE_URL = "http://localhost:8080/api/news";

// Flag to prevent multiple requests at once
let isLoading = false;

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

        // Reset page number to 1 when the category changes
        currentPage = 1;

        await updateTopNews(data);
        return data;
    } catch (error) {
        console.error("Error fetching category news:", error);
        return [];
    }
}

// ✅ 2. updateTopNews
// This function removes old .news-card elements and creates new ones:

async function updateTopNews(newsList) {
    console.log("update top news Called ");
    const topNewsContainer = document.getElementById("top-news");

    if (!newsList.length) return;

    topNewsContainer.innerHTML = "";

    newsList.forEach(news => {
        console.log(news);
        const card = document.createElement("div");
        card.classList.add("news-card");

        card.innerHTML = `
            <img src="https://${news.imageUrl}" alt="News Image" th:onerror="this.src='@{/Assets/default.jpg}'">
            <div class="card-content">
                <h3>${news.headline}</h3>
                <a href="${news.articleLink}" target="_blank">Read More</a>
            </div>
        `;

        topNewsContainer.appendChild(card);
    });
}

let currentPage = 1;
window.addEventListener('scroll', () => {
    const scrollTop = window.scrollY;
    const windowHeight = window.innerHeight;
    const documentHeight = document.documentElement.scrollHeight;

    if (scrollTop + windowHeight >= documentHeight - 10) { 
        // 🔥 User has scrolled to (or very near to) the bottom
        console.log('Reached end of page!');
        
        if (isLoading) return; // Prevent the second call if already loading
        isLoading = true; // Set flag to prevent multiple requests

        currentPage++;

        const currentUrl = window.location.href;
        const urlObj = new URL(currentUrl);
        const pathParts = urlObj.pathname.split("/").filter(Boolean); // only path, no domain
        let lastPart = pathParts.length > 0 ? pathParts[pathParts.length - 1] : "home";

        if (lastPart === "api" || lastPart === "category" || lastPart === "news") {
            lastPart = "home"; // fallback if URL is incomplete
        }

        console.log(lastPart);

        // Load more news data
        fetchNewsByPage(currentPage, lastPart);
    }
});

async function fetchNewsByPage(currentPage, lastPart) {
    const BASE_URL = "http://localhost:8080/api/news";
    try {
        const response = await fetch(`${BASE_URL}/${lastPart}?page=${currentPage}`);
        const data = await response.json();

        const topNewsContainer = document.getElementById("top-news");

        if (!data || !data.length) return;

        data.forEach(news => {
            const card = document.createElement("div");
            card.classList.add("news-card");

            card.innerHTML = `
                <img src="https://${news.imageUrl}" alt="News Image" th:onerror="this.src='@{/Assets/default.jpg}'">
                <div class="card-content">
                    <h3>${news.headline}</h3>
                    <a href="${news.articleLink}" target="_blank">Read More</a>
                </div>
            `;

            topNewsContainer.appendChild(card);
        });
    } catch (error) {
        console.error("Error fetching page news:", error);
    } finally {
        isLoading = false; // Reset the loading flag after the request completes
    }
}
