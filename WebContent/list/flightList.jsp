<%@page import="management.FlightManagementSystem"%>
    <%@ page import="database.FlightDatabase" %>
        <%@page import="java.util.ArrayList"%>
            <%@page import="models.Flight"%>
                <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
                    <!DOCTYPE html>
                    <html>

                    <head>
                        <meta charset="ISO-8859-1">
                        <title>FMS - Flight List</title>
                        <link rel="icon" href="../img/icons/fmsround.png" type="image/x-icon">
                         <link rel="stylesheet" type="text/css" href="../css/style.css">
                        <link href='https://fonts.googleapis.com/css?family=Bubbler One ' rel='stylesheet '>
                    </head>


                    <body>
                       


                       <header>

		<a href="../index.html"> <img id="logo"
			src="../img/icons/fms transparent.png"></a>
		<div class="navbarsections">
			<div class="dropdown">
			<a href="airlineList.jsp">	<button class="dropbtn">Airline</button></a>
				
			</div>

			<div class="dropdown">
				<a href="airportList.jsp"><button class="dropbtn">Airport</button></a>
			</div>


			<div class="dropdown">
				<a href="flightList.jsp"><button class="dropbtn">Flight</button></a>
				
			</div>
			
			<div class="dropdown">
				<a href="flightList.jsp"><button class="dropbtn">Book a flight</button></a>
				
			</div>

			<a id="settingsButton" href="../settings.jsp"> <img
				src="../img/icons/settingsIcon.ico"></a>

			<form class="logoutButton" action="../LogoutServlet" method="GET">
				<button class="logoutbtn">Log out!</button>
			</form>
		</div>
	</header>

                        <h3 id="flightlist">Flight list </h3>
                        
                        <div class="managementFunctions">
                        <a id="createFlightBtn" href="../form/flightForm.jsp">ADD FLIGHT</a>
                        </div>
                        <%! FlightManagementSystem flightms = new FlightManagementSystem(); %>
                            <%! FlightDatabase flightdb = new FlightDatabase(); %>

                                <table class="flightListTable" style="width:100%">
                                    <tr>
                                        <th>Airline</th>
                                        <th>Departure Airport</th>
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
 							
 						try{
 						ArrayList <Flight> fetchDataToList =  flightms.fetchFlightDatabaseContentToList();  
 						for (int i=0; i <fetchDataToList.size(); i++) 
 						
 						{       %>

                            
					<td>
						<form id="airlineFromList" action="../AirlinePreviewServlet"
							method="GET" name="airlineFromList">
							<input type="hidden" name="product_id"
								value="<%=fetchDataToList.get(i).getAirline().getAirlineCodename()%>" />
							<input type="submit" name="airline"
								value="<%=fetchDataToList.get(i).getAirline().getAirlineCodename()%>" />
						</form>

					</td>
					<td>

						<form id="airportFromList" action="../AirportPreviewServlet"
							method="GET" name="airportFromList">
							<input type="hidden" name="product_id"
								value="<%=fetchDataToList.get(i).getAirport().getAirportCodename()%>" />
							<input type="submit" name="airport"
								value="<%=fetchDataToList.get(i).getAirport().getAirportCodename()%>" />
						</form>

					</td>
					<td>

						<form id="destinationAirportFromList"
							action="../AirportPreviewServlet" method="GET"
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
                                            <div class="tablefunctions">
                                                <form id="remove" action="../FlightRemoveServlet" method="GET" name="removeid">
                                                    <input type="hidden" name="product_id" value="<%=fetchDataToList.get(i).getFlightId()%>" />
                                                    <input type="submit" name="remove" value="Delete" />
													</form>
                                                    <form id="update" action="../FlightUpdateServlet" method="GET" name="updateid">
                                                        <input type="hidden" name="product_id" value="<%=fetchDataToList.get(i).getFlightId()%>" />
                                                        <input type="submit" name="update" value="Edit" />
														</form>
                                                        <form id="viewFlight" action="../BookAFlight" method="GET" name="viewFlight">
                                                            <input type="hidden" name="product_id" value="<%=fetchDataToList.get(i).getFlightId()%>" />
                                                            <input type="submit" name="view" value="View" />

                                                        </form>
                                                        </div>
                                            </td>
                                    </tr>

                                    <%
                                            
 						} }  catch (Exception e){
 						
 						%>
                                        <h3 style="text-align:center">Something went wrong. List may be empty. </h3>
                                        

                                        <% } %>

                                </table>



                    </body>

                    </html>