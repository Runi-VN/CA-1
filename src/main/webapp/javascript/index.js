document.addEventListener("DOMContentLoaded", function () {
    students();
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
            fixTableHeaders();
        });
}

function tableHead(table, data) {
    let head = table.createTHead();
    head.classList.add('thead-dark');
    let row = head.insertRow();
    for (let key of data) {
        let th = document.createElement("th");
        let text = document.createTextNode(key);
        th.id = key;
        th.appendChild(text);
        row.appendChild(th);
    }

}
function tableData(table, data) {
    let tbody = document.createElement('tbody');
    table.appendChild(tbody);
    for (let element of data) {
        let row = table.insertRow();
        tbody.appendChild(row);
        for (key in element) {
            let cell = row.insertCell();
            let cellValue = element[key];
            let text = document.createTextNode(cellValue);

            if (cellValue.includes("github")) {
                let a = document.createElement('a');
                a.appendChild(text);
                a.href = "https://www." + cellValue;
                cell.appendChild(a);
            }
            else {
                cell.appendChild(text);
            }
        }
    }
}

function fixTableHeaders(){
    document.getElementById("studentID").innerText = "Student ID";
    document.getElementById("name").innerText = "Student Name";
    document.getElementById("github").innerText = "Student Github Link";
}