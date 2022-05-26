// $(document).ready(
    $.ajax({
        url: "/users/db-info",
        type: "POST",
        success: function (json) {
            if (json.state === 200){
                var data = json.data;
                var ele = document.getElementById("title")
                ele.innerText = "THE ACTIVE USER: " + data.username;

                var g1 = document.getElementById("g1");
                if (data.canInsert) g1.innerText = "Y";
                else g1.innerText = "N";

                var g2 = document.getElementById("g2");
                if (data.canDelete) g2.innerText = "Y";
                else g2.innerText = "N";

                var g3 = document.getElementById("g3");
                if (data.canUpdate) g3.innerText = "Y";
                else g3.innerText = "N";

                var g4 = document.getElementById("g4");
                if (data.canSelect) g4.innerText = "Y";
                else g4.innerText = "N";

                if (data.username === "super"){
                    var body = $("#body");
                    var str = '<button type="button" class="btn seven" onclick="window.location.href=\'db-user-management.html\'">User Privilege Management</button>';
                    body.append(str)
                }
            }
        }
    })
// )