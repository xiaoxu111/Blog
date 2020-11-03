
//删除评论信息
function deleteComment() {
    //获取选择的行数
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
    $.messager.confirm("系统提示", "您确定要删除这<font color=red>" + selectedRows.length + "</font>条数据吗？", function (r) {
        if (r) {
            $.post("/admin/comment/delete.do", {ids: ids}, function (result) {
                if (result.success) {
                    $.messager.alert("系统提示", "数据已成功删除！");
                    //重新加载列表
                    $("#dg").datagrid("reload");
                } else {
                    $.messager.alert("系统提示", "数据删除失败！");
                }
            }, "json");
        }
    });
}

//显示博客标题
function formatBlogTitle(val, row) {
    if (val == null) {
        return "<font color='red'>该博客已被删除！</font>";
    } else {
        return "<a target='_blank' href='/blog/articles/" + val.id + ".html'>" + val.title + "</a>";
    }
}

//显示评论状态
function formatState(val, row) {
    if (val == 0) {
        return "待审核";
    } else if (val == 1) {
        return "审核通过";
    } else if (val == 2) {
        return "审核未通过";
    }
}
