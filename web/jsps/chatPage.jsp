<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="refresh" content="11280">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chat page</title>

        <script src="./scripts/chatRoom.js"></script>

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css" integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
        <link rel="stylesheet" href="./styles/style.css">
        <script
            src="http://code.jquery.com/jquery-3.3.1.min.js"
            integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
        crossorigin="anonymous"></script>
    </head>
    <body onload="openConnection()" style="background: linear-gradient(rgba(0, 0, 0, 0.2), rgba(0, 0, 0, 0.2)), 
          url('https://source.unsplash.com/featured/1280x720/?nature,travel,wallpapers,backgrounds,weather,technology') no-repeat fixed center; background-size: cover">
        <div class="container" style="max-width: 100%">
            <div class="row">
                <div class="col-12">
                    <div class="card card-signin my-5" style="background-color: rgba(255,255,255,0.9); min-height: 93vh; margin-bottom: 1.5rem!important; margin-top: 1.5rem!important">

                        <div class="card-body row">
                            <div class="col" style="display: flex; flex-flow: column;">
                                <h5 class="card-title text-center h4"><b style="margin-right: .5rem">Who is online?</b></h5>
                                <div id="userOnlineBox" class="col-md-11 scrollbar scrollbar-near-moon" style="overflow-y: scroll; overflow-x: hidden; border-radius: 1.5rem; flex-grow: 1; margin-bottom: 1.5rem">
                                </div>
                            </div>

                            <div class="col-7" style="display: flex; flex-flow: column;">
                                <div id="chatBox" class="col-md-11 scrollbar scrollbar-near-moon" style="overflow-y: scroll; overflow-x: hidden; border-radius: 1.5rem; flex-grow: 1; margin-bottom: 1.5rem; height: 78vh">
                                </div>
                                <div>
                                    <input onkeypress="handleKeyPress(event)" type="text" style="vertical-align: middle; display: inline; padding: var(--input-padding-y) var(--input-padding-x); height: auto; border-radius: 2rem;" id="userInput" class="form-control col-md-9" placeholder="Write something here...">
                                    <button onClick="clickBtn('imageInputField')" title="Send an image" class="btn btn-light" style="width: 50px; height: 50px; border-radius: 50%" value="Send"><i class="far fa-image"></i></button>
                                    <button title="Send an attachment" class="btn btn-light" style="width: 50px; height: 50px; border-radius: 50%" value="Send"><i class="fas fa-paperclip"></i></button>
                                    <button id="sendMessageBtn" onclick="sendMessage()" title="Press ENTER to send" class="btn btn-primary" style="width: 50px; height: 50px; border-radius: 50%" value="Send"><i class="fas fa-arrow-right"></i></button>

                                    <form id="formChatImage" action="image" method="POST" enctype="multipart/form-data" style="display: none">
                                        <input id="imageInputField" name="uploadImage" type="file"/>
                                    </form>
                                </div>
                            </div>

                            <div class="col" style="text-align: center">
                                <h4 class="card-title text-center"><b>Hello there,</b></h4>
                                <div style="text-align: center">
                                    <img src="./ava/${userName}" style="border-radius: 50%; display: inline-block; width: 256px; height: 256px; margin-bottom: 1rem">
                                </div>
                                <div style="margin-bottom: 1rem">
                                    <h2>${nickName}</h2>
                                    <div>@${userName}</div>
                                </div>

                                <form action="ava" method="POST" enctype="multipart/form-data" style="display: none">
                                    <input id="input-btn" name="avatar" type="file">
                                    <input id="clickHere" type="submit">
                                </form>
                                <button title="Change nickname" class="btn btn-light" style="width: 50px; height: 50px; border-radius: 50%" value="Send"><i class="fas fa-signature"></i></button>
                                <button onclick="clickBtn('input-btn')" title="Change avatar" class="btn btn-light" style="width: 50px; height: 50px; border-radius: 50%" value="Send"><i class="fas fa-user-circle"></i></button>
                                <a href="logout"><button id="logoutBtn" title="Logout" class="btn btn-light" style="width: 50px; height: 50px; border-radius: 50%" value="Send"><i class="fas fa-sign-out-alt"></i></button><a/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script>
            function clickBtn(button) {
                document.getElementById(button).click();
            }

            $("#input-btn").change(() => {
            ${"clickHere"}.click();
            });

            $("#imageInputField").change(() => {
                var form = document.querySelector('form');
                var formData = new FormData(form);

                $.ajax({
                    type: "POST",
                    contentType: false,
                    processData: false,
                    url: "image",
                    data: formData,
                    success: imageUrl => {
                        webSocket.send("image " + imageUrl);
                    }
                });
            });
        </script>
    </body>
</html>
