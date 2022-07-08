async function fillProfileModal () {
    let user = await getAuthorizedUser();
    document.getElementById("profileFirstLastName").innerText = user.firstName + ' ' + user.lastName;
    document.getElementById("profileEmail").innerText = user.email;
    document.getElementById("profilePhoneNumber").innerText = user.phoneNumber;
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

function setProfileEdit (isEdit) {
    document.getElementById("profileEmail").hidden
        = document.getElementById("profilePhoneNumber").hidden
        = document.getElementById("editProfileButton").hidden
        = isEdit;

    document.getElementById("profileEmailInput").hidden
        = document.getElementById("profilePhoneNumberInput").hidden
        = document.getElementById("saveProfileButton").hidden
        = !isEdit;
}

function editProfile () {
    setProfileEdit(true);
}

async function saveProfile () {
    let form = document.getElementById("profileForm");

    // Display the key/value pairs
    for (var pair of new FormData(form).entries()) {
        console.log(pair[0]+ ', ' + pair[1]);
    }

    const response = await fetch('/users', {
        method:'put',
        body: new FormData(form),
    }).catch(err => {
        console.log(err);
    })

    if (response !== undefined) {
        if (response.ok) {
            setProfileEdit(false);
            fillProfileModal ();
            return response.json();
        }
    }
}