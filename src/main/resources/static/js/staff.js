$.ajax({
    url: "/api/staff",
    type: 'POST',
    success: function (json) {
        var ele = $(".panel")
        ele.empty();
        var head = '  <div class="head">' +
            '    <span class="head-id">id</span>' +
            '    <span class="head-name">name</span>' +
            '    <span class="head-age">age</span>' +
            '    <span class="head-gender">gender</span>' +
            '    <span class="head-number">number</span>' +
            '    <span class="head-supply_center">supply_center</span>' +
            '    <span class="head-mobile_number">mobile_number</span>' +
            '    <span class="head-type">type</span>' +
            '  </div>' +
            '  <div class="spacing">none</div>';
        ele.append(head)
        var list = json.data;
        for (var i = 0; i < list.length; i++) {
            var temp = list[i];
            var row = '<div class="row">' +
                '    <div class="id">' + temp.id + '</div>' +
                '    <div class="name">' + temp.name + '</div>' +
                '    <div class="age">' + temp.age + '</div>' +
                '    <div class="gender">' + temp.gender + '</div>' +
                '    <div class="number">' + temp.number + '</div>' +
                '    <div class="supply_center">' + temp.supplyCenter + '</div>' +
                '    <div class="mobile_number">' + temp.mobileNumber + '</div>' +
                '    <div class="type">' + temp.type + '</div>' +
                '  </div>';
            ele.append(row)
        }
    }
})