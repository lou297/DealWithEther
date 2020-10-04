<template>
  <div style="text-align: left; margin:15px;">
    <h3 style="margin-left:12px; text-align:center; font-size:20px;">문의톡</h3><br>
    <form class="form-horizontal">
      <div class="form-group">
        <label for="name" class="col-sm-2 control-label">Name</label>
          <input type="text" style="border:1px solid black;" class="form-control" id="name">
      </div><br>
      <div class="form-group">
        <label for="room" class="col-sm-2 control-label">Room</label>
          <input type="text" style="border:1px solid black;" class="form-control" id="room">
      </div><br>
      <div class="form-group">
        <label for="msg" class="col-sm-2 control-label">Message</label>
          <input type="text" style="border:1px solid black;" class="form-control" id="msg">
      </div><br>
      <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
          <button type="submit" class="btn btn-default">전송</button>
          <!-- <button class="btn btn-default" @click="Chatting()">Send</button> -->
        </div>
      </div>
    </form>
    <ul id="chat"></ul>
  </div>
</template>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<script src="/socket.io/socket.io.js"></script>
<script>

$(function() {
        // 지정 namespace로 접속한다
        const io = require('socket.io-client');
        var chat = io.connect("http://localhost:8000/chat");

        $("form").submit(function(e) {
          e.preventDefault();

          // 서버로 자신의 정보를 전송한다.
          chat.emit("chat message", {
            name: $("#name").val(),
            room: $("#room").val(),
            msg: $("#msg").val()
          });
        });

        // 서버로부터의 메시지가 수신되면
        chat.on("chat message", function(data) {
          $("#chat").append($("#name").val()).append(" : "+data.msg).append($('<br>'));
        });
      });
</script>
