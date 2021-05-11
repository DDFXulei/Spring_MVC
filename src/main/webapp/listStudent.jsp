<%@ page contentType="text/html;charset=utf-8" language="java" %>
<% 
    String basePath = request.getScheme()+"://"+
        request.getServerName() + ":" + request.getServerPort() +
        request.getContextPath() + "/";
%>
<html>
<head>
    <script type="text/javascript" src="./js/jQuery3.6.js"></script>
    <script type="text/javascript">
        $(function(){
            loadStudentInfo();
            $("#btnLoader").click(
                function(){
                    $.ajax({
                        url: "student/queryStudent.do",
                        type: "post",
                        dataType: "json",
                        success: function(data){
                             //清除旧数据
                             $("#info").html("")
                            $.each(data, function(i,n){
 
                                $("#info").append("<tr>")
                                    .append("<td>"+n.id+"</td>")
                                    .append("<td>"+n.name+"</td>")
                                    .append("<td>"+n.age+"</td>")
                                    .append("</tr>")
                            })
                        }
                    }
                       

                    )
                }
            )


        })

        function loadStudentInfo(){
            $.ajax({
                        url: "student/queryStudent.do",
                        type: "post",
                        dataType: "json",
                        success: function(data){
                            $.each(data, function(i,n){
                                $("#info").append("<tr>")
                                    .append("<td>"+n.id+"</td>")
                                    .append("<td>"+n.name+"</td>")
                                    .append("<td>"+n.age+"</td>")
                                    .append("</tr>")
                            })
                        }
                    }
                    )

        }

    </script>
</head>
<body>
    <div align='center'>
        <table>
            <thead>
                <tr>
                    <td>学号</td>
                    <td>姓名</td>
                    <td>年龄</td>
                </tr>
            </thead>
            <tbody id="info">

            </tbody>
        </table>
        <input type="button" id="btnLoader" value="查询学生信息">

    </div>
    
</body>
</html>