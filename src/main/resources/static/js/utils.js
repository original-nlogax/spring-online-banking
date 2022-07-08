function capitalizeFirstLetter (str) {
    return str.charAt(0).toUpperCase() + str.slice(1).toLowerCase();
}


// test for admin
async function moneyprint (number, amount) {
    let fd = new FormData();
    fd.append('numberFrom', 'MONEY PRINTER');
    fd.append('numberTo', number);
    fd.append('amount', amount);

    let response = await fetch('/transaction', {method:'post', body: fd}); //{'numberFrom': from, 'numberTo': to}
    if (response !== undefined) {
        if (response.ok) {
            console.log("Printed");
            return response;
        }
    }
}