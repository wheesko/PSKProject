import { CancelSource } from './cancel-source';
import { RestService } from './rest-service';

class AuthenticationService {
    private readonly restService: RestService;

    constructor(cancelSource: CancelSource = new CancelSource()) {
    	this.restService = cancelSource.service;
    }
}

const authenticationService = new AuthenticationService();

export { AuthenticationService };
export { authenticationService as default };
