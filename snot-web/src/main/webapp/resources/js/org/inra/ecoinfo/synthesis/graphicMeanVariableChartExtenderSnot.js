/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function graphicMeanVariableChartExtender() {
    this.cfg.axes.xaxis.tickOptions.formatString = '%d/%m/%y'
    this.cfg.axes.yaxis.tickOptions = {
        showGridline: false,
        showMark: false,
        color:'#3c8dbc',
        textColor: '#ffe500',
    }
}