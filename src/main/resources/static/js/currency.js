fillCurrencyList ();

async function fillCurrencyList () {
    const currencies = await getCurrencies();
    const list = document.getElementById("currencyList");

    currencies.forEach(currency => {
        let item = document.createElement("div");
        item.className = "dropdown-item";
        item.onclick = function () {
            document.getElementById("accountEditCurrency").innerText = currency;
        }

        let name = document.createElement("p");
        name.innerText = currency;
        item.appendChild(name);

        list.appendChild(item);
    })
}

async function getCurrencies () {
    const response = await fetch('/currency', {method:'get'});

    if (response !== undefined) {
        if (response.ok) {
            return response.json();
        }
    }
}