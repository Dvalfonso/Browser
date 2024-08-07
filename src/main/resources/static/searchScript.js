async function renderResults() {
    let pages = await getPages();
    let html = '';
    for (let page of pages) {
        html += getPagesHtml(page);
    }
    let results = document.getElementById('results');
    results.innerHTML = html;
    loadQueryInSearch();
}

function getPagesHtml(page) {
    return `
        <div class="result-container">
            <div class="result-header">
                <div class="domain">${page.domain} <img class="picture" src="${loadPicture(page)}"></div>
                <div class="url"><a href="${page.url}">${page.url}</a></div>
            </div>
            <div class="result-body">
                <h2 class="title">${page.title}</h2>
                <p class="description">${page.description}</p>
            </div>
        </div>
    `;
}

function loadPicture(page) {
    if (page.picture == "") {
        page.picture = `img/web.png`;
    }
    return page.picture;
}

function loadQueryInSearch() {
    let query = getQuery();
    document.getElementById('actualQuery').value = query;
}

function getQuery() {
    let auxSplit = window.location.href.split('query=');
    let query = auxSplit[1];
    console.log(query);
    return query;
}

function onClickReSearch() {
    let newQuery = document.getElementById('actualQuery').value;
    window.location.href = 'search.html?query=' + newQuery;
}

renderResults();