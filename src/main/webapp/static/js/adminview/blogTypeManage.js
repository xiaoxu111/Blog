
var url;

function deleteBlogType() {
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
            $.post("/admin/blogType/delete.do", {ids: ids}, function (result) {
                if (result.success) {
                    if (result.exist) {
                        $.messager.alert("系统提示", result.exist);
                    } else {
                        $.messager.alert("系统提示", "数据已成功删除！");
                    }
                    $("#dg").datagrid("reload");
                } else {
                    $.messager.alert("系统提示", "数据删除失败！");
                }
            }, "json");
        }
    });
}

function openBlogTypeAddDialog() {
    $("#dlg").dialog("open").dialog("setTitle", "添加博客类别信息");
    url = "/admin/blogType/save.do";
}

function openBlogTypeModifyDialog() {
    var selectedRows = $("#dg").datagrid("getSelections");
    if (selectedRows.length != 1) {
        $.messager.alert("系统提示", "请选择一条要编辑的数据！");
        return;
    }
    var row = selectedRows[0];
    $("#dlg").dialog("open").dialog("setTitle", "编辑博客类别信息");
    $("#fm").form("load", row);
    url = "/admin/blogType/save.do?id=" + row.id;
}

function saveBlogType() {
    $("#fm").form("submit", {
        url: url,
        onSubmit: function () {
            return $(this).form("validate");
        },
        success: function (result) {
            var result = eval('(' + result + ')');
            if (result.success) {
                $.messager.alert("系统提示", "保存成功！");
                resetValue();
                $("#dlg").dialog("close");
                $("#dg").datagrid("reload");
            } else {
                $.messager.alert("系统提示", "保存失败！");
                return;
            }
        }
    });
}

function resetValue() {
    $("#typeName").val("");
    $("#orderNo").val("");
}

function closeBlogTypeDialog() {
    $("#dlg").dialog("close");
    resetValue();
}