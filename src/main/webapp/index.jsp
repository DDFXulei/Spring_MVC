<%@ page contentType="text/html;charset=utf-8" language="java" %>
<html>
<head>
<title>First SpringMVC</title>
<script type="text/javascript" src="./js/jQuery3.6.js"></script>
<script type="text/javascript">
    $(function(){
        $("button").click(function(){
            //alert("button clicked!")
            $.ajax({
                url:"receiveAjax.do",
                data: {
                    name:"zhangsan",
                    age:22
                },
                type:"post",
                dataType:"json",
                success: function(response){
                    alert("name:"+response.name+" "+"age:"+response.age)
                }
                    })

        })
    })

</script>
</head>
<body>
<h3>学生信息</h3><br>
<form action="object.do">
学生名字：<input type="text" name="name"><br>
学生年龄: <input type="number" name="age"><br>
<input type="submit" value="提交">
</form>

<br>
<button id="btn">发起ajax请求</button>
</body>
</html>