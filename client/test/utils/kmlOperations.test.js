import { describe, expect, test, jest } from "@jest/globals";
import { generateKML } from '@utils/kmlOperations';

describe('generateKML', () => {
    test('bscheidt: generates correct KML for valid trip data', () => {
        const tripName = 'Test Trip';
        const tripData = {
            places: [
                { latitude: 35.6895, longitude: 139.6917, defaultDisplayName: 'Tokyo', region: 'Kanto', country: 'Japan' },
                { latitude: 34.0522, longitude: -118.2437, defaultDisplayName: 'Los Angeles', region: 'California', country: 'USA' }
            ]
        };

        const result = generateKML(tripName, tripData);
        expect(result).toContain(tripName);
        expect(result).toContain('A sample tour that includes 2 places.');
        expect(result).toContain('139.6917,35.6895 -118.2437,34.0522 139.6917,35.6895'); // Check for correct coordinates string
        tripData.places.forEach(place => {
            expect(result).toContain(place.defaultDisplayName);
            expect(result).toContain(place.region);
            expect(result).toContain(place.country);
            expect(result).toContain(`${place.longitude},${place.latitude}`);
        });
    });

    test('bscheidt: generates correct KML for trip with only name', () => {
        const tripName = 'Test Trip';
        const tripData = {
            places: [
                { name: 'Tokyo', latitude: 35.6895, longitude: 139.6917 },
                { name: 'Los Angeles', latitude: 34.0522, longitude: -118.2437 }
            ]
        };

        const result = generateKML(tripName, tripData);
        expect(result).toContain(tripName);
        expect(result).toContain('A sample tour that includes 2 places.');
        expect(result).toContain('139.6917,35.6895 -118.2437,34.0522 139.6917,35.6895'); // Check for correct coordinates string
        tripData.places.forEach(place => {
            expect(result).toContain(place.name);
            expect(result).toContain('Unknown, Unknown');
            expect(result).toContain(`${place.longitude},${place.latitude}`);
        });
    });

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
        expect(result).toContain('0,0 0,0'); // Check for correct coordinates string
        tripData.places.forEach(place => {
            expect(result).toContain(place.name);
            expect(result).toContain('Unknown, Unknown');
            expect(result).toContain('0,0');
        });
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

    test('bscheidt: handles places with missing properties gracefully', () => {
        const tripName = 'Incomplete Places Trip';
        const tripData = {
            places: [
                { latitude: 35.6895, longitude: 139.6917 }, // Missing `defaultDisplayName`, `region`, `country`
            ]
        };

        const result = generateKML(tripName, tripData);
        expect(result).toContain(tripName);
        expect(result).toContain('A sample tour that includes 1 places.');
        expect(result).toContain('139.6917,35.6895');
        expect(result).toContain('Unnamed Place');
    });
});
