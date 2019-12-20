<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<tags:master pageTitle="Registration page">
    <div class="container">

        <form:form method="post" modelAttribute="userForm" class="form-signin">
            <h2 class="form-signin-heading">Create your account</h2>
            <div class="form-group">
                <form:input type="text" path="username" class="form-control" placeholder="Username" autofocus="true"/>
                <form:errors path="username" cssStyle="color: red"/>
            </div>

            <div class="form-group">
                <form:input type="password" path="password" class="form-control" placeholder="Password"/>
                <form:errors path="password" cssStyle="color: red"/>
            </div>

            <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
        </form:form>

    </div>
</tags:master>