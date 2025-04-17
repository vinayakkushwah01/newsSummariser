document.addEventListener("DOMContentLoaded", () => {
    const params = new URLSearchParams(window.location.search);
    const newsId = params.get("id");
    const newsUrl = params.get("url");

    if (newsId && newsUrl) {
        fetchBreakingNewsDetails(newsId, newsUrl);
    } else {
        console.error("Missing ID or URL in query parameters.");
    }
});

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
