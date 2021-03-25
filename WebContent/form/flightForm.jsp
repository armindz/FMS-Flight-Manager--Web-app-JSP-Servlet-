<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="management.AirlineManagementSystem" %>
        <%@ page import="java.util.ArrayList" %>
            <%@ page import="models.Airline" %>
                <%@ page import="management.AirportManagementSystem" %>
                    <%@ page import="models.Airport" %>
                        <!DOCTYPE html>
                        <html>

                        <head>
                            <meta charset="ISO-8859-1">
                            <title>FMS - Create Flight</title>
                            <link rel="icon" href="../img/icons/fmsround.png" type="image/x-icon">
                             <link rel="stylesheet" type="text/css" href="../css/style.css">
                            <link href='https://fonts.googleapis.com/css?family=Bubbler One ' rel='stylesheet '>
                            <link href='https://fonts.googleapis.com/css?family=Nunito' rel='stylesheet'>
                        </head>


                        <body>
                           


                            <header>

		<a href="../index.html"> <img id="logo"
			src="../img/icons/fms transparent.png"></a>
		<div class="navbarsections">
			<div class="dropdown">
			<a href="../list/airlineList.jsp">	<button class="dropbtn">Airline</button></a>
				
			</div>

			<div class="dropdown">
				<a href="../list/airportList.jsp"><button class="dropbtn">Airport</button></a>
			</div>


			<div class="dropdown">
				<a href="../list/flightList.jsp"><button class="dropbtn">Flight</button></a>
				
			</div>
			
			<div class="dropdown">
				<a href="../list/flightList.jsp"><button class="dropbtn">Book a flight</button></a>
				
			</div>

			<a id="settingsButton" href="../settings.jsp"> <img
				src="../img/icons/settingsIcon.ico"></a>

			<form class="logoutButton" action="LogoutServlet" method="GET">
				<button class="logoutbtn">Log out!</button>
			</form>
		</div>
	</header>


  <h2 id="formHeading">Flight Creator</h2>

                            <form class="flightform" action="../FlightAddServlet" method="GET">
                                <img class="formavatar" src="..\img\icons\flightavatar.png">
								<div class="table1">
                                <label for="airlineCodename">Airline Codename: </label><br>
                                <select name="airlineCodename" id="airlineCodenameSelect">
         <%! AirlineManagementSystem airlinems = new AirlineManagementSystem(); %>
         <% ArrayList <Airline> fetchAirlineDataToList = airlinems.fetchDatabaseContentToList(); 
      		for(int i=0; i < fetchAirlineDataToList.size(); i++) { %>
   			 <option value="<%= fetchAirlineDataToList.get(i).getAirlineCodename()%>"><%= fetchAirlineDataToList.get(i).getAirlineCodename()%> - <%=fetchAirlineDataToList.get(i).getAirlineCallsign() %></option>
   			 
   			 <% }  %>
   		
    		
 			 </select> <br/><br/>

                                <label for="airportCodename">Airport Codename:</label><br>
                                <select name="airportCodename" id="airportCodenameSelect">
         <%! AirportManagementSystem airportms = new AirportManagementSystem(); %>
         <% ArrayList <Airport> fetchAirportDataToList = airportms.fetchDatabaseContentToList(); 
      		for(int i=0; i < fetchAirportDataToList.size(); i++) { %>
   			 <option value="<%= fetchAirportDataToList.get(i).getAirportCodename()%>"><%= fetchAirportDataToList.get(i).getAirportCodename()%> - <%= fetchAirportDataToList.get(i).getAirportFullname() %></option>
   			 
   			 <% }  %>
   			  </select> <br/><br/>
                                <label for="destinationAirportCodename">Destination Airport Codename:</label><br>
                                <select name="destinationAirportCodename" id="destinationAirportCodenameSelect">

      	<%	for(int i=0; i < fetchAirportDataToList.size(); i++) { %>
   			 <option value="<%= fetchAirportDataToList.get(i).getAirportCodename()%>"><%= fetchAirportDataToList.get(i).getAirportCodename()%> - <%= fetchAirportDataToList.get(i).getAirportFullname() %></option>
   			 
   			 <% }  %>
   			  </select> <br/><br/>
                                <label for="flightClass">Flight Class:</label><br>
                                <select name="flightClass" id="flightClassSelect">
   			 <option value="FIRST CLASS">First class</option>
    			<option value="BUSINESS CLASS">Business class</option>
   				 <option value="ECONOMY CLASS">Economy class</option>
 			 </select> <br/><br/>
			</div>
			
			<div class="table2">
                                <label for="dateOfFlight">Date of flight:</label><br>
                                <input type="datetime-local" name="dateOfFlight" /> <br/><br/>

                                <label for="seatRow">Latest seat row:</label><br>
                                <select name="seatRow" id="seatRowSelect">
   			 <option value="A">A</option>
    			<option value="B">B</option>
   				 <option value="C">C</option>
   				 <option value="D">D</option>
   				 <option value="E">E</option>
   				 <option value="F">F</option>
 			 </select> <br/><br/>

                                <label for="seatNumber">Seats per row:</label><br>
                                <input type="number" name="seatNumber" /> <br/><br/>

                                <label for="flightPrice">Flight price:</label><br>
                                <input type="number" step="any" name="flightPrice" /> <br/><br/>
</div>
                                <input class="buttonform" type="submit" value="Create" />

                            </form>


                        </body>

                        </html>