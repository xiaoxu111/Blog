var url;

function openTab(text, url, iconCls) {
    if ($("#tabs").tabs("exists", text)) {
        $("#tabs").tabs("select", text);
    } else {
        var content = "<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' src='/backend/" + url + "'></iframe>";
        $("#tabs").tabs("add", {
            title: text,
            iconCls: iconCls,
            closable: true,
            content: content
        });
    }
}

// 修改密码
function modifyPassword() {
    $("#fm").form("submit", {
        url: url,
        onSubmit: function () {
            var newPassword = $("#newPassword").val();
            var newPassword2 = $("#newPassword2").val();
            if (!$(this).form("validate")) {
                return false;
            }
            if (newPassword != newPassword2) {
                $.messager.alert("系统提示", "两次密码输入不一致！");
                return false;
            }
            return true;
        },
        success: function (result) {
            var result = eval('(' + result + ')');
            if (result.success) {
                $.messager.alert("系统提示", "密码修改成功，下一次登录生效！");
                resetValue();
                $("#dlg").dialog("close");
            } else {
                $.messager.alert("系统提示", "密码修改失败！");
                return;
            }
        }
    });
}

// 关闭修改密码框
function closePasswordModifyDialog() {
    resetValue();
    $("#dlg").dialog("close");
}

// 重置
function resetValue() {
    $("#oldPassword").val("");
    $("#newPassword").val("");
    $("#newPassword2").val("");
}

// 安全退出登录
/*function logout() {
    $.messager.confirm("系统提示", "您确定要退出系统吗？", function (r) {
        if (r) {
            window.location.href = '/admin/blogger/logout.do';
        }
    });
}*/

// 刷新系统缓存
function refreshSystem() {
    debugger
    $.post("/admin/system/refreshSystem.do", {}, function (result) {
        if (result.success) {
            $.messager.alert("系统提示", "已成功刷新系统缓存！");
        } else {
            $.messager.alert("系统提示", "刷新系统缓存失败！");
        }
    }, "json");
}
