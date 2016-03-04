var resultsCompact = document.getElementById('results-compact');

function showResults(resp) {
    console.log("show-results", resp);

    var data = JSON.parse(resp);
    var msg = "Response came from " + data[0] + " endpoint. Calls follow.";
    var entries = [msg].concat(data[1].map(function(el, i){
        return JSON.stringify(el);
    }));

    resultsCompact.innerHTML = "";
    for (var i=0; i<entries.length; i++) {
        var row = document.createElement('li');
        row.appendChild(document.createTextNode(entries[i]));
        resultsCompact.appendChild(row);
    }
}

function callFirstServer(id) {
    console.log("first-call", id);
    var r = new XMLHttpRequest();
    r.addEventListener("load", function() {
        showResults(this.responseText);
    });
    r.open("GET", "http://localhost:9201/" + id);
    // Invokes CORS
//    r.setRequestHeader('Xhr-Demo-Request', 'auth');
    r.send();
}

function runDemo() {
    var id = Math.random() + "";
    callFirstServer(id);
}

function addHooks() {
    document.getElementById('go').addEventListener('click', runDemo);
}

addHooks();
