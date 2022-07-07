let currentFromAccountNumber;
let paymentModal;

fillPaymentAccountsList ();

async function fillPaymentAccountsList () {
    const accounts = await getAccounts();
    const list = document.getElementById("accountList");

    accounts.forEach(account => {
        let item = document.createElement("li");
        item.className = "dropdown-item";
        item.onclick = function () {setPaymentAccount(account)};
        item.innerHTML = getFormattedAccountNumber(account.number) + ' ' + account.name +
            " <span class='text-success'>(" + account.balance + " " + account.currency + ")</span>";

        list.appendChild(item);
    })
}

async function transfer () {
    let fd = new FormData(document.getElementById("paymentForm"));
    fd.append('numberFrom', currentFromAccountNumber);


    let response = await fetch('/transaction', {method:'post', body: fd}); //{'numberFrom': from, 'numberTo': to}
    if (response !== undefined) {
        if (response.ok) {
            console.log("Successful transaction");
            paymentModal.hide();
            return response;
        }
    }
}

function setPaymentAccount (account) {
    document.getElementById("currencySymbol").innerText = account.currency;
    document.getElementById("accountDropdownButton").innerHTML =
        getFormattedAccountNumber(account.number) + ' ' + account.name +
        " <span class='test-success'>(" + account.balance + " " + account.currency + ")</span>";

    currentFromAccountNumber = account.number;
}

function openPaymentModal () {
    paymentModal = new bootstrap.Modal(document.getElementById('paymentModal'), {});
    paymentModal.show();
}