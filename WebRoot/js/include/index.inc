<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", -10);
%>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<script src="<%=basePath%>js/lib/jqurey/jquery-1.9.1.js"></script>

<link rel="stylesheet" href="<%=basePath%>js/lib/bootstrap/bootstrap.min.css">
<link rel="stylesheet" href="<%=basePath%>js/lib/bootstrap/font-awesome.min.css">
<script src="<%=basePath%>js/lib/bootstrap/bootstrap.min.js"></script>


<link rel="stylesheet" href="<%=basePath%>js/lib/base/base.css">
<script src="<%=basePath%>js/lib/base/base.js"></script>

<link rel="stylesheet" href="<%=basePath%>js/lib/index/index.css">

<script src="<%=basePath%>js/lib/index/index.js"></script>
<script src="<%=basePath%>js/lib/index/index-vehicle.js"></script>
<script src="<%=basePath%>js/lib/index/index-person.js"></script>
<script src="<%=basePath%>js/lib/index/index-warningPerson.js"></script>
<script src="<%=basePath%>js/lib/index/index-warningVehicle.js"></script>


<link rel="stylesheet" href="<%=basePath%>js/lib/index/sweetalert.css">
<script src="<%=basePath%>js/lib/index/sweetalert-dev.js"></script>
<!--[if lt IE 9]>
  <script src="<%=basePath%>js/lib/base/_respond.js"></script>
  <script src="<%=basePath%>js/lib/base/_html5shiv.js"></script>
<![endif]-->