//Output
var outputDiv = document.getElementById("cars-data-div");

//Buttons
var buttonPrice = document.getElementById("button-price");

var buttonYear = document.getElementById("button-year");

var buttonMake = document.getElementById("button-make");

var buttonAll = document.getElementById("button-all");

var buttonID = document.getElementById("button-id");


//Eventlisteners and functions

buttonAll.addEventListener("click", function () {
    let url = "/CA-1/api/car/all";
    getCars(url);
});

buttonID.addEventListener("click", function () {
    var ID = document.getElementById("input-id").value;
    let url = "/CA-1/api/car/"+ID+"/allInfo";
    getCarByID(url);
});

buttonPrice.addEventListener("click", function () {
    var price = document.getElementById("input-price").value;
    let url = "/CA-1/api/car/all";
    getCars(url, filterPrice, price);
});

buttonYear.addEventListener("click", function () {
    var year = document.getElementById("input-year").value;
    let url = "/CA-1/api/car/all";
    getCars(url, filterYear, year);
});

buttonMake.addEventListener("click", function () {
    var make = document.getElementById("input-make").value;
    let url = "/CA-1/api/car/all";
    getCars(url, filterMake, make);
});


function filterMake(array, filter){return array.filter( car => car.Make === filter )};

function filterYear(array, filter){return array.filter( car => car.Year >= filter )};

function filterPrice(array, filter){return array.filter( car => car.Price <= filter )};

function getCars(url, callback, filter) {
    fetch(url)
    .then(res => res.json()) //in flow1, just do it
    .then(data => {
        // Inside this callback, and only here, the response data is available
        // Take objects out of array. 
        let arrayOfObj = Object.values(data);
        if (typeof callback === "function")
        {
            console.log(arrayOfObj);
            var temp = callback(arrayOfObj, filter);
            console.log(temp);
            arrayOfObj = temp;
        }
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

function getCarByID(url) {
    fetch(url)
    .then(res => res.json()) //in flow1, just do it
    .then(data => {
        // Inside this callback, and only here, the response data is available
        let arrayOfObj = Object.values(data);
        // Make Table Header
        HTML = tableHeader(Object.keys(data));

        // Make Table Body
        let bodyArray = Object.values(arrayOfObj);
        HTML += tableRow(bodyArray);

        // End Table and Return it 
        HTML += endOfTable();
        outputDiv.innerHTML = HTML;
    });
}


//Checkbox for admin
var adminCheckBox = document.getElementById("check-admin");
adminCheckBox.addEventListener("click", function () {
    if (adminCheckBox.checked === false)
    {
        document.getElementById("admin-div").setAttribute("hidden", "hidden");
    } else
    {
        document.getElementById("admin-div").removeAttribute("hidden");
    }
});