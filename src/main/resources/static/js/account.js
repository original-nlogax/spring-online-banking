let currentEditedAccount;
let accountModal;

fillAccountsCardDeck();

async function getAccounts () {
    let user = await getAuthorizedUser();
    return user.accounts;
}

async function fillAccountsCardDeck () {
    const accounts = await getAccounts();
    const addAccountButton = document.getElementById("newAccountButton");
    const deck = document.getElementById("accountsCardDeck");
    deck.textContent = '';

    accounts.forEach(account => {
        let card = document.createElement("div");
        card.className = "card account card-body border-2 shadow rounded";

        let button = document.createElement("button");
        button.setAttribute("type", "button");
        button.className = "btn";
        button.onclick = function () {openAccountEditModal(account);}

        let name = document.createElement("p");
        name.innerText = account.name;
        button.appendChild(name);

        let number = document.createElement("p");
        number.innerText = getFormattedAccountNumber(account.number)
        button.appendChild(number);

        let balance = document.createElement("p");
        balance.innerText = account.balance + " " + account.currency;
        balance.className = "text-success";
        button.appendChild(balance);

        card.appendChild(button);
        deck.appendChild(card);
    });

    deck.appendChild(addAccountButton);
}

function getFormattedAccountNumber (number) {
    return "**** " + number.substring(number.length-4);
}

function openAccountEditModal (account) {
    currentEditedAccount = account;

    let number = document.getElementById("accountNumber");
    let balance = document.getElementById("accountBalance");
    let name = document.getElementById("accountEditName");
    let currency = document.getElementById("accountEditCurrency");

    if (currentEditedAccount !== undefined) {
        number.hidden = balance.hidden = false;
        number.innerText = account.number;
        balance.innerText = account.balance + ' ' + account.currency;
        name.value = account.name;
        currency.innerText = account.currency;
    } else {
        number.hidden = balance.hidden = true;
        name.value = "";
        currency.value = "";
    }

    accountModal = new bootstrap.Modal(document.getElementById('accountEditModal'), {});
    accountModal.show();
}

async function saveAccount () {
    let fd = new FormData(document.getElementById("accountEditForm"));
    fd.append('currency', document.getElementById("accountEditCurrency").innerText);

    let response;
    if (currentEditedAccount === undefined) { // creating new account
        response = await fetch('/accounts', {method:'post', body: fd});
    } else {   // editing existing account
        response = await fetch('/accounts/' + currentEditedAccount.id, {method:'put', body: fd});
    }

    currentEditedAccount = undefined;
    accountModal.hide();

    if (response !== undefined) {
        if (response.ok) {
            await fillAccountsCardDeck();
            //return response.json();
        }
    }
}

async function deleteAccount () {
   let response = await fetch('/accounts/' + currentEditedAccount.id, {method:'delete'});

    if (response !== undefined) {
        if (response.ok) {
            await fillAccountsCardDeck();
        }
    }

    accountModal.hide();
    currentEditedAccount = undefined;
}
