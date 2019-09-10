/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// TABLE HELPER FUNCTIONS - TABLE UTILITY

// START OF TABLE CREATION - USE THE FUNCTIONS IN ORDER:
// ADD THEM TO A STRING IN THE ORDER SEEN HERE. 

/*
EXAMPLE USAGE WHERE data IS AN ARRAY OF OBJECTS.

function getAllTable(data) {
    // Take objects out of array. 
    let arrayOfObj = Object.values(data);
    // Make Table Header
    returnString = tableHeader(Object.keys(arrayOfObj[0]));

    // Make Table Body
    for (let i = 0; i < arrayOfObj.length; i++) {
        let bodyArray = Object.values(arrayOfObj[i]);
        returnString += tableRow(bodyArray);
    }

    // End Table and Return it 
    return returnString += endOfTable();
}
 */

// 1)
/**
 * MAKES A TABLE HEADER. 
 * HAND IT AN ARRAY OF VALUES FOR A TABLE HEADER ROW.
 * Example call: tableHeader(Object.keys(obj))
 * @param {type} array
 * @returns {String}
 */
function tableHeader(array) {
    let returnString = "<table class=\"table\"><thead><tr>";
    array.forEach(element => returnString += "<th scope=\"col\">" + element + "</th>");
    return returnString + "</tr></thead><tbody>";
}

// 2)
/**
 * MAKES A SINGLE TABLE ROW. 
 * HAND IT AN ARRAY OF VALUES FOR A TABLE BODY ROW.
 * CALL THIS FOR EACH ROW IN THE TABLE. 
 * Example call: tableRow(Object.values(obj))
 * @param {type} array
 * @returns {String}
 */
function tableRow(array) {
    let returnString = "<tr>";
    array.forEach(element => returnString += "<td>" + element + "</td>");
    return returnString + "</tr>";
}

// 3)
/**
 * END OF TABLE.
 * CALL THIS WHEN YOU'RE DONE MAKING ROWS.
 * Example call: return tableString + endOfTable();
 * @returns {String}
 */
function endOfTable() {
    return "</tbody></table>";
}

// END OF TABLE HELPER UTIL FUNCTIONS
