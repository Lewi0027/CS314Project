import React from 'react';
import { beforeEach, describe, expect, test } from '@jest/globals';
import user from '@testing-library/user-event';
import { cleanup, render, screen, waitFor } from '@testing-library/react';
import VerifySearch from '@components/Header/VerifySearch';
import { DEFAULT_STARTING_POSITION } from '@utils/constants';
import { isFeatureImplemented, sendAPIRequest } from '@utils/restfulAPI';
import { verifySearch } from '../../../src/components/Header/VerifySearch';
import { MOCK_PLACES } from '../../sharedMocks';

jest.mock('@utils/restfulAPI', () => ({
    isFeatureImplemented: jest.fn(),
    sendAPIRequest: jest.fn()
}));

describe('AddPlace', () => {
	const placeObj = {
		latLng: '40.57, -105.085',
		name: 'Colorado State University, South College Avenue, Fort Collins, Larimer County, Colorado, 80525-1725, United States',
	};

	const props = {
		serverSettings: {serverUrl: "example.com"},
        coordString: "",
        selectedCountry: "United States",
        selectedType: "airport",
        setFoundPlaces: jest.fn(),

	};

    test('base: doesn\'t render', async() => {
        cleanup();
        isFeatureImplemented.mockResolvedValueOnce(false);
        sendAPIRequest.mockResolvedValueOnce({places: []});
        await verifySearch(props);
		expect(props.setFoundPlaces).toHaveBeenCalledWith([]);
	});

    test('base: renders everything', async() => {
        cleanup();
        isFeatureImplemented.mockResolvedValueOnce(true);
        sendAPIRequest.mockResolvedValueOnce({places: MOCK_PLACES});
        await verifySearch(props);
        expect(props.setFoundPlaces).toBeCalledWith(MOCK_PLACES);
    })
});
