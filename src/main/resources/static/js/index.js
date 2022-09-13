function chartSensor(sensorChart) {
    var dataxSensor = [];
    var dataySensor = [];
    $.getJSON("sensors/1/samples", function(data) {populate(data, sensorChart, 0)});
    $.getJSON("sensors/2/samples", function(data) {populate(data, sensorChart, 1)});
}

function populate(data, sensorChart, sensorId) {
    dataxSensor = data.map(function(x){
        return x.date_sampled.split(" ")[1];
    });
    dataySensor = data.map(function(x){
        return x.sample/10;
    });
    sensorChart.data.labels = dataxSensor;
    sensorChart.data.datasets[sensorId].data = dataySensor;
    sensorChart.data.datasets[sensorId].label = 'Temperature Core ' + sensorId;
    sensorChart.update('none');
}

$(function() {
    updateInterval = 2000;
    const ctx = document.getElementById('chartSensor').getContext('2d');
            const sensorChart = new Chart(ctx, {
                type: 'line',
                data: {
                    datasets: [{
                        label: 'Temperature Core 0',
                        borderColor: "#8e5ea2",
                        fill: false
                    },
                    {
                        label: 'Temperature Core 1',
                        borderColor: "#3e95cd",
                        fill: false
                    }
                    ]
                },
                options: {
                    elements: {
                        line: {
                            tension: 0
                        }
                    }
                }
            });
    setInterval(function() {
        chartSensor(sensorChart);
        }, updateInterval);
});
