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

buttonSortPrice.addEventListener("click", function () {
    if (document.getElementById("data-table") !== null)
    {
        sortTable(4, true);
    }
});

buttonSortYear.addEventListener("click", function () {
    if (document.getElementById("data-table") !== null)
    {
        sortTable(1, true);
    }
});

buttonSortMake.addEventListener("click", function () {
    if (document.getElementById("data-table") !== null)
    {
        sortTable(2, false);
    }
});

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

function filterMake(array, filter){return array.filter( car => car.Make.toLowerCase() === filter.toLowerCase() )};

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
            var temp = callback(arrayOfObj, filter);
            arrayOfObj = temp;
        }
        if (typeof arrayOfObj[0] === "undefined")
        {
            outputDiv.innerHTML = "";
            return;
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


//Sorting code
function sortTable(n, dataIsNumbers) {
    var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
    table = document.getElementById("data-table");
    switching = true;
    //Set the sorting direction to ascending:
    dir = "asc";
    /*Make a loop that will continue until
     no switching has been done:*/
    while (switching) {
        //start by saying: no switching is done:
        switching = false;
        rows = table.rows;
        /*Loop through all table rows (except the
         first, which contains table headers):*/
        for (i = 1; i < (rows.length - 1); i++) {
            //start by saying there should be no switching:
            shouldSwitch = false;
            /*Get the two elements you want to compare,
             one from current row and one from the next:*/
            x = rows[i].getElementsByTagName("TD")[n];
            y = rows[i + 1].getElementsByTagName("TD")[n];
            /*Checking if the rows contain strings or numbers*/
            if (dataIsNumbers)
            {
                /*check if the two rows should switch place,
                 based on the direction, asc or desc:*/
                if (dir == "asc") {
                    
                    if (parseInt(x.innerHTML, 10) > parseInt(y.innerHTML, 10)) {
                        //if so, mark as a switch and break the loop:
                        shouldSwitch = true;
                        break;
                    }
                } else if (dir == "desc") {
                    if (parseInt(x.innerHTML, 10) < parseInt(y.innerHTML, 10)) {
                        //if so, mark as a switch and break the loop:
                        shouldSwitch = true;
                        break;
                    }
                }
            } else {
                /*check if the two rows should switch place,
                 based on the direction, asc or desc:*/
                if (dir == "asc") {
                    if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                        //if so, mark as a switch and break the loop:
                        shouldSwitch = true;
                        break;
                    }
                } else if (dir == "desc") {
                    if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
                        //if so, mark as a switch and break the loop:
                        shouldSwitch = true;
                        break;
                    }
                }
            }
        }
        if (shouldSwitch) {
            /*If a switch has been marked, make the switch
             and mark that a switch has been done:*/
            rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
            switching = true;
            //Each time a switch is done, increase this count by 1:
            switchcount++;
        } else {
            /*If no switching has been done AND the direction is "asc",
             set the direction to "desc" and run the while loop again.*/
            if (switchcount == 0 && dir == "asc") {
                dir = "desc";
                switching = true;
            }
        }
    }
}