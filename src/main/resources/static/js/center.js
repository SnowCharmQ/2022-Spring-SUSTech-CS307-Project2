$.ajax({
    url: "/api/center",
    type: "POST",
    success: function (json) {
        var ele = $(".panel");
        ele.empty();
        var head = '<div class="head">' +
            '        <span class="head-id">id</span>' +
            '        <span class="head-name">name</span>' +
            '    </div>' +
            '    <div class="spacing">none</div>';
        ele.append(head);
        var list = json.data;
        for (var i = 0; i < list.length; i++) {
            var temp = list[i];
            var row = '<div class="row">' +
                '        <span class="id">' + temp.id + '</span>' +
                '        <span class="name">' + temp.name + '</span>' +
                '    </div>'
            ele.append(row)
        }
    }
})