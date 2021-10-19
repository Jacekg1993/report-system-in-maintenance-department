<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">

<head>

<title>Register New User Form</title>

<%@ include file="/WEB-INF/view/navigation/sources.jsp"%>

<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/form-validation.css" />

<!-- <script>

	function auto_grow(element) {
	    element.style.height = "100px";
	    element.style.height = (element.scrollHeight)+"px";
	}
	
	</script> -->

<style>
.form-control {
	width: auto;
	font-size: 18px;
	word-break: break-all;
}
</style>

</head>

<body>

	<%@ include file="/WEB-INF/view/navigation/navigation-bar.jsp"%>

	<div class="container-fluid">
		<div class="row">

			<%@ include file="/WEB-INF/view/navigation/side-bar-menu.jsp"%>

			<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">

				<div
					class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
					<h1 class="h2">Szczegóły raportu</h1>

				</div>

				<div class="table-responsive">
					<table
						class="table table-striped table-sm table-bordered table-hover">
						<thead class="table-dark">
							<tr>
								<th scope="col">#</th>
								<th scope="col">Nazwa użytkownika</th>
								<th scope="col">Linia</th>
								<th scope="col">Stacja</th>
								<th scope="col">Data</th>
								<th scope="col">Czas trwania</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${showReportDto != null}">
								<tr>
									<td>${showReportDto.id}</td>
									<td>${showReportDto.userName}</td>
									<td>${showReportDto.productionLineName}</td>
									<td>${showReportDto.productionMachineName}</td>
									<td>${showReportDto.date}</td>
									<td>${showReportDto.duration}</td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</div>

				<div class="row">
					<aside class="col-sm-2">

						<div class="table-responsive">
							<table
								class="table table-striped table-sm table-bordered table-hover">
								<thead class="table-dark">
									<tr>
										<th scope="col">Przyczyny awarii</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="failTypeName"
										items="${showReportDto.failTypesNames}">
										<tr>
											<td>${failTypeName}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>

					</aside>
					<br>
					<aside class="col-sm-9">
						<form:form class="row g-3"
							action="${pageContext.request.contextPath}/user/processSaveUser"
							modelAttribute="showReportDto" accept-charset="UTF-8">

							<%-- 		<div class="row g-3">
								<div class="mb-3">
								<label for="exampleFormControlTextarea1" class="form-label"><h6>Opis</h6></label> 
									<textarea class="form-control"
										placeholder="${showReportDto.description}"
										id="exampleFormControlTextarea1" rows="20" disabled readonly></textarea>

								</div>
							</div> --%>
						</form:form>

						<div contentEditable="false" class="form-control">
							${showReportDto.description} <br><br>
							<c:if test="${showReportDto.isImage == true}">
								<img class="rounded"
									src="${pageContext.request.contextPath}/report/showImage?id=${showReportDto.id}"
									style="max-width: 400px; height: auto;">
								<br>
							</c:if>
						</div>

						<div class="mb-3"></div>

					</aside>
				</div>
			</main>
		</div>
	</div>
</body>
</html>