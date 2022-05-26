var list;

$.ajax({
    url: "/users/db-users",
    type: 'POST',
    success: function (json) {
        var ele = $(".panel")
        ele.empty();
        var head = '    <div class="head">' +
            '        <span class="username">Username</span>' +
            '        <span class="insert-right">Insert Right</span>' +
            '        <span class="delete-right">Delete Right</span>' +
            '        <span class="update-right">Update Right</span>' +
            '        <span class="select-right">Select Right</span>' +
            '    </div>' +
            '    <div class="spacing">none</div>';
        ele.append(head)
        list = json.data;
        for (var i = 0; i < list.length; i++) {
            var temp = list[i];
            var row = '<div class="row">';

            row = row + '<div class="name" id="">' + temp.username + '</div>';

            row = row + '<label class="insert">';
            if (temp.canInsert) row = row + '<input type="checkbox" checked="checked" hidden>';
            else row = row + '<input type="checkbox" hidden>';
            row = row + '<span></span>';
            row = row + '<i class="indicator"></i>';
            row = row + '</label>';

            row = row + '<label class="delete">';
            if (temp.canDelete) row = row + '<input type="checkbox" checked="checked" hidden>';
            else row = row + '<input type="checkbox" hidden>';
            row = row + '<span></span>';
            row = row + '<i class="indicator"></i>';
            row = row + '</label>';

            row = row + '<label class="update">';
            if (temp.canUpdate) row = row + '<input type="checkbox" checked="checked" hidden>';
            else row = row + '<input type="checkbox" hidden>';
            row = row + '<span></span>';
            row = row + '<i class="indicator"></i>';
            row = row + '</label>';

            row = row + '<label class="select">';
            if (temp.canSelect) row = row + '<input type="checkbox" checked="checked" hidden>';
            else row = row + '<input type="checkbox" hidden>';
            row = row + '<span></span>';
            row = row + '<i class="indicator"></i>';
            row = row + '</label>';

            row = row + '</div>';
            ele.append(row);
        }
        var btn = '<div class="spacing">none</div><div class="spacing">none</div><div class="spacing">none</div>' +
            '<button type="button" id="submit" onclick="manage()" style="    transform: translate(-50%, -50%);\n' +
            '    width: 200px;\n' +
            '    height: 80px;\n' +
            '    text-align: center;\n' +
            '    color: #fff;\n' +
            '    font-size: 40px;\n' +
            '    text-transform: uppercase;\n' +
            '    cursor: pointer;\n' +
            '    background: linear-gradient(90deg, #03a9f4, #f441a5, #ffeb3b, #03a9f4);\n' +
            '    background-size: 400%;\n' +
            '    border-radius: 60px;\n' +
            '    left: 50%;\n' +
            '    position: absolute;">SUBMIT</button>';
        ele.append(btn)
    }
})

function manage() {
    var insertEles = document.getElementsByClassName("insert")
    var deleteEles = document.getElementsByClassName("delete")
    var updateEles = document.getElementsByClassName("update")
    var selectEles = document.getElementsByClassName("select")
    var arr = [];
    for (var i = 0;i < list.length;i++){
        var c = insertEles[i].childNodes[0].checked;
        var d = deleteEles[i].childNodes[0].checked;
        var u = updateEles[i].childNodes[0].checked;
        var r = selectEles[i].childNodes[0].checked;
        var str = list[i].username;
        if (c===true) str = str + "1";
        else str = str + "0";
        if (d===true) str = str + "1";
        else str = str + "0";
        if (u===true) str = str + "1";
        else str = str + "0";
        if (r===true) str = str + "1";
        else str = str + "0";
        arr.push(str);
    }
    $.ajax({
        url: "/users/db-user-management",
        type: "POST",
        data: arr.join('-'),
        contentType: "application/json;charset=utf-8",
        success: function () {
            alert("Successfully Submit!")
        },
        error: function (xhr) {
            alert(xhr.message)
        }
    })
}
