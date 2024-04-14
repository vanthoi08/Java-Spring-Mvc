
<%@page contentType="text/html" pageEncoding="UTF-8" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thông tin tài khoản</title>
     <!-- Google Web Fonts -->
     <link rel="preconnect" href="https://fonts.googleapis.com">
     <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
     <link
         href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap"
         rel="stylesheet">

     <!-- Icon Font Stylesheet -->
     <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" />
     <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
         rel="stylesheet">

     <!-- Libraries Stylesheet -->
     <link href="/client/lib/lightbox/css/lightbox.min.css" rel="stylesheet">
     <link href="/client/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
     <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

     <!-- Customized Bootstrap Stylesheet -->
     <link href="/client/css/bootstrap.min.css" rel="stylesheet">

     <!-- Template Stylesheet -->
     <link href="/client/css/style.css" rel="stylesheet">
</head>
<script>
    $(document).ready(() => {
        const avatarFile = $("#avatarFile");
        const orgImage = "${currentUser.avatar}";
        if (orgImage) {
            const urlImage = "/images/avatar/" + orgImage;
            $("#avatarPreview").attr("src", urlImage);
            $("#avatarPreview").css({ "display": "block" });
        }

        avatarFile.change(function (e) {
            const imgURL = URL.createObjectURL(e.target.files[0]);
            $("#avatarPreview").attr("src", imgURL);
            $("#avatarPreview").css({ "display": "block" });
        });
    });
</script>

<body>
    <jsp:include page="../layout/header.jsp" />
    <div class="container-fluid py-5">
        
            <div  style="margin-top: 100px; margin-left: 480px; ">
                <nav aria-label="breadcrumb">
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="/">Home</a></li>
                        <li class="breadcrumb-item active" aria-current="page">Thông tin tài khoản</li>
                    </ol>
                </nav>
            </div>
            <div class="mt-3">
                <div class="row">
                    <div class="col-md-6 col-12 mx-auto">
                        <form:form method="post" action="/user-update-infor"
                            modelAttribute="currentUser" class="row g-2" enctype="multipart/form-data">

                            <!-- Khai báo các biến -->
                            <c:set var="errorEmail">
                                <form:errors path="email" cssClass="invalid-feedback" />
                            </c:set>
                            <c:set var="errorPassword">
                                <form:errors path="password" cssClass="invalid-feedback" />
                            </c:set>
                            <c:set var="errorFullName">
                                <form:errors path="fullName" cssClass="invalid-feedback" />
                            </c:set>


                            <h3 class="mt-3">Thông tin tài khoản</h3>
                            <hr>
                            <div class="col-md-6  col-12">

                                <label for="email" class="form-label">Email:</label>
                                <form:input type="email"
                                    class="form-control ${not empty errorEmail ? 'is-invalid' : ''}"
                                    id="email" path="email" disabled="true" />
                                ${errorEmail}
                            </div>
                            
                            <div class="col-md-6  col-12">
                                <label for="phone" class="form-label">Phone number:</label>
                                <form:input type="text" class="form-control" id="phone"
                                    path="phone" />
                            </div>
                            <div class="col-md-6  col-12">
                                <label for="fullName" class="form-label">Full name:</label>
                                <form:input type="text"
                                    class="form-control ${not empty errorFullName ? 'is-invalid' : ''}"
                                    id="fullName" path="fullName" />
                                ${errorFullName}
                            </div>

                            <div class="col-md-6 col-12">
                                <label for="inputRole" class="form-label">Role:</label>
                                <form:select id="inputRole" class="form-select" path="role.name">
                                    <form:option disabled="true" value="${currentUser.role.name}">${currentUser.role.name}</form:option>
                                </form:select>
                            </div>

                            <div class="col-12">
                                <label for="address" class="form-label">Address:</label>
                                <form:input type="text" class="form-control" id="address"
                                    path="address" />
                            </div>
                           <div class="col-md-6  col-12">
                                <label for="password" class="form-label">Current Password:</label>
                                <form:input type="password"
                                    class="form-control ${not empty errorPassword ? 'is-invalid' : ''}"
                                    id="password" path="" />
                                ${errorPassword}
                            </div>
                            <div class="col-md-6  col-12">
                                <label for="password" class="form-label">New Password:</label>
                                <form:input type="password"
                                    class="form-control ${not empty errorPassword ? 'is-invalid' : ''}"
                                    id="password" path="" />
                                ${errorPassword}
                            </div>

                            <div class="col-md-6  col-12">
                                <label for="avatarFile" class="form-label">Avatar:
                                </label>
                                <input class="form-control" type="file" id="avatarFile"
                                    accept=".png, .jpg, .jpeg" name="hoidanitFile" />
                            </div>
                            <div class="col-12 ">
                                <c:choose>
                                    <c:when test="${currentUser.getAvatar() == null}">
                                        <img style="max-height: 250px; display: none;"
                                            alt="avatar preview" id="avatarPreview" />
                                    </c:when>
                                    <c:otherwise>
                                        <img style="max-height: 250px; display: block;"
                                            alt="avatar preview" id="avatarPreview"
                                            src="images/avatar/${currentUser.avatar}" />
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="col-12 mt-5">
                                <button type="submit" class="btn btn-primary">Update</button>
                                <a href="/"><button type="button"
                                        class="btn btn-secondary mx-3">Cancel</button></a>
                            </div>

                        </form:form>
                    </div>
                </div>

            </div>

 
    </div>


   
   

    <jsp:include page="../layout/footer.jsp" />
        <!-- Back to Top -->
        <a href="#" class="btn btn-primary border-3 border-primary rounded-circle back-to-top"><i
            class="fa fa-arrow-up"></i></a>

      <!-- JavaScript Libraries -->
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
      <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
      <script src="/client/lib/easing/easing.min.js"></script>
      <script src="/client/lib/waypoints/waypoints.min.js"></script>
      <script src="/client/lib/lightbox/js/lightbox.min.js"></script>
      <script src="/client/lib/owlcarousel/owl.carousel.min.js"></script>

      <!-- Template Javascript -->
      <script src="/client/js/main.js"></script>
</body>
</html>