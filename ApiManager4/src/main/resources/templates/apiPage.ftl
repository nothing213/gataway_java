<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>api管理</title>

</head>
<body>

<div>
    <form action="/do_api" method="post">
        id <input name="id" type="number" value="id">
        </br>
        name:<input name="Apiname" type="text" value="">
        </br>
        head: <input name="head" type="text" value="">
        <br>
        body:<input name="body" type="text" value="">
        <input name="run" type="submit" value="保存">
        <#--<input name="save" type="submit" value="保存">-->
    </form>
    <a href="swagger-ui.html">查看api</a>
    <table width="100%">
        <tr>
            <td width="3%">host</td>
            <td width="3%">baseUrl</td>
            <td width="5%">info</td>
            <td width="80%">path</td>
        </tr>
        <#list apiList as api>
            <tr>
                <td width="3%">${(api.apiData)}</td>
                <#--<td width="3%">${(api.)}</td>-->
                <#--<td width="5%">${api.}</td>-->
                <#--<td width="80%">${api.}</td>-->
            </tr>

        </#list>

        <#--<#assign json=apiList />-->
        <#--<#list json.data as item>-->

        <#--id:${item.host}-->

        <#--</#list>-->

    </table>

</div>
</body>
</html>