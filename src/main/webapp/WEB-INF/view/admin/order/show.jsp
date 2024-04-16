<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="utf-8" />
            <meta http-equiv="X-UA-Compatible" content="IE=edge" />
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
            <meta name="description" content="" />
            <meta name="author" content="" />
            <title>Dashboard - SB Admin</title>
            <link href="/css/styles.css" rel="stylesheet" />
            <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        </head>

        <body class="sb-nav-fixed">
            <jsp:include page="../layout/header.jsp" />
            <div id="layoutSidenav">
                <jsp:include page="../layout/sidebar.jsp" />
                <div id="layoutSidenav_content">
                    <main>
                        <div class="container-fluid px-4">
                            <h1 class="mt-4">Manage Orders</h1>
                            <ol class="breadcrumb mb-4">
                                <li class="breadcrumb-item active"> <a href="/admin">Dashboard</a></li>
                                <li class="breadcrumb-item active"> Orders</li>
                            </ol>
                            <div>
                                <c:if test="${ empty orders}">
                                    <tr>
                                        <td colspan="6">
                                            <div class="alert alert-danger" role="alert">
                                                Không có sản phẩm trong giỏ hàng
                                              </div>
                                            
                                        </td>
                                    </tr>
                                </c:if>
                                <c:if test="${not empty orders}">
                                <table class="table table-bordered table-hover">
                                    <thead>
                                        <tr>
                                            <th scope="col">ID</th>
                                            <th scope="col">Total Price</th>
                                            <th scope="col">User</th>
                                            <th scope="col">Status</th>
                                            <th scope="col">Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="order" items="${orders}">
                                            <tr>
                                                <th scope="row">${order.id}</th>
                                                <td>
                                                    <fmt:formatNumber type="number"
                                                    value="${order.totalPrice}" /> đ
                                                </td>
                                                <td>${order.user.fullName}</td>
                                                <td>${order.status}</td>
                                                <td>
                                                    <a href="/admin/order/${order.id}"><button
                                                            class="btn btn-success">View</button></a>
                                                    <a href="/admin/order/update/${order.id}"><button
                                                            class="btn btn-warning mx-2">Update</button></a>
                                                    <a href="/admin/order/delete/${order.id}"><button
                                                            class="btn btn-danger">Delete</button></a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                              
                                    </tbody>
                                </table>
                                  <!-- phân trang -->
                                  <nav aria-label="Page navigation example">
                                    <ul class="pagination justify-content-center">
                                      <li class="page-item">
                                        <a 
                                        class="${ 1 eq currentPage ? 'disabled page-link' : 'page-link'}" 
                                        href="/admin/order?page=${currentPage - 1}" 
                                        aria-label="Previous">
                                          <span aria-hidden="true">&laquo;</span>
                                        </a>
                                      </li>

                                      <c:forEach begin="0" end="${totalPages-1}" varStatus="loop">
                                        <li class="page-item">
                                            <a class="${(loop.index + 1) eq currentPage ? 'active page-link' : 'page-link'}" href="/admin/order?page=${loop.index + 1}">
                                                 ${loop.index + 1}
                                            </a>
                                        </li>
                                   
                                        </c:forEach>
                                  
                                      <li class="page-item">
                                        <a 
                                        class="${ totalPages eq currentPage ? 'disabled page-link' : 'page-link'}"
                                        href="/admin/order?page=${currentPage + 1}" 
                                          aria-label="Next">
                                          <span aria-hidden="true">&raquo;</span>
                                        </a>
                                      </li>
                                    </ul>
                                  </nav>
                            </c:if>
                            </div>
                        </div>
                    </main>

                    <jsp:include page="../layout/footer.jsp" />

                </div>
            </div>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                crossorigin="anonymous"></script>
            <script src="js/scripts.js"></script>
        </body>

        </html>