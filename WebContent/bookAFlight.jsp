
<%@ page import="models.Flight"%>
<%@ page import="booking.BookingFlightTicket"%>
<%@ page import="management.FlightManagementSystem"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="models.Seat"%>
<%@ page import="database.FlightTicketDatabase"%>
<%@ page import="models.FlightTicket"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">
<title>FMS - Book a flight</title>
<link rel="icon" href="img/icons/fmsround.png" type="image/x-icon">
<link rel="stylesheet" type="text/css" href="css/style.css">
<link href='https://fonts.googleapis.com/css?family=Bubbler One '
	rel='stylesheet '>
</head>


<body>



	<header>

		<a href="index.html"> <img id="logo"
			src="img/icons/fms transparent.png"></a>
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
					<a href="#">Airline</a> <a href="#">Airport</a> <a href="#">Flight</a>
				</div>
			</div>
			
			 <a id="settingsButton" href="settings.jsp"> <img  src="img/icons/settingsIcon.ico"></a>
			 
			<form class="logoutButton" action="LogoutServlet" method="GET">
				<button class="logoutbtn">Log out!</button>
			</form>
		</div>
	</header>

	<div class="ticketPreview">

		<% Flight flight = (Flight)request.getAttribute("flightData"); %>

		<% Date date = new Date();  
SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy hh:mm");  
String strDate= formatter.format(date);  
 %>
		<div class="ticketPreviewTable1">
			<img class="ticketPreviewAirplaneIcon"
				src="img/icons/ticketpreviewicon.png"> <img class="barcode"
				src="img/icons/barcode.png">
			<h2>BOARDING PASS</h2>
			<div class="table1left">

				<label for=" airportFROM ">FROM</label>
				<h3 class="airportFROM ">
					<%= flight.getAirport().getAirportCodename() %>
				</h3>



				<label for="name ">NAME</label>
				<h5 class="name ">YOUR NAME</h5>

				<label for="flightID ">FLIGHT ID</label>
				<h5 class="flightID "><%= flight.getFlight_id() %></h5>

				<label for="date ">DATE</label>
				<h5 class="DATE "><%= formatter.format(flight.getDateOfFlight().getTime()) %></h5>

				<label for="flightPrice ">PRICE</label>
				<h5 class="flightPrice "><%= flight.getFlightPrice() %>
					KM
				</h5>
			</div>

			<div class="table1right ">


				<label for="airportTO ">TO</label>
				<h3 class="airportTO ">
					<%= flight.getDestinationAirport().getAirportCodename() %></h3>

				<label for="airline ">AIRLINE</label>
				<h5 class="airline ">
					<%=flight.getAirline().getAirlineCodename() %></h5>

				<label for="seat ">SEAT</label>
				<h5 class="seat ">
					<%= flight.getSeatRow() %></h5>

				<label for="row ">NUMBER</label>
				<h5 class="row ">
					<%= flight.getSeatNumber() %></h5>

				<label for="flightClass ">FLIGHT CLASS</label>
				<h5 class="flightClass ">
					<%= flight.getFlightClass() %></h5>
			</div>

		</div>
		<div class="ticketPreviewTable2 ">
			<h3>Boarding Pass</h3>

			<label for=" airportFROM ">FROM</label>
			<h5 class="airportFROM ">
				<%= flight.getAirport().getAirportCodename() %>
			</h5>

			<label for="airportTO ">TO</label>
			<h5 class="airportTO "><%= flight.getDestinationAirport().getAirportCodename() %></h5>
			<br />
			<br />
			<br /> <label for="airline ">AIRLINE</label>
			<h5 class="airline ">
				<%=flight.getAirline().getAirlineCodename() %></h5>
			<br />
			<br />
			<br /> <label for="name ">NAME</label>
			<h5 class="name ">YOUR NAME</h5>
			<br />
			<br />
			<br /> <label for="flightID ">FLIGHT ID</label>
			<h5 class="flightID "><%= flight.getFlight_id() %></h5>
			<label for="flightPrice ">PRICE</label>
			<h5 class="flightPrice "><%= flight.getFlightPrice() %>
				KM
			</h5>

			<br />
			<br />
			<br /> <label for="date ">DATE</label>
			<h5 class="DATE"><%= formatter.format(flight.getDateOfFlight().getTime()) %></h5>
			<br />
			<br />
			<br /> <label for="seat ">SEAT</label>
			<h5 class="seat ">
				<%= flight.getSeatRow() %></h5>

			<label for="row ">ROW</label>
			<h5 class="row "><%= flight.getSeatNumber() %></h5>

		</div>




	</div>



	<div class="ticketReservation">

		<div class="ticketReservationForm">

			<form class="ticketReservationForm1"
				action="BookingFlightTicketServlet" method="GET">
				
				
				<%! FlightManagementSystem flightms = new FlightManagementSystem();%>
                <%!	ArrayList <Flight> flightFromList =  flightms.getListOfFlights();%>
			
				<label for="flightID">Flight ID:</label><br> 
				<select name="flightID" id="flightIdSelect">
					
					<% for(int i = 0; i < flightFromList.size(); i++) {
                			if ( flightFromList.get(i).getFlight_id() == flight.getFlight_id()) {  %>
								<option value=<%= flight.getFlight_id()%> selected><%= flight.getFlight_id()%> 
								<%=flight.getAirport().getAirportCodename()%> 
								<%=flight.getDestinationAirport().getAirportCodename() %></option>
							
                			<% } else { %>
								<option value=<%= flightFromList.get(i).getFlight_id()%>><%= flightFromList.get(i).getFlight_id()%> 
								<%= flightFromList.get(i).getAirport().getAirportCodename() %>
								<%= flightFromList.get(i).getDestinationAirport().getAirportCodename() %></option>

					<% 
                		}
                	}
                	
                	%>
				</select>
				<br> <label for="name">Name:</label><br> <input
					type="text" name="name" /> <br />
				<br> <label for="seatRow">SEAT ROW:</label><br> <input
					type="text" name="seatRow" /> <br />
				<br /> <label for="seatNumber">SEAT NUMBER:</label><br> <input
					type="number" name="seatNumber" /> <br />
				<br /> <input class="buttonform" type="submit" value="Create" />

			</form>


		</div>

		<div class="ticketReservationSeatPreview">

			<% BookingFlightTicket bft = new BookingFlightTicket();
				
				ArrayList <Seat> listOfSeats = flightms.getListOfSeats();
				%>
			<table class="seatIconTable">

				<tr>

					<% 
					for(int i=0; i < listOfSeats.size();i++) {

						if(listOfSeats.get(i).getFlightId()==flight.getFlight_id()) {
					
							
						%>

					<td>
						<% 	 if(bft.isSeatAvailable(listOfSeats.get(i).getFlightId(),listOfSeats.get(i).getSeatRow(), listOfSeats.get(i).getSeatNumber())){ %>
						<img id="seatIcon"
						title="<%=listOfSeats.get(i).getSeatRow()%> 
							  <%=listOfSeats.get(i).getSeatNumber()%>"
						src="img/icons/seaticon.png"> <% 
						 } 
				
						else { %> <img id="seatIcon"
						title="<%=listOfSeats.get(i).getSeatRow()%>  
							<%=listOfSeats.get(i).getSeatNumber()%> "
						src="img/icons/seatnotavailableicon.png"> <% } %>

					</td>

					<%
							
							if(listOfSeats.get(i).getSeatNumber() == flight.getSeatNumber()) {
								
								
								%>
				</tr>



				<%
						}
						 
						
						%>


				<% 
			}
			}
				%>
			</table>



		</div>


	</div>
	
	
	<!--  BOOKED FLIGHT USER LIST -->
	
	<div class="flightTicketTable">
                            <%! FlightTicketDatabase flightTicketdb = new FlightTicketDatabase(); %>

                                <table class="flightListTable" style="width:100%">
                                    <tr>

                                        <th>Flight ID</th>
                                        <th>Airline</th>
                                        <th>Departure Airport</th>
                                        <th>Destination Airport</th>
                                        <th>Flight class</th>
                                        <th>Date of flight</th>
                                        <th>Latest seat row</th>
                                        <th>Seats per row</th>
                                        <th>Flight price</th>
                                        <th>Name</th>
                                        <th>Functions</th>
                                    </tr>
                                    <tr>


                                        <%	
 							
 						try{
 						ArrayList <FlightTicket> fetchDataToList =  bft.fetchFlightTicketDatabaseContentToList();  
 						for (int i=0; i <=fetchDataToList.size(); i++) 
 						
 						{   if(fetchDataToList.get(i).getFlightId() == flight.getFlight_id()) {     %>

                                          <td>
						<form id="viewFlightID" action="BookAFlight" method="GET"
							name="vievFlightId">
							<input type="hidden" name="product_id"
								value="<%=fetchDataToList.get(i).getFlightId()%>" /> <input
								type="submit" name="view"
								value="<%=fetchDataToList.get(i).getFlightId()%>" />
						</form>

					</td>
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
                                            <td>
                                                <%= fetchDataToList.get(i).getFlightClass() %>
                                            </td>
                                            <td>
                                                <%= fetchDataToList.get(i).getDateOfFlight().getTime() %>
                                            </td>
                                            <td>
                                                <%= fetchDataToList.get(i).getSeatRow() %>
                                            </td>
                                            <td>
                                                <%= fetchDataToList.get(i).getSeatNumber() %>
                                            </td>
                                            <td>
                                                <%= fetchDataToList.get(i).getFlightPrice() %> KM
                                            </td>
                                            <td>
                                          	    <%= fetchDataToList.get(i).getBuyersName() %>
                                            </td>
                                            <td>
                                            <div class="tablefunctions">
                                                <form id="remove" action="FlightTicketRemoveServlet" method="GET" name="removeid">
                                                    <input type="hidden" name="product_id" value="<%=fetchDataToList.get(i).getFlightId()%>" />
                                                    <input type="hidden" name="seatRow" value="<%=fetchDataToList.get(i).getSeatRow()%>" />
                                                    <input type="hidden" name="seatNumber" value="<%=fetchDataToList.get(i).getSeatNumber()%>" />
                                                    <input type="submit" name="remove" value="Remove" />
													</form>
                                                   
                                                        </div>
                                            </td>
                                    </tr>

                                    <%
                                            
 						} } }  catch (Exception e){
 						
 						%>
                                        <h3 style="text-align:center">Something went wrong. List may be empty. </h3>
                                        

                                        <% } %>

                                </table>

</div>

</body>

</html>