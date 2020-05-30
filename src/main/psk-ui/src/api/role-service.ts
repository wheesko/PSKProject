import { CancelSource } from './cancel-source';
import { RestService } from './rest-service';
import { AxiosResponse } from 'axios';
import { TopicCreateRequest } from './model/topic-create-request';
import { Role } from "../models/role";

class RoleService {
	private readonly restService: RestService;

	constructor(cancelSource: CancelSource = new CancelSource()) {
		this.restService = cancelSource.service;
	}

	public getAllRoles = (): Promise<Role[]> => {
		return this.restService
			.get<Role[]>(
				'/roles/getAll'
			)
			.then((response: AxiosResponse) => {
				return response.data;
			});
	};

	public createNewTopic = (topicCreateRequest: TopicCreateRequest): Promise<void> => {
		return this.restService
			.post<void>(
				'/topics/create', topicCreateRequest
			)
			.then((response: AxiosResponse<void>) => {
				return response.data;
			});
	};
}

const roleService = new RoleService();

export { RoleService };
export { roleService as default };
