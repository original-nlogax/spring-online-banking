let currentPayFromAccountId;

function setPaymentAccount (accountId, formattedNumber, name, balance, currency) {
    document.getElementById("currencySymbol").innerHTML = currency;
    document.getElementById("accountDropdownButton").innerHTML =
        formattedNumber + ' ' + name + " <span class='test-success'>(" + balance + " " + currency + ")</span>";

    if (accountId === undefined) currentPayFromAccountId = -1;
    else currentPayFromAccountId = accountId;
}

function openPaymentModal (accountId, number, name, currency) {


    //document.getElementById("accountEditNumber").innerHTML = number;
    //document.getElementById("accountEditName").value = name;
    //document.getElementById("accountEditCurrency").value = currency;

    const accountModal = new bootstrap.Modal(document.getElementById('paymentModal'), {});
    accountModal.show();
}