fillCurrencyList ();

async function fillCurrencyList () {
    const currencies = await getCurrencies();
    const list = document.getElementById("currencyList");

    currencies.forEach(currency => {
        let item = document.createElement("div");
        item.className = "dropdown-item";
        item.onclick = function () {setCurrency(currency);}

        let name = document.createElement("p");
        name.innerText = currency;
        item.appendChild(name);

        list.appendChild(item);
    })
}

function setCurrency (currency) {
    document.getElementById("accountEditCurrency").innerHTML = currency;
    document.getElementById("accountEditCurrencyInput").value = currency;   // hidden input, don't like how it works
}

async function getCurrencies () {
    const response = await fetch('/currency', {method:'get'});

    if (response !== undefined) {
        if (response.ok) {
            return response.json();
        }
    }
}