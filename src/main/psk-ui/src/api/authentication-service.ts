import { CancelSource } from './cancel-source';
import { RestService } from './rest-service';
import { LoginRequest } from './model/login-request';
import { AxiosResponse } from 'axios';

class AuthenticationService {
	private readonly restService: RestService;

	constructor(cancelSource: CancelSource = new CancelSource()) {
		this.restService = cancelSource.service;
	}

	public login = (loginRequest: LoginRequest): Promise<AxiosResponse | null> => {
		return this.restService.post<string>('/login', loginRequest, { baseURL: '' });
	};

	public getSession = (): Promise<void> => {
		return this.restService.get<void>('/account').then(response => response.data);
	};

	public logout = (): Promise<void> => {
		return Promise.resolve();
	};
}

const authenticationService = new AuthenticationService();

export { AuthenticationService };
export { authenticationService as default };
