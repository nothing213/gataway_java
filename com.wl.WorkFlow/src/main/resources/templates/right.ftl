<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="Images/css1/css.css" rel="stylesheet" type="text/css">
</head>
<SCRIPT language=javascript>
<!--
var displayBar=true;
function switchBar(obj){
	if (displayBar)
	{
		parent.frame.cols="0,*";
		displayBar=false;
		obj.value="打开左边管理菜单";
	}
	else{
		parent.frame.cols="195,*";
		displayBar=true;
		obj.value="关闭左边管理菜单";
	}
}

function fullmenu(url){
	if (url==null) {url = "admin_left.asp";}
	parent.leftFrame.location = url;
}

//-->
</SCRIPT>


 



<body>
<h2>
    <a href='/create?name=activiti&key=123456'>绘制流程</a>
</h2>
<div>
    <table width="100%">
        <tr>
            <td width="10%">模型编号</td>
            <td width="10%">版本</td>
            <td width="20%">模型名称</td>
            <td width="20%">模型key</td>
            <td width="40%">操作</td>
        </tr>
        <#list modelList as model>
        <tr>
            <td width="10%">${model.id}</td>
            <td width="10%">${model.version}</td>
            <td width="20%"><#if (model.name)??>${model.name}<#else> </#if></td>
            <td width="20%"><#if (model.key)??>${model.key}<#else> </#if></td>
            <#--				<td width="20%"><#if (model.deploymentId)??>${model.deploymentId}<#else> </#if></td>-->
            <td width="40%">
                <a href="/editor?modelId=${model.id}">编辑</a>
                <a href="/publish?modelId=${model.id}">发布</a>
                <a href="/run?modelName=${model.name}">启动</a>
                <a href="/complete?modelName=${model.name}">完成</a>
                <a href="/revokePublish?modelId=${model.id}">撤销</a>
                <a href="/delete?modelId=${model.id}">删除</a>
            </td>
        </tr>
    </#list>
    </table>


    <span>

            ${resultSet}
    </span>
</div>
</body>
</html>
