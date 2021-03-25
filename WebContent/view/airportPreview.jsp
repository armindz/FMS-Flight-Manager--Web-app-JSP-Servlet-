<%@ page language="java" contentType="text/html; charset=ISO-8859-2"
	pageEncoding="ISO-8859-2"%>
<%@page import="models.Flight"%>
<%@page import="models.Airport"%>
<%@page import="management.FlightManagementSystem"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">
<title>FMS - Airport</title>
<link rel="icon" href="img/icons/fmsround.png" type="image/x-icon">
<link rel="stylesheet" type="text/css" href="css/style.css">
<link href='https://fonts.googleapis.com/css?family=Bubbler One '
	rel='stylesheet '>
<link href='https://fonts.googleapis.com/css?family=Rationale'
	rel='stylesheet'>
<link href='https://fonts.googleapis.com/css?family=Sarpanch'
	rel='stylesheet'>
<link href='https://fonts.googleapis.com/css?family=Lekton'
	rel='stylesheet'>
</head>


<body>



	<header>

		<a href="index.html"> <img id="logo"
			src="img/icons/fms transparent.png"></a>
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
	<%
	Airport airport = (Airport) request.getAttribute("airportData");
	%>
	<div class="viewAirportContainer">

		<div class="airportDataPreview">

			<p>
				Full name:
				<%=airport.getAirportFullname()%>
			</p>
			<p>
				Codename:
				<%=airport.getAirportCodename()%></p>
			<p>
				Type:
				<%=airport.getAirportType()%></p>
			<p>
				City:
				<%=airport.getAirportCity()%></p>
			<p>
				Country:
				<%=airport.getAirportCountry()%></p>

		</div>



		<div class="airportFlightsTable">
			<%!FlightManagementSystem flightms = new FlightManagementSystem();%>


			<h4>DEPARTURES</h4>
			<table class="flightListTable" style="width: 100%">


				<tr>

					<th>Airline</th>
					<th>Airport</th>
					<th>Destination Airport</th>
					<th>Flight class</th>
					<th>Date of flight</th>
					<th>Latest seat row</th>
					<th>Seats per row</th>
					<th>Flight price</th>
					<th>Functions</th>
				</tr>
				<tr>

					<%
					try {
						ArrayList<Flight> fetchDataToList = flightms.fetchFlightDatabaseContentToList();
						for (int i = 0; i < fetchDataToList.size(); i++)

						{

							if (fetchDataToList.get(i).getAirport().getAirportCodename().equals(airport.getAirportCodename())) {
					%>

					
					<td>
						<form id="airlineFromList" action="AirlinePreviewServlet"
							method="GET" name="airlineFromList">
							<input type="hidden" name="product_id"
								value="<%=fetchDataToList.get(i).getAirline().getAirlineCodename()%>" />
							<input type="submit" name="airline"
								value="<%=fetchDataToList.get(i).getAirline().getAirlineCodename()%>" />
						</form>

					</td>
					<td>

						<form id="airportFromList" action="AirportPreviewServlet"
							method="GET" name="airportFromList">
							<input type="hidden" name="product_id"
								value="<%=fetchDataToList.get(i).getAirport().getAirportCodename()%>" />
							<input type="submit" name="airport"
								value="<%=fetchDataToList.get(i).getAirport().getAirportCodename()%>" />
						</form>

					</td>
					<td>

						<form id="destinationAirportFromList"
							action="AirportPreviewServlet" method="GET"
							name="destinationAirportFromList">
							<input type="hidden" name="product_id"
								value="<%=fetchDataToList.get(i).getDestinationAirport().getAirportCodename()%>" />
							<input type="submit" name="destinationAirport"
								value="<%=fetchDataToList.get(i).getDestinationAirport().getAirportCodename()%>" />
						</form>

					</td>
					<td><%=fetchDataToList.get(i).getFlightClass()%></td>
					<td><%=fetchDataToList.get(i).getDateOfFlight().getTime()%></td>
					<td><%=fetchDataToList.get(i).getSeatRow()%></td>
					<td><%=fetchDataToList.get(i).getSeatNumber()%></td>
					<td><%=fetchDataToList.get(i).getFlightPrice()%> KM</td>
					<td>
						<div class="tablefunctions">
							<form id="remove" action="FlightRemoveServlet" method="GET"
								name="removeid">
								<input type="hidden" name="product_id"
									value="<%=fetchDataToList.get(i).getFlightId()%>" /> <input
									type="submit" name="remove" value="Delete" />
							</form>
							<form id="update" action="FlightUpdateServlet" method="GET"
								name="updateid">
								<input type="hidden" name="product_id"
									value="<%=fetchDataToList.get(i).getFlightId()%>" /> <input
									type="submit" name="update" value="Edit" />
							</form>
							<form id="viewFlight" action="BookAFlight" method="GET"
								name="vievFlightId">
								<input type="hidden" name="product_id"
									value="<%=fetchDataToList.get(i).getFlightId()%>" /> <input
									type="submit" name="view" value="View" />

							</form>
						</div>
					</td>
				</tr>

				<%
				}
				}
				} catch (Exception e) {
				%>
				<h3 style="text-align: center">Something went wrong. List may
					be empty.</h3>


				<%
				}
				%>




			</table>
			<h4>ARRIVALS</h4>
			<table class="flightListTable" style="width: 100%">
				<tr>

				
					<th>Airline</th>
					<th>Airport</th>
					<th>Destination Airport</th>
					<th>Flight class</th>
					<th>Date of flight</th>
					<th>Latest seat row</th>
					<th>Seats per row</th>
					<th>Flight price</th>
					<th>Functions</th>
				</tr>
				<tr>

					<%
					try {
						ArrayList<Flight> fetchDataToList = flightms.fetchFlightDatabaseContentToList();
						for (int i = 0; i < fetchDataToList.size(); i++)

						{

							if (fetchDataToList.get(i).getDestinationAirport().getAirportCodename().equals(airport.getAirportCodename())) {
					%>

					
					<td>
						<form id="airlineFromList" action="AirlinePreviewServlet"
							method="GET" name="airlineFromList">
							<input type="hidden" name="product_id"
								value="<%=fetchDataToList.get(i).getAirline().getAirlineCodename()%>" />
							<input type="submit" name="airline"
								value="<%=fetchDataToList.get(i).getAirline().getAirlineCodename()%>" />
						</form>

					</td>
					<td>

						<form id="airportFromList" action="AirportPreviewServlet"
							method="GET" name="airportFromList">
							<input type="hidden" name="product_id"
								value="<%=fetchDataToList.get(i).getAirport().getAirportCodename()%>" />
							<input type="submit" name="airport"
								value="<%=fetchDataToList.get(i).getAirport().getAirportCodename()%>" />
						</form>

					</td>
					<td>

						<form id="destinationAirportFromList"
							action="AirportPreviewServlet" method="GET"
							name="destinationAirportFromList">
							<input type="hidden" name="product_id"
								value="<%=fetchDataToList.get(i).getDestinationAirport().getAirportCodename()%>" />
							<input type="submit" name="destinationAirport"
								value="<%=fetchDataToList.get(i).getDestinationAirport().getAirportCodename()%>" />
						</form>

					</td>
					<td><%=fetchDataToList.get(i).getFlightClass()%></td>
					<td><%=fetchDataToList.get(i).getDateOfFlight().getTime()%></td>
					<td><%=fetchDataToList.get(i).getSeatRow()%></td>
					<td><%=fetchDataToList.get(i).getSeatNumber()%></td>
					<td><%=fetchDataToList.get(i).getFlightPrice()%> KM</td>
					<td>
						<div class="tablefunctions">
							<form id="remove" action="FlightRemoveServlet" method="GET"
								name="removeid">
								<input type="hidden" name="product_id"
									value="<%=fetchDataToList.get(i).getFlightId()%>" /> <input
									type="submit" name="remove" value="Delete" />
							</form>
							<form id="update" action="FlightUpdateServlet" method="GET"
								name="updateid">
								<input type="hidden" name="product_id"
									value="<%=fetchDataToList.get(i).getFlightId()%>" /> <input
									type="submit" name="update" value="Edit" />
							</form>
							<form id="viewFlight" action="BookAFlight" method="GET"
								name="vievFlightId">
								<input type="hidden" name="product_id"
									value="<%=fetchDataToList.get(i).getFlightId()%>" /> <input
									type="submit" name="view" value="View" />

							</form>
						</div>
					</td>
				</tr>

				<%
				}
				}
				} catch (Exception e) {
				%>
				<h3 style="text-align: center">Something went wrong. List may
					be empty.</h3>


				<%
				}
				%>

			</table>







		</div>
	</div>
</body>

</html>