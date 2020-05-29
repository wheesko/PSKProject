import { CancelSource } from './cancel-source';
import { RestService } from './rest-service';
import { AxiosResponse } from 'axios';

class TreeService {
    private readonly restService: RestService;

    constructor(cancelSource: CancelSource = new CancelSource()) {
    	this.restService = cancelSource.service;
    }

    public getTree = (): Promise<any> => {
    	return this.restService
    		.get<any>(
    			'/topics/getTopicTree'
    		)
    		.then((response: AxiosResponse<void>) => {
    			return response.data;
    		});
    };
}

const treeService = new TreeService();

export { TreeService };
export { treeService as default };
