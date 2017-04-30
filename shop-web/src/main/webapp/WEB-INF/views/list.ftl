<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"> 
	<title>Bootstrap 实例 - 基本的表格</title>
	<link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">  
	<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
	<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="panel panel-default">
	  <div class="panel-heading">用户列表</div>
	  <div class="panel-body">
	  	<form class="form-inline">
		  <div class="form-group">
		    <label >Username：</label>
		    <input type="text" class="form-control" placeholder="Username">
		    <button class="btn btn-default" type="submit">查询</button>
		  </div>
	  </form>
	  </div>
	   	<table class="table">
		   <thead>
		      <tr>
		         <th>用户ID</th>
		         <th>用户名</th>
		         <th>用户密码</th>
		         <th>国籍</th>
		         <th>位置</th>
		      </tr>
		   </thead>
		   <tbody>
		   		[#list result.results as user]
		   			<tr>
			         <td>${user.id}</td>
			         <td>${user.uname}</td>
			         <td>${user.pwd}</td>
			         <td>${user.nation}</td>
			         <td>${user.location}</td>
			      </tr>
		   		[/#list]
		   </tbody>
		</table>
	  </div>
	  <nav aria-label="Page navigation">
		  <ul class="pagination">
		    <li class="disabled">
		      <a href="#" aria-label="Previous">
		        <span aria-hidden="true">&laquo;</span>
		      </a>
		    </li>
		    <li><a href="#">1</a></li>
		    <li><a href="#">2</a></li>
		    <li><a href="#">3</a></li>
		    <li><a href="#">4</a></li>
		    <li><a href="#">5</a></li>
		    <li>
		      <a href="#" aria-label="Next">
		        <span aria-hidden="true">&raquo;</span>
		      </a>
		    </li>
		  </ul>
		</nav>
</body>
</html>