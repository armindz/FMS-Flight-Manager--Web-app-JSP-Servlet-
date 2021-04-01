<%@ page language="java" contentType="text/html; charset=ISO-8859-2"
	pageEncoding="ISO-8859-2"%>
<%@page import="java.util.ArrayList"%>
<%@page import="models.FlightTicket"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-2">
<title>Flight Ticket - FMS</title>
<link rel="icon" href="../vimg/icons/fmsround.png" type="image/x-icon">
<link rel="stylesheet" type="text/css" href="css/style.css">
<link href='https://fonts.googleapis.com/css?family=Bubbler One '
	rel='stylesheet '>
</head>
<body style="background-image:none">
	<div class="ticketPreview" onclick="window.print()">

		<%
		FlightTicket flightTicket = (FlightTicket) request.getAttribute("flightTicketData");
		%>

		<%
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm");
		String strDate = formatter.format(date);
		%>
		<div class="ticketPreviewTable1">
			<img class="ticketPreviewAirplaneIcon"
				src="img/icons/ticketpreviewicon.png"> <img class="barcode"
				src="img/icons/barcode.png">
			<h2>BOARDING PASS</h2>
			<div class="table1left">

				<label for=" airportFROM ">FROM</label>
				<h3 class="airportFROM ">
					<%=flightTicket.getFlight().getAirport().getAirportCodename()%>
				</h3>



				<label for="name ">NAME</label>
				<h5 class="name "><%=flightTicket.getBuyersName()%></h5>

				<label for="flightID ">FLIGHT ID</label>
				<h5 class="flightID "><%=flightTicket.getFlight().getFlightId()%></h5>

				<label for="date ">DATE</label>
				<h5 class="DATE "><%=formatter.format(flightTicket.getFlight().getDateOfFlight().getTime())%></h5>

				<label for="flightPrice ">PRICE</label>
				<h5 class="flightPrice "><%=flightTicket.getFlight().getFlightPrice()%>
					KM
				</h5>
			</div>

			<div class="table1right ">


				<label for="airportTO ">TO</label>
				<h3 class="airportTO ">
					<%=flightTicket.getFlight().getDestinationAirport().getAirportCodename()%></h3>

				<label for="airline ">AIRLINE</label>
				<h5 class="airline ">
					<%=flightTicket.getFlight().getAirline().getAirlineCodename()%></h5>

				<label for="seat ">SEAT</label>
				<h5 class="seat ">
					<%=flightTicket.getSeat().getSeatRow()%></h5>

				<label for="row ">NUMBER</label>
				<h5 class="row ">
					<%=flightTicket.getSeat().getSeatNumber()%></h5>

				<label for="flightClass ">FLIGHT CLASS</label>
				<h5 class="flightClass ">
					<%=flightTicket.getFlight().getFlightClass()%></h5>
			</div>

		</div>
		<div class="ticketPreviewTable2 ">
			<h3>Boarding Pass</h3>

			<label for=" airportFROM ">FROM</label>
			<h5 class="airportFROM ">
				<%=flightTicket.getFlight().getAirport().getAirportCodename()%>
			</h5>

			<label for="airportTO ">TO</label>
			<h5 class="airportTO "><%=flightTicket.getFlight().getDestinationAirport().getAirportCodename()%></h5>
			<br />
			<br />
			<br /> <label for="airline ">AIRLINE</label>
			<h5 class="airline ">
				<%=flightTicket.getFlight().getAirline().getAirlineCodename()%></h5>
			<br />
			<br />
			<br /> <label for="name ">NAME</label>
			<h5 class="name ">
				<%=flightTicket.getBuyersName()%></h5>
			<br />
			<br />
			<br /> <label for="flightID ">FLIGHT ID</label>
			<h5 class="flightID "><%=flightTicket.getFlight().getFlightId()%></h5>
			<label for="flightPrice ">PRICE</label>
			<h5 class="flightPrice "><%=flightTicket.getFlight().getFlightPrice()%>
				KM
			</h5>

			<br />
			<br />
			<br /> <label for="date ">DATE</label>
			<h5 class="DATE"><%=formatter.format(flightTicket.getFlight().getDateOfFlight().getTime())%></h5>
			<br />
			<br />
			<br /> <label for="seat ">SEAT</label>
			<h5 class="seat ">
				<%=flightTicket.getSeat().getSeatRow()%></h5>

			<label for="row ">ROW</label>
			<h5 class="row "><%=flightTicket.getSeat().getSeatNumber()%></h5>

		</div>




	</div>
</body>
</html>