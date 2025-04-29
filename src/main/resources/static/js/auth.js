
  function toggleForm(type) {
    if (type === "register") {
      document.getElementById("login-box").style.display = "none";
      document.getElementById("register-box").style.display = "block";
    } else {
      document.getElementById("register-box").style.display = "none";
      document.getElementById("login-box").style.display = "block";
    }
  }

  // Login
  document.getElementById("loginForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const email = document.getElementById("loginEmail").value;
    const password = document.getElementById("loginPassword").value;
  
    try {
      const res = await fetch("http://localhost:8080/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password })
      });
  
      const data = await res.text(); // ✅ now it's parsed
  
      if (res.ok) {
        const now = new Date();
        now.setTime(now.getTime() +  10 * 60 * 60 * 1000); // 10 hours  to milliseconds
        const expires = "expires=" + now.toUTCString();
        document.cookie = "token" + "=" + data + ";" + expires + ";path=/";

        //localStorage.setItem("token", data); // ✅ this works now
        alert("Login successful!");
        window.location.href = /*[[@{/}]]*/ "/"; // ✅ correct redirect with Thymeleaf if inline
      } else {
        alert("Login failed: " + (data.message || "Invalid credentials"));
      }
  
    } catch (err) {
      console.error("Login error:", err);
      alert("Login failed: Server error");
    }
  });
  

  // Register
  document.getElementById("registerForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const roleId = parseInt(document.getElementById("roleId").value);
    const roleName = roleId === 2 ? "Admin" : "User";

    const userData = {
      name,
      email,
      password,
      role: {
        id: roleId,
        name: roleName
      }
    };

    const res = await fetch("http://localhost:8080/register", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(userData)
    });

    if (res.ok) {
      alert("Registration successful! You can now log in.");
      toggleForm("login");
    } else {
      const data = await res.json();
      alert("Registration failed: " + (data.message || "Try again"));
    }
  });

