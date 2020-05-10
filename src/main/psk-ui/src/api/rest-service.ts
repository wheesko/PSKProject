import axios, { AxiosInstance, AxiosPromise, AxiosRequestConfig, CancelTokenSource } from 'axios';
import { store } from '../redux/store';

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
    }

    public get<T = any>(url: string, config?: AxiosRequestConfig): AxiosPromise<T> {
    	return this.axiosInstance.get(url, appendAuthorizationHeader(config, getCurrentJWT()));
    }

    public save<T = any>(url: string, data?: any, config?: AxiosRequestConfig): AxiosPromise<T> {
    	return this.axiosInstance.post(url, data, appendAuthorizationHeader(config, getCurrentJWT()));
    }

    public delete<T = any>(url: string, config?: AxiosRequestConfig): AxiosPromise<T> {
    	return this.axiosInstance.delete(url, appendAuthorizationHeader(config, getCurrentJWT()));
    }

    public update<T = any>(url: string, data?: any, config?: AxiosRequestConfig): AxiosPromise<T> {
    	return this.axiosInstance.put(url, data, appendAuthorizationHeader(config, getCurrentJWT()));
    }

    public patch<T = any>(url: string, data?: any, config?: AxiosRequestConfig): AxiosPromise<T> {
    	return this.axiosInstance.patch(url, data, appendAuthorizationHeader(config, getCurrentJWT()));
    }
}

function getCurrentJWT(): string {
	return store.store.getState().user.token;
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
