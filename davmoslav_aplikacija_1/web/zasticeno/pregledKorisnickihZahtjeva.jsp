<%-- 
    Document   : index
    Created on : Jun 5, 2014, 8:24:22 PM
    Author     : Davor
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pregled korisničkih zahtjeva</title>
        <link href="${pageContext.servletContext.contextPath}/css/displaytag.css" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.servletContext.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>      
        <ul id="list-nav">
            <li><a href="${pageContext.servletContext.contextPath}/Kontroler">Početna stranica</a></li>
            <li><a href="${pageContext.servletContext.contextPath}/OdjavaKorisnika">OdjavaKorisnika</a></li>
            <li><a href="${pageContext.servletContext.contextPath}/PregledMeteoPodataka">Pregled meteoroloških podataka</a></li>
            <li><a href="${pageContext.servletContext.contextPath}/PregledKorisnickihZahtjeva">Pregled korisničkih zahtjeva</a></li>
            <li><a href="${pageContext.servletContext.contextPath}/PregledZahtjevaZaServer">Pregled zahtjeva za server</a></li>
        </ul><br/><br/>

        <sql:setDataSource
            var="meteo"
            driver="${applicationScope.konfiguracija_baza.getDriver_database()}"
            url="${applicationScope.konfiguracija_baza.getServer_database()}${applicationScope.konfiguracija_baza.getUser_database()}"
            user="${applicationScope.konfiguracija_baza.getUser_username()}"
            password="${applicationScope.konfiguracija_baza.getUser_password()}"
            />

        <form action="pregledKorisnickihZahtjeva.jsp" method="POST">
            Filtriranje podataka:
            <br/>
            Zahtjev<input name="naredba" id="naredba"/>
            <br/>
            Unesite od  <input name="odDate" id="odDate" class="odDate"  type="text"/>
            Unesite do  <input name="doDate" id="doDate" class="doDate"  type="text"/>
            <br>
            Straničenje:<select id="stranica" name="stranica">
                <option value="">Svi</option>
                <option value="5">5</option>
                <option value="10">10</option>
                <option value="20">20</option>
                <option value="50">50</option>
                <option value="100">100</option>
            </select>
            <input type="submit" value="Filtriraj" name="submit"/>
        </form>
        <br/><br/>


        <sql:query dataSource="${meteo}" var="ispis">
            SELECT zahtjev, vrijeme_obrade, DATE_FORMAT(FROM_UNIXTIME(`vrijeme`) ,  '%Y.%m.%d %k.%i.%s' ) as 'vrijeme' FROM davmoslav_korisnicki_zahtjevi
            <c:if test="${param.odDate!='' || param.naredba!=''}"> 
                WHERE <c:if test="${param.odDate!=''}">  vrijeme BETWEEN UNIX_TIMESTAMP(STR_TO_DATE('${param.odDate}','%Y.%m.%d %k.%i.%s')) AND UNIX_TIMESTAMP(STR_TO_DATE('${param.doDate}','%Y.%m.%d %k.%i.%s')) </c:if>
                <c:if test="${param.odDate!='' && param.naredba!=''}"> AND </c:if> 
                <c:if test="${param.naredba!=''}"> zahtjev LIKE '%${param.naredba}%' </c:if> 
            </c:if>
        </sql:query>

        <display:table  name="${ispis.rows}" pagesize="${param.stranica}">
            <display:column property="zahtjev" title="Zahtjev"/>
            <display:column property="vrijeme_obrade" title="Vrijeme obrade (u ms)"/>
            <display:column property="vrijeme" title="Vrijeme"/>
        </display:table>

    </body>
</html>