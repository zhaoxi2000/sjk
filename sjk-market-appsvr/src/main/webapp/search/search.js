/**
 * 
 */

var AppTemplate = [ '' ];
var WebApp = {
    WebMain : function() {
        WebApp.InitPage();
    },
    InitPage : function() {
        $("#word").bind("keypress", function(e) {
            if (e.keyCode == 13) {
                $("#srBtn").click();
            }
        });

        $("#srBtn").click(function() {
            $("#loading").show();
            var url = '../app/api/cdn/s/search.json';
            var catalog = $("input[name=appType]:checked").val();
            var params = {
                "q" : $("#word").val(),
                "catalog" : catalog,
                "page" : $("#page").val(),
                "rows" : $("#rows").val()
            };
            $.ajax({
                url : url,
                type : 'GET',
                data : params,
                dataType : 'json',
                cache : false,
                success : function(json) {
                    $("#loading").hide();
                    if (json.result.code == 0) {
                        $("#resultContainer").html("");
                        $("#total").text(json.total);
                        for ( var i in json.data) {
                            WebApp.DrawOneApp(json.data[i]);
                        }
                    } else {
                    }
                },
                error : function() {
                    $("#loading").hide();
                }
            });
        });
    },
    DrawOneApp : function(o) {
        var item = $("#appTmplate").clone().removeClass("hidden");
        var itemId = "id-" + o.id;
        item.attr("id", itemId);
        item.find(".name").text(o.name);
        item.find(".desc").html(o.description);
        item.find(".download").text(o.downloadRank);
        item.find(".market").text(o.marketName);

        var logo = item.find("img.logo");
        var jsimage = new Image();
        jsimage.onload = function() {
            $('#' + itemId).find(".logo").attr('src', this.src);
        };
        jsimage.src = o.logoUrl;
        var signaturesha1 = new String(o.signatureSha1);
        var off = new String(o.officialSigSha1);
        if (signaturesha1 != null && signaturesha1.localeCompare(off) == 0) {
            item.find("span[name=official]").removeClass("hidden").addClass(
                    "show");
        }
        $("#resultContainer").append(item);
    }
};

function formateSize(size) {
    if (size > 1024 && size < 1024 * 1024) {
        return size / 1024 + " KB";
    } else if (size >= 1024 * 1024) {
        return size / (1024 * 1024) + " MB";
    }
    return size + " B";
}
$(document).ready(function() {
    WebApp.WebMain();
});