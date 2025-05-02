document.addEventListener("DOMContentLoaded", async () => {
    const slider = document.getElementById("slider");
    const trendingList = document.getElementById("trending-list");
    const newsContainer = document.getElementById("news-container");
    const categoriesContainer = document.getElementById("navbar-categories");
    const sidebarCategories = document.getElementById("sidebar-categories");

    
    let currentSlide = 0;
    const BASE_URL = "http://localhost:8080/api";

    // Fetch data from API
    async function fetchNews(url) {
        try {
            const response = await fetch(url);
            const data = await response.json();
            if (!Array.isArray(data)) return [];

            return data.map(article => ({
                title: article.headline || article.breakingHeadline || "No Title",
                description: article.subheading || "No Description",
                image: article.imageUrl ? (article.imageUrl.startsWith("//") ? `https:${article.imageUrl}` : article.imageUrl) : "Assets/default.jpg",
                url: article.articleLink || article.breakingUrl || "#",
                id: article.id || null,
                time: article.date || "Unknown Time"
            }));
        } catch (error) {
            console.error("Error fetching news:", error);
            return [];
        }
    }

    // Setup slider dynamically
    async function setupSlider() {
        let currentPage = 0;
const newsData = await fetchNews(`${BASE_URL}/news/home?page=${currentPage}`);

        if (!newsData.length) return;

        slider.innerHTML = "";
        newsData.forEach(news => {
            let slide = document.createElement("div");
            slide.classList.add("slide");
            slide.innerHTML = `
                <img src="${news.image}" alt="news" th:onerror="this.src=@{/Assets/default.jpg};">
                <div class="slide-content">
                    <h2 class="news-title" data-url="${news.url}">${news.title}</h2>
                </div>
            `;
            slider.appendChild(slide);
        });
        addClickEventToTitles();
        slider.children[0].classList.add('active');
        updateSlider();
    }

    function updateSlider() {
        slider.style.transform = `translateX(-${currentSlide * 100}%)`;
    }

    function changeSlide(direction) {
        const totalSlides = slider.children.length;
        if (!totalSlides) return;
        currentSlide = (currentSlide + direction + totalSlides) % totalSlides;
        updateSlider();
        [...slider.children].forEach((slide, index) => slide.classList.toggle('active', index === currentSlide));
    }

    setInterval(() => changeSlide(1), 5000);
    document.getElementById("prev").addEventListener("click", () => changeSlide(-1));
    document.getElementById("next").addEventListener("click", () => changeSlide(1));

    // Fetch Breaking News
    async function fetchBreakingNews() {
        const breakingNews = await fetchNews(`${BASE_URL}/breakingnews`);
        trendingList.innerHTML = "";
        breakingNews.slice(0, 7).forEach(news => {
            let listItem = document.createElement("li");
            listItem.classList.add("news-title");
            listItem.setAttribute("data-id", news.id);
            listItem.setAttribute("data-url", news.url);  // ✅ Add URL attribute
            listItem.innerHTML = `🔥 ${news.title}`;
            trendingList.appendChild(listItem);
        });
        addClickEventToBreakingNews();
    }
    
    function addClickEventToBreakingNews() {
        document.querySelectorAll(".news-title").forEach(title => {
            title.addEventListener("click", function () {
                const newsId = this.getAttribute("data-id");
                const newsUrl = this.getAttribute("data-url");
    
                if (!newsId || !newsUrl) {
                    console.error("Missing news ID or URL.");
                    return;
                }
    
                const params = new URLSearchParams();
                params.set("id", newsId);
                params.set("url", newsUrl);
    
                const finalUrl = `/breakingnews?${params.toString()}`;
                window.location.href = finalUrl;
            });
        });
    }
    
    
    
    // Fetch Top News and render as cards
    async function fetchTopNews() {
        const topNews = await fetchNews(`${BASE_URL}/news/home`);
        const topNewsContainer = document.getElementById("top-news");
    
        if (!topNews.length) return;
    
        topNewsContainer.innerHTML = "";
    
        topNews.forEach(news => {
            const card = document.createElement("div");
            card.classList.add("news-card");
    
            // ✅ Set the ID of the card using news.id
            card.setAttribute("id", news.id);
    
            console.log("Id " + news.id);
    
            card.innerHTML = `
                <img src="${news.image}" alt="News Image" onerror="this.src='/Assets/default.jpg';">
                <div class="card-content">
                    <h3>${news.title}</h3>
                </div>
            `;
    
            topNewsContainer.appendChild(card);
        });
    
        // ✅ Add click event to each news-card
        const cards = document.getElementsByClassName("news-card");
    
        Array.from(cards).forEach(card => {
            card.addEventListener("click", () => {
                const articleId = card.getAttribute("id");
                console.log("Card ID:", articleId);
    
                // Make GET request to backend API
                fetch(`/article/${articleId}`)
                    .then(response => {
                        console.log(response);
                        window.location.href = response.url;
                      // r(response.url);
                    })
                    .then(data => {
                        console.log("Article loaded", data);
                    })
                    .catch(error => {
                        console.error("Error fetching article:", error);
                    });
            });
        });
    }
    





    // Fetch Categories
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
                 
            // Optional: prevent full reload (SPA-style navigation)
            categoryItem.addEventListener("click", async (event) => {
                event.preventDefault();
            
                console.log("Click event noted");
                console.log(url);
            
                await fetchCategoryNews(url);
                currentPage = 1; // Reset page number on category change
                history.pushState({ url: url }, "", BASE_URL+"/category"+url);
            
            
                // Scroll after content is fetched and rendered
                document.getElementById("main-content").scrollIntoView({ behavior: "smooth" });
            });
            
    
            targetElement.appendChild(categoryItem);
        });
    }

    // This runs when the user presses the back or forward button
    window.onpopstate = (event) => {
        console.log("onpopstate fired!");
    
        let path = window.location.pathname;
    
        // Normalize for file-based projects (optional if using a server)
        if (path.endsWith("index.html")) {
            path = "/";
        }
    
        if (path === "/") {
            console.log("User went back to home (index.html)");
            fetchTopNews(); // ← Call your default home content fetcher
        } else if (event.state && event.state.url) {
            console.log("Back/Forward to:", event.state.url);
            fetchCategoryNews(event.state.url);
        } else {
            console.log("No state found, fallback to homepage");
            fetchTopNews();
        }
    };
    
    
    

    // Fetch Breaking News Details
    async function fetchBreakingNewsDetails(newsId) {
        try {
            const response = await fetch(`${BASE_URL}/breakingnews/${newsId}`);
            const data = await response.json();
            if (!data) return;

            document.getElementById("breaking-news-details").innerHTML = `
                <h2>${data.breakingHeadline || "No Headline"}</h2>
                <img src="${data.imageUrl || 'Assets/default.jpg'}" alt="News Image">
                <p>${data.subheading || ""}</p>
                <a href="${data.breakingUrl}" target="_blank">Read Full Article</a>
            `;
        } catch (error) {
            console.error("Error fetching breaking news details:", error);
        }
    }

    // Add Click Event to News Titles
    function addClickEventToTitles() {
        document.querySelectorAll(".news-title").forEach(title => {
            title.addEventListener("click", function () {
                const newsUrl = this.getAttribute("data-url");
                if (newsUrl !== "#") window.open(newsUrl, "_blank");
            });
        });
    }
    // let currentPage = 1;
    // let isLoading = false;  // To prevent multiple fetches at once
    
    // window.addEventListener('scroll', () => {
    //     const scrollTop = window.scrollY;
    //     const windowHeight = window.innerHeight;
    //     const documentHeight = document.documentElement.scrollHeight;
    
    //     if (scrollTop + windowHeight >= documentHeight - 10) { 
    //         // 🔥 User has scrolled to (or very near to) the bottom
    //         console.log('Reached end of page!');
    
    //         // Prevent multiple triggers while already loading
    //         if (isLoading) return;
    
    //         isLoading = true;  // Set loading flag to true
    
    //         setTimeout(async () => {
    //             // Increment page after the data is loaded
    //             const currentUrl = window.location.href;
    //             const urlObj = new URL(currentUrl);
    //             const pathParts = urlObj.pathname.split("/").filter(Boolean); // only path, no domain
    //             let lastPart = pathParts.length > 0 ? pathParts[pathParts.length - 1] : "home";
    
    //             if (lastPart === "api" || lastPart === "category" || lastPart === "news") {
    //                 lastPart = "home"; // fallback if URL is incomplete
    //             }
    
    //             console.log("last part " + lastPart);
    
    //             // Fetch and load more data
    //             await fechNewsByPage(currentPage, lastPart);
    
    //             // Increment page number after the fetch is complete
    //             currentPage++;
    
    //             // Reset loading flag after the fetch
    //             isLoading = false;
    //         }, 200);  // 2-second delay before triggering the next fetch
    //     }
    // });
    
    // async function fechNewsByPage(currentPage, lastPart) {
    //     const BASE_URL = "http://localhost:8080/api/news";
    //     const topNews = await fetchNews(`${BASE_URL}/${lastPart}?page=${currentPage}`);
    //     const topNewsContainer = document.getElementById("top-news");
    
    //     if (!topNews.length) return;
    
    //     topNews.forEach(news => {
    //         const card = document.createElement("div");
    //         card.classList.add("news-card");
    
    //         card.innerHTML = `
    //             <img src="${news.image}" alt="News Image" th:onerror="this.src=@{/Assets/default.jpg};">
    //             <div class="card-content">
    //                 <h3>${news.title}</h3>
    //                 <a href="${news.url}" target="_blank">Read More</a>
    //             </div>
    //         `;
    
    //         topNewsContainer.appendChild(card);
    //     });
    // }
    
    // Initialize all sections


    let currentPage = 1;
    let isLoading = false;  // To prevent multiple fetches at once
    
    window.addEventListener('scroll', async () => {
        const scrollTop = window.scrollY;
        const windowHeight = window.innerHeight;
        const documentHeight = document.documentElement.scrollHeight;
    
        if (scrollTop + windowHeight >= documentHeight - 10) { 
            // 🔥 User has scrolled to (or very near to) the bottom
            console.log('Reached end of page!');
    
            // Prevent multiple triggers while already loading
            if (isLoading) return;
            if (isLoading) return;
            isLoading = true;  // Set loading flag to true
    
            // Add a slight delay (optional)
            await new Promise(resolve => setTimeout(resolve, 200));  // 2-second delay before triggering the next fetch
    
            const currentUrl = window.location.href;
            const urlObj = new URL(currentUrl);
            const pathParts = urlObj.pathname.split("/").filter(Boolean); // only path, no domain
            let lastPart = pathParts.length > 0 ? pathParts[pathParts.length - 1] : "home";
    
            if (lastPart === "api" || lastPart === "category" || lastPart === "news") {
                lastPart = "home"; // fallback if URL is incomplete
            }
    
            console.log("last part " + lastPart);
    
            // Fetch and load more data, await to ensure it's finished before incrementing page
            await fetchNewsByPage(currentPage, lastPart);
    
            // Increment page number after the fetch is complete
            currentPage++;
    
            // Reset loading flag after the fetch is complete
            // isLoading = false;
        }
        // isLoading
    });
    
    async function fetchNewsByPage(currentPage, lastPart) {
        const BASE_URL = "http://localhost:8080/api/news";
        const topNews = await fetchNews(`${BASE_URL}/${lastPart}?page=${currentPage}`);
        const topNewsContainer = document.getElementById("top-news");
    
        if (!topNews.length) return;
    
        topNews.forEach(news => {
            const card = document.createElement("div");
            card.classList.add("news-card");
            card.setAttribute("id", news.id);
    
            card.innerHTML = `
                <img src="${news.image}" alt="News Image" th:onerror="this.src=@{/Assets/default.jpg};">
                <div class="card-content">
                    <h3>${news.title}</h3>
                    <!-- <a href="${news.url}" target="_blank">Read More</a>-->
                </div>
            `;
    
            topNewsContainer.appendChild(card);
        });
        const cards = document.getElementsByClassName("news-card");
    
        Array.from(cards).forEach(card => {
            card.addEventListener("click", () => {
                const articleId = card.getAttribute("id");
                console.log("Card ID:", articleId);
    
                // Make GET request to backend API
                fetch(`/article/${articleId}`)
                    .then(response => {
                        console.log(response);
                        window.location.href = response.url;
                      // r(response.url);
                    })
                    .then(data => {
                        console.log("Article loaded", data);
                    })
                    .catch(error => {
                        console.error("Error fetching article:", error);
                    });
            });
        });
    }
    

    await setupSlider();
    await fetchBreakingNews();
    await fetchCategories(categoriesContainer);  // Navbar
    await fetchCategories(sidebarCategories);    // Sidebar
    await fetchTopNews();
});





