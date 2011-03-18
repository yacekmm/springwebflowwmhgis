var mapa;
		function dodajMarker(lat,lon,opcjeMarkera)
		{
			// tworzymy marker z wspó³rzêdnymi i opcjami z argumentów funkcji
			// dodajMarker
			opcjeMarkera.position = new google.maps.LatLng(lat,lon);
			opcjeMarkera.title = "dd";
			// opcjeMarkera.map = mapa; // obiekt mapa jest obiektem globalnym!
			var marker = new google.maps.Marker(opcjeMarkera);
			marker.setMap(mapa);
		} 
		function mapaStart()   
		{   
			var wspolrzedne = new google.maps.LatLng(#{gmBean.latitude},#{gmBean.longitude});
			var opcjeMapy = {
			  zoom: #{gmBean.zoom},
			  center: wspolrzedne,
			  mapTypeId: #{gmBean.mapType}
			};
			mapa = new google.maps.Map(document.getElementById("mapka"), opcjeMapy);
			// stworzenie markera
            var punkt  = new google.maps.LatLng(53.400,14.600);  
            var opcjeMarkera =  
            {  
                position: wspolrzedne,  
                map: mapa,  
                title: "Pierwszy marker!"  
            }  
            var marker = new google.maps.Marker(opcjeMarkera);   
           // dodajMarker(52.000,19.000,{});
		}