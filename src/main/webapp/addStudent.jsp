<%@ page contentType="text/html;charset=utf-8" language="java" %>

<% 
    String basePath = request.getScheme()+"://"+
        request.getServerName() + ":" + request.getServerPort() +
        request.getContextPath() + "/";
%>
<html>
<head>
<title>SSM整合例子</title>
<base href="<%=basePath%>"/>
<body>
    <div align="center">
        <form action="student/addStudent.do">
            <table>
                <tr>
                    <td>学生姓名：</td>
                    <td><input type="text" name="name"></td>
                </tr>
                <tr>
                    <td>年龄：</td>
                    <td><input type="number" name="age"></td>
                </tr>
                <tr>
                    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                    <td><input type="submit" value="提交"></td>
                </tr>

            </table>
        </form>

    </div>
</body>