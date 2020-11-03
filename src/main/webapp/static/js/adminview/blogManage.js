function formatBlogType(val,row){
    return val.typeName;
}
//点击标题弹出用户预览页面
function formatTitle(val,row){
    return "<a target='_blank' href='/blog/articles/"+row.id+".html'>"+val+"</a>"
}

function searchBlog(){
    $("#dg").datagrid('load',{
        "title":$("#s_title").val()
    });
}
//删除博客信息
function deleteBlog(){
    var selectedRows=$("#dg").datagrid("getSelections");
    if(selectedRows.length==0){
        $.messager.alert("系统提示","请选择要删除的数据！");
        return;
    }
    var strIds=[];
    for(var i=0;i<selectedRows.length;i++){
        strIds.push(selectedRows[i].id);
    }
    var ids=strIds.join(",");
    $.messager.confirm("系统提示","您确定要删除这<font color=red>"+selectedRows.length+"</font>条数据吗？",function(r){
        if(r){
            $.post("/admin/blog/delete.do",{ids:ids},function(result){
                if(result.success){
                    $.messager.alert("系统提示","数据已成功删除！");
                    $("#dg").datagrid("reload");
                }else{
                    $.messager.alert("系统提示","数据删除失败！");
                }
            },"json");
        }
    });
}


function openBlogModifyTab(){
    var selectedRows=$("#dg").datagrid("getSelections");
    if(selectedRows.length!=1){
        $.messager.alert("系统提示","请选择一个要修改的博客！");
        return;
    }
    var row=selectedRows[0];
    window.parent.openTab('修改博客','modifyBlog.jsp?id='+row.id,'icon-writeblog');
}
