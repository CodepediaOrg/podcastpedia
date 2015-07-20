<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="about_podcasting_content" class="bg_color common_radius common_mar_pad shadowy">
	<p>
		El podcasting consiste en la distribución de archivos multimedia (normalmente audio o vídeo, que puede incluir texto como subtítulos y notas) 
		mediante un sistema de redifusión (RSS) que permita suscribirse y usar un programa que lo descarga para que el usuario lo escuche en el momento que quiera. No es necesario estar suscrito para descargarlos.
	</p>
	
	<c:url value="/static/images/podcasting/podcasting.png" var="urlImagePodcasting"/>
	<img id="image_podcasting" src="${urlImagePodcasting}" alt="podcasting graphic" title="podcasting graphic">
</div>

