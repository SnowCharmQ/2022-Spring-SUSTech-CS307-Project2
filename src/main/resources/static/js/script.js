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