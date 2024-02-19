import { describe, expect, test, jest } from "@jest/globals";
import { generateKML } from '@utils/kmlOperations';

describe('generateKML', () => {

    test('bscheidt: generates correct KML for trip with no coordinates', () => {
        const tripName = 'Test Trip';
        const tripData = {
            places: [
                { name: 'Tokyo' },
                { name: 'Los Angeles' }
            ]
        };

        const result = generateKML(tripName, tripData);
        expect(result).toContain(tripName);
        expect(result).toContain('A sample tour that includes 2 places.');
        expect(result).toContain('0,0 0,0');
    });

    test('bscheidt: handles empty places array correctly', () => {
        const tripName = 'Empty Trip';
        const tripData = { places: [] };

        const result = generateKML(tripName, tripData);
        expect(result).toContain(tripName);
        expect(result).toContain('A sample tour that includes 0 places.');
        expect(result).not.toContain('<Placemark>');
    });

    test('bscheidt: handles invalid trip data gracefully', () => {
        const tripName = 'Invalid Trip';
        const tripData = {}; // No places property

        const result = generateKML(tripName, tripData);
        expect(result).toContain(tripName);
        expect(result).toContain('A sample tour that includes 0 places.');
    });
});
