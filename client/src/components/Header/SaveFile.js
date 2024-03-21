import React from 'react';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';
import { SaveTrip } from '@utils/saveTrip'

export default function SaveFile(props) {
    const saveFile = (format) => {
        // Assuming SaveTrip function is modified to accept format ('json' or 'kml')
        // and props contain necessary data like tripName and places
        SaveTrip(props.tripName, {places: props.places}, format);
        props.toggleSaveFile(); // Close the modal after saving
    };

    return (
        <Modal isOpen={props.showSaveFile} toggle={props.toggleSaveFile}>
            <ModalHeader toggle={props.toggleSaveFile}>Save Trip</ModalHeader>
            <ModalBody>
                Choose the format to save your trip:
            </ModalBody>
            <ModalFooter>
                <Button color="primary" onClick={() => saveFile('json')}>Save as JSON</Button>{' '}
                <Button color="secondary" onClick={() => saveFile('kml')}>Save as KML</Button>
            </ModalFooter>
        </Modal>
    );
}
