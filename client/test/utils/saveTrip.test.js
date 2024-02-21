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
});