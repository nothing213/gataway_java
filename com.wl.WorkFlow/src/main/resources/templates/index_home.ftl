<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>后端管理界面</title>
	<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
<div class="header">
	<h1>后端管理界面</h1>
	<ul>
		<li><a href="#">首页</a></li>
		<li><a href="#">文章管理</a></li>
		<li><a href="#">用户管理</a></li>
		<li><a href="#">设置</a></li>
	</ul>
</div>
<div class="content">
	<h2>欢迎您，管理员</h2>
	<p>这里是后端管理界面，您可以进行文章管理、用户管理和系统设置等操作。</p>
	<h3>文章管理</h3>
	<table>
		<tr>
			<th>文章标题</th>
			<th>作者</th>
			<th>发布时间</th>
			<th>操作</th>
		</tr>
		<#list articles as article>
			<tr>
				<td>${article.title}</td>
				<td>${article.author}</td>
				<td>${article.publishTime}</td>
				<td><a href="#">编辑</a> | <a href="#">删除</a></td>
			</tr>
		</#list>
	</table>
	<h3>用户管理</h3>
	<table>
		<tr>
			<th>用户名</th>
			<th>邮箱</th>
			<th>注册时间</th>
			<th>操作</th>
		</tr>
		<#list users as user>
			<tr>
				<td>${user.username}</td>
				<td>${user.email}</td>
				<td>${user.registerTime}</td>
				<td><a href="#">编辑</a> | <a href="#">删除</a></td>
			</tr>
		</#list>
	</table>
</div>
<div class="footer">
	<p>版权所有 &copy; 2020 后端管理界面</p>
</div>
</body>
</html>