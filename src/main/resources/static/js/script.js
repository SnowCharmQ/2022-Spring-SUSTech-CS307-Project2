// 获取要操作的元素
let items = document.querySelectorAll('.item');
let current_tag = document.querySelector('.current-tag');
let handler = document.querySelector('.handler');
let left_box = document.querySelector('.left-box');

// 设置选中项的样式
function setActive() {
    items.forEach((item) => {
        item.classList.remove('active');
    })
    this.classList.add('active');
    current_tag.innerText = this.innerText;
}

// 为每一个li设置点击事件
items.forEach((item) => {
    item.addEventListener('click', setActive);
})

handler.addEventListener('click', function () {
    if (!this.classList.contains('close')) {
        left_box.style.width = 0;
        this.classList.add('close');
    } else {
        left_box.style.width = 250 + 'px';
        this.classList.remove('close');
    }
})

$.ajax({
    url: "/users/db-info",
    type: "POST",
    success: function (json) {
        if (json.state === 200) {
            var data = json.data;
            var ele = document.getElementById("username")
            ele.innerText = data.username;
        }
    }
})

$(document).ready(
    function () {
        $("#menu-btn").click(function () {
            $.post(
                "/api/menu",
                function (result) {
                    var ele = $(".middle")
                    ele.empty()
                    ele.append(result)
                }
            )
        })
    }
)

$(document).ready(
    function () {
        $("#q3-btn").click(function () {
            var ele = $(".middle")
            ele.empty()
            var input = "<input type='text' name='number' id='place-order' style='width: 1000px;" +
                "position: absolute;" +
                "height: 44px;" +
                "top: 200px;" +
                "left: 60px;" +
                "border-bottom: 2px solid silver;" +
                "background: royalblue;" +
                "font-size: 14px;' required/>" +
                "<button type='button' class='select-btn' style='position: absolute;" +
                "    transform: translate(-50%, -50%);" +
                "    width: 230px;" +
                "    height: 90px;" +
                "    top: 400px;" +
                "    left: 530px;" +
                "    line-height: 90px;" +
                "    text-align: center;" +
                "    color: #fff;" +
                "    font-size: 24px;" +
                "    text-transform: uppercase;" +
                "    cursor: pointer;" +
                "    background: linear-gradient(90deg, #03a9f4, #f441a5, #ffeb3b, #03a9f4);" +
                "    background-size: 400%;" +
                "    border-radius: 60px;" +
                "' onclick='placeOrder()'>Place Order</button>"
            ele.append(input)
        })
    }
)

function placeOrder(){
    $.ajax({
        url: "/api/q3api",
        type: "POST",
        data: $("#place-order").serialize(),
        dataType: "json",
        success: function (json) {
            var middle = $(".middle")
            middle.empty()
            if (json.state === 200) {
                alert("Successfully Place Order!")
                var ele = $(".middle")
                ele.empty()
                var input = "<input type='text' name='number' id='place-order' style='width: 1000px;" +
                    "position: absolute;" +
                    "height: 44px;" +
                    "top: 200px;" +
                    "left: 60px;" +
                    "border-bottom: 2px solid silver;" +
                    "background: royalblue;" +
                    "font-size: 14px;' required/>" +
                    "<button type='button' class='select-btn' style='position: absolute;" +
                    "    transform: translate(-50%, -50%);" +
                    "    width: 230px;" +
                    "    height: 90px;" +
                    "    top: 400px;" +
                    "    left: 530px;" +
                    "    line-height: 90px;" +
                    "    text-align: center;" +
                    "    color: #fff;" +
                    "    font-size: 24px;" +
                    "    text-transform: uppercase;" +
                    "    cursor: pointer;" +
                    "    background: linear-gradient(90deg, #03a9f4, #f441a5, #ffeb3b, #03a9f4);" +
                    "    background-size: 400%;" +
                    "    border-radius: 60px;" +
                    "' onclick='placeOrder()'>Place Order</button>"
                ele.append(input)
            } else middle.append(json.message);
        }
    })
}

$(document).ready(
    function () {
        $("#q4-btn").click(function () {
            var ele = $(".middle")
            ele.empty()
            var input = "<input type='text' name='number' id='update-order' style='width: 1000px;" +
                "position: absolute;" +
                "height: 44px;" +
                "top: 200px;" +
                "left: 60px;" +
                "border-bottom: 2px solid silver;" +
                "background: royalblue;" +
                "font-size: 18px;' required/>" +
                "<button type='button' class='select-btn' style='position: absolute;" +
                "    transform: translate(-50%, -50%);" +
                "    width: 230px;" +
                "    height: 90px;" +
                "    top: 400px;" +
                "    left: 530px;" +
                "    line-height: 90px;" +
                "    text-align: center;" +
                "    color: #fff;" +
                "    font-size: 24px;" +
                "    text-transform: uppercase;" +
                "    cursor: pointer;" +
                "    background: linear-gradient(90deg, #03a9f4, #f441a5, #ffeb3b, #03a9f4);" +
                "    background-size: 400%;" +
                "    border-radius: 60px;" +
                "' onclick='updateOrder()'>Update Order</button>"
            ele.append(input)
        })
    }
)

function updateOrder(){
    $.ajax({
        url: "/api/q4api",
        type: "POST",
        data: $("#update-order").serialize(),
        dataType: "json",
        success: function (json) {
            var middle = $(".middle")
            middle.empty()
            if (json.state === 200) {
                alert("Successfully Place Order!")
                var ele = $(".middle")
                ele.empty()
                var input = "<input type='text' name='number' id='update-order' style='width: 1000px;" +
                    "position: absolute;" +
                    "height: 44px;" +
                    "top: 200px;" +
                    "left: 60px;" +
                    "border-bottom: 2px solid silver;" +
                    "background: royalblue;" +
                    "font-size: 18px;' required/>" +
                    "<button type='button' class='select-btn' style='position: absolute;" +
                    "    transform: translate(-50%, -50%);" +
                    "    width: 230px;" +
                    "    height: 90px;" +
                    "    top: 400px;" +
                    "    left: 530px;" +
                    "    line-height: 90px;" +
                    "    text-align: center;" +
                    "    color: #fff;" +
                    "    font-size: 24px;" +
                    "    text-transform: uppercase;" +
                    "    cursor: pointer;" +
                    "    background: linear-gradient(90deg, #03a9f4, #f441a5, #ffeb3b, #03a9f4);" +
                    "    background-size: 400%;" +
                    "    border-radius: 60px;" +
                    "' onclick='updateOrder()'>Update Order</button>"
                ele.append(input)
            } else middle.append(json.message);
        }
    })
}

$(document).ready(
    function () {
        $("#q5-btn").click(function () {
            var ele = $(".middle")
            ele.empty()
            var input = "<input type='text' name='number' id='delete-order' style='width: 1000px;" +
                "position: absolute;" +
                "height: 44px;" +
                "top: 200px;" +
                "left: 60px;" +
                "border-bottom: 2px solid silver;" +
                "background: royalblue;" +
                "font-size: 18px;' required/>" +
                "<button type='button' class='select-btn' style='position: absolute;" +
                "    transform: translate(-50%, -50%);" +
                "    width: 230px;" +
                "    height: 90px;" +
                "    top: 400px;" +
                "    left: 530px;" +
                "    line-height: 90px;" +
                "    text-align: center;" +
                "    color: #fff;" +
                "    font-size: 24px;" +
                "    text-transform: uppercase;" +
                "    cursor: pointer;" +
                "    background: linear-gradient(90deg, #03a9f4, #f441a5, #ffeb3b, #03a9f4);" +
                "    background-size: 400%;" +
                "    border-radius: 60px;" +
                "' onclick='deleteOrder()'>Delete Order</button>"
            ele.append(input)
        })
    }
)

function deleteOrder(){
    $.ajax({
        url: "/api/q5api",
        type: "POST",
        data: $("#delete-order").serialize(),
        dataType: "json",
        success: function (json) {
            var middle = $(".middle")
            middle.empty()
            if (json.state === 200) {
                alert("Successfully Place Order!")
                var ele = $(".middle")
                ele.empty()
                var input = "<input type='text' name='number' id='delete-order' style='width: 1000px;" +
                    "position: absolute;" +
                    "height: 44px;" +
                    "top: 200px;" +
                    "left: 60px;" +
                    "border-bottom: 2px solid silver;" +
                    "background: royalblue;" +
                    "font-size: 18px;' required/>" +
                    "<button type='button' class='select-btn' style='position: absolute;" +
                    "    transform: translate(-50%, -50%);" +
                    "    width: 230px;" +
                    "    height: 90px;" +
                    "    top: 400px;" +
                    "    left: 530px;" +
                    "    line-height: 90px;" +
                    "    text-align: center;" +
                    "    color: #fff;" +
                    "    font-size: 24px;" +
                    "    text-transform: uppercase;" +
                    "    cursor: pointer;" +
                    "    background: linear-gradient(90deg, #03a9f4, #f441a5, #ffeb3b, #03a9f4);" +
                    "    background-size: 400%;" +
                    "    border-radius: 60px;" +
                    "' onclick='deleteOrder()'>Delete Order</button>"
                ele.append(input)
            } else middle.append(json.message);
        }
    })
}

$(document).ready(
    function () {
        $("#q6-btn").click(function () {
            $.post(
                "/api/q6api",
                function (result) {
                    var ele = $(".middle")
                    ele.empty()
                    if (result.state === 200) {
                        for (var i = 0; i < result.data.length; i++) {
                            var str = result.data[i].staffType + "\t" + result.data[i].count + "<br>";
                            ele.append(str)
                        }
                    } else {
                        ele.append(result.message)
                    }
                }
            )
        })
    }
)
$(document).ready(
    function () {
        $("#q7-btn").click(function () {
            $.post(
                "/api/q7api",
                function (result) {
                    var ele = $(".middle")
                    ele.empty()
                    if (result.state === 200) ele.append(result.data)
                    else ele.append(result.message)
                }
            )
        })
    }
)
$(document).ready(
    function () {
        $("#q8-btn").click(function () {
            $.post(
                "/api/q8api",
                function (result) {
                    var ele = $(".middle")
                    ele.empty()
                    if (result.state === 200) ele.append(result.data)
                    else ele.append(result.message)
                }
            )
        })
    }
)
$(document).ready(
    function () {
        $("#q9-btn").click(function () {
            $.post(
                "/api/q9api",
                function (result) {
                    var ele = $(".middle")
                    ele.empty()
                    if (result.state === 200) ele.append(result.data)
                    else ele.append(result.message)
                }
            )
        })
    }
)
$(document).ready(
    function () {
        $("#q10-btn").click(function () {
            $.post(
                "/api/q10api",
                function (result) {
                    var ele = $(".middle")
                    ele.empty()
                    if (result.state === 200) {
                        var str = result.data.productModel + "\t" + result.data.quantity
                        ele.append(str)
                    } else ele.append(result.message)
                }
            )
        })
    }
)
$(document).ready(
    function () {
        $("#q11-btn").click(function () {
            $.post(
                "/api/q11api",
                function (result) {
                    var ele = $(".middle")
                    ele.empty()
                    if (result.state === 200) {
                        for (var i = 0; i < result.data.length; i++) {
                            var str = result.data[i].center + "\t" + result.data[i].avg + "<br>";
                            ele.append(str)
                        }
                    } else ele.append(result.message)
                }
            )
        })
    }
)

$(document).ready(
    function () {
        $("#q12-btn").click(function () {
            var ele = $(".middle")
            ele.empty()
            var input = "<input type='text' name='number' id='product-number' style='width: 260px;" +
                "position: absolute;" +
                "height: 44px;" +
                "top: 200px;" +
                "left: 396px;" +
                "border-bottom: 2px solid silver;" +
                "background: royalblue;" +
                "font-size: 36px;' required/>" +
                "<button type='button' class='select-btn' style='position: absolute;" +
                "    transform: translate(-50%, -50%);" +
                "    width: 230px;" +
                "    height: 90px;" +
                "    top: 400px;" +
                "    left: 530px;" +
                "    line-height: 90px;" +
                "    text-align: center;" +
                "    color: #fff;" +
                "    font-size: 16px;" +
                "    text-transform: uppercase;" +
                "    cursor: pointer;" +
                "    background: linear-gradient(90deg, #03a9f4, #f441a5, #ffeb3b, #03a9f4);" +
                "    background-size: 400%;" +
                "    border-radius: 60px;" +
                "' onclick='selectProduct()'>SELECT</button>"
            ele.append(input)
        })
    }
)

function selectProduct() {
    $.ajax({
        url: "/api/q12api",
        type: "POST",
        data: $("#product-number").serialize(),
        dataType: "json",
        success: function (json) {
            var ele = $(".middle")
            ele.empty()
            if (json.state === 200) {
                for (var i = 0; i < json.data.length; i++) {
                    var str = json.data[i].supplyCenter + "\t" + json.data[i].productModel +
                        "\t" + json.data[i].quantity + "<br>";
                    ele.append(str);
                }
            } else ele.append(json.message);
        }
    })
}

$(document).ready(
    function () {
        $("#q13-btn").click(function () {
            var ele = $(".middle")
            ele.empty()
            var input = "<input type='text' name='number' id='contract-number' style='width: 260px;" +
                "position: absolute;" +
                "height: 44px;" +
                "top: 200px;" +
                "left: 396px;" +
                "border-bottom: 2px solid silver;" +
                "background: royalblue;" +
                "font-size: 24px;' required/>" +
                "<button type='button' class='select-btn' style='position: absolute;" +
                "    transform: translate(-50%, -50%);" +
                "    width: 230px;" +
                "    height: 90px;" +
                "    top: 400px;" +
                "    left: 530px;" +
                "    line-height: 90px;" +
                "    text-align: center;" +
                "    color: #fff;" +
                "    font-size: 16px;" +
                "    text-transform: uppercase;" +
                "    cursor: pointer;" +
                "    background: linear-gradient(90deg, #03a9f4, #f441a5, #ffeb3b, #03a9f4);" +
                "    background-size: 400%;" +
                "    border-radius: 60px;" +
                "' onclick='selectContract()'>SELECT</button>"
            ele.append(input)
        })
    }
)

function selectContract() {
    $.ajax({
        url: "/api/q13api",
        type: "POST",
        data: $("#contract-number").serialize(),
        dataType: "json",
        success: function (json) {
            var ele = $(".middle")
            ele.empty()
            if (json.state === 200) {
                ele.append(json.data)
            } else ele.append(json.message)
        }
    })
}

$(document).ready(
    function () {
        $("#order_info").click(function () {
            var ele = $(".middle")
            ele.empty()
            var ori = '<form id="select-order">' +
                '                <select name="sorting" id="sort-method" class="select_box">' +
                '                    <option value="0">Sorting Method</option>' +
                '                    <option value="1">Sort By Quantity ASC</option>' +
                '                    <option value="2">Sort By Quantity DESC</option>' +
                '                    <option value="3">Sort By Contract Date ASC</option>' +
                '                    <option value="4">Sort By Contract Date DESC</option>' +
                '                </select>' +
                '                <div class="page">Number:</div>' +
                '                <input type="text" name="key" class="key" placeholder="Contract Number">' +
                '                <input type="text" name="page" class="page-num" placeholder="1">' +
                '                <button type="button" class="select-btn" onclick="selectOrder()">SELECT</button>' +
                '            </form>' +
                '            <div class="head">' +
                '                <span class="t-number">Contract Number</span>' +
                '                <span class="t-enterprise">Enterprise</span>' +
                '                <span class="t-model">Product Model</span>' +
                '                <span class="t-quantity">Quantity</span>' +
                '                <span class="t-date">Contract Date</span>' +
                '                <span class="t-type">Contract Type</span>' +
                '            </div>' + '<div class="order-table"></div>'
            ele.append(ori);
        })
    }
)

function selectOrder() {
    $.ajax({
        url: "/api/order",
        type: "POST",
        data: $("#select-order").serialize(),
        dataType: "json",
        success: function (data) {
            if (data.state === 200) {
                var json = data.data;
                // var ele = $(".middle")
                // ele.empty();
                // var ori = '<form id="select-order">' +
                //     '                <select name="sorting" id="sort-method" class="select_box">' +
                //     '                    <option value="0">Sorting Method</option>' +
                //     '                    <option value="1">Sort By Quantity ASC</option>' +
                //     '                    <option value="2">Sort By Quantity DESC</option>' +
                //     '                    <option value="3">Sort By Contract Date ASC</option>' +
                //     '                    <option value="4">Sort By Contract Date DESC</option>' +
                //     '                </select>' +
                //     '                <div class="page">Number:</div>' +
                //     '                <input type="text" name="key" class="key" placeholder="Contract Number">' +
                //     '                <input type="text" name="page" class="page-num" placeholder="1">' +
                //     '                <button type="button" class="select-btn" onclick="selectOrder()">SELECT</button>' +
                //     '            </form>' +
                //     '            <div class="head">' +
                //     '                <span class="t-number">Contract Number</span>' +
                //     '                <span class="t-enterprise">Enterprise</span>' +
                //     '                <span class="t-model">Product Model</span>' +
                //     '                <span class="t-quantity">Quantity</span>' +
                //     '                <span class="t-date">Contract Date</span>' +
                //     '                <span class="t-type">Contract Type</span>' +
                //     '            </div>' + '<div class="order-table"></div>'
                // ele.append(ori);
                var ele = $(".order-table");
                ele.empty();
                var str = '<div class="row">' +
                    '                <span class="number">' + json.contractNum + '</span>' +
                    '                <span class="enterprise">' + json.enterprise + '</span>' +
                    '                <span class="model">' + json.productModel + '</span>' +
                    '                <span class="quantity">' + json.quantity + '</span>' +
                    '                <span class="date">' + json.contractDate + '</span>' +
                    '                <span class="type">' + json.contractType + '</span>' +
                    '            </div>';
                ele.append(str);
            }else{
                var ele = $(".middle");
                ele.empty();
                ele.append(data.message)
            }
        }
    })
}