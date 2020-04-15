import { CancelSource } from './cancel-source';
import { RestService } from './rest-service';
import { LoginRequest } from './model/login-request';
import jwt from 'jsonwebtoken';

class AuthenticationService {
    private readonly restService: RestService;

    constructor(cancelSource: CancelSource = new CancelSource()) {
    	this.restService = cancelSource.service;
    }

    public login = (loginRequest: LoginRequest): Promise<string | null> => {
    	return this.restService.post<string>('/login', loginRequest, { baseURL: '' })
    		.then(response => {
    		    //save token to local storage
    			//decode token
    			//set token, roles, username to redux
    			jwt.decode(response.headers.authorization.replace('Bearer ', ''));

    		    return response.headers.authorization;
    		});
    };
	public getSession = (): Promise<void> => {
		return this.restService.get<void>('/account').then(response => response.data);
	}
}

const authenticationService = new AuthenticationService();

export { AuthenticationService };
export { authenticationService as default };
