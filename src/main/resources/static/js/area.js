/**
 * 省（直辖市）、市（区）、县（区）三级联动
 */


$("#province").click(function () {

    alert("nihao");

    $.ajax({
        type: "Post",
        url: "cityInfo/getAreaList",
        data: {code:"",type:"province"},
        dataType: "JSON",
        success: function (data) {
            if (data.success) {
                $("#province").empty();
                $("#province").append("<option value=''>请选择省份名称</option>");
                $.each(data.obj, function (i, item) {
                    $("#province").append("<option value=' "+ item.id +" '> "+ item.name +" </option>");
                });
            }else {
                layer.msg(data.msg,{icon: 5});
            }
        }, error: function (data) {

        }
    });

});





