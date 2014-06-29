<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Session Id</title>
</head>
<body onload="writeSessionId()">
<p>Server side session id = <%= session.getId() %></p>
<p>Client side session id = <span id="sessionid"></span></p>
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
