
/*判断对象是否为空*/
function isEmptyForString(obj) {

    if (obj != null && obj != "" && obj.length != 0 && obj.trim() != "" && obj.trim().length != 0){
        return false;   //不为空
    }else {
        return true;  //为空
    }
}


//点击菜单时，在当前页面加载菜单所对应的页面
function checkURL() {

    var url = location.hash.replace(/^#/, '');

    var container = $('#content');

    if (url) {

        $('nav li.active').removeClass("active");

        $('nav li:has(a[href="' + url + '"])').addClass("active");
        var title = ($('nav a[href="' + url + '"]').attr('title'))

        document.title = (title || document.title);

        loadURL(url + location.search, container);
    } else {

        var $this = $('nav > ul > li:first-child > a[href!="#"]');

        window.location.hash = $this.attr('href');

    }

}

function loadURL(url, container) {

    $.ajax({
        type : "GET",
        url : url,
        dataType : 'html',
        cache : true,
        beforeSend : function() {

            if (container[0] == $("#content")[0]) {

                $("html").animate({
                    scrollTop : 0
                }, "fast");
            }
        },

        success : function(data) {

            container.css({
                opacity : '0.0'
            }).html(data).delay(50).animate({
                opacity : '1.0'
            }, 300);


        },
        error : function(xhr, ajaxOptions, thrownError) {
            container.html('<h4 style="margin-top:10px; display:block; text-align:left"><i class="fa fa-warning txt-color-orangeDark"></i> Error 404! Page not found.</h4>');
        },
        async : false
    });


}



