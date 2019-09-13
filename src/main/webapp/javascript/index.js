document.addEventListener("DOMContentLoaded", function () {
        students();  
        makeLinks();
});


function students() {
    let url = "/CA-1/api/students/allstudents";

    fetch(url)
        .then(res => res.json())
        .then(jsondata => {
            let table = document.querySelector("table");
            let data = Object.keys(jsondata[0]);
            tableHead(table, data);
            tableData(table, jsondata);
        });
}

function tableHead(table, data) {
    let head = table.createTHead();
    let row = head.insertRow();
    for (let key of data) {
        let th = document.createElement("th");
        let text = document.createTextNode(key);
        th.appendChild(text);
        row.appendChild(th);
    }

}
function tableData(table, data) {
    for (let element of data) {
        let row = table.insertRow();
        for (key in element) {
            let cell = row.insertCell();
            let text = document.createTextNode(element[key]);
            cell.appendChild(text);
        }
    }
}

function makeLinks (){
    if(document.querySelector("td").includes("github")){
        alert("fandt github");
    }

}



//function makeLinks (){
//    if(document.querySelector("td").includes("github")){
//        document.querySelector("td").includes("github").bind("click",function() {
//      location = "github.com";
//   });
//    }
//}

//<a href="groupContract.html" id="contracthtml">Group Contract</a>

//("td#my_clickable_cell").bind("click",function() {
//      location = "url to go to when clicked";
//   });