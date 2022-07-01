var currentPayFromAccountId;

// fixme parameters: needs to be either only account, or all it's properties
function selectPaymentAccount (accountId, formattedNumber, name, balance, currency) {
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

/*

function saveAccountData () {
    // fixme weird
    const form = document.getElementById('accountEditForm');//retrieve the form as a DOM element
    const input = document.createElement('input');//prepare a new input DOM element

    input.setAttribute('name', "id");//set the param name
    input.setAttribute('value', currentEditAccountId);//set the value
    input.setAttribute('type', "hidden")//set the type, like "hidden" or other
    form.appendChild(input);//append the input to the form
    form.submit();//send with added input

    const accountModal = new bootstrap.Modal(document.getElementById('accountEditModal'), {});
    accountModal.hide();
    currentEditAccountId = -1;
}

function deleteAccount () {

    accountModal.hide();
    currentEditAccountId = -1;
}*/