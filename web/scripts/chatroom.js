var webSocket = null;

function openConnection() {
    if (webSocket === null) {
        webSocket = new WebSocket(`ws://${window.location.host}/Fassenger/chatroom`);

        webSocket.onopen = event => {

        }

        webSocket.onmessage = event => {
            var responseObj = JSON.parse(event.data);

            switch (responseObj.type) {
                case "message":
                {
                    let chatBubbleContainer = document.createElement('div');

                    let chatBubble = document.createElement('span');
                    let date = document.createElement('span');
                    let avatar = document.createElement('span');
                    let avaImg = document.createElement('img');

                    avaImg.title = responseObj.user;
                    avaImg.src = "./ava/" + responseObj.user;
                    avaImg.style.width = "100%";
                    avaImg.classList.add('small-ava');
                    // Hide avatar of the last message if they are from the same person
                    let lastUser = $("#chatBox").last().children().last().children(".small-ava").children().attr("title");
                    if (lastUser === responseObj.user) {
                        $("#chatBox").last().children().last().children(".small-ava").css("visibility", "hidden");
                    }

                    chatBubble.innerHTML = responseObj.text;
                    date.innerHTML = responseObj.date;
                    date.classList.add('date');
                    avatar.classList.add('small-ava');

                    avatar.appendChild(avaImg);

                    if (responseObj.isSender) {
                        chatBubble.classList.add('chat-bubble-right');
                        chatBubbleContainer.classList.add('chat-bubble-container-right');

                        chatBubbleContainer.appendChild(date);
                        chatBubbleContainer.appendChild(chatBubble);
                    } else {
                        chatBubble.classList.add('chat-bubble-left');
                        chatBubbleContainer.classList.add('chat-bubble-container-left');

                        chatBubbleContainer.appendChild(avatar);
                        chatBubbleContainer.appendChild(chatBubble);
                        chatBubbleContainer.appendChild(date);
                    }

                    chatBox.appendChild(chatBubbleContainer);
                    chatBox.scrollTop = chatBox.scrollHeight;
                }
                break;
                case "status":
                {
                    let userContainer = document.createElement('div');
                    let avatar = document.createElement('span');
                    let avaImg = document.createElement('img');
                    
                    let userNameBubble = document.createElement('span');
                    userNameBubble.innerHTML = responseObj.user;
                    
                    avaImg.title = responseObj.user;
                    avaImg.src = "./ava/" + responseObj.user;
                    avaImg.style.width = "100%";
                    avaImg.classList.add('small-ava');
                    
                    userContainer.appendChild(avatar);
                    userContainer.appendChild(userNameBubble);
                    
                    userOnlineBox.appendChild(userContainer);
                }
            }
        };

        webSocket.onclose = () => webSocket = null;
    }
}

function sendMessage() {
    if (webSocket !== null && webSocket.readyState == WebSocket.OPEN) {
        webSocket.send(userInput.value);
        userInput.value = "";
    }
}

function handleKeyPress(event) {
    // Number 13 is the "Enter" key on the keyboard
    if (event.keyCode === 13) {
        // Cancel the default action, if needed
        event.preventDefault();
        // Trigger the button element with a click
        document.getElementById("sendMessageBtn").click();
    }
}