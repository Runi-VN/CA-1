/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// Contains JavaScript for groupContract.jsp

// Only starts once the window has loaded, 
// so we know everything is ready for JavaScript. 
window.onload = function () {

    /* VIEW BY NAME START */

    function showData(data) {

        let returnString = tableHeader([data.name]);

        let done = Object.values(data.done);
        for (var i = 0; i < done.length; i++) {
            let doneItem = [done[i]];
            returnString += tableRow(doneItem);
        }

        return returnString += endOfTable();

    }

    // Get viewByName button
    let viewByName = document.getElementById("viewByName");

    // Listener for click. If clicked, populate OutPutReceiver with Data. 
    viewByName.addEventListener("click", function () {

        // Get Name in input field . 
        let name = document.getElementById("nameToView").value;

        // URL OF GET NAME REST ENDPOINT
        let URL = "/CA-1/api/work/" + name;

        // GET DATA
        fetch(URL)
                .then(res => res.json())
                .then(data => {

                    // Get OutPutReceiver
                    let OutPutReceiver = document.getElementById("OutPutReceiver");

                    // Inside this callback, and only here, the response data is available
                    OutPutReceiver.innerHTML = showData(data);
                });
    });

    /* VIEW BY NAME END */

    /* VIEW ALL START */

    function showAllData(data) {
        let students = Object.values(data);

        let returnString = "";

        students.forEach(element => returnString += showData(element));

        return returnString;
    }

    // Get viewAll button
    let viewAll = document.getElementById("viewAll");

    // Listener for click. If clicked, populate OutPutReceiver with Data. 
    viewAll.addEventListener("click", function () {

        // URL OF GET ALL REST ENDPOINT
        let URL = "/CA-1/api/work/all";

        // GET DATA
        fetch(URL)
                .then(res => res.json())
                .then(data => {

                    // Get OutPutReceiver
                    let OutPutReceiver = document.getElementById("OutPutReceiver");

                    // Inside this callback, and only here, the response data is available
                    OutPutReceiver.innerHTML = showAllData(data);
                });
    });

    /* VIEW ALL END */
};
