function getCookie(name) {
    const decodedCookie = decodeURIComponent(document.cookie);
    const cookies = decodedCookie.split(';');
    name = name + "=";
    for (let cookie of cookies) {
        cookie = cookie.trim();
        if (cookie.indexOf(name) === 0) {
            return cookie.substring(name.length, cookie.length);
        }
    }
    return "";
}

// Check Login Status
function checkLoginStatus() {
    //const token = getCookie("token");
   // const loginButton = document.getElementById("login-btn");
   // console.log(loginButton) // Assuming you have a button with id="login-btn"

    // if (token) {
    //     // ✅ Logged in
        
    //     loginButton.textContent = "Logout";
    //     loginButton.onclick = function() {
    //         // Clear cookie
    //         document.cookie = "token=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
    //         alert("Logged out successfully!");
    //         // Redirect to homepage or login page
    //         window.location.href = "/";
    //     };
    // } else {
    //     // ❌ Not logged in
    //     loginButton.textContent = "Login";
    //     loginButton.onclick = function() {
    //         window.location.href = "/auth.html"; // or your login page
    //     };
    // }

    const token = getCookie("token"); // Assume this function retrieves the token from cookies
    const authContainer = document.getElementById("auth-container");
    
    if (token) {
        // ✅ Logged in - show profile icon with dropdown
        authContainer.innerHTML = `
            <div class="profile-menu">
               <img src="/Assets/profile.png" alt="Profile" class="profile-icon" onclick="toggleDropdown()" />

                <div class="dropdown-menu" id="dropdownMenu">
                    <a href="/user/profile">View Profile</a>
                    <a href="/user/update-password">Update Password</a>
                    <a href="#" id="logoutBtn">Logout</a>
                </div>
            </div>
        `;
    
        // Add logout logic
        document.getElementById("logoutBtn").onclick = function() {
            document.cookie = "token=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
            alert("Logged out successfully!");
            window.location.href = "/";
        };
    
    } else {
        // ❌ Not logged in - show login button
        authContainer.innerHTML = `
            <button id="loginBtn">Login</button>
        `;
        document.getElementById("loginBtn").onclick = function () {
            window.location.href = "/auth.html";
        };
    }
    
}
function toggleDropdown() {
    const menu = document.getElementById("dropdownMenu");
    menu.style.display = menu.style.display === "block" ? "none" : "block";
}

document.addEventListener("click", function (event) {
    const menu = document.getElementById("dropdownMenu");
    const icon = document.querySelector(".profile-icon");
    if (menu && !menu.contains(event.target) && !icon.contains(event.target)) {
        menu.style.display = "none";
    }
});
checkLoginStatus();