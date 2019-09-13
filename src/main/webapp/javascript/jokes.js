window.onload = function () {
    let url = "/CA-1/api/jokes/";

    /**
     * For getting a single joke by ID
     * @type type
     */
    document.getElementById("btnFetchId").addEventListener("click", function (e) {
        //get requested ID value
        let id = document.getElementById("idInput").value;
        fetch(url + id)
                .then(res => res.json())
                .then(data => {
                    // Inside this callback, and only here, the response data is available
                    document.getElementById("idOutput").innerHTML = data.joke;
                    document.getElementById("idInput").value = ""; //reset value field.
                });

    });

    /**
     * For getting a random joke
     * @type type
     */
    document.getElementById("btnFetchRandom").addEventListener("click", function (e) {
        fetch(url + "random")
                .then(res => res.json())
                .then(data => {
                    // Inside this callback, and only here, the response data is available
                    document.getElementById("randomOutput").innerHTML = JSON.stringify(data, null, 1);
                });

    });

    /**
     * For getting a random joke AS DTO
     * @type type
     */
    document.getElementById("btnFetchRandomDTO").addEventListener("click", function (e) {
        fetch(url + "random/dto")
                .then(res => res.json())
                .then(data => {
                    // Inside this callback, and only here, the response data is available
                    document.getElementById("randomOutputDTO").innerHTML = JSON.stringify(data, null, 2);
                });

    });

    /**
     * Retrieve all jokes and put it in a table.
     */
    document.getElementById("btnFetchAll").addEventListener("click", function (e) {
        fetch(url + "all")
                .then(res => res.json())
                .then(data => {
                    // Inside this callback, and only here, the response data is available
             
                        // Take objects out of array. 
                        let arrayOfObj = Object.values(data);
                        // Make Table Header
                        let returnString = tableHeader(Object.keys(arrayOfObj[0])); //any of the objects will do

                        // Make Table Body
                        for (let i = 0; i < arrayOfObj.length; i++) {
                            let bodyArray = Object.values(arrayOfObj[i]);
                            returnString += tableRow(bodyArray);
                        }

                        // End Table and Return it 
                         returnString += endOfTable();

                    document.getElementById("allJokes").innerHTML = returnString;
                });

    });
    
    /**
     * Retrieve all jokes and put it in a table, but this time as DTO.
     */
    document.getElementById("btnFetchAllDTO").addEventListener("click", function (e) {
        fetch(url + "all/dto")
                .then(res => res.json())
                .then(data => {
                    // Inside this callback, and only here, the response data is available
             
                        // Take objects out of array. 
                        let arrayOfObj = Object.values(data);
                        // Make Table Header
                        let returnString = tableHeader(Object.keys(arrayOfObj[0])); //any of the objects will do

                        // Make Table Body
                        for (let i = 0; i < arrayOfObj.length; i++) {
                            let bodyArray = Object.values(arrayOfObj[i]);
                            returnString += tableRow(bodyArray);
                        }

                        // End Table and Return it 
                         returnString += endOfTable();

                    document.getElementById("allJokes").innerHTML = returnString;
                });

    });



    /**
     * Secret extra stuff
     * @type type
     */
    document.getElementById("btnFetchChuck").addEventListener("click", function (e) {
        fetch("https://api.icndb.com/jokes/random")
                .then(res => res.json())
                .then(data => {
                    // Inside this callback, and only here, the response data is available
                    document.getElementById("chuckOutput").innerHTML = JSON.stringify(data.value.joke, null, 2);
                });

    });


};