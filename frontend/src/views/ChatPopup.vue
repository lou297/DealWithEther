<template>
  <div style="text-align: left; margin:15px;">
    <h3 style="margin-left:12px; text-align:center; font-size:20px;">문의톡</h3><br><br>
    <form class="form-horizontal">
      <!-- <div class="form-group">
        <label for="room" class="col-sm-2 control-label">Room</label>
          <input type="text" style="border:1px solid black;" class="form-control" id="room">
      </div><br> -->
    </form>
    <ul id="chat"></ul>
    <footer>
       <form class="form-horizontal">
      <div class="form-group" style="float:left;">
          <input type="text" style="width: 80%;" class="footerchat" id="msg">
      </div>
      <div class="form-group" style="float:right; padding-right:30px;">
        <div class="col-sm-offset-2 col-sm-10">
          <button type="submit" class="footerchat" style="width: 10%;">전송</button>
        </div>
      </div>
      </form>
      <div style="clear:both;"></div>
    </footer>
  </div>
</template>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<script src="/socket.io/socket.io.js"></script>
<!-- The core Firebase JS SDK is always required and must be listed first -->
<script src="https://www.gstatic.com/firebasejs/7.22.0/firebase-app.js"></script>
<script src="https://www.gstatic.com/firebasejs/7.22.0/firebase-analytics.js"></script>

<script>

const firebase = require("firebase");
require("firebase/firestore");

  var firebaseConfig = {
    apiKey: "AIzaSyBLERzEBv4p5978AxszC7zqy8aQXXfxCY8",
    authDomain: "ssafy2.firebaseapp.com",
    databaseURL: "https://ssafy2.firebaseio.com",
    projectId: "ssafy2",
    storageBucket: "ssafy2.appspot.com",
    messagingSenderId: "435147211758",
    appId: "1:435147211758:web:c225ef95ebbd44f73284ff",
    measurementId: "G-M80C8DTDRF"
  };
  // Initialize Firebase
  firebase.initializeApp(firebaseConfig);
  firebase.analytics();

  var db = firebase.firestore();
</script>

<script>

export default {
  components: {
  },
  data() {
    return {
      name : this.$route.params.name,
      id : this.$route.params.id
    };
  },
  created() {
    var vm = this;
    $(function() {

        const io = require('socket.io-client');
        var chat = io.connect("http://localhost:8000/chat");

        $("form").submit(function(e) {
            db.collection("chatting").add({
              message: "Ada",
            })

          e.preventDefault();

          // 서버로 자신의 정보를 전송한다.
          chat.emit("chat message", {
            name: vm.name,
            room: vm.id,
            msg: $("#msg").val()
          });
        });

        // 서버로부터의 메시지가 수신되면
        chat.on("chat message", function(data) {
          
          $("#chat").append(vm.name).append(' : '+data.msg).append($('<br>'));
        });
      });
  },
}
</script>

<style>

.footerchat{
  border:1px solid black;
  position: fixed; 
  bottom: 0; 
  margin-bottom: 10px;
}
.triangle-border {
  position:relative;
  padding:10px 10px 10px 10px;
  width:330px;
  border:4px solid #5a8f00;
  color:#333;
  background:#fff;
  -webkit-border-radius:10px;
  -moz-border-radius:10px;
  border-radius:10px;
}

</style>
