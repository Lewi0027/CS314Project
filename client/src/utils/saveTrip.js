import {generateKML} from "./kmlOperations.js";

export function SaveTrip(tripName, tripData, format= 'json'){
    let fileContent, fileType, fileExtension;
    
    if (format === 'kml') {
        fileContent = generateKML(tripName, tripData);
        fileType = "application/vnd.google-earth.kml+xml";
        fileExtension = ".kml";
    }
    else { //Defaults to KML
        fileContent = JSON.stringify(tripData);
        fileType = "application/json";
        fileExtension = ".json";
    }

    const file = new Blob([fileContent], { type: fileType });
    const link = document.createElement("a");
    const url = URL.createObjectURL(file);
    link.href = url;
    link.download = tripName + fileExtension;
    document.body.appendChild(link);
    link.click();
    
    setTimeout(function() {
        document.body.removeChild(link);
        window.URL.revokeObjectURL(url);
    }, 0);
}