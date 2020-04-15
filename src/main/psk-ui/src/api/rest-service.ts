import axios, { AxiosInstance, AxiosPromise, AxiosRequestConfig, CancelTokenSource } from 'axios';

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
    		cancelToken: this.cancelTokenSource.token
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
}

export { RestService };
