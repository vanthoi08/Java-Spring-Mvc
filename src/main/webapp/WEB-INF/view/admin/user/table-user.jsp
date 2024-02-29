<html lang="en">
<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <!-- Latest compiled and minified CSS -->
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
                <!-- Latest compiled JavaScript -->
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
                <title>List Users</title>
            </head>

            <body>
                <div class="container mt-5">
                    <div class="row">
                        <div class="col-12 mx-auto">
                            <div class="d-flex justify-content-between">
                                <h3>Table users</h3>
                                <a href="/admin/user/create"><button class="btn btn-primary">Create a user</button></a>
                            </div>
                            <hr>
                            <table class="table table-bordered table-hover">
                                <thead>
                                    <tr>
                                        <th scope="col">ID</th>
                                        <th scope="col">Email</th>
                                        <th scope="col">Full Name</th>
                                        <th scope="col">Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="user" items="${users1}">
                                        <tr>
                                            <th scope="row">${user.id}</th>
                                            <td>${user.email}</td>
                                            <td>${user.fullName}</td>
                                            <td>
                                                <a href="/admin/user/${user.id}"><button
                                                        class="btn btn-success">View</button></a>
                                                <a href="/admin/user/update/${user.id}"><button
                                                        class="btn btn-warning mx-2">Update</button></a>
                                                <a href="/admin/user/delete/${user.id}"><button
                                                        class="btn btn-danger">Delete</button></a>
                                            </td>
                                        </tr>
                                    </c:forEach>


                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

            </body>

</html>