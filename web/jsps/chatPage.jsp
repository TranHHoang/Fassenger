<%-- 
    Document   : chatPage
    Created on : Mar 5, 2019, 8:53:37 AM
    Author     : Kiruu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <!--    <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Chat page</title>
    
            <script src="./scripts/chatroom.js"></script>
            <script>openConnection()</script>
            fix this change to dynamic
        </head>
        <body>
            <input id="textMessage" type="text" />
            <input onclick="sendMessage()" value="Send Message" type="button" /> <br/><br/>
            <textarea id="textAreaMessage" rows="10" cols="50"></textarea>
            <a href="logout">Sign out</a>
            
        </body>-->
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chat page</title>

        <script src="./scripts/chatroom.js"></script>
        <script>openConnection()</script>
        <!--fix this change to dynamic-->

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css" integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
        <link rel="stylesheet" href="./styles/style.css">
        <script
            src="http://code.jquery.com/jquery-3.3.1.js"
            integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60="
            crossorigin="anonymous"></script>
    </head>
    <body>
        <!--<input id="textMessage" type="text" />-->
        <!--<input onclick="sendMessage()" value="Send Message" type="button" /> <br/><br/>-->
        <!--<textarea id="textAreaMessage" rows="10" cols="50"></textarea>-->
        <div class="container" style="max-width: 100%">
            <div class="row">
                <div class="col-12">
                    <div class="card card-signin my-5" style="background-color: rgba(255,255,255,0.9); min-height: 93vh; margin-bottom: 1.5rem!important; margin-top: 1.5rem!important">

                        <div class="card-body row">
                            <div class="col" style="display: flex; flex-flow: column;">
                                <h5 class="card-title text-center">Online Users</h5>
                                <div class="col-md-11" style="background: white; border-radius: 1.5rem; flex-grow: 1; margin-bottom: 1.5rem">

                                    <!--<textarea id="textAreaMessage" rows="10" cols="50"></textarea>-->
                                </div>
                            </div>

                            <div class="col-7" style="display: flex; flex-flow: column;">
                                <div id="chatBox" class="col-md-11" style="overflow-y: scroll; overflow-x: hidden; border-radius: 1.5rem; flex-grow: 1; margin-bottom: 1.5rem; height: 78vh">

                                    <!--<textarea id="textAreaMessage" rows="10" cols="50"></textarea>-->
                                </div>
                                <div>
                                    <input onkeypress="handleKeyPress(event)" type="text" style="vertical-align: middle; display: inline; padding: var(--input-padding-y) var(--input-padding-x); height: auto; border-radius: 2rem;" id="userInput" class="form-control col-md-9" placeholder="Write something here...">
                                    <button title="Send an image" class="btn btn-light" style="width: 50px; height: 50px; border-radius: 50%" value="Send"><i class="far fa-image"></i></button>
                                    <button title="Send an attachment" class="btn btn-light" style="width: 50px; height: 50px; border-radius: 50%" value="Send"><i class="fas fa-paperclip"></i></button>
                                    <button id="sendMessageBtn" onclick="sendMessage()" title="Press ENTER to send" class="btn btn-primary" style="width: 50px; height: 50px; border-radius: 50%" value="Send"><i class="fas fa-arrow-right"></i></button>
                                </div>
                            </div>

                            <div class="col">
                                <h5 class="card-title text-center">User</h5>
                                <form action="room" method="POST" enctype="multipart/form-data" style="display: none">
                                    <input id="input-btn" name="avatar" type="file">
                                    <input id="clickHere" type="submit">
                                </form>
                                <button onclick="clickBtn()" title="Change avatar" class="btn btn-light" style="width: 50px; height: 50px; border-radius: 50%" value="Send"><i class="fas fa-upload"></i></button>
                                <a href="logout"><button id="logoutBtn" title="Logout" class="btn btn-light" style="width: 50px; height: 50px; border-radius: 50%" value="Send"><i class="fas fa-sign-out-alt"></i></button><a/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script>
            function clickBtn(){
                document.getElementById("input-btn").click()
            }
            
            $("#input-btn").change(function(e) {
                ${"clickHere"}.click()
            });
            
        </script>
        
    </body>
</html>
