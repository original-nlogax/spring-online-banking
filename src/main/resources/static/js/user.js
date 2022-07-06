function editProfileData () {
    alert("edit user data");
}

async function fillProfileModal () {
    let user = await getAuthorizedUser();
    document.getElementById("profileFirstLastName").innerText = user.firstName + ' ' + user.lastName;
    document.getElementById("profileEmail").innerText = user.email;
    document.getElementById("profilePhoneNumber").innerText = user.email;
    //document.getElementById("profileAdminPanelButton").hidden = !user.roles.includes("ROLE_ADMIN");
}

async function getAuthorizedUser () {
    const response = await fetch('auth/user', {
        method: 'GET',
        headers: { "Content-Type": "application/json" },
    }).catch ((error) => {
        console.log(error);
    });

    if (response !== undefined) {
        if (response.ok) {
            return response.json();
        }
    }
}

async function getUser (id) {
    const response = await fetch('users/' + id, {
        method: 'GET',
        headers: { "Content-Type": "application/json" },
    }).catch ((error) => {
        console.log(error);
    });

    if (response !== undefined) {
        if (response.ok) {
            return response.json();
        }
    }
}

async function registerUser () {
    let form = document.getElementById("registerForm");

    const response = await fetch('/users', {
        method:'post',
        body: new FormData(form),

    }).catch(err => {
        console.log(err);
    })

    if (response !== undefined) {
        if (response.ok) {
            window.location = "login";
            return response.json();
        }
    }
}