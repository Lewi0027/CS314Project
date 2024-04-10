export function generateKML(tripName, tripData) {
  if (!tripData || !tripName) return generateEmptyKML();

  let description = `A sample tour that includes ${tripData.places?.length || 0} places.`;
  let kml = generateKMLHeader(tripName, description);

  kml = generateTourPlacemark(kml, tripData);
  kml = generatePlacesPlacemark(kml, tripData);
  kml += generateKMLFooter();

  return kml;
}

function generateEmptyKML() {
  return `<?xml version="1.0" encoding="UTF - 8"?>
    <kml xmlns="http://www.opengis.net/kml/2.2">
      <Document></Document>
    </kml>`;
}

function generateKMLHeader(tripName, description) {
  return `<?xml version="1.0" encoding="UTF-8"?>
<kml xmlns="http://www.opengis.net/kml/2.2">
  <Document>
    <name>${tripName}</name>
    <open>0</open>
    <description>${description}</description>
    <Style id="TourPath">
      <LineStyle>
        <color>77ffffff</color>
        <width>3</width>
      </LineStyle>
    </Style>
    <Style id="Place">
      <IconStyle>
        <scale>1.1</scale>
      </IconStyle>
    </Style>`;
}

function generateKMLFooter() {
  return `
  </Document>
</kml>`;
}

function generateTourPlacemark(kml, tripData) {
  if (Array.isArray(tripData.places) && tripData.places.length > 0) {
    // Generate the coordinates string for the tour path
    let coordinatesStr = tripData.places.map(place => `${place.longitude || '0'},${place.latitude || '0'}`).join(' ');

    // Append the first place's coordinates to the end to close the loop
    const firstPlace = tripData.places[0];
    coordinatesStr += ` ${firstPlace.longitude},${firstPlace.latitude}`;

    kml += `
        <Placemark>
          <name>Tour path</name>
          <styleUrl>#TourPath</styleUrl>
          <LineString>
            <tessellate>1</tessellate>
            <altitudeMode>clampToGround</altitudeMode>
            <coordinates>${coordinatesStr}</coordinates>
          </LineString>
        </Placemark>`;
  }

  return kml;
}

function generatePlacePlacemark(place) {
  const name = place.defaultDisplayName || place.name || 'Unnamed Place';
  const description = `${place.region || 'Unknown'}, ${place.country || 'Unknown'}`;
  const coordinates = `${place.longitude || '0'},${place.latitude || '0'}`;

  return `
    <Placemark>
      <styleUrl>#Place</styleUrl>
      <name>${name}</name>
      <description>${description}</description>
      <Point>
          <coordinates>${coordinates}</coordinates>
      </Point>
    </Placemark>`;
}

function generatePlacesPlacemark(kml, tripData) {
  if (Array.isArray(tripData.places)) {
    tripData.places.forEach(place => {
      kml += generatePlacePlacemark(place);
    });
  }

  return kml;
}