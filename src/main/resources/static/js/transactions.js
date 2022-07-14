let currentFromAccountNumber;
let paymentModal;

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

async function fillTransactionHistory () {
    const transactions = await getTransactions();
    const list = document.getElementById("transactionsList");
    list.innerText = '';

    let i = 1;
    transactions.forEach(transaction => {
        //let tr = document.createElement("tr");
        //tr.inner
        //item.innerHTML = getFormattedAccountNumber(account.number) + ' ' + account.name +
        //    " <span class='text-success'>(" + account.balance + " " + account.currency + ")</span>";
        let item = document.createElement("tr");
        if (transaction !== undefined) {
            item.innerHTML =
                "<th scope='row'>"+ i +"</th>" +
                "<td>" + transaction.date + "</td>" +
                "<td>" + getFormattedAccountNumber(transaction.numberFrom) + "</td>" +
                "<td>" + getFormattedAccountNumber(transaction.numberTo) + "</td>" +
                "<td class='text-success'>" + transaction.amount + " " + transaction.currency + "</td>"

            list.appendChild(item);
            i++;
        }
    });
}

async function getTransactions () {
    let response = await fetch('/transactions/user/', {method:'get'});
    if (response !== undefined) {
        if (response.ok) {
            return response.json();
        }
    }
}

async function transfer () {
    let fd = new FormData(document.getElementById("paymentForm"));
    fd.append('numberFrom', currentFromAccountNumber);

    let response = await fetch('/transactions', {method:'post', body: fd});
    if (response !== undefined) {
        if (response.ok) {
            console.log("Successful transaction");
            fillTransactionHistory();
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