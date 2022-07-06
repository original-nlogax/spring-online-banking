let currentEditedAccount;
let accountModal;

fillAccountsCardDeck();

async function fillAccountsCardDeck () {
    let user = await getAuthorizedUser();
    console.log(user.accounts)
    const deck = document.getElementById("accountsCardDeck");

    let addAccountButton;
    deck.childNodes.forEach(function(card) {
        if (card.id === "newAccountButton") addAccountButton = card;
    });
    deck.textContent = '';

    user.accounts.forEach(account => {
        /*<button type="button" className="btn"
                th:attr="onclick=|openAccountEditModal('${account}', '${account.getId()}', '${account.getNumber()}', '${account.getName()}', '${account.getCurrency()}')|">
            <p th:text="${account.getName()}"></p>
            <p th:text="${account.getFormattedNumber()}"></p>
            <span className="text-success" th:text="${account.getBalance()} + ' ' + ${account.getCurrency()}"></span>
        </button>*/

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
        balance.setAttribute("className", "text-success");
        balance.innerText = account.balance + " " + account.currency;
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

    let number = document.getElementById("accountEditNumber");
    let name = document.getElementById("accountEditName");
    let currency = document.getElementById("accountEditCurrency");

    if (currentEditedAccount !== undefined) {
        number.hidden = false;
        number.innerHTML = account.number;
        name.value = account.name;
        currency.value = account.currency;
    } else {
        number.hidden = true;
        name.value = "";
        currency.value = "";
    }

    accountModal = new bootstrap.Modal(document.getElementById('accountEditModal'), {});
    accountModal.show();
}

async function saveAccount () {
    let form = document.getElementById("accountEditForm");

    let response;

    if (currentEditedAccount === undefined) { // creating new account
        response = await fetch('/accounts', {method:'post', body: new FormData(form)});
    } else {   // editing existing account
        response = await fetch('/accounts/' + currentEditedAccount.id, {method:'put', body: new FormData(form)});
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