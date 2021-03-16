<%@page import="management.AirportManagementSystem"%>
<%@page import="java.util.ArrayList"%>
<%@page import="models.Airport"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">
<title>FMS - Airport List</title>
<link rel="icon" href="../img/icons/fmsround.png" type="image/x-icon">
<link rel="stylesheet" type="text/css" href="../css/style.css">
<link href='https://fonts.googleapis.com/css?family=Bubbler One '
	rel='stylesheet '>
</head>


<body>


  <header>

        <a href="../index.html"> <img id="logo" src="../img/icons/fms transparent.png"></a>
        <div class="navbarsections">
            <div class="dropdown">
                <button class="dropbtn">Create &#11206;</button>
                <div class="dropdown-content">
                    <a href="../form/airlineForm.html">Airline</a>
                    <a href="../form/airportForm.html">Airport</a>
                    <a href="../form/flightForm.jsp">Flight</a>
                </div>
            </div>

            <div class="dropdown">
                <button class="dropbtn">List &#11206;</button>
                <div class="dropdown-content">
                    <a href="airlineList.jsp">Airline</a>
                    <a href="airportList.jsp">Airport</a>
                    <a href="flightList.jsp">Flight</a>
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






	<h3 id="airportlist">Airport list</h3>
	<div class="managementFunctions">
	<a id="createAirportBtn" href="../form/airportForm.html">ADD AIRPORT</a>
	<%!AirportManagementSystem airportms = new AirportManagementSystem();%>
	</div>
	<table class="airportListTable" style="width: 100%">
		<tr>

			<th>Airport Codename</th>
			<th>Airport Fullname</th>
			<th>Airport Type</th>
			<th>Airport City</th>
			<th>Airport Country</th>
			<th>Functions</th>
		</tr>
		<tr>
			<%
			try {
				ArrayList<Airport> fetchDataToList = airportms.fetchDatabaseContentToList();
				for (int i = 0; i < fetchDataToList.size(); i++) {
			%>
			<td> <form id="airportFromList" action="../AirportPreviewServlet"
										method="GET" name="airportFromList">
										<input type="hidden" name="product_id"
										value="<%=fetchDataToList.get(i).getAirportCodename()%>" />
										<input type="submit" name="airline"
										value="<%=fetchDataToList.get(i).getAirportCodename()%>" />
									</form>
			</td>
			<td><%=fetchDataToList.get(i).getAirportFullname()%></td>
			<td><%=fetchDataToList.get(i).getAirportType()%></td>
			<td><%=fetchDataToList.get(i).getAirportCity()%></td>
			<td><%=fetchDataToList.get(i).getAirportCountry()%></td>

			<td>
			<div class="tablefunctions">
				<form id="remove" action="../AirportRemoveServlet" method="GET"
					name="removeid">
					<input type="hidden" name="product_id"
						value="<%=fetchDataToList.get(i).getAirportCodename()%>" /> <input
						type="submit" name="remove" value="Delete" />
				</form>
				<form id="update" action="../AirportUpdateServlet" method="GET"
					name="updateid">
					<input type="hidden" name="product_id"
						value="<%=fetchDataToList.get(i).getAirportCodename()%>" /> <input
						type="submit" name="update" value="Edit" />
				</form>
				<form id="viewAirport" action="../AirportPreviewServlet" method="GET"
					name="viewAirportId">
					<input type="hidden" name="product_id"
						value="<%=fetchDataToList.get(i).getAirportCodename()%>" /> <input
						type="submit" name="view" value="View" />

				</form>
				</div>
			</td>

		</tr>
		<%
		}
		} catch (Exception e) {
		%>
		<h3 style="text-align: center">Something went wrong. List may be
			empty.</h3>

		<%
		}
		%>
	</table>



</body>

</html>