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
    console .log("Checking login status...");
    const token = getCookie("token"); // Assume this function retrieves the token from cookies
    const authContainer = document.getElementById("auth-container");
    
    if (token) {
        // ✅ Logged in - show profile icon with dropdown
        authContainer.innerHTML = `
            <div class="profile-menu">
               <img src="/Assets/profile.png" alt="Profile" class="profile-icon" onclick="toggleDropdown()" />

                <div class="dropdown-menu" id="dropdownMenu">
                   
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


function checkLoginOfUser() {
    const cookies = document.cookie.split(';');
    let token = null;

    for (let cookie of cookies) {
        const [name, value] = cookie.trim().split('=');
        if (name === 'token') {
            token = value;
            break;
        }
    }

    if (!token) return false;

    // Optional: Check expiry if it's stored in another cookie
    // For example, assuming expiry is stored in 'token_expiry' as a timestamp
    const expiryCookie = cookies.find(c => c.trim().startsWith('token_expiry='));
    if (expiryCookie) {
        const expiry = parseInt(expiryCookie.split('=')[1]);
        const now = new Date().getTime();
        return now < expiry;
    }

    // If no explicit expiry info, assume token exists means logged in
    return true;
}
