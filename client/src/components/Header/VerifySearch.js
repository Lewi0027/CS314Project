import { sendAPIRequest } from '@utils/restfulAPI';
import { LOG } from '@utils/constants';
import { isFeatureImplemented } from '@utils/restfulAPI';

async function verifySearch(props) {
	if (!isFeatureImplemented(props.serverSettings, "find")){
	  props.setFoundPlaces([]);
	  return;
	}
	const requestBody = {
	  requestType: "find",
	  match: props.coordString,
	  limit: 5,
	};
	if(props.selectedCountry != 'All Countries'){
	  requestBody.where = [props.selectedCountry];
	}
	if(props.selectedType != 'All Types'){
	  requestBody.type = [props.selectedType];
	}
	const findResponse = await sendAPIRequest(requestBody, props.serverSettings.serverUrl);
	if(findResponse){
	  props.setFoundPlaces(findResponse.places);
	}
	else{
	  LOG.error(`Find request to ${props.serverSettings.serverUrl} failed. Check the log for more details.`, "error");
	}
  }

  export {verifySearch};