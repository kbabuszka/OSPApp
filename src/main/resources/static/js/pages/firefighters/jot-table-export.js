var ExportButtons = document.getElementById('table-jot');
var instance = new TableExport(ExportButtons, {
    formats: ['xlsx'],
    exportButtons: false
});

var exportData = instance.getExportData()['table-jot']['xlsx'];
var XLSbutton = document.getElementById('export-excel');
XLSbutton.addEventListener('click', function (e) {
    instance.export2file(exportData.data, exportData.mimeType, exportData.filename, exportData.fileExtension);
});



TableExport(document.getElementsByTagName("table"), {
  headers: true,                      // (Boolean), display table headers (th or td elements) in the <thead>, (default: true)
  footers: true,                     // (Boolean), display table footers (th or td elements) in the <tfoot>, (default: false)
  formats: ["xlsx", "csv"],           // (String[]), filetype(s) for the export, (default: ['xlsx', 'csv', 'txt'])
  filename: "tabela-jot",                     // (id, String), filename for the downloaded file, (default: 'id')
  bootstrap: true,                    // (Boolean), style buttons using bootstrap, (default: true)
  exportButtons: false,                // (Boolean), automatically generate the built-in export buttons for each of the specified formats (default: true)
  position: "top",                    // (top, bottom), position of the caption element relative to table, (default: 'bottom')
  ignoreRows: null,                   // (Number, Number[]), row indices to exclude from the exported file(s) (default: null)
  ignoreCols: null,                   // (Number, Number[]), column indices to exclude from the exported file(s) (default: null)
  trimWhitespace: true,              // (Boolean), remove all leading/trailing newlines, spaces, and tabs from cell text in the exported file(s) (default: false)
  RTL: false,                         // (Boolean), set direction of the worksheet to right-to-left (default: false)
  sheetname: "jot"                     // (id, String), sheet name for the exported spreadsheet, (default: 'id')
});

//$('#export-excel').on('click',function() {
//	$("#table-jot").tableExport();
//})