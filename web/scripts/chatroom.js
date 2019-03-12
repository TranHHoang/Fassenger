var webSocket = null;

function openConnection() {
    if (webSocket === null) {
        webSocket = new WebSocket("ws://localhost:8080/Fassenger/chatroom");
        
        webSocket.onopen = event => {
            
        }
        
        webSocket.onmessage = event => {
            textAreaMessage.value += event.data + " \n";
        };
        
        webSocket.onclose = () => webSocket = null;
    }
}

function sendMessage() {
    if (webSocket !== null && webSocket.readyState == WebSocket.OPEN) {
        webSocket.send(textMessage.value);
        textMessage.value = "";
    }
}