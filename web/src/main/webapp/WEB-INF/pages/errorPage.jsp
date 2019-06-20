<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>


<tags:master pageTitle="Error">
    <div class="container-fluid">
        <h2 style="text-align: center">Something went wrong! ${errorMsg}</h2>
    </div>
</tags:master>
