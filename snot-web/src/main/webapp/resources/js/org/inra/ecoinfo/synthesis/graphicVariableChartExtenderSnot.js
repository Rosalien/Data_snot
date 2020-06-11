/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function days_between(date1, date2) {
    // The number of milliseconds in one day
    var ONE_DAY = 1000 * 60 * 60 * 24;
    // Convert both dates to milliseconds
    var date1_ms = date1.getTime();
    var date2_ms = date2.getTime();
    // Calculate the difference in milliseconds
    var difference_ms = Math.abs(date1_ms - date2_ms);
    // Convert back to days and return
    return Math.round(difference_ms / ONE_DAY);
}
function resetXAxe() {
    date1 = new Date(this.axes.xaxis.min);
    date2 = new Date(this.axes.xaxis.max);
    var noDays = days_between(date1, date2);
    if (noDays > 200) {
        format = '%m/%Y';
    } else {
        format = '%d/%m/%Y';
    }
    this.axes.xaxis.tickOptions.formatString = format;
    console.log("days_between = " + noDays + " format : " + format);
}

var target_temp_dashed_line = function(target_temp) {
  return { dashedHorizontalLine: {
             name: 'Boiling Pt',
             y: target_temp,
             lineWidth: 3,
             color: '#EF3E42',
             shadow: false
           }
         };
}

function graphicVariableChartExtender() {
    var plot = this.cfg;
    $.jqplot.preDrawHooks.push(resetXAxe);
    // 
    // The "seriesDefaults" option is an options object that will
    // be applied to all series in the chart.
   // Configuration du graphique  
    plot.seriesDefaults = {
        //lineWidth: 1, //épaisseur des lignes     
        showLine: false, //ne fonctionne pas
        markerOptions: {            
            size: 8,            
            style: 'filledCircle',
            color: "#3c8dbc",//bleu du site
            shadowAlpha: 0.1,
            shadowDepth: 2,
            fillToZero: true            
        },
        shadow: false,
    },
            
    // Configuration de la grille
    plot.grid= {
        showGridline: false, //ne fonctionne pas
        drawBorder: true,
        shadow: false,
//        background: 'rgba(0,0,0,0)', //transparent
        background: 'white',     
//        borderWidth: 2,
        gridLineColor: 'white',
//        gridLineWidth: 2,
//        borderColor: 'black'
    }
    
    //voir comment intégrer cette option (https://spin.atomicobject.com/2014/04/17/charting-data-jqplot/)
    plot.canvasOverlay= {
      show: true,
      objects: [target_temp_dashed_line(0)],
    }
    
    console.dir(plot);
}
