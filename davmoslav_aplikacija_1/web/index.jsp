<%-- 
    Document   : index
    Created on : Jun 5, 2014, 8:24:22 PM
    Author     : Davor
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Aplikacija 1 - Davor Moslavac</title>
    </head>
    <body>
        <h1>Aplikacija 1 - Davor Moslavac</h1>
        <c:set var="userName" value="${sessionScope.korisnik}" scope="page" />
        <p> <c:if test="${empty  pageContext.request.remoteUser}" >
                Prijavljeni korisnik: - </br>
            </c:if>
            <c:if test="${not empty pageContext.request.remoteUser}">
                Prijavljeni korisnik: ${pageContext.request.remoteUser}</br>
            </c:if>
        </p>
        <ul id="list-nav">
        <li><a href="${pageContext.servletContext.contextPath}/Kontroler">Početna stranica</a></li>
        <li><a href="${pageContext.servletContext.contextPath}/OdjavaKorisnika">OdjavaKorisnika</a></li>
        <li><a href="${pageContext.servletContext.contextPath}/PregledMeteoPodataka">Pregled meteoroloških podataka</a></li>
        <li><a href="${pageContext.servletContext.contextPath}/PregledKorisnickihZahtjeva">Pregled korisničkih zahtjeva</a></li>
        <li><a href="${pageContext.servletContext.contextPath}/PregledZahtjevaZaServer">Pregled zahtjeva za server</a></li>
        </ul>
    </body>
</html>