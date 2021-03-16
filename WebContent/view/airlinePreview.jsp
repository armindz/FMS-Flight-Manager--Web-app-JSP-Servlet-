<%@ page language="java" contentType="text/html; charset=ISO-8859-2"
	pageEncoding="ISO-8859-2"%>
<%@page import="models.Flight"%>
<%@page import="models.Airline"%>
<%@page import="management.FlightManagementSystem"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">
<title>FMS - Airline</title>
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

        <a href="../index.html"> <img id="logo" src="img/icons/fms transparent.png"></a>
        <div class="navbarsections">
            <div class="dropdown">
                <button class="dropbtn">Create &#11206;</button>
                <div class="dropdown-content">
                    <a href="form/airlineForm.html">Airline</a>
                    <a href="form/airportForm.html">Airport</a>
                    <a href="form/flightForm.jsp">Flight</a>
                </div>
            </div>

            <div class="dropdown">
                <button class="dropbtn">List &#11206;</button>
                <div class="dropdown-content">
                    <a href="list/airlineList.jsp">Airline</a>
                    <a href="list/airportList.jsp">Airport</a>
                    <a href="list/flightList.jsp">Flight</a>
                </div>
            </div>


            <div class="dropdown">
                <button class="dropbtn">Modify &#11206;</button>
                <div class="dropdown-content">
                    <a href="#">Airline</a>
                    <a href="#">Airport</a>
                    <a href="#">Flight</a>
                </div>
            </div>

			 <a id="settingsButton" href="../settings.jsp"> <img  src="../img/icons/settingsIcon.ico"></a>
			 
            <form class="logoutButton" action="LogoutServlet" method="GET">
                <button class="logoutbtn">Log out!</button>
            </form>
        </div>
    </header>
	<%
	Airline airline = (Airline) request.getAttribute("airlineData");
	%>
	<div class="viewAirlineContainer">

		<div class="airlineDataPreview">

			<p>
				Full name:
				<%=airline.getAirlineCallsign()%>
			</p>
			<p>
				Codename:
				<%=airline.getAirlineCodename()%></p>
			<p>
				Country:
				<%=airline.getAirlineCountry()%></p>

		</div>



		<div class="airlineFlightsTable">
			<%!FlightManagementSystem flightms = new FlightManagementSystem();%>

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

							if (fetchDataToList.get(i).getAirline().getAirlineCodename().equals(airline.getAirlineCodename())) {
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
									value="<%=fetchDataToList.get(i).getFlight_id()%>" /> <input
									type="submit" name="remove" value="Remove" />
							</form>
							<form id="update" action="FlightUpdateServlet" method="GET"
								name="updateid">
								<input type="hidden" name="product_id"
									value="<%=fetchDataToList.get(i).getFlight_id()%>" /> <input
									type="submit" name="update" value="Update" />
							</form>
							<form id="viewFlight" action="BookAFlight" method="GET"
								name="vievFlightId">
								<input type="hidden" name="product_id"
									value="<%=fetchDataToList.get(i).getFlight_id()%>" /> <input
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