const margin = {left: 50, top: 30, right: 20, bottom: 30};
const vpHeight = document.getElementById("timeline").clientHeight - margin.bottom - margin.top;
const vpWidth = document.getElementById("timeline").clientWidth - margin.left - margin.right;

var dateScale = d3.scaleTime()
    .domain(d3.extent(articles, d => d.date))
    .range([0, vpHeight]);

var interestScale = d3.scaleLinear()
    .domain([0.0, 1.0])
    .range([0, vpWidth]);

var svg = d3.select("#timeline").append("svg");
svg.attr("class", "explorer--view");

var g = svg.append("g")
    .attr("transform", `translate(${margin.left}, ${margin.top})`);

var currentArticle = "start";

var dateAxisG = g.append("g")
    .attr("class", "axis")
    .attr("transform", "translate(0, 0)");

var dateAxis = d3.axisLeft(dateScale)

function redraw(articles) {
    var articleNodes = g.selectAll("circle").data(articles);

    articleNodes.enter().append("circle")
        .attr("cx", d => interestScale(similarity(d)))
        .attr("cy", d => dateScale(d.date))

        .attr("r", 20)

        .attr("fill", d => colorFromWeights(d))
        .attr("stroke-width", 1)

        .on("click", d => viewArticle(d));

    articleNodes
        .attr("stroke", d => d.id === currentArticle.id ? "red" : "black")

    dateAxisG.call(dateAxis);
}

function colorFromWeights(article) {
    return `rgb(${Math.round(article.weights.politics * 255)}, ${Math.round(article.weights.history * 255)}, ${Math.round(article.weights.currentEvents * 255)})`;
}

function similarity(article) {
    const articleVector = vectorNormalize(weightsToVector(article.weights));
    const userVector = vectorNormalize(weightsToVector(user.preferences));

    return dotProduct(articleVector, userVector);
}

function vectorNormalize(v) {
    const len = Math.sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]);

    return [v[0] / len, v[1] / len, v[2] / len];
}

function dotProduct(v1, v2) {
    return v1[0] * v2[0] + v1[1] * v2[1] + v1[2] * v2[2];
}

function weightsToVector(weights) {
    return [weights.politics, weights.history, weights.currentEvents];
}

function viewArticle(article) {
    if (user.read.indexOf(article.id) === -1) {
        user.read.push(article.id);
    }

    currentArticle = article;
    renderArticle();
}

function renderArticle() {
    document.getElementById("article-header").innerHTML = currentArticle.title;
    document.getElementById("article").innerHTML = currentArticle.flavour;
}

currentArticle = articles.find(a => a.id == "start");
redraw(articles);
renderArticle();
