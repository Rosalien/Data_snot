/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//Paramètre pour le graphique présentant la répartition temporelle des données disponibles

function chartValuesVariableExtender() {
    this.cfg.axes.yaxis.tickOptions = {
        showGridline: false,
        showMark: false,
        textColor: '#3c8dbc' 
    }
    this.cfg.axes.yaxis.min = 0;
    this.cfg.axes.yaxis.max = 1;
    this.cfg.axesDefaults = {
        labelRenderer: $.jqplot.CanvasAxisLabelRenderer
    };
    this.cfg.seriesDefaults = {
//                    rendererOptions: {
//                        smooth: true
//                    },       
        step: true,
        markerOptions: {show: false},
        fill: true,
        color: '#3c8dbc'
    };
}


