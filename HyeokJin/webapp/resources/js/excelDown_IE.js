$.fn.exportBtnClick = function (id) {
    var excelFrame = document.createElement("iframe");
    excelFrame.id = "excelExportFrame";
    excelFrame.position = "absolute";
    excelFrame.style.zIndex = -1;
    excelFrame.style.top = "-10px";
    excelFrame.style.left = "-10px";
    excelFrame.style.height = "0px";
    excelFrame.style.width = "0px";
    document.body.appendChild(excelFrame);
   
    var frmTarget = document.all.excelExportFrame.contentWindow.document;
    frmTarget.open("application/vnd.ms-excel", "replace");
    frmTarget.write("<html>");
    frmTarget.write("<meta http-equiv=\"Content-Type\" content=\"application/vnd.ms-excel; charset=euc-kr\">\r\n");
    frmTarget.write("<body>");
    frmTarget.write(document.getElementById(id).outerHTML); // 테이블 id
    frmTarget.write("</body>");
    frmTarget.write("</html>");
    frmTarget.charset="UTF-8";
    frmTarget.focus();

    frmTarget.execCommand("SaveAs", "false", "excelDown.xls");
    
    document.body.removeChild(document.getElementById("excelExportFrame"));
  }


