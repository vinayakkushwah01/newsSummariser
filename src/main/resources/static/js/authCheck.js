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
    const token = getCookie("token");
    const loginButton = document.getElementById("login-btn");
    console.log(loginButton) // Assuming you have a button with id="login-btn"

    if (token) {
        // ✅ Logged in
        loginButton.textContent = "Logout";
        loginButton.onclick = function() {
            // Clear cookie
            document.cookie = "token=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
            alert("Logged out successfully!");
            // Redirect to homepage or login page
            window.location.href = "/";
        };
    } else {
        // ❌ Not logged in
        loginButton.textContent = "Login";
        loginButton.onclick = function() {
            window.location.href = "/auth.html"; // or your login page
        };
    }

   
}
checkLoginStatus();