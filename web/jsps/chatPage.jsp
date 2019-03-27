<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="refresh" content="11280">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chat page</title>

        <script src="./scripts/chatRoom.js"></script>
        <link rel="stylesheet" href="./styles/bootstrap-4.3.1-dist/css/bootstrap.css">

        <link rel="stylesheet" href="./styles/fontawesome-free-5.8.1-web/css/all.css">
        <link rel="stylesheet" href="./styles/style.css">
        <script src="./scripts/jquery-3.3.1.min.js"></script>
        <script src="./scripts/randomImg.js"></script>

    </head>
    <body onload="openConnection()" style="background: linear-gradient(rgba(0, 0, 0, 0.9), rgba(0, 0, 0, 0.9)) no-repeat fixed center ; background-size: cover">
        <div class="container" style="max-width: 100%">
            <div class="row">
                <div class="col-12">
                    <div class="card card-signin my-5" style="background-color: rgba(255,255,255,0.1); min-height: 93vh; margin-bottom: 1.5rem!important; margin-top: 1.5rem!important">

                        <div class="card-body row">
                            <div class="col" style="display: flex; flex-flow: column;">
                                <h5 class="card-title text-center h4"><b style="margin-right: .5rem; color: white; font-weight: bold">Who is online?</b></h5>
                                <div id="userOnlineBox" class="col-md-11 scrollbar scrollbar-near-moon" style="overflow: auto; border-radius: 1.5rem; flex-grow: 1; margin-bottom: 1.5rem">
                                </div>
                            </div>

                            <div class="col-7" style="display: flex; flex-flow: column;">
                                <div style="overflow: hidden">
                                    <div id="chatBox" class="scrollbar scrollbar-near-moon" style="width: 100%; height: 100%; overflow-y: scroll; padding-right: 17px;box-sizing: content-box; border-radius: 1.5rem; flex-grow: 1; margin-bottom: 1.5rem; height: 78vh">
                                        <div id="chatBoxLoadMoreBtn" style="text-align: center">
                                            <a href="#" onclick="loadMore()" style="color: white;">Load more</a>
                                        </div>
                                    </div>
                                </div>
                                <div>
                                    <input onkeypress="handleKeyPress(event)" type="text" style="vertical-align: middle; display: inline; padding: var(--input-padding-y) var(--input-padding-x); height: auto; border-radius: 2rem; background: white; color: white; border: 1px dashed white" id="userInput" class="form-control col-10" placeholder="Write something here..." required>
                                    <button onClick="clickBtn('imageInputField')" title="Send an image" class="btn btn-light" style="height: 50px; border-radius: .75rem" value="Send"><i class="far fa-image"></i> Image</button>
                                    <button id="sendMessageBtn" onclick="sendMessage()" title="Press ENTER to send" class="btn btn-info" style="height: 50px; border-radius: .75rem" value="Send"><i class="fas fa-arrow-right"></i></button>

                                    <form id="formChatImage" action="image" method="POST" enctype="multipart/form-data" style="display: none">
                                        <input id="imageInputField" name="uploadImage" type="file"/>
                                    </form>
                                </div>
                            </div>

                            <div class="col" style="text-align: center">
                                <h4 class="card-title text-center"><b style=" color: white; font-weight: bold">Hello there,</b></h4>
                                <div style="text-align: center">
                                    <img src="./ava/${userName}" style="object-fit: cover;border-radius: 50%; display: inline-block; width: 256px; height: 256px; margin-bottom: 1rem">
                                </div>
                                <div style="margin-bottom: 1rem">
                                    <h2 style=" color: white; font-weight: bold">${nickName}</h2>
                                    <div style=" color: white;">@${userName}</div>
                                </div>

                                <form action="ava" method="POST" enctype="multipart/form-data" style="display: none">
                                    <input id="input-btn" name="avatar" type="file">
                                    <input id="clickHere" type="submit">
                                </form>
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

            function loadMore() {
                $.ajax({
                    type: "POST",
                    url: "room",
                    success: datas => {
                        var data = JSON.parse(datas)
                        console.log(data[0])
                        for (var i = 0; i < data.length; i++) {
                            if (data[i].isSender === true) {
                                if (data[i].text === undefined) {
                                    $("#chatBoxLoadMoreBtn").after("<div class='chat-bubble-container-right'><span class='date'>" + data[i].date + "</span><img src='./image/" + data[i].image + "' class='small-image'></div>");
                                } else {
                                    $("#chatBoxLoadMoreBtn").after("<div class='chat-bubble-container-right'><span class='date'>" + data[i].date + "</span><span class='chat-bubble-right'>" + data[i].text + "</span></div>");
                                }
                            } else {
                                let avaVisibility = "visible";
                                let lastUser = $("#chatBox").last().children().eq(1).children(".small-ava").children().attr("title");
                                if (lastUser === data[i].user) {
                                    avaVisibility = "hidden";
                                }

                                if (data[i].text === undefined) {
                                    $("#chatBoxLoadMoreBtn").after("<div class='chat-bubble-container-left'><span class='small-ava' style='visibility: " + avaVisibility + "'><img title='" + data[i].user + "' width='100%' src='./ava/" + data[i].user + "' class='small-ava'></span><img src='./image/" + data[i].image + "' class='small-image'><span class='date'>" + data[i].date + "</span></div>");

                                } else {
//                                    $("#chatBoxLoadMoreBtn").after("<div class='chat-bubble-container-left'><span class='chat-bubble-left'>" + data[i].text + "</span><span class='date'>" + data[i].date + "</span></div>");
//                                    $("#chatBoxLoadMoreBtn").after(`<div class='chat-bubble-container-left'><img title='${data[i].user}' width="100%" src='./ava/${data[i].user}' class="small-ava"><span class='chat-bubble-left'>${data[i].text}</span><span class='date'>${data[i].date}</span></div>`);
                                    $("#chatBoxLoadMoreBtn").after("<div class='chat-bubble-container-left'><span class='small-ava' style='visibility: " + avaVisibility + "'><img title='" + data[i].user + "' width='100%' src='./ava/" + data[i].user + "' class='small-ava'></span><span class='chat-bubble-left'>" + data[i].text + "</span><span class='date'>" + data[i].date + "</span></div>");

                                }
                            }

                        }
                    }
                })
            }
        </script>
    </body>
</html>
