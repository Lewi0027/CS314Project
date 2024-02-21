import { describe, expect, test, jest } from "@jest/globals";
import { SaveTrip } from '@utils/saveTrip';
import { afterEach, beforeEach, it } from "node:test";

describe('SaveFile', () =>{
    beforeEach(() =>{
        global.URL.createObjectURL = jest.fn();
        global.document.body.appendChild = jest.fn();
        global.document.body.removeChild = jest.fn();
        global.window.URL.revokeObjectURL = jest.fn();
    });

    afterEach(() =>{
        jest.clearAllMocks();
    });

    test('base: default file without places', ()=>{
        const tripName = 'default test trip';
        const fileText = <Placemark><name></name></Placemark>;
        const downloadURL = 'mocked-URL';

        global.URL.createObjectURL.mockReturnValue(downloadURL);

        SaveTrip(tripName, fileText);

        expect(global.document.createElement).toHaveBeenCalledWith('a');
        expect(global.URL.createObjectURL).toHaveBeenCalledWith(expect.any(Blob));
        expect(global.document.body.appendChild).toHaveBeenCalledWith(expect.any(HTMLAnchorElement));
        expect(global.document.body.removeChild).toHaveBeenCalledWith(expect.any(HTMLAnchorElement));
        expect(global.window.URL.revokeObjectURL).toHaveBeenCalledWith(downloadURL);
    })

    test('base: KML file without places', () =>{
        const tripName = 'test Trip';
        const fileText = <Placemark><name></name></Placemark>;
        const downloadURL = 'mocked-URL';

        global.URL.createObjectURL.mockReturnValue(downloadURL);

        SaveTrip(tripName, fileText, kml);

        expect(global.document.createElement).toHaveBeenCalledWith('a');
        expect(global.URL.createObjectURL).toHaveBeenCalledWith(expect.any(Blob));
        expect(global.document.body.appendChild).toHaveBeenCalledWith(expect.any(HTMLAnchorElement));
        expect(global.document.body.removeChild).toHaveBeenCalledWith(expect.any(HTMLAnchorElement));
        expect(global.window.URL.revokeObjectURL).toHaveBeenCalledWith(downloadURL);
    })
})