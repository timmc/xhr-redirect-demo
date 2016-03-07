// var hostname is set in index.html

var resultsCompact = document.getElementById('results-compact');
var settingCustomHeader = document.getElementById('setting-custom-header');

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

function callFirstServer(id, useCustomHeader) {
    console.log("call-first", id);
    var r = new XMLHttpRequest();
    r.addEventListener("load", function() {
        showResults(this.responseText);
    });
    r.open("GET", "http://"+hostname+":9201/" + id);
    if (useCustomHeader) {
        // Invokes preflight check
        r.setRequestHeader('Xhr-Demo-Request', 'auth');
    }
    r.send();
}

function runDemo() {
    var id = Math.random() + "";
    callFirstServer(id, settingCustomHeader.checked);
}

function addHooks() {
    document.getElementById('go').addEventListener('click', runDemo);
}

addHooks();
