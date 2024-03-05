import { describe, expect, test, jest } from "@jest/globals";
import { SaveTrip } from '@utils/saveTrip';

describe('SaveTrip', () =>{
    beforeEach(() =>{
        jest.useFakeTimers();
        global.URL.createObjectURL = jest.fn();
        global.document.body.appendChild = jest.fn();
        global.document.body.removeChild = jest.fn();
        global.window.URL.revokeObjectURL = jest.fn();

        createElementMock = jest.spyOn(document, 'createElement').mockImplementation((tagName) => {
            if (tagName === 'a'){
                return { 
                    href: '',
                    download: '',
                    click: jest.fn(), 
                };
            }
            return null;
        });
    });

    afterEach(() =>{
        jest.clearAllMocks();
        jest.useRealTimers();
    });

    test('diegocel: default file without places', ()=>{
        const tripName = 'default test trip';
        const fileText = '{"destination": ""}';
        const downloadURL = 'mocked-URL';

        global.URL.createObjectURL.mockReturnValue(downloadURL);

        SaveTrip(tripName, fileText);

        jest.runAllTimers();

        expect(global.document.createElement).toHaveBeenCalledWith('a');
        expect(global.URL.createObjectURL).toHaveBeenCalledWith(expect.any(Blob));
        expect(global.document.body.appendChild).toHaveBeenCalledWith(expect.objectContaining({
            href: expect.any(String),
            download: expect.any(String),
        }));
        expect(global.window.URL.revokeObjectURL).toHaveBeenCalledWith(downloadURL);
    });

    test('bscheidt: invalid format', () => {
        const tripName = 'default test trip';
        const fileText = '{"destination": ""}';
        const downloadURL = 'mocked-URL';
        const format = "test"

        global.URL.createObjectURL.mockReturnValue(downloadURL);

        SaveTrip(tripName, fileText, format);

        expect(global.document.createElement).not.toHaveBeenCalledWith('a');
        expect(global.URL.createObjectURL).not.toHaveBeenCalledWith(expect.any(Blob));
        expect(global.document.body.appendChild).not.toHaveBeenCalledWith(expect.objectContaining({
            href: expect.any(String),
            download: expect.any(String),
        }));
        expect(global.window.URL.revokeObjectURL).not.toHaveBeenCalledWith(downloadURL);

    });

    test('ajlei: default file with one place', ()=>{
        const tripName = 'default test trip';
        const fileText = '{"places":[{"name":"Colorado State University","streetAddress":"South College Avenue","latitude":"40.57474577527911","longitude":"-105.08438232095821","municipality":"Fort Collins","region":"Colorado","country":"United States","postcode":"80524","defaultDisplayName":"Colorado State University"}]}';
        const downloadURL = 'mocked-URL';

        global.URL.createObjectURL.mockReturnValue(downloadURL);

        SaveTrip(tripName, fileText);

        jest.runAllTimers();

        expect(global.document.createElement).toHaveBeenCalledWith('a');
        expect(global.URL.createObjectURL).toHaveBeenCalledWith(expect.any(Blob));
        expect(global.document.body.appendChild).toHaveBeenCalledWith(expect.objectContaining({
            href: expect.any(String),
            download: expect.any(String),
        }));
        expect(global.window.URL.revokeObjectURL).toHaveBeenCalledWith(downloadURL);
    });

    test('ajlei: default file with three places', ()=>{
        const tripName = 'default test trip';
        const fileText = '{"places":[{"name":"Colorado State University","streetAddress":"South College Avenue","latitude":"40.57473775378398","longitude":"-105.0846505165373","municipality":"Fort Collins","region":"Colorado","country":"United States","postcode":"80524","defaultDisplayName":"Colorado State University"},{"name":"Moby Arena","streetAddress":"951 West Plum Street","latitude":"40.57561147381013","longitude":"-105.09316921236862","municipality":"Fort Collins","region":"Colorado","country":"United States","postcode":"80521","defaultDisplayName":"Moby Arena"},{"name":"Student Recreation Center","streetAddress":"Meridian Avenue","latitude":"40.57516871482643","longitude":"-105.08986473086225","municipality":"Fort Collins","region":"Colorado","country":"United States","postcode":"80521","defaultDisplayName":"Student Recreation Center"}]}';
        const downloadURL = 'mocked-URL';

        global.URL.createObjectURL.mockReturnValue(downloadURL);

        SaveTrip(tripName, fileText);

        jest.runAllTimers();

        expect(global.document.createElement).toHaveBeenCalledWith('a');
        expect(global.URL.createObjectURL).toHaveBeenCalledWith(expect.any(Blob));
        expect(global.document.body.appendChild).toHaveBeenCalledWith(expect.objectContaining({
            href: expect.any(String),
            download: expect.any(String),
        }));
        expect(global.window.URL.revokeObjectURL).toHaveBeenCalledWith(downloadURL);
    });


    test('diegocel: KML file without places', () =>{
        const tripName = 'test Trip';
        const fileText = '<Placemark><name></name></Placemark>';
        const downloadURL = 'mocked-URL';

        global.URL.createObjectURL.mockReturnValue(downloadURL);

        SaveTrip(tripName, fileText, 'kml');

        jest.runAllTimers();

        expect(global.document.createElement).toHaveBeenCalledWith('a');
        expect(global.URL.createObjectURL).toHaveBeenCalledWith(expect.any(Blob));
        expect(global.document.body.appendChild).toHaveBeenCalledWith(expect.objectContaining({
            href: expect.any(String),
            download: expect.any(String),
        }));
        expect(global.window.URL.revokeObjectURL).toHaveBeenCalledWith(downloadURL);
    });

    test('ajlei: KML file with one place', () =>{
        const tripName = 'test one place trip';
        const fileText = `
            <?xml version="1.0" encoding="UTF-8"?>
            <kml xmlns="http://www.opengis.net/kml/2.2">
            <Document>
                <name>My Trip</name>
                <open>0</open>
                <description>A sample tour that includes 1 places.</description>
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
                </Style>
                    <Placemark>
                    <name>Tour path</name>
                    <styleUrl>#TourPath</styleUrl>
                    <LineString>
                        <tessellate>1</tessellate>
                        <altitudeMode>clampToGround</altitudeMode>
                        <coordinates>-105.08438232095821,40.57474577527911 -105.08438232095821,40.57474577527911</coordinates>
                    </LineString>
                    </Placemark>
                        <Placemark>
                        <styleUrl>#Place</styleUrl>
                        <name>Colorado State University</name>
                        <description>Colorado, United States</description>
                        <Point>
                            <coordinates>-105.08438232095821,40.57474577527911</coordinates>
                        </Point>
                        </Placemark>
            </Document>
            </kml>
        `;
        const downloadURL = 'mocked-URL';
    
        global.URL.createObjectURL.mockReturnValue(downloadURL);
    
        SaveTrip(tripName, fileText, 'kml');
    
        jest.runAllTimers();
    
        expect(global.document.createElement).toHaveBeenCalledWith('a');
        expect(global.URL.createObjectURL).toHaveBeenCalledWith(expect.any(Blob));
        expect(global.document.body.appendChild).toHaveBeenCalledWith(expect.objectContaining({
            href: expect.any(String),
            download: expect.any(String),
        }));
        expect(global.window.URL.revokeObjectURL).toHaveBeenCalledWith(downloadURL);
    });

    test('ajlei: KML file with three places', () =>{
        const tripName = 'test three places trip';
        const fileText = `
            <?xml version="1.0" encoding="UTF-8"?>
            <kml xmlns="http://www.opengis.net/kml/2.2">
            <Document>
                <name>My Trip</name>
                <open>0</open>
                <description>A sample tour that includes 3 places.</description>
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
                </Style>
                    <Placemark>
                    <name>Tour path</name>
                    <styleUrl>#TourPath</styleUrl>
                    <LineString>
                        <tessellate>1</tessellate>
                        <altitudeMode>clampToGround</altitudeMode>
                        <coordinates>-105.0846505165373,40.57473775378398 -105.09316921236862,40.57561147381013 -105.08986473086225,40.57516871482643 -105.0846505165373,40.57473775378398</coordinates>
                    </LineString>
                    </Placemark>
                        <Placemark>
                        <styleUrl>#Place</styleUrl>
                        <name>Colorado State University</name>
                        <description>Colorado, United States</description>
                        <Point>
                            <coordinates>-105.0846505165373,40.57473775378398</coordinates>
                        </Point>
                        </Placemark>
                        <Placemark>
                        <styleUrl>#Place</styleUrl>
                        <name>Moby Arena</name>
                        <description>Colorado, United States</description>
                        <Point>
                            <coordinates>-105.09316921236862,40.57561147381013</coordinates>
                        </Point>
                        </Placemark>
                        <Placemark>
                        <styleUrl>#Place</styleUrl>
                        <name>Student Recreation Center</name>
                        <description>Colorado, United States</description>
                        <Point>
                            <coordinates>-105.08986473086225,40.57516871482643</coordinates>
                        </Point>
                        </Placemark>
            </Document>
            </kml>
        `;
        const downloadURL = 'mocked-URL';
    
        global.URL.createObjectURL.mockReturnValue(downloadURL);
    
        SaveTrip(tripName, fileText, 'kml');
    
        jest.runAllTimers();
    
        expect(global.document.createElement).toHaveBeenCalledWith('a');
        expect(global.URL.createObjectURL).toHaveBeenCalledWith(expect.any(Blob));
        expect(global.document.body.appendChild).toHaveBeenCalledWith(expect.objectContaining({
            href: expect.any(String),
            download: expect.any(String),
        }));
        expect(global.window.URL.revokeObjectURL).toHaveBeenCalledWith(downloadURL);
    });
    
});