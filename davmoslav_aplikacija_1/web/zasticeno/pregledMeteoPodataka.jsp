<%-- 
    Document   : pregledPrikupljenihMeteoPodataka
    Created on : Jun 11, 2014, 11:29:10 AM
    Author     : Davor
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pregled meteo podataka</title>
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
        
        <form action="${pageContext.servletContext.contextPath}/Kontroler" method="POST" accept-charset="UTF-8">
            Unos nove adrese:<br />
            Nova Adresa:<input name="novaAdresa" id="novaAdresa" />
            <input type="submit" value="Unos" name="unosNoveAdrese"/>
        </form>
        <br /><hr/><br/>

        <form action="pregledMeteoPodataka.jsp" method="POST" accept-charset="UTF-8">
            Filtriranje podataka:
            <br/>
            Adresa:<input type="text" name="adresa" value="${param.adresa}" id="adresa" />
            <br/>
            Unesite od  <input name="odDate" id="odDate" value="${param.odDate}" class="odDate"  type="datetime"/>
            Unesite do  <input name="doDate" id="doDate" value="${param.doDate}" class="doDate"  type="datetime"/>
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

        <sql:query dataSource="${meteo}" var="ispis">
            SELECT adresa,  DATE_FORMAT(FROM_UNIXTIME(`vrijeme`) ,  '%Y.%m.%d %k.%i.%s' ) as 'vrijeme' , vlaga, kisa, temperatura, vjetar, snijeg FROM davmoslav_meteo 
            <c:if test="${!empty param.odDate || !empty param.adresa}">
                WHERE  <c:if test="${param.odDate!=''}">  vrijeme BETWEEN UNIX_TIMESTAMP(STR_TO_DATE('${param.odDate}','%Y.%m.%d %k.%i.%s')) AND UNIX_TIMESTAMP(STR_TO_DATE('${param.doDate}','%Y.%m.%d %k.%i.%s')) </c:if>
                <c:if test="${param.odDate!='' && param.adresa !=''}"> AND </c:if>
                <c:if test="${param.adresa !=''}"> adresa LIKE '%${param.adresa}%' </c:if>
            </c:if>
        </sql:query>

        <display:table  name="${ispis.rows}" pagesize="${param.stranica}">
            <display:column property="adresa" title="Adresa"/>
            <display:column property="vlaga" title="Vlaga"/>
            <display:column property="kisa" title="Kiša"/>
            <display:column property="temperatura" title="Temperatura"/>
            <display:column property="vjetar" title="Brzina vjetra"/>
            <display:column property="snijeg" title="Snijeg"/>   
            <display:column property="vrijeme" title="Vrijeme preuzimanja"/>
        </display:table>

    </body>
</html>
