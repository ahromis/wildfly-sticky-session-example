<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Session ID</title>
</head>
<style>
body {
    background-color: #F2F3F4;
    background-image: url("./images/docker.png");
    background-repeat: no-repeat;
    background-position: right top;
    margin-right: 200px;
}
</style>
<body onload="writeSessionId()";>
<p><b>HTTP Host header = <%=request.getHeader("Host") %></b></p>
<p><b>JSESSIONID = <%= session.getId() %></b></p>
<p><b>Hostname = <%=request.getLocalName() %></b></p>
</body>
</html>
<script lang="javascript">
    function writeSessionId() {
        document.getElementById('sessionid').innerText = getCookie('JSESSIONID');
    }
    function getCookie(cname) {
        var name = cname + "=";
        var ca = document.cookie.split(';');
        for(var i=0; i<ca.length; i++) {
            var c = ca[i].trim();
            if (c.indexOf(name) == 0) return c.substring(name.length,c.length);
        }
        return "";
    }
</script>
