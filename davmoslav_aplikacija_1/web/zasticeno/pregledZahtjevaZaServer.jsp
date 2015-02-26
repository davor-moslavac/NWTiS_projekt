<%-- 
    Document   : pregledZahtjevaZaServer
    Created on : Jun 11, 2014, 11:29:32 AM
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
        <title>Pregled zahtjeva za server</title>
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

        <form action="pregledZahtjevaZaServer.jsp" method="POST">
            Filtriranje podataka:
            <br/>
            Komanda<input name="naredba" id="naredba"/>
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
            SELECT komanda, odgovor, DATE_FORMAT(FROM_UNIXTIME(`vrijeme`) ,  '%Y.%m.%d %k.%i.%s' ) as 'vrijeme' FROM davmoslav_dnevnik

                <c:if test="${param.odDate!='' || param.naredba!=''}">  
                    WHERE <c:if test="${param.odDate!=''}">  UNIX_TIMESTAMP(STR_TO_DATE('${param.odDate}','%Y.%m.%d %k.%i.%s')) AND UNIX_TIMESTAMP(STR_TO_DATE('${param.doDate}','%Y.%m.%d %k.%i.%s')) </c:if>
                    <c:if test="${param.odDate!='' && param.naredba!=''}"> AND </c:if>
                    <c:if test="${param.naredba!=''}"> komanda LIKE '%${param.naredba}%' </c:if> 
                </c:if>


        </sql:query>

        <display:table  name="${ispis.rows}" pagesize="${param.stranica}">
            <display:column property="komanda" title="Komanda"/>
            <display:column property="odgovor" title="Odgovor"/>
            <display:column property="vrijeme" title="Vrijeme"/>
        </display:table>

    </body>
</html>
