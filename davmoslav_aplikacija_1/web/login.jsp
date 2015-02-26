<%-- 
    Document   : login
    Created on : Jun 10, 2014, 11:16:23 PM
    Author     : Davor
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Prijava</title>
    </head>
    <body>
        <h1>Prijava</h1>
        <form method="POST" action="j_security_check">
            Korisničko ime: <input name="j_username"/><br/>
            Lozinka: <input type="password" name="j_password"/><br/>
            <input type="submit" value=" Prijavi se "/>
        </form>
    </body>
</html>