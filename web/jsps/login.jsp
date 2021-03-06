<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <c:if test="${not empty userName}">
        <c:redirect url="./room"></c:redirect>
    </c:if>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="./styles/bootstrap-4.3.1-dist/css/bootstrap.css">
        <link rel="stylesheet" href="./styles/style.css">

        <link rel="stylesheet" href="./styles/fontawesome-free-5.8.1-web/css/all.css">
        <script src="./scripts/jquery-3.3.1.min.js"></script>
        <script src="./scripts/randomImg.js"></script>

    </head>
    <body style="background: linear-gradient(rgba(0, 0, 0, 0.2), rgba(0, 0, 0, 0.2)) no-repeat fixed center; background-size: cover">
        <div class="container">
            <div class="row">
                <div class="col-md-9 col-md-7 col-lg-5 mx-auto">
                    <h2 class="page-title">
                        <svg width="64" height="64">
                        <mask id="mask" x="0" y="0">
                            <rect x="0" y="0" width="64" height="64" fill="#fff"/>
                            <text text-anchor="middle" x="32" y="32" dy="10" style="font-weight: bold">Fa</text>
                        </mask>
                        <rect fill="white" x="0" y="0" rx="32" width="64" height="64" mask="url(#mask)" fill-opacity="0.75"/>    
                        </svg><b style="margin-left: 2px">ssenger</b>
                        <sub>global chat <i class="fas fa-comments"></i></sub>
                    </h2>

                    <div class="card card-signin my-5" style="background-color: rgba(255,255,255,0.5);">

                        <div class="card-body">
                            <div class="col-lg">
                                <h5 class="card-title text-center"><i class="far fa-user"></i> Sign In</h5>
                                <form class="form-signin" action="login" method="POST">

                                    <c:if test="${not empty status}">
                                        <c:choose>
                                            <c:when test="${status == 'SUCCESS'}">
                                                <div class="alert alert-success">${message}</div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="alert alert-danger">${message}</div>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:if>

                                    <div class="form-label-group">
                                        <input type="text" id="inputUserNameSignIn" name="userName" class="form-control" placeholder="Username" required autofocus>
                                        <label for="inputUserNameSignIn">Username</label>
                                    </div>

                                    <div class="form-label-group">
                                        <input type="password" id="inputPasswordSignIn" name="password" class="form-control" placeholder="Password" required>
                                        <label for="inputPasswordSignIn">Password</label>
                                    </div>

                                    <button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit"><i class="fas fa-sign-in-alt"></i> Sign in</button>
                                </form>
                            </div>


                            <hr class="my-4">

                            <div class="col">
                                <h5 class="card-title text-center">Or Sign Up</h5>
                                <form class="form-signin" action="register" method="POST">

                                    <div class="form-label-group">
                                        <input name="userName" type="text" id="inputUserNameSignUp" class="form-control" placeholder="Username" required>
                                        <label for="inputUserNameSignUp">Username</label>
                                    </div>

                                    <div class="form-label-group">
                                        <input name="password" type="password" id="inputPasswordSignUp" class="form-control" placeholder="Password" required>
                                        <label for="inputPasswordSignUp">Password</label>
                                    </div>

                                    <div class="form-label-group">
                                        <input name="repassword" type="password" id="inputRePasswordSignUp" class="form-control" placeholder="Re-enter password" required>
                                        <label for="inputRePasswordSignUp">Re-enter password</label>
                                    </div>

                                    <button class="btn btn-lg btn-success btn-block text-uppercase" type="submit"><i class="fas fa-user-plus"></i> Sign up</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
