
function commentReview(state) {
    var selectedRows = $("#dg").datagrid("getSelections");
    if (selectedRows.length == 0) {
        $.messager.alert("系统提示", "请选择要删除的数据！");
        return;
    }
    var strIds = [];
    for (var i = 0; i < selectedRows.length; i++) {
        strIds.push(selectedRows[i].id);
    }
    var ids = strIds.join(",");
    $.messager.confirm("系统提示", "您确定要审核这<font color=red>" + selectedRows.length + "</font>条评论吗？", function (r) {
        if (r) {
            $.post("/admin/comment/review.do", {
                ids: ids,
                state: state
            }, function (result) {
                if (result.success) {
                    $.messager.alert("系统提示", "提交成功！");
                    $("#dg").datagrid("reload");
                } else {
                    $.messager.alert("系统提示", "提交失败！");
                }
            }, "json");
        }
    });
}


function formatBlogTitle(val, row) {
    if (val == null) {
        return "<font color='red'>该博客已被删除！</font>";
    } else {
        return "<a target='_blank' href='/blog/articles/" + val.id + ".html'>" + val.title + "</a>";
    }
}
