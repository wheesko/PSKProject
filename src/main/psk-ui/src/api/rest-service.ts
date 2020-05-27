import axios, { AxiosInstance, AxiosPromise, AxiosRequestConfig, CancelTokenSource } from 'axios';
import { store } from '../redux/store';
import jwt from 'jsonwebtoken';
import moment from 'moment';
import { updateToken, userLogout } from '../redux/user/actions';
import history from '../history';
import notificationService, { NotificationType } from '../service/notification-service';

export interface RestServiceConfig {
    cancelTokenSource: CancelTokenSource;
}

class RestService {
    public post = this.save;
    public put = this.update;

    private readonly axiosInstance: AxiosInstance;
    private readonly cancelTokenSource: CancelTokenSource;

    constructor(config: RestServiceConfig) {
    	this.cancelTokenSource = config.cancelTokenSource;
    	this.axiosInstance = axios.create({
    		baseURL: '/api',
    		cancelToken: this.cancelTokenSource.token,
    	});

    	this.axiosInstance.interceptors.request.use((config: AxiosRequestConfig) => {
    		// @ts-ignore
    		return config.url.includes('refreshToken') || config.url?.includes('login')
    			? appendAuthorizationHeader(config, getCurrentJWT())
    			: this.checkTokens().then(() => {
    				return appendAuthorizationHeader(config, getCurrentJWT());
    			});
    	});
    }

    public get<T = any>(url: string, config?: AxiosRequestConfig): AxiosPromise<T> {
    	return this.axiosInstance.get(url, config);
    }

    public save<T = any>(url: string, data?: any, config?: AxiosRequestConfig): AxiosPromise<T> {
    	return this.axiosInstance.post(url, data, config);
    }

    public delete<T = any>(url: string, config?: AxiosRequestConfig): AxiosPromise<T> {
    	return this.axiosInstance.delete(url, config);
    }

    public update<T = any>(url: string, data?: any, config?: AxiosRequestConfig): AxiosPromise<T> {
    	return this.axiosInstance.put(url, data, config);
    }

    public patch<T = any>(url: string, data?: any, config?: AxiosRequestConfig): AxiosPromise<T> {
    	return this.axiosInstance.patch(url, data, config);
    }

	private refreshToken = (): Promise<{accessToken: string}> => {
		return this.axiosInstance.post<{accessToken: string}>('/refreshToken', {
			accessToken: store.store.getState().user.token,
			refreshToken: store.store.getState().user.refreshToken
		})
			.then(response => {
				return new Promise(resolve =>{
					store.store.dispatch(updateToken({
						token: response.data.accessToken
					}));
					resolve();
				});
			});
	};

	private checkTokens(): Promise<any> {
		//@ts-ignore
		if(moment().add(10, 'minutes').isAfter(moment.unix(jwt.decode(getCurrentJWT()).exp))
			&& getCurrentJWT() !== null
		) {
			//@ts-ignore
			if(!moment().isAfter(moment.unix(jwt.decode(getCurrentRefreshToken()).exp))) {
				 return this.refreshToken().then(() => Promise.resolve());
			} else {
				store.store.dispatch(userLogout());
				history.push('/');
				notificationService.notify({
					notificationType: NotificationType.ERROR,
					message: 'Session expired',
					description: 'Please login again'
				});
				return Promise.resolve();
			}
		} else return Promise.resolve();
	}
}

function getCurrentJWT(): string {
	return store.store.getState().user.token;
}

function getCurrentRefreshToken(): string {
	return store.store.getState().user.refreshToken;
}

function appendAuthorizationHeader( config: AxiosRequestConfig | undefined, headers: string): AxiosRequestConfig {
	return {
		...config,
		headers: {
			...config?.headers,
			Authorization: headers
		}
	};
}

export { RestService };
