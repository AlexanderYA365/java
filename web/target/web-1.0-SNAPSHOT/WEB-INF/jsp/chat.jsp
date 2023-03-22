<%--
  Created by IntelliJ IDEA.
  User: Александр
  Date: 26.11.2022
  Time: 17:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Чат</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
    <style>
        .messages {
            background-color: #369;
            width: 500px;
            padding: 20px;
            overflow: auto;
            height: 550px;
        }

        .messages .message {
            background-color: #FFF;
            border-radius: 10px;
            margin-bottom: 10px;
        }

        .messages .message .msg .from {
            background-color: #396;
            line-height: 30px;
            text-align: center;
            color: white;
        }

        .messages .message .msg .text {
            padding: 10px;
        }

        textarea.text-message {
            width: 540px;
            padding: 10px;
            resize: none;
            border: none;
            box-shadow: 2px 2px 5px 0 inset;
        }

        .blok1 {
            display: none;
        }

    </style>
    <script>
        let chatUnit = {
            init() {
                this.chatMessage = document.querySelector(".chat-message");
                this.chatMessageContainer = document.querySelector(".message");
                this.messageTextArea = this.chatMessage.querySelector("text-message");
                this.openSocket();
            },
            onOpenSocket() {
                this.messageTextArea = addEventListener("keyup", e => {
                    if (e.ctrlKey && e.keyCode === 13) {
                        e.preventDefault();
                        this.send();
                    }
                });
            },
            send() {
                let sendingText = document.getElementById("text-message").value;
                let date = new Date();
                let sending = {
                    senderId: senderId,
                    receiverId: receiverId,
                    message: sendingText,
                    picture: '',
                    publicationDate: date,
                    edited: 'false',
                    usernameSender: username,
                    usernameReceiving: usernameReceiving,
                    messageType: 'PRIVATE'
                };
                console.log("sending - " + sending)
                this.sendMessage(sending);
            },
            onMessage(message) {
                console.log("message.usernameSender - " + message.usernameSender)
                console.log("message.usernameReceiving - " + message.usernameReceiving)
                let name = (message.usernameSender == username) ? "Я" : message.usernameSender;
                let messageDate = new Date(message.publicationDate);
                let msgBlock = document.createElement("div");
                msgBlock.className = "msg";
                let fromBlock = document.createElement("div");
                fromBlock.className = "from";
                fromBlock.innerText = name;
                let textBlock = document.createElement("div");
                textBlock.className = "text";
                textBlock.innerText = messageDate.getMonth() + " " + messageDate.getDay() + " "
                    + messageDate.getHours() + ":" + messageDate.getMinutes()
                    + ":" + messageDate.getSeconds() + " - " + message.message;
                msgBlock.appendChild(fromBlock);
                msgBlock.appendChild(textBlock);
                this.chatMessageContainer.appendChild(msgBlock);
            },
            onClose() {
            },
            sendMessage(message) {
                this.onMessage(message)
                document.getElementById("text-message").value = "";
                document.querySelector(".messages").scrollIntoView(false);
                this.ws.send(JSON.stringify(message));
            },
            openSocket() {
                username = document.querySelector('#username').innerText.trim();
                usernameReceiving = document.querySelector('#usernameReceiving').innerText.trim();
                usernameSender = document.querySelector('#usernameSender').innerText.trim();
                senderId = document.querySelector('#senderId').innerText.trim();
                var url = window.location;
                var string = url.toString();
                let newUrl = string.substring(5, string.lastIndexOf("/"));
                receiverId = document.querySelector('#receiverId').innerText.trim();
                this.ws = new WebSocket("ws:" + newUrl + "/goChat/" + username);
                this.ws.onopen = () => this.onOpenSocket();
                this.ws.onmessage = (e) => this.onMessage(JSON.parse(e.data));
                this.ws.onclose = (e) => this.onClose();
            },
            onLoad() {
                let listForJavascript = [];
                <c:forEach items="${personalMail}" var="message">
                var arr = [];
                arr.push("<c:out value="${message.senderId}" />");
                arr.push("<c:out value="${message.receiverId}" />");
                arr.push("<c:out value="${message.message}" />");
                arr.push("<c:out value="${message.picture}" />");
                arr.push("<c:out value="${message.publicationDate}" />");
                arr.push("<c:out value="${message.edited}" />");
                arr.push("<c:out value="${message.usernameSender}" />");
                arr.push("<c:out value="${message.usernameReceiving}" />");
                arr.push("<c:out value="${message.messageType}" />");
                listForJavascript.push(arr);
                </c:forEach>
                for (let i = 0; i < listForJavascript.length; i++) {
                    this.onMessage({
                        usernameSender: listForJavascript[i][6],
                        usernameReceiving: listForJavascript[i][7],
                        message: listForJavascript[i][2],
                        publicationDate: listForJavascript[i][4]
                    });
                }
                let block = document.getElementById("f");
                block.scrollTop = block.scrollHeight;
                console.log(block)
            }
        };
        window.addEventListener("load", e => chatUnit.init());
        window.addEventListener("load", e => chatUnit.onLoad());
    </script>
</head>
<body>
<%@ include file="navbar.jsp" %>

<div class="blok1">
    <span id="username"><a href="show-friend?id=${id}">${username}</a></span>
    <input id="personalMail" value='${personalMail}'/>
    <span id="usernameReceiving">${usernameReceiving}</span>
    <span id="usernameSender">${usernameSender}</span>
    <span id="senderId">${senderId}</span>
    <span id="receiverId">${receiverId}</span>
</div>

<div align="center" class="chat-message">
    <div id="f" class="messages">
        <div id="message" class="message">
        </div>
    </div>
    <textarea id="text-message" class="text-message">
    </textarea>
</div>
</body>
</html>
