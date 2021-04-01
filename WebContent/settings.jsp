<%@ page import="javax.servlet.http.HttpSession" %>

<html>

<head>
    <meta charset="ISO-8859-1">
    <title>FMS - Settings</title>
    <link rel="icon" href="img/icons/fmsround.png" type="image/x-icon">
      <link rel="stylesheet" type="text/css" href="css/style.css">
    <link href='https://fonts.googleapis.com/css?family=Bubbler One ' rel='stylesheet '>
</head>


<body>
  


    <header>

		<a href="index.html"> <img id="logo"
			src="img/icons/fms transparent.png"></a>
		<div class="navbarsections">
			<div class="dropdown">
			<a href="list/airlineList.jsp">	<button class="dropbtn">Airline</button></a>
				
			</div>

			<div class="dropdown">
				<a href="list/airportList.jsp"><button class="dropbtn">Airport</button></a>
			</div>


			<div class="dropdown">
				<a href="list/flightList.jsp"><button class="dropbtn">Flight</button></a>
				
			</div>
			
			<div class="dropdown">
				<a href="list/flightList.jsp"><button class="dropbtn">Book a flight</button></a>
				
			</div>

			<a id="settingsButton" href="settings.jsp"> <img
				src="img/icons/settingsIcon.ico"></a>
				
			<form class="logoutButton" action="LogoutServlet" method="GET">
				<button class="logoutbtn">Log out!</button>
			</form>
		
		</div>
	</header>
    <div class="createAccountInSettings">

		
        <form class="loginform" action="UserAddServlet" method="GET">
            <img id="loginavatar" src="img\icons\loginavatar.png">
            <p>Create Account</p>
            <label for="username">Username:</label><br>
            <input type="text" name="username" /><br/><br>

            <label for="password">Password:</label><br>
            <input type="password" name="password" /> <br/><br>

            <input class="buttonform" type="submit" value="Create" />
            <br/>


        </form>
        
        
    </div>

</body>

</html>