
var chartData2 = [
    { type: "Afval",            aantal: (Math.floor((Math.random()*100)+1))},
    { type: "Obstakels",        aantal: (Math.floor((Math.random()*100)+1))},
    { type: "Baanproblemen",    aantal: (Math.floor((Math.random()*100)+1))},
    { type: "Buurtevenementen", aantal: (Math.floor((Math.random()*100)+1))},
    { type: "Buurtacties",      aantal: (Math.floor((Math.random()*100)+1))},
];
var chart;

var chartData = [{ country: "Czech Republic", litres: 156.90 },
{ country: "Ireland", litres: 131.10 },
{ country: "Germany", litres: 115.80 },
{ country: "Australia", litres: 109.90 },
{ country: "Austria", litres: 108.30 },
{ country: "UK", litres: 99.00 }];

window.onload = function () {
    chart = new AmCharts.AmRadarChart();
    chart.dataProvider = chartData;
    chart.categoryField = "country";
    chart.startDuration = 2;

    var valueAxis = new AmCharts.ValueAxis();
    valueAxis.axisAlpha = 0.15;
    valueAxis.minimum = 0;
    valueAxis.dashLength = 3;
    valueAxis.axisTitleOffset = 20;
    valueAxis.gridCount = 5;
    chart.addValueAxis(valueAxis);

    var graph = new AmCharts.AmGraph();
    graph.valueField = "litres";
    graph.bullet = "round";
    graph.balloonText = "[[value]] litres of beer per year"
    chart.addGraph(graph);
    

    chart.write("chartdiv");
    $('text').parent().remove();
}