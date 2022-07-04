let currentEditedAccount;

document.onload = fillAccountsCardDeck();

async function fillAccountsCardDeck () {
    let user = await getAuthorizedUser();
    const deck = document.getElementById("accountsCardDeck");

    console.log(user)
    console.log(user.accounts)
    user.accounts.forEach(account => {
        console.log(account);
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
}

function getFormattedAccountNumber (number) {
    return "**** " + number.substring(number.length-4);
}

function openAccountEditModal (account) {
    currentEditedAccount = account;

    if (currentEditedAccount !== undefined) {
        document.getElementById("accountEditNumber").innerHTML = account.number;
        document.getElementById("accountEditName").value = account.name;
        document.getElementById("accountEditCurrency").value = account.currency;
    }

    const accountModal = new bootstrap.Modal(document.getElementById('accountEditModal'), {});
    accountModal.show();
}

async function saveAccount () {
    let form = document.getElementById("accountEditForm");

    let account;

    if (currentEditedAccount === undefined) { // creating new account
        account = await fetch('/accounts', {method:'post', body: new FormData(form)});
    } else {   // editing existing account
        account = await fetch('/accounts/' + currentEditedAccount.id, {method:'put', body: new FormData(form)});
    }

    console.log(account);
    const accountModal = new bootstrap.Modal(document.getElementById('accountEditModal'), {});
    accountModal.hide();
    currentEditedAccount = undefined;
}

function deleteAccount () {
    const accountModal = new bootstrap.Modal(document.getElementById('accountEditModal'), {});
    accountModal.hide();
    currentEditedAccount = undefined;
}