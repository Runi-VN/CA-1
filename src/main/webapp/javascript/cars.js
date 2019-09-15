//Output
var outputDiv = document.getElementById("cars-data-div");

//Buttons
var buttonPrice = document.getElementById("button-price");

var buttonYear = document.getElementById("button-year");

var buttonMake = document.getElementById("button-make");

var buttonAll = document.getElementById("button-all");

var buttonID = document.getElementById("button-id");

var buttonSortPrice = document.getElementById("button-price-sort");

var buttonSortYear = document.getElementById("button-year-sort");

var buttonSortMake = document.getElementById("button-make-sort");


//Eventlisteners and functions

var sortCounter = 0;

buttonSortPrice.addEventListener("click", function () {
    var price = document.getElementById("input-price").value;
    let url = "/CA-1/api/car/all";
    if (isNaN(price) || price == "")
    {
        getCars(url, null, null, sortPrice);
        return;
    }
    if (sortCounter === 0)
    {
        getCars(url, filterPrice, price, sortPrice);
    } else
    {
        getCars(url, filterPrice, price, sortPrice);
    }
});

buttonSortYear.addEventListener("click", function () {
    var year = document.getElementById("input-year").value;
    let url = "/CA-1/api/car/all";
    if (isNaN(year) || year == "")
    {
        getCars(url, null, null, sortYear);
        return;
    }
    if (sortCounter === 0)
    {
        getCars(url, filterYear, year, sortYear);
    } else
    {
        getCars(url, filterYear, year, sortYear);
    }
});

buttonSortMake.addEventListener("click", function () {
    var make = document.getElementById("input-make").value;
    let url = "/CA-1/api/car/all";
    if (make == "")
    {
        getCars(url, null, null, sortMake);
        return;
    }
    if (sortCounter === 0)
    {
        getCars(url, filterMake, make, sortMake);
    } else
    {
        getCars(url, filterMake, make, sortMake);
    }
});

buttonAll.addEventListener("click", function () {
    let url = "/CA-1/api/car/all";
    getCars(url);
    sortCounter = 0;
});

buttonID.addEventListener("click", function () {
    var ID = document.getElementById("input-id").value;
    let url = "/CA-1/api/car/"+ID+"/allInfo";
    getCarByID(url);
    sortCounter = 0;
});

buttonPrice.addEventListener("click", function () {
    var price = document.getElementById("input-price").value;
    let url = "/CA-1/api/car/all";
    getCars(url, filterPrice, price);
    sortCounter = 0;
});

buttonYear.addEventListener("click", function () {
    var year = document.getElementById("input-year").value;
    let url = "/CA-1/api/car/all";
    getCars(url, filterYear, year);
    sortCounter = 0;
});

buttonMake.addEventListener("click", function () {
    var make = document.getElementById("input-make").value;
    let url = "/CA-1/api/car/all";
    getCars(url, filterMake, make);
    sortCounter = 0;
});

function filterMake(array, filter){return array.filter( car => car.Make.toLowerCase() === filter.toLowerCase() )};

function sortMake(array){return array.sort( (a, b) => a.Make.toUpperCase().localeCompare(b.Make.toUpperCase()) );};

function filterYear(array, filter){return array.filter( car => car.Year >= filter )};

function sortYear(array){return array.sort( (a, b) => a.Year < b.Year );};

function filterPrice(array, filter){return array.filter( car => car.Price <= filter )};

function sortPrice(array){return array.sort( (a, b) => a.Price < b.Price );};

function getCars(url, callback, filter, callback2) {
    fetch(url)
    .then(res => res.json()) //in flow1, just do it
    .then(data => {
        // Inside this callback, and only here, the response data is available
        // Take objects out of array. 
        let arrayOfObj = Object.values(data);
        if (typeof callback === "function")
        {
            var temp = callback(arrayOfObj, filter);
            arrayOfObj = temp;
        }
        if (typeof callback2 === "function")
        {
            if (sortCounter === 0)
            {
                arrayOfObj = callback2(arrayOfObj);
                sortCounter = 1;
            } else
            {
                arrayOfObj = callback2(arrayOfObj);
                arrayOfObj = arrayOfObj.reverse();
                sortCounter = 0;
            }
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
