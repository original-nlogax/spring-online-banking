var currentEditAccountId;

function openAccountEditModal (accountId, name, currency) {
    if (accountId === undefined) currentEditAccountId = -1;
    else currentEditAccountId = accountId;

    document.getElementById("accountEditName").value = name;
    document.getElementById("accountEditCurrency").value = currency;

    const accountModal = new bootstrap.Modal(document.getElementById('accountEditModal'), {});
    accountModal.show();
}

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
}