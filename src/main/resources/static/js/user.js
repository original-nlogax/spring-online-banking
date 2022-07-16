//let profileFirstLastName, profileFirstName, profileLastName, profileEmail, profilePhoneNumber;
//let profileFirstNameInput, profileLastNameInput, profileEmailInput, profilePhoneNumberInput;


async function fillProfileModal () {
    let user = await getAuthorizedUser();

    //fixme bad code
    document.getElementById("profileFirstLastName").innerText = user.firstName + ' ' + user.lastName;
    document.getElementById("profileFirstName").innerText = user.firstName;
    document.getElementById("profileLastName").innerText = user.lastName;
    document.getElementById("profileEmail").innerText = user.email;
    document.getElementById("profilePhoneNumber").innerText = user.phoneNumber;

    document.getElementById("profileFirstNameInput").value = user.firstName;
    document.getElementById("profileLastNameInput").value = user.lastName;
    document.getElementById("profileEmailInput").value = user.email;
    document.getElementById("profilePhoneNumberInput").value = user.phoneNumber;

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

// fixme how to avoid this
function setProfileEdit (isEdit) {
    document.getElementById("profileEmail").hidden
        = document.getElementById("profilePhoneNumber").hidden
        = document.getElementById("profileFirstName").hidden
        = document.getElementById("profileLastName").hidden
        = document.getElementById("editProfileButton").hidden
        = isEdit;

    document.getElementById("profileEmailInput").hidden
        = document.getElementById("profilePhoneNumberInput").hidden
        = document.getElementById("profileFirstNameInput").hidden
        = document.getElementById("profileLastNameInput").hidden
        = document.getElementById("saveProfileButton").hidden
        = !isEdit;
}

function editProfile () {
    setProfileEdit(true);
}

async function saveProfile () {
    let form = document.getElementById("profileForm");
    let user = await getAuthorizedUser ();

    const response = await fetch('/users/' + user.id, {
        method: 'put',
        body: new FormData(form),
    }).catch(err => {
        console.log(err);
    })

    if (response !== undefined) {
        if (response.ok) {
            setProfileEdit(false);
            fillProfileModal ();
        }
    }
}