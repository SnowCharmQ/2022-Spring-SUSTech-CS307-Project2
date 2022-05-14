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
        $("#q6-btn").click(function () {
            $.post(
                "/api/q6api",
                function (result) {
                    var ele = $(".middle")
                    ele.empty()
                    for (var i = 0; i < result.data.length; i++) {
                        var str = result.data[i].staffType + "\t" + result.data[i].count + "<br>";
                        ele.append(str)
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
                    ele.append(result.data)
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
                    ele.append(result.data)
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
                    ele.append(result.data)
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
                    var str = result.data.productModel + "\t" + result.data.quantity
                    ele.append(str)
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
                    for (var i = 0; i < result.data.length; i++) {
                        var str = result.data[i].center + "\t" + result.data[i].avg + "<br>";
                        ele.append(str)
                    }
                }
            )
        })
    }
)
$(document).ready(
    function () {
        $("#q12-btn").click(function () {
            $.post(
                "/api/menu",
                function () {
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
                        "<button type='button' class='select-btn' style='position: absolute;\n" +
                        "    transform: translate(-50%, -50%);\n" +
                        "    width: 230px;\n" +
                        "    height: 90px;\n" +
                        "    top: 400px;\n" +
                        "    left: 530px;\n" +
                        "    line-height: 90px;\n" +
                        "    text-align: center;\n" +
                        "    color: #fff;\n" +
                        "    font-size: 25px;\n" +
                        "    text-transform: uppercase;\n" +
                        "    cursor: pointer;\n" +
                        "    background: linear-gradient(90deg, #03a9f4, #f441a5, #ffeb3b, #03a9f4);\n" +
                        "    background-size: 400%;\n" +
                        "    border-radius: 60px;\n" +
                        "' onclick='selectNumber()'>SELECT</button>"
                    ele.append(input)
                }
            )
        })
    }
)

function selectNumber() {
    $.ajax({
        url: "/api/q12api",
        type: "POST",
        data: $("#product-number").serialize(),
        dataType: "json",
        success: function (json) {
            if (json.state === 200) {
                var ele = $(".middle")
                ele.empty()
                for (var i = 0; i < json.data.length; i++) {
                    var str = json.data[i].supplyCenter + "\t" + json.data[i].productModel +
                        "\t" + json.data[i].quantity + "<br>";
                    ele.append(str)
                }
            }
        }
    })
}