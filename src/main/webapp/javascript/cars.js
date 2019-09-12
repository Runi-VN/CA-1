//Output
var outputDiv = document.getElementById("cars-data-div");

//Buttons
var buttonPrice = document.getElementById("button-price");

var buttonYear = document.getElementById("button-year");

var buttonMake = document.getElementById("button-make");

var buttonAll = document.getElementById("button-all");

//Eventlisteners and functions

buttonAll.addEventListener("click", function () {
    let url = "/CA-1/api/car/all";
    getAllCars(url);
});

function getAllCars(url) {
    fetch(url)
    .then(res => res.json()) //in flow1, just do it
    .then(data => {
        // Inside this callback, and only here, the response data is available
        // Take objects out of array. 
        let arrayOfObj = Object.values(data);
        // Make Table Header
        HTML = tableHeader(Object.keys(arrayOfObj[0]));

        // Make Table Body
        for (let i = 0; i < arrayOfObj.length; i++) {
            let bodyArray = Object.values(arrayOfObj[i]);
            HTML += tableRow(bodyArray);
        }

        // End Table
        HTML += endOfTable();
        outputDiv.innerHTML = HTML;
    });
}