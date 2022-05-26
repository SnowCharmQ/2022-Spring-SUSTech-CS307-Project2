$.ajax({
    url: "/api/enterprise",
    type: 'POST',
    success: function (json) {
        var ele = $(".panel")
        ele.empty();
        var head = '  <div class="head">' +
            '    <span class="head-id">id</span>' +
            '    <span class="head-name">name</span>' +
            '    <span class="head-country">country</span>' +
            '    <span class="head-city">city</span>' +
            '    <span class="head-supply_center">supply_center</span>' +
            '    <span class="head-industry">industry</span>' +
            '  </div>' +
            '  <div class="spacing">none</div>';
        ele.append(head)
        var list = json.data;
        for (var i = 0; i < list.length; i++) {
            var temp = list[i];
            var row = '<div class="row" style="height: 124px">' +
                '    <span class="id">' + temp.id + '</span>' +
                '    <span class="name">' + temp.name + '</span>' +
                '    <span class="country">' + temp.country + '</span>' +
                '    <span class="city">' + temp.city + '</span>' +
                '    <span class="supply_center">' + temp.supplyCenter + '</span>' +
                '    <span class="industry">' + temp.industry + '</span>' +
                '  </div>'
            ele.append(row)
        }
    }
})