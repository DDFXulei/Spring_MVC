
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<% 
    String basePath = request.getScheme()+"://"+
        request.getServerName() + ":" + request.getServerPort() +
        request.getContextPath() + "/";
%>
<html>
<head>
<title>First SpringMVC</title>
<script type="text/javascript" src="./js/jQuery3.6.js"></script>
<script type="text/javascript">
    $(function(){
        $("button").click(function(){
            //alert("button clicked!")
            $.ajax({
                //url:"returnPDF.do",
                url: "doSomeString.do",
                data: {
                    name:"zhangsan",
                    age:22
                },
                type:"post",
                //dataType:"json",
                dataType: "text",
                success: function(response){
                    //alert("name:"+response.name+" "+"age:"+response.age)
                    alert(response);

                }
                    })

        })
    })

</script>
<base href="<%=basePath%>"/>
</head>
<body>
<!--
<h3>学生信息</h3><br>
    <form action="object.do">
    学生名字：<input type="text" name="name"><br>
    学生年龄: <input type="number" name="age"><br>
    <input type="submit" value="提交">
    </form>
     -->

     <div align="center"></div>
     <p>SSM整合的例子</p>
     <table>
        <tr>
            <td><a href="addStudent.jsp">注册学生</a></td>
        </tr>
        <tr>
            <td>浏览学生</td>
        </tr>
     </table>



<br>
<button id="btn">发起ajax请求</button>
</body>
</html>