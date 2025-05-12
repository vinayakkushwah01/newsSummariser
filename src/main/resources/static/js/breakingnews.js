// import { Client } from "@gradio/client";

let FinalNewsURl =null;

document.addEventListener("DOMContentLoaded", async () => {
    const categoriesContainer = document.getElementById("navbar-categories");
    const sidebarCategories = document.getElementById("sidebar-categories");
    const params = new URLSearchParams(window.location.search);
    const newsId = params.get("id");
    const newsUrl = params.get("url");

    if (categoriesContainer) await fetchCategories(categoriesContainer); // Navbar
    if (sidebarCategories) await fetchCategories(sidebarCategories);     // Sidebar

    if (newsId && newsUrl) {
        await fetchBreakingNewsDetails(newsId, newsUrl);
    } else {
        console.error("Missing ID or URL in query parameters.");
        document.getElementById("breaking-news-details").innerHTML = "<p style='color: red;'>Invalid or missing news ID/URL.</p>";
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
            history.pushState({ url }, "", url);
            await fetchCategoryNews(url);
            document.getElementById("main-content")?.scrollIntoView({ behavior: "smooth" });
        });

        targetElement.appendChild(categoryItem);
    });
}
function formatNewsDetails(detailedNews) {
    // Split the detailedNews at every "|" symbol and insert <br> after each "|"
    let formattedText = detailedNews.split('|').join('<br>');

    // Add two blank lines before every occurrence of "ये भी पढ़ें:-"
    formattedText = formattedText.replace(/ये भी पढ़ें:-/g, '<br><br>ये भी पढ़ें:-');

    return formattedText;
}
// Fetch breaking news details
async function fetchBreakingNewsDetails(newsId, newsUrl) {
    FinalNewsURl = newsUrl;
    try {
        const encodedUrl = encodeURIComponent(newsUrl);
        const response = await fetch(`http://localhost:8080/api/breakingnews/${newsId}?url=${encodedUrl}`);
        if (!response.ok) throw new Error("API response not ok");

        const data = await response.json();
        const container = document.getElementById("breaking-news-details");

        const image = data.imageUrl || "/Assets/default.jpg";
        const headline = data.headline || "No Headline";
        const detailedNews = data.detailedNews || "No details available.";
        const formattedDetailedNews = formatNewsDetails(detailedNews);
        container.innerHTML = `
            <h2 style="margin-bottom: 20px;">${headline}</h2>
            <img src="${image}" alt="News Image" style="max-width: 100%; margin-bottom: 20px;" />
            <p style="white-space: pre-line;">${formattedDetailedNews}</p>
            <!--${newsUrl ? `<a href="${newsUrl}" target="_blank" style="display: inline-block; margin-top: 20px; color: blue;">Read Full Article ↗</a>` : ""}-->
        `;
    } catch (error) {
        console.error("Error fetching breaking news details:", error);
        document.getElementById("breaking-news-details").innerHTML = "<p style='color: red;'>Error loading news article. Please try again later.</p>";
    }
}

// Handle browser navigation (back/forward)
window.onpopstate = (event) => {
    let path = window.location.pathname;
    if (path.endsWith("index.html")) path = "/";

    if (path === "/") {
        fetchTopNews(); // Implement this to load default homepage content
    } else if (event.state?.url) {
        fetchCategoryNews(event.state.url);
    } else {
        fetchTopNews();
    }
};

// Placeholder: implement these as needed
async function fetchCategoryNews(url) {
    console.log("Fetching category news for:", url);
    // Add your logic to load category-specific news content
}

async function fetchTopNews() {
    console.log("Fetching top news for homepage...");
    // Add your logic to load top/breaking news for the homepage
}

document.getElementById("summarize-btn").addEventListener("click", async function() {
    // Check if the token is present and valid in cookies
    const token = getCookie("token");
    
   // const tokenExpiry = getCookie("tokenExpiry");
//|| !tokenExpiry || Date.now() > new Date(tokenExpiry).getTime()
    // If token doesn't exist or has expired, show alert and redirect to login
    if (!token ) {
        alert("Please log in first.");
        window.location.href = "/auth.html"; // Redirect to login page
        return;
    }

    // Show loader while summarizing the article
    document.getElementById("loader").style.display = "block";
    document.getElementById("summarized-news").innerHTML = ""; // Clear previous summary

    // Simulating fetching the detailed news text
    const detailedNews = "शोधकर्ताओं का कहना है कि गंध की संरचना को रासायनिक रूप से फिर से बनाने से दूसरों को ममी की गंध का अनुभव करने का मौका मिलेगा और यह बताने में मदद मिलेगी कि अंदर के शव कब सड़ने लगे हैं। यूनिवर्सिटी कॉलेज लंदन के इंस्टीट्यूट फॉर सस्टेनबल हेरिटेज में शोध निदेशक और रिसर्च टीम का नेतृत्व करने वाली सेसिलिया बेम्बिब्रे ने बताया कि 'हम ममी को सूंघने के अनुभव को साझा करना चाहते हैं। इसलिए हम काहिरा में मिस्र के संग्रहालय में प्रस्तुत करने के लिए गंध का पुनिर्निर्माण कर रहे हैं।'";

    // Send POST request to summarize the text using the API
     try {
        const response = await fetch("https://api.apyhub.com/ai/summarize-text&summary_length=long&output_language=hi&summary_length=long", {
 
            method: "POST",
            headers: {
                "apy-token":"APY0710m76tqVGVaRKbCnZFD2VhJYnPOfIgoghsxS57fLH3QBpnbnMj84TWLM5t1FtaALcm",
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                "text" : detailedNews,
                "summary_length": "long",
                "output_language": "hi"
            })
        });

        const result = await response.json();

        // Hide loader and show the summarized news
        document.getElementById("loader").style.display = "none";
        document.getElementById("summarized-news").innerHTML = `
            <h4>Summarized Article:</h4>
            <p>${result.data?.[0] || "Unable to summarize the article."}</p>
        `;
    } catch (error) {
        console.error("Error summarizing text:", error);
        document.getElementById("loader").style.display = "none";
        document.getElementById("summarized-news").innerHTML = `
            <p>Failed to summarize the article. Please try again later.</p>
        `;
    }
});

// Function to get cookies by name
function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
    return null;
}
