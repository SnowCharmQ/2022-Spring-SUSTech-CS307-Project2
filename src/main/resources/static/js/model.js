$.ajax({
    url: "/api/model",
    type: 'POST',
    success: function (json) {
        var ele = $(".panel")
        ele.empty();
        var head = '<div class="head">' +
            '        <span class="head-id">id</span>' +
            '        <span class="head-number">number</span>' +
            '        <span class="head-model">model</span>' +
            '        <span class="head-name">name</span>' +
            '        <span class="head-unit_price">unit_price</span>' +
            '    </div>' +
            '    <div class="spacing">none</div>';
        ele.append(head)
        var list = json.data;
        for (var i = 0; i < list.length; i++) {
            var temp = list[i];
            var row = '<div class="row">' +
                '        <div class="id">' + temp.id + '</div>' +
                '        <div class="number">' + temp.number + '</div>' +
                '        <div class="model">' + temp.model + '</div>' +
                '        <div class="name">' + temp.name + '</div>' +
                '        <div class="unit_price">' + temp.unitPrice + '</div>' +
                '    </div>'
            ele.append(row)
        }
    }
})