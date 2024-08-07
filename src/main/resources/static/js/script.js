
function onCLickSearch() {
    let search = document.getElementById("inputSearch");
    let query = search.value;
    window.location.href = 'search.html?query=' + query;
}

function getQuery() {
    let auxSplit = window.location.href.split('query=');
    let query = auxSplit[1];
    console.log(query);
    return query;
}

async function getPages() {
    let query = getQuery();
    let url = "http://localhost:8080/api/search?query=" + query;
    let response = await fetch(url);
    let json = await response.json();
    console.log(json);
    return json;
}
